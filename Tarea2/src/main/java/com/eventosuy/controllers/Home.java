package com.eventosuy.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import datatypes.DTEvento;
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
        // TODO Auto-generated constructor stub
    }
    
    /**
     * TODO: Hardcodeado, eliminar todo
     */
    private void testingSession() {
    	Fabrica f = Fabrica.getInstancia();
    	List<String> categorias = new ArrayList<>();
		categorias.add("Categoria 1");
		f.getControladorEvento().altaEvento("Nombre1", "Descripcion1", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias);
		f.getControladorEvento().altaEvento("Nombre2", "Descripcion2", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias);
		f.getControladorEvento().altaEvento("Nombre3", "Descripcion3", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias);
		f.getControladorEvento().altaEvento("Nombre4", "Descripcion4", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias);
    }
    
	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO: Hardcodeado
		testingSession();
		Fabrica f = Fabrica.getInstancia();
		Map<String, DTEvento> eventos = f.getControladorEvento().listarEventos();
		
		req.setAttribute("eventos", eventos);
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
