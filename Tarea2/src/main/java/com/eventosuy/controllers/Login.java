package com.eventosuy.controllers;

import java.io.IOException;
import java.time.LocalDate;

import com.eventosuy.models.TipoUsuario;

import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleUsuario;
import excepciones.ContrasenaIncorrecta;
import excepciones.FormatoContrasenaInvalido;
import logica.Fabrica;
import logica.Utilidades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO: Hardcodeado
		//testingSession();
	}

	/**
	 * TODO: Hardcodeado, eliminar todo
	 */
	private void testingSession() {
		Fabrica f = Fabrica.getInstancia();
		// usuarios
		try {
			f.getControladorUsuarios().altaAsistente("Nick1", "Abcdef1!", "Juan", "abc1@abc.com", "Perez", Utilidades.toDate(LocalDate.of(2025, 8, 31)), null);
			f.getControladorUsuarios().altaAsistente("Nick2", "Abcdef1!", "Roberto", "abc2@abc.com", "Gonzalez", Utilidades.toDate(LocalDate.of(2025, 8, 31)), null);
			f.getControladorUsuarios().altaOrganizador("Nick3", "Abcdef1!", "MontevideoOrganiza", "abc3@abc.com", "Descripcion", "https://link.com");
			f.getControladorUsuarios().altaOrganizador("Nick4", "Abcdef1!", "MisEventos", "abc4@abc.com", "Descripcion", "https://link.com");
		} catch (FormatoContrasenaInvalido e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		if (usuario == null || contrasena == null || usuario.isEmpty() || contrasena.isEmpty()) {
			req.getRequestDispatcher("/WEB-INF/views/iniciar-sesion.jsp").forward(req, resp);
			return;
		}

		DTDetalleUsuario datosUsuario;
		try {
			Fabrica f = Fabrica.getInstancia();
			datosUsuario = f.getControladorUsuarios().validarLogin(usuario, contrasena);
			if (datosUsuario != null) {
				HttpSession session = req.getSession(true);
				session.setAttribute("usuarioLogeado", datosUsuario);
				// validación de tipo de usuario
				if (datosUsuario instanceof DTDetalleAsistente) {
					session.setAttribute("tipoUsuarioLogeado", TipoUsuario.ASISTENTE);
				} else if (datosUsuario instanceof DTDetalleOrganizador) {
					session.setAttribute("tipoUsuarioLogeado", TipoUsuario.ORGANIZADOR);
				}
				resp.sendRedirect(req.getContextPath() + "/home");
			} else {
				req.setAttribute("error", "El usuario proporcionado no existe.");
				req.getRequestDispatcher("/WEB-INF/views/iniciar-sesion.jsp").forward(req, resp);
			}
		} catch (ContrasenaIncorrecta e) {
			req.setAttribute("error", "La contraseña es incorrecta.");
			req.getRequestDispatcher("/WEB-INF/views/iniciar-sesion.jsp").forward(req, resp);
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
