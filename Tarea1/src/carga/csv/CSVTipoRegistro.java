package carga.csv;

import com.opencsv.bean.CsvBindByName;

public class CSVTipoRegistro {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "EdicionEvento")
	private String edicion;
	@CsvBindByName(column = "Nombre")
	private String nombre;
	@CsvBindByName(column = "Descripcion")
	private String descripcion;
	@CsvBindByName(column = "Costo")
	private String costo;
	@CsvBindByName(column = "Cupo")
	private String cupos;
	
	public CSVTipoRegistro() {}
	
	public CSVTipoRegistro(String ref, String edicion, String nombre, String descripcion, String costo, String cupos) {
		this.ref = ref;
		this.edicion = edicion;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costo = costo;
		this.cupos = cupos;
	}
	
	public String getRef() {
		return this.ref;
	}
	public String getEdicion() {
		return this.edicion;
	}
	public String getNombre() {
		return this.nombre;
	}
	public String getDescripcion() {
		return this.descripcion;
	}
	public String getCosto() {
		return this.costo;
	}
	public String getCupos() {
		return this.cupos;
	}
}
