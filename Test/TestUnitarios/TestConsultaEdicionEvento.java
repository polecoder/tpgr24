package TestUnitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.DatosTipoRegistroInvalidos;
import excepciones.NombreTipoRegistroRepetido;
import logica.ENivelPatrocinio;
import logica.Fabrica;
import logica.Utilidades;
import logica.datatypes.DTEdicionEvento;

public class TestConsultaEdicionEvento {
private static Fabrica fabrica;
	
	@BeforeEach
	void init() {
		Fabrica.resetInstancia();
		fabrica = Fabrica.getInstancia();
		// Alta Categoría
		try {
			fabrica.getControladorEvento().altaCategoria("Categoria1");
		} catch (Exception e) {
			throw new IllegalStateException("Ya existe una categoría con ese nombre.");
		}
		// Alta Asistente
		try {
			fabrica.getControladorUsuarios().altaAsistente("Asistente1", "abc1234", "NombreAsistente", "mail@mail.com", "ApellidoAsistente", Utilidades.toDate(LocalDate.of(2002, 12, 17)), null);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
		// Alta Evento
		List<String> listaCategorias = new ArrayList<>();
		listaCategorias.add("Categoria1");
		fabrica.getControladorEvento().altaEvento("Evento1", "Descripcion1", Utilidades.toDate(LocalDate.of(2024, 12, 1)), "SIG1", listaCategorias);
		// Alta Edición
		try {
			fabrica.getControladorEvento().altaEdicionEvento("Evento1", "EdicionEvento1", "SIG1", Utilidades.toDate(LocalDate.of(2024, 12, 10)), Utilidades.toDate(LocalDate.of(2024, 12, 18)), Utilidades.toDate(LocalDate.of(2024, 12, 1)), "Montevideo", "Uruguay", "Organizador1");
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
		// Alta Tipo Registro
		try {
			fabrica.getControladorEvento().altaTipoRegistro("TR1", "Descripcion1", 100, 50, "Evento1", "EdicionEvento1");
		} catch (NombreTipoRegistroRepetido | DatosTipoRegistroInvalidos e) {
			throw new IllegalStateException(e.getMessage());
		}
	}
	
	@Test
	void operacionListarRegistrosAEdicion() {
		// Alta Registro
		fabrica.getControladorEvento().altaRegistro("Asistente1", "TR1", "EdicionEvento1", "Evento1", null, Utilidades.toDate(LocalDate.of(2024, 11, 15)));
		
		// Test de la función
		Set<String> esperado = new HashSet<>();
		esperado.add("TR1-NombreAsistente");
		Set<String> resultado = fabrica.getControladorEvento().listarRegistrosAEdicion("Evento1", "EdicionEvento1");
		assertEquals(resultado, esperado);
	}
	
	@Test
	void operacionListarPatrociniosDeEdicion() {
		// Alta Institucion
		try {
			fabrica.getControladorInstitucion().AltaInstitucion("Institucion", "Descripcion1", "https://insti.com");
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
		
		// Alta Patrocinio
		try {
			fabrica.getControladorEvento().altaPatrocinio("Evento1", "EdicionEvento1", Utilidades.toDate(LocalDate.of(2024, 11, 10)), "Institucion", ENivelPatrocinio.Oro, 100f, 50, "CODPAT1");
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
		// Test de la función
		Set<String> esperado = new HashSet<>();
		esperado.add("CODPAT1");
		Set<String> resultado = fabrica.getControladorEvento().listarPatrociniosDeEdicion("Evento1", "EdicionEvento1");
		assertEquals(resultado, esperado);
	}
	
	@Test
	void operacionListarEdicionesEvento() {
		Map<String, DTEdicionEvento> resultado = fabrica.getControladorEvento().listarEdicionesEvento("Evento1");
		if (resultado.size() != 1) {
			fail("El tamaño del resultado no es el esperado.");
		}
		// Como solo debería haber un elemento, se hace la prueba para ese elemento.
		DTEdicionEvento dtEsperado = new DTEdicionEvento("EdicionEvento1", "SIG1", Utilidades.toDate(LocalDate.of(2024, 12, 10)), Utilidades.toDate(LocalDate.of(2024, 12, 18)), Utilidades.toDate(LocalDate.of(2024, 12, 1)), "Montevideo", "Uruguay", "Organizador1");
		DTEdicionEvento dtResultado = resultado.get("EdicionEvento1");
		assertEquals(dtEsperado.getNombre(), dtResultado.getNombre());
		assertEquals(dtEsperado.getSigla(), dtResultado.getSigla());
		assertEquals(dtEsperado.getFechaInicio(), dtResultado.getFechaInicio());
		assertEquals(dtEsperado.getFechaFin(), dtResultado.getFechaFin());
		assertEquals(dtEsperado.getFechaAlta(), dtResultado.getFechaAlta());
		assertEquals(dtEsperado.getCiudad(), dtResultado.getCiudad());
		assertEquals(dtEsperado.getPais(), dtResultado.getPais());
		assertEquals(dtEsperado.getOrganizadorNickname(), dtResultado.getOrganizadorNickname());
	}
}
