package TestUnitarios;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import logica.Fabrica;
import logica.Utilidades;

public class TestAltaEvento {
	
	private static Fabrica fabrica;
	
	@BeforeEach
	void setup() {
		Fabrica.resetInstancia();
	    fabrica = Fabrica.getInstancia();
	}
	
	@Test
	void altaEventoCorrectaUnaCategoria() {
		// Creación de categorías
		try {
			fabrica.getControladorEvento().altaCategoria("Categoria 1");
		} catch (Exception e) {
			throw new IllegalStateException("Ya existe una categoría con ese nombre");
		}
		List<String> categorias = new ArrayList<>();
		categorias.add("Categoria 1");
		fabrica.getControladorEvento().altaEvento("Nombre1", "Descripcion1", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias,null);
	}
	
	@Test
	void altaEventoCorrectaVariasCategorias() {
		// Creación de categorías
		try {
			fabrica.getControladorEvento().altaCategoria("Categoria 1");
		} catch (Exception e) {
			throw new IllegalStateException("Ya existe una categoría con ese nombre");
		}
		try {
			fabrica.getControladorEvento().altaCategoria("Categoria 2");
		} catch (Exception e) {
			throw new IllegalStateException("Ya existe una categoría con ese nombre");
		}
		List<String> categorias = new ArrayList<>();
		categorias.add("Categoria 1");
		categorias.add("Categoria 2");
		fabrica.getControladorEvento().altaEvento("Nombre1", "Descripcion1", Utilidades.toDate(LocalDate.of(2025, 8, 31)), "SIG1", categorias,null);
	}
	
	@Test
	void listaCategorias() {
		try {
			fabrica.getControladorEvento().altaCategoria("Categoria 1");
		} catch (Exception e) {
			throw new IllegalStateException("Ya existe una categoría con ese nombre");
		}
		try {
			fabrica.getControladorEvento().altaCategoria("Categoria 2");
		} catch (Exception e) {
			throw new IllegalStateException("Ya existe una categoría con ese nombre");
		}
		List<String> categorias = new ArrayList<>();
		categorias.add("Categoria 1");
		categorias.add("Categoria 2");
		List<String> listaRealCategorias = fabrica.getControladorEvento().getListaCategorias();
		assertTrue(categorias.containsAll(listaRealCategorias) && listaRealCategorias.containsAll(categorias));
	}
}
