package logica;

import java.util.Date;

public class Patrocinio {
	String codigoPatrocinio;
	Date fechaPatrocinio;
	float aporte;
	ENivelPatrocinio nivel;
	int cuposGratuitos;
	
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
}
