package logica.datatypes;

public class DTDetalleOrganizador extends DTDetalleUsuario {
	private String descripcion;
	private String link;
	
	public DTDetalleOrganizador(String nickname, String nombre, String correo, String descripcion, String link) {
		super(nickname, nombre, correo);
		this.descripcion = descripcion;
		this.link = link;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getLink() {
		return link;
	}
}
