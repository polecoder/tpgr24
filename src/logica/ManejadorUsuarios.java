package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import logica.datatypes.DTAsistente;

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
	
	public void agregarAsistente(String nickname,String nombre, String correo, String apellido, Date fechaNacimiento,Institucion institucion) {
		Asistente A= new Asistente(nickname,nombre,correo,apellido,fechaNacimiento,institucion);
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
	
	public Organizador getOrganizador(String nickname) {
		return conjuntoOrganizadores.get(nickname);
	}
	
	public Asistente getAsistente(String nickname) {
		return conjuntoAsistentes.get(nickname);
	}
}

