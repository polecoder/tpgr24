package logica.csv;

import com.opencsv.bean.CsvBindByName;

public class CSVInstitucion {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "Nombre")
	private String nombre;
	@CsvBindByName(column = "Descripcion")
	private String descripcion;
	@CsvBindByName(column = "SitioWeb")
	private String sitioWeb;
	
	// NECESARIO para OpenCSV
	public CSVInstitucion() {}
	
	public CSVInstitucion(String ref, String nombre, String descripcion, String sitioWeb) {
		this.ref = ref;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.sitioWeb = sitioWeb;
	}
	
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSitioWeb() {
		return sitioWeb;
	}
	public void setSitioWeb(String sitioWeb) {
		this.sitioWeb = sitioWeb;
	}
}
