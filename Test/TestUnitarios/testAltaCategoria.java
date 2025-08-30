package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logica.ControladorEventos;

class TestAltaCategoria {

    private final ControladorEventos ce = ControladorEventos.getInstance();

    @Test
    void altaCategoria_correcta() {
        String nombre = "Ciencias de la Computación";

        // Alta de categoría
        ce.altaCategoria(nombre);

        // Verificamos que la categoría esté en el listado
        assertTrue(ce.listarCategorias().contains(nombre), "La categoría debería haber sido añadida.");
    }

    @Test
    void altaCategoria_duplicada() {
        String nombre = "Robótica";

        // Primera alta
        ce.altaCategoria(nombre);

        // Intentamos dar de alta la misma categoría
        assertThrows(IllegalArgumentException.class, () -> {
            ce.altaCategoria(nombre);
        }, "No se puede dar de alta una categoría duplicada.");
    }

    @Test
    void altaCategoria_nombreInvalido() {
        // Intentamos dar de alta una categoría con nombre vacío o null
        assertThrows(IllegalArgumentException.class, () -> {
            ce.altaCategoria(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ce.altaCategoria("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            ce.altaCategoria("   ");
        });
    }

    @Test
    void altaCategoria_caseInsensitive() {
        // Alta con mayúsculas y minúsculas mezcladas
        String nombre = "Inteligencia artificial";
        ce.altaCategoria(nombre);

        // El sistema no debería permitir agregarla dos veces con distinto caso
        assertThrows(IllegalArgumentException.class, () -> {
            ce.altaCategoria("inteligencia artificial");
        }, "La categoría debe ser case-insensitive.");
    }
}