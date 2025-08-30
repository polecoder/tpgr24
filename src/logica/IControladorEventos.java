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
	public Set<DTPatrocinio> listarPatrociniosEdicion(String nombreEdicion, String nombreEvento);
	public DTDetallePatrocinio mostrarInfoPatrocinio(String codigoPatrocinio);
	public Set<DTEvento> listarEventos();
	public List<DTTipoRegistro> listarTipoRegistro();
	public boolean validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro, String nombreEvento);
	public void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String codigoPatrocinio, LocalDate fecha);
	public Set<DTEdicionEvento> listarEdicionesEvento(String nombreEvento);
	public Set<String> nombresEventos();
	public DTEvento getDTEvento(String nombre);
	public Set<String> nombresCategorias(String nombre);
	public Set<String> nombresEdicionesEvento(String nombre);
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada);
	public void AltaEdicionEvento(String nombreEvento,String nombre,String sigla,LocalDate fechaInicio,LocalDate fechaFin,LocalDate fechaAlt,String ciudad,String pais) throws Exception;
	
}
