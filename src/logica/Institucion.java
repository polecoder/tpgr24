package logica;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Institucion {
	private 
	String nombre;
	String descripcion;
	String sitioWeb;
	Map<String,Asistente> asistentes;
	Set<String> edicionesConPatrocinio;
	public Institucion(String nombre, String descripcion, String sitioWeb) {
	    setNombre(nombre);
	    setDescripcion(descripcion);
	    setSitioWeb(sitioWeb);
	    this.asistentes = new HashMap<>();
	    this.edicionesConPatrocinio = new HashSet<>();

	}
	String getNombre(){
		return this.nombre;
	}
	String getDescripcion(){
		return this.descripcion;
	}
	String getSitioWeb(){
		return this.sitioWeb;
	}
	Map<String,Asistente> getAsistentes(){
		return this.asistentes;
	};
	
	void setNombre(String nombre){
		  this.nombre =nombre;
	}
	void setDescripcion(String descripcion){
		 this.descripcion =descripcion ;
	}
	void setSitioWeb(String sitioWeb){
		this.sitioWeb=sitioWeb;
	}
	
	void agregarAsistente(Asistente asistente){
		this.asistentes.put(asistente.getNickname(), asistente);
	};
	void eliminarAsistente(String nickname) {
		this.asistentes.remove(nickname);
	}
	void agregarEdicion(String edicion){
		this.edicionesConPatrocinio.add(edicion);
	}
}
