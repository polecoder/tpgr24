package logica;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import datatypes.DTPDF;



public class ControladorPDF implements IControladorPDF{
	private static ControladorPDF instance;
	
	private ControladorPDF() {}
	
	public static ControladorPDF getInstance() {
		if (instance == null) {
			instance = new ControladorPDF();
		}
		return instance;
	}
	
	@Override
	public void generarPDFAsistencia(DTPDF dtPDF, String path) {
		if (path == null) { path = "Constancias de asistencia\\"; } // path por defecto (relativo al working directory)
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

            byte[] PDFbyte =  baos.toByteArray();
            guardarPDF(PDFbyte, String.format("%s\\Constancia de asistencia-%s.pdf", path, dtPDF.getNombreCompleto()));
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
	}
	
	public void guardarPDF(byte[] PDFBytes, String path) throws IOException {
		Files.write(Paths.get(path), PDFBytes);
	}
}
