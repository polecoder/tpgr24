package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import datatypes.DTEdicionEvento;

public class Organizador extends Usuario {
	private static final Logger logger = Logger.getLogger(Organizador.class.getName());
	
	private String descripcion;
	private String link;
	private List<Edicion> ediciones;
	private byte[] imagen;
	
	public Organizador(String nickname, String contrasena, String nombre, String correo, String descripcion, String link) {
		super(nickname, contrasena, nombre, correo);
		this.setDescripcion(descripcion);
		this.setLink(link);
		this.ediciones = new ArrayList<>();
		this.setImagen(imagen);
	}
	
	// Getters
	public String getDescripcion() {return descripcion; }
	public String getLink() {return link; }
	public List<Edicion> getEdiciones(){return ediciones; }
	public byte[] getImagen() { return imagen; }
	
	// Setters
	public void setDescripcion(String descripcion) {this.descripcion = descripcion; }
	public void setLink(String link) {this.link = link; }
	public void setImagen(byte[] imagen) { this.imagen = imagen; }
	// Metodos
	public List<DTEdicionEvento> listarEdiciones(){
		List<DTEdicionEvento> myList = new ArrayList<>();
		for (Edicion e : ediciones) {
			DTEdicionEvento DTEd = e.listarEdicion();
			myList.add(DTEd);
		}
		return myList;
	}
	
	public void agregarEdicion(Edicion edicion) {
		this.ediciones.add(edicion);
	}
}
