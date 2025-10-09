package com.eventosuy.controllers;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import datatypes.DTEvento;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.Fabrica;
import logica.IControladorEventos;

@WebServlet("/Eventos")
public class Eventos extends HttpServlet {
	 Fabrica fab= Fabrica.getInstancia();
     IControladorEventos IContEv= fab.getControladorEvento();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada.");
            return;
        }

        switch (accion) {
            case "alta":
                Alta(request, response);
                break;
            case "confirmarAlta":
                confirmarAlta(request, response);
                break;
            case "consulta":
                Consulta(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + accion);
        }
    }

    // --- Muestra el formulario para dar de alta un evento ---
    private void Alta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/altaEvento.jsp");
        dispatcher.forward(request, response);
    }

    // --- Procesa los datos enviados desde el formulario de alta ---
    private void confirmarAlta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Date fechaAlta = new Date();
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String sigla = request.getParameter("sigla");
        String[] categoriasSeleccionadas = request.getParameterValues("categorias");
        if (nombre == null || nombre.trim().isEmpty() ||
            descripcion == null || descripcion.trim().isEmpty() ||
            sigla == null || sigla.trim().isEmpty() ||
            categoriasSeleccionadas == null || categoriasSeleccionadas.length == 0) {

            request.setAttribute("error", "Todos los campos son obligatorios.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/altaEvento.jsp");
            dispatcher.forward(request, response);
            return;
        }

        List<String> CatSel = Arrays.asList(categoriasSeleccionadas);
        IContEv.altaEvento(nombre, descripcion,fechaAlta, sigla, CatSel);

        //  guarda el evento y redirige a la consulta
        request.setAttribute("mensaje", "Evento '" + nombre + "' creado correctamente.");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/consultaEvento.jsp");
        dispatcher.forward(request, response);
    }

    // --- Muestra una página con los datos de consulta de un evento ---
    private void Consulta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String nombre = request.getParameter("nombre");
    	DTEvento evento= IContEv.getDTEvento(nombre);
        request.setAttribute("nombre", evento.getNombre());
        request.setAttribute("descripcion", evento.getDescripcion());
        request.setAttribute("sigla", evento.getSigla());
        request.setAttribute("categorias", IContEv.nombresCategorias(nombre));
        request.setAttribute("fechaAlta", evento.getFechaAlta());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/consultaEvento.jsp");
        dispatcher.forward(request, response);
    }
}
   