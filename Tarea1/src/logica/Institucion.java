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
	public String getNombre(){
		return this.nombre;
	}
	public String getDescripcion(){
		return this.descripcion;
	}
	public String getSitioWeb(){
		return this.sitioWeb;
	}
	public Map<String,Asistente> getAsistentes(){
		return this.asistentes;
	};
	
	public void setNombre(String nombre){
		  this.nombre =nombre;
	}
	public void setDescripcion(String descripcion){
		 this.descripcion =descripcion ;
	}
	public void setSitioWeb(String sitioWeb){
		this.sitioWeb=sitioWeb;
	}
	
	public void agregarAsistente(Asistente asistente){
		this.asistentes.put(asistente.getNickname(), asistente);
	};
	public void eliminarAsistente(String nickname) {
		this.asistentes.remove(nickname);
	}
	public void agregarEdicion(String edicion){
		this.edicionesConPatrocinio.add(edicion);
	}
	
	public boolean checkAsistente(String nicknameAsistente) {
		return asistentes.containsKey(nicknameAsistente);
	}
	public Set<String> getEdicionesPatrocinadas() {
		return this.edicionesConPatrocinio;
	}
}
