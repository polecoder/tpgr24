package datatypes;

import java.util.Date;

public record DTDetallePatrocinio(
		String nombreInstitucion, 
		String codigo, 
		Date fechaAlta, 
		float aporte, 
		ENivelPatrocinio nivel, 
		int cuposGratuitos, 
		String tipoRegistro
) {}
