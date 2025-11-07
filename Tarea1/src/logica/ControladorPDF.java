package logica;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleRegistro;
import datatypes.DTEdicionEvento;
import datatypes.DTEvento;
import datatypes.DTPDF;
import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.RegistroNoExiste;
import excepciones.UsuarioNoExiste;



public class ControladorPDF implements IControladorPDF{
	private static ControladorPDF instance;
	private IControladorEventos controladorEventos;
	private IControladorUsuarios controladorUsuarios;
	
	private ControladorPDF() {
		Fabrica fabrica = Fabrica.getInstancia();
		controladorEventos = ControladorEventos.getInstance();
		controladorUsuarios = ControladorUsuarios.getInstance();
	}
	
	public static ControladorPDF getInstance() {
		if (instance == null) {
			instance = new ControladorPDF();
		}
		return instance;
	}
	
	@Override
	public byte[] generarPDFAsistencia(DTPDF dtPDF, String path) {
		if (path == null) { path = "..\\..\\Facultad\\tprog\\tpgr24\\Constancias de asistencia"; } // path por defecto (relativo al working directory - system32 para web)
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) { // Garantiza que se cierre automaticamente
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            document.add(new Paragraph("Constancia de asistencia").setFontSize(18)); // Titulo
            document.add(new Paragraph("Nombre completo del asistente: "+ dtPDF.getNombreCompleto()));
            document.add(new Paragraph("Nombre del evento: " + dtPDF.getNombreEvento()));
            document.add(new Paragraph("Nombre de la edición: " + dtPDF.getNombreEdicion()));
            document.add(new Paragraph("Ciudad: " + dtPDF.getCiudad()));
            document.add(new Paragraph(String.format("Fechas de la edición: %s - %s", dtPDF.getFechaInicio().toString(), dtPDF.getFechaFin()).toString()));
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
        	throw new RuntimeException("Error generando PDF", e);
        }
	}
	
	public void guardarPDF(byte[] PDFBytes, String path) throws IOException {
		Files.write(Paths.get(path), PDFBytes);
	}
	
	@Override
	public DTPDF getDTPDF(DTDetalleRegistro dtRegistro) throws UsuarioNoExiste, EventoNoExiste, EdicionNoExiste, RegistroNoExiste {
		if (dtRegistro == null) { throw new RegistroNoExiste("Error generando la constancia de asistencia"); }
		DTDetalleAsistente dtAsistente = (DTDetalleAsistente) controladorUsuarios.mostrarDetalleUsuario(dtRegistro.getNicknameAsistente());
		String nombreCompleto = String.format("%s %s",dtAsistente.getNombre(), dtAsistente.getApellido());
		
		String nombreEdicion = dtRegistro.getNombreEdicion();
				
		DTEvento dtEvento = controladorEventos.getEventoPorEdicion(nombreEdicion);
		if (dtEvento == null) { throw new EventoNoExiste("El evento no existe"); }
		String nombreEvento = dtEvento.getNombre();
		
		DTEdicionEvento dtEdicion = controladorEventos.getDTEdicion(nombreEvento, nombreEdicion);
		if (dtEdicion == null) { throw new EdicionNoExiste("La edicion no existe"); }
		String ciudad = dtEdicion.getCiudad();
		Date fechaInicio = dtEdicion.getFechaInicio();
		Date fechaFin = dtEdicion.getFechaFin();
		
		return new DTPDF(nombreCompleto, nombreEvento, nombreEdicion, ciudad, fechaInicio, fechaFin);
	}
}
