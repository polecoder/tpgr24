package logica;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excepciones.FormatoContrasenaInvalido;
import datatypes.DTAsistente;
import datatypes.DTDetalleRegistro;
import datatypes.DTDetalleUsuario;
import datatypes.DTEventoEdicion;
import datatypes.DTRegistro;
import datatypes.DTUsuario;

public interface IControladorUsuarios {
	void altaAsistente(String nickname, String contrasena, String nombre, String correo, String apellido, Date fechaNacimiento, String nombreinstitucion, byte[] imagen) throws FormatoContrasenaInvalido, Exception;
	void altaOrganizador(String nickname, String contrasena, String nombre, String correo, String descripcion, String link, byte[] imagen) throws FormatoContrasenaInvalido, Exception;
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
	
	/**
	 * Devuelve el nombre de la institución de un asistente.
	 * Si no existe devuelve null.
	 * 
	 * @param nicknameAsistente
	 * @return
	 */
	String getNombreInstitucionAsistente(String nickname);
	/**
	 * Modifica el Asistente a en el sistema tal que a.nickname = nickname con los datos pasados por parámetro.
	 * Si el Asistente a no existe entonces no hace nada.
	 * 
	 * @param nickname
	 * @param nuevoNombre
	 * @param nuevoApellido
	 * @param nuevaFechaNacimiento
	 */
	void modificarDatosAsistente(String nickname, String nuevoNombre, String nuevoApellido, Date nuevaFechaNacimiento) throws Exception;
	/**
	 * Modifica el Organizador o en el sistema tal que o.nickname = nickname con los datos pasados por parámetro.
	 * Si el Organizador o no existe entonces no hace nada.
	 * 
	 * @param nickname
	 * @param nuevoNombre
	 * @param nuevaDescripcion
	 * @param nuevoLink
	 */
	void modificarDatosOrganizador(String nickname, String nuevoNombre, String nuevaDescripcion, String nuevoLink) throws Exception;
	void actualizarImagenUsuario(String nickname, byte[] imagen);
	byte[] obtenerImagenPorNickname(String nickname);
}
