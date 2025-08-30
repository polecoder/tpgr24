package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logica.ControladorEventos;

class TestAltaCategoria {

    private final ControladorEventos cev = ControladorEventos.getInstance();

    @Test
    void altaCategoria_correcta() {
        String nombre = "Ciencias de la Computación";

        // Alta de categoría
        cev.altaCategoria(nombre);

        // Verificamos que la categoría esté en el listado
        assertTrue(cev.listarCategorias().containsKey(nombre), "La categoría debería haber sido añadida.");
    }

    @Test
    void altaCategoria_duplicada() {
        String nombre = "Robótica";

        // Primera alta
        cev.altaCategoria(nombre);

        // Intentamos dar de alta la misma categoría
        assertThrows(IllegalArgumentException.class, () -> {
            cev.altaCategoria(nombre);
        }, "No se puede dar de alta una categoría duplicada.");
    }

    @Test
    void altaCategoria_nombreInvalido() {
        // Intentamos dar de alta una categoría con nombre vacío o null
        assertThrows(IllegalArgumentException.class, () -> {
            cev.altaCategoria(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            cev.altaCategoria("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            cev.altaCategoria("   ");
        });
    }

    @Test
    void altaCategoria_caseInsensitive() {
        // Alta con mayúsculas y minúsculas mezcladas
        String nombre = "Inteligencia artificial";
        cev.altaCategoria(nombre);

        // El sistema no debería permitir agregarla dos veces con distinto caso
        assertThrows(IllegalArgumentException.class, () -> {
            cev.altaCategoria("inteligencia artificial");
        }, "La categoría debe ser case-insensitive.");
    }
}