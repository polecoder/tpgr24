package persistencia;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;

@Embeddable
public class IDRegistroArchivado implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "id_asistente")
    private Long idAsistente;
    @Column(name = "id_edicion")
    private Long idEdicion;
    
    // no eliminar
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof IDRegistroArchivado)) return false;
        IDRegistroArchivado that = (IDRegistroArchivado) object;
        return Objects.equals(idAsistente, that.idAsistente) &&
               Objects.equals(idEdicion, that.idEdicion);
    }

    // no eliminar
    @Override
    public int hashCode() {
        return Objects.hash(idAsistente, idEdicion);
    }
}
