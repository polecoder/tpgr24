package persistencia;

import java.util.Date;
import java.util.List;
import java.util.Set;

import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleRegistro;
import datatypes.DTDetalleUsuario;
import datatypes.DTEdicionEvento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import logica.ManejadorEventos;
import logica.ManejadorUsuarios;
import persistencia.TipoUsuario.TipoUsuario;

public class ControladorPersistencia implements IControladorPersistencia {
	private static ControladorPersistencia instancia;
	private ManejadorEventos manejadorEventos;
	private ManejadorUsuarios manejadorUsuarios;
	private EntityManagerFactory fabricaManejadorEntidades = Persistence.createEntityManagerFactory("ArchivoDB");

	private ControladorPersistencia() {
		this.manejadorEventos = ManejadorEventos.getInstancia();
		this.manejadorUsuarios = ManejadorUsuarios.getInstance();
		
		// cerrar el EntityManagerFactory al cerrar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "ControladorPersistencia-shutdownHook"));
	}
	
	/**
	 * Utilizado para cerrar el EntityManagerFactory si está abierto, se llama cuando se cierra la aplicación (visto desde el constructor de la clase)
	 */
	public synchronized void shutdown() {
        try {
            if (fabricaManejadorEntidades != null && fabricaManejadorEntidades.isOpen()) {
                fabricaManejadorEntidades.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static ControladorPersistencia getInstance() {
		if (instancia == null) {
			instancia = new ControladorPersistencia();
		}
		return instancia;
	}

	public static void resetInstance() {
		if (instancia != null) instancia.shutdown();
		ManejadorEventos.resetInstancia();
		ManejadorUsuarios.resetInstance();
		instancia = null;
	}

	@Override
	public void guardarAsistenteEnArchivo(String nicknameAsistente) {
		EntityManager manejadorEntidades = this.fabricaManejadorEntidades.createEntityManager();
		EntityTransaction transaccion = manejadorEntidades.getTransaction();
		try {
			transaccion.begin();
			
			DTDetalleUsuario usuario = this.manejadorUsuarios.mostrarDetalleUsuario(nicknameAsistente);
			if (usuario instanceof DTDetalleOrganizador) return; // si el usuario pasado es un organizador retorna sin hacer nada
			
			DTDetalleAsistente asistente = (DTDetalleAsistente) usuario;
			UsuarioArchivado datosUsuario = new UsuarioArchivado();
			datosUsuario.setNickname(asistente.getNickname());
			datosUsuario.setEmail(asistente.getCorreo());
			datosUsuario.setNombre(asistente.getNombre());
			datosUsuario.setTipoUsuario(TipoUsuario.ASISTENTE);
			
			manejadorEntidades.persist(datosUsuario);
			manejadorEntidades.flush();
			
			AsistenteArchivado datosAsistente = new AsistenteArchivado();
			datosAsistente.setUsuario(datosUsuario);
			datosAsistente.setApellido(asistente.getApellido());
			datosAsistente.setFechaNacimiento(asistente.getNacimiento());
			datosAsistente.setNombreInstitucion(asistente.getInstitucion());
			// TODO: Ver si es necesario guardar los registros asociados al usuario en este momento (probablemente no)
			
			manejadorEntidades.persist(datosAsistente);
			manejadorEntidades.flush();
			
			transaccion.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
			if (manejadorEntidades.getTransaction().isActive()) manejadorEntidades.getTransaction().rollback();
		} finally {
			manejadorEntidades.close();
		}
	}

	@Override
	public void guardarOrganizadorEnArchivo(String nicknameOrganizador) {
		EntityManager manejadorEntidades = this.fabricaManejadorEntidades.createEntityManager();
		EntityTransaction transaccion = manejadorEntidades.getTransaction();
		try {
			transaccion.begin();
			
			DTDetalleUsuario usuario = this.manejadorUsuarios.mostrarDetalleUsuario(nicknameOrganizador);
			if (usuario instanceof DTDetalleAsistente) return; // si el usuario pasado es un asistente retorna sin hacer nada
			
			DTDetalleOrganizador organizador = (DTDetalleOrganizador) usuario;
			UsuarioArchivado datosUsuario = new UsuarioArchivado();
			datosUsuario.setNickname(organizador.getNickname());
			datosUsuario.setEmail(organizador.getCorreo());
			datosUsuario.setNombre(organizador.getNombre());
			datosUsuario.setTipoUsuario(TipoUsuario.ORGANIZADOR);
			
			manejadorEntidades.persist(datosUsuario);
			manejadorEntidades.flush();
			
			OrganizadorArchivado datosOrganizador = new OrganizadorArchivado();
			datosOrganizador.setUsuario(datosUsuario);
			datosOrganizador.setDescripcion(organizador.getDescripcion());
			datosOrganizador.setSitioWeb(organizador.getLink());
			
			manejadorEntidades.persist(datosOrganizador);
			manejadorEntidades.flush();
			
			transaccion.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
			if (manejadorEntidades.getTransaction().isActive()) manejadorEntidades.getTransaction().rollback();
		} finally {
			manejadorEntidades.close();
		}
	}

	@Override
	public void guardarRegistroEnArchivo(DTDetalleRegistro registro) {
		EntityManager manejadorEntidades = this.fabricaManejadorEntidades.createEntityManager();
		EntityTransaction transaccion = manejadorEntidades.getTransaction();
		try {
			transaccion.begin();
			
			// obtenemos la edición
			TypedQuery<EdicionArchivada> queryEdicion = manejadorEntidades.createQuery("SELECT e FROM EdicionArchivada e WHERE e.nombre = :nombre", EdicionArchivada.class); 
			queryEdicion.setParameter("nombre", registro.getNombreEdicion());
			EdicionArchivada edicion = queryEdicion.getSingleResult();
			
			// obtenemos el asistente
			TypedQuery<AsistenteArchivado> queryAsistente = manejadorEntidades.createQuery("SELECT a FROM AsistenteArchivado a WHERE a.nickname = :nickname", AsistenteArchivado.class); 
			queryAsistente.setParameter("nickname", registro.getNicknameAsistente());
			AsistenteArchivado asistente = queryAsistente.getSingleResult();
			
			// creamos el registro
			RegistroArchivado datosRegistro = new RegistroArchivado();
			datosRegistro.setAsistente(asistente);
			datosRegistro.setEdicion(edicion);
			datosRegistro.setCodigoPatrocinio(registro.getCodigoPatrocinio());
			datosRegistro.setCosto(registro.getCosto());
			datosRegistro.setFechaRegistro(registro.getFecha());
			datosRegistro.setNombreTipoRegistro(registro.getNombreTipo());
			datosRegistro.setUsoPatrocinio(registro.getAsistio());
			
			manejadorEntidades.persist(datosRegistro);
			manejadorEntidades.flush();
			
			// agregamos el registro al asistente (no es necesario persistir en la DB)
			asistente.agregarRegistro(datosRegistro);
			
			transaccion.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
			if (manejadorEntidades.getTransaction().isActive()) manejadorEntidades.getTransaction().rollback();
		} finally {
			manejadorEntidades.close();
		}
	}

	@Override
	public void archivarEdicion(String nombreEvento, String nombreEdicion, Date fechaArchivo) {
		EntityManager manejadorEntidades = this.fabricaManejadorEntidades.createEntityManager();
		EntityTransaction transaccion = manejadorEntidades.getTransaction();
		try {
			transaccion.begin();
			
			// obtengo los datos de la edición
			DTEdicionEvento datosEdicion = this.manejadorEventos.getDTEdicion(nombreEdicion, nombreEdicion);
			if (datosEdicion == null) return; // no hago nada si la edición no existe en el sistema
			
			// archivo el organizador asociado a la edición y obtenemos el objeto en la DB
			this.guardarOrganizadorEnArchivo(datosEdicion.getOrganizadorNickname());
			TypedQuery<OrganizadorArchivado> queryOrganizador = manejadorEntidades.createQuery("SELECT o FROM OrganizadorArchivado o WHERE o.nickname = :nickname", OrganizadorArchivado.class); 
			queryOrganizador.setParameter("nickname", datosEdicion.getOrganizadorNickname());
			OrganizadorArchivado organizador = queryOrganizador.getSingleResult();
			
			// creamos la edición
			EdicionArchivada edicion = new EdicionArchivada();
			edicion.setCiudad(datosEdicion.getCiudad());
			edicion.setFechaArchivo(fechaArchivo);
			edicion.setFechaCreacion(datosEdicion.getFechaAlta());
			edicion.setFechaInicio(datosEdicion.getFechaInicio());
			edicion.setFechaFin(datosEdicion.getFechaFin());
			edicion.setNombre(datosEdicion.getNombre());
			edicion.setNombreEvento(nombreEvento);
			edicion.setPais(datosEdicion.getPais());
			edicion.setSigla(datosEdicion.getSigla());
			
			// linkeamos la edición con el organizador
			edicion.setOrganizador(organizador);
			
			manejadorEntidades.persist(edicion);
			manejadorEntidades.flush();
			
			// archivar a todos los asistentes y registros asociados a la edición
			Set<DTDetalleRegistro> registros = this.manejadorEventos.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion);
			for (DTDetalleRegistro registro: registros) {
				this.guardarAsistenteEnArchivo(registro.getNicknameAsistente());
				this.guardarRegistroEnArchivo(registro);
			}
			
			// linkear todos los registros con la edición (no es necesario persistir en la DB)
			TypedQuery<RegistroArchivado> queryRegistros = manejadorEntidades.createQuery("SELECT r FROM RegistroArchivado r", RegistroArchivado.class); 
			List<RegistroArchivado> registrosArchivados = queryRegistros.getResultList();
			edicion.setRegistros(registrosArchivados);
			
			transaccion.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
			if (manejadorEntidades.getTransaction().isActive()) manejadorEntidades.getTransaction().rollback();
		} finally {
			manejadorEntidades.close();
		}
	}
}
