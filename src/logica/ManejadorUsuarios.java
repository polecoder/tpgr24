package logica;

import java.util.ArrayList;
import java.util.List;

import logica.datatypes.DTAsistente;

import java.time.LocalDate;

public class ManejadorUsuarios {
	private static ManejadorUsuarios instance = null;
	
	private List<Asistente> conjuntoAsistentes;
	private List<Organizador> conjuntoOrganizadores;
	
	// Constructor
	private ManejadorUsuarios() {
		conjuntoAsistentes = new ArrayList<>();
		conjuntoOrganizadores = new ArrayList<>();
	}
	
	public static ManejadorUsuarios getInstance() {
		if (instance == null) {
			instance = new ManejadorUsuarios();
		}
		return instance;
	}
	
	// ------- GETTERS ----------
	public List<Asistente> getConjuntoAsistentes() {
		return conjuntoAsistentes;
	}
	
	public List<Organizador> getConjuntoOrganizador() {
		return conjuntoOrganizadores;
	}
	// ------- GETTERS ----------
	
	public boolean existeNick(String nickname) {
		return false;
	}
	
	public boolean existeCorreo(String correo) {
		return false;
	}
	
	public void agregarAsistente(Organizador O) {
		
	}
	
	public Organizador nuevaInstanciaO(String nickname, String correo, String descripcion, String link) {
		return null;
	}
	
	public Asistente nuevaInstanciaA(String nickname, String correo, String apellido, LocalDate fechaNacimiento) {
		return null;
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
