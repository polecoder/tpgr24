package logica;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;


public class Utilidades {
	public static Date toDate(LocalDate fecha) {
		return Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * Busca la imagen ubicada en src/images con el nombre dado, la transforma a bytes y la devuelve.
	 * Si nombreArchivo es null o "-" entonces directamente no busca la imagen, devuelve null
	 * Retorna null si la imagen no existe.
	 * Retorna un error si falla la conversión.
	 * 
	 * @param nombreArchivo
	 * @return
	 */
	public static byte[] archivoImagenToByte(String nombreArchivo) {
		byte[] bytesImagen = null;
		if (nombreArchivo != null && !nombreArchivo.equals("-")) {
		    try (InputStream inputStream = Utilidades.class.getResourceAsStream("/images/" + nombreArchivo)) {
		        if (inputStream != null) {
		            bytesImagen = inputStream.readAllBytes();
		        }
		    } catch (IOException e) {
		        System.err.println("Error leyendo imagen: " + nombreArchivo);
		        e.printStackTrace();
		    }
		}
		return bytesImagen;
	}
	
	/**
	 * Dada una fecha del tipo XMLGeorgianCalendar devuelve su correspondiente en el tipo Date.
	 * ACLARACIÓN: Esta función está pensada para la parte web
	 * @param xmlGeorgianCalendarFecha
	 * @return
	 */
	public static Date XMLGregorianCalendarToDate(XMLGregorianCalendar xmlGeorgianCalendarFecha) {
		return xmlGeorgianCalendarFecha.toGregorianCalendar().getTime();
	}
}
