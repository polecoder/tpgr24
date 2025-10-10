package com.eventosuy.controllers;

import java.io.IOException;
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

import logica.Fabrica;
import logica.IControladorEventos;
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
		// Logica
		Fabrica fabrica = Fabrica.getInstancia();
		IControladorEventos IControladorEventos = fabrica.getControladorEvento();
		IControladorInstitucion IControladorInstitucion = fabrica.getControladorInstitucion();
		// Request Parameters
		String action = (String) request.getParameter("action"); 
		String nombreEvento = (String) request.getParameter("nombreEvento");
		String nombreEdicion = (String) request.getParameter("nombreEdicion");
		
		// Request Attributes
		request.setAttribute("nombreEdicion", nombreEdicion);
		request.setAttribute("rutaImagenEdicion", "./images/IMG-EDEV03.jpeg");
		// Session
		HttpSession session = request.getSession();
		String tipoUsuario = (String) session.getAttribute("tipoUsuario");
		
		// Paginas disponibles a organizadores
		if(tipoUsuario == "organizador") {
			switch(action){
				case "altaInstitucion":
					request.getRequestDispatcher("/WEB-INF/instituciones/altaInstitucion.jsp").forward(request, response);					
					break;
				case "altaPatrocinio":
					// Precondicion: Necesito que request tenga parametros nombreEdicion & nombreEvento.
					
					
					
					Set<String> listaNombresTipoRegistro;
					Set<String> listaNombresInstitucion;
					
					try {
						listaNombresTipoRegistro = IControladorEventos.listarTipoRegistro(nombreEvento, nombreEdicion).keySet();						
					}catch(Exception err) {
						listaNombresTipoRegistro = new HashSet<>(Arrays.asList("Failed. Call support"));
					}
					try {
						listaNombresInstitucion = IControladorInstitucion.nombresInstituciones();						
					}catch(Exception err) {
						listaNombresInstitucion = new HashSet<>(Arrays.asList("Failed. Call support"));
					}
					
					
					request.setAttribute("listaNombresRegistro", listaNombresTipoRegistro);
					request.setAttribute("listaNombresInstitucion", listaNombresInstitucion);
					request.getRequestDispatcher("/WEB-INF/instituciones/altaPatrocinio.jsp").forward(request, response);					
					break;
			}
		}
		
		// Paginas disponibles a todos los usuarios
		switch(action){
			case "consultaPatrocinio":
				// Precondicion: Necesito que request tenga parametros codigoPatrocinio
				String codigoPatrocinio = (String) request.getParameter("codigoPatrocinio");
				
				DTDetallePatrocinio dtPatrocinio = IControladorEventos.mostrarDetallePatrocinio(nombreEvento, nombreEdicion, codigoPatrocinio);
				
				// Request Attributes
				request.setAttribute("codigoPatrocinio", codigoPatrocinio);
				request.setAttribute("nombreEvento", nombreEvento);
				request.setAttribute("dtPatrocinio", dtPatrocinio);
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
					// Precondicion: Necesito que request tenga el dtEdicionEvento en el atributo "dtEdicion"
					// Testing CARGA-DATOS
						DTEvento testingDtEvento = new DTEvento(
								"TestEvento",
								"Evento de testing",
								"TE",
								new Date());
						request.setAttribute("dtEvento", testingDtEvento);
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
						
					// Fin Testing CARGA-DATOS
					Fabrica fabrica = Fabrica.getInstancia();
					DTEdicionEvento dtEdicion = (DTEdicionEvento) request.getAttribute("dtEdicion");
					DTEvento dtEvento = (DTEvento) request.getAttribute("dtEvento");
					
					IControladorEventos IControladorEventos = fabrica.getControladorEvento();
					IControladorInstitucion IControladorInstitucion = fabrica.getControladorInstitucion();
					
					Map<String, DTTipoRegistro> listaNombresTipoRegistro = IControladorEventos.listarTipoRegistro(
							dtEvento.getNombre(), 
							dtEdicion.getNombre());
					Set<String> listaNombresInstitucion = IControladorInstitucion.nombresInstituciones();
					
					
					request.setAttribute("listaNombresRegistro", listaNombresTipoRegistro);
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
