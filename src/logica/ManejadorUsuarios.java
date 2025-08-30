package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import logica.datatypes.DTAsistente;

public class ManejadorUsuarios {
	private static ManejadorUsuarios instance = null;
	
	private Map<String, Asistente> conjuntoAsistentes;
	private Map<String, Organizador> conjuntoOrganizadores;
	
	// Constructor
	private ManejadorUsuarios() {
		this.conjuntoAsistentes = new HashMap<>();
		this.conjuntoOrganizadores = new HashMap<>();	 
		
		/*Asistente ana = new Asistente("ana", "Ana", "Pérez", "ana@mail.com", LocalDate.now());
		this.conjuntoAsistentes.put(ana.getNickname(), ana);
		//this.conjuntoUsuarios.put(ana.getNickname(), ana);
		
		Asistente juan = new Asistente("juan", "j", "Pérez", "juanj", LocalDate.now());
		this.conjuntoAsistentes.put(juan.getNickname(), juan);
		
		Asistente pedro = new Asistente("pedro", "p", "Pérez", "juanj", LocalDate.now());
		this.conjuntoAsistentes.put(pedro.getNickname(), pedro);
		
		Patrocinio pat = new Patrocinio("PatrocinioDemo");
		
		ManejadorEventos me = ManejadorEventos.getInstancia();
		Evento ev = me.getEventoEdicion("ConfTec");
		
		Edicion ed = (ev != null) ? ev.getediciones().get("ConfTec 2025") : null; // o ev.buscarEdicion(...)
		TipoRegistro trGen = (ed != null) ? ed.getTiposRegistro().get("General") : null;
	    TipoRegistro trVip = (ed != null) ? ed.getTiposRegistro().get("VIP") : null;
	    
	    if (trGen != null) {
	        Registro r1 = new Registro(java.time.LocalDate.now(),       1000f, ana, trVip, pat);
	        ana.getRegistros().add(r1);
	    }
	    if (trVip != null) {
	        Registro r3 = new Registro(java.time.LocalDate.now(),       500, ana, trGen, pat);
	        ana.getRegistros().add(r3);
	    }
	    if (trVip != null) {
	        Registro r2 = new Registro(java.time.LocalDate.now().minusDays(1), 2500f, juan, trGen, pat);
	        juan.getRegistros().add(r2);
	        //trVip.getRegistros().add(r2);
	    }*/
	        
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
