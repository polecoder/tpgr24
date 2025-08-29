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
	Map<String, Edicion> ediciones;
	public
	Evento(String nombre, String descripcion , String sigla, LocalDate fechaAlta) {
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
	LocalDate getfechaAlta(){
		return this.fechaAlta;
	}
	Map<String, Categoria> getcategoria(){
		return this.categorias;
	}
	Map<String, Edicion> getediciones(){
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
	void agregarEdicion(Edicion edicion) {
		this.ediciones.put(edicion.getNombre(),edicion);
	};
		
	void crearPatrocinio(DTPatrocinio datosPatrocinio) {
		//TODO
	}
	
	Edicion getEdicion(String nombreEdicion) {
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
