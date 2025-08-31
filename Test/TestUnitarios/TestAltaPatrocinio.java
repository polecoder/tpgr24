	package TestUnitarios;
	
	import static org.junit.jupiter.api.Assertions.*;
	
	import java.time.LocalDate;
	import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
	
	import logica.Fabrica;
	import logica.IControladorEventos;
import logica.IControladorInstitucion;

	 class TestAltaPatrocinio {
		 static IControladorEventos controlador;
		 static IControladorInstitucion controladorInsti;
		 
		 @BeforeAll
		 static void prepararDatos() {
		        Fabrica fabrica = Fabrica.getInstancia();
		        controlador = fabrica.getControladorEvento();
		        controladorInsti = fabrica.getControladorInstitucion();

		        try {
		            controlador.altaEvento("EventoX", "Descripción de prueba", LocalDate.of(2025, 8, 31), "EVX", List.of("Tecnología"));
		            controlador.altaEvento("EventoX2", "Descripción de prueba2", LocalDate.of(2025, 8, 31), "EVX2", List.of("Tecnología"));

		            if (!List.of(controlador.getNombresEdiciones("EventoX")).contains("Ed2026")) {
		                controlador.AltaEdicionEvento("EventoX", "Ed2026", "ED26", LocalDate.of(2025, 9, 1), LocalDate.of(2025, 12, 31), LocalDate.of(2025, 8, 31), "Montevideo", "Uruguay");
		            }

		            if (!List.of(controlador.getNombresEdiciones("EventoX2")).contains("Ed2027")) {
		                controlador.AltaEdicionEvento("EventoX2", "Ed2027", "ED27", LocalDate.of(2025, 9, 1), LocalDate.of(2025, 12, 31), LocalDate.of(2025, 8, 31), "Montevideo", "Uruguay");
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
		            controlador.AltaPatrocinio("EventoX","Ed2026","General","InstitutoX", "ORO", 500000, 2, "COD123");
		        } catch (Exception ex) {
		            fail("No se esperaba excepción en alta exitosa: " + ex.getMessage());
		        }
		    }
		    
		    
		   @Test
		   
		    void camposConEspacios() {
		        Exception ex = assertThrows(Exception.class, () -> {
		        	controlador.AltaPatrocinio("","Ed2027","General","InstitutoX", "ORO", 500000, 2, "COD123");
		        });
		        assertEquals("Todos los campos deben estar completos", ex.getMessage());
		    }
		   
		 @Test
		    void codigoConCaracteresInvalidos() {
		        Exception ex = assertThrows(Exception.class, () -> {
		            controlador.AltaPatrocinio("EventoX", "Ed2026", "General", "InstitutoA", "ORO", 5000000, 2, "COD@123");
		        });
		        assertEquals("El código de patrocinio solo puede contener letras y números", ex.getMessage());
		    }

		 @Test
		    void nivelPatrocinioMalEscrito() {
		        Exception ex = assertThrows(Exception.class, () -> {
		            controlador.AltaPatrocinio("EventoX", "Ed2026", "General", "InstitutoB", "oro", 500000, 2, "COD123");
		        });
		        assertEquals("Nivel de patrocinio inválido", ex.getMessage());
		    }
		 
		    @Test
		    void costoSuperaLimite() {
		        Exception ex = assertThrows(Exception.class, () -> {
		            controlador.AltaPatrocinio("EventoX2", "Ed2027", "General", "InstitutoX", "ORO", 5000, 3, "COD123");
		        });
		        assertEquals("El costo de los registros gratuitos supera el 20% del aporte económico", ex.getMessage());
		    }

		    @Test
		    void institucionDuplicada() throws Exception {

		        Exception ex = assertThrows(Exception.class, () -> {
		            controlador.AltaPatrocinio("EventoX", "Ed2026", "General", "InstitutoX", "ORO", 6000, 1, "COD456");
		        });
		        assertEquals("Ya existe un patrocinio de esa institución para la edición seleccionada", ex.getMessage());
		    }
		}
