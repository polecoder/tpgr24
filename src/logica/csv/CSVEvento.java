package logica.csv;

import com.opencsv.bean.CsvBindByName;

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
	private String fechaAlta;
	@CsvBindByName(column = "Categorias")
	private String categorias;
	
	// NECESARIO para OpenCSV
	public CSVEvento() {}
	
	public CSVEvento(String ref, String nombre, String descripcion, String sigla, String FechaAlta, String categorias) {
		System.out.println(categorias);
	}
	
	public String getNombre() {
		return this.nombre;
	}
}
