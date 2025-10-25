package datatypes;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTUsuario")
@XmlAccessorType(XmlAccessType.FIELD)
public record DTUsuario(String nickname, String nombre, String correo) {

}
