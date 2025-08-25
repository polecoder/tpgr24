package tarea1;

import java.time.LocalDate;
import java.util.List;

public class controladorUsuarios implements IControladorUsuarios {
	private static controladorUsuarios instance = null;
	
	private manejadorUsuarios manejadorUsuarios;
	
	private controladorUsuarios() {
		manejadorUsuarios = manejadorUsuarios.getInstance();
	}
	
	public static controladorUsuarios getInstance() {
		if (instance == null) {
			instance = new controladorUsuarios();
		}
		return instance;
	}
	
	@Override
	public boolean altaAsistente(String nickname, String correo, String apellido, LocalDate fechaNacimiento) {
		return false;
	}
	
	@Override
	public boolean altaOrganizador(String nickname, String correo, String descripcion, String link) {
		return false;
	}
	
	@Override
	public List<DTAsistente> listarAsistentes() {
		return null;
	}
	
	@Override
	public List<DTUsuario> listarUsuarios() {
		return null;
	}
}
