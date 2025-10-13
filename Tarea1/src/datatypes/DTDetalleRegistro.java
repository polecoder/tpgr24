package datatypes;

import java.util.Date;

public class DTDetalleRegistro {
	private String nombreEdicion;
	private String nicknameAsistente;
	private String nombreTipo;
	private Date fecha;
	private float costo;
	private String codigoPatrocinio;
	
	public DTDetalleRegistro(String nombreEdicion, String nicknameAsistente, String nombreTipo, Date fecha, float costo, String codigoPatrocinio) {
		this.setNombreEdicion(nombreEdicion);
		this.setNicknameAsistente(nicknameAsistente);
		this.setNombreTipo(nombreTipo);
		this.setFecha(fecha);
		this.setCosto(costo);
		this.setCodigoPatrocinio(codigoPatrocinio);
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
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public float getCosto() {
		return costo;
	}
	public void setCosto(float costo) {
		this.costo = costo;
	}
	
	public String getCodigoPatrocinio() {
		return codigoPatrocinio;
	}
	public void setCodigoPatrocinio(String codigoPatrocinio) {
		this.codigoPatrocinio = codigoPatrocinio;
	}
	
	public boolean equalsRegistro(DTDetalleRegistro evento) {
		boolean nombreEdicion = this.getNombreEdicion() == evento.getNombreEdicion();
		boolean nickAsistente = this.getNicknameAsistente() == evento.getNicknameAsistente();
		boolean nombreTipoRegistro = this.getNombreTipo() == evento.getNombreTipo();
		boolean fecha = this.getFecha() == evento.getFecha();
		boolean costo = this.getCosto() == evento.getCosto();
		return nombreEdicion && nickAsistente && nombreTipoRegistro && fecha && costo;
	}
}
