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
	private EEstadoEvento estado;

	/**
	 * Constructor sin argumentos requerido por JAXB.
	 * NO USAR en código de aplicación. Usar DTEvento(String, String, ...) en su lugar.
	 */
	public DTEvento() {
	}
	
	public
	DTEvento(String nombre, String descripcion , String sigla, Date fechaAlta, Set<String> categorias, byte[] imagen, EEstadoEvento estado) {
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setSigla(sigla);
		this.setFechaAlta(fechaAlta);
		this.setImagen(imagen);
		this.setCategorias(categorias);
		this.setEstado(estado);
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
	
	public EEstadoEvento getEstado() {
		return estado;
	}

	public void setFechaAlta(Date fechaAlta){ 
		this.fechaAlta = fechaAlta;
	}
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	public void setSigla(String sigla){
		this.sigla = sigla;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	void setEstado(EEstadoEvento estado) {
		this.estado=estado;
	}
	
	public void setCategorias(Set<String> categorias) {
		this.categorias=categorias;
		
	}
	public Set<String> getCategorias(){
		return this.categorias;
	}
}
