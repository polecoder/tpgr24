package testunitarios;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import logica.ControladorEventos;

class TestAltaCategoria {

    private final ControladorEventos cev = ControladorEventos.getInstance();

    @Test
    void altaCategoriacorrecta() {
        String nombre = "Ciencias de la Computación";

        // Alta de categoría
    	assertDoesNotThrow(() -> {
			cev.altaCategoria(nombre);
		});

        // Verificamos que la categoría esté en el listado
        assertTrue(cev.listarCategorias().containsKey(nombre), "La categoría debería haber sido añadida.");
    }

    @Test 
    void altaCategoriaduplicada() {
    	    // Primera vez: se da de alta correctamente
    	assertDoesNotThrow(() -> {
    		cev.altaCategoria("Cuentos");
		});
    	    

    	    // Segunda vez: debe lanzar excepción
    	    Exception excepcion = assertThrows( Exception.class, () -> {
    	        cev.altaCategoria("Cuentos");
    	    });

    	    // Validamos el mensaje de la excepción
    	    assertEquals("La Categoría ya existe", excepcion.getMessage());
    	}


    @Test
    void altaCategorianombreInvalido() {
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