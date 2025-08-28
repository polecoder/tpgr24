package logica;

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
     * Devuelve true si existe un evento e en el sistema tal que e.nombre = nombreEvento.
     * En caso contrario devuelve false
     * 
     * @param nombreEvento
     * @return
     */
	public boolean existeEvento(String nombreEvento);
}
