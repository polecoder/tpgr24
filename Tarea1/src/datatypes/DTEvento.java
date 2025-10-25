package datatypes;

import java.util.Date;
import java.util.Set;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTEvento")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTEvento {
	private String nombre;
	private String sigla;
	private String descripcion;
	private Date fechaAlta;
	private byte[] imagen;
	private Set<String> categorias;

	/**
	 * Constructor sin argumentos requerido por JAXB.
	 * NO USAR en código de aplicación. Usar DTEvento(String, String, ...) en su lugar.
	 */
	public DTEvento() {
	}
	
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
