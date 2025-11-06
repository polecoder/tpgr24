package logica;

import java.io.IOException;

import datatypes.DTPDF;

public interface IControladorPDF {
	/**
	 * Genera la constancia de asistencia para los datos de dtPDF y lo guarda en path
	 * Si path == null se usa el por defecto
	 * @param dtPDF
	 * @param path
	 * @return
	 */
	public void generarPDFAsistencia(DTPDF dtPDF, String path);
}
