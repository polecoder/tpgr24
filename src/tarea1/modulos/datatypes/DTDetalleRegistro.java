package tarea1.modulos.datatypes;

import java.time.LocalDate;

public class DTDetalleRegistro {
	private String nombreEdicion;
	private String nicknameAsistente;
	private String nombreTipo;
	private LocalDate fecha;
	private float costo;
	
	public DTDetalleRegistro(String nombreEdicion, String nicknameAsistente, String nombreTipo, LocalDate fecha, float costo) {
		this.setNombreEdicion(nombreEdicion);
		this.setNicknameAsistente(nicknameAsistente);
		this.setNombreTipo(nombreTipo);
		this.setFecha(fecha);
		this.setCosto(costo);
	}
	
	public String getNombreEdicion() {
		return nombreEdicion;
	}
	public void setNombreEdicion(String nombreEdicion) {
		this.nombreEdicion = nombreEdicion;
	}
	
	public String getNicknameAsistente() {
		return nicknameAsistente;
	}
	public void setNicknameAsistente(String nicknameAsistente) {
		this.nicknameAsistente = nicknameAsistente;
	}
	
	public String getNombreTipo() {
		return nombreTipo;
	}
	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public float getCosto() {
		return costo;
	}
	public void setCosto(float costo) {
		this.costo = costo;
	}
}
