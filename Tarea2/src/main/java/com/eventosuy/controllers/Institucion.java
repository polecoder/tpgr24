package com.eventosuy.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.eventosuy.models.TipoUsuario;
import com.eventosuy.servidor.DTDetallePatrocinio;
import com.eventosuy.servidor.DTEdicionEvento;
import com.eventosuy.servidor.DTEvento;
import com.eventosuy.servidor.Publicador;
import com.eventosuy.servidor.PublicadorService;
import com.eventosuy.servidor.StringArray;
import com.eventosuy.utils.Utilidades;
import com.eventosuy.servidor.DTTipoRegistro;
import com.eventosuy.servidor.ENivelPatrocinio;
import com.eventosuy.servidor.Patrocinio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Institucion")
@MultipartConfig
public class Institucion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Servicios
	private final static PublicadorService servicio = new PublicadorService();
	private final static Publicador puerto = servicio.getPublicadorPort();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Institucion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Extract request parameters
		String action = request.getParameter("action");
		String nombreEvento = request.getParameter("nombreEvento");
		String nombreEdicion = request.getParameter("nombreEdicion");

		// Set common attributes
		request.setAttribute("nombreEdicion", nombreEdicion);
		try {
			DTEdicionEvento dtEdicion = null;
			if (nombreEvento != null && !nombreEvento.isBlank() && nombreEdicion != null && !nombreEdicion.isBlank()) {
				dtEdicion = puerto.getDTEdicion(nombreEvento, nombreEdicion);				
			}
			if (dtEdicion != null) {
				request.setAttribute("imagenEdicion", dtEdicion.getImagen());							
			} else {
				request.setAttribute("imagenEdicion", null);
			}
		}catch(Exception error) {
			error.printStackTrace();
		}
		
		// Get user type from session
		HttpSession session = request.getSession();
		TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuarioLogeado");
		
		// Handle organizer-specific actions
		if (tipoUsuario != null && tipoUsuario.equals(TipoUsuario.ORGANIZADOR)) {
			if (handeGetAccionesOrganizador(request, response, action, nombreEvento, nombreEdicion, puerto,
					puerto)) {
				return;
			}
		}

		// Handle public actions
		handlePublicAction(request, response, action, nombreEvento, nombreEdicion, puerto);
	}

	private boolean handeGetAccionesOrganizador(HttpServletRequest request, HttpServletResponse response, String action,
			String nombreEvento, String nombreEdicion, Publicador controladorEventos,
			Publicador controladorInstitucion) throws ServletException, IOException {

		switch (action) {
		case "altaInstitucion":
			request.getRequestDispatcher("/WEB-INF/views/alta-institucion.jsp").forward(request, response);
			return true;

		case "altaPatrocinio":
			handleAltaPatrocinio(request, response, nombreEvento, nombreEdicion, controladorEventos,
					controladorInstitucion);
			return true;

		default:
			return false;
		}
	}

	private void handleAltaPatrocinio(HttpServletRequest request, HttpServletResponse response, String nombreEvento,
			String nombreEdicion, Publicador controladorEventos,
			Publicador controladorInstitucion) throws ServletException, IOException {
		List<String> listaNombresTipoRegistro = controladorEventos.listarTipoRegistroKeys(nombreEvento, nombreEdicion).getItem();
//		Set<String> listaNombresTipoRegistro = ((Map<String, DTTipoRegistro>) controladorEventos.listarTipoRegistro(nombreEvento, nombreEdicion)).keySet();
		List<String> listaNombresInstitucion = controladorInstitucion.nombresInstituciones().getItem();

		request.setAttribute("nombreEvento", nombreEvento);
		request.setAttribute("listaNombresRegistro", listaNombresTipoRegistro);
		request.setAttribute("listaNombresInstitucion", listaNombresInstitucion);
		request.getRequestDispatcher("/WEB-INF/views/patrocinios/alta-patrocinio.jsp").forward(request, response);
	}

	private void handlePublicAction(HttpServletRequest request, HttpServletResponse response, String action,
			String nombreEvento, String nombreEdicion, Publicador controladorEventos)
			throws ServletException, IOException {

		if ("consultaPatrocinio".equals(action)) {
			String patrocinador = request.getParameter("nombre_patrocinador");
			Patrocinio patrocinio = controladorEventos.getPatrocinio(nombreEvento, nombreEdicion, patrocinador);
			DTEvento evento = controladorEventos.getDTEvento(nombreEvento);
			DTEdicionEvento edicion = controladorEventos.getDTEdicion(nombreEvento, nombreEdicion);
			
			String codigoPatrocinio = patrocinio.getCodigoPatrocinio();

			DTDetallePatrocinio dtPatrocinio = obtenerDetallePatrocinio(controladorEventos, nombreEvento, nombreEdicion,
					codigoPatrocinio);
			DTTipoRegistro dtTipoRegistro = obtenerTipoRegistro(controladorEventos, nombreEvento, nombreEdicion,
					dtPatrocinio.getTipoRegistro());
			
			request.setAttribute("datosEvento", evento);
			request.setAttribute("datosEdicion", edicion);
			request.setAttribute("codigoPatrocinio", codigoPatrocinio);
			request.setAttribute("dtPatrocinio", dtPatrocinio);
			request.setAttribute("dtTipoRegistro", dtTipoRegistro);
			request.getRequestDispatcher("/WEB-INF/views/patrocinios/consulta-patrocinio.jsp").forward(request, response);
		}
	}

	private DTDetallePatrocinio obtenerDetallePatrocinio(Publicador controladorEventos, String nombreEvento,
			String nombreEdicion, String codigoPatrocinio) {

		DTDetallePatrocinio dtPatrocinio = controladorEventos.mostrarDetallePatrocinio(nombreEvento, nombreEdicion,
				codigoPatrocinio);

		if (dtPatrocinio == null) {
			DTDetallePatrocinio dTDetallePatrocinioDefault = new DTDetallePatrocinio();
			dTDetallePatrocinioDefault.setNombreInstitucion("Institucion failed: Call support"); 
			dTDetallePatrocinioDefault.setCodigo("codigo failed: Call support");
			dTDetallePatrocinioDefault.setFechaAlta(null); 
			dTDetallePatrocinioDefault.setAporte(0); 
			dTDetallePatrocinioDefault.setNivel(null); 
			dTDetallePatrocinioDefault.setCuposGratuitos(0); 
			dTDetallePatrocinioDefault.setTipoRegistro("Tipo de Registro failed: Call support");
			return dTDetallePatrocinioDefault;
		}
		return dtPatrocinio;
	}

	private DTTipoRegistro obtenerTipoRegistro(Publicador controladorEventos, String nombreEvento,
			String nombreEdicion, String tipoRegistro) {

		try {
			DTTipoRegistro dtTipoRegistro = controladorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion,
					tipoRegistro);

			if (dtTipoRegistro != null) {
				return dtTipoRegistro;
			}
		} catch (Exception e) {
			// Fall through to return default
		}
		DTTipoRegistro dTTipoRegistroDefault = new DTTipoRegistro();
		dTTipoRegistroDefault.setNombre("Tipo de Registro failed: Call support"); 
		dTTipoRegistroDefault.setDescripcion("Descripcion failed: Call support"); 
		dTTipoRegistroDefault.setCosto(0); 
		dTTipoRegistroDefault.setCuposMax(0); 
		dTTipoRegistroDefault.setCuposDisponibles(0);
		return dTTipoRegistroDefault;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Extract request parameters
		String action = request.getParameter("action");
		String nombreEvento = request.getParameter("nombreEvento");
		String nombreEdicion = request.getParameter("nombreEdicion");

		// Set common attributes
		request.setAttribute("nombreEdicion", nombreEdicion);
		try {
			DTEdicionEvento dtEdicion = null;
			if (nombreEvento != null && !nombreEvento.isBlank() && nombreEdicion != null && !nombreEdicion.isBlank()) {
				dtEdicion = puerto.getDTEdicion(nombreEvento, nombreEdicion);				
			}
			if (dtEdicion != null) {
				request.setAttribute("imagenEdicion", dtEdicion.getImagen());							
			} else {
				request.setAttribute("imagenEdicion", null);
			}		
		}catch(Exception error) {
			error.printStackTrace();
		}

		// Get user type from session
		HttpSession session = request.getSession();
		TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuarioLogeado");

		// Handle organizer-specific actions
		if (tipoUsuario.equals(TipoUsuario.ORGANIZADOR)) {
			if (handePostAccionesOrganizador(request, response, action, nombreEvento, nombreEdicion, puerto,
					puerto)) {
				return;
			}
		}

		// Handle public actions
		handlePublicAction(request, response, action, nombreEvento, nombreEdicion, puerto);
	}

	private boolean handePostAccionesOrganizador(HttpServletRequest request, HttpServletResponse response,
			String action, String nombreEvento, String nombreEdicion, Publicador controladorEventos,
			Publicador controladorInstitucion) throws ServletException, IOException {

		switch (action) {
		case "altaInstitucion":
			handlePostAltaInsitucion(request, response, nombreEvento, nombreEdicion, controladorEventos,
					controladorInstitucion);
			return true;

		case "altaPatrocinio":
			handlePostAltaPatrocinio(request, response, nombreEvento, nombreEdicion, controladorEventos,
					controladorInstitucion);
			return true;

		default:
			return false;
		}
	}

	private void handlePostAltaInsitucion(HttpServletRequest request, HttpServletResponse response, String nombreEvento,
			String nombreEdicion, Publicador controladorEventos,
			Publicador controladorInstitucion) throws ServletException, IOException {
		// Get text fields
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		String sitioWeb = request.getParameter("sitioWeb");

		try {
	        darAltaInstitucion(controladorInstitucion, nombre, descripcion, sitioWeb);
	        response.sendRedirect(request.getContextPath() + "/home");
	    } catch (Exception ex) {
	    	
	        request.setAttribute("error", ex.getMessage());
	        request.getRequestDispatcher("/WEB-INF/views/alta-institucion.jsp").forward(request, response);
	    }
	}

	private void darAltaInstitucion(Publicador controladorInstitucion, String nombre, String descripcion,
			String sitioWeb) throws Exception{
			controladorInstitucion.altaInstitucion(nombre, descripcion, sitioWeb);
		
	}

	private void handlePostAltaPatrocinio(HttpServletRequest request, HttpServletResponse response, String nombreEvento,
			String nombreEdicion, Publicador controladorEventos,
			Publicador controladorInstitucion) throws ServletException, IOException {
		try {
		    String nombreTipoRegistro = request.getParameter("TipoRegistro");
		    String nombreInstitucion = request.getParameter("Instituciones");
		    String nivelPatrocinio = request.getParameter("Nivel");
		    
		    float aporte = -1;
		    String aporteStr = request.getParameter("Aporte");
		    if (aporteStr != null && !aporteStr.isEmpty()) {
		        aporte = Float.parseFloat(aporteStr);
		    }
		    
		    int cupos = -1;
		    String cuposStr = request.getParameter("Cupos");
		    if (cuposStr != null && !cuposStr.isEmpty()) {
		        cupos = Integer.parseInt(cuposStr);
		    }
		    
		    String codigo = request.getParameter("Codigo");
		    if (nombreTipoRegistro.isEmpty() || nombreInstitucion.isEmpty() || nivelPatrocinio.isEmpty() || aporte < 0 || cupos < 0 || codigo.isEmpty()) {
		    	request.setAttribute("error", "Debe completar todos los campos");
		    	handeGetAccionesOrganizador(request, response, "altaPatrocinio", nombreEvento, nombreEdicion, controladorEventos, controladorInstitucion);
		    	return;
		    } else {
		    	darAltaPatrocinio(controladorEventos,
		    			controladorInstitucion,
			    		nombreEvento,
			    		nombreEdicion,
			    		new Date(),
			    		nombreInstitucion, 
			    		ENivelPatrocinio.valueOf(nivelPatrocinio), 
			    		aporte, 
			    		cupos,
			    		codigo,
			    		nombreTipoRegistro,
			    		request,
			    		response);
		    }
		} catch (NumberFormatException e) {
		    // Handle invalid number format
		    request.setAttribute("error", "Formato de número inválido");
		    request.getRequestDispatcher("/WEB-INF/views/patrocinios/alta-patrocinio.jsp").forward(request, response);
		}
	}
	private void darAltaPatrocinio(Publicador controladorEventos, Publicador controladorInstitucion, String eventoSeleccionado,  String edicionSeleccionada,Date FechaAlta,String institucionSeleccionada, 
			ENivelPatrocinio nivelPatrocinio, float aporteEconomico, int cupos,String codigoPatrocinio, String tipoRegistroSeleccionado, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			controladorEventos.altaPatrocinio(eventoSeleccionado, 
					edicionSeleccionada,
					Utilidades.dateToXMLGregorianCalendar(FechaAlta),
					institucionSeleccionada, 
					nivelPatrocinio,
					aporteEconomico,
					cupos,
					codigoPatrocinio,
					tipoRegistroSeleccionado);
			response.sendRedirect(request.getContextPath() + "/home"); // hago redirect a home solo si el alta fue éxitoso
		} catch (Exception error) {
			
			StringArray arrayInstituciones = controladorInstitucion.nombresInstituciones();
			Set<String> listaNombresInstitucion = new HashSet<>();
			if (arrayInstituciones != null && arrayInstituciones.getItem() != null) {
			    for (String nombre : arrayInstituciones.getItem()) {
			        listaNombresInstitucion.add(nombre);
			    }
			}

			StringArray arrayTiposRegistro = controladorEventos.getNombresTipoRegistro(eventoSeleccionado, edicionSeleccionada);
			Set<String> listaNombresTipoRegistro = new HashSet<>();
			if (arrayTiposRegistro != null && arrayTiposRegistro.getItem() != null) {
			    for (String tipo : arrayTiposRegistro.getItem()) {
			        listaNombresTipoRegistro.add(tipo);
			    }
			}
			request.setAttribute("listaNombresRegistro", listaNombresTipoRegistro);
			request.setAttribute("listaNombresInstitucion", listaNombresInstitucion);
			request.setAttribute("nombreEvento", eventoSeleccionado);
			request.setAttribute("nombreEdicion", edicionSeleccionada);
			request.setAttribute("error", error.getMessage());
			request.getRequestDispatcher("/WEB-INF/views/patrocinios/alta-patrocinio.jsp").forward(request, response);
		}
	}
}
