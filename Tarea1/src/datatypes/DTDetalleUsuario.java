package datatypes;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
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
	private Set<String> dataTypeSeguidores;
	private Set<String> dataTypeSeguidos;
	
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
	
	public Set<String> getDataTypeSeguidores(){ return dataTypeSeguidores; }
	public Set<String> getDataTypeSeguidos(){ return dataTypeSeguidos; }
	
	public void setDataTypeSeguidores(Set<String> seguidores) { this.dataTypeSeguidores = seguidores; }
	public void setDataTypeSeguidos(Set<String> seguidos) { this.dataTypeSeguidos = seguidos; }
	
	// Sobre-escritura
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) return true;
        if (obj == null || !(obj instanceof DTDetalleUsuario)) return false;
        DTDetalleUsuario otro = (DTDetalleUsuario) obj;
        boolean comparacion = 
        		this.nickname.equals(otro.nickname) &&
        		this.nombre.equals(otro.nombre) &&
        		this.correo.equals(otro.correo) &&
        		this.imagen.equals(otro.imagen) &&
        		this.tipo.equals(otro.tipo);
        return comparacion;
    }
    @Override
    public int hashCode() {
        return Objects.hash(nickname,nombre,correo,imagen,tipo);
    }
}
