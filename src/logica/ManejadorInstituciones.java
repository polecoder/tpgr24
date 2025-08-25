package logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import logica.datatypes.DTPatrocinio;

public class ManejadorInstituciones {
	private static ManejadorInstituciones instance = null;
	
	private List<Institucion> conjuntoInstituciones;
	
	// Constructor
	private ManejadorInstituciones() {
		conjuntoInstituciones = new ArrayList<>();
	}
	
	public static ManejadorInstituciones getInstance() {
		if (instance == null) {
			instance = new ManejadorInstituciones();
		}
		return instance;
	}
	
	// ------- GETTERS ----------
	public List<Institucion> getConjuntoAsistentes() {
		return conjuntoInstituciones;
	}
	// ------- GETTERS ----------
	
	public DTPatrocinio getPatrocinio(String codigoPatrocinio) {
		return null;
	}
}
