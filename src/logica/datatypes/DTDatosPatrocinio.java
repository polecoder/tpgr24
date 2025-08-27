package logica.datatypes;
//import java.time.LocalDate;
import logica.ENivelPatrocinio;

public record DTDatosPatrocinio(String nombreEdicion, String nombreInstitucion, ENivelPatrocinio nivelPatrocinio, float monto, String nombreTipoRegistro, int cupos, int codigoPatrocinio) {

}