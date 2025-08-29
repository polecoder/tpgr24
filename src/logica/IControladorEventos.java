package logica;

import java.util.List;

import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;
import logica.datatypes.DTPatrocinio;
import logica.datatypes.DTTipoRegistro;

import java.time.LocalDate;

public interface IControladorEventos {
	List<DTPatrocinio> listarPatrociniosEdicion(String nombreEdicion, String nombreEvento);
	DTDetallePatrocinio mostrarInfoPatrocinio(String codigoPatrocinio);
	List<DTEvento> listarEventos();
	List<DTTipoRegistro> listarTipoRegistro();
	boolean validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro, String nombreEvento);
	void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String codigoPatrocinio, LocalDate fecha);
	List<DTEdicionEvento> listarEdicionesEvento(String nombreEvento);
	
	boolean altaCategoria(String nombre);
	boolean existeCategoria(String nombre);
}
