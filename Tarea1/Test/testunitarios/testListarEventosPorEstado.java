package testunitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import datatypes.DTEvento;
import datatypes.EEstadoEvento;
import logica.Fabrica;
import logica.Utilidades;

public class testListarEventosPorEstado {
    private static Fabrica fabrica;
    
    @BeforeEach
    void init() {
        Fabrica.resetInstancia();
        fabrica = Fabrica.getInstancia();
        
        // Alta Categoría
        assertDoesNotThrow(() -> {
            fabrica.getControladorEvento().altaCategoria("Categoria1");
        });
        
        List<String> listaCategorias = new ArrayList<>();
        listaCategorias.add("Categoria1");
        
        // eventos en curso
        fabrica.getControladorEvento().altaEvento(
            "EventoEnCurso1", 
            "Descripcion evento en curso 1", 
            Utilidades.toDate(LocalDate.now().minusDays(30)), 
            "SIG_EC1", 
            listaCategorias, 
            null, 
            null, 
            0
        );
        
        fabrica.getControladorEvento().altaEvento(
            "EventoEnCurso2", 
            "Descripcion evento en curso 2", 
            Utilidades.toDate(LocalDate.now().minusDays(15)), 
            "SIG_EC2", 
            listaCategorias, 
            null, 
            null, 
            0
        );
        
        // eventos finalizados
        fabrica.getControladorEvento().altaEvento(
            "EventoFinalizado1", 
            "Descripcion evento finalizado 1", 
            Utilidades.toDate(LocalDate.of(2023, 1, 1)), 
            "SIG_EF1", 
            listaCategorias, 
            null, 
            null, 
            0
        );
        
        fabrica.getControladorEvento().altaEvento(
            "EventoFinalizado2", 
            "Descripcion evento finalizado 2", 
            Utilidades.toDate(LocalDate.of(2022, 6, 15)), 
            "SIG_EF2", 
            listaCategorias, 
            null, 
            null, 
            0
        );
    }
    
    @Test
    void testListarEventosEnCurso() {
        Set<DTEvento> resultado = fabrica.getControladorEvento().listarEventosEnCurso();
        
        // verificar que hay al menos los eventos en curso creados
        assertTrue(resultado.size() >= 2);
        
        // verificar que contiene los eventos esperados
        boolean contieneEvento1 = false;
        boolean contieneEvento2 = false;
        
        for (DTEvento evento : resultado) {
            if (evento.getNombre().equals("EventoEnCurso1")) {
                contieneEvento1 = true;
            }
            if (evento.getNombre().equals("EventoEnCurso2")) {
                contieneEvento2 = true;
            }
        }
        
        assertTrue(contieneEvento1);
        assertTrue(contieneEvento2);
    }
    
    
    @Test
    void testListarEventosPorEstadoEnCurso() {
        Set<DTEvento> resultado = fabrica.getControladorEvento().listarEventosPorEstado(EEstadoEvento.EN_CURSO);
        
        assertTrue(resultado.size() >= 2);
        
        for (DTEvento evento : resultado) {
            // los eventos en curso deberian estar en la lista
            assertTrue(evento.getNombre().contains("EnCurso") || evento.getEstado() == EEstadoEvento.EN_CURSO);
        }
    }
    
    @Test
    void testEventosEnCursoNoContienenFinalizados() {
        Set<DTEvento> eventosEnCurso = fabrica.getControladorEvento().listarEventosEnCurso();
        
        // verificar que ningun evento finalizado este en la lista de eventos en curso
        for (DTEvento evento : eventosEnCurso) {
            assertTrue(!evento.getNombre().contains("Finalizado"));
        }
    }
}
