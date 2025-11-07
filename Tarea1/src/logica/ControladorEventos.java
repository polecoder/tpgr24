package logica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import excepciones.AporteInsuficiente;
import excepciones.AsistenteYaRegistrado;
import excepciones.DatosTipoRegistroInvalidos;
import excepciones.EdicionFinalizada;
import excepciones.EdicionNoExiste;
import excepciones.EdicionYaArchivada;
import excepciones.EventoFinalizado;
import excepciones.EventoNoExiste;
import excepciones.FechaFinEsPasada;
import excepciones.FormularioIncompletoEdicion;
import excepciones.FormularioIncompletoPatrocinio;
import excepciones.NoHayCuposDisponibles;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.PatrocinioInvalido;
import excepciones.TipoRegistroNoExiste;
import excepciones.UsuarioNoExiste;
import excepciones.YaAsisteAEdicion;
import persistencia.EdicionArchivada;
import persistencia.ManejadorPersistencia;
import persistencia.RegistroArchivado;
import datatypes.DTDetallePatrocinio;
import datatypes.DTDetalleRegistro;
import datatypes.DTEdicionEvento;
import datatypes.DTEvento;
import datatypes.DTEventoVisitas;
import datatypes.DTTipoRegistro;
import datatypes.EEstadoEdicion;
import datatypes.EEstadoEvento;
import datatypes.ENivelPatrocinio;

public class ControladorEventos implements IControladorEventos {
	private static ControladorEventos instancia;
	private ManejadorEventos manejadorEventos;
	private ManejadorUsuarios manejadorUsuarios;
	private ManejadorPersistencia manejadorPersistencia;

	private ControladorEventos() {
		this.manejadorEventos = ManejadorEventos.getInstancia();
		this.manejadorUsuarios = ManejadorUsuarios.getInstance();
		this.manejadorPersistencia = ManejadorPersistencia.getInstance();
	}

	public static ControladorEventos getInstance() {
		if (instancia == null) {
			instancia = new ControladorEventos();
		}
		return instancia;
	}

	public static void resetInstance() {
		ManejadorEventos.resetInstancia();
		ManejadorUsuarios.resetInstance();
		instancia = null;
	}

	public List<String> getEdicionesDeOrganizadorPorEstado(String organizadorNickname, EEstadoEdicion... estados) {
		Set<EEstadoEdicion> filtro = new HashSet<>(Arrays.asList(estados));
		return ManejadorEventos.getInstancia().filtrarEdicionesPorOrganizadorYEstado(organizadorNickname, filtro);
	}

	public Map<String, DTEdicionEvento> listarEdicionesEvento(String nombreEvento) {
		Evento asistente = manejadorEventos.getEvento(nombreEvento);
		Map<String, Edicion> eds = asistente.getEdiciones();
		Map<String, DTEdicionEvento> mLista = new HashMap<>();

		for (Edicion ed : eds.values()) {
			String nombre = ed.getNombre();
			String sigla = ed.getSigla();
			Date fInicio = ed.getFechaInicio();
			Date fFin = ed.getFechaFin();
			Date fAlta = ed.getFechaAlta();
			String ciudad = ed.getCiudad();
			String pais = ed.getPais();
			String oNick = ed.getOrganizadorNickname();
			byte[] imagen = ed.getImagen();
			EEstadoEdicion estado = ed.getEstado();
			String videoURL = ed.getVideoURL();

			mLista.put(nombre, new DTEdicionEvento(nombre, sigla, fInicio, fFin, fAlta, ciudad, pais, oNick, imagen,
					estado, videoURL));
		}

		return mLista;
	}

	public Map<String, DTEvento> listarEventos() {
		return manejadorEventos.listarEventos();
	}

	public Map<String, DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion) {
		return manejadorEventos.listarTipoRegistro(nombreEvento, nombreEdicion);
	}

	public boolean validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro,
			String nombreEvento) throws AsistenteYaRegistrado, NoHayCuposDisponibles {
		try {
			boolean ret = false;
			Evento evento = manejadorEventos.getEvento(nombreEvento);
			Edicion edicion = evento.getEdicion(nombreEdicion);
			ret = edicion.validar(nicknameAsistente, nombreTipoRegistro);
			return ret;
		} catch (AsistenteYaRegistrado | NoHayCuposDisponibles exception) {
			throw exception;
		}
	}

	/*
	 * public void confirmarRegistro(String nicknameAsistente, String
	 * nombreTipoRegistro, String nombreEdicion, String codigoPatrocinio, LocalDate
	 * fecha) { // HACER }
	 */ // Reemplazada por "altaRegistro()"

	/**
	 * PRE-CONDICIÓN: Los datos ingresados por parámetro están formateados
	 * correctamente. Da de alta un Evento con los parámetros indicados en el
	 * sistema. Si ya existe un Evento con el nombre indicado en el sistema, no hace
	 * nada.
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param fechaAlta
	 * @param sigla
	 * @param categorias
	 */
    public void altaEvento(String nombre, String descripcion, Date fechaAlta, String sigla, List<String> categorias, byte[] imagen, EEstadoEvento estado, int visitas) {
    	this.manejadorEventos.altaEvento(nombre, descripcion, fechaAlta, sigla, categorias, imagen, estado, visitas);
    } 

	/**
	 * Retorna true en caso de que exista un evento e en el sistema tal que e.nombre
	 * == nombreEvento Retorna false en cualquier otro caso.
	 * 
	 * @param nombreEvento
	 */
	public boolean existeEvento(String nombreEvento) {
		return this.manejadorEventos.existeEvento(nombreEvento);
	}

	@Override
	public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento,
			String nombreEdicion) throws NombreTipoRegistroRepetido, DatosTipoRegistroInvalidos {
		if (costo < 0)
			throw new DatosTipoRegistroInvalidos("El costo debe ser mayor o igual a cero");
		if (cuposMax <= 0)
			throw new DatosTipoRegistroInvalidos("Los cupos máximos deben ser mayores a cero");
		if (nombre == "")
			throw new DatosTipoRegistroInvalidos("El nombre no puede ser vacío");

		try {
			manejadorEventos.altaTipoRegistro(nombre, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
		} catch (NombreTipoRegistroRepetido ex) {
			throw new NombreTipoRegistroRepetido("El nombre del ya está en otro TipoRegistro de esta edicion");
		}
	}

	@Override
	public String[] getTiposRegistro(String nombreEvento, String nombreEdicion) throws EventoNoExiste, EdicionNoExiste {
		try {
			return manejadorEventos.getTiposRegistro(nombreEvento, nombreEdicion);
		} catch (EventoNoExiste | EdicionNoExiste ex) {
			throw ex;
		}
	}

	@Override
	public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombreTipoRegistro)
			throws TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste {
		try {
			return manejadorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro);
		} catch (TipoRegistroNoExiste | EventoNoExiste | EdicionNoExiste exception) {
			throw exception;
		}
	}

	@Override
	public void altaPatrocinio(String eventoSeleccionado, String edicionSeleccionada, Date FechaAlta,
			String institucionSeleccionada, ENivelPatrocinio nivelPatrocinio, float aporteEconomico, int cupos,
			String codigoPatrocinio, String tipoRegistroSeleccionado) throws FormularioIncompletoPatrocinio,
			AporteInsuficiente, TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste, EdicionFinalizada {

		// Validación de campos obligatorios
		if (eventoSeleccionado == null || eventoSeleccionado.isEmpty() || edicionSeleccionada == null
				|| edicionSeleccionada.isEmpty() || institucionSeleccionada == null || institucionSeleccionada.isEmpty()
				|| nivelPatrocinio == null || codigoPatrocinio == null || codigoPatrocinio.isEmpty()) {
			throw new FormularioIncompletoPatrocinio("Todos los campos deben estar completos");
		}

		if (!manejadorEventos.existeEvento(eventoSeleccionado)) {
			throw new FormularioIncompletoPatrocinio("El evento seleccionado no existe en el sistema.");
		}

		if (manejadorEventos.getEdicion(eventoSeleccionado, edicionSeleccionada) == null) {
			throw new FormularioIncompletoPatrocinio("La edición seleccionada no existe en el sistema.");
		}

		// Validación de duplicado
		if (manejadorEventos.existePatrocinio(institucionSeleccionada, edicionSeleccionada)) {
			throw new FormularioIncompletoPatrocinio(
					"Ya existe un patrocinio de esa institución para la edición seleccionada");
		}

		// Validación del formato del código de patrocinio (alfanumérico)
		if (!codigoPatrocinio.matches("[a-zA-Z0-9]+")) {
			throw new FormularioIncompletoPatrocinio("El código de patrocinio solo puede contener letras y números");
		}

		// La edición sigue vigente
		Edicion edicion = manejadorEventos.getEdicion(eventoSeleccionado, edicionSeleccionada);
		if (edicion.getFechaFin().before(new Date())) {
			throw new EdicionFinalizada("La edición seleccionada ya ha finalizado.");
		}

		// Validacion del 20%
		float costoTipoRegistro = getDTTipoRegistro(eventoSeleccionado, edicionSeleccionada, tipoRegistroSeleccionado)
				.costo();
		if (aporteEconomico * 0.20 < costoTipoRegistro * cupos) {
			throw new AporteInsuficiente("El aporte debe superar el 20% del costo del total de los cupos");
		}

		try {
			manejadorEventos.altaPatrocinio(eventoSeleccionado, FechaAlta, edicionSeleccionada, institucionSeleccionada,
					nivelPatrocinio, aporteEconomico, cupos, codigoPatrocinio, tipoRegistroSeleccionado);
		} catch (TipoRegistroNoExiste exception) {
			exception.printStackTrace(); // no llega nunca a esta línea
		}
	}

	@Override
	public DTEvento getDTEvento(String nombre) {
		return manejadorEventos.getDTEvento(nombre);
	}

	@Override
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada) {
		// chequeo primero que la edición no esté archivada
		EdicionArchivada edicionArchivada = manejadorPersistencia.getEdicionEnArchivo(edicionSeleccionada);
		if (edicionArchivada != null) return edicionArchivada.getDatosEdicion();
		
		return manejadorEventos.getDTEdicion(eventoSeleccionado, edicionSeleccionada);
	}

	public Set<String> nombresEventos() {
		return manejadorEventos.getNombresEventos();
	}

	public Set<String> nombresCategorias(String nombre) {
		return manejadorEventos.getNombresCategorias(nombre);
	}

	public Set<String> nombresEdicionesEvento(String nombre) throws EventoNoExiste {
		try {
			return manejadorEventos.getNombresEdiciones(nombre);
		} catch (EventoNoExiste ex) {
			throw ex;
		}
	}

	public void altaEdicionEvento(String nombreEvento, String nombre, String sigla, Date fechaInicio, Date fechaFin,
			Date fechaAlta, String ciudad, String pais, String organizadorNickname, byte[] img, String URL)
			throws FormularioIncompletoEdicion, EventoFinalizado, EdicionYaArchivada {
		// devuelvo error si ya existe una edición con este nombre en el archivo
		if (manejadorPersistencia.getEdicionEnArchivo(nombre) != null) throw new EdicionYaArchivada("Ya existe una edición con este nombre en el archivo.");
		
		
		if (manejadorEventos.getEvento(nombreEvento).isFinalizado())
			throw new EventoFinalizado("No se pueden crear ediciones para un evento finalizado");

		if (nombre == null || nombre.isEmpty() || sigla == null || sigla.isEmpty() || ciudad == null || ciudad.isEmpty()
				|| pais == null || pais.isEmpty()) {
			throw new FormularioIncompletoEdicion("Todos los campos deben estar completos");
		}

		if (manejadorEventos.existeEdicion(nombre)) {
			throw new FormularioIncompletoEdicion("El nombre de la edición ya está en uso");
		}

		if (fechaFin.before(fechaInicio)) {
			throw new FormularioIncompletoEdicion("La fecha de fin no puede ser anterior a la fecha de inicio");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String alta = sdf.format(fechaAlta);
		String inicio = sdf.format(fechaInicio);
		String fin = sdf.format(fechaFin);

		if (alta.compareTo(inicio) > 0 || alta.compareTo(fin) > 0) {
			throw new FormularioIncompletoEdicion("La fecha de inicio no puede ser posterior a la fecha actual");
		}

		if (!ciudad.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
			throw new FormularioIncompletoEdicion("La ciudad solo puede contener letras y espacios");
		}

		if (!pais.matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]+$")) {
			throw new FormularioIncompletoEdicion("El país solo puede contener letras y espacios");
		}

		String videoURL = null;
		if (URL != null) {
			videoURL = URL.replace("watch?v=", "embed/").replace("&", "?");
		}

		Edicion edicion = manejadorEventos.altaEdicion(nombreEvento, nombre, sigla, fechaInicio, fechaFin, fechaAlta,
				ciudad, pais, organizadorNickname, img, videoURL);
		this.manejadorUsuarios.agregarEdicionAOrganizador(edicion, organizadorNickname);
	}

	public void altaCategoria(String nombre) throws Exception {
		if (manejadorEventos.existeCategoria(nombre)) {
			throw new Exception("La Categoría ya existe");
		}
		this.manejadorEventos.altaCategoria(nombre);
	}

	public boolean existeCategoria(String nombre) {
		return this.manejadorEventos.existeCategoria(nombre);
	}

	public Map<String, Categoria> listarCategorias() {
		return this.manejadorEventos.getCategorias();
	}

		public void altaRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String nombreEvento, String codigoPatrocinio, Date fecha) throws EventoNoExiste, EdicionNoExiste, FechaFinEsPasada, TipoRegistroNoExiste, UsuarioNoExiste, PatrocinioInvalido, YaAsisteAEdicion{
			Evento evento = manejadorEventos.getEvento(nombreEvento);
			if (evento == null) { throw new EventoNoExiste("El evento no existe"); }
			
			Edicion edicion = evento.getEdicion(nombreEdicion);
			if (edicion == null) { throw new EdicionNoExiste("La edición no existe"); }
			
			if (edicion.yaAsiste(nicknameAsistente)) { throw new YaAsisteAEdicion("Ya está registrado a esta edición."); }
			if (edicion.getFechaFin().before(new Date())) { throw new FechaFinEsPasada("La edición ya terminó, no puedes registrarte."); }
			
			Asistente asistente = manejadorUsuarios.getAsistente(nicknameAsistente);
			if (asistente == null) { throw new UsuarioNoExiste("El asistente no existe"); }
			
			TipoRegistro tipoRegistro = edicion.getTipoRegistro(nombreTipoRegistro); // Si no existe hace el throw
			
			float costo = tipoRegistro.getCosto();
			
			Patrocinio patrocinio = null;
			if (codigoPatrocinio != null && !codigoPatrocinio.isBlank()) {
				boolean patrocinioValido = edicion.patrocinioValido(codigoPatrocinio, nicknameAsistente, nombreTipoRegistro);
				if (patrocinioValido) {
					patrocinio = edicion.getPatrocinio(codigoPatrocinio);
					patrocinio.usarCupo();
					costo = 0f;
				}
			}
			this.manejadorEventos.altaRegistro(asistente, tipoRegistro, patrocinio, new Date(), costo,edicion);
		}
	public Set<String> listarRegistrosAEdicion(String nombreEvento, String nombreEdicion) {
		return manejadorEventos.listarRegistrosAEdicion(nombreEvento, nombreEdicion);
	}

	public Set<String> listarPatrociniosDeEdicion(String nombreEvento, String nombreEdicion) {
		return manejadorEventos.listarPatrociniosDeEdicion(nombreEvento, nombreEdicion);
	}

	@Override
	public List<String> getListaCategorias() {
		List<String> resultado = new ArrayList<>();
		for (String nombreCategoria : this.manejadorEventos.getCategorias().keySet()) {
			resultado.add(nombreCategoria);
		}
		return resultado;
	}

	public String[] getNombresEdiciones(String nombreEvento) {
		Map<String, DTEdicionEvento> listaEdiciones = listarEdicionesEvento(nombreEvento);
		String[] nombreEdiciones = new String[listaEdiciones.size()]; // Creo lista vacia de nombres

		// Por cada evento, agrego su nombre a la lista
		int contador = 0;
		for (String edicion : listaEdiciones.keySet()) {
			nombreEdiciones[contador] = edicion;
		}
		return nombreEdiciones;
	}

	@Override
	public Set<String> getNombresTipoRegistro(String eventoSeleccionado, String edicionSeleccionada) {
		if (manejadorPersistencia.getEdicionEnArchivo(edicionSeleccionada) != null) return null;
		
		return manejadorEventos.getNombresTipoRegistro(eventoSeleccionado, edicionSeleccionada);
	}

	@Override
	public Set<String> nombresEdicionesIngresadas(String nombreEvento) {
		Set<String> listaEdicionesIngresadas = manejadorEventos.getNombresEdicionesIngresadas(nombreEvento);
		return listaEdicionesIngresadas;
	}

	@Override
	public void setEstadoEdicion(String eventoSeleccionado, String edicionSeleccionada, boolean bool) {
		manejadorEventos.setEstadoEdicion(eventoSeleccionado, edicionSeleccionada, bool);
	}

	@Override
	public DTDetallePatrocinio mostrarDetallePatrocinio(String nombreEvento, String nombreEdicion,
			String codigoPatrocinio) {
		Edicion edicion = this.manejadorEventos.getEdicion(nombreEvento, nombreEdicion);
		if (edicion != null) {
			Patrocinio patrocinio = edicion.getPatrocinio(codigoPatrocinio);
			TipoRegistro tiporeg = patrocinio.getTipoRegistro();
			if (patrocinio != null)
				return new DTDetallePatrocinio(patrocinio.getInstitucion().getNombre(), codigoPatrocinio,
						patrocinio.getFechaPatrocinio(), patrocinio.getAporte(), patrocinio.getNivel(),
						patrocinio.getCuposGratuitos(), tiporeg.getNombre());
		}
		return null;
	}

	@Override
	public Set<DTDetalleRegistro> listarDTRegistrosAEdicion(String nombreEvento, String nombreEdicion) {
		EdicionArchivada edicionArchivada = manejadorPersistencia.getEdicionEnArchivo(nombreEdicion);
		// si la edición NO está archivada, la busco en el sistema y la devuelvo
		if (edicionArchivada == null) return manejadorEventos.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion);
		
		// la edición está archivada
		Set<DTDetalleRegistro> resultado = new HashSet<>();
		for (RegistroArchivado registro: edicionArchivada.getRegistros()) {
			DTDetalleRegistro detalle = registro.getDatosRegistro();
			resultado.add(detalle);
		}
		
		return resultado;
	}

	@Override
	public Set<String> listarPatrocinadores(String nombreEvento, String nombreEdicion) {
		if (manejadorPersistencia.getEdicionEnArchivo(nombreEdicion) != null) return null;
		
		return manejadorEventos.listarPatrocinadores(nombreEvento, nombreEdicion);
	}

	@Override
	public Patrocinio getPatrocinio(String nombreEvento, String nombreEdicion, String patrocinador) {
		return manejadorEventos.getPatrocinio(nombreEvento, nombreEdicion, patrocinador);
	}

	public void finalizarEvento(String nombreEvento) throws EventoNoExiste, EventoFinalizado {
		Evento ev = manejadorEventos.getEvento(nombreEvento);
		if (ev.isFinalizado()) {
			throw new EventoFinalizado("El evento ya finalizo");
		}
		ev.finalizar();
	}

	public Set<DTEvento> listarEventosPorEstado(EEstadoEvento estado) {
		Set<DTEvento> resultado = new HashSet<>();
		Collection<Evento> todoslosEventos = manejadorEventos.getEventos().values();
		for (Evento ev : todoslosEventos) {
			if (ev.getEstado() == estado) {
				DTEvento dtEv = this.getDTEvento(ev.getNombre());
				resultado.add(dtEv);
			}
		}
		return resultado;
	}

	public Set<DTEvento> listarEventosEnCurso() {
		return listarEventosPorEstado(EEstadoEvento.EN_CURSO);
	}

public Set<DTEvento> listarEventosFinalizados() {
    return listarEventosPorEstado(EEstadoEvento.FINALIZADO);
}

public List<DTEventoVisitas> obtenerEventosMasVisitados(int limite) {
	limite = 5;
	
	List<DTEventoVisitas> resultado = new ArrayList<>();

    Map<String, Evento> mapaEventos = manejadorEventos.getEventos();
    List<Evento> listaEventos = new ArrayList<>();

    for (String nombre : mapaEventos.keySet()) {
        listaEventos.add(mapaEventos.get(nombre));
    }

    // ordenar
    for (int i = 0; i < listaEventos.size() - 1; i++) {
        for (int j = 0; j < listaEventos.size() - i - 1; j++) {
            Evento e1 = listaEventos.get(j);
            Evento e2 = listaEventos.get(j + 1);

            if (e1.getVisitas() < e2.getVisitas()) {
                listaEventos.set(j, e2);
                listaEventos.set(j + 1, e1);
            } else if (e1.getVisitas() == e2.getVisitas()) {
                // si empatan en visitas, ordeno por nombre alfabéticamente
                if (e1.getNombre().compareToIgnoreCase(e2.getNombre()) > 0) {
                    listaEventos.set(j, e2);
                    listaEventos.set(j + 1, e1);
                }
            }
        }
    }

    // tomo los primeros 5, lo podemos cambiar por otro numero
    int contador = 0;
    for (Evento e : listaEventos) {
        if (contador >= limite) {
            break;
        }
        resultado.add(new DTEventoVisitas(e.getNombre(), e.getVisitas()));
        contador++;
    }

    return resultado;
}
/*public DTEvento consultarEvento(String nombre) {
	return manejadorEventos.consultarEvento(nombre);
}*/

public int registrarVisitaEvento(String nombre) {
	return manejadorEventos.registrarVisitaEvento(nombre);
}
	
	@Override
	public void archivarEdicion(String nombreEvento, String nombreEdicion, Date fechaArchivo) throws EdicionFinalizada, EdicionNoExiste, EdicionYaArchivada {
		Edicion edicion = this.manejadorEventos.getEdicion(nombreEvento, nombreEdicion);
		// si la edición ya está archivada
		if (manejadorPersistencia.getEdicionEnArchivo(nombreEdicion) != null) throw new EdicionYaArchivada("La edición seleccionada ya está archivada.");
		// si la edición no existe
		if (edicion == null) throw new EdicionNoExiste("La edición seleccionada no existe en el sistema");
		// si la edición ya finalizó
		if (edicion.getFechaFin().before(new Date())) throw new EdicionFinalizada("La edición seleccionada ya finalizó");
		
		// archivo la edición desde el manejador
		this.manejadorPersistencia.archivarEdicion(nombreEvento, nombreEdicion, fechaArchivo);
		// elimino la edición del sistema si todo sale bien
		this.eliminarEdicion(nombreEvento, nombreEdicion);
	}
	
	@Override
	public void eliminarEdicion(String nombreEvento, String nombreEdicion) throws EdicionNoExiste {
		Edicion edicion = this.manejadorEventos.getEdicion(nombreEvento, nombreEdicion);
		// si la edición no existe
		if (edicion == null) throw new EdicionNoExiste("La edición seleccionada no existe en el sistema");
		
		DTEdicionEvento datosEdicion = this.manejadorEventos.getDTEdicion(nombreEvento, nombreEdicion);
		
		// eliminar links de organizador
		Organizador organizador = this.manejadorUsuarios.getOrganizador(datosEdicion.getOrganizadorNickname()); // el organizador tiene que existir en el sistema
		organizador.eliminarEdicion(edicion);
		
		// eliminar links de evento
		Evento evento = this.manejadorEventos.getEvento(nombreEvento); // el evento tiene que existir en el sistema
		evento.eliminarEdicion(nombreEdicion);
		
		// elimino los links con todas las instituciones
		Map<String, Patrocinio> patrocinios = edicion.getPatrocinios();
		for (Patrocinio patrocinio: patrocinios.values()) {
			Institucion institucion = patrocinio.getInstitucion();
			institucion.eliminarEdicionPatrocinada(nombreEdicion);
		}
	}
	
	@Override
	public List<String> listarEdicionesArchivadasDeOrganizador(String nicknameOrganizador) {
		// obtengo las ediciones
		List<EdicionArchivada> ediciones = manejadorPersistencia.getEdicionesArchivadasDeOrganizador(nicknameOrganizador);
		if (ediciones == null) return null;
		
		// preparo el resultado
		List<String> resultado = new ArrayList<>();
		for (EdicionArchivada edicion: ediciones) {
			DTEdicionEvento detalleEdicion = edicion.getDatosEdicion();
			String nombreEdicion = detalleEdicion.getNombre();
			resultado.add(nombreEdicion);
		}
		return resultado;
	}
}
