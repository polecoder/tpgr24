package tarea1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class manejadorInstituciones {
	private static manejadorInstituciones instance = null;
	
	private List<Institucion> conjuntoInstituciones;
	
	// Constructor
	private manejadorInstituciones() {
		conjuntoInstituciones = new ArrayList<>();
	}
	
	public static manejadorInstituciones getInstance() {
		if (instance == null) {
			instance = new manejadorInstituciones();
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
