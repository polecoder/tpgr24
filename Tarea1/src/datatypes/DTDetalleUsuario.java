package datatypes;

import java.util.Arrays;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTDetalleUsuario")
@XmlSeeAlso({DTDetalleAsistente.class, DTDetalleOrganizador.class}) 
@XmlAccessorType(XmlAccessType.FIELD)
public class DTDetalleUsuario {
	private String nickname;
	private String nombre;
	private String correo;
	private byte[] imagen;
	private ETipoUsuario tipo;
	
	/**
	 * Constructor sin argumentos requerido por JAXB.
	 * NO USAR en código de aplicación. Usar DTDetalleUsuario(String, String, ...) en su lugar.
	 */
	public DTDetalleUsuario() {
	}
	
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
}
