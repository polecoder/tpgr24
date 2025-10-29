	package testunitarios;
	
	import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logica.Fabrica;
	import logica.IControladorEventos;
import logica.IControladorInstitucion;
import logica.Utilidades;
import datatypes.ENivelPatrocinio;

	 class TestAltaPatrocinio {
		 private static IControladorEventos controlador;
		 private static IControladorInstitucion controladorInsti;
		 
		 @BeforeAll
		 static void prepararDatos() {
		        Fabrica fabrica = Fabrica.getInstancia();
		        controlador = fabrica.getControladorEvento();
		        controladorInsti = fabrica.getControladorInstitucion();
		        assertDoesNotThrow(() -> {
		        	controlador.altaCategoria("cat1");
		        });
		        List<String> categorias = new ArrayList<>();
		        
		        
		        assertDoesNotThrow(() -> {
		            controlador.altaEvento("EventoX", "Descripción de prueba",
		                    Utilidades.toDate(LocalDate.of(2025, 8, 31)), null, categorias, null, null);
		            controlador.altaEvento("EventoX2", "Descripción de prueba2",
		                    Utilidades.toDate(LocalDate.of(2025, 9, 10)), null, categorias, null, null);
		            
		            if (!List.of(controlador.getNombresEdiciones("EventoX")).contains("Ed2026")) {
		                controlador.altaEdicionEvento("EventoX", "Ed2026", "ED26", Utilidades.toDate( LocalDate.of(2025, 9, 1) ), Utilidades.toDate( LocalDate.of(2025, 12, 31) ), Utilidades.toDate( LocalDate.of(2025, 8, 31) ), "Montevideo", "Uruguay", "organizador11", null, null);
		            }

		            if (!List.of(controlador.getNombresEdiciones("EventoX2")).contains("Ed2027")) {
		                controlador.altaEdicionEvento("EventoX2", "Ed2027", "ED27", Utilidades.toDate( LocalDate.of(2025, 9, 1) ), Utilidades.toDate( LocalDate.of(2025, 12, 31) ), Utilidades.toDate( LocalDate.of(2025, 8, 31)), "Montevideo", "Uruguay", "organizador12", null, null);
		            }

		            controlador.altaTipoRegistro("General", "Acceso completo", 1000f, 100, "EventoX", "Ed2026");
		            controladorInsti.altaInstitucion("InstitutoX", "descrip", "http://web");
		            controladorInsti.altaInstitucion("InstitutoA", "descrip", "http://web");
		            controladorInsti.altaInstitucion("InstitutoB", "descrip", "http://web");
		            
		        });
		 }
		     


		    @Test
		   
		    void altaExitosa() {
		    	
		    	assertDoesNotThrow(() -> {
		    		ENivelPatrocinio nivel=ENivelPatrocinio.valueOf("Oro");
		            controlador.altaPatrocinio("EventoX", "Ed2026", Utilidades.toDate(LocalDate.now( )), "InstitutoX", nivel , 500000f, 2, "COD123", "General");
		    	});
		    }
		    
		    
		   @Test
		   
		    void camposConEspacios() {
		        Exception excepcion = assertThrows(Exception.class, () -> {
		        	 ENivelPatrocinio nivel=ENivelPatrocinio.valueOf("Oro");
		        	controlador.altaPatrocinio("", "Ed2027", Utilidades.toDate( LocalDate.now() ), "InstitutoX", nivel, 500000f, 2, "COD123", "General");
		        });
		        assertEquals("Todos los campos deben estar completos", excepcion.getMessage());
		    }
		   
		 @Test
		    void codigoConCaracteresInvalidos() {
		        Exception excepcion = assertThrows(Exception.class, () -> {
		        	ENivelPatrocinio nivel=ENivelPatrocinio.valueOf("Oro");
		            controlador.altaPatrocinio("EventoX", "Ed2026", Utilidades.toDate( LocalDate.now() ), "InstitutoA", nivel, 500000f, 2, "COD@123", "General");
		        });
		        assertEquals("El código de patrocinio solo puede contener letras y números", excepcion.getMessage());
		    }

		    @Test
		    void institucionDuplicada() throws Exception {

		        Exception excepcion = assertThrows(Exception.class, () -> {
		        	ENivelPatrocinio nivel=ENivelPatrocinio.valueOf("Oro");
		            controlador.altaPatrocinio("EventoX", "Ed2026", Utilidades.toDate( LocalDate.now() ), "InstitutoX", nivel, 6000f, 1, "COD456", "General");
		        });
		        assertEquals("Ya existe un patrocinio de esa institución para la edición seleccionada", excepcion.getMessage());
		    }
		}
