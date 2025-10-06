package logica;
import datatypes.DTUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Usuario {
	private static final Logger logger = Logger.getLogger(Usuario.class.getName());
	private	String nickname;
	private String nombre;
	private String correo;
	private String contrasena;
	
	// Constructor
    public Usuario(String nickname, String contrasena, String nombre, String correo) {
        this.setNickname(nickname);
        this.setContrasena(contrasena);
        this.setNombre(nombre);
        this.setCorreo(correo);
    }
    // Getters
    public String getNickname() {return nickname;}
    public String getNombre() {return nombre;}
    public String getCorreo() {return correo;}
    public String getContrasena() {return contrasena;}

    // Setters
    public void setNickname(String nick) {this.nickname = nick;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setCorreo(String correo) {this.correo = correo;}
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}
    
    public DTUsuario listarUsuario() {
    	try {
    		return new DTUsuario(nickname, nombre, correo);
    	}catch(Exception LUError) {
    		// Log the error
            logger.log(Level.SEVERE, "Usuario::listarUsuarios() ERROR: ", LUError);
            return new DTUsuario("","","");
    	}
    }
}
