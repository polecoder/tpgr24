package datatypes;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "EEstadoEdicion")
@XmlEnum
public enum EEstadoEdicion {
	@XmlEnumValue("Ingresada")
	Ingresada, 
	@XmlEnumValue("Aceptada")
	Aceptada, 
	@XmlEnumValue("Rechazada")
	Rechazada,
	@XmlEnumValue("Archivada")
	Archivada,
}
