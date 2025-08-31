package logica;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import logica.datatypes.DTAsistente;
import logica.datatypes.DTDetalleRegistro;
import logica.datatypes.DTUsuario;
import logica.datatypes.DTRegistro;

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

	public void altaAsistente(String nickname, String nombre, String correo, String apellido, Date fechaNacimiento,
			String nombreinstitucion) throws Exception {
		if (nickname == null || nickname.isEmpty() || nombre == null || nombre.isEmpty() || apellido == null
				|| apellido.isEmpty() || correo == null || correo.isEmpty() || fechaNacimiento == null) {
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

		manejadorUsuarios.agregarAsistente(nickname, nombre, correo, apellido, fechaNacimiento,
				IContInstituciones.getInstitucion(nombreinstitucion));
	}

	public void altaOrganizador(String nickname, String nombre, String correo, String descripcion, String link)
			throws Exception {
		if (nickname == null || nickname.isEmpty() || nombre == null || nombre.isEmpty() || descripcion == null
				|| descripcion.isEmpty() || correo == null || correo.isEmpty()) {
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

	public Map<String, DTAsistente> listarAsistentes() {
		return manejadorUsuarios.listarAsistentes();
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

	private final ManejadorEventos me = ManejadorEventos.getInstancia();

	private String nombreTipoDe(Registro r) {
		for (Evento ev : me.getEventos().values()) { // getEventos() público o package
			for (Edicion ed : ev.getEdiciones().values()) { // idem getEdiciones()
				for (TipoRegistro tr : ed.getTiposRegistro().values()) {
					if (tr.getRegistros().contains(r)) {
						return tr.getNombre();
					}
				}
			}
		}
		return "(sin tipo)"; // fallback para no romper
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

		return new DTDetalleRegistro(nombreEd, a.getNickname(), nombreTipo, r.getFechaRegistro(), r.getCosto());
	}

	@Override
	public Set<String> nombresOrganizadores() {
		return manejadorUsuarios.getConjuntoOrganizador().keySet();
	}
}

