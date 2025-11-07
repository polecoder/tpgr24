package com.eventosuy.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import datatypes.*;
import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.RegistroNoExiste;
import excepciones.UsuarioNoExiste;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.Fabrica;
import logica.IControladorPDF;

/**
 * Servlet implementation class Home
 */
@WebServlet("/pdf")
@MultipartConfig
public class ServletPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletPDF() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Fabrica fabrica = Fabrica.getInstancia();
		IControladorPDF controladorPDF = fabrica.getControladorPDF();	
		
		DTDetalleRegistro dtRegistro = construirDTDetalleRegistro(request);
		
		DTPDF dtPDF = null;
		try { 
			dtPDF = controladorPDF.getDTPDF(dtRegistro); 
		} catch (UsuarioNoExiste | EventoNoExiste | EdicionNoExiste | RegistroNoExiste e) { e.printStackTrace(); }
		String nombrePDF = String.format("Constancia de asistencia-%s-%s.pdf", dtPDF.getNombreEdicion(),dtPDF.getNombreCompleto());
		
		byte[] PDFbytes = controladorPDF.generarPDFAsistencia(dtPDF, null);
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", String.format("attachment; filename=%s", nombrePDF));
		response.setContentLength(PDFbytes.length);

		response.getOutputStream().write(PDFbytes);
		response.getOutputStream().flush();
	}

	private DTDetalleRegistro construirDTDetalleRegistro(HttpServletRequest request) {
		DTDetalleUsuario dtUsuario = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
		
		String nombreEdicion = request.getParameter("nombreEdicion");
		String nombreTipo = request.getParameter("nombreTipo");
		String codigoPatrocinio = request.getParameter("codigoPatrocinio");

		String fechaStr = request.getParameter("fecha");

		float costo = Float.parseFloat(request.getParameter("costo"));
		boolean asistio = Boolean.parseBoolean(request.getParameter("asistio"));
		Date fecha = null;
		try { fecha = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha")); } 
		catch (ParseException ignore) { }
		
		return new DTDetalleRegistro(nombreEdicion, dtUsuario.getNickname(), nombreTipo, fecha, costo, codigoPatrocinio, asistio);
	}
	
	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException { }
	
}
