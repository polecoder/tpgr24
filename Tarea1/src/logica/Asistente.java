package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleUsuario;

public class Asistente extends Usuario {
	private String apellido;
	private Date nacimiento;
	private List<Registro> registros;
	private Institucion institucion;
	
	public Asistente(String nickname, String contrasena, String nombre, String correo, String apellido, Date fechaNacimiento, Institucion institucion) {
		super(nickname, contrasena, nombre, correo);
		this.setApellido(apellido);
		this.setNacimiento(fechaNacimiento);
		this.registros = new ArrayList<>();
		this.setInstitucion(institucion);
	}
	
	// Getters
	public String getApellido() {return apellido; }
	public Date getNacimiento() {return nacimiento; }
	public List<Registro> getRegistros() {return registros; }
	public Institucion getInstitucion() {return institucion; }
	// Setters
	public void setApellido(String apellido) {this.apellido = apellido; }
	public void setNacimiento(Date nacimiento) {this.nacimiento = nacimiento; }
	public void setInstitucion(Institucion institucion) {this.institucion=institucion; }
	// Metodos
	public void addRegistro(Registro registro) {
		this.registros.add(registro);
	}
	public void eliminarRegistro(Registro registro) {
		this.registros.remove(registro);
	}
	public DTDetalleUsuario toDataType() {
		DTDetalleUsuario asistenteDataType =  new DTDetalleAsistente(
				this.nickname,
				this.nombre,
				this.correo,
				this.apellido,
				this.nacimiento,
				this.institucion != null ? this.institucion.getNombre() : null,
				this.imagen);
		Set<String> dataTypeSeguidores = new HashSet<String>();
		Set<String> dataTypeSeguidos = new HashSet<String>();
		for(Usuario seguidor : this.seguidores) {
			dataTypeSeguidores.add(seguidor.getNickname());
		}
		for(Usuario seguido : this.seguidos) {
			dataTypeSeguidos.add(seguido.getNickname());
		}
		asistenteDataType.setDataTypeSeguidores(dataTypeSeguidores);
		asistenteDataType.setDataTypeSeguidos(dataTypeSeguidos);
		
		return asistenteDataType;
	}
}

