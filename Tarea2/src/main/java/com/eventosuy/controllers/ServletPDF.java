package com.eventosuy.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.eventosuy.servidor.DTDetalleRegistro;
import com.eventosuy.servidor.DTDetalleUsuario;
import com.eventosuy.servidor.EdicionNoExiste_Exception;
import com.eventosuy.servidor.EventoNoExiste_Exception;
import com.eventosuy.servidor.Publicador;
import com.eventosuy.servidor.PublicadorService;
import com.eventosuy.servidor.UsuarioNoExiste_Exception;
import com.eventosuy.servidor.RegistroNoExiste_Exception;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletPDF
 */
@WebServlet("/pdf")
@MultipartConfig
public class ServletPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Servicios
	private final static PublicadorService servicio = new PublicadorService();
	private final static Publicador puerto = servicio.getPublicadorPort();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletPDF() {
		super();
	}

	/**
	 * @throws IOException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			DTDetalleRegistro dtRegistro = construirDTDetalleRegistro(request);
			
			if (dtRegistro == null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos incompletos para generar el PDF.");
				return;
			}

			byte[] PDFbytes = puerto.generarPDFDirecto(dtRegistro);
			
			if (PDFbytes == null || PDFbytes.length == 0) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar el PDF");
				return;
			}

			String nombrePDF = String.format("Constancia_asistencia_%s.pdf", 
				sanitizeFilename(dtRegistro.getNombreEdicion()));

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", nombrePDF));
			response.setContentLength(PDFbytes.length);
			response.getOutputStream().write(PDFbytes);
			response.getOutputStream().flush();

		} catch (UsuarioNoExiste_Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no existe");
		} catch (EventoNoExiste_Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Evento no existe");
		} catch (EdicionNoExiste_Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Edición no existe");
		} catch (RegistroNoExiste_Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Registro no existe");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno: " + e.getMessage());
		}
	}

	private DTDetalleRegistro construirDTDetalleRegistro(HttpServletRequest request) {
		try {
			DTDetalleUsuario dtUsuario = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
			
			if (dtUsuario == null) return null;

			String nombreEdicion = request.getParameter("nombreEdicion");
			String nombreTipo = request.getParameter("nombreTipo");
			String codigoPatrocinio = request.getParameter("codigoPatrocinio");
			String fechaStr = request.getParameter("fecha");
			String costoStr = request.getParameter("costo");
			String asistioStr = request.getParameter("asistio");

			if (nombreEdicion == null || nombreTipo == null || costoStr == null || 
			    asistioStr == null || fechaStr == null) {
				return null;
			}

			float costo = Float.parseFloat(costoStr);
			boolean asistio = Boolean.parseBoolean(asistioStr);
			Date fecha = null;
			try { 
				fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr); 
			} catch (ParseException e) { 
				fecha = new Date();
			}

			XMLGregorianCalendar fechaXML = dateToXMLGregorianCalendar(fecha);

			DTDetalleRegistro DTrespuesta = new DTDetalleRegistro();
			DTrespuesta.setNombreEdicion(nombreEdicion);
			DTrespuesta.setNicknameAsistente(dtUsuario.getNickname());
			DTrespuesta.setNombreTipo(nombreTipo);
			DTrespuesta.setFecha(fechaXML);
			DTrespuesta.setCosto(costo);
			DTrespuesta.setCodigoPatrocinio(codigoPatrocinio != null ? codigoPatrocinio : "");
			DTrespuesta.setAsistio(asistio);

			return DTrespuesta;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static XMLGregorianCalendar dateToXMLGregorianCalendar(Date date) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String sanitizeFilename(String filename) {
		if (filename == null) return "archivo";
		return filename.replaceAll("[^a-zA-Z0-9._-]", "_");
	}

	/**
	 * @throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}