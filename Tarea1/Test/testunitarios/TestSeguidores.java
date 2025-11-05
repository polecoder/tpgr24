package testunitarios;

import java.util.Date;

import org.junit.jupiter.api.Test;

import logica.Fabrica;
import logica.IControladorUsuarios;
import logica.Usuario;

public class TestSeguidores {
	@Test
	void testAsistenteSiguiendoAsistente() {
		Fabrica fabrica = Fabrica.getInstancia();
		IControladorUsuarios controladorUsuarios = fabrica.getControladorUsuarios();
		Usuario asistente1 = controladorUsuarios.altaAsistente("santib", "Taller.24", "Santiago", "santiago.b@find.edu.uy", "Barich", new Date(), "fing", null);
	}
}
