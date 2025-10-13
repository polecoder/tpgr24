package logica;
import datatypes.DTUsuario;

public class Usuario {
	private	String nickname;
	private String nombre;
	private String correo;
	private String contrasena;
	
	// Constructor
    protected Usuario(String nickname, String contrasena, String nombre, String correo) {
        this.setNickname(nickname);
        this.setContrasena(contrasena);
        this.setNombre(nombre);
        this.setCorreo(correo);
    }
    // Getters
    public String getNickname() {return nickname; }
    public String getNombre() {return nombre; }
    public String getCorreo() {return correo; }
    public String getContrasena() {return contrasena; }

    // Setters
    public void setNickname(String nick) {this.nickname = nick; }
    public void setNombre(String nombre) {this.nombre = nombre; }
    public void setCorreo(String correo) {this.correo = correo; }
    public void setContrasena(String contrasena) {this.contrasena = contrasena; }
    
    public DTUsuario listarUsuario() {
		return new DTUsuario(nickname, nombre, correo);
    }
}
