package datatypes;

import java.util.Date;

public class DTDetalleAsistente extends DTDetalleUsuario {
	private String apellido;
	private Date nacimiento;
	private String institucion;
	
	/*public DTDetalleAsistente(String nickname, String nombre, String correo, String apellido, Date nacimiento) {
		super(nickname, nombre, correo);
		this.apellido = apellido;
		this.nacimiento = nacimiento;
	}*/
	//CON IMAGEN
	public DTDetalleAsistente(String nickname, String nombre, String correo,
            String apellido, Date nacimiento, String institucion, byte[] imagen) {
	super(nickname, nombre, correo, ETipoUsuario.Asistente, imagen);
	this.apellido = apellido;
	this.nacimiento = nacimiento;
	this.institucion=institucion;
	}
	
	public String getApellido() {
		return apellido;
	}

	public Date getNacimiento() {
		return nacimiento;
	}
	
	public String getInstitucion() {
		return institucion;
	}
	
	public boolean equals(DTDetalleAsistente o) {
		boolean nickname = this.getNickname()== o.getNickname();
		boolean nombre = this.getNombre() == o.getNombre();
		boolean correo = this.getCorreo() == o.getCorreo();
		boolean apellido = this.apellido == o.getApellido();
		boolean nacimiento = this.nacimiento == o.getNacimiento();
		return nickname && nombre && correo && apellido && nacimiento;
	}
}
