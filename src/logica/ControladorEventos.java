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
		manejadorEventos.categorias.put(nombre, cat);		
		return true;
	}
	public boolean existeCategoria(String nombre) {return categorias.containsKey(nombre);}
	public Map<String, Categoria> listarCategorias(){return categorias;}

	@Override
	public Set<DTTipoRegistro> listarTipoRegistro() {
		// TODO Auto-generated method stub
		return Collections.emptySet();
	}

	@Override
	public Set<String> getNombresTipoRegistro(String eventoSeleccionado, String edicionSeleccionada) {
		return manejadorEventos.getNombresTipoRegistro(eventoSeleccionado,edicionSeleccionada);
	}

	@Override
	public void AltaPatrocinio(String eventoSeleccionado, String edicionSeleccionada, String tipoRegistroSeleccionado,
			String institucionSeleccionada, String NivelPatrocinio, float aporteEco, int cupos,
			String codigoPatrocinio) throws Exception {
	    
	    // Validación de campos obligatorios
	    if (eventoSeleccionado == null || eventoSeleccionado.isEmpty()
	        || edicionSeleccionada == null || edicionSeleccionada.isEmpty()
	        || tipoRegistroSeleccionado == null || tipoRegistroSeleccionado.isEmpty()
	        || institucionSeleccionada == null || institucionSeleccionada.isEmpty()
	        || NivelPatrocinio == null || NivelPatrocinio.isEmpty()
	        || codigoPatrocinio == null || codigoPatrocinio.isEmpty()) {
	        throw new Exception("Todos los campos deben estar completos");
	    }
	    
	    // Validación del nivel de patrocinio
	    ENivelPatrocinio nivel;
	    try {
	        nivel = ENivelPatrocinio.valueOf(NivelPatrocinio.trim());
	    } catch (IllegalArgumentException ex) {
	        throw new Exception("Nivel de patrocinio inválido");
	    }

	    // Validación del aporte económico
	    if (Float.isNaN(aporteEco) || aporteEco <= 0) {
	        throw new Exception("El aporte económico debe ser un número válido y mayor a cero");
	    }
	    
	    // Validación de duplicado
	    if (manejadorEventos.existePatrocinio(institucionSeleccionada, edicionSeleccionada)) {
	        throw new Exception("Ya existe un patrocinio de esa institución para la edición seleccionada");
	    }

	    // Validación de cantidad de cupos
	    if (cupos < 0) {
	        throw new Exception("La cantidad de registros gratuitos no puede ser negativa");
	    }

	    // Validación del 20% del aporte
	    float valorCupo = manejadorEventos.eventos.get(eventoSeleccionado).getEdicion(edicionSeleccionada).getTipoRegistro(tipoRegistroSeleccionado).getCosto(); 
	    float costoTotalGratuitos = cupos * valorCupo;
	    float limitePermitido = aporteEco * 0.2f;
	    if (costoTotalGratuitos > limitePermitido) {
	        throw new Exception("El costo de los registros gratuitos supera el 20% del aporte económico");
	    }

	    // Validación del formato del código de patrocinio (alfanumérico)
	    if (!codigoPatrocinio.matches("[a-zA-Z0-9]+")) {
	        throw new Exception("El código de patrocinio solo puede contener letras y números");
	    }
		manejadorEventos.AltaPatrocinio(eventoSeleccionado,edicionSeleccionada,tipoRegistroSeleccionado,institucionSeleccionada,nivel,
				aporteEco,cupos,codigoPatrocinio);
	}
}
