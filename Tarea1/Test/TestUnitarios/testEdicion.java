package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;

import excepciones.NombreTipoRegistroRepetido;
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
             "Montevideo", "Uruguay", "Edicion", "Edicion");
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
    void patrocinios_validar_y_check_ramas_y_eliminar() throws Exception {
        Edicion ed = nuevaEdicion();

        TipoRegistro agotado = new TipoRegistro("Agotado", "Sin cupos", 0f, 10, 0 /* cuposDisponibles=0 */);
        ed.agregarTipoRegistro(agotado);
        assertThrows(Exception.class, () -> ed.validar("nick", "Agotado"));

        assertFalse(ed.check("COD-INEXISTENTE", "nick",agotado.getNombre()));

        Institucion inst = new Institucion("Fing", "fing@uni.edu", "Facultad Ing.");
        Patrocinio pat = new Patrocinio(
                "PAT-001",
                Utilidades.toDate(LocalDate.now()),
                10000f,
                ENivelPatrocinio.Bronce,
                0,
                inst,
                agotado);
        ed.agregarPatrocinio(pat);

        assertFalse(ed.check("PAT-001", "cualquierNick",agotado.getNombre()));

        ed.eliminarPatrocinio("PAT-001");
        assertFalse(ed.getPatrocinios().containsKey("PAT-001"));
    }

}
