package testunitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.IControladorUsuarios;
import logica.Utilidades;
import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleUsuario;
import datatypes.DTEventoEdicion;
import datatypes.DTRegistro;
import datatypes.EEstadoEdicion;
import logica.Fabrica;

class TestConsultaUsuario {

    private IControladorUsuarios iCU;

    @BeforeEach
    void setUp() {
    	Fabrica.resetInstancia();
    	Fabrica fabrica = Fabrica.getInstancia();
        iCU = fabrica.getControladorUsuarios();
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
    
    @Test
    void testOperacionGetEdicionesDeOrganizadorPorEstado() {
    	Fabrica fabrica = Fabrica.getInstancia();
    	assertDoesNotThrow(() -> {
    		iCU.altaOrganizador("lauraorganiza", "Eventos.2025!", "Laura", "laura@mail.com", "Organizadora de eventos", "http://laura.com", null);
    		fabrica.getControladorEvento().altaCategoria("Categoria1");
    		List<String> categorias = new ArrayList<>();
    		categorias.add("Categoria1");
    		fabrica.getControladorEvento().altaEvento("Nombre1", "Descripcion1", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias, null);
    		fabrica.getControladorEvento().altaEdicionEvento("Nombre1", "Edicion1", "SIG1", Utilidades.toDate(LocalDate.of(2025, 8, 31)), Utilidades.toDate(LocalDate.of(2025, 9, 30)), Utilidades.toDate(LocalDate.of(2025, 8, 31)), "Montevideo", "Uruguay", "lauraorganiza", null);
    	});
    	
    	assertTrue(fabrica.getControladorEvento().existeEvento("Nombre1"));
    	assertFalse(fabrica.getControladorEvento().existeEvento("Nombre2"));
    	assertTrue(fabrica.getControladorEvento().existeCategoria("Categoria1"));
    	assertFalse(fabrica.getControladorEvento().existeCategoria("Categoria2"));
    	
    	List<String> resultado = fabrica.getControladorEvento().getEdicionesDeOrganizadorPorEstado("lauraorganiza", EEstadoEdicion.Ingresada);
    	assertEquals(resultado.get(0), "Edicion1");
    }
    
    @Test
    void testOperacionListarDetalleUsuarios() {
    	Fabrica fabrica = Fabrica.getInstancia();
    	assertDoesNotThrow(() -> {
    		fabrica.getControladorInstitucion().altaInstitucion("Institucion", "Descripcion1", "https://a.com");
    		iCU.altaOrganizador("lauraorganiza", "Eventos.2025!", "Laura", "laura@mail.com", "Organizadora de eventos", "http://laura.com", null);
    		iCU.altaAsistente("asistente", "Eventos.2025!", "Juan", "juan@mail.com", "Perez", Utilidades.toDate(LocalDate.now()), "Institucion", null);
    		fabrica.getControladorEvento().altaCategoria("Categoria1");
    		List<String> categorias = new ArrayList<>();
    		categorias.add("Categoria1");
    		fabrica.getControladorEvento().altaEvento("Nombre1", "Descripcion1", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias, null);
    		fabrica.getControladorEvento().altaEdicionEvento("Nombre1", "Edicion1", "SIG1", Utilidades.toDate(LocalDate.of(2025, 8, 31)), Utilidades.toDate(LocalDate.of(2025, 9, 30)), Utilidades.toDate(LocalDate.of(2025, 8, 31)), "Montevideo", "Uruguay", "lauraorganiza", null);
    	});
    	
    	List<DTDetalleUsuario> resultado = fabrica.getControladorUsuarios().listarDetalleUsuarios();
    	// TODO: Comparar de alguna forma el resultado
    }
}
