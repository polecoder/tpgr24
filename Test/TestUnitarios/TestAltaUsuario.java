package TestUnitarios;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

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

    // ----------- Casos válidos de alta de usuario -----------
    
    @Test
    void altaOrganizadorValido() {
        assertDoesNotThrow(() -> { controladorUsuario.altaOrganizador("lucasM", "Lucas", "lucas.martinez@gmail.com", "Encargado de eventos", "https://lucasorganiza.com"); }); 
    }

    @Test
    void altaAsistenteValido() {
        assertDoesNotThrow(() -> { controladorUsuario.altaAsistente("florenciaR", "Eventos.2025!", "Florencia", "flor.rosas@hotmail.com", "Rosas", Utilidades.toDate(LocalDate.of(1992, 3, 14)), null); });
    }

    // ----------- Validación de campos obligatorios -----------

    @Test
    void altaOrganizadorConCamposVacios() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("", "", "", "", ""); });
        assertTrue(ex.getMessage().contains("Nick, nombre, correo y descripción son campos obligatorios"));
    }

    @Test
    void altaAsistenteConCamposObligatoriosVacios() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("", "", "", "", "", null, null); });
        assertTrue(ex.getMessage().contains("Nick, contraseña, nombre, apellido, correo y fecha de nacimiento son campos obligatorios"));
    }

    // ----------- Validación de formatos inválidos -----------

    @Test
    void organizadorConCorreoInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("valentinaB", "Valentina", "valentinagmail.com", "Organizadora de ferias", "https://feriasuy.com"); });
        assertTrue(ex.getMessage().contains("formato del correo"));
    }

    @Test
    void organizadorConNombreInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("jorge123", "Jorge99", "jorge@mail.com", "Coordinador", "https://jorgeeventos.com"); });
        assertTrue(ex.getMessage().contains("solo puede contener letras"));
    }

    @Test
    void organizadorConLinkInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("marianaL", "Mariana", "mariana.l@gmail.com", "Gestora cultural", "www.mariana.com"); });
        assertTrue(ex.getMessage().contains("link debe comenzar con http:// o https://"));
    }

    @Test
    void asistenteConCorreoInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("andresV", "Eventos.2025!", "Andrés", "andresmail.com", "Varela", Utilidades.toDate(LocalDate.of(1988, 7, 22)), null); });
        assertTrue(ex.getMessage().contains("formato del correo"));
    }

    @Test
    void asistenteConNombreInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("carlaM", "Eventos.2025!", "Carla123", "carla.m@gmail.com", "Martínez", Utilidades.toDate(LocalDate.of(1995, 11, 5)), null); });
        assertTrue(ex.getMessage().contains("solo puede contener letras"));
    }

    @Test
    void asistenteConApellidoInvalido() {
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("sebastianT", "Eventos.2025!", "Sebastián", "sebastian.t@gmail.com", "Torres99", Utilidades.toDate(LocalDate.of(1993, 9, 30)), null); });
        assertTrue(ex.getMessage().contains("solo puede contener letras"));
    }

    // ----------- Validación de duplicados (nickname y correo) -----------

    @Test
    void nicknameDuplicadoEntreOrganizadores() throws Exception {
        controladorUsuario.altaOrganizador("nicoF", "Nicolás", "nicolas.f@gmail.com", "Organizador de congresos", "http://congresosuy.com");
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("nicoF", "Federico", "federico@gmail.com", "Eventos académicos", "http://eventosfed.com"); });
        assertTrue(ex.getMessage().contains("ya existe"));
    }

    @Test
    public void nicknameDuplicadoEntreAsistenteYOrganizador() throws Exception {
        controladorUsuario.altaAsistente("sofiaG", "Eventos.2025!", "Sofía", "sofia.g@gmail.com", "Gómez", Utilidades.toDate(LocalDate.of(1990, 1, 1)), null);
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("sofiaG", "Gonzalo", "gonzalo.g@gmail.com", "Organizador de charlas", "https://charlasuy.com"); });
        assertTrue(ex.getMessage().contains("ya existe"));
    }

    @Test
    void correoDuplicadoEntreAsistentes() throws Exception {
        controladorUsuario.altaAsistente("martinC", "Eventos.2025!", "Martín", "martin.c@gmail.com", "Cabrera", Utilidades.toDate(LocalDate.of(1990, 1, 1)), null);
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaAsistente("luciaD", "Eventos.2025!", "Lucía", "martin.c@gmail.com", "Díaz", Utilidades.toDate(LocalDate.of(1995, 3, 15)), null); });
        assertTrue(ex.getMessage().contains("correo ya está en uso"));
    }

    @Test
    public void correoDuplicadoEntreAsistenteYOrganizador() throws Exception {
        controladorUsuario.altaAsistente("agustinaM", "Eventos.2025!", "Agustina", "agustina.m@gmail.com", "Morales", Utilidades.toDate(LocalDate.of(1990, 1, 1)), null);
        Exception ex = assertThrows(Exception.class, () -> { controladorUsuario.altaOrganizador("rodrigoP", "Rodrigo", "agustina.m@gmail.com", "Organizador de talleres", "https://talleresuy.com"); });
        assertTrue(ex.getMessage().contains("correo ya está en uso"));
    }

    // ----------- Alta de asistente con institución válida -----------
    // TODO: implementar test que valide alta con institución existente

    // ----------- Verificación post-alta (estado interno del sistema) -----------

    @Test
    void altaOrganizadorSeAgregaCorrectamente() throws Exception {
        controladorUsuario.altaOrganizador("mariaS", "María", "maria.s@gmail.com", "Eventos sociales", "https://mariaeventos.com");
        Organizador org = ManejadorUsuarios.getInstance().getOrganizador("mariaS");
        assertNotNull(org);
        assertEquals("maria.s@gmail.com", org.getCorreo());
    }
}


