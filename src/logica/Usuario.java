package logica;
import logica.datatypes.DTUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Usuario {
	private static final Logger logger = Logger.getLogger(Usuario.class.getName());
	private	String nickname;
	private String nombre;
	private String correo;
	
	// Constructor
    public Usuario(String nickname, String nombre, String correo) {
        this.setNickname(nickname);
        this.setNombre(nombre);
        this.setCorreo(correo);
    }
    // Getters
    public String getNickname() {return nickname;}
    public String getNombre() {return nombre;}
    public String getCorreo() {return correo;}

    // Setters
    public void setNickname(String nick) {this.nickname = nick;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setCorreo(String correo) {this.correo = correo;}
    
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
