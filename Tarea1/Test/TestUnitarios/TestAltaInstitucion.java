package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logica.Fabrica;
import logica.IControladorInstitucion;

class TestAltaInstitucion {
    IControladorInstitucion controlador = Fabrica.getInstancia().getControladorInstitucion();

    @Test
    void altaInstitucionValida() {
        assertDoesNotThrow(() -> {
            controlador.AltaInstitucion("Instituto de Robótica", "Centro de investigación avanzada", "https://robotica.edu.uy");
        });

        assertTrue(controlador.nombresInstituciones().contains("Instituto de Robótica"));
    }

    @Test
    void altaInstitucionConCamposVacios() {
        Exception ex = assertThrows(Exception.class, () -> {
            controlador.AltaInstitucion("", "", "");
        });
        assertEquals("Todos los campos son obligatorios", ex.getMessage());
    }

    @Test
    void altaInstitucionConNombreInvalido() {
        Exception ex = assertThrows(Exception.class, () -> {
            controlador.AltaInstitucion("Instituto@123", "Centro de investigación", "https://robotica.edu.uy");
        });
        assertEquals("El nombre solo puede contener letras, espacios y paréntesis", ex.getMessage());
    }

    @Test
    void altaInstitucionConSitioWebInvalido() {
        Exception ex = assertThrows(Exception.class, () -> {
            controlador.AltaInstitucion("Instituto de IA", "Centro de IA", "robotica.edu.uy");
        });
        assertEquals("El sitio web debe comenzar con http:// o https://", ex.getMessage());
    }

    @Test
    void altaInstitucionConNombreRepetido() throws Exception {
        // Alta inicial
        controlador.AltaInstitucion("Instituto de Datos", "Centro de análisis", "https://datos.edu.uy");

        // Intento duplicado
        Exception ex = assertThrows(Exception.class, () -> {
            controlador.AltaInstitucion("Instituto de Datos", "Otro centro", "https://duplicado.edu.uy");
        });
        assertEquals("Ya existe una institución con ese nombre", ex.getMessage());
    }
}
