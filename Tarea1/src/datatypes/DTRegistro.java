package datatypes;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTRegistro")
@XmlAccessorType(XmlAccessType.FIELD)
public record DTRegistro(
    String nombreEdicion,
    String nombreTipo
) {}
