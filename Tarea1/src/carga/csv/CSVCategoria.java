package carga.csv;

import com.opencsv.bean.CsvBindByName;

public class CSVCategoria {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "Nombre")
	private String nombre;
	
	// NECESARIO para OpenCSV
	public CSVCategoria() {}
	
	public CSVCategoria(String ref, String nombre) {
		this.setRef(ref);
		this.setNombre(nombre);
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
}
