package datatypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTDetalleOrganizador")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTDetalleOrganizador extends DTDetalleUsuario {
	private String descripcion;
	private String link;
	
	/*public DTDetalleOrganizador(String nickname, String nombre, String correo, String descripcion, String link) {
		super(nickname, nombre, correo);
		this.descripcion = descripcion;
		this.link = link;
	}*/
	/**
	 * Constructor sin argumentos requerido por JAXB.
	 * NO USAR en código de aplicación. Usar DTDetalleOrganizador(String, String, ...) en su lugar.
	 */
	public DTDetalleOrganizador() {
	}
	
	public DTDetalleOrganizador(String nickname, String nombre, String correo,
            String descripcion, String link, byte[] imagen) {
			super(nickname, nombre, correo, ETipoUsuario.Organizador, imagen);
			this.descripcion = descripcion;
			this.link = link;
			}
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public String getLink() {
		return link;
	}
	
	public boolean equalsOrganizador(DTDetalleOrganizador organizador) {
		boolean nickname = this.getNickname() == organizador.getNickname();
		boolean nombre = this.getNombre() == organizador.getNombre();
		boolean correo = this.getCorreo() == organizador.getCorreo();
		boolean descripcion = this.getDescripcion() == organizador.getDescripcion();
		boolean link = this.getLink() == organizador.getLink();
		return nickname && nombre && correo && descripcion && link;
	}
}
