package persistencia;

import java.util.Date;

import jakarta.persistence.*;

// TODO: Borrar archivo
public class TestPersistencia {
	public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ArchivoDB");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // --- Organizador + Usuario (persistidos por cascade al persistir la Edicion) ---
            UsuarioArchivado uOrg = new UsuarioArchivado();
            uOrg.setNickname("org1");
            uOrg.setEmail("org1@example.com");
            uOrg.setNombre("Org Uno");
            uOrg.setTipoUsuario(persistencia.TipoUsuario.TipoUsuario.ORGANIZADOR);

            OrganizadorArchivado org = new OrganizadorArchivado();
            org.setUsuario(uOrg);
            org.setDescripcion("Organizador de prueba");
            org.setSitioWeb("https://example.org");

            // --- Edicion ---
            EdicionArchivada ed = new EdicionArchivada();
            ed.setNombre("Edicion X");
            ed.setSigla("EX");
            ed.setCiudad("Ciudad");
            ed.setPais("Pais");
            ed.setFechaInicio(new Date());
            ed.setFechaFin(new Date());
            ed.setFechaCreacion(new Date());
            ed.setFechaArchivo(new Date());
            ed.setNombreEvento("Evento X");
            ed.setOrganizador(org);

            em.persist(ed);
            em.flush();

            // --- Usuario asistente + AsistenteArchivado ---
            UsuarioArchivado uAsis = new UsuarioArchivado();
            uAsis.setNickname("asis1");
            uAsis.setEmail("asis1@example.com");
            uAsis.setNombre("Asistente Uno");
            uAsis.setTipoUsuario(persistencia.TipoUsuario.TipoUsuario.ASISTENTE);

            em.persist(uAsis);
            em.flush();

            AsistenteArchivado asis = new AsistenteArchivado();
            asis.setUsuario(uAsis);
            asis.setApellido("Perez");
            asis.setFechaNacimiento(new Date());
            asis.setNombreInstitucion("Universidad Ejemplo");

            em.persist(asis);
            em.flush();

            // --- RegistroArchivado ---
            RegistroArchivado reg = new RegistroArchivado();
            reg.setAsistente(asis);
            reg.setEdicion(ed);
            reg.setNombreTipoRegistro("Estándar");
            reg.setUsoPatrocinio(false);
            reg.setCodigoPatrocinio(null);
            reg.setFechaRegistro(new Date());
            reg.setCosto(0.0f);

            em.persist(reg);
            em.flush();

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
