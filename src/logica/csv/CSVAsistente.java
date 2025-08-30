package logica.csv;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

public class CSVAsistente {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "Apellido")
	private String apellido;
	@CsvBindByName(column = "FechaNacimiento")
	@CsvDate("dd/MM/yyyy")
	private Date fechaNacimiento;
	@CsvBindByName(column = "Institucion")
	private String institucion;
	
	// NECESARIO para OpenCSV
	public CSVAsistente() {}

	public CSVAsistente(String ref, String apellido, Date fechaNacimiento, String institucion) {
		super();
		this.ref = ref;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.institucion = institucion;
	}

	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getInstitucion() {
		return institucion;
	}
	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}
}
