package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import excepciones.EventoNoExiste;

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
import logica.datatypes.DTDetalleRegistro;
//import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;
import logica.datatypes.DTRegistro;
import logica.datatypes.DTUsuario;
import logica.Asistente;
import logica.Registro;

class testConsultaRegistro {
	@Test
	void test() {
		
		ControladorEventos controlador = ControladorEventos.getInstance();
		   ManejadorEventos manejador = ManejadorEventos.getInstancia(); 
		   
		//creo evento
		Evento evento = new Evento("ExpoTech", "Feria de tecnología", "EXP", Utilidades.toDate(LocalDate.of(2023, 5, 10)));
		manejador.agregarEvento(evento);
		
		//creo edicion
		
		Edicion ed1 = new Edicion("Expo", "EXP", Utilidades.toDate(LocalDate.of(2023,9,1)), Utilidades.toDate(LocalDate.of(2023, 12, 1)), Utilidades.toDate(LocalDate.now()), "Montevideo", "Uruguay", "organizador1", evento.getNombre());
		evento.agregarEdicion(ed1);
		
		//creo Tiporegistro
		TipoRegistro Gen = new TipoRegistro("General", "Acceso General", 100, 200, 500);
		TipoRegistro Vip = new TipoRegistro("Vip", "Acceso vip", 100, 300, 200);
		
		//asociar registro a edicion
		ed1.agregarTipoRegistro(Vip);
		ed1.agregarTipoRegistro(Gen);
		
		//crear asistente
		Asistente ana = new Asistente("ana", "abc1234", "Ana", "Perez", "ana@mail.com", Utilidades.toDate(LocalDate.now()),null);
		
		//creo Registro
		Registro reg1 = new Registro(Utilidades.toDate(LocalDate.now()), 100, ana, null);
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
        Registro r = ana.getRegistros().get(0);
        assertEquals(100f, r.getCosto(), 0.001f);
        
	}
	
	@Test
	void listarRegistrosUsuario() {
	    // Controladores / manejadores
	    ControladorUsuarios cu = ControladorUsuarios.getInstance();
	    ManejadorUsuarios  mu = ManejadorUsuarios.getInstance();
	    ManejadorEventos   me = ManejadorEventos.getInstancia();

	    // --- Asistente ---
	    Asistente ana = new Asistente("anaCR", "abc1234", "Ana", "Perez", "anaCR@mail.com", Utilidades.toDate(LocalDate.of(1999, 5, 1)),null);
	    mu.getConjuntoAsistentes().put(ana.getNickname(), ana);

	    // --- Evento + Edición ---
	    Evento evento = new Evento("ExpoTech", "EXP", "Feria de tecnología", Utilidades.toDate(LocalDate.of(2023, 5, 10)));
	    me.agregarEvento(evento);

	    Edicion ed = new Edicion("Expo", "EXP",
	            Utilidades.toDate(LocalDate.of(2023, 9, 1)),
	            Utilidades.toDate(LocalDate.of(2023, 12, 1)),
	            Utilidades.toDate(LocalDate.now()), "Montevideo", "Uruguay","organizador1", evento.getNombre());
	    evento.agregarEdicion(ed);

	    // --- Tipos de registro ---
	    TipoRegistro gen = new TipoRegistro("General", "Acceso General", 100, 200, 500);
	    TipoRegistro vip = new TipoRegistro("Vip",     "Acceso vip",     250, 300, 200);
	    ed.agregarTipoRegistro(vip);
	    ed.agregarTipoRegistro(gen);

	    // --- Patrocinio + Registros del usuario ---
	    
	    Patrocinio pat = new Patrocinio("Patro",Utilidades.toDate(LocalDate.now()), 0, null, 0, null);
	    Registro r1 = new Registro(Utilidades.toDate(LocalDate.now()),100f, ana, pat);
	    Registro r2 = new Registro(Utilidades.toDate(LocalDate.now().plusDays(1)), 250f, ana, pat);
	    
	    gen.addRegistro(r1);
	    vip.addRegistro(r2);

	    ana.getRegistros().add(r1);
	    ana.getRegistros().add(r2);
	    

	    // --- ACT: listar registros del usuario ---
	    java.util.List<logica.datatypes.DTRegistro> regs = cu.listarRegistrosUsuario("anaCR");

	    // --- ASSERTS ---
	    assertNotNull(regs);
	    assertEquals(2, regs.size(), "Debe listar 2 registros");

	    java.util.Set<String> tipos = regs.stream()
	            .map(logica.datatypes.DTRegistro::nombreTipo)
	            .collect(java.util.stream.Collectors.toSet());
	    
	    Set<String> tipos1 = regs.stream()
	            .map(DTRegistro::nombreTipo)
	            .filter(java.util.Objects::nonNull)
	            .map(s -> s.trim().toLowerCase())
	            .collect(java.util.stream.Collectors.toSet());

	    assertEquals(java.util.Set.of("general", "vip"), tipos1, "Tipos esperados en la lista");

	    // Detalle índice 0 (sin asumir orden: solo validamos coherencia)
	    logica.datatypes.DTDetalleRegistro d0 = cu.obtenerDetalleRegistro("anaCR", 0);
	    assertNotNull(d0);
	    assertEquals("anaCR", d0.getNicknameAsistente());
	    assertTrue(java.util.Set.of("General", "Vip").contains(d0.getNombreTipo()));
	}
	
	@Test
	void obtenerDetalleRegistro_usuarioSinRegistros_e_indicesInvalidos() {
	    ControladorUsuarios cu = ControladorUsuarios.getInstance();
	    ManejadorUsuarios  mu = ManejadorUsuarios.getInstance();

	    // Usuario sin registros
	    Asistente bob = new Asistente("bobCR", "abc1234", "Bob", "Lopez", "bobCR@mail.com", Utilidades.toDate(LocalDate.of(2000, 1, 1)),null);
	    mu.getConjuntoAsistentes().put(bob.getNickname(), bob);

	    // listar → vacío
	    java.util.List<logica.datatypes.DTRegistro> regs = cu.listarRegistrosUsuario("bobCR");
	    assertNotNull(regs);
	    assertTrue(regs.isEmpty(), "Sin registros → lista vacía");

	    // obtenerDetalle → null (no debe lanzar excepción)
	    assertNull(cu.obtenerDetalleRegistro("bobCR", 0));
	    assertNull(cu.obtenerDetalleRegistro("bobCR", -1));
	    assertNull(cu.obtenerDetalleRegistro("bobCR", 99));
	}
	
	@Test
    void listar_y_detalle_usuarioInexistente_oNickInvalido() {
		
		 ControladorUsuarios cu = ControladorUsuarios.getInstance();
        // inexistente
        List<DTRegistro> r1 = cu.listarRegistrosUsuario("no-existe-CR");
        assertNotNull(r1);
        assertTrue(r1.isEmpty());
        assertNull(cu.obtenerDetalleRegistro("no-existe-CR", 0));

        // nick vacío
        List<DTRegistro> r2 = cu.listarRegistrosUsuario("");
        assertNotNull(r2);
        assertTrue(r2.isEmpty());
        assertNull(cu.obtenerDetalleRegistro("", 0));

        // nick null
        List<DTRegistro> r3 = cu.listarRegistrosUsuario(null);
        assertNotNull(r3);
        assertTrue(r3.isEmpty());
        assertNull(cu.obtenerDetalleRegistro(null, 0));
    }
	@Test
    void listar_y_detalle_ordenYUltimo() {
        final String NICK = "carlosCR";
        final String MAIL = "carlosCR@mail.com";
        
        ManejadorEventos   me = ManejadorEventos.getInstancia();
        ControladorUsuarios cu = ControladorUsuarios.getInstance();
	    ManejadorUsuarios  mu = ManejadorUsuarios.getInstance();

        // Usuario
        Asistente carlos = new Asistente(NICK, "abc1234", "Carlos", "Ríos", MAIL, Utilidades.toDate(LocalDate.of(1995, 7, 4)),null);
        mu.getConjuntoAsistentes().put(carlos.getNickname(), carlos);

        // Evento + edición
        Evento ev = new Evento("DevConf", "DC", "Conferencia Dev", Utilidades.toDate(LocalDate.now()));
        me.agregarEvento(ev);
        Edicion ed = new Edicion("DevConf 2026", "DC26",
                Utilidades.toDate(LocalDate.of(2026, 4, 10)), Utilidades.toDate(LocalDate.of(2026, 4, 12)),
                Utilidades.toDate(LocalDate.now()), "Montevideo", "Uruguay","organizador2", ev.getNombre());
        ev.agregarEdicion(ed);

        // Tipos
        TipoRegistro gen = new TipoRegistro("General", "Acceso general", 500, 1000, 1000);
        TipoRegistro vip = new TipoRegistro("VIP", "Acceso VIP", 1500, 200, 200);
        ed.getTiposRegistro().put(gen.getNombre(), gen);
        ed.getTiposRegistro().put(vip.getNombre(), vip);

        
        // Patrocinio + Registros (en orden: Gen, VIP, Gen)
        Patrocinio pat = new Patrocinio("PAT", null, 0, null, 0, null);
        Registro r1 = new Registro(Utilidades.toDate(LocalDate.now()),               500f,  carlos, pat);
        Registro r2 = new Registro(Utilidades.toDate(LocalDate.now().plusDays(1)),  1500f,  carlos, pat);
        Registro r3 = new Registro(Utilidades.toDate(LocalDate.now().plusDays(2)),   500f,  carlos, pat);
        gen.addRegistro(r1);
        vip.addRegistro(r2);
        gen.addRegistro(r3);
        carlos.getRegistros().add(r1);
        carlos.getRegistros().add(r2);
        carlos.getRegistros().add(r3);


        // A) El usuario aparece en listarUsuarios
        Set<String> nicks = cu.listarUsuarios()
                .stream().map(DTUsuario::nickname).collect(Collectors.toSet());
        assertTrue(nicks.contains(NICK));

        // B) listar registros → 3, con tipos esperados (sin asumir orden)
        List<DTRegistro> regs = cu.listarRegistrosUsuario(NICK);
        assertNotNull(regs);
        assertEquals(3, regs.size());
        Set<String> tipos = regs.stream().map(DTRegistro::nombreTipo).collect(Collectors.toSet());
        assertEquals(Set.of("General", "VIP"), tipos);

        // C) detalle del último índice válido
        int last = regs.size() - 1;
        DTDetalleRegistro det = cu.obtenerDetalleRegistro(NICK, last);
        assertNotNull(det);
        assertEquals(NICK, det.getNicknameAsistente());
        assertTrue(Set.of("General", "VIP").contains(det.getNombreTipo()));
        // (si tu implementación llena nombre de edición en el detalle)
        // assertEquals("DevConf 2026", det.getNombreEdicion());
    }
	@Test
    void detalle_indicesInvalidos_yBorde() {
		
		ManejadorEventos   me = ManejadorEventos.getInstancia();
        ControladorUsuarios cu = ControladorUsuarios.getInstance();
	    ManejadorUsuarios  mu = ManejadorUsuarios.getInstance();
		
        final String NICK = "mariaCR";
        Asistente u = new Asistente(NICK, "abc1234", "María", "Quintana", "maria@mail.com", Utilidades.toDate(LocalDate.of(1998, 2, 2)),null);
        mu.getConjuntoAsistentes().put(u.getNickname(), u);

        // Contexto mínimo
        Evento ev = new Evento("DataDay", "DD", "Data Day", Utilidades.toDate(LocalDate.now()));
        me.agregarEvento(ev);
        Edicion ed = new Edicion("DataDay 2025", "DD25",
                Utilidades.toDate(LocalDate.of(2025, 6, 1)), Utilidades.toDate(LocalDate.of(2025, 6, 2)),
                Utilidades.toDate(LocalDate.now()), "Salto", "Uruguay","organizador3", ev.getNombre());
        ev.agregarEdicion(ed);

        TipoRegistro gen = new TipoRegistro("General", "Acceso general", 800, 500, 500);
        ed.getTiposRegistro().put(gen.getNombre(), gen);
        
        
         Patrocinio pat = new Patrocinio("P", null, 0, null, 0, null); 

        // Un solo registro
        Registro r = new Registro(Utilidades.toDate(LocalDate.now()), 800f, u, pat);
        u.getRegistros().add(r);
        // Índices inválidos
        assertNull(cu.obtenerDetalleRegistro(NICK, -1));
        assertNull(cu.obtenerDetalleRegistro(NICK, 5));

        // Índices válidos
        List<DTRegistro> regs = cu.listarRegistrosUsuario(NICK);
        assertNotNull(regs);
        assertEquals(1, regs.size());
        assertNotNull(cu.obtenerDetalleRegistro(NICK, 0));
        assertNotNull(cu.obtenerDetalleRegistro(NICK, regs.size() - 1));
        
    }
}
