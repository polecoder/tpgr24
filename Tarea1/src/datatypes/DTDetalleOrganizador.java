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
	
	public boolean equals(DTDetalleOrganizador o) {
		boolean nickname = this.getNickname() == o.getNickname();
		boolean nombre = this.getNombre() == o.getNombre();
		boolean correo = this.getCorreo() == o.getCorreo();
		boolean descripcion = this.getDescripcion() == o.getDescripcion();
		boolean link = this.getLink() == o.getLink();
		return nickname && nombre && correo && descripcion && link;
	}
}
