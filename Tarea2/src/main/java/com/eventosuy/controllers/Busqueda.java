package com.eventosuy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import datatypes.DTEdicionEvento;
import datatypes.DTEvento;
import logica.Fabrica;
import logica.IControladorEventos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/Busqueda")
public class Busqueda extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Busqueda() {
        super();
    }
    
    
    @Override
    public void init() throws ServletException {
        super.init();
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
	private void processRequest(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		Fabrica fabrica = Fabrica.getInstancia();
		IControladorEventos controladorEventos = fabrica.getControladorEvento();
		
		List<DTEvento> eventosEnCurso = new ArrayList<>(controladorEventos.listarEventosEnCurso());
		List<DTEvento> eventosBusqueda = new ArrayList<>();
		String query = request.getParameter("query");
		for (DTEvento evento : eventosEnCurso) {
			if (coincideBusqueda(evento, query, controladorEventos)) {
				eventosBusqueda.add(evento);
			}
		}
		request.setAttribute("eventosBusqueda", eventosBusqueda);
		request.getRequestDispatcher("/WEB-INF/views/busqueda.jsp").forward(request, resp);
		
		/* TODO: preguntar si se listan
		List<DTEvento> eventosFinalizados = new ArrayList<>(f.getControladorEvento().listarEventosFinalizados());
		req.setAttribute("eventosFinalizados", eventosFinalizados);
		*/
	}
	
	private boolean coincideBusqueda(DTEvento evento, String query, IControladorEventos controladorEventos) {
		boolean coincide = false;
		if (evento.getNombre().contains(query) || evento.getDescripcion().contains(query)) {
			coincide = true;
		} else {
			Map<String, DTEdicionEvento> listaEdiciones = controladorEventos.listarEdicionesEvento(evento.getNombre());
			for (DTEdicionEvento edicion : listaEdiciones.values()) {
				if (edicion.getNombre().contains(query)) { // TODO: revisar estado de edicion por usuario, revisar case sensitive
					coincide = true;
					break;
				}
			}
		}
		return coincide;
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
