package logica;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

public class ManejadorEventos {
	private 
	static ManejadorEventos instancia;
	Map<String,Evento> eventos;
	Map<String, Categoria> categorias;
	ManejadorEventos(){
		 this.eventos = new HashMap<>();
		 this.categorias = new HashMap<>();
	};
	public
	static ManejadorEventos getInstancia() {
		if (instancia==null) {
			instancia= new ManejadorEventos();
		}
		return instancia;
	};
	
	Evento getEventoEdicion(String nombreEdicion) {
		//TODO
		return null;
	};
	
	boolean contieneEdicion(String NombreEdicion) {
		//TODO
		return true;
	};
	
	Set<DTEvento> listarEventos(){
		//TODO
		return null;
	};
	
	Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion){
		//TODO
		return null;
	};
	
	boolean Validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro){
		//TODO
		return true;
	};

	void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion) {
		//TODO
	};
		
	Registro getRegistro(String nombreEdicion,String nickAsistente){
		//TODO
		return null;
	};
	
	Edicion getEdicion(String nombreEdicion){
		//TODO
		return null;
	}
	
	/**
	 * Crea una categoría de nombre "nombreCategoria" y la agrega a la colección de categorías.
	 * 
	 * @param nombreCategoria
	 */
	public void altaCategoria(String nombreCategoria) {
		Categoria categoria = new Categoria(nombreCategoria);
		this.categorias.put(nombreCategoria, categoria);
	}
	
	/**
     * Devuelve true si existe un evento e en el sistema tal que e.nombre = nombreEvento.
     * En caso contrario devuelve false
     * 
     * @param nombreEvento
     * @return
     */
    public boolean existeEvento(String nombreEvento) {
    	return this.eventos.containsKey(nombreEvento);
    }
	
	/**
	 * Retorna el evento e en el sistema tal que e.nombre = nombreEvento.
	 * Si el evento no existe, retorna null
	 * 
	 * @param nombreEvento
	 * @return
	 */
	public Evento getEvento(String nombreEvento) {
		if (existeEvento(nombreEvento)) {
			return this.eventos.get(nombreEvento);
		}
		return null;
	}
	
	/**
	 * Agrega la categoría c tal que c.nombre = nombreCategoria al evento e tal que e.nombre = nombreEvento.
	 * También ingresa el Evento en la colección de Eventos de la categoría.
	 * Si alguno de los dos elementos del sistema no existe, no hace nada.
	 * 
	 * @param nombreEvento
	 * @param nombreCategoria
	 */
	public void agregarCategoriaAEvento(String nombreEvento, String nombreCategoria) {
		if (this.eventos.containsKey(nombreEvento) && this.categorias.containsKey(nombreCategoria)) {
			Evento evento = this.eventos.get(nombreEvento);
			Categoria categoria = this.categorias.get(nombreCategoria);
			evento.agregarCategoria(categoria);
			categoria.agregarEvento(evento);
		}
	}
	
	/**
	 * PRE-CONDICIÓN: Los datos ingresados por parámetro están formateados correctamente.
	 * Da de alta un Evento con los parámetros indicados en el sistema.
	 * Si ya existe un Evento con el nombre indicado en el sistema, no hace nada.
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param fechaAlta
	 * @param sigla
	 * @param categorias
	 */
	public void altaEvento(String nombre, String descripcion, Date fechaAlta, String sigla, List<String> categorias) {
		if (!existeEvento(nombre)) {
			Evento evento = new Evento(nombre, descripcion, sigla, fechaAlta);
			this.eventos.put(nombre, evento);
			for (String nombreCategoria: categorias) {
				agregarCategoriaAEvento(nombre, nombreCategoria);
			}
		}
	}

	
	public void altaEdicion(String nombreEvento, String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,LocalDate fechaAlt, String ciudad, String pais) {
	}
	
	public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento, String nombreEdicion) {
	}
	/**
	 * Imprime en consola todas las categorías que existen en la colección de categorías.
	 */
	public void imprimirCategorias() {
		if (this.categorias != null) {
			System.out.println(this.categorias);
		}
	}
}
