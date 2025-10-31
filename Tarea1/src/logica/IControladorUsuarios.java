package logica;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excepciones.ContrasenaIncorrecta;
import excepciones.FormatoContrasenaInvalido;
import excepciones.FormularioIncompletoAsistente;
import excepciones.FormularioIncompletoOrganizador;
import excepciones.UsuarioNoExiste;
import datatypes.DTAsistente;
import datatypes.DTDetalleRegistro;
import datatypes.DTDetalleUsuario;
import datatypes.DTEdicionEvento;
import datatypes.DTEventoEdicion;
import datatypes.DTRegistro;
import datatypes.DTUsuario;

public interface IControladorUsuarios {
	void altaAsistente(String nickname, String contrasena, String nombre, String correo, String apellido, Date fechaNacimiento, String nombreinstitucion, byte[] imagen) throws FormatoContrasenaInvalido, FormularioIncompletoAsistente;
	void altaOrganizador(String nickname, String contrasena, String nombre, String correo, String descripcion, String link, byte[] imagen) throws FormatoContrasenaInvalido, FormularioIncompletoOrganizador;
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
	public DTDetalleUsuario mostrarDetalleUsuario(String nicknameUsuario) throws UsuarioNoExiste;
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
	void modificarDatosAsistente(String nickname, String nuevoNombre, String nuevoApellido, Date nuevaFechaNacimiento) throws FormularioIncompletoAsistente;
	/**
	 * Modifica el Organizador o en el sistema tal que o.nickname = nickname con los datos pasados por parámetro.
	 * Si el Organizador o no existe entonces no hace nada.
	 * 
	 * @param nickname
	 * @param nuevoNombre
	 * @param nuevaDescripcion
	 * @param nuevoLink
	 */
	void modificarDatosOrganizador(String nickname, String nuevoNombre, String nuevaDescripcion, String nuevoLink) throws FormularioIncompletoOrganizador;
	void actualizarImagenUsuario(String nickname, byte[] imagen);
	byte[] obtenerImagenPorNickname(String nickname);
	/**
	 * Devuelve una lista de todos los datos detallados de todos los usuarios que existen en el sistema.
	 * 
	 * @return
	 */
	List<DTDetalleUsuario> listarDetalleUsuarios();
	/**
	 * Busca el usuario "u" en el sistema tal que u.nickname = usuario o u.correo = usuario. Sino lo encuentra devuelve null
	 * En caso de que este usuario "u" exista, valida el login, es decir si la contraseña proporcionada es correcta. Si la contraseña no es correcta, retorna null.
	 * 
	 * Retorna el detalle del usuario en caso que todas las validaciones se cumplan.
	 * 
	 * @param usuario - nickname o correo
	 * @param contrasena
	 * @return
	 * @throws ContrasenaIncorrecta 
	 */
	DTDetalleUsuario validarLogin(String usuario, String contrasena) throws ContrasenaIncorrecta;
	public void marcarAsistencia(String nickname, int indice) throws excepciones.UsuarioNoExiste,excepciones.RegistroNoExiste,excepciones.YaAsisteAEdicion;
	List<DTEdicionEvento> listarEdicionesConRegistrosUsuario(String nick);
	DTDetalleRegistro getDTDetalleRegistroPorEdicion(String edicion, String Usuario);
}
