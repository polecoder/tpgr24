package com.eventosuy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.eventosuy.servidor.Publicador;
import com.eventosuy.servidor.PublicadorService;
import com.eventosuy.utils.Utilidades;
import com.eventosuy.servidor.DTEdicionEvento;
import com.eventosuy.servidor.DTEvento;
import com.eventosuy.servidor.EEstadoEdicion;
import datatypes.EEstadoEvento;

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

    // Servicios
 	private final static PublicadorService servicio = new PublicadorService();
 	private final static Publicador puerto = servicio.getPublicadorPort();

    // --- GET ---
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("alta".equals(accion)) {
            Alta(request,response);
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
            case "finalizar" :
            	Finalizar(request, response);
             	break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida: " + accion);
        }
      
    }
    
    private void Finalizar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	String nombre = request.getParameter("nombre");
    	
    	//solo organizador
    	com.eventosuy.models.TipoUsuario tipo = (com.eventosuy.models.TipoUsuario) request.getSession().getAttribute("tipoUsuarioLogeado");
    	String nick = (String) request.getSession().getAttribute("usuarioNickname");
    	
    	if (tipo == null || tipo!= com.eventosuy.models.TipoUsuario.ORGANIZADOR || nick == null) {
    		response.sendError(HttpServletResponse.SC_FORBIDDEN);
    		return;
    	}
    	try {
    		puerto.finalizarEvento(nombre);
    		request.setAttribute("flashOK", "Evento finalizado correctamente");
    		request.setAttribute("nombre", nombre);
    		Consulta(request, response);
    	}catch(excepciones.EventoFinalizado e) {
    		request.setAttribute("flashWARN", "El evento ya esta finalizado");
    		request.setAttribute("nombre", nombre);
    		Consulta(request, response);
    	} catch(excepciones.EventoNoExiste e) {
    		response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    	} catch(Exception e) {
    		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo finalizar el evento.");
    	} 
    }


	// --- Formulario de alta ---
    private void Alta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    		List<String> categorias = (List<String>) puerto.getListaCategorias();
        request.setAttribute("categorias", categorias);         
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/eventos/alta-evento.jsp");
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

        // TODO: Manejar errores estandarizando con el parámetro error (actualmente se usa JS)
        // Validar campos obligatorios
        if (nombre == null || nombre.trim().isEmpty() ||
            descripcion == null || descripcion.trim().isEmpty() ||
            sigla == null || sigla.trim().isEmpty() ||
            categoriasSeleccionadas == null || categoriasSeleccionadas.length == 0) {

            request.setAttribute("error", "Todos los campos son obligatorios.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/eventos/alta-evento.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Validar existencia de evento
        if (puerto.existeEvento(nombre)) {
        	List<String> categorias = (List<String>) puerto.getListaCategorias();
            request.setAttribute("categorias", categorias);    
            request.setAttribute("error", "Ya existe un evento con este nombre");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/eventos/alta-evento.jsp");
            dispatcher.forward(request, response);
            return;
        }

        List<String> CatSel = Arrays.asList(categoriasSeleccionadas);
        XMLGregorianCalendar xmlGregorianCalendarFechaAlta;
		try {
			xmlGregorianCalendarFechaAlta = Utilidades.dateToXMLGregorianCalendar(fechaAlta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			xmlGregorianCalendarFechaAlta = null;
		}
        puerto.altaEvento(nombre, descripcion, xmlGregorianCalendarFechaAlta, sigla, (com.eventosuy.servidor.ArrayList) CatSel, imagen, EEstadoEvento.EN_CURSO);

        // Preparar datos para la vista de consulta
        request.setAttribute("mensaje", "Evento '" + nombre + "' creado correctamente.");
        request.setAttribute("nombre", nombre);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("sigla", sigla);
        request.setAttribute("categorias", CatSel);
        request.setAttribute("imagen", imagen);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        request.setAttribute("fechaAlta", formato.format(fechaAlta));
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/eventos/consulta-evento.jsp");
        dispatcher.forward(request, response);
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
        
        Set<String> categoriasSet = (Set<String>) puerto.nombresCategorias(nombre);
        List<String> categoriasList = new ArrayList<>(categoriasSet);
        request.setAttribute("categorias", categoriasList);

        request.setAttribute("imagen", evento.getImagen());

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        request.setAttribute("fechaAlta", formato.format(evento.getFechaAlta()));
        // estado del evento
        boolean finalizado = (evento.getEstado() == EEstadoEvento.FINALIZADO);
        request.setAttribute("eventoFinalizado", finalizado);
        
        //boton finalizar para organizadores
        com.eventosuy.models.TipoUsuario tipo = (com.eventosuy.models.TipoUsuario) request.getSession().getAttribute("tipoUsuarioLogeado");
            boolean esOrganizador = (tipo != null && tipo == com.eventosuy.models.TipoUsuario.ORGANIZADOR);
            request.setAttribute("esOrganizador", esOrganizador);

        Map<String, DTEdicionEvento> edicionesMap = (Map<String, DTEdicionEvento>) puerto.listarEdicionesEvento(nombre);
        List<DTEdicionEvento> edicionesList = new ArrayList<>(edicionesMap.values());
        List<DTEdicionEvento> edicionesAceptadas = edicionesList.stream()
        	    .filter(element -> EEstadoEdicion.ACEPTADA == element.getEstado())
        	    .collect(Collectors.toList());
        
        
        request.setAttribute("ediciones", edicionesAceptadas);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/eventos/consulta-evento.jsp");
        dispatcher.forward(request, response);
    }
}

