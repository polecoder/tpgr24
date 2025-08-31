package logica;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
	
	public void altaAsistente(String nickname, String nombre, String correo, String apellido, Date fechaNacimiento, String nombreinstitucion) throws Exception {
	    if (nickname == null || nickname.isEmpty() ||nombre == null || nombre.isEmpty() ||apellido == null || apellido.isEmpty() ||correo == null || correo.isEmpty() ||fechaNacimiento == null) {
	        throw new Exception("Nick, nombre, apellido, correo y fecha de nacimiento son campos obligatorios");
	    }

	    if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) {
	        throw new Exception("El nombre solo puede contener letras y espacios");
	    }

	    if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) {
	        throw new Exception("El apellido solo puede contener letras y espacios");
	    }

	    if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
	        throw new Exception("El formato del correo es inválido");
	    }

	    if (manejadorUsuarios.existeNick(nickname)) {
	        throw new Exception("El nickname ya existe");
	    }

	    if (manejadorUsuarios.existeCorreo(correo)) {
	        throw new Exception("El correo ya está en uso");
	    }

	    manejadorUsuarios.agregarAsistente(nickname, nombre, correo, apellido, fechaNacimiento, IContInstituciones.getInstitucion(nombreinstitucion));
	}

	public void altaOrganizador(String nickname, String nombre, String correo, String descripcion, String link) throws Exception {
	    if (nickname == null || nickname.isEmpty() ||
	        nombre == null || nombre.isEmpty() ||
	        descripcion == null || descripcion.isEmpty() ||
	        correo == null || correo.isEmpty()) {
	        throw new Exception("Nick, nombre, correo y descripción son campos obligatorios");
	    }

	    if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) {
	        throw new Exception("El nombre solo puede contener letras y espacios");
	    }

	    if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
	        throw new Exception("El formato del correo es inválido");
	    }

	    if (link != null && !link.isEmpty() && !link.matches("^(https?://).+")) {
	        throw new Exception("El link debe comenzar con http:// o https://");
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

	@Override
	public Set<String> nombresOrganizadores() {
		// TODO Auto-generated method stub
		return null;
	}
}

