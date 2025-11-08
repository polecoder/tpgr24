package servidor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.datatype.XMLGregorianCalendar;

import datatypes.DTDetallePatrocinio;
import java.io.InputStream;
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
import datatypes.EEstadoEvento;
import datatypes.ENivelPatrocinio;
import excepciones.AporteInsuficiente;
import excepciones.ContrasenaIncorrecta;
import excepciones.DatosTipoRegistroInvalidos;
import excepciones.EdicionFinalizada;
import excepciones.EdicionNoExiste;
import excepciones.EdicionYaArchivada;
import excepciones.EventoFinalizado;
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
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.Endpoint;
import logica.IControladorEventos;
import logica.IControladorInstitucion;
import logica.IControladorUsuarios;
import logica.Institucion;
import logica.Patrocinio;
import logica.Utilidades;
import webwrappers.DTEventoEntry;
import logica.Fabrica;

@WebService 
@XmlSeeAlso({DTEventoEntry.class, datatypes.DTRegistro.class})
@SOAPBinding (style = Style.RPC, parameterStyle = ParameterStyle.WRAPPED)
public class Publicador {
	private Endpoint endpoint = null;
	private Fabrica fabrica = Fabrica.getInstancia();
	private IControladorEventos controladorEventos = fabrica.getControladorEvento();
	private IControladorInstitucion controladorInstitucion = fabrica.getControladorInstitucion();
	private IControladorUsuarios controladorUsuarios = fabrica.getControladorUsuarios();
	
	// Constructor
	public Publicador() {}
	
	/**
	 * Retorna la ip del servidor configurada en config.properties
	 * @return
	 */
	private String getIP() {
		Properties properties = new Properties();
		String ip = "localhost:4230"; // TODO: borrar pero es util para probar en eclipse
        try {
        	InputStream input = new FileInputStream("config.properties");
            properties.load(input);
            ip = properties.getProperty("server.ip");
        } catch (IOException ex) {
            return ip;
        }
        return ip;
	}
	
	// Metodos
	@WebMethod(exclude = true)
	public void publicar() {
		String ip = getIP();
		endpoint = Endpoint.publish(String.format("http://%s/publicador", ip),this);
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
	public void archivarEdicion(String nombreEvento, String nombreEdicion, Date fechaArchivo) throws EdicionFinalizada, EdicionNoExiste, EdicionYaArchivada{
		controladorEventos.archivarEdicion(nombreEvento, nombreEdicion, fechaArchivo);
	}
    

	@WebMethod
	public String[] nombresCategorias(String nombre){
		Set<String> res = controladorEventos.nombresCategorias(nombre);
		return (String[]) res.toArray(new String[0]);
	}
	@WebMethod
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada) {
	    return controladorEventos.getDTEdicion(eventoSeleccionado, edicionSeleccionada);
	}

	@WebMethod
	public DTEventoEntry[] listarEventos() {
		Map<String, DTEvento> res = controladorEventos.listarEventos();
		ArrayList<DTEventoEntry> list = new ArrayList<>();
		for (Map.Entry<String, DTEvento> entry : res.entrySet()) {
			list.add(new DTEventoEntry(entry.getKey(), entry.getValue()));
		}
		return list.toArray(new DTEventoEntry[0]);
	}
	
	@WebMethod
	public DTEvento[] listarEventosEnCurso() {
		Set<DTEvento> res = controladorEventos.listarEventosEnCurso();
		return res.toArray(new DTEvento[0]);
	}
	
	@WebMethod
	public DTEvento[] listarEventosFinalizados() {
		Set<DTEvento> res = controladorEventos.listarEventosFinalizados();
		return res.toArray(new DTEvento[0]);
	}

	@WebMethod
	public DTEdicionEvento[] listarEdicionesEvento(String nombreEvento) {
		Map<String, DTEdicionEvento> res = controladorEventos.listarEdicionesEvento(nombreEvento);
		ArrayList<DTEdicionEvento> list = new ArrayList<>();
		for (Map.Entry<String, DTEdicionEvento> evento : res.entrySet()) {
			list.add(evento.getValue());
		}
		return (DTEdicionEvento[]) list.toArray(new DTEdicionEvento[0]);
	}


	@WebMethod
	public DTDetalleRegistro[] listarDTRegistrosAEdicion(String nombreEvento, String nombreEdicion) {
	    return controladorEventos.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion).toArray(new DTDetalleRegistro[0]);
	}

	@WebMethod
	public String[] getNombresTipoRegistro(String eventoSeleccionado, String edicionSeleccionada) {
	    return controladorEventos.getNombresTipoRegistro(eventoSeleccionado, edicionSeleccionada).toArray(new String[0]);
	}

	@WebMethod
	public HashMap<String, DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion) {
		Map<String, DTTipoRegistro> res = controladorEventos.listarTipoRegistro(nombreEvento, nombreEdicion);
	    return new HashMap<String, DTTipoRegistro> (res);
	}
	
	@WebMethod
	public String[] listarTipoRegistroKeys(String nombreEvento, String nombreEdicion) {
		Map<String, DTTipoRegistro> res = controladorEventos.listarTipoRegistro(nombreEvento, nombreEdicion);
		Set<String> resKeys = res.keySet();
		return resKeys.toArray(new String[0]);
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
	public void altaEdicionEvento(String nombreEvento, String nombre, String sigla, Date fechaInicio, Date fechaFin, Date fechaAlta, String ciudad, String pais, String organizadorNickname, byte[] img, String URL) throws FormularioIncompletoEdicion, EventoFinalizado, EdicionYaArchivada {
		controladorEventos.altaEdicionEvento(nombreEvento, nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, organizadorNickname, img, URL);
	}

	@WebMethod
	public String[] getEdicionesDeOrganizadorPorEstado(String organizadorNickname, EEstadoEdicion estados) {
	    List<String> res = controladorEventos.getEdicionesDeOrganizadorPorEstado(organizadorNickname, estados);
	    return res.toArray(new String[0]);
	}

	@WebMethod
	public String[] listarRegistrosAEdicion(String selectedEvento, String selectedEdicion) {
	    Set<String> res = controladorEventos.listarRegistrosAEdicion(selectedEvento, selectedEdicion);
	    return res.toArray(new String[0]);
	}

	@WebMethod
	public void altaRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion, String nombreEvento, String codigoPatrocinio, Date fecha) throws EventoNoExiste, EdicionNoExiste, FechaFinEsPasada, TipoRegistroNoExiste, UsuarioNoExiste, PatrocinioInvalido, YaAsisteAEdicion {
	    controladorEventos.altaRegistro(nicknameAsistente, nombreTipoRegistro, nombreEdicion, nombreEvento, codigoPatrocinio, fecha);
	}
	
	@WebMethod
	public String[] nombresEdicionesEvento(String nombre) throws EventoNoExiste{
	    Set<String> res = controladorEventos.nombresEdicionesEvento(nombre);
	    return res.toArray(new String[0]);
	}

	@WebMethod
	public String[] nombresEventos() {
	    Set<String> res = controladorEventos.nombresEventos(); // o como lo tengas
	    return res.toArray(new String[0]);
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
	public void altaPatrocinio(String eventoSeleccionado, String edicionSeleccionada, Date FechaAlta, String institucionSeleccionada, ENivelPatrocinio nivelPatrocinio, float aporteEconomico, int cupos, String codigoPatrocinio, String tipoRegistroSeleccionado) throws FormularioIncompletoPatrocinio, AporteInsuficiente, TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste, EdicionFinalizada {
	    controladorEventos.altaPatrocinio(eventoSeleccionado, edicionSeleccionada, FechaAlta, institucionSeleccionada, nivelPatrocinio, aporteEconomico, cupos, codigoPatrocinio, tipoRegistroSeleccionado);
	}
	@WebMethod
	public String[] getListaCategorias() {
		List<String> res = controladorEventos.getListaCategorias();
		return res.toArray(new String[0]);
	}
	@WebMethod
	public String[] listarPatrocinadores(String nombreEvento, String nombreEdicion){
		return controladorEventos.listarPatrocinadores(nombreEvento, nombreEdicion).toArray(new String[0]);
	}
	
	// -------------------	USUARIOS		------------------- //
	@WebMethod
	public DTEventoEdicion[] getEventosEdicionesDeOrganizador(String nicknameOrganizador) {
	    List<DTEventoEdicion> res = controladorUsuarios.getEventosEdicionesDeOrganizador(nicknameOrganizador);
	    return res.toArray(new DTEventoEdicion[0]);
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
	public DTRegistro[] listarRegistrosUsuario(String nickname) {
	    List<DTRegistro> res = controladorUsuarios.listarRegistrosUsuario(nickname);
	    return res.toArray(new DTRegistro[0]);
	}

	@WebMethod
	public DTUsuario[] listarUsuarios() {
		List<DTUsuario> res = controladorUsuarios.listarUsuarios();
	    return res.toArray(new DTUsuario[0]);
	}

	@WebMethod
	public void altaAsistente(String nickname, String contrasena, String nombre, String correo, String apellido, Date fechaNacimiento, String nombreinstitucion, byte[] imagen) throws FormatoContrasenaInvalido, FormularioIncompletoAsistente {
		if (nombreinstitucion != null && (nombreinstitucion.isBlank() || "-".equals(nombreinstitucion))) {
	        nombreinstitucion = null;
	    }
	    controladorUsuarios.altaAsistente(nickname, contrasena, nombre, correo, apellido, fechaNacimiento, nombreinstitucion, imagen);
	}

	@WebMethod
	public void altaOrganizador(String nickname, String contrasena, String nombre, String correo, String descripcion, String link, byte[] imagen) throws FormatoContrasenaInvalido, FormularioIncompletoOrganizador {
	    controladorUsuarios.altaOrganizador(nickname, contrasena, nombre, correo, descripcion, link, imagen);
	}
	
	@WebMethod
	public DTDetalleUsuario[] listarDetalleUsuarios() {
		List<DTDetalleUsuario> res = controladorUsuarios.listarDetalleUsuarios();
		return res.toArray(new DTDetalleUsuario[0]);
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
	public String[] nombresInstituciones() {
		Set<String> res = controladorInstitucion.nombresInstituciones();
	    return res.toArray(new String[0]);
	}
	@WebMethod
	public void finalizarEvento(String nombre) throws EventoNoExiste, EventoFinalizado {
			controladorEventos.finalizarEvento(nombre);
	}
	
	@WebMethod
	public void altaEvento(String nombre, String descripcion, String sigla, String[] catSel, byte[] imagen, EEstadoEvento enCurso) { 
		java.util.List<String> categorias = new java.util.ArrayList<>();
		if (catSel != null) {
			categorias = java.util.Arrays.asList(catSel);
		}
		controladorEventos.altaEvento(nombre, descripcion, new Date(), sigla, categorias, imagen, enCurso,0);
	}
}