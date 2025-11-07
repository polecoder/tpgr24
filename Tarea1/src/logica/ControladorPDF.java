package logica;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

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

            // 1. HEADER CON FRANJA DE COLOR
            Color colorPrincipal = new DeviceRgb(0, 123, 255); // azul Bootstrap
            PdfFont fontBold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            Div header = new Div()
                .setBackgroundColor(colorPrincipal)
                .setWidth(UnitValue.createPercentValue(100))
                .setTextAlignment(TextAlignment.CENTER)
                .setPadding(10);
            header.add(new Paragraph("CONSTANCIA DE ASISTENCIA")
                .setFontSize(18)
                .setFont(fontBold)
                .setFontColor(ColorConstants.WHITE));
            document.add(header);

            // 2. Tabla para agregar IMG | TXT
            Table layoutSuperior = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                .useAllAvailableWidth()
                .setMarginTop(20);

            // Imagen (columna izquierda)
            byte[] imagenEdicion = controladorEventos.getDTEdicion(dtPDF.getNombreEvento(), dtPDF.getNombreEdicion()).getImagen();
            ImageData imageData = ImageDataFactory.create(imagenEdicion);
            Image img = new Image(imageData)
                .setAutoScale(true)
                .setMarginRight(10);
            layoutSuperior.addCell(new Cell().add(img).setBorder(Border.NO_BORDER));

            // Texto alineado a la imagen (columna derecha)
            Cell celdaTexto = new Cell().setBorder(Border.NO_BORDER);
	
	         // Evento
	         Paragraph pEvento = new Paragraph()
	             .setFontSize(12)
	             .setTextAlignment(TextAlignment.LEFT);
	         pEvento.add(new Text("Evento: ").setFont(fontBold));
	         pEvento.add(new Text(dtPDF.getNombreEvento()));
	         celdaTexto.add(pEvento);
	
	         // Edición
	         Paragraph pEdicion = new Paragraph()
	             .setFontSize(12)
	             .setTextAlignment(TextAlignment.LEFT);
	         pEdicion.add(new Text("Edición: ").setFont(fontBold));
	         pEdicion.add(new Text(dtPDF.getNombreEdicion()));
	         celdaTexto.add(pEdicion);
	
	         // Inicio
	         Paragraph pInicio = new Paragraph()
	             .setFontSize(12)
	             .setTextAlignment(TextAlignment.LEFT);
	         pInicio.add(new Text("Inicio: ").setFont(fontBold));
	         pInicio.add(new Text(new SimpleDateFormat("dd-MM-yyyy").format(dtPDF.getFechaInicio())));
	         celdaTexto.add(pInicio);
	
	         // Fin
	         Paragraph pFin = new Paragraph()
	             .setFontSize(12)
	             .setTextAlignment(TextAlignment.LEFT);
	         pFin.add(new Text("Fin: ").setFont(fontBold));
	         pFin.add(new Text(new SimpleDateFormat("dd-MM-yyyy").format(dtPDF.getFechaFin())));
	         celdaTexto.add(pFin);
	
	         // Agregar la celda completa a la tabla
	         layoutSuperior.addCell(celdaTexto);

            document.add(layoutSuperior);

            // 3. TEXTO GRANDE INFERIOR
            Paragraph textoInferior = new Paragraph()
                .setFontSize(12)
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setMarginTop(20);

            textoInferior.add(new Text("Por la presente se certifica que el asistente "));
            textoInferior.add(new Text(dtPDF.getNombreCompleto()).setFont(fontBold));

            textoInferior.add(new Text(" ha asistido a la edición "));
            textoInferior.add(new Text(dtPDF.getNombreEdicion()).setFont(fontBold));

            textoInferior.add(new Text(", correspondiente al evento "));
            textoInferior.add(new Text(dtPDF.getNombreEvento()).setFont(fontBold));

            textoInferior.add(new Text(", la cuál se llevó a cabo en "));
            textoInferior.add(new Text(dtPDF.getCiudad()).setFont(fontBold));
            
            document.add(textoInferior);

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
