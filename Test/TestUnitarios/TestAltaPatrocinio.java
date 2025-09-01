	package TestUnitarios;
	
	import static org.junit.jupiter.api.Assertions.*;
	
	import java.time.LocalDate;
	import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import logica.ENivelPatrocinio;
import logica.Fabrica;
	import logica.IControladorEventos;
import logica.IControladorInstitucion;
import logica.Utilidades;

	 class TestAltaPatrocinio {
		 static IControladorEventos controlador;
		 static IControladorInstitucion controladorInsti;
		 
		 @BeforeAll
		 static void prepararDatos() {
		        Fabrica fabrica = Fabrica.getInstancia();
		        controlador = fabrica.getControladorEvento();
		        controladorInsti = fabrica.getControladorInstitucion();
		       

		        try {
		            controlador.altaEvento("EventoX", "Descripción de prueba", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "EVX", List.of("Tecnología"));
		            controlador.altaEvento("EventoX2", "Descripción de prueba2", Utilidades.toDate(LocalDate.of(2025, 8, 31) ), "EVX2", List.of("Tecnología"));

		            if (!List.of(controlador.getNombresEdiciones("EventoX")).contains("Ed2026")) {
		                controlador.altaEdicionEvento("EventoX", "Ed2026", "ED26",Utilidades.toDate( LocalDate.of(2025, 9, 1) ),Utilidades.toDate( LocalDate.of(2025, 12, 31) ),Utilidades.toDate( LocalDate.of(2025, 8, 31) ), "Montevideo", "Uruguay","organizador11");
		            }

		            if (!List.of(controlador.getNombresEdiciones("EventoX2")).contains("Ed2027")) {
		                controlador.altaEdicionEvento("EventoX2", "Ed2027", "ED27", Utilidades.toDate( LocalDate.of(2025, 9, 1) ),Utilidades.toDate( LocalDate.of(2025, 12, 31) ),Utilidades.toDate( LocalDate.of(2025, 8, 31)), "Montevideo", "Uruguay", "organizador12");
		            }

		            controlador.altaTipoRegistro("General", "Acceso completo", 1000f, 100, "EventoX", "Ed2026");
		            controladorInsti.AltaInstitucion("InstitutoX", "descrip", "http://web");
		            controladorInsti.AltaInstitucion("InstitutoA", "descrip", "http://web");
		            controladorInsti.AltaInstitucion("InstitutoB", "descrip", "http://web");
		            
		            

		        } catch (Exception ex) {
		            fail("Error al preparar datos de prueba: " + ex.getMessage());
		        }
		    }


		    @Test
		   
		    void altaExitosa() {
		        try {
		        	ENivelPatrocinio nivel=ENivelPatrocinio.valueOf("Oro");
		            controlador.altaPatrocinio("EventoX","Ed2026",Utilidades.toDate(LocalDate.now( )),"InstitutoX", nivel , 500000f, 2, "COD123");
		        } catch (Exception ex) {
		            fail("No se esperaba excepción en alta exitosa: " + ex.getMessage());
		        }
		    }
		    
		    
		   @Test
		   
		    void camposConEspacios() {
		        Exception ex = assertThrows(Exception.class, () -> {
		        	 ENivelPatrocinio nivel=ENivelPatrocinio.valueOf("Oro");
		        	controlador.altaPatrocinio("","Ed2027",Utilidades.toDate( LocalDate.now() ),"InstitutoX", nivel, 500000f, 2, "COD123");
		        });
		        assertEquals("Todos los campos deben estar completos", ex.getMessage());
		    }
		   
		 @Test
		    void codigoConCaracteresInvalidos() {
		        Exception ex = assertThrows(Exception.class, () -> {
		        	ENivelPatrocinio nivel=ENivelPatrocinio.valueOf("Oro");
		            controlador.altaPatrocinio("EventoX", "Ed2026",Utilidades.toDate( LocalDate.now() ), "InstitutoA", nivel, 500000f, 2, "COD@123");
		        });
		        assertEquals("El código de patrocinio solo puede contener letras y números", ex.getMessage());
		    }

		    @Test
		    void institucionDuplicada() throws Exception {

		        Exception ex = assertThrows(Exception.class, () -> {
		        	ENivelPatrocinio nivel=ENivelPatrocinio.valueOf("Oro");
		            controlador.altaPatrocinio("EventoX", "Ed2026", Utilidades.toDate( LocalDate.now() ),"InstitutoX", nivel, 6000f, 1, "COD456");
		        });
		        assertEquals("Ya existe un patrocinio de esa institución para la edición seleccionada", ex.getMessage());
		    }
		}
