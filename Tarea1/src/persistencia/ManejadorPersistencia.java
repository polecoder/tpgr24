package persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

// TODO: Revisar el funcionamiento al pasar a Web Services
public class ManejadorPersistencia {
	private static ManejadorPersistencia instancia;
	private ManejadorEventos manejadorEventos;
	private ManejadorUsuarios manejadorUsuarios;
	private EntityManagerFactory fabricaManejadorEntidades;

	private ManejadorPersistencia() {
		this.manejadorEventos = ManejadorEventos.getInstancia();
		this.manejadorUsuarios = ManejadorUsuarios.getInstance();
		
		// configurar correctamente la URL de la EntityManagerFactory
		String basePath = System.getProperty("catalina.base");
		if (basePath == null || basePath.isEmpty()) basePath = System.getProperty("user.dir");
		try {
			// TODO: Super hardcodeado -> Mucho ojo con esto en Facultad
			Path carpetaData = Paths.get(basePath, "Tarea1/data");
			Files.createDirectories(carpetaData);
			
			// para configurar el persistence.xml
			String jdbcUrl = "jdbc:hsqldb:file:" + carpetaData.resolve("ArchivoDB").toAbsolutePath().toString() + ";shutdown=true";
			Map<String, String> propiedades = new HashMap<>();
			propiedades.put("jakarta.persistence.jdbc.url", jdbcUrl);
			propiedades.put("jakarta.persistence.jdbc.user", "SA");
            propiedades.put("jakarta.persistence.jdbc.password", "");
            propiedades.put("jakarta.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
			
			// creamos el EntityManagerFactory con la url correcta
			this.fabricaManejadorEntidades = Persistence.createEntityManagerFactory("ArchivoDB", propiedades);
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
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

	public static ManejadorPersistencia getInstance() {
		if (instancia == null) {
			instancia = new ManejadorPersistencia();
		}
		return instancia;
	}

	public static void resetInstance() {
		if (instancia != null) instancia.shutdown();
		ManejadorEventos.resetInstancia();
		ManejadorUsuarios.resetInstance();
		instancia = null;
	}

	/**
	 * PRE-CONDICIÓN: El asistente existe en el sistema.<p>
	 * Guarda el asistente con nickname `nicknameAsistente` en el archivo.
	 * 
	 * @param nicknameAsistente el nickname del asistente para guardar en el archivo
	 */
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

	/**
	 * PRE-CONDICIÓN: El organizador existe en el sistema.<p>
	 * Guarda el organizador con nickname `nicknameOrganizador` en el archivo.
	 * 
	 * @param nicknameOrganizador el nickname del organizador para guardar en el archivo
	 */
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

	/**
	 * PRE-CONDICIÓN: El asistente asociado al registro ya está en el archivo. De la misma forma la edición asociada al registro ya está en el archivo<p>
	 * Guarda el registro con los datos pasados por el parámetro `registro` en el archivo
	 * 
	 * @param registro los datos del registro para guardar en el archivo
	 */
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

	/**
	 * Archiva una edición y todos sus datos relacionados en el archivo.
	 * Esto incluye:<p>
	 * 1. Registros asociados<p>
	 * 2. Asistentes asociados a los registros asociados<p>
	 * 3. Organizadores asociados<p>
	 * 
	 * @param registro los datos del registro para guardar en el archivo
	 */
	public void archivarEdicion(String nombreEvento, String nombreEdicion, Date fechaArchivo) {
		EntityManager manejadorEntidades = this.fabricaManejadorEntidades.createEntityManager();
		EntityTransaction transaccion = manejadorEntidades.getTransaction();
		try {
			transaccion.begin();
			
			// obtengo los datos de la edición
			DTEdicionEvento datosEdicion = this.manejadorEventos.getDTEdicion(nombreEdicion, nombreEdicion);
			if (datosEdicion == null) {
				transaccion.rollback();
				return; // no hago nada si la edición no existe en el sistema
			}
			
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
