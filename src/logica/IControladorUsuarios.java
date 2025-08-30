package logica;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.Map;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTDetalleRegistro;
import logica.datatypes.DTRegistro;

public interface IControladorUsuarios {
	void altaAsistente(String nickname, String nombre, String correo, String apellido, LocalDate fechaNacimiento,String institucion) throws Exception;
	void altaOrganizador(String nickname, String nombre, String correo, String descripcion, String link) throws Exception;
	
	Map<String, DTAsistente> listarAsistentes();
	List<DTUsuario> listarUsuarios();
	Set<String> nombresOrganizadores();
	
	
	
	List<DTRegistro> listarRegistrosUsuario(String nickname);
	DTDetalleRegistro obtenerDetalleRegistro(String nickname, int indice);
	
}
