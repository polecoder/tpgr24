package logica;

import java.time.LocalDate;
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
	LocalDate fechaAlta;
	Map<String, Categoria> categorias;
	 private final Map<String, Edicion> ediciones;
	public
	Evento(String nombre, String descripcion , String sigla, LocalDate fechaAlta) {
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setSigla(sigla);
		this.setFechaAlta(fechaAlta);
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
	public LocalDate getfechaAlta(){
		return this.fechaAlta;
	}
	public Map<String, Categoria> getcategoria(){
		return this.categorias;
	}
	public Map<String, Edicion> getediciones(){
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
	public void setFechaAlta(LocalDate fechaAlta) {
		this.fechaAlta=fechaAlta;
	}
	public void agregarEdicion(Edicion edicion) {
		if (edicion == null) return;
		this.ediciones.put(edicion.getNombre(),edicion);
	};
	
	public void agregarCategoria(Categoria categoria) {
		this.categorias.put(categoria.getNombre(), categoria);
	};
	
	public void crearPatrocinio(DTPatrocinio datosPatrocinio) {
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
