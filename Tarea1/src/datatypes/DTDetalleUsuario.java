package datatypes;

import java.util.Arrays;
import java.util.Set;

public class DTDetalleUsuario {
	private String nickname;
	private String nombre;
	private String correo;
	private byte[] imagen;
	private ETipoUsuario tipo;
	private Set<DTDetalleUsuario> dataTypeSeguidores;
	private Set<DTDetalleUsuario> dataTypeSeguidos;
	
	public DTDetalleUsuario(String nickname, String nombre, String correo, ETipoUsuario tipo, byte[] imagen) {
		super();
		this.nickname = nickname;
		this.nombre = nombre;
		this.correo = correo;
		this.imagen   = imagen == null ? null : Arrays.copyOf(imagen, imagen.length);
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
	
	public Set<DTDetalleUsuario> getDataTypeSeguidores(){ return dataTypeSeguidores; }
	public Set<DTDetalleUsuario> getDataTypeSeguidos(){ return dataTypeSeguidos; }
	
	public void setDataTypeSeguidores(Set<DTDetalleUsuario> seguidores) { this.dataTypeSeguidores = seguidores; }
	public void setDataTypeSeguidos(Set<DTDetalleUsuario> seguidos) { this.dataTypeSeguidores = seguidos; }
}
