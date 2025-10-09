package com.eventosuy.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logica.Fabrica;

/**
 * Servlet implementation class Home 
 */
@WebServlet ("/Institucion")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		
		switch(action){
			case "altaInsitucion":
				// hace que se ejecute el jsp sin cambiar la url
				request.getRequestDispatcher("/WEB-INF/instituciones/altaInsitucion.jsp").forward(request, response);
				break;
			case "consultaPatrocinio":
				// hace que se ejecute el jsp sin cambiar la url
				request.getRequestDispatcher("/WEB-INF/instituciones/consultaPatrocinio.jsp").forward(request, response);
				break;
			case "altaPatrocinio":
				// manda una redirección a otra URL (cambia la URL)
				request.getRequestDispatcher("/WEB-INF/instituciones/altaPatrocinio.jsp").forward(request, response);
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Fabrica f = Fabrica.getInstancia();
		String action = (String) request.getAttribute("action");
		
		switch(action){
			case "altaInsitucion":
				// hace que se ejecute el jsp sin cambiar la url
				request.getRequestDispatcher("/WEB-INF/home/iniciar.jsp").
						forward(request, response);
				break;
			case "consultaPatrocinio":
				// hace que se ejecute el jsp sin cambiar la url
				request.getRequestDispatcher("/WEB-INF/home/inicioErroneo.jsp").
						forward(request, response);
				break;
			case "altaPatrocinio":
				// manda una redirección a otra URL (cambia la URL)
				response.sendRedirect("/gamebook/perfil");
				break;
		}
	}

}
