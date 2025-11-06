package testunitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.ETipoUsuario;
import excepciones.UsuarioNoExiste;
import logica.Fabrica;
import logica.IControladorUsuarios;

public class TestSeguidores {
	private Fabrica fabrica = Fabrica.getInstancia();
	private IControladorUsuarios controladorUsuarios = fabrica.getControladorUsuarios();
	private Date testDate = new Date();
	
	@BeforeEach
	public void init() {
		Fabrica.resetInstancia();
		fabrica = Fabrica.getInstancia();
		controladorUsuarios = fabrica.getControladorUsuarios();
		// Asistentes de prueba
		assertDoesNotThrow(() -> controladorUsuarios.altaAsistente("santib", "Taller.24", "Santiago",
				"santiago.b@fing.edu.uy", "Barich", testDate, null, null));
		assertDoesNotThrow(() -> controladorUsuarios.altaAsistente("user01", "Taller.24", "User", "user@fing.edu.uy",
				"CeroUno", testDate, null, null));		
		// Organizadores de prueba
		assertDoesNotThrow(() -> controladorUsuarios.altaOrganizador("user02", "Taller.24", "UserDos", "user2@fing.edu.uy","organizador de pruebas", "https://algo.com",null));
		assertDoesNotThrow(() -> controladorUsuarios.altaOrganizador("orgPruebas", "Taller.24", "Organizador", "organiza@fing.edu.uy","organizador de pruebas 2", "https://algo2.com",null));
	}

	@Test
	void testAsistenteSiguiendoAsistente() {
		assertDoesNotThrow(() -> controladorUsuarios.seguir("santib", "user01"));
		DTDetalleAsistente detalleSanti = (DTDetalleAsistente) assertDoesNotThrow(() -> controladorUsuarios.mostrarDetalleUsuario("santib"));
		DTDetalleAsistente detalleUser01 = (DTDetalleAsistente) assertDoesNotThrow(() -> controladorUsuarios.mostrarDetalleUsuario("user01"));

		assertEquals(detalleSanti.getNickname(), "santib");
		assertEquals(detalleSanti.getNombre(), "Santiago");
		assertEquals(detalleSanti.getCorreo(), "santiago.b@fing.edu.uy");
		assertEquals(detalleSanti.getApellido(), "Barich");
		assertEquals(detalleSanti.getNacimiento(), testDate);
		assertEquals(detalleSanti.getInstitucion(), null);
		assertEquals(detalleSanti.getImagen(), null);
		assertEquals(detalleSanti.getTipo(), ETipoUsuario.Asistente);
		
		assertEquals(detalleUser01.getNickname(), "user01");
		assertEquals(detalleUser01.getNombre(), "User");
		assertEquals(detalleUser01.getCorreo(), "user@fing.edu.uy");
		assertEquals(detalleUser01.getApellido(), "CeroUno");
		assertEquals(detalleUser01.getNacimiento(), testDate);
		assertEquals(detalleUser01.getInstitucion(), null);
		assertEquals(detalleUser01.getImagen(), null);
		assertEquals(detalleUser01.getTipo(), ETipoUsuario.Asistente);

	    assertEquals(1, detalleSanti.getDataTypeSeguidos().size());
	    assertTrue(detalleSanti.getDataTypeSeguidos().stream()
	        .anyMatch(u -> u.equals("user01")));
	    
	    assertEquals(1, detalleUser01.getDataTypeSeguidores().size());
	    assertTrue(detalleUser01.getDataTypeSeguidores().stream()
	        .anyMatch(u -> u.equals("santib")));
	    
	    assertEquals(0, detalleSanti.getDataTypeSeguidores().size());
	    assertEquals(0, detalleUser01.getDataTypeSeguidos().size());
	}
	@Test
	void testAsistenteSiguiendoOrganizador() {
		assertDoesNotThrow(() -> controladorUsuarios.seguir("santib", "user02"));
		DTDetalleAsistente detalleSanti = (DTDetalleAsistente) assertDoesNotThrow(() -> controladorUsuarios.mostrarDetalleUsuario("santib"));
		DTDetalleOrganizador detalleUser02 = (DTDetalleOrganizador) assertDoesNotThrow(() -> controladorUsuarios.mostrarDetalleUsuario("user02"));

		assertEquals(detalleSanti.getNickname(), "santib");
		assertEquals(detalleSanti.getNombre(), "Santiago");
		assertEquals(detalleSanti.getCorreo(), "santiago.b@fing.edu.uy");
		assertEquals(detalleSanti.getApellido(), "Barich");
		assertEquals(detalleSanti.getNacimiento(), testDate);
		assertEquals(detalleSanti.getInstitucion(), null);
		assertEquals(detalleSanti.getImagen(), null);
		assertEquals(detalleSanti.getTipo(), ETipoUsuario.Asistente);
		
		assertEquals(detalleUser02.getNickname(), "user02");
		assertEquals(detalleUser02.getNombre(), "UserDos");
		assertEquals(detalleUser02.getCorreo(), "user2@fing.edu.uy");
		assertEquals(detalleUser02.getDescripcion(), "organizador de pruebas");
		assertEquals(detalleUser02.getLink(), "https://algo.com");
		assertEquals(detalleUser02.getImagen(), null);
		assertEquals(detalleUser02.getTipo(), ETipoUsuario.Organizador);

	    assertEquals(1, detalleSanti.getDataTypeSeguidos().size());
	    assertTrue(detalleSanti.getDataTypeSeguidos().stream()
	        .anyMatch(u -> u.equals("user02")));
	    
	    assertEquals(1, detalleUser02.getDataTypeSeguidores().size());
	    assertTrue(detalleUser02.getDataTypeSeguidores().stream()
	        .anyMatch(u -> u.equals("santib")));
	    
	    assertEquals(0, detalleSanti.getDataTypeSeguidores().size());
	    assertEquals(0, detalleUser02.getDataTypeSeguidos().size());
	}
	@Test
	void testOrganizadorSiguiendoAsistente() {
		assertDoesNotThrow(() -> controladorUsuarios.seguir("user02","santib"));
		DTDetalleAsistente detalleSanti = (DTDetalleAsistente) assertDoesNotThrow(() -> controladorUsuarios.mostrarDetalleUsuario("santib"));
		DTDetalleOrganizador detalleUser02 = (DTDetalleOrganizador) assertDoesNotThrow(() -> controladorUsuarios.mostrarDetalleUsuario("user02"));

		assertEquals(detalleSanti.getNickname(), "santib");
		assertEquals(detalleSanti.getNombre(), "Santiago");
		assertEquals(detalleSanti.getCorreo(), "santiago.b@fing.edu.uy");
		assertEquals(detalleSanti.getApellido(), "Barich");
		assertEquals(detalleSanti.getNacimiento(), testDate);
		assertEquals(detalleSanti.getInstitucion(), null);
		assertEquals(detalleSanti.getImagen(), null);
		assertEquals(detalleSanti.getTipo(), ETipoUsuario.Asistente);
		
		assertEquals(detalleUser02.getNickname(), "user02");
		assertEquals(detalleUser02.getNombre(), "UserDos");
		assertEquals(detalleUser02.getCorreo(), "user2@fing.edu.uy");
		assertEquals(detalleUser02.getDescripcion(), "organizador de pruebas");
		assertEquals(detalleUser02.getLink(), "https://algo.com");
		assertEquals(detalleUser02.getImagen(), null);
		assertEquals(detalleUser02.getTipo(), ETipoUsuario.Organizador);

	    assertEquals(1, detalleSanti.getDataTypeSeguidores().size());
	    assertTrue(detalleSanti.getDataTypeSeguidores().stream()
	        .anyMatch(u -> u.equals("user02")));
	    
	    assertEquals(1, detalleUser02.getDataTypeSeguidos().size());
	    assertTrue(detalleUser02.getDataTypeSeguidos().stream()
	        .anyMatch(u -> u.equals("santib")));
	    
	    assertEquals(0, detalleSanti.getDataTypeSeguidos().size());
	    assertEquals(0, detalleUser02.getDataTypeSeguidores().size());
	}
	@Test
	void testOrganizadorSiguiendoOrganizador() {
		assertDoesNotThrow(() -> controladorUsuarios.seguir("user02","orgPruebas"));
		DTDetalleOrganizador detalleOrgPruebas = (DTDetalleOrganizador) assertDoesNotThrow(() -> controladorUsuarios.mostrarDetalleUsuario("orgPruebas"));
		DTDetalleOrganizador detalleUser02 = (DTDetalleOrganizador) assertDoesNotThrow(() -> controladorUsuarios.mostrarDetalleUsuario("user02"));

		assertEquals(detalleOrgPruebas.getNickname(), "orgPruebas");
		assertEquals(detalleOrgPruebas.getNombre(), "Organizador");
		assertEquals(detalleOrgPruebas.getCorreo(), "organiza@fing.edu.uy");
		assertEquals(detalleOrgPruebas.getDescripcion(), "organizador de pruebas 2");
		assertEquals(detalleOrgPruebas.getLink(), "https://algo2.com");
		assertEquals(detalleOrgPruebas.getImagen(), null);
		assertEquals(detalleOrgPruebas.getTipo(), ETipoUsuario.Organizador);
		
		assertEquals(detalleUser02.getNickname(), "user02");
		assertEquals(detalleUser02.getNombre(), "UserDos");
		assertEquals(detalleUser02.getCorreo(), "user2@fing.edu.uy");
		assertEquals(detalleUser02.getDescripcion(), "organizador de pruebas");
		assertEquals(detalleUser02.getLink(), "https://algo.com");
		assertEquals(detalleUser02.getImagen(), null);
		assertEquals(detalleUser02.getTipo(), ETipoUsuario.Organizador);

	    assertEquals(1, detalleOrgPruebas.getDataTypeSeguidores().size());
	    assertTrue(detalleOrgPruebas.getDataTypeSeguidores().stream()
	        .anyMatch(u -> u.equals("user02")));
	    
	    assertEquals(1, detalleUser02.getDataTypeSeguidos().size());
	    assertTrue(detalleUser02.getDataTypeSeguidos().stream()
	        .anyMatch(u -> u.equals("orgPruebas")));
	    
	    assertEquals(0, detalleOrgPruebas.getDataTypeSeguidos().size());
	    assertEquals(0, detalleUser02.getDataTypeSeguidores().size());
	}
	@Test
	void testSiguiendoMultiples() {
		assertDoesNotThrow(() -> controladorUsuarios.seguir("santib","user02"));
		assertDoesNotThrow(() -> controladorUsuarios.seguir("santib","orgPruebas"));
		assertDoesNotThrow(() -> controladorUsuarios.seguir("santib","user01"));
		DTDetalleAsistente detalleSanti = (DTDetalleAsistente) assertDoesNotThrow(() -> controladorUsuarios.mostrarDetalleUsuario("santib"));

		assertEquals(detalleSanti.getNickname(), "santib");
		assertEquals(detalleSanti.getNombre(), "Santiago");
		assertEquals(detalleSanti.getCorreo(), "santiago.b@fing.edu.uy");
		assertEquals(detalleSanti.getApellido(), "Barich");
		assertEquals(detalleSanti.getNacimiento(), testDate);
		assertEquals(detalleSanti.getInstitucion(), null);
		assertEquals(detalleSanti.getImagen(), null);
		assertEquals(detalleSanti.getTipo(), ETipoUsuario.Asistente);

		assertEquals(3, detalleSanti.getDataTypeSeguidos().size());
	    assertTrue(detalleSanti.getDataTypeSeguidos().stream()
	        .anyMatch(u -> u.equals("user02")));
	    assertTrue(detalleSanti.getDataTypeSeguidos().stream()
	    		.anyMatch(u -> u.equals("orgPruebas")));
	    assertTrue(detalleSanti.getDataTypeSeguidos().stream()
	    		.anyMatch(u -> u.equals("user01")));
	}
	@Test
	void testSeguirUsuarioNoExiste() {
		assertThrows(UsuarioNoExiste.class, () -> controladorUsuarios.seguir("santib","userNoExiste"));
	}
	@Test
	void testDejarDeSeguirUsuarioNoExiste() {
		assertThrows(Exception.class, () -> controladorUsuarios.dejarDeSeguir("santib","userNoExiste"));
	}
	@Test
	void testDejarDeSeguir() {
		assertDoesNotThrow(()-> controladorUsuarios.seguir("santib", "user01"));
		assertDoesNotThrow(()-> controladorUsuarios.dejarDeSeguir("santib", "user01"));
	}
	@Test
	void testDejarDeSeguirUsuarioNoSeguido() {
		DTDetalleAsistente detalleSanti = (DTDetalleAsistente) assertDoesNotThrow(() -> controladorUsuarios.mostrarDetalleUsuario("santib"));
		assertFalse(detalleSanti.getDataTypeSeguidos().stream()
		        .anyMatch(u -> u.equals("orgPruebas")));
		assertDoesNotThrow(()-> controladorUsuarios.dejarDeSeguir("santib", "orgPruebas"));
	}
}
