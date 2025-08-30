package logica.csv;

import com.opencsv.bean.CsvBindByName;

public class CSVEdicion {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "Evento")
	private String evento;
	@CsvBindByName(column = "Organizador")
	private String organizador;
	@CsvBindByName(column = "Nombre")
	private String nombre;
	@CsvBindByName(column = "Sigla")
	private String sigla;
	@CsvBindByName(column = "Ciudad")
	private String ciudad;
	@CsvBindByName(column = "Pais")
	private String pais;
	@CsvBindByName(column = "FechaInicio")
	private String fechaInicio;
	@CsvBindByName(column = "FechaFin")
	private String fechaFin;
	@CsvBindByName(column = "FechaAlta")
	private String fechaAlta;
	
	// NECESARIO para OpenCSV
	public CSVEdicion() {}
	
	public CSVEdicion(String ref, String evento, String organizador, String nombre, String sigla, String ciudad, String pais, String fechaInicio, String fechaFin, String fechaAlta) {
		this.ref = ref;
		this.evento = evento;
		this.organizador = organizador;
		this.nombre = nombre;
		this.sigla = sigla;
		this.ciudad = ciudad;
		this.pais = pais;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.fechaAlta = fechaAlta;
	}
	
	public String getRef() {
		return this.ref;
	}
	public String getEvento() {
		return this.evento;
	}
	public String getOrganizador() {
		return this.organizador;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getSigla() {
		return this.sigla;
	}
	public String getCiudad() {
		return this.ciudad;
	}
	public String getPais() {
		return this.pais;
	}
	public String getFechaInicio() {
		return this.fechaInicio;
	}
	public String getFechaFin() {
		return this.fechaFin;
	}
	public String getFechaAlta() {
		return this.fechaAlta;
	}
}
