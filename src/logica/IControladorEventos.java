package logica;

import java.util.Date;
import java.util.List;
import java.util.Set;

import excepciones.DatosTipoRegistroInvalidos;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
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
	public void altaEdicionEvento(String nombreEvento,String nombre,String sigla,Date fechaInicio,Date fechaFin,Date fechaAlt,String ciudad,String pais) throws Exception;
	public String[] getNombreEventos();
	public String[] getNombresEdiciones(String itemAt);
	public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento,String nombreEdicion)throws NombreTipoRegistroRepetido, DatosTipoRegistroInvalidos;
	public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombre) throws TipoRegistroNoExiste;
	public void altaRegistro(String nicknameAsistente, String nombreTipoRegistro,String nombreEdicion, String nombreEvento, String codigoPatrocinio, Date fecha);
	public void altaPatrocinio(String codigoPatrocinio, Date fechaPatrocinio, float aporte, ENivelPatrocinio nivel, int cuposGratuitos, String nombreInstitucion, String nombreEvento,String nombreEdicion);
}
