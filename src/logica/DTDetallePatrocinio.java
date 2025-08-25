package logica;

import java.time.LocalDate;

public record DTDetallePatrocinio(String codigo, LocalDate fecha, float monto, ENivelPatrocinio nivel, int cuposGratuitos) {

}
