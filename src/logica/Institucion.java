package logica;

import java.util.HashMap;
import java.util.Map;

public class Institucion {
	private 
	String nombre;
	String descripcion;
	String sitioWeb;
	Map<String,Asistente> asistentes;
	public Institucion(String nombre, String descripcion, String sitioWeb) {
	    setNombre(nombre);
	    setDescripcion(descripcion);
	    setSitioWeb(sitioWeb);
	    this.asistentes = new HashMap<>();
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
}
