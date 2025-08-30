package logica;

import java.time.LocalDate;
import java.util.List;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTUsuario;

public interface IControladorUsuarios {
	public void altaAsistente(String nickname, String nombre, String correo, String apellido, LocalDate fechaNacimiento,String nombreinstitucion) throws Exception;
	public void altaOrganizador(String nickname, String nombre, String correo, String descripcion, String link) throws Exception;
	
	List<DTAsistente> listarAsistentes();
	List<DTUsuario> listarUsuarios();
}
