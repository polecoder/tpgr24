package logica;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import logica.datatypes.DTInstitucion;

public class ControladorInstitucion implements IControladorInstitucion {
	private static ControladorInstitucion instancia;
	private ManejadorInstituciones ManejadorInstituciones;
	private ControladorInstitucion() {
		this.ManejadorInstituciones=ManejadorInstituciones.getInstance();
	};
	public static ControladorInstitucion getInstance() {
		if (instancia==null) {
			instancia= new ControladorInstitucion();
		}
		return instancia;
	}
	
	public Set<DTInstitucion> listarInstituciones() {
		//TODO
		return null;
	}

	@Override
	public Institucion getInstitucion(String nombre) {
		return ManejadorInstituciones.getInstitucion(nombre);
	}
	@Override
	public Set<String> nombresInstituciones() {
	    Set<String> nombresInstituciones = new HashSet<>();
	    Collection<Institucion> instituciones = ManejadorInstituciones.getConjuntoInstituciones().values();

	    if (instituciones != null && !instituciones.isEmpty()) {
	        for (Institucion institucion : instituciones) {
	            nombresInstituciones.add(institucion.getNombre());
	        }
	    }

	    return nombresInstituciones;
	}
}

