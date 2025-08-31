package logica.datatypes;

public class DTDetalleUsuario {
	private String nickname;
	private String nombre;
	private String correo;
	
	public DTDetalleUsuario(String nickname, String nombre, String correo) {
		super();
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
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
}
