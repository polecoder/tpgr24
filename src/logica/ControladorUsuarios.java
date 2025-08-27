package logica;

import java.time.LocalDate;
import java.util.List;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTUsuario;

public class ControladorUsuarios implements IControladorUsuarios {
	private static ControladorUsuarios instance = null;
	
	private ManejadorUsuarios manejadorUsuarios;
	
	private ControladorUsuarios() {
		manejadorUsuarios = ManejadorUsuarios.getInstance();
	}
	
	public static ControladorUsuarios getInstance() {
		if (instance == null) {
			instance = new ControladorUsuarios();
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
