package datatypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTEventoEdicion")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTEventoEdicion{
	String nombreEvento;
	String nombreEdicion;
	
	/**
	 * Constructor sin argumentos requerido por JAXB.
	 * NO USAR en código de aplicación. Usar DTEventoEdicion(String, String, ...) en su lugar.
	 */
	public DTEventoEdicion() {
	}
	
	public DTEventoEdicion(String nombreEvento, String nombreEdicion) {
		this.setNombreEvento(nombreEvento);
		this.setNombreEdicion(nombreEdicion);
	}
	
	// Setters
	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}
	public void setNombreEdicion(String nombreEdicion) {
		this.nombreEdicion = nombreEdicion;
	}
	
	// Getters
	public String nombreEvento() {
		return nombreEvento;
	}
	public String nombreEdicion() {
		return nombreEdicion;
	}
	// Getters
	public String getNombreEvento() {
		return nombreEvento;
	}
	public String getNombreEdicion() {
		return nombreEdicion;
	}
}
