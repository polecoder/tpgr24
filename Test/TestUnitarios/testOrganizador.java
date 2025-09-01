package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import logica.Organizador;
import logica.Edicion;
import logica.datatypes.DTEdicionEvento;

class testOrganizador {

	@Test
	void testgettersysetters() {
		Organizador org = new Organizador("nick", "Juan", "juan@mail", "Organizador Principal", "www.com");
		assertEquals("Organizador Principal", org.getDescripcion());
		assertEquals("www.com", org.getLink());
		assertNotNull(org.getEdiciones());
		assertTrue(org.getEdiciones().isEmpty());
		
		org.setDescripcion("Nueva descrip");
		org.setLink("www.nuevo");
		
		assertEquals("Nueva descrip", org.getDescripcion());
		assertEquals("www.nuevo", org.getLink());
	}
	
	@Test 
	void testListarEdiciones1(){
		Organizador org = new Organizador("nick2", "Ana", "ana@mail.com",
                "Organizadora", "www.ana.com");
		
		Edicion ed = new Edicion("Edicion2025", "ED25",java.sql.Date.valueOf("2025-01-01"),java.sql.Date.valueOf("2025-12-31"),java.sql.Date.valueOf("2024-12-01"),"Montevideo", "Uruguay", "Pedro", "evento");
        org.getEdiciones().add(ed);
        
        List<DTEdicionEvento> lista = org.listarEdiciones();
        
        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals("Edicion2025", lista.get(0).getNombre());
	}
	
}
