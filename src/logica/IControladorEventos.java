package logica;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excepciones.DatosTipoRegistroInvalidos;
import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

public interface IControladorEventos {
	public Map<String,DTEvento> listarEventos();
	public Map<String, DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion);
	public boolean validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro, String nombreEvento) throws Exception;
	// public void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String codigoPatrocinio, LocalDate fecha); // Reemplazado por "altaRegistro()"
	public Map<String,DTEdicionEvento> listarEdicionesEvento(String nombreEvento);
	public Set<String> nombresEventos();
	public DTEvento getDTEvento(String nombre);
	public Set<String> nombresCategorias(String nombre);
	public Set<String> nombresEdicionesEvento(String nombre) throws EventoNoExiste;
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada);
	public void altaEdicionEvento(String nombreEvento,String nombre,String sigla,Date fechaInicio,Date fechaFin,Date fechaAlt,String ciudad,String pais, String organizadorNickname) throws Exception;
	// public String[] getNombresEdiciones(String itemAt); // Reemplazada por "nombresEdicionesEvento()"
	public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento,String nombreEdicion)throws NombreTipoRegistroRepetido, DatosTipoRegistroInvalidos;
	public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombre) throws TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste;
	public void altaCategoria(String nombre) throws Exception;
	public boolean existeCategoria(String nombre);

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
	public void altaEvento(String nombre, String descripcion, Date fechaAlta, String sigla, List<String> categorias);
	/**
	 * Retorna true en caso de que exista un evento e en el sistema tal que e.nombre == nombreEvento
	 * Retorna false en cualquier otro caso.
	 * 
	 * @param nombreEvento
	 */
	public boolean existeEvento(String nombreEvento);
	public void altaRegistro(String nicknameAsistente, String nombreTipoRegistro,String nombreEdicion, String nombreEvento, String codigoPatrocinio, Date fecha);
	public Set<String> listarRegistrosAEdicion(String selectedEvento, String selectedEdicion);
	public Set<String> listarPatrociniosDeEdicion(String selectedEvento, String selectedEdicion);
	public Set<String> getNombresTipoRegistro(String eventoSeleccionado, String edicionSeleccionada);
	public void altaPatrocinio(String eventoSeleccionado,  String edicionSeleccionada,Date FechaAlta,String institucionSeleccionada, ENivelPatrocinio nivelPatrocinio, float aporteEconomico, int cupos,String codigoPatrocinio) throws Exception;
	public String[] getNombresEdiciones(String nombreEvento);
	/**
	 * Devuelve una lista que contienen el nombre de todas las categorías
	 * 
	 * @return
	 */
	public List<String> getListaCategorias();
	public String[] getTiposRegistro(String nombreEvento, String nombreEdicion) throws EventoNoExiste, EdicionNoExiste;
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
}
