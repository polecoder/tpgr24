package logica;

import java.time.LocalDate;
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
		 
		 /*//creo evento
		 Evento ev = new Evento("ConfYec", "", "", LocalDate.now());
		 this.eventos.put("ConfTec", ev);
		 
		 //creo edicion
		 Edicion ed = new Edicion("ConfTec 2025", "", LocalDate.now(), LocalDate.now(), "", "");
		  ev.getediciones().put(ed.getNombre(), ed);
		 
		  //tiposRegistro
		 TipoRegistro trGen = new TipoRegistro("General", "Acceso general", 1000, 500, 100, ed);
		 TipoRegistro trVip = new TipoRegistro("VIP",     "Acceso VIP",     2500,  100, 20, ed);
		 ed.getTiposRegistro().put(trGen.getNombre(), trGen);
		 ed.getTiposRegistro().put(trVip.getNombre(), trVip);*/
	};
	public static ManejadorEventos getInstancia() {
		if (instancia==null) {
			instancia= new ManejadorEventos();
		}
		return instancia;
	};
	
	Evento getEventoEdicion(String nombreEdicion) {
		return eventos.get(nombreEdicion);
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
}
