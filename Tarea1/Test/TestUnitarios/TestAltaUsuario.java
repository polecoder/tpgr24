package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.IControladorInstitucion;
import logica.IControladorUsuarios;
import logica.Fabrica;
import logica.ManejadorUsuarios;
import logica.Organizador;
import logica.Utilidades;

class TestAltaUsuario {
	Fabrica fabrica= Fabrica.getInstancia();
    IControladorUsuarios controladorUsuario = fabrica.getControladorUsuarios();
    IControladorInstitucion controladorInstitucion = fabrica.getControladorInstitucion();

    @BeforeEach
    public void init() {
    	Fabrica.resetInstancia();
    	fabrica = Fabrica.getInstancia();
    	controladorUsuario = fabrica.getControladorUsuarios();
        controladorInstitucion = fabrica.getControladorInstitucion();
    }
    // ----------- Casos válidos de alta de usuario -----------
    
    @Test
    void altaOrganizadorValido() {
        assertDoesNotThrow(() -> { controladorUsuario.altaOrganizador("lucasM", "Eventos.2025!", "Lucas", "lucas.martinez@gmail.com", "Encargado de eventos", "https://lucasorganiza.com", null); }); 
    }

    @Test
    void altaAsistenteValido() {
        assertDoesNotThrow(() -> { controladorUsuario.altaAsistente("florenciaR", "Eventos.2025!", "Florencia", "flor.rosas@hotmail.com", "Rosas", Utilidades.toDate(LocalDate.of(1992, 3, 14)), null, null); });
    }

    // ----------- Validación de campos obligatorios -----------

    @Test
    void altaOrganizadorConCamposVacios() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("", "", "", "", "", "", null); });
        assertTrue(ex.getMessage().contains("Nick, contrasena, nombre, correo y descripción son campos obligatorios"));
    }

    @Test
    void altaAsistenteConCamposObligatoriosVacios() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("", "", "", "", "", null, null, null); });
        assertTrue(ex.getMessage().contains("Nick, contraseña, nombre, apellido, correo y fecha de nacimiento son campos obligatorios"));
    }

    // ----------- Validación de formatos inválidos -----------

    @Test
    void organizadorConCorreoInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("valentinaB", "Eventos.2025!", "Valentina", "valentinagmail.com", "Organizadora de ferias", "https://feriasuy.com", null); });
        assertTrue(ex.getMessage().contains("formato del correo"));
    }

    @Test
    void organizadorConNombreInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("jorge123", "Eventos.2025!", "Jorge99", "jorge@mail.com", "Coordinador", "https://jorgeeventos.com", null); });
        assertTrue(ex.getMessage().contains("solo puede contener letras"));
    }

    @Test
    void organizadorConLinkInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("marianaL", "Eventos.2025!", "Mariana", "mariana.l@gmail.com", "Gestora cultural", "www.mariana.com", null); });
        assertTrue(ex.getMessage().contains("link debe comenzar con http:// o https://"));
    }

    @Test
    void asistenteConCorreoInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("andresV", "Eventos.2025!", "Andrés", "andresmail.com", "Varela", Utilidades.toDate(LocalDate.of(1988, 7, 22)), null, null); });
        assertTrue(ex.getMessage().contains("formato del correo"));
    }

    @Test
    void asistenteConNombreInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("carlaM", "Eventos.2025!", "Carla123", "carla.m@gmail.com", "Martínez", Utilidades.toDate(LocalDate.of(1995, 11, 5)), null, null); });
        assertTrue(ex.getMessage().contains("solo puede contener letras"));
    }

    @Test
    void asistenteConApellidoInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("sebastianT", "Eventos.2025!", "Sebastián", "sebastian.t@gmail.com", "Torres99", Utilidades.toDate(LocalDate.of(1993, 9, 30)), null, null); });
        assertTrue(ex.getMessage().contains("solo puede contener letras"));
    }
    
    @Test
    void asistenteConFechaInvalida() {
    	Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("fedeC45", "Eventos.2025!", "Federico", "fede45@gmail.com", "Colina", Utilidades.toDate(LocalDate.of(2045, 9, 30)), null, null); });
        assertTrue(ex.getMessage().contains("en el futuro"));
    }

    // ----------- Validación de duplicados (nickname y correo) -----------

    @Test
    void nicknameDuplicadoEntreOrganizadores() throws Exception {
        controladorUsuario.altaOrganizador("nicoF", "Eventos.2025!", "Nicolás", "nicolas.f@gmail.com", "Organizador de congresos", "http://congresosuy.com", null);
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("nicoF", "Eventos.2025!", "Federico", "federico@gmail.com", "Eventos académicos", "http://eventosfed.com", null); });
        assertTrue(ex.getMessage().contains("ya existe"));
    }

    @Test
    void nicknameDuplicadoEntreAsistentes() throws Exception {
    	controladorUsuario.altaAsistente("sofiaP", "Eventos.2025!", "Sofía", "sofia.p@gmail.com", "Pereira", Utilidades.toDate(LocalDate.of(1997, 1, 1)), null, null);
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("sofiaP", "Eventos.2025!", "Lucía", "lucia.d@gmail.com", "Díaz", Utilidades.toDate(LocalDate.of(1995, 3, 15)), null, null); });
        assertTrue(ex.getMessage().contains("ya existe"));
    }
    
    @Test
    public void nicknameDuplicadoEntreAsistenteYOrganizador() throws Exception {
        controladorUsuario.altaAsistente("sofiaG", "Eventos.2025!", "Sofía", "sofia.g@gmail.com", "Gómez", Utilidades.toDate(LocalDate.of(1990, 1, 1)), null, null);
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("sofiaG", "Eventos.2025!", "Gonzalo", "gonzalo.g@gmail.com", "Organizador de charlas", "https://charlasuy.com", null); });
        assertTrue(ex.getMessage().contains("ya existe"));
    }

    @Test
    void correoDuplicadoEntreAsistentes() throws Exception {
        controladorUsuario.altaAsistente("martinC", "Eventos.2025!", "Martín", "martin.c@gmail.com", "Cabrera", Utilidades.toDate(LocalDate.of(1990, 1, 1)), null, null);
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("luciaD", "Eventos.2025!", "Lucía", "martin.c@gmail.com", "Díaz", Utilidades.toDate(LocalDate.of(1995, 3, 15)), null, null); });
        assertTrue(ex.getMessage().contains("correo ya está en uso"));
    }

    @Test
    public void correoDuplicadoEntreAsistenteYOrganizador() throws Exception {
        controladorUsuario.altaAsistente("agustinaM", "Eventos.2025!", "Agustina", "agustina.m@gmail.com", "Morales", Utilidades.toDate(LocalDate.of(1990, 1, 1)), null, null);
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("rodrigoP", "Eventos.2025!", "Rodrigo", "agustina.m@gmail.com", "Organizador de talleres", "https://talleresuy.com", null); });
        assertTrue(ex.getMessage().contains("correo ya está en uso"));
    }
    
    @Test
    public void contrasenaIncorrectaOrganizador() {
    	// Caso 1
    	Exception ex1 = assertThrows(Exception.class, () -> {
    		controladorUsuario.altaOrganizador("lucasM", "123!aA", "Lucas", "lucas.martinez@gmail.com", "Encargado de eventos", "https://lucasorganiza.com", null);; 
    	});
    	assertTrue(ex1.getMessage().contains("al menos 8 caracteres"));
    	// Caso 2
    	Exception ex2 = assertThrows(Exception.class, () -> {
    		controladorUsuario.altaOrganizador("lucasM", "abcde123!", "Lucas", "lucas.martinez@gmail.com", "Encargado de eventos", "https://lucasorganiza.com", null);; 
    	});
    	assertTrue(ex2.getMessage().contains("al menos una mayúscula"));
    	// Caso 3
    	Exception ex3 = assertThrows(Exception.class, () -> {
    		controladorUsuario.altaOrganizador("lucasM", "Abcdefgh!", "Lucas", "lucas.martinez@gmail.com", "Encargado de eventos", "https://lucasorganiza.com", null);; 
    	});
    	assertTrue(ex3.getMessage().contains("al menos un dígito"));
    	// Caso 4
    	Exception ex4 = assertThrows(Exception.class, () -> {
    		controladorUsuario.altaOrganizador("lucasM", "Abc1defgh", "Lucas", "lucas.martinez@gmail.com", "Encargado de eventos", "https://lucasorganiza.com", null);; 
    	});
    	assertTrue(ex4.getMessage().contains("al menos un carácter especial"));
    }
    
    @Test
    public void contrasenaIncorrectaAsistente() {
    	// Caso 1
    	Exception ex1 = assertThrows(Exception.class, () -> {
    		controladorUsuario.altaAsistente("florenciaR", "123!aA", "Florencia", "flor.rosas@hotmail.com", "Rosas", Utilidades.toDate(LocalDate.of(1992, 3, 14)), null, null); 
    	});
    	assertTrue(ex1.getMessage().contains("al menos 8 caracteres"));
    	// Caso 2
    	Exception ex2 = assertThrows(Exception.class, () -> {
    		controladorUsuario.altaAsistente("florenciaR", "abcde123!", "Florencia", "flor.rosas@hotmail.com", "Rosas", Utilidades.toDate(LocalDate.of(1992, 3, 14)), null, null);
    	});
    	assertTrue(ex2.getMessage().contains("al menos una mayúscula"));
    	// Caso 3
    	Exception ex3 = assertThrows(Exception.class, () -> {
    		controladorUsuario.altaAsistente("florenciaR", "Abcdefgh!", "Florencia", "flor.rosas@hotmail.com", "Rosas", Utilidades.toDate(LocalDate.of(1992, 3, 14)), null, null); 
    	});
    	assertTrue(ex3.getMessage().contains("al menos un dígito"));
    	// Caso 4
    	Exception ex4 = assertThrows(Exception.class, () -> {
    		controladorUsuario.altaAsistente("florenciaR", "Abc1defgh", "Florencia", "flor.rosas@hotmail.com", "Rosas", Utilidades.toDate(LocalDate.of(1992, 3, 14)), null, null); 
    	});
    	assertTrue(ex4.getMessage().contains("al menos un carácter especial"));
    }

    // ----------- Alta de asistente con institución válida -----------
    // TODO: implementar test que valide alta con institución existente
    
    
    // ----------- Verificación post-alta (estado interno del sistema) -----------

    @Test
    void altaOrganizadorSeAgregaCorrectamente() throws Exception {
        controladorUsuario.altaOrganizador("mariaS", "Eventos.2025!", "María", "maria.s@gmail.com", "Eventos sociales", "https://mariaeventos.com", null);
        Organizador org = ManejadorUsuarios.getInstance().getOrganizador("mariaS");
        assertNotNull(org);
        assertEquals("maria.s@gmail.com", org.getCorreo());
    }
}


