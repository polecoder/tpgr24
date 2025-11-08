package datatypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTUsuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTUsuario {

    private String nickname;
    private String nombre;
    private String correo;

    // Constructor vacío (necesario para JAXB)
    public DTUsuario() {
    }

    // Constructor con parámetros
    public DTUsuario(String nickname, String nombre, String correo) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.correo = correo;
    }

    // Getters y setters
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
    public String nickname() {
    	return nickname;
    }
    public String nombre() {
    	return nombre;
    }
    public String correo() {
    	return correo;
    }
}
