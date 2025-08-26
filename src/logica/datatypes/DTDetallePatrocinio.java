package logica.datatypes;

import java.time.LocalDate;
import logica.ENivelPatrocinio;

public record DTDetallePatrocinio(String codigo, LocalDate fecha, float monto, ENivelPatrocinio nivel, int cuposGratuitos) {

}
