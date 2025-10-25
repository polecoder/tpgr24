package datatypes;

import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "DTDetallePatrocinio")
@XmlAccessorType(XmlAccessType.FIELD)
public record DTDetallePatrocinio(
		String nombreInstitucion, 
		String codigo, 
		Date fechaAlta, 
		float aporte, 
		ENivelPatrocinio nivel, 
		int cuposGratuitos, 
		String tipoRegistro
) {}
