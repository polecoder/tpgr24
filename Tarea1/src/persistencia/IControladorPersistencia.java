package persistencia;

import java.util.Date;

import datatypes.DTDetalleRegistro;

public interface IControladorPersistencia {
	/**
	 * PRE-CONDICIÓN: El asistente existe en el sistema.<p>
	 * Guarda el asistente con nickname `nicknameAsistente` en el archivo.
	 * 
	 * @param nicknameAsistente el nickname del asistente para guardar en el archivo
	 */
	public void guardarAsistenteEnArchivo(String nicknameAsistente);
	/**
	 * PRE-CONDICIÓN: El organizador existe en el sistema.<p>
	 * Guarda el organizador con nickname `nicknameOrganizador` en el archivo.
	 * 
	 * @param nicknameOrganizador el nickname del organizador para guardar en el archivo
	 */
	public void guardarOrganizadorEnArchivo(String nicknameOrganizador);
	/**
	 * PRE-CONDICIÓN: El asistente asociado al registro ya está en el archivo. De la misma forma la edición asociada al registro ya está en el archivo<p>
	 * Guarda el registro con los datos pasados por el parámetro `registro` en el archivo
	 * 
	 * @param registro los datos del registro para guardar en el archivo
	 */
	public void guardarRegistroEnArchivo(DTDetalleRegistro registro);
	/**
	 * Archiva una edición y todos sus datos relacionados en el archivo.
	 * Esto incluye:<p>
	 * 1. Registros asociados<p>
	 * 2. Asistentes asociados a los registros asociados<p>
	 * 3. Organizadores asociados<p>
	 * 
	 * @param registro los datos del registro para guardar en el archivo
	 */
	public void archivarEdicion(String nombreEvento, String nombreEdicion, Date fechaArchivo);
}
