package persistencia;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "registros")
public class RegistroArchivado {
	@EmbeddedId
	private IDRegistroArchivado id;
	// IMPORTANTE: el valor de @MapsId tiene que tener el mismo nombre que el que le corresponde en IDRegistroArchivado.java
	@MapsId("idAsistente")
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name= "id_asistente", nullable = false)
	private AsistenteArchivado asistente;
	// IMPORTANTE: el valor de @MapsId tiene que tener el mismo nombre que el que le corresponde en IDRegistroArchivado.java
	@MapsId("idEdicion")
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name= "id_edicion", nullable = false)
	private EdicionArchivada edicion;
	
	@Column(name = "nombre_tipo_registro", nullable = false)
	private String nombreTipoRegistro;
	@Column(name = "uso_patrocinio", nullable = false)
	private boolean usoPatrocinio;
	@Column(name = "codigo_patrocinio")
	private String codigoPatrocinio;
	@Temporal(TemporalType.DATE)
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
    
    // getters
	public AsistenteArchivado getAsistente() {
		return asistente;
	}
	public EdicionArchivada getEdicion() {
		return edicion;
	}
	public String getNombreTipoRegistro() {
		return nombreTipoRegistro;
	}
	public boolean getUsoPatrocinio() {
		return usoPatrocinio;
	}
	public String getCodigoPatrocinio() {
		return codigoPatrocinio;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public float getCosto() {
		return costo;
	}

	// setters
	public void setAsistente(AsistenteArchivado asistente) {
		this.asistente = asistente;
	}

	public void setEdicion(EdicionArchivada edicion) {
		this.edicion = edicion;
	}

	public void setNombreTipoRegistro(String nombreTipoRegistro) {
		this.nombreTipoRegistro = nombreTipoRegistro;
	}

	public void setUsoPatrocinio(boolean usoPatrocinio) {
		this.usoPatrocinio = usoPatrocinio;
	}

	public void setCodigoPatrocinio(String codigoPatrocinio) {
		this.codigoPatrocinio = codigoPatrocinio;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}
}
