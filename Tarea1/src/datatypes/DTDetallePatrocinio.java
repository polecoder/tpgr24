package datatypes;

import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "DTDetallePatrocinio")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTDetallePatrocinio {
	String nombreInstitucion;
	String codigo;
	Date fechaAlta;
	float aporte;
	ENivelPatrocinio nivel;
	int cuposGratuitos;
	String tipoRegistro;

	/**
	 * Constructor sin argumentos requerido por JAXB. NO USAR en código de
	 * aplicación. Usar DTDetallePatrocinio(String, String, ...) en su lugar.
	 */
	public DTDetallePatrocinio() {
	}

	public DTDetallePatrocinio(String nombreInstitucion, String codigo, Date fechaAlta, float aporte,
			ENivelPatrocinio nivel, int cuposGratuitos, String tipoRegistro) {
		this.setNombreInstitucion(nombreInstitucion);
		this.setCodigo(codigo);
		this.setFechaAlta(fechaAlta);
		this.setAporte(aporte);
		this.setNivel(nivel);
		this.setCuposGratuitos(cuposGratuitos);
		this.setTipoRegistro(tipoRegistro);
	}
	
	// Setters
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	
	public void setAporte(float aporte) {
		this.aporte = aporte;
	}
	
	public void setNivel(ENivelPatrocinio nivel) {
		this.nivel = nivel;
	}
	
	public void setCuposGratuitos(int cuposGratuitos) {
		this.cuposGratuitos = cuposGratuitos;
	}
	
	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	
	// Getters
	public String nombreInstitucion() {
		return nombreInstitucion;
	}
	
	public String codigo() {
		return codigo;
	}
	
	public Date fechaAlta() {
		return fechaAlta;
	}
	
	public float aporte() {
		return aporte;
	}
	
	public ENivelPatrocinio nivel() {
		return nivel;
	}
	
	public int cuposGratuitos() {
		return cuposGratuitos;
	}
	
	public String tipoRegistro() {
		return tipoRegistro;
	}
}