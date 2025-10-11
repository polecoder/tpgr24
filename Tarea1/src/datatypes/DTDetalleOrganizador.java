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
super(nickname, nombre, correo, imagen);
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
		boolean nickname = this.nickname == o.getNickname();
		boolean nombre = this.nombre == o.getNombre();
		boolean correo = this.correo == o.getCorreo();
		boolean descripcion = this.descripcion == o.getDescripcion();
		boolean link = this.link == o.getLink();
		return nickname && nombre && correo && descripcion && link;
	}
}
