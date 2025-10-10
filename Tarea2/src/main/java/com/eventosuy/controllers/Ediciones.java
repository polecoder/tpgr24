package com.eventosuy.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datatypes.DTDetalleRegistro;
import datatypes.DTEdicionEvento;
import datatypes.DTTipoRegistro;
import excepciones.DatosTipoRegistroInvalidos;
import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorUsuarios;
import logica.Utilidades;

@WebServlet("/Ediciones")
public class Ediciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Ediciones () {super();} // Constructor (no lleva parametros)
	
	private final static Fabrica fabrica = Fabrica.getInstancia();
	private final static IControladorUsuarios controladorUsuarios = fabrica.getControladorUsuarios();
	private final static IControladorEventos controladorEventos = fabrica.getControladorEvento();
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		String CU = request.getParameter("caso_de_uso"); // Llamo distinto JSP dependiendo del Caso de Uso
		if (CU == null) { CU = (String) request.getAttribute("caso_de_uso"); } // Test
		switch (CU) {
		case "ConsultaEdicion":
			request = CUConsultaEdicion(request);
			request.getRequestDispatcher("/WEB-INF/consulta-edicion.jsp").forward(request, response);
			break;
		case "AltaEdicion":
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				CUAltaEdicion(request, response); // Crea la Edicion y muestra su consulta
			} else { 
				request.getRequestDispatcher("/WEB-INF/alta-edicion.jsp").forward(request, response); // Abre el formulario
			}
			break;
		case "AltaTipoRegistro":
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				CUAltaTipoRegistro(request, response); // Crea el TipoRegistro y muestra su consulta
			} else { 
				request.getRequestDispatcher("/WEB-INF/alta-tipo-registro.jsp").forward(request, response); // Abre el formulario
			}
			break;
		case "ConsultaTipoRegistro":
			request = CUConsultaTipoRegistro(request);
			request.getRequestDispatcher("/WEB-INF/consulta-tipo-registro.jsp").forward(request, response);
			break;
		}
	}
	
	private HttpServletRequest CUConsultaEdicion(HttpServletRequest request) {
		String nombreEvento = (String) request.getAttribute("nombre_evento");
		String nombreEdicion = (String) request.getAttribute("nombre_edicion");
		
		DTEdicionEvento dtEdicion = controladorEventos.getDTEdicion(nombreEvento, nombreEdicion); // Asumo tiene la imagen
		Set<String> setPatrocinios = controladorEventos.listarPatrociniosDeEdicion(nombreEvento, nombreEdicion);
		Set<String> setTiposRegistro = controladorEventos.getNombresTipoRegistro(nombreEvento, nombreEdicion);
		Set<DTDetalleRegistro> setRegistros = controladorEventos.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion);
		Map<String, DTDetalleRegistro> asistentesRegistrados = new HashMap<>();
		for (DTDetalleRegistro dtRegistro : setRegistros) {
			asistentesRegistrados.put(dtRegistro.getNicknameAsistente(), dtRegistro);
		}
		
		request.setAttribute("detalle_edicion", dtEdicion);
		request.setAttribute("lista_patrocinios", setPatrocinios);
		request.setAttribute("lista_tipos_registro", setTiposRegistro);
		
		String nickname = (String) request.getSession().getAttribute("nickname");
		if (nickname != null && nickname.equals(dtEdicion.getOrganizadorNickname())) {
			request.setAttribute("es_organizador", true);
			request.setAttribute("lista_registros", setRegistros);
		} else if (asistentesRegistrados.keySet().contains(nickname)) {
			request.setAttribute("es_asistente", true);
			request.setAttribute("detalle_registro", asistentesRegistrados.get(nickname));
		}
		
		return request;
	}
	
	private void CUAltaEdicion(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nombreEvento = request.getParameter("nombreEvento");
		String nombreEdicion = request.getParameter("nombreEdicion");
		String sigla = request.getParameter("sigla");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaInicio = formatter.parse(request.getParameter("fechaInicio"));
		Date fechaFin = formatter.parse(request.getParameter("fechaFin"));
		Date fechaAlta = new Date();
		String ciudad = request.getParameter("ciudad");
		String pais = request.getParameter("pais");
		String nickOrganizador = (String) request.getSession().getAttribute("nickname");
		
		controladorEventos.altaEdicionEvento(nombreEvento, nombreEdicion, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, nickOrganizador);
		
		request.setAttribute("nombre_edicion", nombreEdicion);
		request = CUConsultaEdicion(request);
		request.getRequestDispatcher("/WEB-INF/consulta-edicion.jsp").forward(request, response);
	}
	
	private HttpServletRequest CUConsultaTipoRegistro(HttpServletRequest request) throws TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste {
		String nombreEvento = request.getParameter("nombre_evento");
		String nombreEdicion = request.getParameter("nombre_edicion");
		String nombreTipoRegistro = (String) request.getAttribute("nombre_tipo_registro");
		DTTipoRegistro dtTipoRegistro = controladorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro);
		request.setAttribute("detalle_tipo_registro", dtTipoRegistro);
		
		return request;
	}
	
	private void CUAltaTipoRegistro(HttpServletRequest request, HttpServletResponse response) throws NombreTipoRegistroRepetido, DatosTipoRegistroInvalidos, TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste, ServletException, IOException {
		String nombreTipoRegistro = request.getParameter("nombreTipoRegistro");
		String descripcion = request.getParameter("descripcion");
		float costo = Float.parseFloat(request.getParameter("costo"));
		int cuposMax = Integer.parseInt(request.getParameter("cuposMax"));
		String nombreEvento = request.getParameter("nombre_evento");
		String nombreEdicion = request.getParameter("nombre_edicion");
		
		controladorEventos.altaTipoRegistro(nombreTipoRegistro, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);		
		request.setAttribute("nombre_tipo_registro", nombreTipoRegistro);
		request = CUConsultaTipoRegistro(request);
		request.getRequestDispatcher("/WEB-INF/consulta-tipo-registro.jsp").forward(request, response);
	}
	
	private HttpServletRequest Test(HttpServletRequest request) throws Exception  {
		String pais, ciudad, sigla, descripcion;
		Date fecha;
		float costo;
		int cuposMax;
		List<String> categorias;
		String nombreEventoTest1, nombreEdicionTest1, nombreCategoriaTest1, nombreTipoRegistroTest1;
		
		// Variables generales
		pais = "Uruguay";
		ciudad = "Montevideo";
		sigla = "FING";
		descripcion = "descripcion";
		fecha = Utilidades.toDate(LocalDate.now());
		categorias = new ArrayList<String>();
		costo = 1f;
		cuposMax = 1;
		
		// Variables Test 1
		nombreEventoTest1 = "nombreEventoTest1";
		nombreEdicionTest1 = "nombreEdicionTest1";				
		nombreCategoriaTest1 = "categoriaTest1";
		nombreTipoRegistroTest1 = "nombreTipoRegistroTest1";
		
		// Testea un alta exitoso estandar
		try {
			controladorEventos.altaCategoria(nombreCategoriaTest1);
		} catch (Exception e) {
			 throw new RuntimeException("Error al registrar la categoría: " + e.getMessage());
		}
		categorias.add(nombreCategoriaTest1);
		controladorEventos.altaEvento(nombreEventoTest1, descripcion, fecha, sigla, categorias);
		controladorEventos.altaEdicionEvento(nombreEventoTest1, nombreEdicionTest1, sigla, fecha, fecha, fecha, ciudad, pais, "");
		controladorEventos.altaTipoRegistro(nombreTipoRegistroTest1, descripcion, costo, cuposMax, nombreEventoTest1, nombreEdicionTest1);
		
		request.setAttribute("caso_de_uso", "AltaTipoRegistro");
		request.setAttribute("nombre_evento", nombreEventoTest1);
		request.setAttribute("nombre_edicion", nombreEdicionTest1);
		request.setAttribute("nombre_tipo_registro", nombreTipoRegistroTest1);
		
		return request;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*try {
			request = Test(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
