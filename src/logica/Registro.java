package logica;

import java.util.Date;
import java.util.Set;

import logica.datatypes.DTDetalleRegistro;

public class Registro {
	private Date fechaRegistro;
	private float costo;
	private Asistente asistente;
	private Patrocinio patrocinio;
	
	/**
	 * Observar que patrocinio puede ser null en caso de que el registro se haga de forma normal, sin código de patrocinio
	 * 
	 * @param fechaRegistro
	 * @param costo
	 * @param asistente
	 * @param patrocinio
	 */
	public Registro(Date fechaRegistro, float costo, Asistente asistente, Patrocinio patrocinio) {
		this.setFechaRegistro(fechaRegistro);
		this.setCosto(costo);
		this.setPatrocinio(patrocinio);
		this.setAsistente(asistente);
	}
	
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public float getCosto() {
		return costo;
	}
	public void setCosto(float costo) {
		this.costo = costo;
	}

	public Asistente getAsistente() {
		return asistente;
	}
	public void setAsistente(Asistente asistente) {
		this.asistente = asistente;
	}

	public Patrocinio getPatrocinio() {
		return patrocinio;
	}
	public void setPatrocinio(Patrocinio patrocinio) {
		this.patrocinio = patrocinio;
	}
	
	// TODO: Implementar
	public Set<DTDetalleRegistro> mostrarDetalle() {
		return null;
	}
	
	// TODO: Implementar
	public boolean validar(String nicknameAsistente) {
		return false;
	}
}
