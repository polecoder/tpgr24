package logica;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

public class ManejadorEventos {
	private 
	static ManejadorEventos instancia;
	Map<String,Evento> eventos;
	ManejadorEventos(){
		 this.eventos = new HashMap<>();
	};
	public
	static ManejadorEventos getInstancia() {
		if (instancia==null) {
			instancia= new ManejadorEventos();
		}
		return instancia;
	};
	
	Evento getEventoEdicion(String nombreEdicion) {
		//TODO
		return null;
	};
	
	boolean contieneEdicion(String NombreEdicion) {
		//TODO
		return true;
	};
	
	Set<DTEvento> listarEventos(){
		//TODO
		return null;
	};
	
	Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion){
		//TODO
		return null;
	};
	
	boolean Validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro){
		//TODO
		return true;
	};

	void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion) {
		//TODO
	};
		
	Registro getRegistro(String nombreEdicion,String nickAsistente){
		//TODO
		return null;
	};
	
	Edicion getEdicion(String nombreEdicion){
		//TODO
		return null;
	}
	
	/**
	 * Retorna el evento e en el sistema tal que e.nombre = nombreEvento.
	 * Si el evento no existe, retorna null
	 * 
	 * @param nombreEvento
	 * @return
	 */
	Evento getEvento(String nombreEvento) {
		if (this.eventos.containsKey(nombreEvento)) {
			return this.eventos.get(nombreEvento);
		}
		return null;
	}
}
