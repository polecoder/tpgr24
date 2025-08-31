package logica.datatypes;

import java.util.Date;

public class DTEvento {
 private 
 	String nombre;
	String sigla;
	String descripcion;
	Date fechaAlta;
public
	DTEvento(String nombre, String descripcion , String sigla, Date date) {
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setSigla(sigla);
		this.setFechaAlta(date);
		
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
	
	public Date getFechaAlta() {
		return this.fechaAlta;
	}
	void setFechaAlta(Date fechaAlta){ this.fechaAlta = fechaAlta;}
	void setNombre(String nombre){
		  this.nombre =nombre;
	}
	void setDescripcion(String descripcion){
		 this.descripcion =descripcion ;
	}
	void setSigla(String sigla){
		this.sigla=sigla;
	}
}
