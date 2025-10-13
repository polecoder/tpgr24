package testunitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import logica.Fabrica;
import logica.IControladorInstitucion;

class TestAltaInstitucion {
    private IControladorInstitucion controlador = Fabrica.getInstancia().getControladorInstitucion();
    
    public IControladorInstitucion getControlador() {
    	return Fabrica.getInstancia().getControladorInstitucion();
    };

    @Test
    void altaInstitucionValida() {
        assertDoesNotThrow(() -> {
            controlador.altaInstitucion("Instituto de Robótica", "Centro de investigación avanzada", "https://robotica.edu.uy");
        });

        assertTrue(controlador.nombresInstituciones().contains("Instituto de Robótica"));
    }

    @Test
    void altaInstitucionConCamposVacios() {
        Exception excepcion = assertThrows(Exception.class, () -> {
            controlador.altaInstitucion("", "", "");
        });
        assertEquals("Todos los campos son obligatorios", excepcion.getMessage());
    }

    @Test
    void altaInstitucionConNombreInvalido() {
        Exception excepcion = assertThrows(Exception.class, () -> {
            controlador.altaInstitucion("Instituto@123", "Centro de investigación", "https://robotica.edu.uy");
        });
        assertEquals("El nombre solo puede contener letras, espacios y paréntesis", excepcion.getMessage());
    }

    @Test
    void altaInstitucionConSitioWebInvalido() {
        Exception excepcion = assertThrows(Exception.class, () -> {
            controlador.altaInstitucion("Instituto de IA", "Centro de IA", "robotica.edu.uy");
        });
        assertEquals("El sitio web debe comenzar con http:// o https://", excepcion.getMessage());
    }

    @Test
    void altaInstitucionConNombreRepetido() throws Exception {
        // Alta inicial
        controlador.altaInstitucion("Instituto de Datos", "Centro de análisis", "https://datos.edu.uy");

        // Intento duplicado
        Exception exception = assertThrows(Exception.class, () -> {
            controlador.altaInstitucion("Instituto de Datos", "Otro centro", "https://duplicado.edu.uy");
        });
        assertEquals("Ya existe una institución con ese nombre", exception.getMessage());
    }
}
