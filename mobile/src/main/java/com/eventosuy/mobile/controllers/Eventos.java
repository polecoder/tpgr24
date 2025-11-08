package com.eventosuy.mobile.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.eventosuy.mobile.servidor.DTEdicionEvento;
import com.eventosuy.mobile.servidor.DTEvento;
import com.eventosuy.mobile.servidor.Publicador;
import com.eventosuy.mobile.servidor.PublicadorService;
import com.eventosuy.mobile.utils.Utilidades;
import com.eventosuy.mobile.servidor.EEstadoEdicion;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Eventos")

public class Eventos extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final static PublicadorService servicio = new PublicadorService();
 	private final static Publicador puerto = servicio.getPublicadorPort();

    // --- GET ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 String accion = request.getParameter("accion");
         if (accion == null) {
             response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada.");
             return;
         }

         switch (accion) {
             case "consulta":
                 Consulta(request, response);
                 break;
             case "presentacion":
            	 List<DTEvento> eventosEnCurso = (List<DTEvento>) puerto.listarEventosEnCurso().getItem();
         		 request.setAttribute("eventosEnCurso", eventosEnCurso);
             
             	 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/eventos/presentacion-eventos.jsp");
                  dispatcher.forward(request, response);
                 break;
             default:
                 response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + accion);
         }
     }

      

    // --- Consulta de evento ---
    private void Consulta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = (String) request.getAttribute("nombre");
        if (nombre == null) {
            nombre = request.getParameter("nombre");
        }

        DTEvento evento = puerto.getDTEvento(nombre);

        request.setAttribute("nombre", evento.getNombre());
        request.setAttribute("descripcion", evento.getDescripcion());
        request.setAttribute("sigla", evento.getSigla());
        
      
        List<String> categoriasList =  puerto.nombresCategorias(nombre).getItem();
        request.setAttribute("categorias", categoriasList);

        request.setAttribute("imagen", evento.getImagen());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaA= Utilidades.XMLGregorianCalendarToDate(evento.getFechaAlta());
        request.setAttribute("fechaAlta", formato.format(fechaA));

        List<DTEdicionEvento> edicionesList = puerto.listarEdicionesEvento(nombre).getItem();
        List<DTEdicionEvento> edicionesAceptadas = edicionesList.stream()
        	    .filter(element -> EEstadoEdicion.ACEPTADA == element.getEstado())
        	    .collect(Collectors.toList());
        
        request.setAttribute("ediciones", edicionesAceptadas);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/eventos/consulta-evento.jsp");
        dispatcher.forward(request, response);
    }
}


