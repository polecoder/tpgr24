package persistencia;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "ediciones_archivadas")
public class EdicionArchivada {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String nombre;
	@Column(nullable = false)
	private String sigla;
	@Column(nullable = false)
	private String ciudad;
	@Column(nullable = false)
	private String pais;
	@Column(name = "fecha_inicio", nullable = false)
	private Date fechaInicio;
	@Column(name = "fecha_fin", nullable = false)
	private Date fechaFin;
	@Column(name = "fecha_creacion", nullable = false)
	private Date fechaCreacion;
	@Column(name = "fecha_archivo", nullable = false)
	private Date fechaArchivo;
	@Column(name = "nombre_evento", nullable = false)
	private String nombreEvento;
	
	
	// no eliminar
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof EdicionArchivada)) return false;
        EdicionArchivada that = (EdicionArchivada) object;
        return Objects.equals(id, that.id);
    }

    // no eliminar
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
