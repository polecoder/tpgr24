package TestUnitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
	void AltaOrganizadorCorreoRepetido() {
		try {
			controladorUsuarios.altaOrganizador("nickOrganizador1", "Eventos.2025!", "nombreOrganizadorA", "correo@gmail.com", "descripcion", "https://test.com");
			controladorUsuarios.altaOrganizador("nickOrganizador2", "Eventos.2025!", "nombreOrganizadorB", "correo@gmail.com", "descripcion", "https://test.com");
		} catch (Exception ex) { assertEquals("El correo ya está en uso", ex.getMessage()); }
	}
	
	@Test
	@Order(2)
	void AltaAsistenteConInstitucion() {
		try {
			controladorInstitucion.AltaInstitucion("nombreInstitucion", "descripcion", "https://institucion.com");
			controladorUsuarios.altaAsistente("nickAsistente", "Eventos.2025!", "nombreAsistente", "asistente@gmail.com", "apellido", fecha, "nombreInstitucion");
		} catch (Exception ex) { fail(ex.getMessage()); }
		
		Map<String, DTAsistente> asistentes = controladorUsuarios.listarAsistentes();
		if (!asistentes.containsKey("nickAsistente")) { fail("No se lista correctamente"); }
		
		Institucion institucion = controladorInstitucion.getInstitucion("nombreInstitucion");
		assertTrue(institucion.getAsistentes().containsKey("nickAsistente"), "Falla en link Institucion->Asistente");
	}
	
	@Test
	@Order(3)
	void DetalleAsistente() {
		try {
			DTDetalleUsuario detalle = controladorUsuarios.mostrarDetalleUsuario("nickAsistente");
			DTDetalleAsistente esperado = new DTDetalleAsistente("nickAsistente", "nombreAsistente", "asistente@gmail.com", "apellido", fecha);
			assertTrue(esperado.equals((DTDetalleAsistente)detalle));
		} catch (Exception ex) { fail(ex.getMessage()); }
	}
	
	@Test
	@Order(4)
	void DetalleOrganizador() {
		try {
			controladorUsuarios.altaOrganizador("nickOrganizador", "Eventos.2025!", "nombreOrganizador", "organizador@gmail.com", "descripcion", "https://test.com");
			DTDetalleUsuario detalle = controladorUsuarios.mostrarDetalleUsuario("nickOrganizador");
			DTDetalleOrganizador esperado = new DTDetalleOrganizador("nickOrganizador", "nombreOrganizador", "organizador@gmail.com", "descripcion", "https://test.com");
			assertTrue(esperado.equals((DTDetalleOrganizador)detalle));
		} catch (Exception ex) { fail(ex.getMessage()); }
	}
	
	@Test
	@Order(5)
	void UsuarioNoExiste() {
		try {
			controladorUsuarios.mostrarDetalleUsuario("nickNoExiste");
		} catch (Exception ex) { assertEquals(ex.getMessage(), "El usuario indicado no existe en el sistema."); }
	}
}
