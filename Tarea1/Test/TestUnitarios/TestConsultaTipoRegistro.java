package TestUnitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.DatosTipoRegistroInvalidos;
import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.NombreTipoRegistroRepetido;
import logica.Fabrica;
import logica.Utilidades;

public class TestConsultaTipoRegistro {
	private static Fabrica fabrica;
	
	@BeforeEach
	void init() {
		Fabrica.resetInstancia();
		fabrica = Fabrica.getInstancia();
	}
	
	@Test
	void operacionGetTiposRegistro() {
		// Alta Categoría
		try {
			fabrica.getControladorEvento().altaCategoria("Categoria1");
		} catch (Exception e) {
			throw new IllegalStateException("Ya existe una categoría con ese nombre.");
		}
		// Alta Organizador
		try {
			fabrica.getControladorUsuarios().altaOrganizador("Organizador1", "Eventos.2025!", "Nombre", "mail@mail.com", "Descripcion1", "https://Link1");
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
		// Alta Evento
		List<String> listaCategorias = new ArrayList<>();
		listaCategorias.add("Categoria1");
		fabrica.getControladorEvento().altaEvento("Evento1", "Descripcion1", Utilidades.toDate(LocalDate.of(2024, 12, 1)), "SIG1", listaCategorias,null);
		// Alta Edición
		try {
			fabrica.getControladorEvento().altaEdicionEvento("Evento1", "EdicionEvento1", "SIG1", Utilidades.toDate(LocalDate.of(2024, 12, 10)), Utilidades.toDate(LocalDate.of(2024, 12, 18)), Utilidades.toDate(LocalDate.of(2024, 12, 1)), "Montevideo", "Uruguay", "Organizador1",null);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
		// Alta Tipo Registro
		try {
			fabrica.getControladorEvento().altaTipoRegistro("TR1", "Descripcion1", 100, 50, "Evento1", "EdicionEvento1");
		} catch (NombreTipoRegistroRepetido | DatosTipoRegistroInvalidos e) {
			throw new IllegalStateException(e.getMessage());
		}
		// Test de la función
		try {
			String[] resultado = fabrica.getControladorEvento().getTiposRegistro("Evento1", "EdicionEvento1");
			String[] esperado = {"TR1"};
			if (resultado.length != esperado.length) {
				fail("El resultado de getTiposRegistros() es distinto al esperado.");
			}
			for (int i = 0; i < resultado.length; i++) {
				assertEquals(resultado[i], esperado[i]);
			}
			
			
		} catch (EventoNoExiste | EdicionNoExiste e) {
			throw new IllegalStateException(e.getMessage());
		}
	}
}
