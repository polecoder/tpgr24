package testunitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.DatosTipoRegistroInvalidos;
import excepciones.NombreTipoRegistroRepetido;
import logica.Fabrica;
import logica.Utilidades;
import datatypes.DTDetallePatrocinio;
import datatypes.DTEdicionEvento;
import datatypes.ENivelPatrocinio;

public class TestConsultaEdicionEvento {
private static Fabrica fabrica;
	
	@BeforeEach
	void init() {
		Fabrica.resetInstancia();
		fabrica = Fabrica.getInstancia();
		// Alta Categoría
		assertDoesNotThrow(() -> {
			fabrica.getControladorEvento().altaCategoria("Categoria1");
	        });

		// Alta Asistente
		
		assertDoesNotThrow(() -> {
			fabrica.getControladorUsuarios().altaAsistente("Asistente1", "Eventos.2025!", "NombreAsistente", "mail@mail.com", "ApellidoAsistente", Utilidades.toDate(LocalDate.of(2002, 12, 17)), null, null);
	        });

		// Alta Evento
		List<String> listaCategorias = new ArrayList<>();
		listaCategorias.add("Categoria1");
		fabrica.getControladorEvento().altaEvento("Evento1", "Descripcion1", Utilidades.toDate(LocalDate.of(2024, 12, 1)), "SIG1", listaCategorias, null, null);
		// Alta Edición
		assertDoesNotThrow(() -> {
			fabrica.getControladorEvento().altaEdicionEvento("Evento1", "EdicionEvento1", "SIG1", Utilidades.toDate(LocalDate.of(2024, 12, 10)), Utilidades.toDate(LocalDate.of(2050, 12, 18)), Utilidades.toDate(LocalDate.of(2024, 12, 1)), "Montevideo", "Uruguay", "Organizador1", null, null);
	        });
	
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
		assertDoesNotThrow(() -> {
			fabrica.getControladorEvento().altaRegistro("Asistente1", "TR1", "EdicionEvento1", "Evento1", null, Utilidades.toDate(LocalDate.of(2024, 11, 15)));
		});
		// Test de la función
		Set<String> esperado = new HashSet<>();
		esperado.add("TR1-NombreAsistente");
		Set<String> resultado = fabrica.getControladorEvento().listarRegistrosAEdicion("Evento1", "EdicionEvento1");
		assertEquals(resultado, esperado);
	}
	
	@Test
	void operacionListarPatrociniosDeEdicion() {
		// Alta Institucion
		
		 assertDoesNotThrow(() -> {
			 fabrica.getControladorInstitucion().altaInstitucion("Institucion", "Descripcion1", "https://insti.com");
	        });
		
		// Alta Patrocinio
		 
		 assertDoesNotThrow(() -> {
			 fabrica.getControladorEvento().altaPatrocinio("Evento1", "EdicionEvento1", Utilidades.toDate(LocalDate.of(2024, 11, 10)), "Institucion", ENivelPatrocinio.Oro, 10000f, 1, "CODPAT1", "TR1");
	        });
		 
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
		DTEdicionEvento dtEsperado = new DTEdicionEvento("EdicionEvento1", "SIG1", Utilidades.toDate(LocalDate.of(2024, 12, 10)), Utilidades.toDate(LocalDate.of(2050, 12, 18)), Utilidades.toDate(LocalDate.of(2024, 12, 1)), "Montevideo", "Uruguay", "Organizador1", null, null, null);
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
	
	@Test
	void testOperacionGetNombresTipoRegistro() {
		Set<String> resultado = fabrica.getControladorEvento().getNombresTipoRegistro("Evento1", "EdicionEvento1");
		if (resultado.size() != 1) {
			fail("El tamaño del resultado no es el esperado.");
		}
		assertTrue(resultado.contains("TR1"));
	}
	
	@Test
	void testOperacionMostrarDetallePatrocinio() {
		assertDoesNotThrow(() -> {
			fabrica.getControladorInstitucion().altaInstitucion("Institucion", "Descripcion1", "https://web.web.com");
			fabrica.getControladorEvento().altaPatrocinio("Evento1", "EdicionEvento1", Utilidades.toDate(LocalDate.of(2025, 10, 1)), "Institucion", ENivelPatrocinio.Platino, 10000, 1, "CODPAT1", "TR1");
		});
		DTDetallePatrocinio resultado1 = fabrica.getControladorEvento().mostrarDetallePatrocinio("Evento1", "EdicionEvento1", "CODPAT1");
		assertEquals(resultado1.fechaAlta(), Utilidades.toDate(LocalDate.of(2025, 10, 1)));
		assertEquals(resultado1.nombreInstitucion(), "Institucion");
		assertEquals(resultado1.nivel(), ENivelPatrocinio.Platino);
		assertEquals(resultado1.aporte(), 10000);
		assertEquals(resultado1.cuposGratuitos(), 1);
		assertEquals(resultado1.codigo(), "CODPAT1");
		assertEquals(resultado1.tipoRegistro(), "TR1");
		
		DTDetallePatrocinio resultado2 = fabrica.getControladorEvento().mostrarDetallePatrocinio("Evento1", "EdicionInexistente", "CODPAT1");
		assertNull(resultado2);
	}
}
