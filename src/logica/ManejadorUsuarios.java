package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTDetalleAsistente;
import logica.datatypes.DTDetalleOrganizador;
import logica.datatypes.DTDetalleUsuario;

public class ManejadorUsuarios {
	private static ManejadorUsuarios instance = null;
	
	private Map<String,Asistente> conjuntoAsistentes;
	private Map<String,Organizador> conjuntoOrganizadores;
	
	// Constructor
	private ManejadorUsuarios() {
		conjuntoAsistentes = new HashMap<>();
		conjuntoOrganizadores = new HashMap<>();
	}
	
	public static void resetInstance() {
		instance = null;
	}
	
	public static ManejadorUsuarios getInstance() {
		if (instance == null) {
			instance = new ManejadorUsuarios();
		}
		return instance;
	}
	
	// ------- GETTERS ----------
	public Map<String,Asistente> getConjuntoAsistentes() {
		return conjuntoAsistentes;
	}
	
	public Map<String,Organizador> getConjuntoOrganizador() {
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
	
	public void agregarAsistente(String nickname, String contrasena, String nombre, String correo, String apellido, Date fechaNacimiento,Institucion institucion) {
		Asistente A= new Asistente(nickname,contrasena,nombre,correo,apellido,fechaNacimiento,institucion);
		if (institucion!=null) {
			institucion.asistentes.put(nickname, A);
		}
		conjuntoAsistentes.put(A.getNickname(), A);
	}
	
	public void agregarOrganizador(String nickname, String nombre, String correo, String descripcion, String link) {
		Organizador O=new Organizador(nickname,nombre,correo,descripcion,link);
		conjuntoOrganizadores.put(O.getNickname(), O);
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
	 * Devuelve el detalle del usuario, en caso de que no exista devuelve una excepción.
	 * 
	 * @param nicknameUsuario
	 * @return
	 * @throws Exception 
	 */
	public DTDetalleUsuario mostrarDetalleUsuario(String nicknameUsuario) throws Exception {
		if (this.getOrganizador(nicknameUsuario) != null) {
			Organizador organizador = this.getOrganizador(nicknameUsuario);
			return new DTDetalleOrganizador(organizador.getNickname(), organizador.getNombre(), organizador.getCorreo(), organizador.getDescripcion(), organizador.getLink());
		} else if (this.getAsistente(nicknameUsuario) != null) {
			Asistente asistente = this.getAsistente(nicknameUsuario);
			return new DTDetalleAsistente(asistente.getNickname(), asistente.getNombre(), asistente.getCorreo(), asistente.getApellido(), asistente.getNacimiento());
		} else {
			throw new Exception("El usuario indicado no existe en el sistema.");
		}
	}
	
	/**
	 * Si no existe el organizador no hace nada.
	 * 
	 * @param e
	 * @param nombreOrganizador
	 */
	public void agregarEdicionAOrganizador(Edicion e, String nombreOrganizador) {
		Organizador o = this.getOrganizador(nombreOrganizador);
		if (o != null) {
			o.agregarEdicion(e);
		}
	}
}

