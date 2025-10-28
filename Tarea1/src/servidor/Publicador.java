package servidor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datatypes.DTDetallePatrocinio;
import datatypes.DTDetalleRegistro;
import datatypes.DTDetalleUsuario;
import datatypes.DTEdicionEvento;
import datatypes.DTEvento;
import datatypes.DTEventoEdicion;
import datatypes.DTInstitucion;
import datatypes.DTRegistro;
import datatypes.DTTipoRegistro;
import datatypes.DTUsuario;
import datatypes.EEstadoEdicion;
import datatypes.ENivelPatrocinio;
import excepciones.AporteInsuficiente;
import excepciones.ContrasenaIncorrecta;
import excepciones.DatosTipoRegistroInvalidos;
import excepciones.EdicionFinalizada;
import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.FechaFinEsPasada;
import excepciones.FormatoContrasenaInvalido;
import excepciones.FormularioIncompletoAsistente;
import excepciones.FormularioIncompletoEdicion;
import excepciones.FormularioIncompletoInstitucion;
import excepciones.FormularioIncompletoOrganizador;
import excepciones.FormularioIncompletoPatrocinio;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.PatrocinioInvalido;
import excepciones.TipoRegistroNoExiste;
import excepciones.UsuarioNoExiste;
import excepciones.YaAsisteAEdicion;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.jws.soap.SOAPBinding.Style;
import jakarta.xml.ws.Endpoint;
import logica.IControladorEventos;
import logica.IControladorInstitucion;
import logica.IControladorUsuarios;
import logica.Institucion;
import logica.Patrocinio;
import logica.Fabrica;

@WebService 
@SOAPBinding (style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class Publicador {
	private Endpoint endpoint = null;
	private Fabrica fabrica = Fabrica.getInstancia();
	private IControladorEventos controladorEventos = fabrica.getControladorEvento();
	private IControladorInstitucion controladorInstitucion = fabrica.getControladorInstitucion();
	private IControladorUsuarios controladorUsuarios = fabrica.getControladorUsuarios();
	
	// Constructor
	public Publicador() {}
	
	// Metodos
	@WebMethod(exclude = true)
	public void publicar() {
		endpoint = Endpoint.publish("http://localhost:4230/publicador",this);
	}
	@WebMethod(exclude = true)
	public Endpoint getEndpoint() {
		return this.endpoint;
	}
	
	// -------------------	TEST		------------------- //
	@WebMethod
	public String helloWorld() {
		return "Hello World! Client!";
	}
	
	// -------------------	EVENTOS		------------------- //
	@WebMethod
	public DTEvento getDTEvento(String nombre) {
	    return controladorEventos.getDTEvento(nombre);
	}

	@WebMethod
	public HashSet<String> nombresCategorias(String nombre){
		Set<String> res = controladorEventos.nombresCategorias(nombre);
		return new HashSet<String>(res);
	}
	@WebMethod
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada) {
	    return controladorEventos.getDTEdicion(eventoSeleccionado, edicionSeleccionada);
	}

	@WebMethod
	public HashMap<String, DTEvento> listarEventos() {
		Map<String, DTEvento> res = controladorEventos.listarEventos();
	    return new HashMap<String, DTEvento>(res);
	}

	@WebMethod
	public HashMap<String, DTEdicionEvento> listarEdicionesEvento(String nombreEvento) {
		Map<String, DTEdicionEvento> res = controladorEventos.listarEdicionesEvento(nombreEvento);
	    return new HashMap<String, DTEdicionEvento> (res);
	}

	@WebMethod
	public HashSet<DTDetalleRegistro> listarDTRegistrosAEdicion(String nombreEvento, String nombreEdicion) {
		Set<DTDetalleRegistro> res = controladorEventos.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion);
	    return new HashSet<DTDetalleRegistro> (res);
	}

	@WebMethod
	public HashSet<String> getNombresTipoRegistro(String eventoSeleccionado, String edicionSeleccionada) {
		Set<String> res = controladorEventos.getNombresTipoRegistro(eventoSeleccionado, edicionSeleccionada);
	    return new HashSet<String>(res);
	}

	@WebMethod
	public HashMap<String, DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion) {
		Map<String, DTTipoRegistro> res = controladorEventos.listarTipoRegistro(nombreEvento, nombreEdicion);
	    return new HashMap<String, DTTipoRegistro> (res);
	}

	@WebMethod
	public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento, String nombreEdicion) throws NombreTipoRegistroRepetido, DatosTipoRegistroInvalidos {
	    controladorEventos.altaTipoRegistro(nombre, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
	}

	@WebMethod
	public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombre) throws TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste {
	    return controladorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombre);
	}

	@WebMethod
	public void altaEdicionEvento(String nombreEvento, String nombre, String sigla, Date fechaInicio, Date fechaFin, Date fechaAlt, String ciudad, String pais, String organizadorNickname, byte[] img) throws FormularioIncompletoEdicion {
	    controladorEventos.altaEdicionEvento(nombreEvento, nombre, sigla, fechaInicio, fechaFin, fechaAlt, ciudad, pais, organizadorNickname, img);
	}

	@WebMethod
	public ArrayList<String> getEdicionesDeOrganizadorPorEstado(String organizadorNickname, EEstadoEdicion estados) {
		List<String> res = controladorEventos.getEdicionesDeOrganizadorPorEstado(organizadorNickname, estados);
	    return new ArrayList<String> (res);
	}

	@WebMethod
	public HashSet<String> listarRegistrosAEdicion(String selectedEvento, String selectedEdicion) {
		Set<String> res = controladorEventos.listarRegistrosAEdicion(selectedEvento, selectedEdicion);
	    return new HashSet<String> (res);
	}

	@WebMethod
	public void altaRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String nombreEvento, String codigoPatrocinio, Date fecha) throws EventoNoExiste, EdicionNoExiste, FechaFinEsPasada, TipoRegistroNoExiste, UsuarioNoExiste, PatrocinioInvalido, YaAsisteAEdicion {
	    controladorEventos.altaRegistro(nicknameAsistente, nombreTipoRegistro, nombreEdicion, nombreEvento, codigoPatrocinio, fecha);
	}

	@WebMethod
	public HashSet<String> nombresEdicionesEvento(String nombre) throws EventoNoExiste {
		Set<String> res = controladorEventos.nombresEdicionesEvento(nombre);
	    return new HashSet<String> (res);
	}

	@WebMethod
	public HashSet<String> nombresEventos() {
		Set<String> res = controladorEventos.nombresEventos();
	    return new HashSet<String>(res);
	}
	
	@WebMethod
	public DTDetallePatrocinio mostrarDetallePatrocinio(String nombreEvento, String nombreEdicion, String codigoPatrocinio) {
	    return controladorEventos.mostrarDetallePatrocinio(nombreEvento, nombreEdicion, codigoPatrocinio);
	}
	@WebMethod
	public boolean existeEvento(String nombreEvento) {
		return controladorEventos.existeEvento(nombreEvento);
	}

	@WebMethod
	public Patrocinio getPatrocinio(String nombreEvento, String nombreEdicion, String patrocinador) {
	    return controladorEventos.getPatrocinio(nombreEvento, nombreEdicion, patrocinador);
	}
	
	@WebMethod
	public void altaEvento(String nombre, String descripcion, Date fechaAlta, String sigla, ArrayList<String> categorias, byte[] imagen) {
	    controladorEventos.altaEvento(nombre, descripcion, fechaAlta, sigla, categorias, imagen);
	}

	@WebMethod
	public void altaPatrocinio(String eventoSeleccionado, String edicionSeleccionada, Date FechaAlta, String institucionSeleccionada, ENivelPatrocinio nivelPatrocinio, float aporteEconomico, int cupos, String codigoPatrocinio, String tipoRegistroSeleccionado) throws FormularioIncompletoPatrocinio, AporteInsuficiente, TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste, EdicionFinalizada {
	    controladorEventos.altaPatrocinio(eventoSeleccionado, edicionSeleccionada, FechaAlta, institucionSeleccionada, nivelPatrocinio, aporteEconomico, cupos, codigoPatrocinio, tipoRegistroSeleccionado);
	}
	@WebMethod
	public ArrayList<String> getListaCategorias() {
		List<String> res = controladorEventos.getListaCategorias();
		return new ArrayList<String> (res);
	}
	@WebMethod
	public HashSet<String> listarPatrocinadores(String nombreEvento, String nombreEdicion){
		Set<String> res = controladorEventos.listarPatrocinadores(nombreEvento, nombreEdicion);
		return new HashSet<String>(res);
	}
	
	// -------------------	USUARIOS		------------------- //
	@WebMethod
	public ArrayList<DTEventoEdicion> getEventosEdicionesDeOrganizador(String nicknameOrganizador){
		List<DTEventoEdicion> res = controladorUsuarios.getEventosEdicionesDeOrganizador(nicknameOrganizador);
		return new ArrayList<DTEventoEdicion>(res);
	}
	@WebMethod
	public DTDetalleUsuario validarLogin(String usuario, String contrasena) throws ContrasenaIncorrecta{
		return controladorUsuarios.validarLogin(usuario, contrasena);
	}
	
	@WebMethod
	public DTDetalleUsuario mostrarDetalleUsuario(String nicknameUsuario) throws UsuarioNoExiste {
	    return controladorUsuarios.mostrarDetalleUsuario(nicknameUsuario);
	}

	@WebMethod
	public DTDetalleRegistro obtenerDetalleRegistro(String nickname, int indice) {
	    return controladorUsuarios.obtenerDetalleRegistro(nickname, indice);
	}

	@WebMethod
	public String getNombreInstitucionAsistente(String nickname) {
	    return controladorUsuarios.getNombreInstitucionAsistente(nickname);
	}

	@WebMethod
	public void modificarDatosOrganizador(String nickname, String nuevoNombre, String nuevaDescripcion, String nuevoLink) throws FormularioIncompletoOrganizador {
	    controladorUsuarios.modificarDatosOrganizador(nickname, nuevoNombre, nuevaDescripcion, nuevoLink);
	}

	@WebMethod
	public void modificarDatosAsistente(String nickname, String nuevoNombre, String nuevoApellido, Date nuevaFechaNacimiento) throws FormularioIncompletoAsistente {
	    controladorUsuarios.modificarDatosAsistente(nickname, nuevoNombre, nuevoApellido, nuevaFechaNacimiento);
	}

	@WebMethod
	public ArrayList<DTRegistro> listarRegistrosUsuario(String nickname) {
		List<DTRegistro> res = controladorUsuarios.listarRegistrosUsuario(nickname);
	    return new ArrayList<DTRegistro>(res);
	}

	@WebMethod
	public ArrayList<DTUsuario> listarUsuarios() {
		List<DTUsuario> res = controladorUsuarios.listarUsuarios();
	    return new ArrayList<DTUsuario> (res);
	}

	@WebMethod
	public void altaAsistente(String nickname, String contrasena, String nombre, String correo, String apellido, Date fechaNacimiento, String nombreinstitucion, byte[] imagen) throws FormatoContrasenaInvalido, FormularioIncompletoAsistente {
	    controladorUsuarios.altaAsistente(nickname, contrasena, nombre, correo, apellido, fechaNacimiento, nombreinstitucion, imagen);
	}

	@WebMethod
	public void altaOrganizador(String nickname, String contrasena, String nombre, String correo, String descripcion, String link, byte[] imagen) throws FormatoContrasenaInvalido, FormularioIncompletoOrganizador {
	    controladorUsuarios.altaOrganizador(nickname, contrasena, nombre, correo, descripcion, link, imagen);
	}
	
	@WebMethod
	public ArrayList<DTDetalleUsuario> listarDetalleUsuarios() {
		List<DTDetalleUsuario> res = controladorUsuarios.listarDetalleUsuarios();
		return new ArrayList<DTDetalleUsuario> (res);
	}
	
	// -------------------	INSTITUCIONES		------------------- //
	@WebMethod
	public HashSet<DTInstitucion> listarInstituciones() {
		Set<DTInstitucion> res = controladorInstitucion.listarInstituciones();
	    return new HashSet<DTInstitucion> (res);
	}

	@WebMethod
	public void altaInstitucion(String nombre, String descripcion, String sitioWeb) throws FormularioIncompletoInstitucion {
	    controladorInstitucion.altaInstitucion(nombre, descripcion, sitioWeb);
	}

	@WebMethod
	public Institucion getInstitucion(String nombre) {
	    return controladorInstitucion.getInstitucion(nombre);
	}

	@WebMethod
	public HashSet<String> nombresInstituciones() {
		Set<String> res = controladorInstitucion.nombresInstituciones();
	    return new HashSet<String> (res);
	}
}