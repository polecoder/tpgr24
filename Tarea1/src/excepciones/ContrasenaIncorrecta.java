package excepciones;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "ContrasenaIncorrecta")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContrasenaIncorrecta extends Exception {
    private static final long serialVersionUID = 1L;
    
    public ContrasenaIncorrecta() {
    }
    
    public ContrasenaIncorrecta(String mensaje) {
        super(mensaje);
    }
}