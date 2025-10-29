package testunitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import excepciones.DatosTipoRegistroInvalidos;
import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.Fabrica;
import logica.IControladorEventos;
import logica.Utilidades;
import datatypes.DTTipoRegistro;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAltaTipoRegistro {
	private static IControladorEventos controladorEventos;
	
	// Declariacion de variable para usar en más de un Test
	private static String pais, ciudad, sigla, descripcion;
	private static Date fecha;
	private static float costo;
	private static int cuposMax;
	private static List<String> categorias;
	
	private static String nombreEventoTest1, nombreEdicionTest1, nombreCategoriaTest1, nombreTipoRegistroTest1;			
	private static String nombreEdicionTest3;
	private static String nombreEventoTest4, nombreEdicionTest4;
	private static String nombreTipoRegistroTest6;
	private static String nombreTipoRegistroTest7;
	private static String nombreTipoRegistroTest8;
	private static String nombreTipoRegistroTest9;
	// Declariacion de variable para usar en más de un Test
	
	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstancia();
		controladorEventos = fabrica.getControladorEvento();
		
		// Variables generales
		pais = "Uruguay";
		ciudad = "Montevideo";
		sigla = "FING";
		descripcion = "descripcion";
		fecha = Utilidades.toDate(LocalDate.now());
		categorias = new ArrayList<String>();
		costo = 1f;
		cuposMax = 1;
		
		// Variables Test 1
		nombreEventoTest1 = "nombreEventoTest1";
		nombreEdicionTest1 = "nombreEdicionTest1";				
		nombreCategoriaTest1 = "categoriaTest1";
		nombreTipoRegistroTest1 = "nombreTipoRegistroTest1";
		
		// Variables Test 3
		nombreEdicionTest3 = "nombreEdicionTest3";
		
		// Variables Test 4
		nombreEventoTest4 = "nombreEventoTest4";
		nombreEdicionTest4 = "nombreEdicionTest4";
		
		// Variables Test 6
		nombreTipoRegistroTest6 = "nombreTipoRegistroTest6";
		
		// Variables Test 7
		nombreTipoRegistroTest7 = "nombreTipoRegistroTest7";
		
		// Variables Test 8
		nombreTipoRegistroTest8 = "nombreTipoRegistroTest8";
		
		// Variables Test 9
		nombreTipoRegistroTest9 = "nombreTipoRegistroTest9";
	}
	
	@Test
	@Order(1)
	void testAltaTipoRegistroExitosa(){
		// Testea un alta exitoso estandar
		
		assertDoesNotThrow(() -> {
			controladorEventos.altaCategoria(nombreCategoriaTest1);
		});
		categorias.add(nombreCategoriaTest1);
		controladorEventos.altaEvento(nombreEventoTest1, descripcion, fecha, sigla, categorias, null, null);
		
		assertDoesNotThrow(() -> {
			controladorEventos.altaEdicionEvento(nombreEventoTest1, nombreEdicionTest1, sigla, fecha, fecha, fecha, ciudad, pais, "", null, null);
		});
		
		try { 
			controladorEventos.altaTipoRegistro(nombreTipoRegistroTest1, descripcion, costo, cuposMax, nombreEventoTest1, nombreEdicionTest1);
		} catch (NombreTipoRegistroRepetido ex) {
			fail(ex.getMessage());
		} catch (DatosTipoRegistroInvalidos ex) {
			fail(ex.getMessage());
		}
		
		DTTipoRegistro dtTipoRegistroGeneradoTest1 = null;
		try { 
			dtTipoRegistroGeneradoTest1 = controladorEventos.getDTTipoRegistro(nombreEventoTest1, nombreEdicionTest1, nombreTipoRegistroTest1);
		} catch (TipoRegistroNoExiste ex) {
			fail(ex.getMessage());
		} catch (EventoNoExiste ex) {
			fail(ex.getMessage());
		} catch (EdicionNoExiste ex) {
			fail(ex.getMessage());
		}
		DTTipoRegistro dtTipoRegistroEsperado = new DTTipoRegistro(nombreTipoRegistroTest1, descripcion, costo, cuposMax, cuposMax);
		assertEquals(dtTipoRegistroGeneradoTest1, dtTipoRegistroEsperado);	
	}
	
	@Test
	@Order(2)
	void testAltaTipoRegistroNombreRepetido() {
		// Testea que no se pueda agregar un TipoRegistro con nombre repetido a una Edicion
		assertThrows(NombreTipoRegistroRepetido.class,
				()-> controladorEventos.altaTipoRegistro(nombreTipoRegistroTest1, descripcion, costo, cuposMax, nombreEventoTest1, nombreEdicionTest1));
	}
	
	@Test
	@Order(3)
	void testAltaTipoRegistroNombreRepetidoOtraEdicion() {
		// Testea agregar un TipoRegistro con nombre repetido en OTRA Edicion pero MISMO evento
		
		assertDoesNotThrow(() -> {
			controladorEventos.altaEdicionEvento(nombreEventoTest1, nombreEdicionTest3, sigla, fecha, fecha, fecha, ciudad, pais, "organizador14", null, null);
		});
		
		try { 
			controladorEventos.altaTipoRegistro(nombreTipoRegistroTest1, descripcion, costo, cuposMax, nombreEventoTest1, nombreEdicionTest3);
		} catch (NombreTipoRegistroRepetido ex) {
			fail(ex.getMessage());
		} catch (DatosTipoRegistroInvalidos ex) {
			fail(ex.getMessage());
		}
		
		DTTipoRegistro dtTipoRegistroGeneradoTest3 = null;
		try { 
			dtTipoRegistroGeneradoTest3 = controladorEventos.getDTTipoRegistro(nombreEventoTest1, nombreEdicionTest3, nombreTipoRegistroTest1);
		} catch (TipoRegistroNoExiste ex) {
			fail(ex.getMessage());
		} catch (EventoNoExiste ex) {
			fail(ex.getMessage());
		} catch (EdicionNoExiste ex) {
			fail(ex.getMessage());
		}
		DTTipoRegistro dtTipoRegistroEsperado = new DTTipoRegistro(nombreTipoRegistroTest1, descripcion, costo, cuposMax, cuposMax);
		assertEquals(dtTipoRegistroGeneradoTest3, dtTipoRegistroEsperado);	 
	}
	
	@Test
	@Order(4)
	void testAltaTipoRegistroNombreRepetidoOtroEvento() {
		// Testea agregar un TipoRegistro con nombre repetido en OTRO Evento
		controladorEventos.altaEvento(nombreEventoTest4, descripcion, fecha, sigla, categorias, null, null);
		
		assertDoesNotThrow(() -> {
			controladorEventos.altaEdicionEvento(nombreEventoTest4, nombreEdicionTest4, sigla, fecha, fecha, fecha, ciudad, pais, "organizador15", null, null);
		});
		
		try { 
			controladorEventos.altaTipoRegistro(nombreTipoRegistroTest1, descripcion, costo, cuposMax, nombreEventoTest4, nombreEdicionTest4);
		} catch (NombreTipoRegistroRepetido ex) {
			fail(ex.getMessage());
		} catch (DatosTipoRegistroInvalidos ex) {
			fail(ex.getMessage());
		}
		
		DTTipoRegistro dtTipoRegistroGeneradoTest3 = null;
		try { 
			dtTipoRegistroGeneradoTest3 = controladorEventos.getDTTipoRegistro(nombreEventoTest4, nombreEdicionTest4, nombreTipoRegistroTest1);
		} catch (TipoRegistroNoExiste ex) {
			fail(ex.getMessage());
		} catch (EventoNoExiste ex) {
			fail(ex.getMessage());
		} catch (EdicionNoExiste ex) {
			fail(ex.getMessage());
		}
		DTTipoRegistro dtTipoRegistroEsperado = new DTTipoRegistro(nombreTipoRegistroTest1, descripcion, costo, cuposMax, cuposMax);
		assertEquals(dtTipoRegistroGeneradoTest3, dtTipoRegistroEsperado);
	}
	
	@Test
	@Order(5)
	void testAltaTipoRegistroNombreVacio() {
		// Testea que no se pueda agregar un TipoRegistro con nombre vacío
		String nombreVacio = "";
		assertThrows(DatosTipoRegistroInvalidos.class,
				()-> controladorEventos.altaTipoRegistro(nombreVacio, descripcion, costo, cuposMax, nombreEventoTest1, nombreEdicionTest1));
	}
	
	@Test
	@Order(6)
	void testAltaTipoRegistroCostoNegativo() {
		// Testea que no se pueda agregar un TipoRegistro con costo negativo (menor a cero)
		float costoNegativo = -1f;
		assertThrows(DatosTipoRegistroInvalidos.class,
				()-> controladorEventos.altaTipoRegistro(nombreTipoRegistroTest6, descripcion, costoNegativo, cuposMax, nombreEventoTest1, nombreEdicionTest1));
	}
	
	@Test
	@Order(7)
	void testAltaTipoRegistroCostoCero() {
		// Testea que se pueda agregar un TipoRegistro con costo cero
		float costoCero = 0f;
		try { 
			controladorEventos.altaTipoRegistro(nombreTipoRegistroTest7, descripcion, costoCero, cuposMax, nombreEventoTest4, nombreEdicionTest4);
		} catch (NombreTipoRegistroRepetido ex) {
			fail(ex.getMessage());
		} catch (DatosTipoRegistroInvalidos ex) {
			fail(ex.getMessage());
		}
		
		DTTipoRegistro dtTipoRegistroGeneradoTest3 = null;
		try { 
			dtTipoRegistroGeneradoTest3 = controladorEventos.getDTTipoRegistro(nombreEventoTest4, nombreEdicionTest4, nombreTipoRegistroTest7);
		} catch (TipoRegistroNoExiste ex) {
			fail(ex.getMessage());
		} catch (EventoNoExiste ex) {
			fail(ex.getMessage());
		} catch (EdicionNoExiste ex) {
			fail(ex.getMessage());
		}
		DTTipoRegistro dtTipoRegistroEsperado = new DTTipoRegistro(nombreTipoRegistroTest7, descripcion, costoCero, cuposMax, cuposMax);
		assertEquals(dtTipoRegistroGeneradoTest3, dtTipoRegistroEsperado);
	}
	
	@Test
	@Order(8)
	void testAltaTipoRegistroCuposMaxNegativos() {
		// Testea que se pueda agregar un TipoRegistro con cupos negativos (menores a cero)
		int cuposNegativos = -1;
		assertThrows(DatosTipoRegistroInvalidos.class,
				()-> controladorEventos.altaTipoRegistro(nombreTipoRegistroTest8, descripcion, costo, cuposNegativos, nombreEventoTest1, nombreEdicionTest1));
	}
	
	@Test
	@Order(9)
	void testAltaTipoRegistroCuposMaxCero() {
		// Testea que se pueda agregar un TipoRegistro con cupos negativos (menores a cero)
		int cuposCero = 0;
		assertThrows(DatosTipoRegistroInvalidos.class,
				()-> controladorEventos.altaTipoRegistro(nombreTipoRegistroTest9, descripcion, costo, cuposCero, nombreEventoTest1, nombreEdicionTest1));
	}
}
