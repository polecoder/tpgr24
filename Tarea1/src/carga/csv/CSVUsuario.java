package carga.csv;

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
	@CsvBindByName(column = "Contraseña")
	private String contrasena;
	@CsvBindByName(column = "Imagen")
	private String imagen;
	
	// NECESARIO para OpenCSV
	public CSVUsuario() {}

	public CSVUsuario(String ref, String tipo, String nickname, String nombre, String correo, String contrasena, String imagen) {
		super();
		this.ref = ref;
		this.tipo = tipo;
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.imagen = imagen;
	}
	
	public String getRef() {
		return ref;
	}
	public String getTipo() {
		return tipo;
	}
	public String getNickname() {
		return nickname;
	}
	public String getNombre() {
		return nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public String getContrasena() {
		return contrasena;
	}
	public String getImagen() {
		return imagen;
	}
}
