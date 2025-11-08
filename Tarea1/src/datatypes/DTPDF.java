package datatypes;

import java.util.Date;

public class DTPDF {
	private String nombreCompleto;
	private String nombreEvento;
	private String nombreEdicion;
	private String ciudad;
	private Date fechaInicio;
	private Date fechaFin;
	
	public DTPDF() {
	}
	
	public DTPDF(String nombreCompleto, String nombreEvento, String nombreEdicion, String ciudad, Date fechaInicio, Date fechaFin) {
		this.setNombreCompleto(nombreCompleto);
		this.setNombreEvento(nombreEvento);
		this.setNombreEdicion(nombreEdicion);
		this.setCiudad(ciudad);
		this.setFecha(fechaInicio);
		this.setFechaFin(fechaFin);
	}
	
	public String getNombreCompleto() {
		return this.nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	public String getNombreEvento() {
		return this.nombreEvento;
	}
	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}
	
	public String getNombreEdicion() {
		return this.nombreEdicion;
	}
	public void setNombreEdicion(String nombreEdicion) {
		this.nombreEdicion = nombreEdicion;
	}
	
	public String getCiudad() {
		return this.ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	public Date getFechaInicio() {
		return this.fechaInicio;
	}
	public void setFecha(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public Date getFechaFin() {
		return this.fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
}
