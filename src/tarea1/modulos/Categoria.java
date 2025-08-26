package tarea1.modulos;

import java.util.HashMap;

public class Categoria {
	private String nombre;
	private HashMap<String, Evento> eventos;
	
	public Categoria(String nombre) {
		this.setNombre(nombre);
		this.setEventos(new HashMap<>());
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public HashMap<String, Evento> getEventos() {
		return eventos;
	}
	public void setEventos(HashMap<String, Evento> eventos) {
		this.eventos = eventos;
	}

	public void agregarEvento(Evento evento) {
		this.eventos.put(evento.getNombre(), evento);
	}
	public void eliminarEvento(String nombreEvento) {
		this.eventos.remove(nombreEvento);
	}
}
