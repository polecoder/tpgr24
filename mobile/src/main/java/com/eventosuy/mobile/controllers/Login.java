package com.eventosuy.mobile.controllers;

import java.io.IOException;
import java.util.List;

import com.eventosuy.mobile.models.TipoUsuario;

import carga.CargaDatos;
import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleUsuario;
import excepciones.ContrasenaIncorrecta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Fabrica;

/**
 * Servlet implementation class Login
 */
@WebServlet ("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * Carga todos los datos de prueba ubicados en la carpeta data/
     */
    private void cargarDatos() {
    	List<String> rutasArchivos = List.of(
    			getServletContext().getRealPath("data/2025Categorias.csv"),
    			getServletContext().getRealPath("data/2025Usuarios.csv"),
    			getServletContext().getRealPath("data/2025Instituciones.csv"),
    			getServletContext().getRealPath("data/2025Usuarios-Organizadores.csv"),
    			getServletContext().getRealPath("data/2025Usuarios-Asistentes.csv"),
    			getServletContext().getRealPath("data/2025Eventos.csv"),
    			getServletContext().getRealPath("data/2025EdicionesEventos.csv"),
    			getServletContext().getRealPath("data/2025TipoRegistro.csv"),
    			getServletContext().getRealPath("data/2025Patrocinios.csv"),
    			getServletContext().getRealPath("data/2025Registros.csv")
    	);
    	String[] rutas = rutasArchivos.toArray(new String[0]);
    	
    	CargaDatos cargaDatos = CargaDatos.getInstancia();
    	cargaDatos.cargarArchivos(rutas);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        cargarDatos();
    }
    
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
			Fabrica f = Fabrica.getInstancia();
			datosUsuario = f.getControladorUsuarios().validarLogin(usuario, contrasena);
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
		} catch (ContrasenaIncorrecta e) {
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
