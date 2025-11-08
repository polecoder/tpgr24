package logica;
import java.util.HashSet;
import java.util.Set;

import datatypes.DTDetalleUsuario;
import datatypes.DTUsuario;

public abstract class Usuario {
	protected String nickname;
	protected String nombre;
	protected String correo;
	protected String contrasena;
	protected byte[] imagen;
	protected Set<Usuario> seguidores;
	protected Set<Usuario> seguidos;
	
	// Constructor
    protected Usuario(String nickname, String contrasena, String nombre, String correo) {
        this.setNickname(nickname);
        this.setContrasena(contrasena);
        this.setNombre(nombre);
        this.setCorreo(correo);
        this.setImagen(imagen);
        this.setSeguidores(new HashSet<Usuario>());
        this.setSeguidos(new HashSet<Usuario> ());
    }
    // Getters
    public String getNickname() {return nickname; }
    public String getNombre() {return nombre; }
    public String getCorreo() {return correo; }
    public String getContrasena() {return contrasena; }
	public byte[] getImagen() { return imagen; }
	public Set<Usuario> getSeguidores() { return seguidores; }
	public Set<Usuario> getSeguidos() { return seguidos; }

    // Setters
    public void setNickname(String nick) {this.nickname = nick; }
    public void setNombre(String nombre) {this.nombre = nombre; }
    public void setCorreo(String correo) {this.correo = correo; }
    public void setContrasena(String contrasena) {this.contrasena = contrasena; }
	public void setImagen(byte[] imagen) { this.imagen = imagen; }
	public void setSeguidores(Set<Usuario> seguidores) { this.seguidores = seguidores; }
	public void setSeguidos(Set<Usuario> seguidos) { this.seguidos = seguidos; }
    
	// Metodos
    public DTUsuario listarUsuario() {
		return new DTUsuario(nickname, nombre, correo);
    }
    public abstract DTDetalleUsuario toDataType();
    public void seguir(Usuario usuario) {
    	seguidos.add(usuario);
    }
    public void dejarDeSeguir(Usuario usuario) {
    	seguidos.remove(usuario);
    }
    public void agregarSeguidor(Usuario seguidor) {
    	seguidores.add(seguidor);
    }
    public void quitarSeguidor(Usuario seguidor) {
    	seguidores.remove(seguidor);
    }
    
    // Sobre-escritura
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) return true;
        if (obj == null || !(obj instanceof Usuario)) return false;
        Usuario otro = (Usuario) obj;
        
        return this.nickname.equals(otro.nickname);
    }
    @Override
    public int hashCode() {
        return nickname.hashCode();
    }
}
