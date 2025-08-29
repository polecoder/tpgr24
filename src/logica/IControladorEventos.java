package logica;

import java.util.List;
import java.util.Set;

import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;
import logica.datatypes.DTPatrocinio;
import logica.datatypes.DTTipoRegistro;

import java.time.LocalDate;

public interface IControladorEventos {
	Set<DTPatrocinio> listarPatrociniosEdicion(String nombreEdicion, String nombreEvento);
	DTDetallePatrocinio mostrarInfoPatrocinio(String codigoPatrocinio);
	Set<DTEvento> listarEventos();
	Set<DTTipoRegistro> listarTipoRegistro();
	boolean validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro, String nombreEvento);
	void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String codigoPatrocinio, LocalDate fecha);
	Set<DTEdicionEvento> listarEdicionesEvento(String nombreEvento);
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
	public void altaEvento(String nombre, String descripcion, LocalDate fechaAlta, String sigla, List<String> categorias);
	/**
	 * Retorna true en caso de que exista un evento e en el sistema tal que e.nombre == nombreEvento
	 * Retorna false en cualquier otro caso.
	 * 
	 * @param nombreEvento
	 */
	public boolean existeEvento(String nombreEvento);
}
