package testunitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Institucion;
import logica.ManejadorInstituciones;
import datatypes.DTPatrocinio;

public class TestManejadorInstituciones {

    private ManejadorInstituciones manejador;

    @BeforeEach
    void setUp() {
        manejador = ManejadorInstituciones.getInstance();
        manejador.setInstituciones(new HashMap<>()); // limpiar estado previo
    }

    @Test
    void testTodasLasFunciones() {
        // altaInstitucion
        Institucion inst1 = manejador.altaInstitucion("Inst1", "Descripción 1", "http://web1");
        assertNotNull(inst1);
        assertEquals("Inst1", inst1.getNombre());

        // agregarInstitucion
        Institucion inst2 = new Institucion("Inst2", "Descripción 2", "http://web2");
        manejador.agregarInstitucion(inst2);
        assertEquals(inst2, manejador.getInstitucion("Inst2"));

        // altaInstitucion (duplicado con mayúscula)
        manejador.altaInstitucion("Inst3", "Descripción 3", "http://web3");
        Institucion inst3 = manejador.getInstitucion("Inst3");
        assertNotNull(inst3);
        assertEquals("Inst3", inst3.getNombre());

        // getInstituciones
        Map<String, Institucion> mapa = manejador.getInstituciones();
        assertEquals(3, mapa.size());
        assertTrue(mapa.containsKey("Inst1"));
        assertTrue(mapa.containsKey("Inst2"));
        assertTrue(mapa.containsKey("Inst3"));

        // existeNombre
        assertTrue(manejador.existeNombre("Inst2"));
        assertFalse(manejador.existeNombre("NoExiste"));

        // eliminarPatrocinio
        manejador.eliminarPatrocinio("Inst2");
        assertFalse(manejador.existeNombre("Inst2"));
        assertNull(manejador.getInstitucion("Inst2"));

        // getPatrocinio (actualmente devuelve null)
        DTPatrocinio dt = manejador.getPatrocinio("COD123");
        assertNull(dt);
    }
}
