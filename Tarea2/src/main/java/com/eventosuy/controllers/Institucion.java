package com.eventosuy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import datatypes.DTEdicionEvento;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import logica.Fabrica;
import logica.IControladorInstitucion;

/**
 * Servlet implementation class Home 
 */
@WebServlet ("/Institucion")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = (String) request.getParameter("action"); 
		
		HttpSession session = request.getSession();
		String tipoUsuario = (String) session.getAttribute("tipoUsuario");
		System.out.printf("doGet() tipoUsuario is: %s\n", tipoUsuario);
		
		// Paginas disponibles a organizadores
		if(tipoUsuario == "organizador") {
			switch(action){
				case "altaInstitucion":
					request.getRequestDispatcher("/WEB-INF/instituciones/altaInstitucion.jsp").forward(request, response);					
					break;
				case "altaPatrocinio":
					// Testing CARGA-DATOS
					DTEdicionEvento testingDtEdicion = new DTEdicionEvento(
							"TestEdicionEvento", 
							"TEE", 
							new Date(), 
							new Date(), 
							new Date(), 
							"Montevideo", 
							"Uruguay", 
							"MisEventos"
							);
					request.setAttribute("dtEdicion", testingDtEdicion);
					request.setAttribute("listaNombresRegistro", new ArrayList<>(Arrays.asList("tipo1", "tipo2", "tipo3", "tipo4", "tipo5","Corredor 21K")));
					// Fin Testing

					Fabrica fabrica = Fabrica.getInstancia();
					IControladorInstitucion IControladorInstitucion = fabrica.getControladorInstitucion();
					Set<String> listaNombresInstitucion = IControladorInstitucion.nombresInstituciones();
					request.setAttribute("listaNombresInstitucion", listaNombresInstitucion);
					request.getRequestDispatcher("/WEB-INF/instituciones/altaPatrocinio.jsp").forward(request, response);					
					break;
			}
		}
		
		// Paginas disponibles a todos los usuarios
		switch(action){
			case "consultaPatrocinio":
				request.getRequestDispatcher("/WEB-INF/instituciones/consultaPatrocinio.jsp").forward(request, response);
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String action = (String) request.getParameter("action"); 
		
		HttpSession session = request.getSession();
		String tipoUsuario = (String) session.getAttribute("tipoUsuario");
		System.out.printf("doGet() tipoUsuario is: %s\n", tipoUsuario);
		
		// Paginas disponibles a organizadores
		if(tipoUsuario == "organizador") {
			switch(action){
				case "altaInstitucion":
					request.getRequestDispatcher("/WEB-INF/instituciones/altaInstitucion.jsp").forward(request, response);					
					break;
				case "altaPatrocinio":
					// Testing CARGA-DATOS
					DTEdicionEvento testingDtEdicion = new DTEdicionEvento(
							"TestEdicionEvento", 
							"TEE", 
							new Date(), 
							new Date(), 
							new Date(), 
							"Montevideo", 
							"Uruguay", 
							"MisEventos"
							);
					request.setAttribute("dtEdicion", testingDtEdicion);
					request.setAttribute("listaNombresRegistro", new ArrayList<>(Arrays.asList("tipo1", "tipo2", "tipo3", "tipo4", "tipo5","Corredor 21K")));
					// Fin Testing

					Fabrica fabrica = Fabrica.getInstancia();
					IControladorInstitucion IControladorInstitucion = fabrica.getControladorInstitucion();
					Set<String> listaNombresInstitucion = IControladorInstitucion.nombresInstituciones();
					request.setAttribute("listaNombresInstitucion", listaNombresInstitucion);
					request.getRequestDispatcher("/WEB-INF/instituciones/altaPatrocinio.jsp").forward(request, response);					
					break;
			}
		}
		
		// Paginas disponibles a todos los usuarios
		switch(action){
			case "consultaPatrocinio":
				request.getRequestDispatcher("/WEB-INF/instituciones/consultaPatrocinio.jsp").forward(request, response);
				break;
		}
	}

}
