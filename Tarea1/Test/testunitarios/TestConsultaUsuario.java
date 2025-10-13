package testunitarios;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.IControladorUsuarios;
import logica.Utilidades;
import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleUsuario;
import datatypes.DTEventoEdicion;
import datatypes.DTRegistro;

import logica.Fabrica;

class TestConsultaUsuario {

    private IControladorUsuarios iCU;

    @BeforeEach
    void setUp() {
    	Fabrica f = Fabrica.getInstancia();
        iCU = f.getControladorUsuarios();
    }

    @Test
    void testConsultaAsistente() throws Exception {
        // Arrange
        String nickname = "asistente1";
        iCU.altaAsistente(nickname, "Eventos.2025!", "Juan", "juan@mail.com", "Perez", Utilidades.toDate(LocalDate.now()), "Institucion1", null);

        // Act
        DTDetalleAsistente detalle = (DTDetalleAsistente) iCU.mostrarDetalleUsuario(nickname);

        // Assert
        assertNotNull(detalle);
        assertEquals("Juan", detalle.getNombre());
        assertEquals("Perez", detalle.getApellido());
        assertEquals("juan@mail.com", detalle.getCorreo());

        // Además el admin debería poder ver registros asociados al asistente
        List<DTRegistro> registros = iCU.listarRegistrosUsuario(nickname);
        assertNotNull(registros);
        // Al inicio debería estar vacío
        assertTrue(registros.isEmpty());
    }

    @Test
    void testConsultaOrganizador() throws Exception {
        // Arrange
        String nickname = "organizador1";
        iCU.altaOrganizador(nickname, "Eventos.2025!", "Laura", "laura@mail.com", "Organizadora de eventos", "http://laura.com", null);

        // Act
        DTDetalleUsuario detalle = iCU.mostrarDetalleUsuario(nickname);

        // Assert
        assertNotNull(detalle);
        assertEquals("Laura", detalle.getNombre());
        assertEquals("laura@mail.com", detalle.getCorreo());

        // Además el admin debería poder ver ediciones asociadas al organizador
        List<DTEventoEdicion> eventosEdiciones = iCU.getEventosEdicionesDeOrganizador(nickname);
        assertNotNull(eventosEdiciones);
        // Al inicio debería estar vacío
        assertTrue(eventosEdiciones.isEmpty());
    }
}
