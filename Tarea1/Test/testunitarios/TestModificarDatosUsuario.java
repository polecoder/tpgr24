package testunitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Fabrica;
import logica.Utilidades;
import datatypes.DTDetalleAsistente;

public class TestModificarDatosUsuario {
	
	private Fabrica fabrica;
	
	@BeforeEach
	public void setup() {
		Fabrica.resetInstancia();
		fabrica = Fabrica.getInstancia();
		
		assertDoesNotThrow(() -> {
			fabrica.getControladorUsuarios().altaAsistente("pedrog", "Eventos.2025!", "Pedro", "pedro@mail.com", "García", Utilidades.toDate(LocalDate.of(2001, 10, 2)), null, null);
			fabrica.getControladorUsuarios().altaOrganizador("gabrielam", "Eventos.2025!", "Gabriel", "gaby@mail.com", "Organizadora de eveos de perros", "https://perritos.gabycom.uy", null);
		});
	}
	
	@Test
	public void testModificarDatosAsistenteCorrecto() {
		
		assertDoesNotThrow(() -> {
			fabrica.getControladorUsuarios().modificarDatosAsistente("pedrog", "Pedrito", "García Pérez", Utilidades.toDate(LocalDate.of(2001, 10, 3)));
			DTDetalleAsistente datosAsistente = (DTDetalleAsistente) fabrica.getControladorUsuarios().mostrarDetalleUsuario("pedrog");
			assertEquals(datosAsistente.getNombre(), "Pedrito");
			assertEquals(datosAsistente.getApellido(), "García Pérez");
			assertEquals(datosAsistente.getNacimiento(), Utilidades.toDate(LocalDate.of(2001, 10, 3)));
		});
	}
	
	@Test
	public void testModificarDatosOrganizadorCorrecto() {
		assertDoesNotThrow(() -> {
			fabrica.getControladorUsuarios().modificarDatosOrganizador("gabrielam", "Gabriela", "Organizadora de eventos de perros", "https://perritos.gaby.com.uy");
		});
	}
	
	@Test
	public void testDatosIncorrectosAsistente() {
		// Campos faltantes
		Exception excepcion1 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosAsistente("pedrog", "", "García Pérez", Utilidades.toDate(LocalDate.of(2001, 10, 3)));
		});
		assertTrue(excepcion1.getMessage().contains("El nuevo nombre no puede ser vacío."));
		Exception excepcion5 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosAsistente("pedrog", "Pedrito", "", Utilidades.toDate(LocalDate.of(2001, 10, 3)));
		});
		assertTrue(excepcion5.getMessage().contains("El nuevo apellido no puede ser vacío."));
		Exception excepcion3 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosAsistente("pedrog", "Pedrito", "García Pérez", null);
		});
		assertTrue(excepcion3.getMessage().contains("Ingrese una nueva fecha de nacimiento."));
		
		// Campos iguales al estado actual del sistema
		Exception excepcion4 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosAsistente("pedrog", "Pedro", "García", Utilidades.toDate(LocalDate.of(2001, 10, 2)));
		});
		assertTrue(excepcion4.getMessage().contains("No se modificó ningún campo."));
		
		// Validación de datos de campos
		Exception exception = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosAsistente("pedrog", "p123", "García Pérez", Utilidades.toDate(LocalDate.of(2001, 10, 3)));
		});
		assertTrue(exception.getMessage().contains("El nuevo nombre solo puede contener letras y espacios."));
		Exception excepcion6 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosAsistente("pedrog", "Pedrito", "g123", Utilidades.toDate(LocalDate.of(2001, 10, 3)));
		});
		assertTrue(excepcion6.getMessage().contains("El nuevo apellido solo puede contener letras y espacios."));
		Exception excepcion7 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosAsistente("pedrog", "Pedrito", "García Pérez", Utilidades.toDate(LocalDate.of(2050, 10, 3)));
		});
		assertTrue(excepcion7.getMessage().contains("La nueva fecha de nacimiento no puede ser en el futuro."));
	}
	
	@Test
	public void testDatosIncorrectosOrganizador() {
		// Campos faltantes
		Exception excepcion1 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosOrganizador("gabrielam", "", "Organizadora de eventos de perros", "https://perritos.gaby.com.uy");
		});
		assertTrue(excepcion1.getMessage().contains("El nuevo nombre no puede ser vacío."));
		Exception excepcion2 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosOrganizador("gabrielam", "Gabriela", "", "https://perritos.gaby.com.uy");
		});
		assertTrue(excepcion2.getMessage().contains("La nueva descripción no puede ser vacía."));
		Exception excepcion3 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosOrganizador("gabrielam", "Gabriela", "Organizadora de eventos de perros", "");
		});
		assertTrue(excepcion3.getMessage().contains("El nuevo link no puede ser vacío."));
		
		// Campos iguales al estado actual del sistema
		Exception excepcion4 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosOrganizador("gabrielam", "Gabriel", "Organizadora de eveos de perros", "https://perritos.gabycom.uy");
		});
		assertTrue(excepcion4.getMessage().contains("No se modificó ningún campo."));
		
		// Validación de datos de campos
		Exception excepcion5 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosOrganizador("gabrielam", "a123", "Organizadora de eventos de perros", "https://perritos.gaby.com.uy");
		});
		assertTrue(excepcion5.getMessage().contains("El nuevo nombre solo puede contener letras y espacios."));
		Exception excepcion6 = assertThrows(Exception.class, () -> {
			fabrica.getControladorUsuarios().modificarDatosOrganizador("gabrielam", "Gabriela", "Organizadora de eventos de perros", "perritos.gaby.com.uy");
		});
		assertTrue(excepcion6.getMessage().contains("El nuevo link debe comenzar con http:// o https://."));
	}
}
