package carga.csv;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

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
	@CsvDate("dd/MM/yyyy")
	private Date fechaInicio;
	@CsvBindByName(column = "FechaFin")
	@CsvDate("dd/MM/yyyy")
	private Date fechaFin;
	@CsvBindByName(column = "FechaAlta")
	@CsvDate("dd/MM/yyyy")
	private Date fechaAlta;
	@CsvBindByName(column = "Estado")
	private String estado;
	@CsvBindByName(column = "Imagen")
	private String imagen;
	
	// NECESARIO para OpenCSV
	public CSVEdicion() {}
	
	public CSVEdicion(String ref, String evento, String organizador, String nombre, String sigla, String ciudad, String pais, Date fechaInicio, Date fechaFin, Date fechaAlta, String estado, String imagen) {
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
		this.estado = estado;
		this.imagen = imagen;
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
	public Date getFechaInicio() {
		return this.fechaInicio;
	}
	public Date getFechaFin() {
		return this.fechaFin;
	}
	public Date getFechaAlta() {
		return this.fechaAlta;
	}
	public String getEstado() {
		return estado;
	}
	public String getImagen() {
		return imagen;
	}
}
