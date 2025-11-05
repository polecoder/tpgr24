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
import excepciones.EdicionYaArchivada;
import excepciones.UsuarioNoExiste;
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
		if (basePath == null || basePath.isEmpty())
			basePath = System.getProperty("user.dir");
		try {
			// TODO: Super hardcodeado -> Mucho ojo con esto en Facultad
			Path carpetaData = Paths.get(basePath, "Tarea1/data");
			Files.createDirectories(carpetaData);

			// para configurar el persistence.xml
			String jdbcUrl = "jdbc:hsqldb:file:" + carpetaData.resolve("ArchivoDB").toAbsolutePath().toString()
					+ ";shutdown=true";
			Map<String, String> propiedades = new HashMap<>();
			propiedades.put("jakarta.persistence.jdbc.url", jdbcUrl);
			propiedades.put("jakarta.persistence.jdbc.user", "SA");
			propiedades.put("jakarta.persistence.jdbc.password", "");
			propiedades.put("jakarta.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
			// TODO: Eliminar las siguientes dos propiedades, sirven para logging
			propiedades.put("eclipselink.logging.level", "FINE");
			propiedades.put("eclipselink.logging.parameters", "true");

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
	 * Utilizado para cerrar el EntityManagerFactory si está abierto, se llama
	 * cuando se cierra la aplicación (visto desde el constructor de la clase)
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
		if (instancia != null)
			instancia.shutdown();
		ManejadorEventos.resetInstancia();
		ManejadorUsuarios.resetInstance();
		instancia = null;
	}

	/**
	 * PRE-CONDICIÓN: El asistente existe en el sistema.
	 * <p>
	 * Guarda el asistente con nickname `nicknameAsistente` en el archivo.
	 * 
	 * @param manejadorEntidades para registrar los movimientos
	 * @param nicknameAsistente  el nickname del asistente para guardar en el
	 *                           archivo
	 */
	private void guardarAsistenteEnArchivo(EntityManager manejadorEntidades, String nicknameAsistente) {
		DTDetalleUsuario usuario;
		try {
			usuario = this.manejadorUsuarios.mostrarDetalleUsuario(nicknameAsistente);
			if (usuario instanceof DTDetalleOrganizador)
				return; // si el usuario pasado es un organizador retorna sin hacer nada

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
		} catch (UsuarioNoExiste e) {
			e.printStackTrace();
		}
	}

	/**
	 * PRE-CONDICIÓN: El organizador existe en el sistema.
	 * <p>
	 * Guarda el organizador con nickname `nicknameOrganizador` en el archivo.
	 * 
	 * @param manejadorEntidades  para registrar los movimientos
	 * @param nicknameOrganizador el nickname del organizador para guardar en el
	 *                            archivo
	 */
	private void guardarOrganizadorEnArchivo(EntityManager manejadorEntidades, String nicknameOrganizador) {
		DTDetalleUsuario usuario;
		try {
			usuario = this.manejadorUsuarios.mostrarDetalleUsuario(nicknameOrganizador);
			if (usuario instanceof DTDetalleAsistente) {
				return; // si el usuario pasado es un asistente retorna sin hacer nada
			}

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
		} catch (UsuarioNoExiste e) {
			e.printStackTrace();
		}
	}

	/**
	 * PRE-CONDICIÓN: El asistente asociado al registro ya está en el archivo. De la
	 * misma forma la edición asociada al registro ya está en el archivo
	 * <p>
	 * Guarda el registro con los datos pasados por el parámetro `registro` en el
	 * archivo
	 * 
	 * @param manejadorEntidades para registrar los movimientos
	 * @param registro           los datos del registro para guardar en el archivo
	 */
	private void guardarRegistroEnArchivo(EntityManager manejadorEntidades, DTDetalleRegistro registro) {
		// obtenemos la edición
		TypedQuery<EdicionArchivada> queryEdicion = manejadorEntidades
				.createQuery("SELECT e FROM EdicionArchivada e WHERE e.nombre = :nombre", EdicionArchivada.class);
		queryEdicion.setParameter("nombre", registro.getNombreEdicion());
		EdicionArchivada edicion = queryEdicion.getSingleResult();

		// obtenemos el asistente
		TypedQuery<UsuarioArchivado> queryUsuario = manejadorEntidades
				.createQuery("SELECT u FROM UsuarioArchivado u WHERE u.nickname = :nickname", UsuarioArchivado.class);
		queryUsuario.setParameter("nickname", registro.getNicknameAsistente());
		List<UsuarioArchivado> listaUsuarios = queryUsuario.getResultList();
		if (listaUsuarios.isEmpty()) {
			throw new IllegalStateException("Asistente no persistido: " + registro.getNicknameAsistente());
		}
		UsuarioArchivado usuario = listaUsuarios.get(0);
		AsistenteArchivado asistente = manejadorEntidades.find(AsistenteArchivado.class, usuario.getId());

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
	}

	/**
	 * Busca en el archivo una edición con nombre = `nombreEdicion`. Si existe la devuelve.<p>
	 * Devuelve null en caso contrario.
	 * 
	 * @param manejadorEntidades
	 * @param nombreEdicion
	 * @return la edición archivada o null si no existe
	 */
	private EdicionArchivada getEdicionEnArchivo(EntityManager manejadorEntidades, String nombreEdicion) {
		TypedQuery<EdicionArchivada> queryEdicion = manejadorEntidades.createQuery("SELECT e FROM EdicionArchivada e WHERE e.nombre = :nombre", EdicionArchivada.class);
		queryEdicion.setParameter("nombre", nombreEdicion);
		List<EdicionArchivada> listaEdiciones = queryEdicion.getResultList();
		
		if (!listaEdiciones.isEmpty()) return listaEdiciones.get(0);
		
		return null;
	}
	
	/**
	 * Wrapper público para `getEdiciónEnArchivo`. Extiende la funcionalidad del otro método para ámbitos fuera de esta clase.<p>
	 * Busca en el archivo una edición con nombre = `nombreEdicion`. Si existe la devuelve.<p>
	 * Devuelve null en caso contrario.
	 * 
	 * @param nombreEdicion
	 * @return la edición archivada o null si no existe
	 */
	public EdicionArchivada getEdicionEnArchivo(String nombreEdicion) {
		EntityManager manejadorEntidades = fabricaManejadorEntidades.createEntityManager();
		try {
			return getEdicionEnArchivo(manejadorEntidades, nombreEdicion);
		} finally {
			manejadorEntidades.close();
		}
	}
	
	/**
	 * Devuelve todas las ediciones archivadas que tiene un organizador.<p>
	 * Si el usuario no existe en la DB devuelve null.
	 * En caso de que no tenga ninguna devuelve null.
	 * 
	 * @param nicknameOrganizador
	 * @return
	 */
	private List<EdicionArchivada> getEdicionesArchivadasDeOrganizador(EntityManager manejadorEntidades, String nicknameOrganizador) {
		TypedQuery<UsuarioArchivado> queryUsuario = manejadorEntidades.createQuery(
				"SELECT u FROM UsuarioArchivado u WHERE u.nickname = :nickname", UsuarioArchivado.class);
		queryUsuario.setParameter("nickname", nicknameOrganizador);
		List<UsuarioArchivado> listaUsuarios = queryUsuario.getResultList();
		
		if (listaUsuarios.isEmpty()) return null;
		
		UsuarioArchivado usuario = listaUsuarios.get(0);
		OrganizadorArchivado organizador = manejadorEntidades.find(OrganizadorArchivado.class, usuario.getId());
		
		return organizador.getEdiciones();
	}
	
	/**
	 * Wrapper público para `getEdicionesArchivadasDeOrganizador`. Extiende la funcionalidad del otro método para ámbitos fuera de esta clase.<p>
	 * Devuelve todas las ediciones archivadas que tiene un organizador.<p>
	 * Si el usuario no existe en la DB devuelve null.
	 * En caso de que no tenga ninguna devuelve null.
	 * 
	 * @param nicknameOrganizador
	 * @return
	 */
	public List<EdicionArchivada> getEdicionesArchivadasDeOrganizador(String nicknameOrganizador) {
		EntityManager manejadorEntidades = fabricaManejadorEntidades.createEntityManager();
		try {
			return getEdicionesArchivadasDeOrganizador(manejadorEntidades, nicknameOrganizador);
		} finally {
			manejadorEntidades.close();
		}
	}
	
	/**
	 * PRE-CONDICIÓN: La edición pasada por parámetro existe en el sistema y no está archivada.<p>
	 * Archiva una edición y todos sus datos relacionados en el archivo. Esto
	 * incluye:
	 * <p>
	 * 1. Registros asociados
	 * <p>
	 * 2. Asistentes asociados a los registros asociados
	 * <p>
	 * 3. Organizadores asociados
	 * <p>
	 * 
	 * @param nombreEvento
	 * @param nombreEdicion
	 * @param fechaArchivo
	 * @throws EdicionYaArchivada si la edición seleccionada ya existe en el Archivo
	 */
	public void archivarEdicion(String nombreEvento, String nombreEdicion, Date fechaArchivo){
		EntityManager manejadorEntidades = this.fabricaManejadorEntidades.createEntityManager();
		EntityTransaction transaccion = manejadorEntidades.getTransaction();
		try {
			transaccion.begin();

			// obtengo los datos de la edición
			DTEdicionEvento datosEdicion = this.manejadorEventos.getDTEdicion(nombreEvento, nombreEdicion);
			if (datosEdicion == null) {
				transaccion.rollback();
				return; // no hago nada si la edición no existe en el sistema
			}

			// archivo el organizador asociado a la edición y obtenemos el objeto en la DB
			this.guardarOrganizadorEnArchivo(manejadorEntidades, datosEdicion.getOrganizadorNickname());

			TypedQuery<UsuarioArchivado> queryUsuario = manejadorEntidades.createQuery(
					"SELECT u FROM UsuarioArchivado u WHERE u.nickname = :nickname", UsuarioArchivado.class);
			queryUsuario.setParameter("nickname", datosEdicion.getOrganizadorNickname());
			List<UsuarioArchivado> listaUsuarios = queryUsuario.getResultList();
			if (listaUsuarios.isEmpty()) {
				throw new IllegalStateException("Organizador no persistido: " + datosEdicion.getOrganizadorNickname());
			}
			UsuarioArchivado usuario = listaUsuarios.get(0);
			OrganizadorArchivado organizador = manejadorEntidades.find(OrganizadorArchivado.class, usuario.getId());

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
			organizador.agregarEdicion(edicion);

			manejadorEntidades.persist(edicion);
			manejadorEntidades.flush();

			// archivar a todos los asistentes y registros asociados a la edición
			Set<DTDetalleRegistro> registros = this.manejadorEventos.listarDTRegistrosAEdicion(nombreEvento,
					nombreEdicion);
			for (DTDetalleRegistro registro : registros) {
				this.guardarAsistenteEnArchivo(manejadorEntidades, registro.getNicknameAsistente());
				this.guardarRegistroEnArchivo(manejadorEntidades, registro);
			}

			// linkear todos los registros con la edición (no es necesario persistir en la
			// DB)
			TypedQuery<RegistroArchivado> queryRegistros = manejadorEntidades
					.createQuery("SELECT r FROM RegistroArchivado r", RegistroArchivado.class);
			List<RegistroArchivado> registrosArchivados = queryRegistros.getResultList();
			edicion.setRegistros(registrosArchivados);

			transaccion.commit();
		} catch (Exception exception) {
			if (manejadorEntidades.getTransaction().isActive())
				manejadorEntidades.getTransaction().rollback();
		} finally {
			manejadorEntidades.close();
		}
	}
}
