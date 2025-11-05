package com.eventosuy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import datatypes.DTDetalleRegistro;
import datatypes.DTDetalleUsuario;
import datatypes.DTEdicionEvento;
import datatypes.DTTipoRegistro;
import excepciones.EdicionFinalizada;
import excepciones.EdicionNoExiste;
import excepciones.EdicionYaArchivada;
import excepciones.UsuarioNoExiste;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorUsuarios;

@WebServlet("/Ediciones")
@MultipartConfig
public class Ediciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Ediciones () {super();} // Constructor (no lleva parametros)
	
	private final static Fabrica fabrica = Fabrica.getInstancia();
	private final static IControladorEventos controladorEventos = fabrica.getControladorEvento();
	private final static IControladorUsuarios controladorUsuarios = fabrica.getControladorUsuarios();
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		String accion = request.getParameter("accion"); // Llamo distinto JSP dependiendo del Caso de Uso
		if (accion == null) { accion = (String) request.getAttribute("accion"); } // Test
		switch (accion) {
		case "ConsultaEdicion":
			request = CUConsultaEdicion(request, response);
			request.getRequestDispatcher("/WEB-INF/views/ediciones/consulta-edicion.jsp").forward(request, response);
			break;
		case "AltaEdicion":
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				CUAltaEdicion(request, response); // Crea la Edicion y muestra su consulta
			} else { 
				byte[] image = controladorEventos.getDTEvento(request.getParameter("nombre_evento")).getImagen();
				request.setAttribute("imagen_edicion", image);
				request.getRequestDispatcher("/WEB-INF/views/ediciones/alta-edicion.jsp").forward(request, response); // Abre el formulario
			}
			break;
		case "AltaTipoRegistro":
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				CUAltaTipoRegistro(request, response); // Crea el TipoRegistro y muestra su consulta
			} else { 
				byte[] image = controladorEventos.getDTEdicion(request.getParameter("nombre_evento"), request.getParameter("nombre_edicion")).getImagen();
				request.setAttribute("imagen_edicion", image);
				request.getRequestDispatcher("/WEB-INF/views/registros/alta-tipo-registro.jsp").forward(request, response); // Abre el formulario
			}
			break;
		case "ConsultaTipoRegistro":
			request = CUConsultaTipoRegistro(request, response);
			request.getRequestDispatcher("/WEB-INF/views/registros/consulta-tipo-registro.jsp").forward(request, response);
			break;
		case "ArchivarEdicion":
			CUArchivarEdicion(request, response);
			break;
		default:
			request.getRequestDispatcher("/WEB-INF/error.jsp");
		}
	}
	
	private HttpServletRequest CUConsultaEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreEdicion = request.getParameter("nombre_edicion");
		if (nombreEdicion == null) { nombreEdicion = (String) request.getAttribute("nombre_edicion"); }
		
		String nombreEvento = request.getParameter("nombre_evento");
		if (nombreEvento == null) { nombreEvento = (String) request.getAttribute("nombre_evento"); }
		
		if (nombreEvento == null) { 
			for (String evento : controladorEventos.listarEventos().keySet()) {
				if (controladorEventos.listarEdicionesEvento(evento).containsKey(nombreEdicion)) {
					nombreEvento = evento;
					request.setAttribute("nombre_evento", nombreEvento);
					break;
				}
			}
		}
		
		// TODO: Revisar si esto rompe algo (necesario para ediciones archivadas)
//		if (nombreEvento == null) {
//			request.setAttribute("error_message", "La edicion no es de ningun evento");
//			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
//			return request;
//		}
		
		// todo esto contempla consultas a ediciones archivadas
		DTEdicionEvento dtEdicion = controladorEventos.getDTEdicion(nombreEvento, nombreEdicion); // asumo tiene la imagen
		Set<String> setPatrocinios = controladorEventos.listarPatrocinadores(nombreEvento, nombreEdicion);
		String videoURL = dtEdicion.getVideoURL();
		Set<String> setTiposRegistro = controladorEventos.getNombresTipoRegistro(nombreEvento, nombreEdicion);
		Set<DTDetalleRegistro> setRegistros = controladorEventos.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion);
		Map<String, DTDetalleRegistro> asistentesRegistrados = new HashMap<>();
		for (DTDetalleRegistro dtRegistro : setRegistros) {
			asistentesRegistrados.put(dtRegistro.getNicknameAsistente(), dtRegistro);
		}
		
		request.setAttribute("detalle_edicion", dtEdicion);
		request.setAttribute("lista_patrocinios", setPatrocinios);
		request.setAttribute("lista_tipos_registro", setTiposRegistro);
		request.setAttribute("finalizada", false);
		request.setAttribute("videoURL", videoURL);
		if (dtEdicion.getFechaFin().before(new Date())) { 
			request.setAttribute("finalizada", true);
		}
		
		DTDetalleUsuario dtUsuario = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
		String nickname = null;
		if (dtUsuario != null) {nickname = dtUsuario.getNickname();}
		if (nickname != null && nickname.equals(dtEdicion.getOrganizadorNickname())) {
			request.setAttribute("lista_registros", setRegistros);
		} else if (nickname != null && asistentesRegistrados.keySet().contains(nickname)) {
			request.setAttribute("detalle_registro", asistentesRegistrados.get(nickname));
		}
		
		DTEdicionEvento ed =controladorEventos.getDTEdicion(nombreEvento, nombreEdicion);
		try {
			DTDetalleUsuario u =controladorUsuarios.mostrarDetalleUsuario(ed.getOrganizadorNickname());
			request.setAttribute("imagenOrg",u.getImagen());
		} catch (UsuarioNoExiste e) {
			//no va a entrar aca nunca
			e.printStackTrace();
		}
		return request;
	}
	
	private void CUAltaEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
		String nombreEvento = request.getParameter("nombre_evento");
		if (nombreEvento == null) { nombreEvento = (String) request.getAttribute("nombre_evento"); }
		
		String nombreEdicion = request.getParameter("nombre_edicion");
		String sigla = request.getParameter("sigla");
		String URL = request.getParameter("URL");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaInicio = formatter.parse(request.getParameter("fecha_inicio"));
		Date fechaFin = formatter.parse(request.getParameter("fecha_fin"));
		Date fechaAlta = new Date();
		String ciudad = request.getParameter("ciudad");
		String pais = request.getParameter("pais");
		DTDetalleUsuario dtUsuario = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
		String nickOrganizador = null;
		if (dtUsuario != null) {nickOrganizador = dtUsuario.getNickname();}
		Part fotoPart = request.getPart("foto");
		byte[] imagenBytes = null;
        if (fotoPart != null && fotoPart.getSize() > 0) {
            try (InputStream inputStream = fotoPart.getInputStream()) {
                imagenBytes = inputStream.readAllBytes();
            }
        }
		
		try {
			controladorEventos.altaEdicionEvento(nombreEvento, nombreEdicion, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, nickOrganizador, imagenBytes, URL);
		} catch (Exception ex) {
			byte[] image = controladorEventos.getDTEvento(request.getParameter("nombre_evento")).getImagen();
			request.setAttribute("imagen_edicion", image);
			request.setAttribute("error_message", ex.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/ediciones/alta-edicion.jsp").forward(request, response);
			return;
		}
		
		request.setAttribute("nombre_edicion", nombreEdicion);
		request = CUConsultaEdicion(request, response);
		request.getRequestDispatcher("/WEB-INF/views/ediciones/consulta-edicion.jsp").forward(request, response);
	}
	
	private HttpServletRequest CUConsultaTipoRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreEvento = request.getParameter("nombre_evento");
		if (nombreEvento == null) { nombreEvento = (String) request.getAttribute("nombre_evento"); }
		String nombreEdicion = request.getParameter("nombre_edicion");
		if (nombreEdicion == null) { nombreEdicion = (String) request.getAttribute("nombre_edicion"); }
		String nombreTipoRegistro = request.getParameter("nombre_tipo_registro");
		if (nombreTipoRegistro == null) { nombreTipoRegistro = (String) request.getAttribute("nombre_tipo_registro"); }
		
		DTTipoRegistro dtTipoRegistro = null;
		 try {
		        dtTipoRegistro = controladorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro);
		        request.setAttribute("detalle_tipo_registro", dtTipoRegistro);
		    } catch (Exception ex) {
		        request.setAttribute("error_tipo_registro", "El nombre del tipo de registro ya está en uso");
		    }
		byte[] imagenEdicion = controladorEventos.getDTEdicion(nombreEvento, nombreEdicion).getImagen();
		request.setAttribute("imagenEdicion", imagenEdicion);
		request.setAttribute("detalle_tipo_registro", dtTipoRegistro);
		return request;
	}
	
	private void CUAltaTipoRegistro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreEvento = request.getParameter("nombre_evento");
		if (nombreEvento == null) { nombreEvento = (String) request.getAttribute("nombre_evento"); }
		String nombreEdicion = request.getParameter("nombre_edicion");
		if (nombreEdicion == null) { nombreEdicion = (String) request.getAttribute("nombre_edicion"); }
		
		String nombreTipoRegistro = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		float costo = Float.parseFloat(request.getParameter("costo"));
		int cuposMax = Integer.parseInt(request.getParameter("cupos_max"));
		request.setAttribute("error_tipo_registro",null);
		try {
			controladorEventos.altaTipoRegistro(nombreTipoRegistro, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);		
		} catch (Exception ex) {
			byte[] image = controladorEventos.getDTEdicion(request.getParameter("nombre_evento"), request.getParameter("nombre_edicion")).getImagen();
			request.setAttribute("imagen_edicion", image);
			request.setAttribute("error_tipo_registro", "Ya existe un tipo de registro con ese nombre.");
			request.getRequestDispatcher("/WEB-INF/views/registros/alta-tipo-registro.jsp").forward(request, response);
			return;
		}
		request.setAttribute("nombre_tipo_registro", nombreTipoRegistro);
		request = CUConsultaTipoRegistro(request, response);
		request.getRequestDispatcher("/WEB-INF/views/registros/consulta-tipo-registro.jsp").forward(request, response);
	}
	
	private void CUArchivarEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreEvento = request.getParameter("nombre_evento");
		if (nombreEvento == null) nombreEvento = (String) request.getAttribute("nombre_evento");
		String nombreEdicion = request.getParameter("nombre_edicion");
		if (nombreEdicion == null) nombreEdicion = (String) request.getAttribute("nombre_edicion");
		
		// setear todos los atributos usados por el .jsp
		DTEdicionEvento detalleEdicion = controladorEventos.getDTEdicion(nombreEvento, nombreEdicion);
		request.setAttribute("detalle_edicion", detalleEdicion);
		
		Set<String> setPatrocinios = controladorEventos.listarPatrocinadores(nombreEvento, nombreEdicion);
		request.setAttribute("lista_patrocinios", setPatrocinios);
		
		Set<String> setTiposRegistro = controladorEventos.getNombresTipoRegistro(nombreEvento, nombreEdicion);
		request.setAttribute("lista_tipos_registro", setTiposRegistro);
		
		String videoURL = detalleEdicion.getVideoURL();
		request.setAttribute("videoURL", videoURL);
		
		request.setAttribute("finalizada", false);
		if (detalleEdicion.getFechaFin().before(new Date())) { 
			request.setAttribute("finalizada", true);
		}
		
		Set<DTDetalleRegistro> setRegistros = controladorEventos.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion);
		Map<String, DTDetalleRegistro> asistentesRegistrados = new HashMap<>();
		for (DTDetalleRegistro dtRegistro : setRegistros) {
			asistentesRegistrados.put(dtRegistro.getNicknameAsistente(), dtRegistro);
		}
		DTDetalleUsuario dtUsuario = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
		String nickname = null;
		if (dtUsuario != null) {nickname = dtUsuario.getNickname();}
		if (nickname != null && nickname.equals(detalleEdicion.getOrganizadorNickname())) {
			request.setAttribute("lista_registros", setRegistros);
		} else if (nickname != null && asistentesRegistrados.keySet().contains(nickname)) {
			request.setAttribute("detalle_registro", asistentesRegistrados.get(nickname));
		}
		// imagen organizador
		try {
			DTDetalleUsuario u = controladorUsuarios.mostrarDetalleUsuario(detalleEdicion.getOrganizadorNickname());
			request.setAttribute("imagenOrg", u.getImagen());
		} catch (UsuarioNoExiste e) {
			e.printStackTrace();
		}
		
		// parte lógica
		try {
			controladorEventos.archivarEdicion(nombreEvento, nombreEdicion, new Date());
		} catch (EdicionFinalizada exception) {
			request.setAttribute("error", "La edición seleccionada ya finalizó.");
			request.getRequestDispatcher("/WEB-INF/views/ediciones/consulta-edicion.jsp").forward(request, response);
			return;
		} catch (EdicionNoExiste exception) {
			request.setAttribute("error", "La edición seleccionada no existe en el sistema.");
			request.getRequestDispatcher("/WEB-INF/views/ediciones/consulta-edicion.jsp").forward(request, response);
			return;
		} catch (EdicionYaArchivada exception) {
			request.setAttribute("error", "La edición seleccionada ya está archivada.");
			request.getRequestDispatcher("/WEB-INF/views/ediciones/consulta-edicion.jsp").forward(request, response);
			return;
		}
		
		// el archivo fue éxitoso
		request.setAttribute("archivado", "La edición se archivó correctamente.");
		response.sendRedirect(request.getContextPath() + "/Ediciones?accion=ConsultaEdicion&nombre_evento=" + nombreEvento + "&nombre_edicion=" + nombreEdicion);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
