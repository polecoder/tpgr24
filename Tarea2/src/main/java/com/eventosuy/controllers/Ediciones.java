package com.eventosuy.controllers;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import datatypes.DTDetalleRegistro;
import datatypes.DTEdicionEvento;
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
		if (!("POST".equalsIgnoreCase(request.getMethod()))) {
			request.setAttribute("caso_de_uso", "AltaEdicion");
		}
		request.setAttribute("nombre_evento", "ExpoTech1");
		
		
		String CU = (String) request.getAttribute("caso_de_uso"); // Llamo distinto JSP dependiendo del Caso de Uso
		switch (CU) {
		case "ConsultaEdicion":
			request = CUConsultaEdicion(request);
			request.getRequestDispatcher("/WEB-INF/consulta-edicion.jsp").forward(request, response);
			break;
		case "AltaEdicion":
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				request = CUAltaEdicion(request);
				request.getRequestDispatcher("/WEB-INF/consulta-edicion.jsp").forward(request, response);
			} else { request.getRequestDispatcher("/WEB-INF/alta-edicion.jsp").forward(request, response); }
			break;
		case "AltaTipoRegistro":
			request.getRequestDispatcher("/WEB-INF/alta-tipo-registro.jsp").forward(request, response);
			break;
		case "ConsultaTipoRegistro":
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
	
	private HttpServletRequest CUAltaEdicion(HttpServletRequest request) throws Exception {
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
		return request;
	}
	
	private void Test()  {
		Fabrica.getInstancia().getControladorEvento().altaEvento("ExpoTech1", "Feria de tecnología", Utilidades.toDate(LocalDate.of(2023, 5, 10)), "EXP", Collections.emptyList());
        String nombreEdicion = "ExpoTech2025";
        String sigla = "ET25";
        String ciudad = "Montevideo";
        String pais = "Uruguay";
        Date fechaAlta = Utilidades.toDate(LocalDate.of(2025, 8, 29));
        Date fechaInicio = Utilidades.toDate(LocalDate.of(2025, 9, 10));
        Date fechaFin = Utilidades.toDate(LocalDate.of(2025, 9, 12));
        String organizador = "Organizador3";

        try {
        	Fabrica.getInstancia().getControladorEvento().altaEdicionEvento("ExpoTech1", nombreEdicion, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, organizador);
        } catch (Exception ex) {
        	
        }
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Test();
		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Test();
		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
