package logica;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import excepciones.ContrasenaIncorrecta;
import excepciones.FormatoContrasenaInvalido;
import datatypes.DTAsistente;
import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleRegistro;
import datatypes.DTDetalleUsuario;
import datatypes.DTEventoEdicion;
import datatypes.DTUsuario;
import datatypes.DTRegistro;
public class ControladorUsuarios implements IControladorUsuarios {
	private static ControladorUsuarios instance = null;
	private ManejadorUsuarios manejadorUsuarios;
	private IControladorInstitucion IContInstituciones;

	private ControladorUsuarios() {
		manejadorUsuarios = ManejadorUsuarios.getInstance();
		IContInstituciones = ControladorInstitucion.getInstance();
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
			String nombreinstitucion, byte[] imagen) throws FormatoContrasenaInvalido, Exception  {
		if (nickname == null || nickname.isEmpty() || contrasena == null || contrasena.isEmpty() || nombre == null || nombre.isEmpty() || apellido == null
				|| apellido.isEmpty() || correo == null || correo.isEmpty() || fechaNacimiento == null) {
			throw new Exception("Nick, contraseña, nombre, apellido, correo y fecha de nacimiento son campos obligatorios");
			/*manejadorUsuarios.agregarAsistente(
			        nickname,
			        contrasena,
			        nombre,
			        correo,
			        apellido,
			        fechaNacimiento,
			        IContInstituciones.getInstitucion(nombreinstitucion),
			        imagen 
			    );*/
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

		if (fechaNacimiento.after(new Date())) {
			throw new Exception("La fecha de nacimiento no puede ser en el futuro.");
		}
		
		if (manejadorUsuarios.existeNick(nickname)) {
			throw new Exception("El nickname del asistente ya existe");
		}

		if (manejadorUsuarios.existeCorreo(correo)) {
			throw new Exception("El correo ya está en uso");
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
				IContInstituciones.getInstitucion(nombreinstitucion), imagen);
	}
	
	@Override
	public void altaOrganizador(String nickname, String contrasena, String nombre, String correo, String descripcion, String link, byte[] imagen)
			throws FormatoContrasenaInvalido, Exception {
		if (nickname == null || nickname.isEmpty() || nombre == null || nombre.isEmpty() || descripcion == null
				|| descripcion.isEmpty() || correo == null || correo.isEmpty()) {
			throw new Exception("Nick, contrasena, nombre, correo y descripción son campos obligatorios");
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
			throw new Exception("El nickname del organizador ya existe");
		}

		if (manejadorUsuarios.existeCorreo(correo)) {
			throw new Exception("El correo ya está en uso");
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
	    Asistente a = manejadorUsuarios.getAsistente(nickname);
	    if (a != null) return a.getImagen();
	    Organizador o = manejadorUsuarios.getOrganizador(nickname);
	    if (o != null) return o.getImagen();
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
		Asistente a = manejadorUsuarios.getAsistente(nickname); // mu = ManejadorUsuarios.getInstance()
		List<DTRegistro> out = new ArrayList<>();
		if (a == null)
			return out;

		ManejadorEventos me = ManejadorEventos.getInstancia();

		for (Registro r : a.getRegistros()) {
			String nombreEd = me.nombreEdicionDeRegistro(r);
			String nombreTipo = me.nombreTipoDeRegistro(r);
			out.add(new DTRegistro(nombreEd, nombreTipo));
		}
		return out;
	}

	@Override
	public DTDetalleRegistro obtenerDetalleRegistro(String nickname, int indice) {
		Asistente a = manejadorUsuarios.getAsistente(nickname);
		if (a == null)
			return null;

		List<Registro> regs = a.getRegistros();
		if (indice < 0 || indice >= regs.size())
			return null;

		Registro r = regs.get(indice);

		ManejadorEventos me = ManejadorEventos.getInstancia();
		String nombreEd = me.nombreEdicionDeRegistro(r);
		String nombreTipo = me.nombreTipoDeRegistro(r);
		
		DTDetalleRegistro dtRegistro = null;
		if (r.getPatrocinio() != null) {
			dtRegistro = new DTDetalleRegistro(nombreEd, a.getNickname(), nombreTipo, r.getFechaRegistro(), r.getCosto(), r.getPatrocinio().getCodigoPatrocinio());
		} else {
			dtRegistro = new DTDetalleRegistro(nombreEd, a.getNickname(), nombreTipo, r.getFechaRegistro(), r.getCosto(), null);
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
	public DTDetalleUsuario mostrarDetalleUsuario(String nicknameUsuario) throws Exception {
		return this.manejadorUsuarios.mostrarDetalleUsuario(nicknameUsuario);
	}
	
	@Override
	public Set<String> nombresOrganizadores() {
		return manejadorUsuarios.getConjuntoOrganizador().keySet();
	}

	@Override
	public List<DTEventoEdicion> getEventosEdicionesDeOrganizador(String nicknameOrganizador) {
		Organizador o = this.manejadorUsuarios.getOrganizador(nicknameOrganizador);
		List<DTEventoEdicion> resultado = new ArrayList<>();
		if (o != null) {
			for (Edicion e: o.getEdiciones()) {
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
		Asistente a = this.manejadorUsuarios.getAsistente(nicknameAsistente);
		if (a != null) {
			Institucion i = a.getInstitucion();
			if (i != null) {
				return i.getNombre();
			}
		}
		return "";
	}

	@Override
	public void modificarDatosAsistente(String nickname, String nuevoNombre, String nuevoApellido,
			Date nuevaFechaNacimiento) throws Exception {
		Asistente asistente = this.manejadorUsuarios.getAsistente(nickname);
		// Asistente no existe en el sistema
		if (asistente == null) return;
		
		// Validación de campos vacíos
		if (nuevoNombre == null || nuevoNombre.isEmpty()) throw new Exception("El nuevo nombre no puede ser vacío.");
		if (nuevoApellido == null || nuevoApellido.isEmpty()) throw new Exception("El nuevo apellido no puede ser vacío.");
		if (nuevaFechaNacimiento == null) throw new Exception("Ingrese una nueva fecha de nacimiento.");
		
		// Ningún campo se modificó
		if (nuevoNombre.equals(asistente.getNombre()) && nuevoApellido.equals(asistente.getApellido()) && nuevaFechaNacimiento.equals(asistente.getNacimiento())) throw new Exception("No se modificó ningún campo.");
		
		// Validación de los datos nuevos
		if (!nuevoNombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) throw new Exception("El nuevo nombre solo puede contener letras y espacios.");
		if (!nuevoApellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) throw new Exception("El nuevo apellido solo puede contener letras y espacios.");
		if (nuevaFechaNacimiento.after(new Date())) throw new Exception("La nueva fecha de nacimiento no puede ser en el futuro.");
		
		// Seteo los campos (notar que si los datos son iguales a los anteriores no se hace nada)
		asistente.setNombre(nuevoNombre);
		asistente.setApellido(nuevoApellido);
		asistente.setNacimiento(nuevaFechaNacimiento);
	}

	@Override
	public void modificarDatosOrganizador(String nickname, String nuevoNombre, String nuevaDescripcion,
			String nuevoLink) throws Exception {
		Organizador organizador = this.manejadorUsuarios.getOrganizador(nickname);
		// Organizador no existe en el sistema
		if (organizador == null) return;
		
		// Validación de campos vacíos
		if (nuevoNombre == null || nuevoNombre.isEmpty()) throw new Exception("El nuevo nombre no puede ser vacío.");
		if (nuevaDescripcion == null || nuevaDescripcion.isEmpty()) throw new Exception("La nueva descripción no puede ser vacía.");
		if (nuevoLink == null || nuevoLink.isEmpty()) throw new Exception("El nuevo link no puede ser vacío.");
		
		// Ningún campo se modificó
		if (nuevoNombre.equals(organizador.getNombre()) && nuevaDescripcion.equals(organizador.getDescripcion()) && nuevoLink.equals(organizador.getLink()) ) throw new Exception("No se modificó ningún campo.");
		
		// Validación de los datos nuevos
		if (!nuevoNombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚñÑ]+)*$")) throw new Exception("El nuevo nombre solo puede contener letras y espacios.");
		if (!nuevoLink.matches("^(https?://).+")) throw new Exception("El nuevo link debe comenzar con http:// o https://.");
		
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
			resultado.add(new DTDetalleAsistente(a.getNickname(), a.getNombre(), a.getCorreo(), a.getApellido(), a.getNacimiento(),insti,a.getImagen()));
		}
		for (Organizador o: organizadores.values()) {
			resultado.add(new DTDetalleOrganizador(o.getNickname(), o.getNombre(), o.getCorreo(), o.getDescripcion(), o.getLink(),o.getImagen()));
		}
		return resultado;
	}

	@Override
	public DTDetalleUsuario validarLogin(String usuario, String contrasena) throws ContrasenaIncorrecta {
		Usuario u = this.manejadorUsuarios.getAsistente(usuario);
		if (u == null) u = this.manejadorUsuarios.getOrganizador(usuario);
		if (u == null) u = this.manejadorUsuarios.getAsistentePorCorreo(usuario);
		if (u == null) u = this.manejadorUsuarios.getOrganizadorPorCorreo(usuario);
		// si el usuario sigue siendo null en este punto entonces no existe en el sistema
		if (u == null) return null;
		// valido que la contraseña sea correcta
		if (!contrasena.equals(u.getContrasena())) {
			throw new ContrasenaIncorrecta("La contraseña es incorrecta.");
		}
		// todas las validaciones ok, retorno el detalle de usuario
		try {
			return this.mostrarDetalleUsuario(u.getNickname());
		} catch (Exception e) {
			return null;
		}
	}
}
