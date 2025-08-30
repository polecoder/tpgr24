package logica.datatypes;

import java.time.LocalDate;

public class DTEvento {
 private 
 	String nombre;
	String sigla;
	String descripcion;
	LocalDate fechaAlta;
public
	DTEvento(String nombre, String descripcion , String sigla, LocalDate fechaAlta) {
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.sigla=sigla;
		this.fechaAlta=fechaAlta;
		
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
	
	public LocalDate getfechaAlta() {
		return this.fechaAlta;
	}
	
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
