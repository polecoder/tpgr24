package testunitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import excepciones.FormularioIncompletoOrganizador;
import excepciones.UsuarioNoExiste;
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
		assertThrows(FormularioIncompletoOrganizador.class, () -> {
			controladorUsuarios.altaOrganizador("nickOrganizador1", "Eventos.2025!", "nombreOrganizadorA", "correo@gmail.com", "descripcion", "https://test.com", null);
			controladorUsuarios.altaOrganizador("nickOrganizador2", "Eventos.2025!", "nombreOrganizadorB", "correo@gmail.com", "descripcion", "https://test.com", null);
		});
	}
	
	@Test
	@Order(2)
	void altaAsistenteConInstitucion() {
		
		assertDoesNotThrow(() -> {
			controladorInstitucion.altaInstitucion("nombreInstitucion", "descripcion", "https://institucion.com");
			controladorUsuarios.altaAsistente("nickAsistente1", "Eventos.2025!", "nombreAsistente", "asistente@gmail.com", "apellido", fecha, "nombreInstitucion", null);
		});
		
		Map<String, DTAsistente> asistentes = controladorUsuarios.listarAsistentes();
		if (!asistentes.containsKey("nickAsistente1")) { fail("No se lista correctamente"); }
		
		Institucion institucion = controladorInstitucion.getInstitucion("nombreInstitucion");
		assertTrue(institucion.getAsistentes().containsKey("nickAsistente1"), "Falla en link Institucion->Asistente");
	}
	
	@Test
	@Order(3)
	void detalleAsistente() {
		assertDoesNotThrow(() -> {
			controladorUsuarios.altaAsistente("nickAsistente11", "Eventos.2025!", "nombreAsistente", "asistente11@gmail.com", "apellido", fecha, "nombreInstitucion", null);
			DTDetalleUsuario detalle = controladorUsuarios.mostrarDetalleUsuario("nickAsistente11");
			DTDetalleAsistente esperado = new DTDetalleAsistente("nickAsistente11", "nombreAsistente", "asistente11@gmail.com", "apellido", fecha, null, null);
			// TODO: Comparar detalle con esperado
		});
	}
	
	@Test
	@Order(4)
	void detalleOrganizador() {
		
		assertDoesNotThrow(() -> {
			controladorUsuarios.altaOrganizador("nickOrganizador1", "Eventos.2025!", "nombreOrganizador", "organizador@gmail.com", "descripcion", "https://test.com", null);
			DTDetalleUsuario detalle = controladorUsuarios.mostrarDetalleUsuario("nickOrganizador1");
			DTDetalleOrganizador esperado = new DTDetalleOrganizador("nickOrganizador1", "nombreOrganizador", "organizador@gmail.com", "descripcion", "https://test.com", null);
			// TODO: Comparar detalle con esperado
		});
	}
	
	@Test
	@Order(5)
	void usuarioNoExiste() {
		assertThrows(UsuarioNoExiste.class, () -> {
			controladorUsuarios.mostrarDetalleUsuario("nickNoExiste");
		});
		
	}
}
