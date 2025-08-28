package logica;

import java.time.LocalDate;
import java.util.List;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTUsuario;

public class ControladorUsuarios implements IControladorUsuarios {
	private static ControladorUsuarios instance = null;
	private ManejadorUsuarios manejadorUsuarios;
	private IControladorInstitucion IContInstituciones;
	
	private ControladorUsuarios() {
		manejadorUsuarios = ManejadorUsuarios.getInstance();
		IContInstituciones= ControladorInstitucion.getInstance();
	}
	
	public static ControladorUsuarios getInstance() {
		if (instance == null) {
			instance = new ControladorUsuarios();
		}
		return instance;
	}
	
	public void altaAsistente(String nickname, String nombre, String correo, String apellido, LocalDate fechaNacimiento,String nombreinstitucion) throws Exception {
		// chequeo campos obligatorios
		if (nickname == null || nickname.isEmpty() ||nombre == null || nombre.isEmpty() ||apellido == null || apellido.isEmpty() ||correo == null || correo.isEmpty() ||
			fechaNacimiento == null) {
			
			     throw new Exception("Nick, nombre, apellido, correo y fecha de nacimiento son campos obligatorios");
			}
		// chequeo nickname
		if (manejadorUsuarios.existeNick(nickname)) {
			throw new Exception("El nickname ya existe");
		}
		// chequeo correo
		if (manejadorUsuarios.existeCorreo(correo)) {
			throw new Exception("El correo ya está en uso");
		}
		// si todo está bien, creo el asistente
		manejadorUsuarios.agregarAsistente(nickname, nombre, correo, apellido, fechaNacimiento, IContInstituciones.getInstitucion(nombreinstitucion));
	}
	
	public void altaOrganizador(String nickname, String nombre, String correo, String descripcion, String link) throws Exception {
		if (nickname == null || nickname.isEmpty() ||nombre == null || nombre.isEmpty() ||descripcion == null || descripcion.isEmpty() ||correo == null || correo.isEmpty()) { 
			throw new Exception("Nick, nombre, correo y descripcion son campos obligatorios");
		}
				
		if (manejadorUsuarios.existeNick(nickname)) {
			throw new Exception("El nickname ya existe");
		}
				
		if (manejadorUsuarios.existeCorreo(correo)) {
			throw new Exception("El correo ya está en uso");
		}
		
		manejadorUsuarios.agregarOrganizador(nickname, nombre, correo, descripcion, link);
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
