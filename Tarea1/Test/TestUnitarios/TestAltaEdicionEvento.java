package TestUnitarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.Test;

import logica.Fabrica;
import logica.IControladorEventos; 
import logica.Utilidades;
import datatypes.DTEdicionEvento;

class TestAltaEdicionEvento {
    private Fabrica fabrica = Fabrica.getInstancia();
    private IControladorEventos controlador = fabrica.getControladorEvento();
    
    public IControladorEventos getControlador() {
    	return Fabrica.getInstancia().getControladorEvento();
    }
    
    public Fabrica getFabrica() {
    	return Fabrica.getInstancia();
    }

    @Test
    void altaEdicionEventoValida() throws Exception {
    	controlador.altaEvento("ExpoTech1", "Feria de tecnología", Utilidades.toDate(LocalDate.of(2023, 5, 10)), "EXP", Collections.emptyList(), null);
        String nombreEdicion = "ExpoTech2025";
        String sigla = "ET25";
        String ciudad = "Montevideo";
        String pais = "Uruguay";
        Date fechaAlta = Utilidades.toDate(LocalDate.of(2025, 8, 29));
        Date fechaInicio = Utilidades.toDate(LocalDate.of(2025, 9, 10));
        Date fechaFin = Utilidades.toDate(LocalDate.of(2025, 9, 12));
        String organizador = "Organizador3";

        assertDoesNotThrow(() -> {
            controlador.altaEdicionEvento("ExpoTech1", nombreEdicion, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais, organizador, null);
        });

        DTEdicionEvento dt = controlador.getDTEdicion("ExpoTech1", nombreEdicion);
        assertNotNull(dt);
        assertEquals(nombreEdicion, dt.getNombre());
        assertEquals(sigla, dt.getSigla());
        assertEquals(ciudad, dt.getCiudad());
        assertEquals(pais, dt.getPais());
        assertEquals(fechaInicio, dt.getFechaInicio());
        assertEquals(fechaAlta, dt.getFechaAlta());
        assertEquals(fechaFin, dt.getFechaFin());
        assertEquals(organizador, dt.getOrganizadorNickname());
    }

    @Test
    void altaEdicionConCamposVacios() {
        Exception ex = assertThrows(Exception.class, () -> {
            controlador.altaEdicionEvento("ExpoTech1", "", "", Utilidades.toDate(LocalDate.of(2025, 8, 29)), Utilidades.toDate(LocalDate.of(2025, 9, 10)), Utilidades.toDate(LocalDate.of(2025, 9, 12)), "", "", "", null);
        });
        assertTrue(ex.getMessage().contains("Todos los campos deben estar completos"));
    }

    @Test
    void fechaFinAntesDeInicio() {
        Exception ex = assertThrows(Exception.class, () -> {
            controlador.altaEdicionEvento("ExpoTech1", "ExpoTech2026", "ET26", Utilidades.toDate(LocalDate.of(2025, 10, 30)), Utilidades.toDate(LocalDate.of(2025, 9, 30)), Utilidades.toDate(LocalDate.of(2025, 8, 29)), "Montevideo", "Uruguay", "organizador4", null);
        });
        assertTrue(ex.getMessage().contains("La fecha de fin no puede ser anterior a la fecha de inicio"));
    }
 
    @Test
    void fechaAltaPosteriorAInicioYFin() {
        Exception ex = assertThrows(Exception.class, () -> {
            controlador.altaEdicionEvento("ExpoTech1", "ExpoTech2027", "ET27", Utilidades.toDate(LocalDate.of(2025, 9, 10)), Utilidades.toDate(LocalDate.of(2025, 9, 15)), Utilidades.toDate(LocalDate.of(2025, 9, 20)), "Montevideo", "Uruguay", "organizador5", null);
        });
        assertTrue(ex.getMessage().contains("La fecha de alta no puede ser posterior a la fecha de inicio ni a la fecha de fin"));
    }

    @Test
    void ciudadYPaisConNumerosYFechasInvalidas() throws Exception {
        // Ciudad con números
        controlador.altaEvento("ExpoTech1", "Feria de tecnología", Utilidades.toDate(LocalDate.of(2023, 5, 10)), "EXP", Collections.emptyList(), null);
        Exception ex1 = assertThrows(Exception.class, () -> {
            controlador.altaEdicionEvento("ExpoTech1", "ExpoTech2028", "ET28", Utilidades.toDate(LocalDate.of(2025, 9, 10)), Utilidades.toDate(LocalDate.of(2025, 9, 12)), Utilidades.toDate(LocalDate.of(2025, 8, 29)), "Montevideo123", "Uruguay", "organizador6", null);
        });
        assertTrue(ex1.getMessage().contains("La ciudad solo puede contener letras y espacios"));

        // País con números
        Exception ex2 = assertThrows(Exception.class, () -> {
            controlador.altaEdicionEvento("ExpoTech1", "ExpoTech2029", "ET29", Utilidades.toDate(LocalDate.of(2025, 9, 10)), Utilidades.toDate(LocalDate.of(2025, 9, 12)), Utilidades.toDate(LocalDate.of(2025, 8, 29)), "Montevideo", "Uruguay2025", "organizador7", null);
        });
        assertTrue(ex2.getMessage().contains("El país solo puede contener letras y espacios"));

        // Fecha de fin antes que inicio
        Exception ex3 = assertThrows(Exception.class, () -> {
            controlador.altaEdicionEvento("ExpoTech1", "ExpoTech2030", "ET30", Utilidades.toDate(LocalDate.of(2025, 10, 10)), Utilidades.toDate( LocalDate.of(2025, 10, 5)), Utilidades.toDate(LocalDate.of(2025, 9, 1)), "Montevideo", "Uruguay", "organizador8", null);
        });
        assertTrue(ex3.getMessage().contains("La fecha de fin no puede ser anterior a la fecha de inicio"));

     
	    // Nombre de edición ya existente
	    // Primero se registra una edición válida
	    assertDoesNotThrow(() -> {
	        controlador.altaEdicionEvento("ExpoTech1", "ExpoTech2032", "ET32", Utilidades.toDate(LocalDate.of(2025, 10, 10)), Utilidades.toDate(LocalDate.of(2025, 10, 12)), Utilidades.toDate(LocalDate.of(2025, 9, 1)), "Montevideo", "Uruguay", "organizador9", null);
	    });
	
	    // Luego se intenta registrar otra con el mismo nombre
	    Exception ex5 = assertThrows(Exception.class, () -> {
	        controlador.altaEdicionEvento("ExpoTech1", "ExpoTech2032", "ET33", Utilidades.toDate(LocalDate.of(2025, 11, 10)), Utilidades.toDate(LocalDate.of(2025, 11, 12)), Utilidades.toDate(LocalDate.of(2025, 9, 5)), "Montevideo", "Uruguay", "organizador10", null);
	    });
	    assertTrue(ex5.getMessage().contains("El nombre de la edición ya está en uso"));
    }
}

