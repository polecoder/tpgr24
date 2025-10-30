package testunitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorInstitucion;
import logica.IControladorUsuarios;
import logica.Utilidades;
import datatypes.DTDetalleRegistro;
import datatypes.ENivelPatrocinio;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRegistroAEdicion {
	private static IControladorEventos controladorEventos;
	private static IControladorUsuarios controladorUsuarios;
	private static IControladorInstitucion controladorInstituciones;
	
	private static String nickOrganizador, contrasenaOrganizador, nombreOrganizador, correo1, descripcion, link;
	private static String nickAsistente, contrasenaAsistente, nombreAsistente, apellido;
	private static String nombreEvento, sigla, nombreCategoria;
	private static Date fecha;
	private static List<String> categorias;
	private static String nombreEdicion, ciudad, pais, correo2;
	private static String nombreTipoRegistro;
	private static float costo;
	private static int cuposMax;
	
	@BeforeEach
	public void iniciar() {
		Fabrica.resetInstancia();
		Fabrica fabrica = Fabrica.getInstancia();
		controladorEventos = fabrica.getControladorEvento();
		controladorUsuarios = fabrica.getControladorUsuarios();
		controladorInstituciones = fabrica.getControladorInstitucion();
		
		// Variables Generales
		nickOrganizador = "nickOrganizador";
		contrasenaOrganizador = "Eventos.2025!";
		nombreOrganizador = "nombreOrganizador";
		correo1 = "nombreOrganizador@gamil.com";
		descripcion = "descripcion";
		link = "https://nickOrganizador.com";
		nickAsistente = "nickAsistente";
		contrasenaAsistente = "Eventos.2025!";
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

	// Testea un registro exitoso estandar sin patrocinio
	@Test
	@Order(1)
	void testRegistroExitosoSinPatrocinio() {
		
		assertDoesNotThrow(() -> {
			controladorUsuarios.altaOrganizador(nickOrganizador, contrasenaOrganizador, nombreOrganizador, correo1, descripcion, link, null);
			controladorUsuarios.altaAsistente(nickAsistente, contrasenaAsistente, nombreAsistente, correo2, apellido, fecha, null, null);
			controladorEventos.altaEvento(nombreEvento, descripcion, fecha, sigla, categorias, null, null, 0);
			controladorEventos.altaEdicionEvento(nombreEvento, nombreEdicion, sigla, fecha, Utilidades.toDate(LocalDate.of(2050, 11, 10)), fecha, ciudad, pais, "organizador18", null, null);
			controladorEventos.altaTipoRegistro(nombreTipoRegistro, descripcion, costo, cuposMax, nombreEvento, nombreEdicion); 
			controladorEventos.altaRegistro(nickAsistente, nombreTipoRegistro, nombreEdicion, nombreEvento, null, fecha);
		});
		
		DTDetalleRegistro dtRegistroGenerado = controladorUsuarios.obtenerDetalleRegistro(nickAsistente, 0);
		DTDetalleRegistro dtRegistroEsperado = new DTDetalleRegistro(nombreEdicion, nickAsistente, nombreTipoRegistro, fecha, costo, null, false);
		// TODO: Chequear que dtRegistroEsperado sea igual dtRegistroGenerado
	}
	
	// Testea un registro exitoso estandar sin patrocinio
	@Test
	@Order(2)
	void testRegistroExitosoConPatrocinio() {
		
		assertDoesNotThrow(() -> {
			controladorUsuarios.altaOrganizador(nickOrganizador, contrasenaOrganizador, nombreOrganizador, correo1, descripcion, link, null);
			controladorInstituciones.altaInstitucion("Institucion", "Descripcion1", "https://insti.com");
			controladorUsuarios.altaAsistente(nickAsistente, contrasenaAsistente, nombreAsistente, correo2, apellido, fecha, "Institucion", null);
			controladorEventos.altaEvento(nombreEvento, descripcion, fecha, sigla, categorias, null, null, 0);
			controladorEventos.altaEdicionEvento(nombreEvento, nombreEdicion, sigla, fecha, Utilidades.toDate(LocalDate.of(2050, 11, 10)), fecha, ciudad, pais, "organizador18", null, null);
			controladorEventos.altaTipoRegistro(nombreTipoRegistro, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
			controladorEventos.altaPatrocinio(nombreEvento, nombreEdicion, Utilidades.toDate(LocalDate.of(2024, 11, 10)), "Institucion", ENivelPatrocinio.Oro, 10000f, 1, "CODPAT1", nombreTipoRegistro);
			controladorEventos.altaRegistro(nickAsistente, nombreTipoRegistro, nombreEdicion, nombreEvento, "CODPAT1", fecha);
		});
		DTDetalleRegistro dtRegistroGenerado = controladorUsuarios.obtenerDetalleRegistro(nickAsistente, 0);
		DTDetalleRegistro dtRegistroEsperado = new DTDetalleRegistro(nombreEdicion, nickAsistente, nombreTipoRegistro, fecha, 0, null, false);
		// TODO: comparar registro esperado con el generado
	}
}
	
