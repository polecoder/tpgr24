package logica;

import java.util.HashMap;
import java.util.Map;

import logica.datatypes.DTPatrocinio;

public class ManejadorInstituciones {
	private static ManejadorInstituciones instance = null;
	
	private Map<String, Institucion> instituciones;
	
	// Constructor
	private ManejadorInstituciones() {
		setInstituciones(new HashMap<>());
	}
	
	public static ManejadorInstituciones getInstance() {
		if (instance == null) {
			instance = new ManejadorInstituciones();
		}
		return instance;
	}
	
	/**
	 * Devuelve la institución i tal que i.nombre == nombreInstitucion.
	 * Retorna null en caso de que no exista
	 * 
	 * @param nombreInstitucion
	 * @return
	 */
	public Institucion getInstitucion(String nombreInstitucion) {
		if (this.instituciones.containsKey(nombreInstitucion)) {
			return this.instituciones.get(nombreInstitucion);
		}
		return null;
	}
	public Map<String, Institucion> getInstituciones() {
		return instituciones;
	}
	public void setInstituciones(Map<String, Institucion> instituciones) {
		this.instituciones = instituciones;
	}
	public void agregarInstitucion(Institucion institucion) {
		this.instituciones.put(institucion.getNombre(), institucion);
	}
	public void eliminarPatrocinio(String nombreInstitucion) {
		this.instituciones.remove(nombreInstitucion);
	}
	
	/**
	 * Se crea una instancia de Instituciones y se agrega a la colección de instituciones y la retorna.
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param sitioWeb
	 * @return
	 */
	public Institucion altaInstitucion(String nombre, String descripcion, String sitioWeb) {
		Institucion institucion = new Institucion(nombre, descripcion, sitioWeb);
		this.agregarInstitucion(institucion);
		return institucion;
	}
	
	/**
	 * Imprime en consola todas las instituciones que existen en la colección de instituciones.
	 */
	public void imprimirInstituciones() {
		if (this.instituciones != null) {
			System.out.println(this.instituciones);
		}
	}
	
	public DTPatrocinio getPatrocinio(String codigoPatrocinio) {
		return null;
	}
}
