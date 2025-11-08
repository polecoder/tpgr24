package logica;

import java.util.Date;

import datatypes.DTDetalleRegistro;

public class Registro {
	private Date fechaRegistro;
	private float costo;
	private Asistente asistente;
	private Patrocinio patrocinio;
	private Edicion edicion;
	private String nombreTipoRegistro;
	private boolean asistio;
	
	/**
	 * Observar que patrocinio puede ser null en caso de que el registro se haga de forma normal, sin código de patrocinio
	 * 
	 * @param fechaRegistro
	 * @param costo
	 * @param asistente
	 * @param patrocinio
	 * @param edicion
	 */
	public Registro(Date fechaRegistro, float costo, Asistente asistente, Patrocinio patrocinio, Edicion edicion,String nombreTipoRegistro) {
		this.setFechaRegistro(fechaRegistro);
		this.setCosto(costo);
		this.setPatrocinio(patrocinio);
		this.setAsistente(asistente);
		this.setEdicion(edicion);
		this.setTipoRegistro(nombreTipoRegistro);
		this.setAsistio(false);
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
	
	public void setAsistio(boolean asistio) {
		this.asistio = asistio;
	}
	
	public boolean getAsistio() {
		return this.asistio;
	}
	
	public Edicion getEdicion() {
		return edicion;
	}
	
	public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}
	
	private void setTipoRegistro(String nombreTipoRegistro) {
		this.nombreTipoRegistro= nombreTipoRegistro;
		
	}
	
	private String getTipoRegistro() {
		return this.nombreTipoRegistro;
		
	}
	

	public DTDetalleRegistro mostrarDetalle() {
		DTDetalleRegistro dtRegistro = null;
		if (this.getPatrocinio() != null) {
			dtRegistro = new DTDetalleRegistro(this.getEdicion().getNombre(), this.getAsistente().getNickname(),this.getTipoRegistro(), this.getFechaRegistro(), this.getCosto(), this.getPatrocinio().getCodigoPatrocinio(), this.getAsistio());
		} else {
			dtRegistro = new DTDetalleRegistro(this.getEdicion().getNombre(),  this.getAsistente().getNickname(), this.getTipoRegistro(),  this.getFechaRegistro(), this.getCosto(), null, this.getAsistio());
		}
		return dtRegistro;
	}
	
	public boolean validar(String nicknameAsistente) {
		return nicknameAsistente == asistente.getNickname();
	}

}
