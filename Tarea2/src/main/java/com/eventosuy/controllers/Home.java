package com.eventosuy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    }
    
    /**
     * TODO: Hardcodeado, eliminar todo
     */
    private void testingSession() {
    	Fabrica f = Fabrica.getInstancia();
    	List<String> categorias = new ArrayList<>();
		categorias.add("Categoria 1");
		
		//cargo una imagen para probar
		byte[] imagenEvento=null;
		
		try (InputStream is = getServletContext().getResourceAsStream("/images/IMG-EV04.png")) {
	        if (is != null) {
	            imagenEvento = is.readAllBytes();
	        } else {
	        	imagenEvento = null;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

		// Eventos
		f.getControladorEvento().altaEvento("Nombre1", "Descripcion1", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias,imagenEvento);
		f.getControladorEvento().altaEvento("Nombre2", "Descripcion2", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias,null);
		f.getControladorEvento().altaEvento("Nombre3", "Descripcion3", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias,null);
		f.getControladorEvento().altaEvento("Nombre4", "Descripcion4", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias,null);
		
		// Usuarios
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
    
	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO: Hardcodeado
		testingSession();
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
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
