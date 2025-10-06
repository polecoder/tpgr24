package datatypes;

import java.util.Date;

public class DTDetalleRegistro {
	private String nombreEdicion;
	private String nicknameAsistente;
	private String nombreTipo;
	private Date fecha;
	private float costo;
	
	public DTDetalleRegistro(String nombreEdicion, String nicknameAsistente, String nombreTipo, Date fecha, float costo) {
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
	
	public boolean equals(DTDetalleRegistro o) {
		boolean nombreEdicion = this.getNombreEdicion() == o.getNombreEdicion();
		boolean nickAsistente = this.getNicknameAsistente() == o.getNicknameAsistente();
		boolean nombreTipoRegistro = this.getNombreTipo() == o.getNombreTipo();
		boolean fecha = this.getFecha() == o.getFecha();
		boolean costo = this.getCosto() == o.getCosto();
		return nombreEdicion && nickAsistente && nombreTipoRegistro && fecha && costo;
	}
}
