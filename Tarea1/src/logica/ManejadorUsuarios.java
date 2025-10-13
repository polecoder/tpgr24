package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import datatypes.DTAsistente;
import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleUsuario;
import excepciones.UsuarioNoExiste;

public class ManejadorUsuarios {
	private static ManejadorUsuarios instance = null;
	
	private Map<String, Asistente> conjuntoAsistentes;
	private Map<String, Organizador> conjuntoOrganizadores;

	// Constructor
	private ManejadorUsuarios() {
		conjuntoAsistentes = new HashMap<>();
		conjuntoOrganizadores = new HashMap<>();
	}

	public static void resetInstance() {
		instance = null;
	}

	public void actualizarImagenUsuario(String nickname, byte[] imagen) {
		if (nickname == null)
			return;
		Asistente asistente = getAsistente(nickname);
		if (asistente != null) {
			asistente.setImagen(imagen);
			return;
		}
		Organizador organizador = getOrganizador(nickname);
		if (organizador != null) {
			organizador.setImagen(imagen);
		}
	}

	public static ManejadorUsuarios getInstance() {
		if (instance == null) {
			instance = new ManejadorUsuarios();
		}
		return instance;
	}

	// ------- GETTERS ----------
	public Map<String, Asistente> getConjuntoAsistentes() {
		return conjuntoAsistentes;
	}

	public Map<String, Organizador> getConjuntoOrganizador() {
		return conjuntoOrganizadores;
	}

	public boolean existeNick(String nickname) {
		return conjuntoAsistentes.containsKey(nickname) || conjuntoOrganizadores.containsKey(nickname);
	}

	public boolean existeCorreo(String correo) {
		for (Asistente a : conjuntoAsistentes.values()) {
			if (a.getCorreo().equals(correo)) {
				return true;
			}
		}
		for (Organizador o : conjuntoOrganizadores.values()) {
			if (o.getCorreo().equals(correo)) {
				return true;
			}
		}
		return false;
	}

	public void agregarAsistente(String nickname, String contrasena, String nombre, String correo, String apellido,
			Date fechaNacimiento, Institucion institucion, byte[] imagen) {
		Asistente asistente = new Asistente(nickname, contrasena, nombre, correo, apellido, fechaNacimiento,
				institucion);
		if (institucion != null) {
			institucion.agregarAsistente(asistente);
		}
		conjuntoAsistentes.put(asistente.getNickname(), asistente);
		if (imagen != null && imagen.length > 0)
			asistente.setImagen(imagen);
		conjuntoAsistentes.put(nickname, asistente);
	}

	public void agregarOrganizador(String nickname, String contrasena, String nombre, String correo, String descripcion,
			String link, byte[] imagen) {
		Organizador organizador = new Organizador(nickname, contrasena, nombre, correo, descripcion, link);
		conjuntoOrganizadores.put(organizador.getNickname(), organizador);
		if (imagen != null && imagen.length > 0)
			organizador.setImagen(imagen);
		conjuntoOrganizadores.put(nickname, organizador);
	}

	public Map<String, DTAsistente> listarAsistentes() {
		Map<String, DTAsistente> CtoA = new HashMap<>();
		for (Asistente a : conjuntoAsistentes.values()) {
			CtoA.put(a.getNickname(), new DTAsistente(a.getNickname(), a.getNombre(), a.getCorreo()));
		}
		return CtoA;
	}

	/**
	 * Retorna null en caso de que no exista el organizador
	 * 
	 * @param nickname
	 * @return
	 */
	public Organizador getOrganizador(String nickname) {
		if (this.conjuntoOrganizadores.containsKey(nickname)) {
			return conjuntoOrganizadores.get(nickname);
		}
		return null;
	}

	/**
	 * Retorna null en caso de que no exista el asistente
	 * 
	 * @param nickname
	 * @return
	 */
	public Asistente getAsistente(String nickname) {
		if (this.conjuntoAsistentes.containsKey(nickname)) {
			return conjuntoAsistentes.get(nickname);
		}
		return null;
	}

	/**
	 * Retorna null en caso de que no exista ningún asistente con el correo pasado
	 * por parámetro.
	 * 
	 * @param correo
	 * @return
	 */
	public Asistente getAsistentePorCorreo(String correo) {
		for (Asistente a : conjuntoAsistentes.values()) {
			if (a.getCorreo().equals(correo)) {
				return a;
			}
		}
		return null;
	}

	/**
	 * Retorna null en caso de que no exista ningún asistente con el correo pasado
	 * por parámetro.
	 * 
	 * @param correo
	 * @return
	 */
	public Organizador getOrganizadorPorCorreo(String correo) {
		for (Organizador o : conjuntoOrganizadores.values()) {
			if (o.getCorreo().equals(correo)) {
				return o;
			}
		}
		return null;
	}

	/**
	 * Devuelve el detalle del usuario, en caso de que no exista devuelve una
	 * excepción.
	 * 
	 * @param nicknameUsuario
	 * @return
	 * @throws Exception
	 */
	public DTDetalleUsuario mostrarDetalleUsuario(String nicknameUsuario) throws UsuarioNoExiste {
		if (this.getOrganizador(nicknameUsuario) != null) {
			Organizador organizador = this.getOrganizador(nicknameUsuario);
			return new DTDetalleOrganizador(organizador.getNickname(), organizador.getNombre(), organizador.getCorreo(),
					organizador.getDescripcion(), organizador.getLink(), organizador.getImagen());
		} else if (this.getAsistente(nicknameUsuario) != null) {
			Asistente asistente = this.getAsistente(nicknameUsuario);
			String insti = null;
			if (asistente.getInstitucion() != null) {
				insti = asistente.getInstitucion().getNombre();
			}
			return new DTDetalleAsistente(asistente.getNickname(), asistente.getNombre(), asistente.getCorreo(),
					asistente.getApellido(), asistente.getNacimiento(), insti, asistente.getImagen());
		} else {
			throw new UsuarioNoExiste("El usuario indicado no existe en el sistema.");
		}
	}

	/**
	 * Si no existe el organizador no hace nada.
	 * 
	 * @param edicion
	 * @param nombreOrganizador
	 */
	public void agregarEdicionAOrganizador(Edicion edicion, String nombreOrganizador) {
		Organizador organizador = this.getOrganizador(nombreOrganizador);
		if (organizador != null) {
			organizador.agregarEdicion(edicion);
		}
	}
}
