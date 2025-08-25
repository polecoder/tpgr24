package tarea1;

import java.util.List;
import java.time.LocalDate;

public interface IControladorEventos {
	List<DTPatrocinio> listarPatrociniosEdicion(String nombreEdicion, String nombreEvento);
	DTDetallePatrocinio mostrarInfoPatrocinio(String codigoPatrocinio);
	List<DTEvento> listarEventos();
	List<DTTipoRegistro> listarTipoRegistro();
	boolean validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro, String nombreEvento);
	void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String codigoPatrocinio, LocalDate fecha);
	List<DTEdicionEvento> listarEdicionesEvento(String nombreEvento);
}
