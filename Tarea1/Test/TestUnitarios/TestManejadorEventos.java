package TestUnitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorUsuarios;
import logica.Utilidades;
import datatypes.DTEvento;
import datatypes.DTTipoRegistro;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestManejadorEventos {
	
	private static IControladorEventos controladorEventos;
	private static IControladorUsuarios controladorUsuarios;
	private static Date fecha;
	
	@BeforeAll
	public static void inicio() {
		Fabrica fabrica = Fabrica.getInstancia();
		controladorEventos = fabrica.getControladorEvento();
		controladorUsuarios = fabrica.getControladorUsuarios();
		fecha = Utilidades.toDate(LocalDate.now());
	}
	
	@Test
	@Order(1)
	void testListarEventos() {
		List<String> categorias = new ArrayList<>();
		categorias.add("categoria1");
		assertDoesNotThrow(() -> {
			controladorEventos.altaCategoria("categoria1");
		});
		controladorEventos.altaEvento("nombreEvento", "descripcion", fecha, "sigla", categorias, null);
		Map<String, DTEvento> eventos = controladorEventos.listarEventos();
		assertTrue(eventos.containsKey("nombreEvento"));
	}
	
	@Test
	@Order(2)
	void testListarTipoRegistro() {
		assertDoesNotThrow(() -> {
			controladorUsuarios.altaOrganizador("nickOrganizadorListarTipoRegistro", "Eventos.2025!", "nombreOrganizador", "correo@gmail.com", "descripcion", "https://link.com", null);
			controladorEventos.altaEdicionEvento("nombreEvento", "nombreEdicionListarTipoRegistro", "sigla", fecha, fecha, fecha, "ciudad", "pais", "nickOrganizadorListarTipoRegistro", null);
			controladorEventos.altaTipoRegistro("nombreTipoRegistro", "descripcion", 1f, 10, "nombreEvento", "nombreEdicionListarTipoRegistro");
		});
		
		Map<String, DTTipoRegistro> tiposRegistro = controladorEventos.listarTipoRegistro("nombreEvento", "nombreEdicionListarTipoRegistro");
		assertTrue(tiposRegistro.containsKey("nombreTipoRegistro"));
	}
}
