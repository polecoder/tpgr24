package logica;

import java.time.LocalDate;
import java.util.List;

public interface IControladorUsuarios {
	boolean altaAsistente(String nickname, String correo, String apellido, LocalDate fechaNacimiento);
	boolean altaOrganizador(String nickname, String correo, String descripcion, String link);
	
	List<DTAsistente> listarAsistentes();
	List<DTUsuario> listarUsuarios();
}
