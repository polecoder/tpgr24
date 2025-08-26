package tarea1.modulos.datatypes;

import java.time.LocalDate;

public class DTEdicion {
	private String nombre;
	private String sigla;
	private LocalDate fechaInicio;
	
	public DTEdicion(String nombre, String sigla, LocalDate fechaInicio) {
		this.setNombre(nombre);
		this.setSigla(sigla);
		this.setFechaInicio(fechaInicio);
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
}
