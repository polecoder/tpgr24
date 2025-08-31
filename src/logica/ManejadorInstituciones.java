package logica;


import java.util.HashMap;

import java.util.Map;

import logica.datatypes.DTPatrocinio;

public class ManejadorInstituciones {
	private static ManejadorInstituciones instance = null;
	
	private Map<String,Institucion> conjuntoInstituciones;
	
	// Constructor
	private ManejadorInstituciones() {
	    this.conjuntoInstituciones = new HashMap<>();
	}
	
	public static ManejadorInstituciones getInstance() {
		if (instance == null) {
			instance = new ManejadorInstituciones();
		}
		return instance;
	}
	
	// ------- GETTERS ----------
	public Map<String,Institucion>getConjuntoInstituciones() {
		return conjuntoInstituciones;
	}
	
	public Institucion getInstitucion(String Nombre) {
		return conjuntoInstituciones.get(Nombre);
	}
	// ------- GETTERS ----------
	
	public DTPatrocinio getPatrocinio(String codigoPatrocinio) {
		return null; 
	}

	public void AgregarInstitucion(String nombre, String descripcion, String sitioWeb) {
		Institucion insti= new Institucion(nombre,descripcion,sitioWeb);
		conjuntoInstituciones.put(nombre, insti);
		
	}

	public boolean existeNombre(String nombre) {
		return conjuntoInstituciones.containsKey(nombre);
	}
}
