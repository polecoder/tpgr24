package TestUnitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import logica.Fabrica;
import logica.IControladorInstitucion;
import logica.IControladorUsuarios;
import logica.Institucion;
import logica.Utilidades;
import datatypes.DTAsistente;
import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleUsuario;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestsManejadorUsuario {
	
	private static IControladorUsuarios controladorUsuarios;
	private static IControladorInstitucion controladorInstitucion;
	private static Date fecha;
	@BeforeAll
	public static void inicio() {
		Fabrica fabrica = Fabrica.getInstancia();
		controladorUsuarios = fabrica.getControladorUsuarios();
		controladorInstitucion = fabrica.getControladorInstitucion();
		
		fecha = Utilidades.toDate(LocalDate.now());
	}
	
	@Test
	@Order(1)
	void altaOrganizadorCorreoRepetido() {
		assertDoesNotThrow(() -> {
			controladorUsuarios.altaOrganizador("nickOrganizador1", "Eventos.2025!", "nombreOrganizadorA", "correo@gmail.com", "descripcion", "https://test.com", null);
			controladorUsuarios.altaOrganizador("nickOrganizador2", "Eventos.2025!", "nombreOrganizadorB", "correo@gmail.com", "descripcion", "https://test.com", null);
		});
	}
	
	@Test
	@Order(2)
	void altaAsistenteConInstitucion() {
		
		assertDoesNotThrow(() -> {
			controladorInstitucion.altaInstitucion("nombreInstitucion", "descripcion", "https://institucion.com");
			controladorUsuarios.altaAsistente("nickAsistente", "Eventos.2025!", "nombreAsistente", "asistente@gmail.com", "apellido", fecha, "nombreInstitucion", null);
		});
		
		Map<String, DTAsistente> asistentes = controladorUsuarios.listarAsistentes();
		if (!asistentes.containsKey("nickAsistente")) { fail("No se lista correctamente"); }
		
		Institucion institucion = controladorInstitucion.getInstitucion("nombreInstitucion");
		assertTrue(institucion.getAsistentes().containsKey("nickAsistente"), "Falla en link Institucion->Asistente");
	}
	
	@Test
	@Order(3)
	void edtalleAsistente() {
		assertDoesNotThrow(() -> {
			DTDetalleUsuario detalle = controladorUsuarios.mostrarDetalleUsuario("nickAsistente");
			DTDetalleAsistente esperado = new DTDetalleAsistente("nickAsistente", "nombreAsistente", "asistente@gmail.com", "apellido", fecha, null, null);
			assertTrue(esperado.equals((DTDetalleAsistente) detalle));
		});
	}
	
	@Test
	@Order(4)
	void detalleOrganizador() {
		
		assertDoesNotThrow(() -> {
			controladorUsuarios.altaOrganizador("nickOrganizador", "Eventos.2025!", "nombreOrganizador", "organizador@gmail.com", "descripcion", "https://test.com", null);
			DTDetalleUsuario detalle = controladorUsuarios.mostrarDetalleUsuario("nickOrganizador");
			DTDetalleOrganizador esperado = new DTDetalleOrganizador("nickOrganizador", "nombreOrganizador", "organizador@gmail.com", "descripcion", "https://test.com", null);
			assertTrue(esperado.equals((DTDetalleOrganizador) detalle));
		});
	}
	
	@Test
	@Order(5)
	void usuarioNoExiste() {
		
		assertDoesNotThrow(() -> {
			controladorUsuarios.mostrarDetalleUsuario("nickNoExiste");
		});
		
	}
}
