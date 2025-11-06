package persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

/**
 * AsistenteArchivado comparte la PK con UsuarioArchivado:<p>
 * La columna id_usuario es PK en asistentes y FK hacia usuarios(id_usuario).<p>
 * Se mapea con @MapsId para indicar que la FK también es la PK de Organizador.<p>
 */
@Entity
@Table(name = "asistentes")
public class AsistenteArchivado {
	@Id
	@Column(name = "id_usuario")
	private Long id;
	@OneToOne(cascade = CascadeType.PERSIST, optional = false)
	@MapsId
	@JoinColumn(name = "id_usuario", nullable = false)
	private UsuarioArchivado usuario;
	// IMPORTANTE: el valor de mappedBy tiene que tener el mismo nombre que el que le corresponde en RegistroArchivado.java
	@OneToMany(mappedBy = "asistente", cascade = CascadeType.PERSIST)
	private List<RegistroArchivado> registros = new ArrayList<>();
	@Column(nullable = false)
	private String apellido;
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento", nullable = false)
	private Date fechaNacimiento;
	@Column(name = "nombre_institucion")
	private String nombreInstitucion;
	
	// no eliminar
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof AsistenteArchivado)) return false;
        AsistenteArchivado that = (AsistenteArchivado) object;
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
 	public List<RegistroArchivado> getRegistros() {
 		return registros;
 	}
 	public String getApellido() {
 		return apellido;
 	}
 	public Date getFechaNacimiento() {
 		return fechaNacimiento;
 	}
 	public String getNombreInstitucion() {
 		return nombreInstitucion;
 	}

 	// setters
 	public void setUsuario(UsuarioArchivado usuario) {
 		this.usuario = usuario;
 	}
 	public void setRegistros(List<RegistroArchivado> registros) {
 		this.registros = registros;
 	}
 	public void agregarRegistro(RegistroArchivado registro) {
 		this.registros.add(registro);
 	}
 	public void setApellido(String apellido) {
 		this.apellido = apellido;
 	}
 	public void setFechaNacimiento(Date fechaNacimiento) {
 		this.fechaNacimiento = fechaNacimiento;
 	}
 	public void setNombreInstitucion(String nombreInstitucion) {
 		this.nombreInstitucion = nombreInstitucion;
 	}
}
