package testunitarios;

import org.junit.jupiter.api.Test;

import datatypes.DTDetalleRegistro;
import datatypes.EEstadoEvento;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logica.Asistente;
import logica.Edicion;
import logica.Evento;
import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorUsuarios;
import logica.Patrocinio;
import logica.Registro;
import logica.TipoRegistro;
import logica.Utilidades;

class testRegistro {
	 private Fabrica fabrica = Fabrica.getInstancia();
	 private IControladorEventos controlador = fabrica.getControladorEvento();
	 private IControladorUsuarios controladorUsuario = fabrica.getControladorUsuarios();
	 private List<String> categorias = new ArrayList<>();
	 
@Test
	void test() {
		Asistente asistente = new Asistente("nickA", "Eventos.2025!", "Ana", "Pérez", "ana@mail.com",
                Utilidades.toDate(LocalDate.of(2000, 5, 1)), null);

        Patrocinio patrocinio = new Patrocinio("PAT01", Utilidades.toDate(LocalDate.now()), 
                1000f, null, 5, null, null);
        
        assertDoesNotThrow(() -> { 
			 controladorUsuario.altaOrganizador("organizadorL", "Eventos.2025!", "Lucas", "Orglucas.martinez@gmail.com", "Encargado de eventos", "https://lucasorganiza.com", null);
		 }); 
		
		 categorias.add("Categoria 1");
		 controlador.altaEvento("EventoPrueba2","prueba",Utilidades.toDate(LocalDate.of(2023, 5, 10)),"EP1",categorias, null,EEstadoEvento.EN_CURSO, 0);
		 
		Edicion ed = new Edicion( "EdicionPruebita2","EdP1", Utilidades.toDate(LocalDate.of(2025, 9, 10)),  Utilidades.toDate(LocalDate.of(2025, 12, 9)), Utilidades.toDate(LocalDate.of(2025, 8, 29)), "ciudad", "pais", "organizadorL","EventoPrueba2", null, null);
		 
		TipoRegistro treg= new TipoRegistro("gral", "prueba", 100f, 3, 3);
		
		ed.agregarTipoRegistro(treg);
		
        Registro reg = new Registro(Utilidades.toDate(LocalDate.now()), 100f, asistente, patrocinio,ed,treg.getNombre());
        
        assertEquals(asistente, reg.getAsistente());
        
        assertEquals(patrocinio, reg.getPatrocinio());
        
        DTDetalleRegistro dtr= reg.mostrarDetalle();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String fecha=formatter.format(dtr.getFecha());
        String fecha2=formatter.format(new Date());
        assertEquals(fecha2, fecha);
        assertEquals("EdicionPruebita2", dtr.getNombreEdicion());
        assertEquals("nickA", dtr.getNicknameAsistente());
        assertEquals(100, dtr.getCosto());
        assertEquals("PAT01", dtr.getCodigoPatrocinio());
	}
}
