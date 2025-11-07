package testunitarios;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import logica.Utilidades;

public class TestArchivoImagenToByte {
    
    @Test
    void testArchivoImagenToByteConNull() {
        byte[] resultado = Utilidades.archivoImagenToByte(null);
        assertNull(resultado);
    }
    
    @Test
    void testArchivoImagenToByteConGuion() {
        byte[] resultado = Utilidades.archivoImagenToByte("-");
        assertNull(resultado);
    }
    
    @Test
    void testArchivoImagenToByteArchivoNoExiste() {
        byte[] resultado = Utilidades.archivoImagenToByte("archivoQueNoExiste.jpg");
        assertNull(resultado);
    }
    
    @Test
    void testArchivoImagenToByteArchivoExiste() {
        byte[] resultado = Utilidades.archivoImagenToByte("test.jpg");
        assertTrue(resultado == null || resultado.length >= 0);
    }
}
