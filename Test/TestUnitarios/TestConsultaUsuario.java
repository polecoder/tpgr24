package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.IControladorUsuarios;
import logica.Utilidades;
import logica.datatypes.DTDetalleAsistente;
import logica.datatypes.DTDetalleUsuario;
import logica.datatypes.DTEventoEdicion;
import logica.datatypes.DTRegistro;

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
        iCU.altaAsistente(nickname, "Juan", "juan@mail.com", "Perez", Utilidades.toDate(LocalDate.now()), "Institucion1");

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
        iCU.altaOrganizador(nickname, "Laura", "laura@mail.com", "Organizadora de eventos", "http://laura.com");

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
