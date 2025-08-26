package logica;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

import logica.datatypes.DTEdicionEvento;

public class Organizador extends Usuario {
	private static final Logger logger = Logger.getLogger(Organizador.class.getName());
	
	private String descripcion;
	private String link;
	private List<Edicion> ediciones;
	
	public Organizador(String nickname, String nombre, String correo, String descripcion, String link) {
		super(nickname,nombre,correo);
		this.descripcion = descripcion;
		this.link = link;
		this.ediciones = new ArrayList<>();
	}
	
	// Getters
	public String getDescripcion() {return descripcion;}
	public String getLink() {return link;}
	public List<Edicion> getEdiciones(){return ediciones;}
	
	// Setters
	public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
	public void setLink(String link) {this.link = link;}
	// Metodos
	public List<DTEdicionEvento> listarEdiciones(){
		try {
			List<DTEdicionEvento> myList = new ArrayList<>();
			for(Edicion e : ediciones) {
				DTEdicionEvento DTEd = e.listarEdicion();
				myList.add(DTEd);
			}
			return myList;
		}catch(Exception LEError) {
			// Log the error
            logger.log(Level.SEVERE, "Organizador::listarEdiciones() ERROR: ", LEError);
            return new ArrayList<>();
		}
	}
	
}
