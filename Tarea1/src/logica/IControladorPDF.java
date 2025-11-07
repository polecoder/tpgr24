package logica;

import java.io.IOException;

import datatypes.DTDetalleRegistro;
import datatypes.DTPDF;
import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.RegistroNoExiste;
import excepciones.UsuarioNoExiste;

public interface IControladorPDF {
	/**
	 * Genera la constancia de asistencia para los datos de dtPDF y lo guarda en path
	 * Si path == null se usa el por defecto
	 * @param dtPDF
	 * @param path
	 * @return
	 */
	public byte[] generarPDFAsistencia(DTPDF dtPDF, String path);
	
	public DTPDF getDTPDF(DTDetalleRegistro dtRegistro) throws UsuarioNoExiste, EventoNoExiste, EdicionNoExiste, RegistroNoExiste;
}
