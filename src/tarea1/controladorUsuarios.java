package tarea1;

import java.time.LocalDate;
import java.util.List;

public class controladorUsuarios {
	private static controladorUsuarios instance = null;
	
	private manejadorUsuarios manejadorUsuarios;
	
	private controladorUsuarios() {
		manejadorUsuarios = manejadorUsuarios.getInstance();
	}
	
	private static controladorUsuarios getInstance() {
		if (instance == null) {
			instance = new controladorUsuarios();
		}
		return instance;
	}
	
	public boolean altaAsistente(String nickname, String correo, String apellido, LocalDate fechaNacimiento) {
		return false;
	}
	
	public boolean altaOrganizador(String nickname, String correo, String descripcion, String link) {
		return false;
	}
	
	public List<DTAsistente> listarAsistentes() {
		return null;
	}
	
	public List<DTUsuario> listarUsuarios() {
		return null;
	}
}
