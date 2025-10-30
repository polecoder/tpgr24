package persistencia;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.*;

public class RegistroArchivado {
	@EmbeddedId
	private IDRegistroArchivado id;
	
	// TODO: Relación con Asistente
	
	@MapsId("id_edicion")
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name= "id_edicion", nullable = false)
	private EdicionArchivada edicion;
	@Column(name = "nombre_tipo_registro", nullable = false)
	private String nombreTipoRegistro;
	@Column(name = "uso_patrocinio", nullable = false)
	private boolean usoPatrocinio;
	@Column(name = "codigo_patrocinio")
	private String codigoPatrocinio;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(nullable = false)
	private float costo;
	
	// no eliminar
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof RegistroArchivado)) return false;
        RegistroArchivado that = (RegistroArchivado) object;
        return Objects.equals(id, that.id);
    }

    // no eliminar
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
