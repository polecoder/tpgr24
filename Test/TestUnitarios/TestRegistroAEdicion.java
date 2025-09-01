package TestUnitarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorInstitucion;
import logica.IControladorUsuarios;
import logica.Utilidades;
import logica.datatypes.DTDetalleRegistro;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRegistroAEdicion {
	private static IControladorEventos controladorEventos;
	private static IControladorUsuarios controladorUsuarios;
	private static IControladorInstitucion controladorInstituciones;
	
	private static String nickOrganizador, nombreOrganizador, correo1, descripcion, link;
	private static String nickAsistente, nombreAsistente, apellido;
	private static String nombreEvento, sigla, nombreCategoria;
	private static Date fecha;
	private static List<String> categorias;
	private static String nombreEdicion, ciudad, pais, correo2;
	private static String nombreTipoRegistro;
	private static float costo;
	private static int cuposMax;
	
	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstancia();
		controladorEventos = fabrica.getControladorEvento();
		controladorUsuarios = fabrica.getControladorUsuarios();
		controladorInstituciones = fabrica.getControladorInstitucion();
		
		// Variables Generales
		nickOrganizador = "nickOrganizador";
		nombreOrganizador = "nombreOrganizador";
		correo1 = "nombreOrganizador@gamil.com";
		descripcion = "descripcion";
		link = "https://nickOrganizador.com";
		nickAsistente = "nickAsistente";
		nombreAsistente = "nombreAsistente";
		apellido = "apellido";
		fecha = Utilidades.toDate(LocalDate.now());
		nombreEvento = "nombreEvento";
		correo2 = "nombreAsistente@gmail.com";
		sigla = "SIGLA";
		nombreCategoria = "nombreCategoria";
		categorias = new ArrayList<>();
		categorias.add(nombreCategoria);
		ciudad = "Montevideo";
		pais = "Uruguay";
		nombreEdicion = "nombreEdicion";
		costo = 1f;
		cuposMax = 10;
		nombreTipoRegistro = "nombreTipoRegistro";
	}

	@Test
	@Order(1)
	void testRegistroExitosoSinPatrocinio() {
		// Testea un registro exitoso estandar sin patrocinio
		try { 
			controladorUsuarios.altaOrganizador(nickOrganizador, nombreOrganizador, correo1, descripcion, link);
			controladorUsuarios.altaAsistente(nickAsistente, nombreAsistente, correo2, apellido, fecha, null);
			controladorEventos.altaEvento(nombreEvento, descripcion, fecha, sigla, categorias);
			controladorEventos.altaEdicionEvento(nombreEvento, nombreEdicion, sigla, fecha, fecha, fecha, ciudad, pais, "organizador18");
			controladorEventos.altaTipoRegistro(nombreTipoRegistro, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
		} catch (Exception ex) { fail(ex.getMessage()); }
		
		controladorEventos.altaRegistro(nickAsistente, nombreTipoRegistro, nombreEdicion, nombreEvento, null, fecha);
		DTDetalleRegistro dtRegistroGenerado = controladorUsuarios.obtenerDetalleRegistro(nickAsistente, 0);
		DTDetalleRegistro dtRegistroEsperado = new DTDetalleRegistro(nombreEdicion, nickAsistente, nombreTipoRegistro, fecha, costo);
		assertTrue(dtRegistroEsperado.equals(dtRegistroGenerado));
	
	}
}
	
