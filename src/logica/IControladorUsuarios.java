package logica;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Map;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTDetalleRegistro;
import logica.datatypes.DTRegistro;

public interface IControladorUsuarios {
	void altaAsistente(String nickname, String nombre, String correo, String apellido, Date fechaNacimiento, String nombreinstitucion) throws Exception;
	void altaOrganizador(String nickname, String nombre, String correo, String descripcion, String link) throws Exception;
	
	Map<String, DTAsistente> listarAsistentes();
	List<DTUsuario> listarUsuarios();
	Set<String> nombresOrganizadores();
	
	
	
	List<DTRegistro> listarRegistrosUsuario(String nickname);
	DTDetalleRegistro obtenerDetalleRegistro(String nickname, int indice);
	
}
