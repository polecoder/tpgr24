package com.eventosuy.mobile.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.eventosuy.mobile.servidor.Publicador;
import com.eventosuy.mobile.servidor.PublicadorService;

import com.eventosuy.mobile.servidor.DTDetalleRegistro;
import com.eventosuy.mobile.servidor.DTDetalleUsuario;

/**
 * Servlet implementation class Registros
 */

@WebServlet("/Registros")
public class Registros extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registros() {
        super();
       
    }

    private final static PublicadorService servicio = new PublicadorService();
 	private final static Publicador puerto = servicio.getPublicadorPort();
	    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {		
    	String accion = request.getParameter("accion"); 
		if (accion == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada.");
			return;
		}
		String NombreEdicion=(String) request.getParameter("edicion");
		DTDetalleUsuario usuarioLogeado = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");

		if (usuarioLogeado == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Usuario no ha iniciado sesión.");
            return;
        }
		
		
		switch (accion) {
		case "Consulta":
			DTDetalleRegistro dtr = puerto.getDTDetalleRegistroPorEdicion(NombreEdicion, usuarioLogeado.getNickname());
			
			
			request.setAttribute("detalle", dtr);
			request.getRequestDispatcher("/WEB-INF/views/registros/consulta-registro.jsp").forward(request, response);
			
			break;
		case "Confirmar Asistencia": {
			
			
		    try {
		        puerto.confirmarAsistencia(usuarioLogeado.getNickname(), NombreEdicion);
		        DTDetalleRegistro dtr1 = puerto.getDTDetalleRegistroPorEdicion(NombreEdicion, usuarioLogeado.getNickname());
		        // Vuelve a consultar el detalle actualizado
		        request.setAttribute("detalle", dtr1);
		        request.setAttribute("mensaje", "Asistencia confirmada correctamente.");

		        request.getRequestDispatcher("/WEB-INF/views/registros/consulta-registro.jsp").forward(request, response);
		    } catch (Exception e) {
		        request.setAttribute("error", "Error al confirmar asistencia: " + e.getMessage());
		        request.getRequestDispatcher("/WEB-INF/views/registros/consulta-registro.jsp").forward(request, response);
		    }
		    break;
		}
		default:
			request.getRequestDispatcher("/WEB-INF/error.jsp");
			
		}
   
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

}
	