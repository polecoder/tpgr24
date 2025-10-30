package com.eventosuy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import carga.CargaDatos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.eventosuy.servidor.DTEventoEntry;
import com.eventosuy.servidor.DTEventoEntryArray;
import com.eventosuy.servidor.DTDetalleUsuarioArray;
import com.eventosuy.servidor.DTDetalleUsuario;
import com.eventosuy.servidor.DTEvento;
import com.eventosuy.servidor.DTEventoArray;
import com.eventosuy.servidor.Publicador;
import com.eventosuy.servidor.PublicadorService;

/**
 * Servlet implementation class Home
 */
@WebServlet ("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
    }
    
    /**
     * Carga todos los datos de prueba ubicados en la carpeta data/
     */
    private void cargarDatos() {
    	List<String> rutasArchivos = List.of(
    			getServletContext().getRealPath("data/2025Categorias.csv"),
    			getServletContext().getRealPath("data/2025Usuarios.csv"),
    			getServletContext().getRealPath("data/2025Instituciones.csv"),
    			getServletContext().getRealPath("data/2025Usuarios-Organizadores.csv"),
    			getServletContext().getRealPath("data/2025Usuarios-Asistentes.csv"),
    			getServletContext().getRealPath("data/2025Eventos.csv"),
    			getServletContext().getRealPath("data/2025EdicionesEventos.csv"),
    			getServletContext().getRealPath("data/2025TipoRegistro.csv"),
    			getServletContext().getRealPath("data/2025Patrocinios.csv"),
    			getServletContext().getRealPath("data/2025Registros.csv")
    	);
    	String[] rutas = rutasArchivos.toArray(new String[0]);
    	
    	CargaDatos cargaDatos = CargaDatos.getInstancia();
    	cargaDatos.cargarArchivos(rutas);
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        cargarDatos();
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
	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PublicadorService servicio = new PublicadorService();
		Publicador puerto = servicio.getPublicadorPort();

		List<DTEvento> eventosEnCurso = puerto.listarEventosEnCurso().getItem();
		req.setAttribute("eventosEnCurso", eventosEnCurso);
		
		List<DTEvento> eventosFinalizados = puerto.listarEventosFinalizados().getItem();
		req.setAttribute("eventosFinalizados", eventosFinalizados);
		
		List<DTDetalleUsuario> usuarios = puerto.listarDetalleUsuarios().getItem();
		req.setAttribute("usuarios", usuarios);
		
		List<String> categorias = puerto.getListaCategorias().getItem();
		req.setAttribute("categorias", categorias);
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
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
