package logica;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import logica.datatypes.DTPatrocinio;

public class Evento {
	private 
	String nombre;
	String sigla;
	String descripcion;
	LocalDate fechaAlta;
	Map<String, Categoria> categorias;
	 private final Map<String, Edicion> ediciones = new HashMap<>();
	public
	Evento(String nombre, String descripcion , String sigla, LocalDate fechaAlta) {
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.sigla=sigla;
		this.fechaAlta=fechaAlta;
		//this.categorias=new HashMap<>();
		//this.ediciones=new HashMap<>();
	}
	String getNombre(){
		return this.nombre;
	}
	String getSigla(){
		return this.sigla;
	}
	String getDescripcion(){
		return this.descripcion;
	}
	LocalDate getfechaAlta(){
		return this.fechaAlta;
	}
	Map<String, Categoria> getcategoria(){
		return this.categorias;
	}
	public Map<String, Edicion> getediciones(){
		return this.ediciones;
	};
	
	void setNombre(String nombre){
		  this.nombre =nombre;
	}
	void setDescripcion(String descripcion){
		 this.descripcion =descripcion;
	}
	void setSigla(String sigla){
		this.sigla=sigla;
	}
	void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta=fechaAlta;
	}
	public void agregarEdicion(Edicion edicion) {
		if (edicion == null) return;
		this.ediciones.put(edicion.getNombre(),edicion);
	};
		
	void crearPatrocinio(DTPatrocinio datosPatrocinio) {
		//TODO
	}
}
