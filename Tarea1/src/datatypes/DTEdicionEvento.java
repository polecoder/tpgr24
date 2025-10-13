package datatypes;
import java.util.Date;

public class DTEdicionEvento {
	private String nombre;
	private String sigla;
	private Date fechaInicio;
	private Date fechaFin;
	private String ciudad;
	private String pais;
	private Date fechaAlta;
	private String organizadorNickname;
	private byte[] imagen;
	private EEstadoEdicion estado;


	public DTEdicionEvento(String nombre, String sigla, Date fechaInicio, Date fechaFin, 
			Date fechaAlta, String ciudad, String pais, String organizadorNickname, byte[] imagen,
			 EEstadoEdicion estado) {
	    this.setNombre(nombre);
	    this.setSigla(sigla);
	    this.setFechaInicio(fechaInicio);
	    this.setFechaFin(fechaFin);
	    this.setFechaAlta(fechaAlta);
	    this.setCiudad(ciudad);
	    this.setPais(pais);
	    this.setOrganizadorNickname(organizadorNickname);
	    this.setImagen(imagen); 
	    this.setEstado(estado);
	}

	// === Getters y Setters ===
	public String getNombre() {
		return nombre;	
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
	    return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
	    this.fechaFin = fechaFin;
	}

	public Date getFechaAlta() {
	    return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
	    this.fechaAlta = fechaAlta;
	}

	public String getCiudad() {
	    return ciudad;
	}
	public void setCiudad(String ciudad) {
	    this.ciudad = ciudad;
	}

	public String getPais() {
	    return pais;
	}
	public void setPais(String pais) {
	    this.pais = pais;
	}

	public String getOrganizadorNickname() {
		return organizadorNickname;
	}
	public void setOrganizadorNickname(String organizadorNickname) {
		this.organizadorNickname = organizadorNickname;
	}

	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	public EEstadoEdicion getEstado() {
		return this.estado;
	}
	public void setEstado(EEstadoEdicion estado) {
		this.estado = estado;
	}
	
	/**
	 * Devuelve true si y solo si los datatypes son iguales.
	 */
	public boolean equals(DTEdicionEvento edicion) {
		return this.ciudad == edicion.getCiudad() &&
			   this.fechaAlta == edicion.getFechaAlta() &&
			   this.fechaFin == edicion.getFechaFin() &&
			   this.fechaInicio == edicion.getFechaInicio() &&
			   this.nombre == edicion.getNombre() &&
			   this.organizadorNickname == edicion.getOrganizadorNickname() &&
			   this.pais == edicion.getPais() &&
			   this.sigla == edicion.getSigla();
	}
}
