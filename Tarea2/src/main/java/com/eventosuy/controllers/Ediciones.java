package com.eventosuy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.eventosuy.servidor.Publicador;
import com.eventosuy.servidor.PublicadorService;
import com.eventosuy.servidor.UsuarioNoExiste_Exception;
import com.eventosuy.servidor.DTEdicionEvento;
import com.eventosuy.servidor.DTDetalleUsuario;
import com.eventosuy.servidor.DTDetalleRegistro;
import com.eventosuy.servidor.EdicionFinalizada;
import com.eventosuy.servidor.EdicionFinalizada_Exception;
import com.eventosuy.servidor.EdicionNoExiste;
import com.eventosuy.servidor.EdicionNoExiste_Exception;
import com.eventosuy.servidor.EdicionYaArchivada;
import com.eventosuy.servidor.EdicionYaArchivada_Exception;
import com.eventosuy.servidor.DTTipoRegistro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import com.eventosuy.utils.Utilidades;

@WebServlet("/Ediciones")
@MultipartConfig
public class Ediciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Ediciones () {super();} // Constructor (no lleva parametros)
	
	// Servicios
	private final static PublicadorService servicio = new PublicadorService();
	private final static Publicador puerto = servicio.getPublicadorPort();
	
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
				byte[] image = puerto.getDTEvento(request.getParameter("nombre_evento")).getImagen();
				request.setAttribute("imagen_edicion", image);
				request.getRequestDispatcher("/WEB-INF/views/ediciones/alta-edicion.jsp").forward(request, response); // Abre el formulario
			}
			break;
		case "AltaTipoRegistro":
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				CUAltaTipoRegistro(request, response); // Crea el TipoRegistro y muestra su consulta
			} else { 
				byte[] image = puerto.getDTEdicion(request.getParameter("nombre_evento"), request.getParameter("nombre_edicion")).getImagen();
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
	
	private HttpServletRequest CUConsultaEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DatatypeConfigurationException {
		String nombreEdicion = request.getParameter("nombre_edicion");
		if (nombreEdicion == null) { nombreEdicion = (String) request.getAttribute("nombre_edicion"); }
		
		String nombreEvento = request.getParameter("nombre_evento");
		if (nombreEvento == null) { nombreEvento = (String) request.getAttribute("nombre_evento"); }
		
		if (nombreEvento == null) { 
		    try {
		        com.eventosuy.servidor.DTEventoEntryArray eventosArray = puerto.listarEventos();
		        List<com.eventosuy.servidor.DTEventoEntry> eventos = eventosArray.getItem();
		        
		        if (eventos != null) {
		            for (com.eventosuy.servidor.DTEventoEntry eventoEntry : eventos) {
		                String evento = eventoEntry.getKey(); // El nombre del evento
		                
		                com.eventosuy.servidor.DTEdicionEventoArray edicionesArray = puerto.listarEdicionesEvento(evento);
		                List<com.eventosuy.servidor.DTEdicionEvento> ediciones = edicionesArray.getItem();
		                
		                if (ediciones != null) {
		                    for (com.eventosuy.servidor.DTEdicionEvento ed : ediciones) {
		                        if (ed.getNombre().equals(nombreEdicion)) {
		                            nombreEvento = evento;
		                            request.setAttribute("nombre_evento", nombreEvento);
		                            break;
		                        }
		                    }
		                }
		                
		                if (nombreEvento != null) break;
		            }
		        }
		    } catch (Exception e) {
		        log("Error buscando evento: " + e.getMessage());
		    }
		}
		
		if (nombreEvento == null) {
			request.setAttribute("error_message", "La edicion no es de ningun evento");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
			return request;
		}
		
		DTEdicionEvento dtEdicion = puerto.getDTEdicion(nombreEvento, nombreEdicion); // Asumo tiene la imagen
		List<String> setPatrocinios = puerto.listarPatrocinadores(nombreEvento, nombreEdicion).getItem();
		String videoURL = dtEdicion.getVideoURL();
		List<String> setTiposRegistro = puerto.getNombresTipoRegistro(nombreEvento, nombreEdicion).getItem();
		List<DTDetalleRegistro> setRegistros = puerto.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion).getItem();
		Map<String, DTDetalleRegistro> asistentesRegistrados = new HashMap<>();
		for (DTDetalleRegistro dtRegistro : setRegistros) {
			asistentesRegistrados.put(dtRegistro.getNicknameAsistente(), dtRegistro);
		}
		
		request.setAttribute("detalle_edicion", dtEdicion);
		request.setAttribute("lista_patrocinios", setPatrocinios);
		request.setAttribute("lista_tipos_registro", setTiposRegistro);
		request.setAttribute("finalizada", false);
		request.setAttribute("videoURL", videoURL);
		
		GregorianCalendar hoy = new GregorianCalendar();
		XMLGregorianCalendar xmlHoy = DatatypeFactory.newInstance().newXMLGregorianCalendar(hoy);
		
		if (dtEdicion.getFechaFin().compare(xmlHoy) == DatatypeConstants.LESSER) { 
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
		
		DTEdicionEvento ed =puerto.getDTEdicion(nombreEvento, nombreEdicion);
		try {
			DTDetalleUsuario u = puerto.mostrarDetalleUsuario(ed.getOrganizadorNickname());
			request.setAttribute("imagenOrg",u.getImagen());
		} catch (Exception e) {
			//no va a entrar aca nunca
			e.printStackTrace();
		}
		return request;
	}
	
	private void CUAltaEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, DatatypeConfigurationException {
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
		byte[] imagenBytes = new byte[0];
        if (fotoPart != null && fotoPart.getSize() > 0) {
            try (InputStream inputStream = fotoPart.getInputStream()) {
                imagenBytes = inputStream.readAllBytes();
            }
        }
		
		try {
			XMLGregorianCalendar xmlGregorianCalendarFechaInicio = Utilidades.dateToXMLGregorianCalendar(fechaInicio);
			XMLGregorianCalendar xmlGregorianCalendarFechaFin = Utilidades.dateToXMLGregorianCalendar(fechaFin);
			XMLGregorianCalendar xmlGregorianCalendarFechaAlta = Utilidades.dateToXMLGregorianCalendar(fechaAlta);
			
			puerto.altaEdicionEvento(nombreEvento, nombreEdicion, sigla,
					xmlGregorianCalendarFechaInicio, xmlGregorianCalendarFechaFin, xmlGregorianCalendarFechaAlta, 
					ciudad, pais, nickOrganizador, imagenBytes, URL);
			
		} catch (Exception ex) {
			
		    ex.printStackTrace();
			
			byte[] image = puerto.getDTEvento(request.getParameter("nombre_evento")).getImagen();
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
		        dtTipoRegistro = puerto.getDTTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro);
		        request.setAttribute("detalle_tipo_registro", dtTipoRegistro);
		    } catch (Exception ex) {
		        request.setAttribute("error_tipo_registro", "El nombre del tipo de registro ya está en uso");
		    }
		byte[] imagenEdicion = puerto.getDTEdicion(nombreEvento, nombreEdicion).getImagen();
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
			puerto.altaTipoRegistro(nombreTipoRegistro, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);		
		} catch (Exception ex) {
			byte[] image = puerto.getDTEdicion(request.getParameter("nombre_evento"), request.getParameter("nombre_edicion")).getImagen();
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
		DTEdicionEvento detalleEdicion = puerto.getDTEdicion(nombreEvento, nombreEdicion);
		request.setAttribute("detalle_edicion", detalleEdicion);
		
		List<String> setPatrocinios = puerto.listarPatrocinadores(nombreEvento, nombreEdicion).getItem();
		request.setAttribute("lista_patrocinios", setPatrocinios);
		
		List<String> setTiposRegistro = puerto.getNombresTipoRegistro(nombreEvento, nombreEdicion).getItem();
		request.setAttribute("lista_tipos_registro", setTiposRegistro);
		
		String videoURL = detalleEdicion.getVideoURL();
		request.setAttribute("videoURL", videoURL);
		
		request.setAttribute("finalizada", false);
		Date dateFechaFin = Utilidades.XMLGregorianCalendarToDate(detalleEdicion.getFechaFin());
		if (dateFechaFin.before(new Date())) { 
			request.setAttribute("finalizada", true);
		}
		
		List<DTDetalleRegistro> setRegistros = puerto.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion).getItem();
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
			DTDetalleUsuario u = puerto.mostrarDetalleUsuario(detalleEdicion.getOrganizadorNickname());
			request.setAttribute("imagenOrg", u.getImagen());
		} catch (UsuarioNoExiste_Exception e) {
			e.printStackTrace();
		}
		
		// parte lógica
		try {
			puerto.archivarEdicion(nombreEvento, nombreEdicion, Utilidades.dateToXMLGregorianCalendar(new Date()));
		} catch (EdicionFinalizada_Exception exception) {
			request.setAttribute("error", "La edición seleccionada ya finalizó.");
			request.getRequestDispatcher("/WEB-INF/views/ediciones/consulta-edicion.jsp").forward(request, response);
			return;
		} catch (EdicionNoExiste_Exception exception) {
			request.setAttribute("error", "La edición seleccionada no existe en el sistema.");
			request.getRequestDispatcher("/WEB-INF/views/ediciones/consulta-edicion.jsp").forward(request, response);
			return;
		} catch (EdicionYaArchivada_Exception exception) {
			request.setAttribute("error", "La edición seleccionada ya está archivada.");
			request.getRequestDispatcher("/WEB-INF/views/ediciones/consulta-edicion.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("error", e.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/ediciones/consulta-edicion.jsp").forward(request, response);
		}
		
		// el archivo fue éxitoso
		request.setAttribute("archivado", "La edición se archivó correctamente.");
		response.sendRedirect(request.getContextPath() + "/Ediciones?accion=ConsultaEdicion&nombre_evento=" + URLEncoder.encode(nombreEvento, "UTF-8") + "&nombre_edicion=" + URLEncoder.encode(nombreEdicion, "UTF-8"));
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
