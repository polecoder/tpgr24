package testunitarios;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.DTEventoVisitas;
import logica.Fabrica;
import logica.Utilidades;

public class TestEventosMasVisitados {
    
    private static Fabrica fabrica;
    
    @BeforeEach
    void init() {
        Fabrica.resetInstancia();
        fabrica = Fabrica.getInstancia();
        
        assertDoesNotThrow(() -> {
            fabrica.getControladorEvento().altaCategoria("Categoria1");
        });
    }
    
    @Test
    void testSinEventos() {
        // no hay eventos en el sistema
        List<DTEventoVisitas> resultado = fabrica.getControladorEvento().obtenerEventosMasVisitados(5);
        
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }
    
    @Test
    void testUnSoloEvento() {
        // solo hay 1 evento
        crearEvento("Evento1", 100);
        
        List<DTEventoVisitas> resultado = fabrica.getControladorEvento().obtenerEventosMasVisitados(5);
        
        assertEquals(1, resultado.size());
        assertEquals("Evento1", resultado.get(0).getNombre());
        assertEquals(100, resultado.get(0).getVisitas());
    }
    
    @Test
    void testMenosDe5Eventos() {
        // hay menos de 5 eventos (debería devolver todos)
        crearEvento("Evento1", 50);
        crearEvento("Evento2", 30);
        crearEvento("Evento3", 80);
        
        List<DTEventoVisitas> resultado = fabrica.getControladorEvento().obtenerEventosMasVisitados(5);
        
        assertEquals(3, resultado.size(), "Debería devolver los 3 eventos que existen");
    }
    
    @Test
    void testOrdenDescendenteVisitas() {
        // verificar que están ordenados de mayor a menor visitas
        crearEvento("EventoA", 100);
        crearEvento("EventoB", 500);
        crearEvento("EventoC", 300);
        crearEvento("EventoD", 50);
        crearEvento("EventoE", 200);
        
        List<DTEventoVisitas> resultado = fabrica.getControladorEvento().obtenerEventosMasVisitados(5);
        
        assertEquals(5, resultado.size());
        
        assertEquals("EventoB", resultado.get(0).getNombre()); // 500
        assertEquals("EventoC", resultado.get(1).getNombre()); // 300
        assertEquals("EventoE", resultado.get(2).getNombre()); // 200
        assertEquals("EventoA", resultado.get(3).getNombre()); // 100
        assertEquals("EventoD", resultado.get(4).getNombre()); // 50
        
    }
    
    @Test
    void testMasDe5EventosSoloDevuelve5() {
        // si hay más de 5 eventos
        crearEvento("Evento1", 100);
        crearEvento("Evento2", 200);
        crearEvento("Evento3", 300);
        crearEvento("Evento4", 400);
        crearEvento("Evento5", 500);
        crearEvento("Evento6", 50);  
        crearEvento("Evento7", 25);  
        
        List<DTEventoVisitas> resultado = fabrica.getControladorEvento().obtenerEventosMasVisitados(5);
        
        assertEquals(5, resultado.size(), "Debería devolver exactamente 5 eventos");
        
        // verificar que son los 5 con más visitas
        assertEquals("Evento5", resultado.get(0).getNombre());
        assertEquals("Evento4", resultado.get(1).getNombre());
        assertEquals("Evento3", resultado.get(2).getNombre());
        assertEquals("Evento2", resultado.get(3).getNombre());
        assertEquals("Evento1", resultado.get(4).getNombre());
        
        // verificar que no están los de menos visitas
        boolean contieneEvento6 = resultado.stream().anyMatch(e -> e.getNombre().equals("Evento6"));
        boolean contieneEvento7 = resultado.stream().anyMatch(e -> e.getNombre().equals("Evento7"));
        assertFalse(contieneEvento6, "No debería incluir Evento6");
        assertFalse(contieneEvento7, "No debería incluir Evento7");
    }
    
    @Test
    void testEmpateMismasVisitasOrdenAlfabetico() {
        // eventos con mismo número de visitas deben ordenarse alfabéticamente
        crearEvento("Z", 100);
        crearEvento("A", 100);
        crearEvento("B", 100);
        
        List<DTEventoVisitas> resultado = fabrica.getControladorEvento().obtenerEventosMasVisitados(5);
        
        assertEquals(3, resultado.size());
        
        // todos tienen 100 visitas, deberían estar en orden alfabético
        assertEquals("A", resultado.get(0).getNombre());
        assertEquals("B", resultado.get(1).getNombre());
        assertEquals("Z", resultado.get(2).getNombre());
        
        // verificar que todos tienen las mismas visitas
        assertEquals(100, resultado.get(0).getVisitas());
        assertEquals(100, resultado.get(1).getVisitas());
        assertEquals(100, resultado.get(2).getVisitas());
    }
    
    @Test
    void testEmpateParcialMismasVisitas() {
        // algunos eventos empatan y otros no
        crearEvento("Evento1", 500);
        crearEvento("Z", 300);
        crearEvento("A", 300);  
        crearEvento("B", 300);   
        crearEvento("Evento5", 100);
        
        List<DTEventoVisitas> resultado = fabrica.getControladorEvento().obtenerEventosMasVisitados(5);
        
        assertEquals(5, resultado.size());
        
        assertEquals("Evento1", resultado.get(0).getNombre());
        assertEquals(500, resultado.get(0).getVisitas());
        
        //mismas visitas
        assertEquals("A", resultado.get(1).getNombre());
        assertEquals(300, resultado.get(1).getVisitas());
        
        assertEquals("B", resultado.get(2).getNombre());
        assertEquals(300, resultado.get(2).getVisitas());
        
        assertEquals("Z", resultado.get(3).getNombre());
        assertEquals(300, resultado.get(3).getVisitas());
        
        assertEquals("Evento5", resultado.get(4).getNombre());
        assertEquals(100, resultado.get(4).getVisitas());
    }
    
    @Test
    void testEventosConCeroVisitas() {
        // eventos sin visitas
        crearEvento("EventoA", 0);
        crearEvento("EventoB", 0);
        crearEvento("EventoC", 10);
        
        List<DTEventoVisitas> resultado = fabrica.getControladorEvento().obtenerEventosMasVisitados(5);
        
        assertEquals(3, resultado.size());
        
        // el de 10 visitas primero
        assertEquals("EventoC", resultado.get(0).getNombre());
        assertEquals(10, resultado.get(0).getVisitas());
        
        // los de 0 visitas después, ordenados alfabéticamente
        assertEquals("EventoA", resultado.get(1).getNombre());
        assertEquals("EventoB", resultado.get(2).getNombre());
    }
    
    @Test
    void testLimiteIgnoradoSiempreDevuelve5() {
    	
        // verificar que el límite siempre es 5 (esta hardcodeado en la función)
        crearEvento("Evento1", 100);
        crearEvento("Evento2", 200);
        crearEvento("Evento3", 300);
        crearEvento("Evento4", 400);
        crearEvento("Evento5", 500);
        crearEvento("Evento6", 600);
        crearEvento("Evento7", 700);
        
        // aunque pida limite 10, deberia devolver solo 5
        List<DTEventoVisitas> resultado = fabrica.getControladorEvento().obtenerEventosMasVisitados(10);
        
        assertEquals(5, resultado.size());
    }
    
    @Test
    void testNombresConMayusculasMinusculas() {
        // orden alfabetico insensible a mayusculas/minusculas
        crearEvento("z", 100);
        crearEvento("A", 100);
        crearEvento("B", 100);
        
        List<DTEventoVisitas> resultado = fabrica.getControladorEvento().obtenerEventosMasVisitados(5);
        
        // debería ordenar alfabéticamente sin importar mayúsculas
        assertEquals("A", resultado.get(0).getNombre());
        assertEquals("B", resultado.get(1).getNombre());
        assertEquals("z", resultado.get(2).getNombre());
    }
    
    //para no hacerlo todas las veces
    private void crearEvento(String nombre, int visitas) {
        List<String> categorias = new ArrayList<>();
        categorias.add("Categoria1");
        
        fabrica.getControladorEvento().altaEvento(
            nombre,
            "Descripción de " + nombre,
            Utilidades.toDate(LocalDate.of(2024, 12, 1)),
            "SIG",
            categorias,
            null,
            null,
            visitas
        );
    }
}