package datatypes;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTRegistro")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTRegistro {

    String nombreEdicion;
    String nombreTipo;

    public DTRegistro() { 
    }

    public DTRegistro(String nombreEdicion, String nombreTipo) {
        this.nombreEdicion = nombreEdicion;
        this.nombreTipo = nombreTipo;
    }

    public String getNombreEdicion() { return nombreEdicion; }
    public void setNombreEdicion(String nombreEdicion) { this.nombreEdicion = nombreEdicion; }

    public String getNombreTipo() { return nombreTipo; }
    public void setNombreTipo(String nombreTipo) { this.nombreTipo = nombreTipo; }
}
