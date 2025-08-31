package logica;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import logica.datatypes.DTInstitucion;

public class ControladorInstitucion implements IControladorInstitucion {
	private static ControladorInstitucion instancia;
	private ManejadorInstituciones ManejadorInstituciones;
	private ControladorInstitucion() {
		this.ManejadorInstituciones=logica.ManejadorInstituciones.getInstance();
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
	@Override
	public void AltaInstitucion(String nombre, String descripcion, String sitioWeb) throws Exception {
	    // Validación de campos obligatorios
	    if (nombre == null || nombre.trim().isEmpty() ||
	        descripcion == null || descripcion.trim().isEmpty() ||
	        sitioWeb == null || sitioWeb.trim().isEmpty()) {
	        throw new Exception("Todos los campos son obligatorios");
	    }
	    
	    // Validación de nombre ya existente (sin implementación aún)
	    if (ManejadorInstituciones.existeNombre(nombre)) {
	        throw new Exception("Ya existe una institución con ese nombre");
	    }

	    // Validación de formato de nombre
	    if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ()\\s]+$")) {
	        throw new Exception("El nombre solo puede contener letras, espacios y paréntesis");
	    }

	    // Validación de formato de sitio web
	    if (!sitioWeb.matches("^(https?://).+")) {
	        throw new Exception("El sitio web debe comenzar con http:// o https://");
	    }

	    // Si todo está bien, continuar con la lógica de alta
	    ManejadorInstituciones.AgregarInstitucion(nombre, descripcion, sitioWeb);
	}

}

