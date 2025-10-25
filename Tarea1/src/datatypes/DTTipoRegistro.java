package datatypes;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "DTTipoRegistro")
@XmlAccessorType(XmlAccessType.FIELD)
public record DTTipoRegistro(
    String nombre, 
    String descripcion, 
    float costo, 
    int cuposMax, 
    int cuposDisponibles
    ) {
}
