package persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name="id_organizador")
	private OrganizadorArchivado organizador;
	// IMPORTANTE: el valor de mappedBy tiene que tener el mismo nombre que el que le corresponde en RegistroArchivado.java
	@OneToMany(mappedBy = "edicion", cascade = CascadeType.PERSIST)
	private List<RegistroArchivado> registros = new ArrayList<>();
	
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
