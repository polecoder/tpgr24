package testunitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import datatypes.EEstadoEvento;
import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorUsuarios;
import logica.Utilidades;

public class TestArchivarYFinalizar {
	 private Fabrica fabrica = Fabrica.getInstancia();
	 private IControladorEventos controlador = fabrica.getControladorEvento();
	 private IControladorUsuarios controladorUsuario = fabrica.getControladorUsuarios();
	 private List<String> categorias = new ArrayList<>();
	
	 @Test
	 @Order(1)
	 void ArchivarEdicion() {
		 assertDoesNotThrow(() -> { 
			 controladorUsuario.altaOrganizador("lucasM", "Eventos.2025!", "Lucas", "lucas.martinez@gmail.com", "Encargado de eventos", "https://lucasorganiza.com", null);
		 }); 
		
		 categorias.add("Categoria 1");
		 controlador.altaEvento("EventoPrueba1","prueba",Utilidades.toDate(LocalDate.of(2023, 5, 10)),"EP1",categorias, null,EEstadoEvento.EN_CURSO, 0);
		 
		 assertDoesNotThrow(() -> {
			controlador.altaEdicionEvento("EventoPrueba1", "EdicionPruebita1","EdP1", Utilidades.toDate(LocalDate.of(2025, 9, 10)),  Utilidades.toDate(LocalDate.of(2025, 12, 9)), Utilidades.toDate(LocalDate.of(2025, 8, 29)), "ciudad", "pais", "lucasM", null,null);
		 });
		 
		 
		 assertDoesNotThrow(() -> {
			 controlador.archivarEdicion("EventoPrueba1","EdicionPruebita1", new Date());
		 });
		 
	 }	
	 

	@Test
	 @Order(2)
	void ArchivarEdicionNoExistente() {
		 Exception excepcion1 = assertThrows(Exception.class, () -> {
			 controlador.archivarEdicion("EventoPrueba1","Edi", new Date());
		 });
		 assertTrue(excepcion1.getMessage().contains("La edición seleccionada no existe en el sistema"));
	}	 
	
	@Test
	 @Order(3)
	void ArchivarEdicionYaFinalizada() {
		 assertDoesNotThrow(() -> {
			controlador.altaEdicionEvento("EventoPrueba1", "EdicionPrueba2","EdP1", Utilidades.toDate(LocalDate.of(2025, 9, 10)),  Utilidades.toDate(LocalDate.of(2025, 9, 12)), Utilidades.toDate(LocalDate.of(2025, 8, 29)), "ciudad", "pais", "lucasM", null,null);
		 });
		 
		 Exception excepcion2 = assertThrows(Exception.class, () -> {
			 controlador.archivarEdicion("EventoPrueba1","EdicionPrueba2", new Date());
		 });
		 assertTrue(excepcion2.getMessage().contains("La edición seleccionada ya finalizó"));
     }
	

@Test 
@Order(4)
	void FinalizarEventos() {
	assertDoesNotThrow(() -> {controlador.finalizarEvento("EventoPrueba1");
	});
	Exception excepcion3 = assertThrows(Exception.class, () -> {
		controlador.finalizarEvento("EventoPrueba1");
	 });
	 assertTrue(excepcion3.getMessage().contains("El evento ya finalizo"));
	
	}

}
