package logica.csv;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

public class CSVEvento {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "Nombre")
	private String nombre;
	@CsvBindByName(column = "Descripcion")
	private String descripcion;
	@CsvBindByName(column = "Sigla")
	private String sigla;
	@CsvBindByName(column = "FechaAlta")
	@CsvDate("dd/MM/yyyy")
	private Date fechaAlta;
	@CsvBindByName(column = "Categorias")
	private String categorias;
	
	// NECESARIO para OpenCSV
	public CSVEvento() {}
	
	public CSVEvento(String ref, String nombre, String descripcion, String sigla, Date fechaAlta, String categorias) {
		this.ref = ref;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.sigla = sigla;
		this.fechaAlta = fechaAlta;
		this.categorias = categorias;
	}
	
	public String getRef() {
		return this.ref;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getDescripcion() {
		return this.descripcion;
	}
	public String getSigla() {
		return this.sigla;
	}
	public Date getFechaAlta() {
		return this.fechaAlta;
	}
	public String getCategorias() {
		return this.categorias;
	}
}
