package carga.csv;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

public class CSVPatrocinio {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "EdicionEvento")
	private String edicion;
	@CsvBindByName(column = "Institucion")
	private String institucion;
	@CsvBindByName(column = "Nivel")
	private String nivel;
	@CsvBindByName(column = "TipoRegistro")
	private String tipoRegistro;
	@CsvBindByName(column = "Aporte")
	private float aporte;
	@CsvBindByName(column = "fechaPatrocinio")
	@CsvDate("yyyy-MM-dd")
	private Date fechaPatrocinio;
	@CsvBindByName(column = "cantidadRegistros")
	private int cuposGratuitos;
	@CsvBindByName(column = "codigoPatrocinio")
	private String codigoPatrocinio;
	
	// NECESARIO para OpenCSV
	public CSVPatrocinio() {}
	
	public CSVPatrocinio(String ref, String edicion, String institucion, String nivel, String tipoRegistro, float aporte, Date fechaPatrocinio, int cuposGratuitos, String codigoPatrocinio) {
		this.ref = ref;
		this.edicion = edicion;
		this.institucion = institucion;
		this.nivel = nivel;
		this.tipoRegistro = tipoRegistro;
		this.aporte = aporte;
		this.fechaPatrocinio = fechaPatrocinio;
		this.cuposGratuitos = cuposGratuitos;
		this.codigoPatrocinio = codigoPatrocinio;
	}
	
	public String getRef() {
		return ref;
	}
	public String getEdicion() {
		return edicion;
	}
	public String getInstitucion() {
		return institucion;
	}
	public String getNivel() {
		return nivel;
	}
	public String getTipoRegistro() {
		return tipoRegistro;
	}
	public float getAporte() {
		return aporte;
	}
	public Date getFechaPatrocinio() {
		return fechaPatrocinio;
	}
	public int getCuposGratuitos() {
		return cuposGratuitos;
	}
	public String getCodigoPatrocinio() {
		return codigoPatrocinio;
	}
}
