package testunitarios;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import logica.Asistente;
import logica.Patrocinio;
import logica.Registro;
import logica.Utilidades;

class testRegistro {

	@Test
	void test() {
		Asistente asistente = new Asistente("nickA", "Eventos.2025!", "Ana", "Pérez", "ana@mail.com",
                Utilidades.toDate(LocalDate.of(2000, 5, 1)), null);

        Patrocinio patrocinio = new Patrocinio("PAT01", Utilidades.toDate(LocalDate.now()), 
                1000f, null, 5, null, null);

        Registro reg = new Registro(Utilidades.toDate(LocalDate.now()), 100f, asistente, patrocinio);
        
        assertEquals(asistente, reg.getAsistente());
        
        assertEquals(patrocinio, reg.getPatrocinio());
	}
}
