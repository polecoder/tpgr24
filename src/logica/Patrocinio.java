package logica;

import java.util.Date;

public class Patrocinio {
	String codigoPatrocinio;
	Date fechaPatrocinio;
	float aporte;
	ENivelPatrocinio nivel;
	int cuposGratuitos;
	Institucion institucion;
	
	public Patrocinio(String codigoPatrocinio, Date fechaPatrocinio, float aporte, ENivelPatrocinio nivel,
			int cuposGratuitos, Institucion institucion) {
		this.codigoPatrocinio = codigoPatrocinio;
		this.fechaPatrocinio = fechaPatrocinio;
		this.aporte = aporte;
		this.nivel = nivel;
		this.cuposGratuitos = cuposGratuitos;
		this.institucion = institucion;
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
			System.out.printf("ERROR: Patrocinio::checkAsistente() ERROR {0}", CAError);
			return false;
		}
	}
}