package logica;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import excepciones.DatosTipoRegistroInvalidos;
import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.datatypes.DTDatosPatrocinio;
import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;
import logica.datatypes.DTPatrocinio;
import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTDatosPatrocinio;
import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

public class ControladorEventos implements IControladorEventos {
	private static ControladorEventos instancia;
	private ManejadorEventos manejadorEventos;
	private ManejadorUsuarios manejadorUsuarios;
	private IControladorInstitucion interfazInstitucion;

	private ControladorEventos() {
		this.manejadorEventos = ManejadorEventos.getInstancia();
		this.interfazInstitucion = ControladorInstitucion.getInstance();
		this.manejadorUsuarios = ManejadorUsuarios.getInstance();
	}

	public static ControladorEventos getInstance() {
		if (instancia == null) {
			instancia = new ControladorEventos();
		};
		return instancia;
	}

	public Map<String,DTEdicionEvento> listarEdicionesEvento(String nombreEvento) {
    	Evento ev = manejadorEventos.getEvento(nombreEvento);
    	Map<String, Edicion> eds = ev.getEdiciones();
    	Map<String, DTEdicionEvento> mLista = new HashMap<>();
    	for(Edicion ed : eds.values()) {
    		String nombre = ed.getNombre();
    		String sigla = ed.getSigla();
    		Date fInicio = ed.getFechaInicio();
    		Date fFin = ed.getFechaFin();
    		Date fAlta = ed.getFechaAlta();
    		String ciudad = ed.getCiudad();
    		String pais = ed.getPais();
    		String oNick = ed.getOrganizadorNickname();
    		mLista.put(nombre, new DTEdicionEvento(nombre,sigla,fInicio, fFin, fAlta, ciudad, pais, oNick));
    	}
    	return mLista;
    }

	public Set<DTPatrocinio> listarPatrociniosEdicion(String nombreEdicion, String nombreEvento) {
		return null;
	}

	public DTDetallePatrocinio mostrarInfoPatrocinio(String codigoPatrocinio) {
		return null;
	}

	public void altaPatrocinio(DTDatosPatrocinio datosPatrocinio) {
		// HACER
	}

    public Map<String,DTEvento> listarEventos() {
        return manejadorEventos.listarEventos();
    }

	public Map<String,DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion) {
		return manejadorEventos.listarTipoRegistro(nombreEvento, nombreEdicion);
	}

    public boolean validar(String nicknameAsistente, String nombreEdicion,
                           String nombreTipoRegistro, String nombreEvento) {
        Evento ev = manejadorEventos.getEvento(nombreEvento);
        Edicion ed = ev.getEdicion(nombreEdicion);
        return ed.validar(nicknameAsistente, nombreTipoRegistro);
    }

	/* public void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro,
			String nombreEdicion, String codigoPatrocinio, LocalDate fecha) {
		// HACER
	}*/ // Reemplazada por "altaRegistro()"



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
	public void altaEvento(String nombre, String descripcion, Date fechaAlta, String sigla, List<String> categorias) {
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

	@Override
	public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento, String nombreEdicion) throws NombreTipoRegistroRepetido, DatosTipoRegistroInvalidos {
		if (costo < 0) throw new DatosTipoRegistroInvalidos("El costo debe ser mayor o igual a cero");
		if (cuposMax <= 0) throw new DatosTipoRegistroInvalidos("Los cupos máximos deben ser mayores a cero");
		if (nombre == "") throw new DatosTipoRegistroInvalidos("El nombre no puede ser vacío");

		try {
			manejadorEventos.altaTipoRegistro(nombre, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
		} catch (NombreTipoRegistroRepetido ex) {
			throw new NombreTipoRegistroRepetido("El nombre del ya está en otro TipoRegistro de esta edicion");
		}
	}

	public String[] getTiposRegistro(String nombreEvento, String nombreEdicion) throws EventoNoExiste, EdicionNoExiste {
    	try { return manejadorEventos.getTiposRegistro(nombreEvento,nombreEdicion);
    	} catch (EventoNoExiste ex) { throw ex;
    	} catch (EdicionNoExiste ex) { throw ex;
    	}
    }

	@Override
	public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombreTipoRegistro) throws TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste {
    	try {
    		manejadorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro);
    	} catch (TipoRegistroNoExiste ex) { throw ex;
    	} catch (EventoNoExiste ex) { throw ex; 
    	} catch (EdicionNoExiste ex) { throw ex;
    	}
    	return manejadorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro);
    }

	@Override
	public void altaPatrocinio(String codigoPatrocinio, Date fechaPatrocinio, float aporte, ENivelPatrocinio nivel, int cuposGratuitos, String nombreInstitucion, String nombreEvento,String nombreEdicion) {
		Institucion institucion = this.interfazInstitucion.getInstitucion(nombreInstitucion);
		Edicion edicion = this.manejadorEventos.getEdicion(nombreEvento, nombreEdicion);
		this.manejadorEventos.altaPatrocinio(codigoPatrocinio, fechaPatrocinio, aporte, nivel, cuposGratuitos, institucion, edicion);
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
	
	public Set<String> nombresEdicionesEvento(String nombre) throws EventoNoExiste {
		try {
			return manejadorEventos.getNombresEdiciones(nombre);
		} catch (EventoNoExiste ex) {
			throw ex;
		}
	}


	public void altaEdicionEvento(String nombreEvento, String nombre, String sigla,Date fechaInicio, Date fechaFin, Date fechaAlta, String ciudad, String pais, String organizadorNickname) throws Exception {

		if (nombre == null || nombre.isEmpty() ||sigla == null || sigla.isEmpty() ||ciudad == null || ciudad.isEmpty() ||pais == null || pais.isEmpty()) {
			throw new Exception("Todos los campos deben estar completos");
		}

		if (manejadorEventos.contieneEdicion(nombreEvento,nombre)) {
			throw new Exception("El nombre de la edición ya está en uso");
		}

		if (fechaFin.before(fechaInicio)) {
			throw new Exception("La fecha de fin no puede ser anterior a la fecha de inicio");
		}

		if (fechaAlta.after(fechaInicio) || fechaAlta.after(fechaFin)) {
			throw new Exception("La fecha de alta no puede ser posterior a la fecha de inicio ni a la fecha de fin");
		}

		if (!ciudad.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
			throw new Exception("La ciudad solo puede contener letras y espacios");
		}

		if (!pais.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
			throw new Exception("El país solo puede contener letras y espacios");
		}

		manejadorEventos.altaEdicion(nombreEvento, nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, organizadorNickname);
	}

		public void altaCategoria(String nombre) {
			this.manejadorEventos.altaCategoria(nombre);		
		}
		public boolean existeCategoria(String nombre) {return this.manejadorEventos.existeCategoria(nombre);}
		public Map<String, Categoria> listarCategorias() {return this.manejadorEventos.getCategorias();}

	public void altaRegistro(String nicknameAsistente, String nombreTipoRegistro,
			String nombreEdicion, String nombreEvento, String codigoPatrocinio, Date fecha) {
		try {
			Evento ev = manejadorEventos.getEvento(nombreEvento);
			Edicion ed = ev.getEdicion(nombreEdicion);
			TipoRegistro tr = ed.getTipoRegistro(nombreTipoRegistro);
			int cupo = tr.getCuposDisponibles();
			tr.setCuposDisponibles(cupo-1);
			float cost = tr.getCosto();
			Asistente as = manejadorUsuarios.getAsistente(nicknameAsistente);
			Patrocinio p = null;
			boolean checkEd = ed.check(codigoPatrocinio, nicknameAsistente);
			if(checkEd) {
				p = ed.getPatrocinio(codigoPatrocinio);
				int cuposP = p.getCuposGratuitos();
				p.setCuposGratuitos(cuposP-1);
				cost = 0;
			}
			this.manejadorEventos.altaRegistro(as, tr, p, fecha, cost);
		} catch(Exception CRError) {

		}
	}
	public Set<String> listarRegistrosAEdicion(String nombreEvento, String nombreEdicion) {
    	return manejadorEventos.listarRegistrosAEdicion(nombreEvento, nombreEdicion);
    }
	public Set<String> listarPatrociniosDeEdicion(String nombreEvento, String nombreEdicion) {
    	return manejadorEventos.listarPatrociniosDeEdicion(nombreEvento, nombreEdicion);
    }
}
