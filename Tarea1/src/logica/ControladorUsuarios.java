package logica;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import excepciones.ContrasenaIncorrecta;
import excepciones.EdicionNoExiste;
import excepciones.FormatoContrasenaInvalido;
import excepciones.FormularioIncompletoAsistente;
import excepciones.FormularioIncompletoOrganizador;
import excepciones.RegistroNoExiste;
import excepciones.UsuarioNoExiste;
import datatypes.DTAsistente;
import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleRegistro;
import datatypes.DTDetalleUsuario;
import datatypes.DTEdicionEvento;
import datatypes.DTEventoEdicion;
import datatypes.DTUsuario;
import datatypes.EEstadoEdicion;
import datatypes.DTRegistro;

public class ControladorUsuarios implements IControladorUsuarios {
	private static ControladorUsuarios instance = null;
	private ManejadorUsuarios manejadorUsuarios;
	private IControladorInstitucion iContInstituciones;

	private ControladorUsuarios() {
		manejadorUsuarios = ManejadorUsuarios.getInstance();
		iContInstituciones = ControladorInstitucion.getInstance();
	}

	public static ControladorUsuarios getInstance() {
		if (instance == null) {
			instance = new ControladorUsuarios();
		}
		return instance;
	}
	
	public static void resetInstance() {
		ManejadorUsuarios.resetInstance();
		instance = null;
	}

	@Override
	public void altaAsistente(String nickname, String contrasena, String nombre, String correo, String apellido, Date fechaNacimiento,
			String nombreinstitucion, byte[] imagen) throws FormatoContrasenaInvalido, FormularioIncompletoAsistente  {
		if (nickname == null || nickname.isBlank()) {
		    throw new FormularioIncompletoAsistente("El campo nickname es obligatorio.");
		}

		if (contrasena == null || contrasena.isBlank()) {
		    throw new FormularioIncompletoAsistente("El campo contraseña es obligatorio.");
		}

		if (nombre == null || nombre.isBlank()) {
		    throw new FormularioIncompletoAsistente("El campo nombre es obligatorio.");
		}

		if (apellido == null || apellido.isBlank()) {
		    throw new FormularioIncompletoAsistente("El campo apellido es obligatorio.");
		}

		if (correo == null || correo.isBlank()) {
		    throw new FormularioIncompletoAsistente("El campo correo es obligatorio.");
		}

		if (fechaNacimiento == null) {
		    throw new FormularioIncompletoAsistente("El campo fecha de nacimiento es obligatorio.");
		}


		if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) {
			throw new FormularioIncompletoAsistente("El nombre solo puede contener letras y espacios");
		}

		if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) {
			throw new FormularioIncompletoAsistente("El apellido solo puede contener letras y espacios");
		}

		if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
			throw new FormularioIncompletoAsistente("El formato del correo es inválido");
		}

		if (fechaNacimiento.after(new Date())) {
			throw new FormularioIncompletoAsistente("La fecha de nacimiento no puede ser en el futuro.");
		}
		
		if (manejadorUsuarios.existeNick(nickname)) {
			throw new FormularioIncompletoAsistente("El nickname del asistente ya existe");
		}

		if (manejadorUsuarios.existeCorreo(correo)) {
			throw new FormularioIncompletoAsistente("El correo ya está en uso");
		}

		// Validación de contraseña
		if (contrasena.length() < 8) {
			throw new FormatoContrasenaInvalido("La contraseña debe tener al menos 8 caracteres.");
		}
		if (!contrasena.chars().anyMatch(Character::isUpperCase)) {
			throw new FormatoContrasenaInvalido("La contraseña debe tener al menos una mayúscula.");
		}
		if (!contrasena.chars().anyMatch(Character::isDigit)) {
			throw new FormatoContrasenaInvalido("La contraseña debe tener al menos un dígito.");
		}
        boolean hasSpecial = contrasena.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
        if (!hasSpecial) {
            throw new FormatoContrasenaInvalido("La contraseña debe tener al menos un carácter especial.");
        }
		
		manejadorUsuarios.agregarAsistente(nickname, contrasena, nombre, correo, apellido, fechaNacimiento,
				iContInstituciones.getInstitucion(nombreinstitucion), imagen);
	}
	
	@Override
	public void altaOrganizador(String nickname, String contrasena, String nombre, String correo, String descripcion, String link, byte[] imagen)
			throws FormatoContrasenaInvalido, FormularioIncompletoOrganizador {
		if (nickname == null || nickname.isBlank()) {
		    throw new FormularioIncompletoOrganizador("El campo nickname es obligatorio.");
		}

		if (nombre == null || nombre.isBlank()) {
		    throw new FormularioIncompletoOrganizador("El campo nombre es obligatorio.");
		}

		if (descripcion == null || descripcion.isBlank()) {
		    throw new FormularioIncompletoOrganizador("El campo descripción es obligatorio.");
		}

		if (correo == null || correo.isBlank()) {
		    throw new FormularioIncompletoOrganizador("El campo correo es obligatorio.");
		}

		if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) {
			throw new FormularioIncompletoOrganizador("El nombre solo puede contener letras y espacios");
		}

		if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
			throw new FormularioIncompletoOrganizador("El formato del correo es inválido");
		}

		if (link != null && !link.isEmpty() && !link.matches("^(https?://).+")) {
			throw new FormularioIncompletoOrganizador("El link debe comenzar con http:// o https://");
		}

		if (manejadorUsuarios.existeNick(nickname)) {
			throw new FormularioIncompletoOrganizador("El nickname ya está en uso");
		}

		if (manejadorUsuarios.existeCorreo(correo)) {
			throw new FormularioIncompletoOrganizador("El correo ya está en uso");
		}
		
		// Validación de contraseña
		if (contrasena.length() < 8) {
			throw new FormatoContrasenaInvalido("La contraseña debe tener al menos 8 caracteres.");
		}
		if (!contrasena.chars().anyMatch(Character::isUpperCase)) {
			throw new FormatoContrasenaInvalido("La contraseña debe tener al menos una mayúscula.");
		}
		if (!contrasena.chars().anyMatch(Character::isDigit)) {
			throw new FormatoContrasenaInvalido("La contraseña debe tener al menos un dígito.");
		}
        boolean hasSpecial = contrasena.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
        if (!hasSpecial) {
            throw new FormatoContrasenaInvalido("La contraseña debe tener al menos un carácter especial.");
        }
		
		manejadorUsuarios.agregarOrganizador(nickname, contrasena, nombre, correo, descripcion, link, imagen);
	}

	public Map<String, DTAsistente> listarAsistentes() {
		return manejadorUsuarios.listarAsistentes();
	}
	public void actualizarImagenUsuario(String nickname, byte[] imagen) {
	    if (imagen == null || imagen.length == 0) return;
	    manejadorUsuarios.actualizarImagenUsuario(nickname, imagen);
	}
	
	public byte[] obtenerImagenPorNickname(String nickname) {
	    Asistente asistente = manejadorUsuarios.getAsistente(nickname);
	    if (asistente != null) return asistente.getImagen();
	    Organizador organizador = manejadorUsuarios.getOrganizador(nickname);
	    if (organizador != null) return organizador.getImagen();
	    return null;
	}

	public List<DTUsuario> listarUsuarios() {
		List<DTUsuario> usu = new ArrayList<>();
		for (Asistente a : manejadorUsuarios.getConjuntoAsistentes().values()) {
			usu.add(a.listarUsuario());
		}
		for (Organizador o : manejadorUsuarios.getConjuntoOrganizador().values()) {
			usu.add(o.listarUsuario());
		}
		return usu;
	}

	@Override
	public List<DTRegistro> listarRegistrosUsuario(String nickname) {
		Asistente asistente = manejadorUsuarios.getAsistente(nickname);
		List<DTRegistro> out = new ArrayList<>();
		if (asistente == null)
			return out;

		ManejadorEventos manejadoreventos = ManejadorEventos.getInstancia();

		for (Registro r : asistente.getRegistros()) {
			String nombreEd = manejadoreventos.nombreEdicionDeRegistro(r);
			String nombreTipo = manejadoreventos.nombreTipoDeRegistro(r);
			out.add(new DTRegistro(nombreEd, nombreTipo));
		}
		return out;
	}

	@Override
	public DTDetalleRegistro obtenerDetalleRegistro(String nickname, int indice) {
		Asistente asistente = manejadorUsuarios.getAsistente(nickname);
		if (asistente == null)
			return null;

		List<Registro> regs = asistente.getRegistros();
		if (indice < 0 || indice >= regs.size())
			return null;

		Registro registro = regs.get(indice);

		ManejadorEventos manejadoreventos = ManejadorEventos.getInstancia();
		String nombreEd = manejadoreventos.nombreEdicionDeRegistro(registro);
		String nombreTipo = manejadoreventos.nombreTipoDeRegistro(registro);
		
		DTDetalleRegistro dtRegistro = null;
		if (registro.getPatrocinio() != null) {
			dtRegistro = new DTDetalleRegistro(nombreEd, asistente.getNickname(), nombreTipo, registro.getFechaRegistro(), registro.getCosto(), registro.getPatrocinio().getCodigoPatrocinio(), registro.getAsistio());
		} else {
			dtRegistro = new DTDetalleRegistro(nombreEd, asistente.getNickname(), nombreTipo, registro.getFechaRegistro(), registro.getCosto(), null, registro.getAsistio());
		}
		
		return dtRegistro;
	}

	/**
	 * Devuelve el detalle del usuario, en caso de que no exista devuelve una excepción.
	 * 
	 * @param nicknameUsuario
	 * @return
	 * @throws Exception 
	 */
	public DTDetalleUsuario mostrarDetalleUsuario(String nicknameUsuario) throws UsuarioNoExiste {
		return this.manejadorUsuarios.mostrarDetalleUsuario(nicknameUsuario);
	}
	
	@Override
	public Set<String> nombresOrganizadores() {
		return manejadorUsuarios.getConjuntoOrganizador().keySet();
	}

	@Override
	public List<DTEventoEdicion> getEventosEdicionesDeOrganizador(String nicknameOrganizador) {
		Organizador organizador = this.manejadorUsuarios.getOrganizador(nicknameOrganizador);
		List<DTEventoEdicion> resultado = new ArrayList<>();
		if (organizador != null) {
			for (Edicion e: organizador.getEdiciones()) {
				resultado.add(new DTEventoEdicion(e.getNombreEvento(), e.getNombre()));
			}
		}
		return resultado;
	}
	
	/**
	 * Devuelve el nombre de la institución de un asistente.
	 * Si no existe devuelve null.
	 * 
	 * @param nicknameAsistente
	 * @return
	 */
	@Override
	public String getNombreInstitucionAsistente(String nicknameAsistente) {
		Asistente asistente = this.manejadorUsuarios.getAsistente(nicknameAsistente);
		if (asistente != null) {
			Institucion institucion = asistente.getInstitucion();
			if (institucion != null) {
				return institucion.getNombre();
			}
		}
		return "";
	}

	@Override
	public void modificarDatosAsistente(String nickname, String nuevoNombre, String nuevoApellido,
			Date nuevaFechaNacimiento) throws FormularioIncompletoAsistente {
		Asistente asistente = this.manejadorUsuarios.getAsistente(nickname);
		// Asistente no existe en el sistema
		if (asistente == null) return;
		
		// Validación de campos vacíos
		if (nuevoNombre == null || nuevoNombre.isEmpty()) throw new FormularioIncompletoAsistente("El nuevo nombre no puede ser vacío.");
		if (nuevoApellido == null || nuevoApellido.isEmpty()) throw new FormularioIncompletoAsistente("El nuevo apellido no puede ser vacío.");
		if (nuevaFechaNacimiento == null) throw new FormularioIncompletoAsistente("Ingrese una nueva fecha de nacimiento.");
		
		// Ningún campo se modificó
		if (nuevoNombre.equals(asistente.getNombre()) && nuevoApellido.equals(asistente.getApellido()) && nuevaFechaNacimiento.equals(asistente.getNacimiento())) throw new FormularioIncompletoAsistente("No se modificó ningún campo.");
		
		// Validación de los datos nuevos
		if (!nuevoNombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) throw new FormularioIncompletoAsistente("El nuevo nombre solo puede contener letras y espacios.");
		if (!nuevoApellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) throw new FormularioIncompletoAsistente("El nuevo apellido solo puede contener letras y espacios.");
		if (nuevaFechaNacimiento.after(new Date())) throw new FormularioIncompletoAsistente("La nueva fecha de nacimiento no puede ser en el futuro.");
		
		// Seteo los campos (notar que si los datos son iguales a los anteriores no se hace nada)
		asistente.setNombre(nuevoNombre);
		asistente.setApellido(nuevoApellido);
		asistente.setNacimiento(nuevaFechaNacimiento);
	}

	@Override
	public void modificarDatosOrganizador(String nickname, String nuevoNombre, String nuevaDescripcion,
			String nuevoLink) throws FormularioIncompletoOrganizador {
		Organizador organizador = this.manejadorUsuarios.getOrganizador(nickname);
		// Organizador no existe en el sistema
		if (organizador == null) return;
		
		// Validación de campos vacíos
		if (nuevoNombre == null || nuevoNombre.isEmpty()) throw new FormularioIncompletoOrganizador("El nuevo nombre no puede ser vacío.");
		if (nuevaDescripcion == null || nuevaDescripcion.isEmpty()) throw new FormularioIncompletoOrganizador("La nueva descripción no puede ser vacía.");
		if (nuevoLink == null || nuevoLink.isEmpty()) throw new FormularioIncompletoOrganizador("El nuevo link no puede ser vacío.");
		
		// Ningún campo se modificó
		if (nuevoNombre.equals(organizador.getNombre()) && nuevaDescripcion.equals(organizador.getDescripcion()) && nuevoLink.equals(organizador.getLink()) ) throw new FormularioIncompletoOrganizador("No se modificó ningún campo.");
		
		// Validación de los datos nuevos
		if (!nuevoNombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) throw new FormularioIncompletoOrganizador("El nuevo nombre solo puede contener letras y espacios.");
		if (!nuevoLink.matches("^(https?://).+")) throw new FormularioIncompletoOrganizador("El nuevo link debe comenzar con http:// o https://.");
		
		// Seteo los campos (notar que si los datos son iguales a los anteriores no se hace nada)
		organizador.setNombre(nuevoNombre);
		organizador.setDescripcion(nuevaDescripcion);
		organizador.setLink(nuevoLink);
	}

	@Override
	public List<DTDetalleUsuario> listarDetalleUsuarios() {
		List<DTDetalleUsuario> resultado = new ArrayList<>();
		Map<String, Asistente> asistentes = this.manejadorUsuarios.getConjuntoAsistentes();
		Map<String, Organizador> organizadores = this.manejadorUsuarios.getConjuntoOrganizador();
		for (Asistente a: asistentes.values()) {
			String insti=null;
			if (a.getInstitucion()!=null) {
				insti=a.getInstitucion().getNombre();
			}
			resultado.add(new DTDetalleAsistente(a.getNickname(), a.getNombre(), a.getCorreo(), a.getApellido(), a.getNacimiento(), insti, a.getImagen()));
		}
		for (Organizador o: organizadores.values()) {
			resultado.add(new DTDetalleOrganizador(o.getNickname(), o.getNombre(), o.getCorreo(), o.getDescripcion(), o.getLink(), o.getImagen()));
		}
		return resultado;
	}

	@Override
	public DTDetalleUsuario validarLogin(String usuario, String contrasena) throws ContrasenaIncorrecta {
		Usuario usuario1 = this.manejadorUsuarios.getAsistente(usuario);
		if (usuario1 == null) usuario1 = this.manejadorUsuarios.getOrganizador(usuario);
		if (usuario1 == null) usuario1 = this.manejadorUsuarios.getAsistentePorCorreo(usuario);
		if (usuario1 == null) usuario1 = this.manejadorUsuarios.getOrganizadorPorCorreo(usuario);
		// si el usuario sigue siendo null en este punto entonces no existe en el sistema
		if (usuario1 == null) return null;
		// valido que la contraseña sea correcta
		if (!contrasena.equals(usuario1.getContrasena())) {
			throw new ContrasenaIncorrecta("La contraseña es incorrecta.");
		}
		// todas las validaciones ok, retorno el detalle de usuario
		try {
			return this.mostrarDetalleUsuario(usuario1.getNickname());
		} catch (UsuarioNoExiste e) {
			return null;
		}
	}
	
	
	
	public void confirmarAsistencia(String nickname, String nombreEdicion) throws Exception {
	    Asistente a = manejadorUsuarios.getAsistente(nickname);
	    if (a == null) throw new Exception("Asistente no encontrado.");

	    boolean confirmado = false;
	    for (Registro r : a.getRegistros()) {
	        if (r.getEdicion().getNombre().equals(nombreEdicion)) {
	            r.setAsistio(true);
	            confirmado = true;
	            break;
	        }
	    }
	    if (!confirmado) throw new Exception("Registro no encontrado para esa edición.");
	}

	
	@Override
	public List<DTEdicionEvento> listarEdicionesConRegistrosUsuario(String nick) {
		List<DTEdicionEvento> DTEdiciones = new ArrayList<>();
		List<Edicion> ediciones =manejadorUsuarios.listarEdicionesConRegistros(nick);
		if (ediciones!=null) {
			for (Edicion edi : ediciones) {
				DTEdiciones.add(new DTEdicionEvento(edi.getNombre(), edi.getSigla(), edi.getFechaInicio(), edi.getFechaFin(), 
						edi.getFechaAlta(), edi.getCiudad(), edi.getPais(),edi.getOrganizadorNickname(),edi.getImagen(),edi.getEstado(), edi.getVideoURL()));
			}
		}
	return DTEdiciones;
	}

	@Override
	public DTDetalleRegistro getDTDetalleRegistroPorEdicion(String edicion, String Usuario) {
		Asistente asistente = manejadorUsuarios.getAsistente(Usuario);
		if (asistente == null)
			return null;
		List<Registro> regs = asistente.getRegistros();
		
		for(Registro reg : regs) {
			
			if (reg.getEdicion().getNombre().equals(edicion)) {
				ManejadorEventos manejadoreventos = ManejadorEventos.getInstancia();
				String nombreTipo = manejadoreventos.nombreTipoDeRegistro(reg);
				String codigoPatrocinio = null;
				if (reg.getPatrocinio() != null) {
					codigoPatrocinio = reg.getPatrocinio().getCodigoPatrocinio();
				}
			
				return new DTDetalleRegistro(edicion, Usuario, nombreTipo, reg.getFechaRegistro(), reg.getCosto(), codigoPatrocinio, reg.getAsistio());
				
			}
		}
		
		return null;
		
	}

	@Override
	public boolean existeNick(String nick) {
		return manejadorUsuarios.existeNick(nick);
	}
}
