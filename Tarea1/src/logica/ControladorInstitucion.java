package logica;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import datatypes.DTInstitucion;
import excepciones.FormularioIncompletoInstitucion;

public class ControladorInstitucion implements IControladorInstitucion {
	private static ControladorInstitucion instancia;
	private ManejadorInstituciones manejadorInstituciones;
	private ControladorInstitucion() {
		this.manejadorInstituciones=ManejadorInstituciones.getInstance();
	};
	public static ControladorInstitucion getInstance() {
		if (instancia==null) {
			instancia= new ControladorInstitucion();
		}
		return instancia;
	}
	
	public static void resetInstancia() {
		ManejadorInstituciones.resetInstance();
		instancia = null;
	}
	
	public Set<DTInstitucion> listarInstituciones() {
		//TODO
		return null;
	}

	@Override
	public Institucion getInstitucion(String nombre) {
		return manejadorInstituciones.getInstitucion(nombre);
	}
	@Override
	public Set<String> nombresInstituciones() {
	    Set<String> nombresInstituciones = new HashSet<>();
	    Collection<Institucion> instituciones = manejadorInstituciones.getInstituciones().values();

	    if (instituciones != null && !instituciones.isEmpty()) {
	        for (Institucion institucion : instituciones) {
	            nombresInstituciones.add(institucion.getNombre());
	        }
	    }

	    return nombresInstituciones;
	}
	@Override
	public void altaInstitucion(String nombre, String descripcion, String sitioWeb) throws FormularioIncompletoInstitucion {
	    // Validación de campos obligatorios
	    if (nombre == null || nombre.trim().isEmpty() ||
	        descripcion == null || descripcion.trim().isEmpty() ||
	        sitioWeb == null || sitioWeb.trim().isEmpty()) {
	        throw new FormularioIncompletoInstitucion("Todos los campos son obligatorios");
	    }
	    
	    // Validación de nombre ya existente
	    if (manejadorInstituciones.existeNombre(nombre)) {
	        throw new FormularioIncompletoInstitucion("Ya existe una institución con ese nombre");
	    }

	    // Validación de formato de nombre
	    if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ()\\s]+$")) {
	        throw new FormularioIncompletoInstitucion("El nombre solo puede contener letras, espacios y paréntesis");
	    }

	    // Validación de formato de sitio web
	    if (!sitioWeb.matches("^(https?://).+")) {
	        throw new FormularioIncompletoInstitucion("El sitio web debe comenzar con http:// o https://");
	    }

	    // Si todo está bien, continuar con la lógica de alta
	    manejadorInstituciones.altaInstitucion(nombre, descripcion, sitioWeb);
	}
}

