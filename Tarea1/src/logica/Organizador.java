package logica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleUsuario;
import datatypes.DTEdicionEvento;

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
		DTDetalleUsuario organizadorDataType = new DTDetalleOrganizador(
				this.nickname,
				this.nombre,
				this.correo,
				this.descripcion,
				this.link,
				this.imagen);
		Set<String> dataTypeSeguidores = new HashSet<String>();
		Set<String> dataTypeSeguidos = new HashSet<String>();
		for(Usuario seguidor : this.seguidores) {
			dataTypeSeguidores.add(seguidor.getNickname());
		}
		for(Usuario seguido : this.seguidos) {
			dataTypeSeguidos.add(seguido.getNickname());
		}
		organizadorDataType.setDataTypeSeguidores(dataTypeSeguidores);
		organizadorDataType.setDataTypeSeguidos(dataTypeSeguidos);
		
		return organizadorDataType;
	}
	
	/**
	 * Elimina la edición pasada por el parámetro `edicion` de la colección de ediciones.<p>
	 * Si la edición no existe no hace nada.
	 * 
	 * @param nombreEdicion
	 */
	public void eliminarEdicion(Edicion edicion) {
		boolean existe = this.ediciones.contains(edicion);
		if (existe) this.ediciones.remove(edicion);
	}
}
