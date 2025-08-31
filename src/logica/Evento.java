package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import excepciones.TipoRegistroNoExiste;
import logica.datatypes.DTPatrocinio;
import logica.datatypes.DTTipoRegistro;

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
	public String getNombre(){
		return this.nombre;
	}
	public String getSigla(){
		return this.sigla;
	}
	public String getDescripcion(){
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
	
	public void setNombre(String nombre){
		  this.nombre =nombre;
	}
	public void setDescripcion(String descripcion){
		 this.descripcion =descripcion;
	}
	public void setSigla(String sigla){
		this.sigla=sigla;
	}
	void setFechaAlta(Date fechaAlta) {
		this.fechaAlta=fechaAlta;
	}
	public void agregarCategoria(Categoria categoria) {
		this.categorias.put(categoria.getNombre(), categoria);
	}
	public void eliminarCategoria(String nombreCategoria) {
		this.categorias.remove(nombreCategoria);
	}
	public void agregarEdicion(Edicion edicion) {
		this.ediciones.put(edicion.getNombre(),edicion);
	}
	public void eliminarEdicion(String nombreEdicion) {
		this.ediciones.remove(nombreEdicion);
	}
	
	void crearPatrocinio(DTPatrocinio datosPatrocinio) {
		//TODO
	}
	
	public Edicion getEdicion(String nombreEdicion) {
		return ediciones.get(nombreEdicion);
	}
	
	public DTTipoRegistro getDTTipoRegistro(String nombreEdiocion, String nombreTipoRegistro) throws TipoRegistroNoExiste {
		Edicion edicion = ediciones.get(nombreEdiocion);
		try {
			edicion.getDTTipoRegistro(nombreTipoRegistro);
		} catch (TipoRegistroNoExiste ex) {
			throw new TipoRegistroNoExiste("El Tipo de Registro no existe");
		};
		return edicion.getDTTipoRegistro(nombreTipoRegistro);
	}
}
