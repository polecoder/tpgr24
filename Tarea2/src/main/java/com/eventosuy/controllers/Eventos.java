package com.eventosuy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import datatypes.DTEdicionEvento;
import datatypes.DTEvento;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import logica.Fabrica;
import logica.IControladorEventos;

@WebServlet("/Eventos")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB antes de escribir en disco
    maxFileSize = 1024 * 1024 * 5,   // 5MB por archivo
    maxRequestSize = 1024 * 1024 * 10 // 10MB total
)
public class Eventos extends HttpServlet {

    private static final long serialVersionUID = 1L;

    Fabrica fab = Fabrica.getInstancia();
    IControladorEventos IContEv = fab.getControladorEvento();

    // --- GET ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("alta".equals(accion)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Alta/AltaEvento.jsp");
            dispatcher.forward(request, response);
        } else if ("consulta".equals(accion)) {
            Consulta(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida en GET: " + accion);
        }
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

    // --- Formulario de alta ---
    private void Alta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Alta/AltaEvento.jsp");
        dispatcher.forward(request, response);
    }

    // --- Procesar alta ---
    private void confirmarAlta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Date fechaAlta = new Date();
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String sigla = request.getParameter("sigla");
        String[] categoriasSeleccionadas = request.getParameterValues("categorias");

        byte[] imagen = null;
        Part filePart = request.getPart("imagen"); // leer imagen opcional
        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream()) {
                imagen = inputStream.readAllBytes();
            }
        }

        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty() ||
            descripcion == null || descripcion.trim().isEmpty() ||
            sigla == null || sigla.trim().isEmpty() ||
            categoriasSeleccionadas == null || categoriasSeleccionadas.length == 0) {

            request.setAttribute("error", "Todos los campos son obligatorios.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Alta/AltaEvento.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Validar existencia de evento
        if (IContEv.existeEvento(nombre)) {
            request.setAttribute("error", "Ya existe un evento con este nombre");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Errores/altaEventoError.jsp");
            dispatcher.forward(request, response);
            return;
        }

        List<String> CatSel = Arrays.asList(categoriasSeleccionadas);
        IContEv.altaEvento(nombre, descripcion, fechaAlta, sigla, CatSel, imagen);

        // Preparar datos para la vista de consulta
        request.setAttribute("mensaje", "Evento '" + nombre + "' creado correctamente.");
        request.setAttribute("nombre", nombre);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("sigla", sigla);
        request.setAttribute("categorias", CatSel);
        request.setAttribute("imagen", imagen);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        request.setAttribute("fechaAlta", formato.format(fechaAlta));
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Consulta/ConsultaEvento.jsp");
        dispatcher.forward(request, response);
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
        request.setAttribute("categorias", IContEv.nombresCategorias(nombre));
        request.setAttribute("imagen", evento.getImagen());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        request.setAttribute("fechaAlta", formato.format(evento.getFechaAlta()));

        Map<String, DTEdicionEvento> edicionesMap = IContEv.listarEdicionesEvento(nombre);
        List<DTEdicionEvento> edicionesList = new ArrayList<>(edicionesMap.values());
        request.setAttribute("ediciones", edicionesList);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Consulta/ConsultaEvento.jsp");
        dispatcher.forward(request, response);
    }
}

