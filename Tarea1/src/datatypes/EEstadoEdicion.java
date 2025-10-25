package datatypes;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "EEstadoEdicion")
@XmlAccessorType(XmlAccessType.FIELD)
public enum EEstadoEdicion {
	Ingresada,
	Aceptada,
	Rechazada
}
