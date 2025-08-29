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
	List<DTTipoRegistro> listarTipoRegistro();
	boolean validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro, String nombreEvento);
	void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String codigoPatrocinio, LocalDate fecha);
	Set<DTEdicionEvento> listarEdicionesEvento(String nombreEvento);
	Set<String> nombresEventos();
	DTEvento getDTEvento(String nombre);
	public Set<String> nombresCategorias(String nombre);
	public Set<String> nombresEdicionesEvento(String nombre);
	DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada);
}
