package logica.csv;

import com.opencsv.bean.CsvBindByName;

public class CSVUsuario {
	@CsvBindByName(column = "Ref")
	private String ref;
	@CsvBindByName(column = "Tipo")
	private String tipo;
	@CsvBindByName(column = "Nickname")
	private String nickname;
	@CsvBindByName(column = "Nombre")
	private String nombre;
	@CsvBindByName(column = "Correo")
	private String correo;
	
	// NECESARIO para OpenCSV
	public CSVUsuario() {}

	public CSVUsuario(String ref, String tipo, String nickname, String nombre, String correo) {
		super();
		this.ref = ref;
		this.tipo = tipo;
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
	}
	
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
}
