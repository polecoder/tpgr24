package logica;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
import datatypes.DTDetallePatrocinio;
import datatypes.DTDetalleRegistro;
import datatypes.DTEdicionEvento;
import datatypes.DTEvento;
import datatypes.DTEventoVisitas;
import datatypes.DTTipoRegistro;
import datatypes.EEstadoEdicion;
import datatypes.EEstadoEvento;
import datatypes.ENivelPatrocinio;

public interface IControladorEventos {
	public Map<String, DTEvento> listarEventos();
	public Map<String, DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion);
	public boolean validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro, String nombreEvento) throws AsistenteYaRegistrado, NoHayCuposDisponibles;
	// public void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String codigoPatrocinio, LocalDate fecha); // Reemplazado por "altaRegistro()"
	public Map<String, DTEdicionEvento> listarEdicionesEvento(String nombreEvento);
	public Set<String> nombresEventos();
	public DTEvento getDTEvento(String nombre);
	/*public DTEvento consultarEvento(String nombre);*/
	int registrarVisitaEvento(String nombre); 
	public Set<String> nombresCategorias(String nombre);
	public Set<String> nombresEdicionesEvento(String nombre) throws EventoNoExiste;
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada);
	public void altaEdicionEvento(String nombreEvento, String nombre, String sigla, Date fechaInicio, Date fechaFin, Date fechaAlt, String ciudad, String pais, String organizadorNickname, byte[] img, String URL) throws FormularioIncompletoEdicion, EventoFinalizado, EdicionYaArchivada;
	// public String[] getNombresEdiciones(String itemAt); // Reemplazada por "nombresEdicionesEvento()"
	public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento, String nombreEdicion)throws NombreTipoRegistroRepetido, DatosTipoRegistroInvalidos;
	public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombre) throws TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste;
	public void altaCategoria(String nombre) throws Exception;
	public boolean existeCategoria(String nombre);
	/**
	 * TODO: Tendría que estar en ControladorUsuarios<p>
	 * Devuelve todas las ediciones con estados iguales a los pasados por parámetro. Acepta más de un estado.<p>
	 * 
	 * @param organizadorNickname
	 * @param estados
	 * @return
	 */
	List<String> getEdicionesDeOrganizadorPorEstado(String organizadorNickname,
            EEstadoEdicion... estados);

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
	 * @param imagen
	 */
	public void altaEvento(String nombre, String descripcion, Date fechaAlta, String sigla, List<String> categorias, byte[] imagen, EEstadoEvento estado, int visitas);
	/**
	 * Retorna true en caso de que exista un evento e en el sistema tal que e.nombre == nombreEvento
	 * Retorna false en cualquier otro caso.
	 * 
	 * @param nombreEvento
	 */
	public boolean existeEvento(String nombreEvento);
	public void altaRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String nombreEvento, String codigoPatrocinio, Date fecha) throws EventoNoExiste, EdicionNoExiste, FechaFinEsPasada, TipoRegistroNoExiste, UsuarioNoExiste, PatrocinioInvalido, YaAsisteAEdicion;
	public Set<String> listarRegistrosAEdicion(String selectedEvento, String selectedEdicion);
	public Set<String> listarPatrociniosDeEdicion(String selectedEvento, String selectedEdicion);
	/**
	 * Retorna el nombre de los tipos de registro para la edición pasada por parámetro.<p>
	 * Si la edición está archivada, retorna null.
	 * 
	 * @param eventoSeleccionado
	 * @param edicionSeleccionada
	 * @return un set con todos los nombres de los tipos de registro o null si la edición está archivada
	 */
	public Set<String> getNombresTipoRegistro(String eventoSeleccionado, String edicionSeleccionada);
	public void altaPatrocinio(String eventoSeleccionado,  String edicionSeleccionada, Date FechaAlta, String institucionSeleccionada, ENivelPatrocinio nivelPatrocinio, float aporteEconomico, int cupos, String codigoPatrocinio, String tipoRegistroSeleccionado) throws FormularioIncompletoPatrocinio, AporteInsuficiente, TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste, EdicionFinalizada;
	public String[] getNombresEdiciones(String nombreEvento);
	
	/**
	 * Devuelve una lista que contienen el nombre de todas las categorías
	 * 
	 * @return
	 */
	public List<String> getListaCategorias();
	public String[] getTiposRegistro(String nombreEvento, String nombreEdicion) throws EventoNoExiste, EdicionNoExiste;
	public Set<String> nombresEdicionesIngresadas(String eventoSeleccionado);
	public void setEstadoEdicion(String eventoSeleccionado, String edicionSeleccionada, boolean bool);
	/**
	 * Devuelve el detalle del patrocinio específicado por los parámetros.
	 * Si alguno de los parámetros representa a una instancia que no existe en el sistema, la función devuelve null.
	 * 
	 * @param nombreEvento
	 * @param nombreEdicion
	 * @param nombrePatrocinio
	 * @return
	 */
	public DTDetallePatrocinio mostrarDetallePatrocinio(String nombreEvento, String nombreEdicion, String codigoPatrocinio);
	/**
	 * Devuelve todos los registros asociados a la edición pasada por parámetro, incluso si la edición está archivada.
	 * 
	 * @param nombreEvento
	 * @param nombreEdicion
	 * @return
	 */
	public Set<DTDetalleRegistro> listarDTRegistrosAEdicion(String nombreEvento, String nombreEdicion);
	/**
	 * Devuelve el nombre de todas las instituciones que patrocinan a la edición pasada por parámetro.<p>
	 * Si la edición buscada está archivada, retorna null.
	 * 
	 * @param nombreEvento
	 * @param nombreEdicion
	 * @return un set con los patrocinadores o null si la edición asociada está archivada
	 */
	public Set<String> listarPatrocinadores(String nombreEvento, String nombreEdicion);
	public Patrocinio getPatrocinio(String nombreEvento, String nombreEdicion, String patrocinador);
	void finalizarEvento(String nombreEvento)
	        throws EventoNoExiste, EventoFinalizado;
	Set<DTEvento> listarEventosPorEstado(EEstadoEvento estado);
    Set<DTEvento> listarEventosEnCurso();       
    
    List<DTEventoVisitas> obtenerEventosMasVisitados(int limite);
    Set<DTEvento> listarEventosFinalizados();
    /**
     * Archiva la edición correspondiente a la pasada por parametro en la DB de Archivo.
     * 
     * @param nombreEvento
     * @param nombreEdicion
     * @throws EdicionFinalizada si la edición elegida ya finalizó
     * @throws EdicionNoExiste si la edición elegida no existe en el sistema
     * @throws EdicionYaArchivada si la edición elegida ya fue archivada anteriormente
     */
    public void archivarEdicion(String nombreEvento, String nombreEdicion, Date fechaArchivo) throws EdicionFinalizada, EdicionNoExiste, EdicionYaArchivada;
    /**
     * Elimina la edición pasada por parámetro del sistema, actualizando todos sus links y los objetos linkeados a la misma.
     * 
     * @param nombreEvento
     * @param nombreEdicion
     * @throws EdicionNoExiste si la edición elegida no existe en el sistema
     */
    public void eliminarEdicion(String nombreEvento, String nombreEdicion) throws EdicionNoExiste;
    /**
     * Devuelve el nombre de todas las ediciones organizadas por el organizador pasado por parámetro.<p>
     * Si el usuario no existe o si no tiene ediciones archivadas devuelve null.
     * 
     * @param nicknameOrganizador
     * @return una lista con el nombre de todas las ediciones archivadas del usuario
     */
    public List<String> listarEdicionesArchivadasDeOrganizador(String nicknameOrganizador);
}
