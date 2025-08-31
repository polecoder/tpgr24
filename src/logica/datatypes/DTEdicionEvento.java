package logica.datatypes;
import java.util.Date;

public class DTEdicionEvento {
	private String nombre;
	private String sigla;
	private Date fechaInicio;
	private Date fechaFin;
	private String ciudad;
	private String pais;
	private Date fechaAlta;
	
	public DTEdicionEvento(String nombre, String sigla, Date fechaInicio, Date fechaFin, Date fechaAlta, String ciudad, String pais) {
	    this.setNombre(nombre);
	    this.setSigla(sigla);
	    this.setFechaInicio(fechaInicio);
	    this.setFechaFin(fechaFin);
	    this.setFechaAlta(fechaAlta);
	    this.setCiudad(ciudad);
	    this.setPais(pais);
	}
	
	

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
}
