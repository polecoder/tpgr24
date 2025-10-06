package logica;

import java.util.Date;

import datatypes.ENivelPatrocinio;


public class Patrocinio {
	String codigoPatrocinio;
	Date fechaPatrocinio;
	float aporte;
	ENivelPatrocinio nivel;
	int cuposGratuitos;
	Institucion institucion;
	TipoRegistro tipoRegistro;
	
	public Patrocinio(String codigoPatrocinio, Date fechaPatrocinio, float aporte, ENivelPatrocinio nivel,
			int cuposGratuitos, Institucion institucion, TipoRegistro tipoRegistro) {
		this.setCodigoPatrocinio(codigoPatrocinio);
		this.setFechaPatrocinio(fechaPatrocinio);
		this.setAporte(aporte);
		this.setNivel(nivel);
		this.setCuposGratuitos(cuposGratuitos);
		this.setInstitucion(institucion);
		this.setTipoRegistro(tipoRegistro);
	}
	
	public String getCodigoPatrocinio() {
		return codigoPatrocinio;
	}
	public void setCodigoPatrocinio(String codigoPatrocinio) {
		this.codigoPatrocinio = codigoPatrocinio;
	}
	public Date getFechaPatrocinio() {
		return fechaPatrocinio;
	}
	public void setFechaPatrocinio(Date fechaPatrocinio) {
		this.fechaPatrocinio = fechaPatrocinio;
	}
	public float getAporte() {
		return aporte;
	}
	public void setAporte(float aporte) {
		this.aporte = aporte;
	}
	public ENivelPatrocinio getNivel() {
		return nivel;
	}
	public void setNivel(ENivelPatrocinio nivel) {
		this.nivel = nivel;
	}
	public int getCuposGratuitos() {
		return cuposGratuitos;
	}
	public void setCuposGratuitos(int cuposGratuitos) {
		this.cuposGratuitos = cuposGratuitos;
	}
	
	public Institucion getInstitucion() {
		return institucion;
	}
	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
	public void setCupos(int cupos) {this.cuposGratuitos = cupos;}
	
	public boolean checkAsistente(String nicknameAsistente) {
		try {
			return institucion.checkAsistente(nicknameAsistente);
		}catch(Exception CAError) {
			return false;
		}
	}
	public void setTipoRegistro(TipoRegistro tipoRegistro) { this.tipoRegistro = tipoRegistro;}
	public TipoRegistro getTipoRegistro() { return tipoRegistro;}
}