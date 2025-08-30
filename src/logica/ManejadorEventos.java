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
	
	public void altaEvento(String nombre, String descripcion, LocalDate fechaAlta, String sigla, List<String> categorias) {	
	}
	
	public void altaEdicion(String nombreEvento, String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,LocalDate fechaAlt, String ciudad, String pais) {
	}
	
	public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento, String nombreEdicion) {
	}
	public void altaPatrocinio(String nombreEdicion, String nombreInstitucion, ENivelPatrocinio nivel, String nombreTipoRegistro, float aporte, Date fechaPatrocinio, int cuposGratuitos, String codigoPatrocinio) {
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
