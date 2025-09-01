package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import logica.Asistente;
import logica.Patrocinio;
import logica.Registro;
import logica.Utilidades;

class testRegistro {

	@Test
	void test() {
		Asistente asistente = new Asistente("nickA", "Ana", "Pérez", "ana@mail.com",
                Utilidades.toDate(LocalDate.of(2000, 5, 1)), null);

        Patrocinio patrocinio = new Patrocinio("PAT01", Utilidades.toDate(LocalDate.now()), 
                1000f, null, 5, null);

        Registro reg = new Registro(Utilidades.toDate(LocalDate.now()), 100f, asistente, patrocinio);
        
        assertEquals(asistente, reg.getAsistente());
        
        assertEquals(patrocinio, reg.getPatrocinio());
        
        assertNull(reg.mostrarDetalle());
        
        assertTrue(reg.validar("nickA"), "El nickname debe coincidir");
        assertFalse(reg.validar("otroNick"), "Si no coincide, debe ser false");
	}

}
