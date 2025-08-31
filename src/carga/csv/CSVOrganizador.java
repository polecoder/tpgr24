package carga.csv;

import com.opencsv.bean.CsvBindByName;

public class CSVOrganizador {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "Descripcion")
	private String descripcion;
	@CsvBindByName(column = "SitioWeb")
	private String sitioWeb;
	
	// NECESARIO para OpenCSV
	public CSVOrganizador() {}
	
	public CSVOrganizador(String ref, String descripcion, String sitioWeb) {
		super();
		this.ref = ref;
		this.descripcion = descripcion;
		this.sitioWeb = sitioWeb;
	}

	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
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
