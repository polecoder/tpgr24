package persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import datatypes.DTEdicionEvento;
import datatypes.EEstadoEdicion;
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
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_inicio", nullable = false)
	private Date fechaInicio;
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_fin", nullable = false)
	private Date fechaFin;
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_creacion", nullable = false)
	private Date fechaCreacion;
	@Temporal(TemporalType.DATE)
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
    
    // getters
    public Long getId() {
		return id;
	}
 	public String getNombre() {
 		return nombre;
 	}
 	public String getSigla() {
 		return sigla;
 	}
 	public String getCiudad() {
 		return ciudad;
 	}
 	public String getPais() {
 		return pais;
 	}
 	public Date getFechaInicio() {
 		return fechaInicio;
 	}
 	public Date getFechaFin() {
 		return fechaFin;
 	}
 	public Date getFechaCreacion() {
 		return fechaCreacion;
 	}
 	public Date getFechaArchivo() {
 		return fechaArchivo;
 	}
 	public String getNombreEvento() {
 		return nombreEvento;
 	}
 	public OrganizadorArchivado getOrganizador() {
 		return organizador;
 	}
 	public List<RegistroArchivado> getRegistros() {
 		return registros;
 	}

 	// setters
 	public void setId(Long id) {
		this.id = id;
	}
 	public void setNombre(String nombre) {
 		this.nombre = nombre;
 	}
 	public void setSigla(String sigla) {
 		this.sigla = sigla;
 	}
 	public void setCiudad(String ciudad) {
 		this.ciudad = ciudad;
 	}
 	public void setPais(String pais) {
 		this.pais = pais;
 	}
 	public void setFechaInicio(Date fechaInicio) {
 		this.fechaInicio = fechaInicio;
 	}
 	public void setFechaFin(Date fechaFin) {
 		this.fechaFin = fechaFin;
 	}
 	public void setFechaCreacion(Date fechaCreacion) {
 		this.fechaCreacion = fechaCreacion;
 	}
 	public void setFechaArchivo(Date fechaArchivo) {
 		this.fechaArchivo = fechaArchivo;
 	}
 	public void setNombreEvento(String nombreEvento) {
 		this.nombreEvento = nombreEvento;
 	}
 	public void setOrganizador(OrganizadorArchivado organizador) {
 		this.organizador = organizador;
 	}
 	public void setRegistros(List<RegistroArchivado> registros) {
 		this.registros = registros;
 	}
 	
 	// métodos
 	
 	/**
 	 * Crea un `DTEdicionEvento` con los datos asociados a la edición.<p>
 	 * El `DTEdicionEvento` se crea con la edición en `Archivada`.
 	 * 
 	 * @return los datos de la edición
 	 */
 	public DTEdicionEvento getDatosEdicion() {
 		OrganizadorArchivado organizador = this.organizador;
 		UsuarioArchivado usuario = organizador.getUsuario();
 		String nicknameOrganizador = usuario.getNickname();
 		return new DTEdicionEvento(this.getNombre(), this.getSigla(), this.getFechaInicio(), this.getFechaFin(), this.getFechaCreacion(), this.getCiudad(), this.getPais(), nicknameOrganizador, null, EEstadoEdicion.Archivada, null);
 	}
}
