package datatypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTTipoRegistro")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTTipoRegistro {
	String nombre;
	String descripcion;
	float costo;
	int cuposMax;
	int cuposDisponibles;

	/**
	 * Constructor sin argumentos requerido por JAXB.
	 * NO USAR en código de aplicación. Usar DTTipoRegistro(String, String, ...) en su lugar.
	 */
	public DTTipoRegistro() {
	}
	
	public DTTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, int cuposDisponibles) {
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setCosto(costo);
		this.setCuposMax(cuposMax);
		this.setCuposDisponibles(cuposDisponibles);
	};

	// Setters
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setCosto(float costo) {
		this.costo = costo;
	}
	public void setCuposMax(int cuposMax) {
		this.cuposMax= cuposMax;
	}
	public void setCuposDisponibles(int cuposDisponibles) {
		this.cuposDisponibles = cuposDisponibles;
	}
	// Getters
	public String nombre() {
		return nombre;
	}
	public String descripcion() {
		return descripcion;
	}
	public float costo() {
		return costo;
	}
	public int cuposMax() {
		return cuposMax;
	}
	public int cuposDisponibles() {
		return cuposDisponibles;
	}
}
