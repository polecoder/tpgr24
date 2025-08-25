package logica;

public class DTEventos {
 private 
 	String nombre;
	String sigla;
	String descripcion;
public
	DTEventos(String nombre, String descripcion , String sigla) {
		this.nombre=nombre;
		this.descripcion=descripcion;
		this.sigla=sigla;
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
