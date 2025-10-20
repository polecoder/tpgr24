package testunitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Asistente;
import logica.Institucion;
import logica.Utilidades;

public class TestInstitucion {

    private Institucion institucion;

    @BeforeEach
    void setUp() {
        institucion = new Institucion("InstitutoX", "Descripción de prueba", "http://web");
    }

    @Test
    void constructorYGettersFuncionan() {
        assertEquals("InstitutoX", institucion.getNombre());
        assertEquals("Descripción de prueba", institucion.getDescripcion());
        assertEquals("http://web", institucion.getSitioWeb());
        assertTrue(institucion.getAsistentes().isEmpty());
        assertTrue(institucion.getEdicionesPatrocinadas().isEmpty());
    }
    @Test
    void settersActualizanDatos() {
        institucion.setNombre("NuevoNombre");
        institucion.setDescripcion("Nueva descripción");
        institucion.setSitioWeb("http://nuevo");

        assertEquals("NuevoNombre", institucion.getNombre());
        assertEquals("Nueva descripción", institucion.getDescripcion());
        assertEquals("http://nuevo", institucion.getSitioWeb());
    }
    
    @Test
    void agregarYEliminarAsistenteFunciona() {
    	Date date = Utilidades.toDate(LocalDate.of(2025, 10, 2));
    	Asistente asistente = new Asistente("mNick", "Eventos.2025!", "mNombre", "correo@ejemplo.com", "mApellido", date, institucion);
        institucion.agregarAsistente(asistente);

        Map<String, Asistente> mapa = institucion.getAsistentes();
        assertTrue(mapa.containsKey("mNick"));
        assertEquals(asistente, mapa.get("mNick"));

        institucion.eliminarAsistente("mNick");
        assertFalse(mapa.containsKey("mNick"));
    }

    @Test
    void agregarEdicionYCheckAsistenteFuncionan() {
    	Date date = Utilidades.toDate(LocalDate.of(2025, 5, 1));
        institucion.agregarEdicion("Ed2025");
        Set<String> ediciones = institucion.getEdicionesPatrocinadas();
        assertTrue(ediciones.contains("Ed2025"));

        Asistente asistente = new Asistente("Nicky", "Eventos.2025!", "mNombre2", "correo2@ejemplo.com", "mApellido2", date, institucion);
        institucion.agregarAsistente(asistente);

        assertTrue(institucion.asistentePertence("Nicky"));
        assertFalse(institucion.asistentePertence("otroNick"));
    }
}

