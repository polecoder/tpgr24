package com.eventosuy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import datatypes.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorInstitucion;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Institucion")
@MultipartConfig
public class Institucion extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		// Initialize controllers
		Fabrica fabrica = Fabrica.getInstancia();
		IControladorEventos controladorEventos = fabrica.getControladorEvento();
		IControladorInstitucion controladorInstitucion = fabrica.getControladorInstitucion();

		// Extract request parameters
		String action = request.getParameter("action");
		String nombreEvento = request.getParameter("nombreEvento");
		String nombreEdicion = request.getParameter("nombreEdicion");

		// Set common attributes
		request.setAttribute("nombreEdicion", nombreEdicion);
		request.setAttribute("rutaImagenEdicion", "./images/IMG-EDEV03.jpeg");

		// Get user type from session
		HttpSession session = request.getSession();
		String tipoUsuario = (String) session.getAttribute("tipoUsuario");

		// Handle organizer-specific actions
		if ("organizador".equals(tipoUsuario)) {
			if (handeGetAccionesOrganizador(request, response, action, nombreEvento, nombreEdicion, controladorEventos,
					controladorInstitucion)) {
				return;
			}
		}

		// Handle public actions
		handlePublicAction(request, response, action, nombreEvento, nombreEdicion, controladorEventos);
	}

	private boolean handeGetAccionesOrganizador(HttpServletRequest request, HttpServletResponse response, String action,
			String nombreEvento, String nombreEdicion, IControladorEventos controladorEventos,
			IControladorInstitucion controladorInstitucion) throws ServletException, IOException {

		switch (action) {
		case "altaInstitucion":
			request.getRequestDispatcher("/WEB-INF/instituciones/altaInstitucion.jsp").forward(request, response);
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
			String nombreEdicion, IControladorEventos controladorEventos,
			IControladorInstitucion controladorInstitucion) throws ServletException, IOException {

		Set<String> listaNombresTipoRegistro = obtenerTiposRegistro(controladorEventos, nombreEvento, nombreEdicion);
		Set<String> listaNombresInstitucion = obtenerNombresInstituciones(controladorInstitucion);

		request.setAttribute("listaNombresRegistro", listaNombresTipoRegistro);
		request.setAttribute("listaNombresInstitucion", listaNombresInstitucion);
		request.getRequestDispatcher("/WEB-INF/instituciones/altaPatrocinio.jsp").forward(request, response);
	}

	private Set<String> obtenerTiposRegistro(IControladorEventos controladorEventos, String nombreEvento,
			String nombreEdicion) {
		try {
			return controladorEventos.listarTipoRegistro(nombreEvento, nombreEdicion).keySet();
		} catch (Exception e) {
			return new HashSet<>(Arrays.asList("Failed. Call support"));
		}
	}

	private Set<String> obtenerNombresInstituciones(IControladorInstitucion controladorInstitucion) {
		try {
			return controladorInstitucion.nombresInstituciones();
		} catch (Exception e) {
			return new HashSet<>(Arrays.asList("Failed. Call support"));
		}
	}

	private void handlePublicAction(HttpServletRequest request, HttpServletResponse response, String action,
			String nombreEvento, String nombreEdicion, IControladorEventos controladorEventos)
			throws ServletException, IOException {

		if ("consultaPatrocinio".equals(action)) {
			String codigoPatrocinio = request.getParameter("codigoPatrocinio");

			DTDetallePatrocinio dtPatrocinio = obtenerDetallePatrocinio(controladorEventos, nombreEvento, nombreEdicion,
					codigoPatrocinio);
			DTTipoRegistro dtTipoRegistro = obtenerTipoRegistro(controladorEventos, nombreEvento, nombreEdicion,
					dtPatrocinio.tipoRegistro());

			request.setAttribute("codigoPatrocinio", codigoPatrocinio);
			request.setAttribute("dtPatrocinio", dtPatrocinio);
			request.setAttribute("dtTipoRegistro", dtTipoRegistro);
			request.getRequestDispatcher("/WEB-INF/instituciones/consultaPatrocinio.jsp").forward(request, response);
		}
	}

	private DTDetallePatrocinio obtenerDetallePatrocinio(IControladorEventos controladorEventos, String nombreEvento,
			String nombreEdicion, String codigoPatrocinio) {

		DTDetallePatrocinio dtPatrocinio = controladorEventos.mostrarDetallePatrocinio(nombreEvento, nombreEdicion,
				codigoPatrocinio);

		if (dtPatrocinio == null) {
			return new DTDetallePatrocinio("Institucion failed: Call support", "codigo failed: Call support",
					new Date(), 0, null, 0, "Tipo de Registro failed: Call support");
		}
		return dtPatrocinio;
	}

	private DTTipoRegistro obtenerTipoRegistro(IControladorEventos controladorEventos, String nombreEvento,
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

		return new DTTipoRegistro("Tipo de Registro failed: Call support", "Descripcion failed: Call support", 0, 0, 0);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Initialize controllers
		Fabrica fabrica = Fabrica.getInstancia();
		IControladorEventos controladorEventos = fabrica.getControladorEvento();
		IControladorInstitucion controladorInstitucion = fabrica.getControladorInstitucion();

		// Extract request parameters
		String action = request.getParameter("action");
		String nombreEvento = request.getParameter("nombreEvento");
		String nombreEdicion = request.getParameter("nombreEdicion");

		// Set common attributes
		request.setAttribute("nombreEdicion", nombreEdicion);
		request.setAttribute("rutaImagenEdicion", "./images/IMG-EDEV03.jpeg");

		// Get user type from session
		HttpSession session = request.getSession();
		String tipoUsuario = (String) session.getAttribute("tipoUsuario");

		// Handle organizer-specific actions
		if ("organizador".equals(tipoUsuario)) {
			if (handePostAccionesOrganizador(request, response, action, nombreEvento, nombreEdicion, controladorEventos,
					controladorInstitucion)) {
				return;
			}
		}

		// Handle public actions
		handlePublicAction(request, response, action, nombreEvento, nombreEdicion, controladorEventos);
	}

	private boolean handePostAccionesOrganizador(HttpServletRequest request, HttpServletResponse response,
			String action, String nombreEvento, String nombreEdicion, IControladorEventos controladorEventos,
			IControladorInstitucion controladorInstitucion) throws ServletException, IOException {

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
			String nombreEdicion, IControladorEventos controladorEventos,
			IControladorInstitucion controladorInstitucion) throws ServletException, IOException {
		// Get text fields
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		String sitioWeb = request.getParameter("sitioWeb");

		// Get the uploaded file
		Part imagenPart = request.getPart("imagen");
		String fileName = imagenPart.getSubmittedFileName();
		InputStream fileContent = imagenPart.getInputStream();

		darAltaInstitucion(controladorInstitucion, nombre, descripcion, sitioWeb);

		response.sendRedirect("./index.jsp");

	}

	private void darAltaInstitucion(IControladorInstitucion controladorInstitucion, String nombre, String descripcion,
			String sitioWeb) {
		try {
			controladorInstitucion.AltaInstitucion(nombre, descripcion, sitioWeb);
			System.out.println("AltaInstitucion finalizó con Exito!");
		} catch (Exception error) {
			// Error AltaInstitucion redirigir a mensaje de error 500.
			System.out.println("darAltaInstitucion::ERROR");
		}
	}

	private void handlePostAltaPatrocinio(HttpServletRequest request, HttpServletResponse response, String nombreEvento,
			String nombreEdicion, IControladorEventos controladorEventos,
			IControladorInstitucion controladorInstitucion) throws ServletException, IOException {
		// Get text fields
		String nombre = request.getParameter("nombre");

		response.sendRedirect("./index.jsp");
	}

}
