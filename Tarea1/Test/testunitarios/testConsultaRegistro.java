package testunitarios;

import org.junit.jupiter.api.Test;

import excepciones.EventoNoExiste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;


import logica.TipoRegistro;
import logica.Utilidades;
import logica.Patrocinio;
import logica.ControladorEventos;
import logica.ControladorUsuarios;
import logica.Edicion;
import logica.Evento;
import logica.ManejadorEventos;
import logica.ManejadorUsuarios;
import datatypes.DTDetalleRegistro;
//import datatypes.DTEdicionEvento;
import datatypes.DTEvento;
import datatypes.DTRegistro;
import datatypes.DTUsuario;
import logica.Asistente;
import logica.Registro;

class testConsultaRegistro {
	@Test
	void test() {
		
		ControladorEventos controlador = ControladorEventos.getInstance();
		   ManejadorEventos manejador = ManejadorEventos.getInstancia(); 
		   
		//creo evento
		Evento evento = new Evento("ExpoTech", "Feria de tecnología", "EXP", Utilidades.toDate(LocalDate.of(2023, 5, 10)), null, null, 0);
		manejador.agregarEvento(evento);
		
		//creo edicion
		
		Edicion ed1 = new Edicion("Expo", "EXP", Utilidades.toDate(LocalDate.of(2023, 9, 1)), Utilidades.toDate(LocalDate.of(2023, 12, 1)), Utilidades.toDate(LocalDate.now()), "Montevideo", "Uruguay", "organizador1", evento.getNombre(), null, null);
		evento.agregarEdicion(ed1);
		
		//creo Tiporegistro
		TipoRegistro Gen = new TipoRegistro("General", "Acceso General", 100, 200, 500);
		TipoRegistro Vip = new TipoRegistro("Vip", "Acceso vip", 100, 300, 200);
		
		//asociar registro a edicion
		ed1.agregarTipoRegistro(Vip);
		ed1.agregarTipoRegistro(Gen);
		
		//crear asistente
		Asistente ana = new Asistente("ana", "Eventos.2025!", "Ana", "Perez", "ana@mail.com", Utilidades.toDate(LocalDate.now()), null);
		
		//creo Registro
		Registro reg1 = new Registro(Utilidades.toDate(LocalDate.now()), 100, ana, null,ed1,Gen.getNombre());
		ana.getRegistros().add(reg1);
		
		DTEvento dtEv = controlador.getDTEvento("ExpoTech"); // ¡Evento, no "Expo"!
        assertNotNull(dtEv, "El evento debería existir en el controlador");
        assertEquals("ExpoTech", dtEv.getNombre());

        // B) El evento debe listar la EDICIÓN "Expo"
        Set<String> ediciones = Collections.emptySet();
        try { ediciones = controlador.nombresEdicionesEvento("ExpoTech");
        } catch (EventoNoExiste ex) {
        	fail("Esperabamos que el evento exista (B)");
        	return;
        }
        assertTrue(ediciones.contains("Expo"), "El evento debería listar la edición 'Expo'");

        // C) La edición debe tener ambos tipos de registro
        assertTrue(ed1.getTiposRegistro().containsKey("Vip"),     "Falta tipo 'Vip'");
        assertTrue(ed1.getTiposRegistro().containsKey("General"), "Falta tipo 'General'");

        // D) El asistente debe tener exactamente 1 registro y con costo 100
        assertEquals(1, ana.getRegistros().size(), "El asistente debería tener 1 registro");
        Registro registro = ana.getRegistros().get(0);
        assertEquals(100f, registro.getCosto(), 0.001f);
        
	}
	
	@Test
	void listarRegistrosUsuario() {
	    // Controladores / manejadores
	    ControladorUsuarios controladorusuarios = ControladorUsuarios.getInstance();
	    ManejadorUsuarios  manejadorusuarios = ManejadorUsuarios.getInstance();
	    ManejadorEventos   manejadoreventos = ManejadorEventos.getInstancia();

	    // --- Asistente ---
	    Asistente ana = new Asistente("anaCR", "Eventos.2025!", "Ana", "Perez", "anaCR@mail.com", Utilidades.toDate(LocalDate.of(1999, 5, 1)), null);
	    manejadorusuarios.getConjuntoAsistentes().put(ana.getNickname(), ana);

	    // --- Evento + Edición ---
	    Evento evento = new Evento("ExpoTech", "EXP", "Feria de tecnología", Utilidades.toDate(LocalDate.of(2023, 5, 10)), null, null, 0);
	    manejadoreventos.agregarEvento(evento);

	    Edicion edicion = new Edicion("Expo", "EXP",
	            Utilidades.toDate(LocalDate.of(2023, 9, 1)),
	            Utilidades.toDate(LocalDate.of(2023, 12, 1)),
	            Utilidades.toDate(LocalDate.now()), "Montevideo", "Uruguay", "organizador1", evento.getNombre(), null, null);
	    evento.agregarEdicion(edicion);

	    // --- Tipos de registro ---
	    TipoRegistro gen = new TipoRegistro("General", "Acceso General", 100, 200, 500);
	    TipoRegistro vip = new TipoRegistro("Vip",     "Acceso vip",     250, 300, 200);
	    edicion.agregarTipoRegistro(vip);
	    edicion.agregarTipoRegistro(gen);

	    // --- Patrocinio + Registros del usuario ---
	    
	    Patrocinio pat = new Patrocinio("Patro", Utilidades.toDate(LocalDate.now()), 0, null, 0, null, null);
	    Registro registro1 = new Registro(Utilidades.toDate(LocalDate.now()), 100f, ana, pat,edicion,gen.getNombre());
	    Registro registro2 = new Registro(Utilidades.toDate(LocalDate.now().plusDays(1)), 250f, ana, pat,edicion,vip.getNombre());
	    
	    gen.addRegistro(registro1);
	    vip.addRegistro(registro2);

	    ana.getRegistros().add(registro1);
	    ana.getRegistros().add(registro2);
	    

	    // --- ACT: listar registros del usuario ---
	    java.util.List<datatypes.DTRegistro> regs = controladorusuarios.listarRegistrosUsuario("anaCR");

	    // --- ASSERTS ---
	    assertNotNull(regs);
	    assertEquals(2, regs.size(), "Debe listar 2 registros");

	    java.util.Set<String> tipos = regs.stream()
	            .map(datatypes.DTRegistro::getNombreTipo)
	            .collect(java.util.stream.Collectors.toSet());
	    
	    Set<String> tipos1 = regs.stream()
	            .map(DTRegistro::getNombreTipo)
	            .filter(java.util.Objects::nonNull)
	            .map(s -> s.trim().toLowerCase())
	            .collect(java.util.stream.Collectors.toSet());

	    assertEquals(java.util.Set.of("general", "vip"), tipos1, "Tipos esperados en la lista");

	    // Detalle índice 0 (sin asumir orden: solo validamos coherencia)
	    datatypes.DTDetalleRegistro datatypes = controladorusuarios.obtenerDetalleRegistro("anaCR", 0);
	    assertNotNull(datatypes);
	    assertEquals("anaCR", datatypes.getNicknameAsistente());
	    assertTrue(java.util.Set.of("General", "Vip").contains(datatypes.getNombreTipo()));
	}
	
	@Test
	void obtenerDetalleRegistrousuarioSinRegistroseindicesInvalidos() {
	    ControladorUsuarios controladorusuarios = ControladorUsuarios.getInstance();
	    ManejadorUsuarios  manejadorusuarios = ManejadorUsuarios.getInstance();

	    // Usuario sin registros
	    Asistente bob = new Asistente("bobCR", "Eventos.2025!", "Bob", "Lopez", "bobCR@mail.com", Utilidades.toDate(LocalDate.of(2000, 1, 1)), null);
	    manejadorusuarios.getConjuntoAsistentes().put(bob.getNickname(), bob);

	    // listar → vacío
	    java.util.List<datatypes.DTRegistro> regs = controladorusuarios.listarRegistrosUsuario("bobCR");
	    assertNotNull(regs);
	    assertTrue(regs.isEmpty(), "Sin registros → lista vacía");

	    // obtenerDetalle → null (no debe lanzar excepción)
	    assertNull(controladorusuarios.obtenerDetalleRegistro("bobCR", 0));
	    assertNull(controladorusuarios.obtenerDetalleRegistro("bobCR", -1));
	    assertNull(controladorusuarios.obtenerDetalleRegistro("bobCR", 99));
	}
	
	@Test
    void listarydetalleusuarioInexistenteoNickInvalido() {
		
		 ControladorUsuarios controlador = ControladorUsuarios.getInstance();
        // inexistente
        List<DTRegistro> registro1 = controlador.listarRegistrosUsuario("no-existe-CR");
        assertNotNull(registro1);
        assertTrue(registro1.isEmpty());
        assertNull(controlador.obtenerDetalleRegistro("no-existe-CR", 0));

        // nick vacío
        List<DTRegistro> registro2 = controlador.listarRegistrosUsuario("");
        assertNotNull(registro2);
        assertTrue(registro2.isEmpty());
        assertNull(controlador.obtenerDetalleRegistro("", 0));

        // nick null
        List<DTRegistro> registro3 = controlador.listarRegistrosUsuario(null);
        assertNotNull(registro3);
        assertTrue(registro3.isEmpty());
        assertNull(controlador.obtenerDetalleRegistro(null, 0));
    }
	@Test
    void listarydetalleordenYUltimo() {
        final String NICK = "carlosCR";
        final String MAIL = "carlosCR@mail.com";
        
        ManejadorEventos   manejadoreventos = ManejadorEventos.getInstancia();
        ControladorUsuarios controladorusuarios = ControladorUsuarios.getInstance();
	    ManejadorUsuarios  manejadorusuarios = ManejadorUsuarios.getInstance();

        // Usuario
        Asistente carlos = new Asistente(NICK, "Eventos.2025!", "Carlos", "Ríos", MAIL, Utilidades.toDate(LocalDate.of(1995, 7, 4)), null);
        manejadorusuarios.getConjuntoAsistentes().put(carlos.getNickname(), carlos);

        // Evento + edición
        Evento evento = new Evento("DevConf", "DC", "Conferencia Dev", Utilidades.toDate(LocalDate.now()), null, null, 0);
        manejadoreventos.agregarEvento(evento);
        Edicion edicion = new Edicion("DevConf 2026", "DC26",
                Utilidades.toDate(LocalDate.of(2026, 4, 10)), Utilidades.toDate(LocalDate.of(2026, 4, 12)),
                Utilidades.toDate(LocalDate.now()), "Montevideo", "Uruguay", "organizador2", evento.getNombre(), null, null);
        evento.agregarEdicion(edicion);

        // Tipos
        TipoRegistro gen = new TipoRegistro("General", "Acceso general", 500, 1000, 1000);
        TipoRegistro vip = new TipoRegistro("VIP", "Acceso VIP", 1500, 200, 200);
        edicion.getTiposRegistro().put(gen.getNombre(), gen);
        edicion.getTiposRegistro().put(vip.getNombre(), vip);

        
        // Patrocinio + Registros (en orden: Gen, VIP, Gen)
        Patrocinio pat = new Patrocinio("PAT", null, 0, null, 0, null, null);
        Registro registro1 = new Registro(Utilidades.toDate(LocalDate.now()),               500f,  carlos, pat,edicion,gen.getNombre());
        Registro registro2 = new Registro(Utilidades.toDate(LocalDate.now().plusDays(1)),  1500f,  carlos, pat,edicion,vip.getNombre());
        Registro registro3 = new Registro(Utilidades.toDate(LocalDate.now().plusDays(2)),   500f,  carlos, pat,edicion,gen.getNombre());
        gen.addRegistro(registro1);
        vip.addRegistro(registro2);
        gen.addRegistro(registro3);
        carlos.getRegistros().add(registro1);
        carlos.getRegistros().add(registro2);
        carlos.getRegistros().add(registro3);


        // A) El usuario aparece en listarUsuarios
        Set<String> nicks = controladorusuarios.listarUsuarios()
                .stream().map(DTUsuario::nickname).collect(Collectors.toSet());
        assertTrue(nicks.contains(NICK));

        // B) listar registros → 3, con tipos esperados (sin asumir orden)
        List<DTRegistro> regs = controladorusuarios.listarRegistrosUsuario(NICK);
        assertNotNull(regs);
        assertEquals(3, regs.size());
        Set<String> tipos = regs.stream().map(DTRegistro::getNombreTipo).collect(Collectors.toSet());
        assertEquals(Set.of("General", "VIP"), tipos);

        // C) detalle del último índice válido
        int last = regs.size() - 1;
        DTDetalleRegistro det = controladorusuarios.obtenerDetalleRegistro(NICK, last);
        assertNotNull(det);
        assertEquals(NICK, det.getNicknameAsistente());
        assertTrue(Set.of("General", "VIP").contains(det.getNombreTipo()));
        // (si tu implementación llena nombre de edición en el detalle)
        // assertEquals("DevConf 2026", det.getNombreEdicion());
    }
	@Test
    void detalleindicesInvalidosyBorde() {
		
		ManejadorEventos   manejadoreventos = ManejadorEventos.getInstancia();
        ControladorUsuarios controladorusuarios = ControladorUsuarios.getInstance();
	    ManejadorUsuarios  manejadorusuarios = ManejadorUsuarios.getInstance();
		
        final String NICK = "mariaCR";
        Asistente asistente = new Asistente(NICK, "Eventos.2025!", "María", "Quintana", "maria@mail.com", Utilidades.toDate(LocalDate.of(1998, 2, 2)), null);
        manejadorusuarios.getConjuntoAsistentes().put(asistente.getNickname(), asistente);

        // Contexto mínimo
        Evento evento = new Evento("DataDay", "DD", "Data Day", Utilidades.toDate(LocalDate.now()), null, null, 0);
        manejadoreventos.agregarEvento(evento);
        Edicion edicion = new Edicion("DataDay 2025", "DD25",
                Utilidades.toDate(LocalDate.of(2025, 6, 1)), Utilidades.toDate(LocalDate.of(2025, 6, 2)),
                Utilidades.toDate(LocalDate.now()), "Salto", "Uruguay", "organizador3", evento.getNombre(), null, null);
        evento.agregarEdicion(edicion);

        TipoRegistro gen = new TipoRegistro("General", "Acceso general", 800, 500, 500);
        edicion.getTiposRegistro().put(gen.getNombre(), gen);
        
        
         Patrocinio pat = new Patrocinio("P", null, 0, null, 0, null, null); 

        // Un solo registro
        Registro registro = new Registro(Utilidades.toDate(LocalDate.now()), 800f, asistente, pat,edicion,gen.getNombre());
        asistente.getRegistros().add(registro);
        // Índices inválidos
        assertNull(controladorusuarios.obtenerDetalleRegistro(NICK, -1));
        assertNull(controladorusuarios.obtenerDetalleRegistro(NICK, 5));

        // Índices válidos
        List<DTRegistro> regs = controladorusuarios.listarRegistrosUsuario(NICK);
        assertNotNull(regs);
        assertEquals(1, regs.size());
        assertNotNull(controladorusuarios.obtenerDetalleRegistro(NICK, 0));
        assertNotNull(controladorusuarios.obtenerDetalleRegistro(NICK, regs.size() - 1));
        
    }
}
