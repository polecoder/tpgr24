package logica.datatypes;

import java.util.Date;

public class DTDetalleAsistente extends DTDetalleUsuario {
	private String apellido;
	private Date nacimiento;
	
	public DTDetalleAsistente(String nickname, String nombre, String correo, String apellido, Date nacimiento) {
		super(nickname, nombre, correo);
		this.apellido = apellido;
		this.nacimiento = nacimiento;
	}
	public String getApellido() {
		return apellido;
	}

	public Date getNacimiento() {
		return nacimiento;
	}
	
	public boolean equals(DTDetalleAsistente o) {
		boolean nickname = this.nickname == o.getNickname();
		boolean nombre = this.nombre == o.getNombre();
		boolean correo = this.correo == o.getCorreo();
		boolean apellido = this.apellido == o.getApellido();
		boolean nacimiento = this.nacimiento == o.getNacimiento();
		return nickname && nombre && correo && apellido && nacimiento;
	}
}
