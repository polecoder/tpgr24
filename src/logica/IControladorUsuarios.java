package logica;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTUsuario;

public interface IControladorUsuarios {
	void altaAsistente(String nickname, String nombre, String correo, String apellido, LocalDate fechaNacimiento,String institucion) throws Exception;
	void altaOrganizador(String nickname, String nombre, String correo, String descripcion, String link) throws Exception;
	
	List<DTAsistente> listarAsistentes();
	List<DTUsuario> listarUsuarios();
	Set<String> nombresOrganizadores();
	
	
}
