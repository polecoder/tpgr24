package datatypes;

import java.util.Date;
import java.util.Set;

public class DTEvento {
	private 
	String nombre;
	String sigla;
	String descripcion;
	Date fechaAlta;
	byte[] imagen;
	Set<String> categorias;

	public
	DTEvento(String nombre, String descripcion , String sigla, Date fechaAlta, Set<String> categorias, byte[] imagen) {
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setSigla(sigla);
		this.setFechaAlta(fechaAlta);
		this.setImagen(imagen);
		this.setCategorias(categorias);
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
	public byte[] getImagen() {
		return this.imagen;
	}

	void setFechaAlta(Date fechaAlta){ 
		this.fechaAlta = fechaAlta;
	}
	void setNombre(String nombre){
		this.nombre = nombre;
	}
	void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	void setSigla(String sigla){
		this.sigla = sigla;
	}
	void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	private void setCategorias(Set<String> categorias) {
		this.categorias=categorias;
		
	}
	public Set<String> getCategorias(){
		return this.categorias;
	}

}
