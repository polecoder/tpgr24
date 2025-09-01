package logica;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTDetalleRegistro;
import logica.datatypes.DTDetalleUsuario;
import logica.datatypes.DTEventoEdicion;
import logica.datatypes.DTRegistro;
import logica.datatypes.DTUsuario;

public interface IControladorUsuarios {
	void altaAsistente(String nickname, String nombre, String correo, String apellido, Date fechaNacimiento, String nombreinstitucion) throws Exception;
	void altaOrganizador(String nickname, String nombre, String correo, String descripcion, String link) throws Exception;
	Map<String, DTAsistente> listarAsistentes();
	List<DTUsuario> listarUsuarios();
	Set<String> nombresOrganizadores();
	List<DTRegistro> listarRegistrosUsuario(String nickname);
	DTDetalleRegistro obtenerDetalleRegistro(String nickname, int indice);
	/**
	 * Devuelve el detalle del usuario, en caso de que no exista devuelve una excepción.
	 * 
	 * @param nicknameUsuario
	 * @return
	 * @throws Exception 
	 */
	public DTDetalleUsuario mostrarDetalleUsuario(String nicknameUsuario) throws Exception;
	List<DTEventoEdicion> getEventosEdicionesDeOrganizador(String nicknameOrganizador);
}
