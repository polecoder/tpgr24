package logica;
import datatypes.DTDetalleUsuario;
import datatypes.DTUsuario;

public abstract class Usuario {
	protected String nickname;
	protected String nombre;
	protected String correo;
	protected String contrasena;
	protected byte[] imagen;
	
	// Constructor
    protected Usuario(String nickname, String contrasena, String nombre, String correo) {
        this.setNickname(nickname);
        this.setContrasena(contrasena);
        this.setNombre(nombre);
        this.setCorreo(correo);
        this.setImagen(imagen);
    }
    // Getters
    public String getNickname() {return nickname; }
    public String getNombre() {return nombre; }
    public String getCorreo() {return correo; }
    public String getContrasena() {return contrasena; }
	public byte[] getImagen() { return imagen; }

    // Setters
    public void setNickname(String nick) {this.nickname = nick; }
    public void setNombre(String nombre) {this.nombre = nombre; }
    public void setCorreo(String correo) {this.correo = correo; }
    public void setContrasena(String contrasena) {this.contrasena = contrasena; }
	public void setImagen(byte[] imagen) { this.imagen = imagen; }
    
	// Metodos
    public DTUsuario listarUsuario() {
		return new DTUsuario(nickname, nombre, correo);
    }
    public abstract DTDetalleUsuario toDataType();
}
