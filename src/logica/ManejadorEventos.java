package logica;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

public class ManejadorEventos {
	private 
	static ManejadorEventos instancia;
	Map<String,Evento> eventos;
	ManejadorEventos(){
		
		 this.eventos = new HashMap<>();
		 Evento eventoejemplo=new Evento("hola", "hola2", "hola3", LocalDate.of(2025, 8, 28));
		 Categoria categoria= new Categoria("Holacat");
		 Categoria categoria2= new Categoria("Holacat2");
		 eventoejemplo.categorias.put("Holacat", categoria);
		 eventoejemplo.categorias.put("Holacat2", categoria2);
		 eventos.put("hola", eventoejemplo);
		 
		 Edicion edicion=new Edicion("chau", "chauu",null, null, null, null);
		 eventoejemplo.ediciones.put("chauu", edicion);
		 
		 Evento eventoejemploo=new Evento("holaa", "holaa2", "holaa3", LocalDate.of(2025, 8, 28));
		 Categoria categoriaa= new Categoria("Holacata");
		 Categoria categoriaa2= new Categoria("Holacata2");
		 eventoejemploo.categorias.put("Holacata", categoriaa);
		 eventoejemploo.categorias.put("Holacata2", categoriaa2);
		 eventos.put("holaa", eventoejemploo);
		 
		 
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
	
	Edicion getEdicion(String evento, String edicion){
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
		Edicion edicion=eventos.get(eventoSeleccionado).getediciones().get(eventoSeleccionado);
		DTEdicionEvento dtEvento= new DTEdicionEvento(edicion.getNombre(),edicion.getSigla(), edicion.getFechaInicio());
		return dtEvento;
	}
}
