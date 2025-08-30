package logica.datatypes;

import java.time.LocalDate;

public class DTEdicionEvento {
	private String nombre;
	private String sigla;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private String ciudad;
	private String pais;
	private LocalDate fechaAlta;
	
	public DTEdicionEvento(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, String ciudad, String pais) {
	    this.setNombre(nombre);
	    this.setSigla(sigla);
	    this.setFechaInicio(fechaInicio);
	    this.setFechaFin(fechaFin);
	    this.setFechaAlta(fechaAlta);
	    this.setCiudad(ciudad);
	    this.setPais(pais);
	}
	
	

	public String getNombre() {
		return nombre;	
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public LocalDate getFechaFin() {
	    return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
	    this.fechaFin = fechaFin;
	}

	public LocalDate getFechaAlta() {
	    return fechaAlta;
	}

	public void setFechaAlta(LocalDate fechaAlta) {
	    this.fechaAlta = fechaAlta;
	}

	public String getCiudad() {
	    return ciudad;
	}

	public void setCiudad(String ciudad) {
	    this.ciudad = ciudad;
	}

	public String getPais() {
	    return pais;
	}

	public void setPais(String pais) {
	    this.pais = pais;
	}
}
