package logica;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excepciones.DatosTipoRegistroInvalidos;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.datatypes.DTDatosPatrocinio;
import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;
import logica.datatypes.DTPatrocinio;
import logica.datatypes.DTTipoRegistro;

public class ControladorEventos implements IControladorEventos {
    private static ControladorEventos instancia;
    private ManejadorEventos manejadorEventos;
    private final Map<String, Categoria> categorias = new HashMap<>();

    private ControladorEventos() {
    	this.manejadorEventos =ManejadorEventos.getInstancia();
    }

    public static ControladorEventos getInstance() {
        if (instancia == null) {
        	instancia = new ControladorEventos();
        };
        return instancia;
    }
  
    public Set<DTEdicionEvento> listarEdicionesEvento(String nombreEvento) {
        return Collections.emptySet();
    }

    public Set<DTPatrocinio> listarPatrociniosEdicion(String nombreEdicion, String nombreEvento) {
        return Collections.emptySet();
    }

    public DTDetallePatrocinio mostrarInfoPatrocinio(String codigoPatrocinio) {
        return null;
    }

    public void altaPatrocinio(DTDatosPatrocinio datosPatrocinio) {
        // HACER
    }

    public Set<DTEvento> listarEventos() {
        return Collections.emptySet();
    }

    public Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion) {
        return Collections.emptySet();
    }

    public boolean validar(String nicknameAsistente, String nombreEdicion,
                           String nombreTipoRegistro, String nombreEvento) {
        return false;
    }

    public void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro,
                                  String nombreEdicion, String codigoPatrocinio, LocalDate fecha) {
        // HACER
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
    public void altaEvento(String nombre, String descripcion, LocalDate fechaAlta, String sigla, List<String> categorias) {
		this.manejadorEventos.altaEvento(nombre, descripcion, fechaAlta, sigla, categorias);
    }
    
    /**
	 * Retorna true en caso de que exista un evento e en el sistema tal que e.nombre == nombreEvento
	 * Retorna false en cualquier otro caso.
	 * 
	 * @param nombreEvento
	 */
	public boolean existeEvento(String nombreEvento) {
		return this.manejadorEventos.existeEvento(nombreEvento);
	}
    
    public boolean existeTipoRegistro(String nombre, String nombreEvento, String nombreEdicion) {
    	return false;
    }
    
    public String[] getNombreEventos() {
    	Set<DTEvento> listaEventos = listarEventos(); // Obtengo los DTEvento
		String[] nombreEventos = new String[listaEventos.size()]; // Creo lista vacia de nombres
		
		// Por cada evento, agrego su nombre a la lista
		int i = 0;
		for (DTEvento evento : listaEventos) {
			nombreEventos[i] = evento.getNombre();
		}
		return new String[] {"Ev1", "Ev2"};//nombreEventos;
    }
    
    public String[] getNombresEdiciones(String nombreEvento) {
    	Set<DTEdicionEvento> listaEdiciones = listarEdicionesEvento(nombreEvento);
    	String[] nombreEdiciones= new String[listaEdiciones.size()]; // Creo lista vacia de nombres
		
		// Por cada evento, agrego su nombre a la lista
		int i = 0;
		for (DTEdicionEvento evento : listaEdiciones) {
			nombreEdiciones[i] = evento.getNombre();
		}
		return nombreEdiciones;
    }
    
    public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento, String nombreEdicion) throws NombreTipoRegistroRepetido, DatosTipoRegistroInvalidos {
    	if (costo < 0) throw new DatosTipoRegistroInvalidos("El costo debe ser mayor o igual a cero");
    	if (cuposMax <= 0) throw new DatosTipoRegistroInvalidos("Los cupos máximos deben ser mayores a cero");
    	System.out.print(nombre);
    	if (nombre == "") throw new DatosTipoRegistroInvalidos("El nombre no puede ser vacío");
    	
    	try {
    		manejadorEventos.altaTipoRegistro(nombre, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
    	} catch (NombreTipoRegistroRepetido ex) {
    		throw new NombreTipoRegistroRepetido("El nombre del ya está en otro TipoRegistro de esta edicion");
    	}
    }
    
    public String[] getTiposRegistro(String nombreEvento, String nombreEdicion) {
    	return manejadorEventos.getTiposRegistro(nombreEvento,nombreEdicion);
    }
    
    public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombreTipoRegistro) throws TipoRegistroNoExiste {
    	try {
    		manejadorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro);
    	} catch (TipoRegistroNoExiste ex) {
    		throw new TipoRegistroNoExiste("El Tipo de Registro no existe");
    	};
    	return manejadorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro);
    }

	@Override
	public DTEvento getDTEvento(String nombre) {
		return manejadorEventos.getDTEvento(nombre);
	}

	@Override
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada) {
		return manejadorEventos.getDTEdicion(eventoSeleccionado,edicionSeleccionada);
	}

	public Set<String> nombresEventos(){
		  return  manejadorEventos.getNombresEventos(); 
		}
	
	public Set<String> nombresCategorias(String nombre){
		 return manejadorEventos.getNombresCategorias(nombre);
		}
	
	public Set<String> nombresEdicionesEvento(String nombre) {
		return manejadorEventos.getNombresEdiciones(nombre);
	}

	
	public void AltaEdicionEvento(String nombreEvento, String nombre, String sigla,LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaAlta, String ciudad, String pais) throws Exception {

		if (nombre == null || nombre.isEmpty() ||sigla == null || sigla.isEmpty() ||ciudad == null || ciudad.isEmpty() ||pais == null || pais.isEmpty()) {
			    throw new Exception("Todos los campos deben estar completos");
			}

		if (manejadorEventos.contieneEdicion(nombreEvento,nombre)) {
			throw new Exception("El nombre de la edición ya está en uso");
		}

		LocalDate hoy = LocalDate.now();
		if (fechaInicio.isBefore(hoy) || fechaFin.isBefore(hoy) || fechaAlta.isBefore(hoy)) {
			throw new Exception("Ninguna de las fechas puede estar en el pasado");
		}

		if (fechaFin.isBefore(fechaInicio)) {
			throw new Exception("La fecha de fin no puede ser anterior a la fecha de inicio");
		}

		if (fechaAlta.isAfter(fechaInicio) || fechaAlta.isAfter(fechaFin)) {
			throw new Exception("La fecha de alta no puede ser posterior a la fecha de inicio ni a la fecha de fin");
		}

		if (!ciudad.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
			throw new Exception("La ciudad solo puede contener letras y espacios");
		}

		if (!pais.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
			throw new Exception("El país solo puede contener letras y espacios");
		}

		manejadorEventos.AltaEdicion(nombreEvento, nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais);
	}

	public boolean altaCategoria(String nombre) {
		Categoria cat = new Categoria(nombre);
		categorias.put(nombre, cat);		
		return true;
	}
	public boolean existeCategoria(String nombre) {return categorias.containsKey(nombre);}
	public Map<String, Categoria> listarCategorias(){return categorias;}

	@Override
	public Set<DTTipoRegistro> listarTipoRegistro() {
		// TODO Auto-generated method stub
		return Collections.emptySet();
	}
}
