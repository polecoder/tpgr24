package com.eventosuy.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import carga.CargaDatos;
import datatypes.DTDetalleUsuario;
import datatypes.DTEvento;
import excepciones.FormatoContrasenaInvalido;
import logica.Fabrica;
import logica.Utilidades;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO: Hardcodeado
    }
    
    /**
     * TODO: Hardcodeado, eliminar todo
     */
    private void testingSession() {
    	if (getServletConfig() == null) return;
    	Fabrica f = Fabrica.getInstancia();
    	List<String> categorias = new ArrayList<>();
		categorias.add("Categoria 1");
		

		// Eventos
		f.getControladorEvento().altaEvento("Nombre1", "Descripcion1", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias, null);
		f.getControladorEvento().altaEvento("Nombre2", "Descripcion2", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias, null);
		f.getControladorEvento().altaEvento("Nombre3", "Descripcion3", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias, null);
		f.getControladorEvento().altaEvento("Nombre4", "Descripcion4", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias, null);
		
		// Usuarios
		try {
			f.getControladorUsuarios().altaAsistente("Nick1", "Abcdef1!", "Juan", "abc1@abc.com", "Perez", Utilidades.toDate(LocalDate.of(2025, 8, 31)),null, null);
			f.getControladorUsuarios().altaAsistente("Nick2", "Abcdef1!", "Roberto", "abc2@abc.com", "Gonzalez", Utilidades.toDate(LocalDate.of(2025, 8, 31)),null, null);
			f.getControladorUsuarios().altaOrganizador("Nick3", "Abcdef1!", "MontevideoOrganiza", "abc3@abc.com", "Descripcion", "https://link.com",null);
			f.getControladorUsuarios().altaOrganizador("Nick4", "Abcdef1!", "MisEventos", "abc4@abc.com", "Descripcion", "https://link.com",null);
		} catch (FormatoContrasenaInvalido e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Categorias
		try {
			f.getControladorEvento().altaCategoria("Categoria1");
			f.getControladorEvento().altaCategoria("Categoria2");
			f.getControladorEvento().altaCategoria("Categoria3");
			f.getControladorEvento().altaCategoria("Categoria4");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
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
		Fabrica f = Fabrica.getInstancia();
		
		Map<String, DTEvento> eventos = f.getControladorEvento().listarEventos();
		req.setAttribute("eventos", eventos);
		
		List<DTDetalleUsuario> usuarios = f.getControladorUsuarios().listarDetalleUsuarios();
		req.setAttribute("usuarios", usuarios);
		
		List<String> categorias = f.getControladorEvento().getListaCategorias();
		req.setAttribute("categorias", categorias);
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 testingSession();
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
