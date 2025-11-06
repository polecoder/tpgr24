package persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

/**
 * OrganizadorArchivado comparte la PK con UsuarioArchivado:<p>
 * La columna id_usuario es PK en organizadores y FK hacia usuarios(id_usuario).<p>
 * Se mapea con @MapsId para indicar que la FK también es la PK de Organizador.<p>
 */
@Entity
@Table(name = "organizadores")
public class OrganizadorArchivado {
	@Id
	@Column(name = "id_usuario")
	private Long id;
	@OneToOne(cascade = CascadeType.PERSIST, optional = false)
	@MapsId
	@JoinColumn(name = "id_usuario", nullable = false)
	private UsuarioArchivado usuario;
	@Column(nullable = false)
	private String descripcion;
	@Column(name="sitio_web")
	private String sitioWeb;
	// IMPORTANTE: el valor de mappedBy tiene que tener el mismo nombre que el que le corresponde en EdicionArchivada.java
	@OneToMany(mappedBy = "organizador", cascade = CascadeType.PERSIST)
	private List<EdicionArchivada> ediciones = new ArrayList<>();
	
	// no eliminar
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof OrganizadorArchivado)) return false;
        OrganizadorArchivado that = (OrganizadorArchivado) object;
        return Objects.equals(id, that.id);
    }

    // no eliminar
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    // getters
 	public UsuarioArchivado getUsuario() {
 		return usuario;
 	}
 	public String getDescripcion() {
 		return descripcion;
 	}
 	public String getSitioWeb() {
 		return sitioWeb;
 	}
 	public List<EdicionArchivada> getEdiciones() {
 		return ediciones;
 	}

 	// setters
 	public void setUsuario(UsuarioArchivado usuario) {
 		this.usuario = usuario;
 	}
 	public void setDescripcion(String descripcion) {
 		this.descripcion = descripcion;
 	}
 	public void setSitioWeb(String sitioWeb) {
 		this.sitioWeb = sitioWeb;
 	}
 	public void agregarEdicion(EdicionArchivada edicion) {
 		ediciones.add(edicion);
 	}
}
