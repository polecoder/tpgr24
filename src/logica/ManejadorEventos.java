package logica;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

public class ManejadorEventos {
	
	private static ManejadorEventos instancia;
	private Map<String,Evento> eventos;
	private ManejadorEventos(){
		 this.eventos = new HashMap<>();
	};
	
	public static ManejadorEventos getInstancia() {
		if (instancia==null) {
			instancia= new ManejadorEventos();
		}
		return instancia;
	};
	
	public Evento getEventoEdicion(String nombreEdicion) {
		//TODO
		return null;
	};
	
	public boolean contieneEdicion(String NombreEdicion) {
		//TODO
		return true;
	};
	
	public Set<DTEvento> listarEventos(){
		//TODO
		return null;
	};
	
	public Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion){
		//TODO
		return null;
	};
	
	public boolean Validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro){
		//TODO
		return true;
	};

	public void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion) {
		//TODO
	};
	
	
	public Registro getRegistro(String nombreEdicion,String nickAsistente){
		//TODO
		return null;
	};
	
	public Edicion getEdicion(String evento, String edicion){
		return eventos.get(edicion).getediciones().get(edicion);
	}
	public Set<String> getNombresCategorias(String Nombre) {
		return eventos.get(Nombre).getcategoria().keySet();
	}
	public Set<String> getNombresEdiciones(String nombre){
		return eventos.get(nombre).getediciones().keySet();
	};
	public DTEvento getDTEvento(String nombre) {
		Evento evento=eventos.get(nombre);
		DTEvento dtEvento= new DTEvento(evento.getNombre(), evento.getDescripcion() , evento.getSigla(),evento.getfechaAlta());
		return dtEvento;
	}
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada) {
		Edicion edicion=eventos.get(eventoSeleccionado).getediciones().get(edicionSeleccionada);
		DTEdicionEvento dtEvento= new DTEdicionEvento(edicion.getNombre(),edicion.getSigla(), edicion.getFechaInicio());
		return dtEvento;
	}
	
	public Set<String> getNombresEventos(){
		return eventos.keySet();
	};
	
	public void agregarEvento(Evento evento) {
		eventos.put(evento.getNombre(), evento);
	}
}
