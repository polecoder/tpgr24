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

            // usuario + organizador
            UsuarioArchivado u = new UsuarioArchivado();
            u.setNickname("org1");
            u.setEmail("org1@example.com");
            u.setNombre("Org Uno");
            u.setTipoUsuario(persistencia.TipoUsuario.TipoUsuario.ORGANIZADOR);

            OrganizadorArchivado org = new OrganizadorArchivado();
            org.setUsuario(u);
            org.setDescripcion("Organizador de prueba");
            org.setSitioWeb("https://example.org");

            // edicion
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

            em.persist(ed); // con cascade persist en relaciones debería persistir todo
            em.getTransaction().commit();

            System.out.println("OK: Edicion persistida con id = " + ed.getId());
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
            emf.close();
        }
    }
}
