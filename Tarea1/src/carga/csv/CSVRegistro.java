package carga.csv;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

public class CSVRegistro {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "Usuario")
	private String usuario;
	@CsvBindByName(column = "EdicionEvento")
	private String edicion;
	@CsvBindByName(column = "TipoRegistro")
	private String tipoRegistro;
	@CsvBindByName(column = "FechaRegistro")
	@CsvDate("dd/MM/yyyy")
	private Date fechaRegistro;
	@CsvBindByName(column = "Costo")
	private float costo;
	@CsvBindByName(column = "Patrocinio")
	private String patrocinio;
	@CsvBindByName(column = "Asiste")
	private String asiste;
	
	
	// NECESARIO para OpenCSV
	public CSVRegistro() {}

	public CSVRegistro(String ref, String usuario, String edicion, String tipoRegistro, Date fechaRegistro, float costo,
			String patrocinio, String asiste) {
		this.ref = ref;
		this.usuario = usuario;
		this.edicion = edicion;
		this.tipoRegistro = tipoRegistro;
		this.fechaRegistro = fechaRegistro;
		this.costo = costo;
		this.patrocinio = patrocinio;
		this.asiste = asiste;
	}

	public String getRef() {
		return ref;
	}
	public String getUsuario() {
		return usuario;
	}
	public String getEdicion() {
		return edicion;
	}
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public float getCosto() {
		return costo;
	}
	public String getPatrocinio() {
		return patrocinio;
	}
	public String getAsiste() {
		return asiste;
	}
}