package TestUnitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;


import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.ControladorEventos;
import logica.Fabrica;
import logica.IControladorEventos;
import logica.datatypes.DTTipoRegistro;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAltaTipoRegistro {
	private static ControladorEventos controladorEventos = ControladorEventos.getInstancia();
	
	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstancia();
		controladorEventos = fabrica.getControladorEvento();
	}
	
	@Test
	@Order(1)
	void testAltaTipoRegistro (){
		String nombre = "Nombre 1";
		String descripcion = "Descripcion 1";
		float costo = 2.5f;
		int cuposMax = 100;
		
		String nombreEvento = "Ev1";
		String nombreEdicion = "Op1";
		
		DTTipoRegistro esperado = new DTTipoRegistro(nombre, descripcion, costo, cuposMax, cuposMax);
		DTTipoRegistro generado = null;
		try {
			controladorEventos.altaTipoRegistro(nombre, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
		} catch (NombreTipoRegistroRepetido ex) {
			fail("Nombre ya esta en uso");
			return;
		}
		try {
			generado = controladorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombre);
		} catch (TipoRegistroNoExiste ex) {
			fail("TipoRegistro no existe");
		}
		assertTrue(esperado.equals(generado));
	}
	
	@Test
	@Order(2)
	void testAltaTipoRegistroNombreRepetido() {
		String nombre = "Nombre 1";
		String descripcion = "Descripcion 2";
		float costo = 2f;
		int cuposMax = 200;
		
		String nombreEvento = "Ev1";
		String nombreEdicion = "Op1";
		
		DTTipoRegistro generado = null;
		try {
			controladorEventos.altaTipoRegistro(nombre, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
		} catch (NombreTipoRegistroRepetido ex) {
			assertEquals("El nombre del ya está en otro TipoRegistro de esta edicion", ex.getMessage());
		}
		try {
			generado = controladorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombre);
		} catch (TipoRegistroNoExiste ex) {
			fail("TipoRegistro no existe");
		}
	}
	
	@Test
	@Order(3)
	void testAltaTipoRegistroNombreRepetidoOtraEdicion() {
		String nombre = "Nombre 1";
		String descripcion = "Descripcion 3";
		float costo = 3f;
		int cuposMax = 300;
		
		String nombreEvento = "Ev2";
		String nombreEdicion = "Op2";
		
		DTTipoRegistro esperado = new DTTipoRegistro(nombre, descripcion, costo, cuposMax, cuposMax);
		DTTipoRegistro generado = null;
		try {
			controladorEventos.altaTipoRegistro(nombre, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
		} catch (NombreTipoRegistroRepetido ex) {
			fail("El nombre del ya está en otro TipoRegistro de esta edicion");
		}
		try {
			generado = controladorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombre);
		} catch (TipoRegistroNoExiste ex) {
			fail("TipoRegistro no existe");
		}
		assertTrue(esperado.equals(generado));
	}
	
	@Test
	@Order(4)
	void testAltaTipoRegistroCostoNoFloat() {
		String nombre = "Nombre 1";
		String descripcion = "Descripcion 3";
		float costo = 3f;
		int cuposMax = 300;
		
		String nombreEvento = "Ev2";
		String nombreEdicion = "Op2";
		
		DTTipoRegistro esperado = new DTTipoRegistro(nombre, descripcion, costo, cuposMax, cuposMax);
		DTTipoRegistro generado = null;
		try {
			controladorEventos.altaTipoRegistro(nombre, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
		} catch (NombreTipoRegistroRepetido ex) {
			fail("El nombre del ya está en otro TipoRegistro de esta edicion");
		}
		try {
			generado = controladorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombre);
		} catch (TipoRegistroNoExiste ex) {
			fail("TipoRegistro no existe");
		}
		assertTrue(esperado.equals(generado));
	}
}
