package com.eventosuy.mobile.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import datatypes.DTEdicionEvento;
import datatypes.DTEvento;
import datatypes.EEstadoEdicion;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.Fabrica;
import logica.IControladorEventos;

@WebServlet("mobile/Eventos")

public class Eventos extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Fabrica fab = Fabrica.getInstancia();
    IControladorEventos IContEv = fab.getControladorEvento();

    // --- GET ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            Consulta(request, response);
    }

    // --- POST ---
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leer acción desde getParameter() ya que MultipartConfig permite mezclarlo
        String accion = request.getParameter("accion");
        if (accion == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada.");
            return;
        }

        switch (accion) {
            case "consulta":
                Consulta(request, response);
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

        DTEvento evento = IContEv.getDTEvento(nombre);

        request.setAttribute("nombre", evento.getNombre());
        request.setAttribute("descripcion", evento.getDescripcion());
        request.setAttribute("sigla", evento.getSigla());
        
        Set<String> categoriasSet = IContEv.nombresCategorias(nombre);
        List<String> categoriasList = new ArrayList<>(categoriasSet);
        request.setAttribute("categorias", categoriasList);

        request.setAttribute("imagen", evento.getImagen());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        request.setAttribute("fechaAlta", formato.format(evento.getFechaAlta()));

        Map<String, DTEdicionEvento> edicionesMap = IContEv.listarEdicionesEvento(nombre);
        List<DTEdicionEvento> edicionesList = new ArrayList<>(edicionesMap.values());
        List<DTEdicionEvento> edicionesAceptadas = edicionesList.stream()
        	    .filter(element -> EEstadoEdicion.Aceptada == element.getEstado())
        	    .collect(Collectors.toList());
        
        
        request.setAttribute("ediciones", edicionesAceptadas);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/eventos/consulta-evento.jsp");
        dispatcher.forward(request, response);
    }
}


