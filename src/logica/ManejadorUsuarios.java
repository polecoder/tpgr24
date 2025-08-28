package logica;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logica.datatypes.DTAsistente;

import java.time.LocalDate;

public class ManejadorUsuarios {
	private static ManejadorUsuarios instance = null;
	
	private Map<String,Asistente> conjuntoAsistentes;
	private Map<String,Organizador> conjuntoOrganizadores;
	
	// Constructor
	private ManejadorUsuarios() {
		conjuntoAsistentes = new HashMap<>();
		conjuntoOrganizadores = new HashMap<>();
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
	// ------- GETTERS ----------
	
	public boolean existeNick(String nickname) {
		return (conjuntoAsistentes.containsKey(nickname)||(conjuntoOrganizadores.containsKey(nickname)));
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
	
	public void agregarAsistente(String nickname,String nombre, String correo, String apellido, LocalDate fechaNacimiento,Institucion institucion) {
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
	
	
	public List<DTAsistente> listarAsistentes() {
		return null;
	}
	
	public Organizador getOrganizador(String nickname) {
		return null;
	}
	
	public Asistente getAsistente(String nickname) {
		return null;
	}
}
