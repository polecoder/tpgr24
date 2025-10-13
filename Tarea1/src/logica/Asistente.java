package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Asistente extends Usuario {
	
	
	private String apellido;
	private Date nacimiento;
	private List<Registro> registros;
	private Institucion institucion;
	private byte[] imagen;
	
	public Asistente(String nickname, String contrasena, String nombre, String correo, String apellido, Date fechaNacimiento, Institucion institucion) {
		super(nickname, contrasena, nombre, correo);
		this.setApellido(apellido);
		this.setNacimiento(fechaNacimiento);
		this.registros = new ArrayList<>();
		this.setInstitucion(institucion);
		this.setImagen(imagen);
	}
	
	// Getters
	public String getApellido() {return apellido; }
	public Date getNacimiento() {return nacimiento; }
	public List<Registro> getRegistros() {return registros; }
	public Institucion getInstitucion() {return institucion; }
	public byte[] getImagen() { return imagen; }
	// Setters
	public void setApellido(String apellido) {this.apellido = apellido; }
	public void setNacimiento(Date nacimiento) {this.nacimiento = nacimiento; }
	public void setInstitucion(Institucion institucion) {this.institucion=institucion; }
	public void setImagen(byte[] imagen) { this.imagen = imagen; }
	// Metodos
	public void addRegistro(Registro registro) {
		this.registros.add(registro);
	}
}

