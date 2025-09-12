package logica.datatypes;

import java.util.Date;
import logica.ENivelPatrocinio;

public record DTDetallePatrocinio(String nombreInstitucion, String codigo, Date fechaAlta, float aporte, ENivelPatrocinio nivel, String nombreTipoRegistro, int cuposGratuitos) {}
