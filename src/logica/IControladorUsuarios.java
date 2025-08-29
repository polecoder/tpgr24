package logica;

import java.time.LocalDate;
import java.util.List;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTDetalleRegistro;
import logica.datatypes.DTRegistro;

public interface IControladorUsuarios {
	boolean altaAsistente(String nickname, String correo, String apellido, LocalDate fechaNacimiento);
	boolean altaOrganizador(String nickname, String correo, String descripcion, String link);
	
	List<DTAsistente> listarAsistentes();
	List<DTUsuario> listarUsuarios();
	
	List<DTRegistro> listarRegistrosUsuario(String nickname);
	DTDetalleRegistro obtenerDetalleRegistro(String nickname, int indice);
	
}
