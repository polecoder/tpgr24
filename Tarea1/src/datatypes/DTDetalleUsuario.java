package datatypes;

public class DTDetalleUsuario {
	protected String nickname;
	protected String nombre;
	protected String correo;
	protected ETipoUsuario tipo;
	
	public DTDetalleUsuario(String nickname, String nombre, String correo, ETipoUsuario tipo) {
		super();
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
		this.tipo = tipo;
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
	public ETipoUsuario getTipo() {
		return tipo;
	}
}
