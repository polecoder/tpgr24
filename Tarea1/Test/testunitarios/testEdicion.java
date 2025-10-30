package testunitarios;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;

import excepciones.NombreTipoRegistroRepetido;
import excepciones.PatrocinioInvalido;
import excepciones.TipoRegistroNoExiste;
import logica.Edicion;
import logica.Institucion;
import logica.Patrocinio;
import logica.TipoRegistro;
import logica.Utilidades;
import datatypes.DTTipoRegistro;
import datatypes.ENivelPatrocinio;

class testEdicion {
	

	private Edicion nuevaEdicion() {
	 return new Edicion(
             "ED2025", "ED25",
             Utilidades.toDate(LocalDate.of(2025, 9, 1)),
             Utilidades.toDate(LocalDate.of(2025, 9, 3)),
             Utilidades.toDate(LocalDate.now()),
             "Montevideo", "Uruguay", "Edicion", "Edicion", null, null);
	} 
	@Test
	void test1() throws Exception{
		
		Edicion ed = nuevaEdicion();
		
		ed.agregarTipoRegistro("General", "Acceso general", 1000f, 100);
        ed.agregarTipoRegistro("VIP",     "Acceso VIP",     3000f,  50);
        
        Map<String, DTTipoRegistro> listado = ed.listarTipoRegistro();
        assertEquals(2, listado.size());
        assertTrue(listado.containsKey("General"));
        assertTrue(listado.containsKey("VIP"));
        assertEquals("Acceso general", listado.get("General").descripcion());
        assertEquals(3000f, listado.get("VIP").costo(), 0.001f);
        
        DTTipoRegistro dtGen = ed.getDTTipoRegistro("General");
        assertEquals("General", dtGen.nombre());
        
        assertThrows(TipoRegistroNoExiste.class, () -> ed.getDTTipoRegistro("NO-EXISTE"));
		
        assertThrows(TipoRegistroNoExiste.class, () -> ed.getTipoRegistro("X"));
        
        assertThrows(NombreTipoRegistroRepetido.class,
                () -> ed.agregarTipoRegistro("General", "otra", 1f, 1));
        
        TipoRegistro estudiante = new TipoRegistro("Estudiante", "Desc", 500, 10, 10);
        ed.agregarTipoRegistro(estudiante);
        assertTrue(ed.getTiposRegistro().containsKey("Estudiante"));
	}
	
	@Test
    void patrociniosvalidarycheckramasyeliminar() throws Exception {
        Edicion edicion = nuevaEdicion();

        TipoRegistro agotado = new TipoRegistro("Agotado", "Sin cupos", 0f, 10, 0 /* cuposDisponibles=0 */);
        edicion.agregarTipoRegistro(agotado);
        assertThrows(Exception.class, () -> edicion.validar("nick", "Agotado"));

        assertThrows(PatrocinioInvalido.class, () -> edicion.patrocinioValido("COD-INEXISTENTE", "nick", agotado.getNombre()));

        Institucion inst = new Institucion("Fing", "fing@uni.edu", "Facultad Ing.");
        Patrocinio pat = new Patrocinio(
                "PAT-001",
                Utilidades.toDate(LocalDate.now()),
                10000f,
                ENivelPatrocinio.Bronce,
                0,
                inst,
                agotado);
        edicion.agregarPatrocinio(pat);

        assertThrows(PatrocinioInvalido.class, () -> edicion.patrocinioValido("PAT-001", "cualquierNick", agotado.getNombre()));

        edicion.eliminarPatrocinio("PAT-001");
        assertFalse(edicion.getPatrocinios().containsKey("PAT-001"));
    }

}
