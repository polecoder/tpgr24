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
        try {
			cev.altaCategoria(nombre);
		} catch (Exception e) {
			 throw new RuntimeException("Error al registrar la categoría: " + e.getMessage());
		}

        // Verificamos que la categoría esté en el listado
        assertTrue(cev.listarCategorias().containsKey(nombre), "La categoría debería haber sido añadida.");
    }

    @Test 
    void altaCategoria_duplicada() {
    	    // Primera vez: se da de alta correctamente
    	    try {
				cev.altaCategoria("Cuentos");
			} catch (Exception e) {
				throw new RuntimeException("Error al registrar la categoría: " + e.getMessage());
			}

    	    // Segunda vez: debe lanzar excepción
    	    Exception ex = assertThrows( Exception.class, () -> {
    	        cev.altaCategoria("Cuentos");
    	    });

    	    // Validamos el mensaje de la excepción
    	    assertEquals("La Categoría ya existe", ex.getMessage());
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

}