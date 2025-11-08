package com.eventosuy.mobile.controllers;

import java.io.IOException;

import com.eventosuy.mobile.models.TipoUsuario;
import com.eventosuy.mobile.servidor.ContrasenaIncorrecta_Exception;
import com.eventosuy.mobile.servidor.DTDetalleAsistente;
import com.eventosuy.mobile.servidor.DTDetalleOrganizador;
import com.eventosuy.mobile.servidor.DTDetalleUsuario;
import com.eventosuy.mobile.servidor.Publicador;
import com.eventosuy.mobile.servidor.PublicadorService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet ("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// servicios
	private final static PublicadorService servicio = new PublicadorService();
	private final static Publicador puerto = servicio.getPublicadorPort();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String usuario = req.getParameter("usuario");
		String contrasena = req.getParameter("contrasena");
		
		// muestro la página de login si el usuario todavía no logeo
		if (req.getMethod().equalsIgnoreCase("GET")) {
		    req.getRequestDispatcher("/WEB-INF/views/usuarios/iniciar-sesion.jsp").forward(req, resp);
		    return;
		}

		DTDetalleUsuario datosUsuario;
		try {
			datosUsuario = puerto.validarLogin(usuario, contrasena);
			if (datosUsuario != null) {
				HttpSession session = req.getSession(true);
				// validación de tipo de usuario
				if (datosUsuario instanceof DTDetalleAsistente) {
					session.setAttribute("usuarioLogeado", datosUsuario);
					session.setAttribute("tipoUsuarioLogeado", TipoUsuario.ASISTENTE);
					// TODO: Actualizar redirect
					req.getRequestDispatcher("/index.jsp").forward(req, resp);
				} else if (datosUsuario instanceof DTDetalleOrganizador) {
					req.setAttribute("error", "El usuario proporcionado corresponde a un organizador. Solo puede ingresar como asistente desde un celular.");
					req.getRequestDispatcher("/WEB-INF/views/usuarios/iniciar-sesion.jsp").forward(req, resp);
				}
			} else {
				req.setAttribute("error", "El usuario proporcionado no existe.");
				req.getRequestDispatcher("/WEB-INF/views/usuarios/iniciar-sesion.jsp").forward(req, resp);
			}
		} catch (ContrasenaIncorrecta_Exception exception) {
			req.setAttribute("error", "La contraseña es incorrecta.");
			req.getRequestDispatcher("/WEB-INF/views/usuarios/iniciar-sesion.jsp").forward(req, resp);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
