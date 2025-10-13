package datatypes;

public class DTDetalleOrganizador extends DTDetalleUsuario {
	private String descripcion;
	private String link;
	
	/*public DTDetalleOrganizador(String nickname, String nombre, String correo, String descripcion, String link) {
		super(nickname, nombre, correo);
		this.descripcion = descripcion;
		this.link = link;
	}*/
	
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
