package logica;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import logica.datatypes.DTPatrocinio;

public class Evento {
	private 
	String nombre;
	String sigla;
	String descripcion;
	Date fechaAlta;
	Map<String, Categoria> categorias;
	Map<String, Edicion> ediciones;
	public
	Evento(String nombre, String descripcion , String sigla, Date fechaAlta) {
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.sigla=sigla;
		this.fechaAlta=fechaAlta;
		this.categorias=new HashMap<>();
		this.ediciones=new HashMap<>();
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
	Date getFechaAlta(){
		return this.fechaAlta;
	}
	Map<String, Categoria> getCategoria(){
		return this.categorias;
	}
	Map<String, Edicion> getEdiciones(){
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
	void setFechaAlta(Date fechaAlta) {
		this.fechaAlta=fechaAlta;
	}
	void agregarCategoria(Categoria categoria) {
		this.categorias.put(categoria.getNombre(), categoria);
	}
	void eliminarCategoria(String nombreCategoria) {
		this.categorias.remove(nombreCategoria);
	}
	void agregarEdicion(Edicion edicion) {
		this.ediciones.put(edicion.getNombre(),edicion);
	}
	void eliminarEdicion(String nombreEdicion) {
		this.ediciones.remove(nombreEdicion);
	}
	
	void crearPatrocinio(DTPatrocinio datosPatrocinio) {
		//TODO
	}
}
