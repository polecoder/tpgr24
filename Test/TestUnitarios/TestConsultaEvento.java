package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import logica.Fabrica;
import logica.IControladorEventos;
import logica.Utilidades;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;

class TestConsultaEvento {
    Fabrica fabrica = Fabrica.getInstancia();
    IControladorEventos controlador = fabrica.getControladorEvento();

    @Test
    void consultaEventoCompleta() throws Exception {
        // Crear categorías necesarias
        controlador.altaCategoria("Robótica");
        controlador.altaCategoria("IA");


        List<String> categorias = Arrays.asList("Robótica", "IA");

        controlador.altaEvento("ExpoTech", "Feria de tecnología", LocalDate.of(2023, 5, 10), "EXP", categorias);
        controlador.AltaEdicionEvento("ExpoTech", "ExpoTech2023", "ET2023", LocalDate.of(2023, 9, 1), LocalDate.of(2023, 12, 1), LocalDate.of(2023, 8, 3),"Montevideo", "Uruguay");

        Set<String> nombres = controlador.nombresEventos();
        assertTrue(nombres.contains("ExpoTech"));
	  ControladorEventos controlador = ControladorEventos.getInstance();
	   ManejadorEventos manejador = ManejadorEventos.getInstancia();
	@Test
	void consultaEventoCompleta() throws Exception {
	  
	    // Simulación de alta de evento
	    Evento evento = new Evento("ExpoTech", "Feria de tecnología", "EXP", Utilidades.toDate(LocalDate.of(2023, 5, 10)));
	    manejador.agregarEvento(evento);
 
	    // Simulación de categorías
	    Categoria cat1= new Categoria("Robótica");
	    Categoria cat2= new Categoria("IA");
	    evento.agregarCategoria(cat1); 
	    evento.agregarCategoria(cat2); 

	    // Simulación de ediciones
	    Edicion edicion2023 = new Edicion("ExpoTech2023", "ET2023", Utilidades.toDate(LocalDate.of(2023, 9, 1)), Utilidades.toDate(LocalDate.of(2023, 12, 1)), Utilidades.toDate(LocalDate.of(2023, 8, 3)),"Montevideo","Uruguay");
	    evento.agregarEdicion(edicion2023);

	    // Validación: nombres de eventos
	    Set<String> nombres = controlador.nombresEventos();
	    assertTrue(nombres.contains("ExpoTech"));

	    // Validación: datos del evento
        DTEvento dtEvento = controlador.getDTEvento("ExpoTech");
        assertEquals("ExpoTech", dtEvento.getNombre());
        assertEquals("EXP", dtEvento.getSigla());
        assertEquals("Feria de tecnología", dtEvento.getDescripcion());

	    // Validación: categorías
        Set<String> categoriasEvento = controlador.nombresCategorias("ExpoTech");
        assertTrue(categoriasEvento.contains("Robótica"));
        assertTrue(categoriasEvento.contains("IA"));

	    // Validación: ediciones
        Set<String> ediciones = controlador.nombresEdicionesEvento("ExpoTech");
        assertTrue(ediciones.contains("ExpoTech2023"));

        DTEdicionEvento dtEdicion = controlador.getDTEdicion("ExpoTech", "ExpoTech2023");
        assertEquals("ExpoTech2023", dtEdicion.getNombre());
    }

    @Test
    void consultaEventoSinEdiciones() throws Exception {
        // Crear categorías necesarias
        controlador.altaCategoria("Robótica");
        controlador.altaCategoria("IA");

        List<String> categorias = Arrays.asList("Robótica", "IA");

        controlador.altaEvento("SoloCategorias", "Evento sin ediciones", LocalDate.of(2024, 2, 1), "SC", categorias);
	    // Validación: detalle de edición
	    DTEdicionEvento dtEdicion = controlador.getDTEdicion("ExpoTech", "ExpoTech2023");
	    assertEquals("ExpoTech2023", dtEdicion.getNombre());
	}
	//evento sin ediciones 
	@Test
	void consultaEventoSinEdiciones() throws Exception {	  

	    Evento evento2 = new Evento("SoloCategorias", "SC", "Evento sin ediciones", Utilidades.toDate(LocalDate.of(2024, 2, 1)));
	    Categoria cat3= new Categoria("Robótica");
	    Categoria cat4= new Categoria("IA");
	    evento2.agregarCategoria(cat3); 
	    evento2.agregarCategoria(cat4); 

	    manejador.agregarEvento(evento2);

        DTEvento dt = controlador.getDTEvento("SoloCategorias");
        assertEquals("SoloCategorias", dt.getNombre());

        Set<String> ediciones = controlador.nombresEdicionesEvento("SoloCategorias");
        assertTrue(ediciones.isEmpty());
    }

    @Test
    void eventoConMultiplesEdiciones() throws Exception {
        controlador.altaEvento("DataFest", "Festival de datos", Utilidades.toDate(LocalDate.of(2022, 4, 1), "DF", Arrays.asList()));

        controlador.AltaEdicionEvento("DataFest", "DataFest2022", "DF22", Utilidades.toDate(LocalDate.of(2022, 6, 1)), Utilidades.toDate(LocalDate.of(2022, 6, 3)), Utilidades.toDate(LocalDate.of(2022, 5, 1)),
                "Salto", "Uruguay");

        controlador.AltaEdicionEvento("DataFest", "DataFest2023", "DF23",Utilidades.toDate(LocalDate.of(2023, 6, 1)), Utilidades.toDate(LocalDate.of(2023, 6, 3)), Utilidades.toDate(LocalDate.of(2023, 5, 1)),
                "Paysandú", "Uruguay");

        Set<String> ediciones = controlador.nombresEdicionesEvento("DataFest");
        assertTrue(ediciones.contains("DataFest2022"));
        assertTrue(ediciones.contains("DataFest2023"));
    }
}
