package logica;

import java.util.ArrayList;
import java.util.List;

import datatypes.DTDetalleUsuario;
import datatypes.DTEdicionEvento;
import datatypes.ETipoUsuario;

public class Organizador extends Usuario {
	
	private String descripcion;
	private String link;
	private List<Edicion> ediciones;
	
	public Organizador(String nickname, String contrasena, String nombre, String correo, String descripcion, String link) {
		super(nickname, contrasena, nombre, correo);
		this.setDescripcion(descripcion);
		this.setLink(link);
		this.ediciones = new ArrayList<>();
	}
	
	// Getters
	public String getDescripcion() {return descripcion; }
	public String getLink() {return link; }
	public List<Edicion> getEdiciones(){return ediciones; }
	
	// Setters
	public void setDescripcion(String descripcion) {this.descripcion = descripcion; }
	public void setLink(String link) {this.link = link; }
	
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
	
	public DTDetalleUsuario toDataType() {
		return new DTDetalleUsuario(
				this.nickname,
				this.nombre,
				this.correo,
				ETipoUsuario.Organizador,
				this.imagen);
	}
}
