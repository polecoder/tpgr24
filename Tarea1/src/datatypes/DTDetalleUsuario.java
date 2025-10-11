package datatypes;

import java.util.Arrays;

public class DTDetalleUsuario {
	protected String nickname;
	protected String nombre;
	protected String correo;
	protected byte[] imagen;
	protected ETipoUsuario tipo;
	
	public DTDetalleUsuario(String nickname, String nombre, String correo, ETipoUsuario tipo,byte[] imagen) {
		super();
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
		this.imagen   = (imagen == null ? null : Arrays.copyOf(imagen, imagen.length));
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
	public byte[] getImagen()   { return imagen; }
	public ETipoUsuario getTipo() {
		return tipo;
	}
}
