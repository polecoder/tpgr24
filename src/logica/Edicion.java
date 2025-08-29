package logica;

import java.time.LocalDate;
import java.util.HashMap;

import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.datatypes.DTEdicion;
import logica.datatypes.DTTipoRegistro;

public class Edicion {
	private String nombre;
	private String sigla;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private LocalDate fechaAlta;
	private String ciudad;
	private String pais;
	private HashMap<String, TipoRegistro> tiposRegistro;
	private HashMap<String, Patrocinio> patrocinios;
	
	public Edicion(String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin, String ciudad, String pais) {
		this.setNombre(nombre);
		this.setSigla(sigla);
		this.setFechaInicio(fechaInicio);
		this.setFechaFin(fechaFin);
		this.setFechaAlta(LocalDate.now());
		this.setCiudad(ciudad);
		this.setPais(pais);
		this.setPatrocinios(new HashMap<>());
		this.setTiposRegistro(new HashMap<>());
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
	
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public LocalDate getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(LocalDate fechaAlta) {
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

	/**
	 * Retorna null en caso de que el Patrocinio no exista en las asociaciones a la edición
	 * 
	 * @param nombrePatrocinio
	 * @return
	 */
	public Patrocinio getPatrocinio(String nombrePatrocinio) {
		return this.patrocinios.get(nombrePatrocinio);
	}
	public HashMap<String, Patrocinio> getPatrocinios() {
		return patrocinios;
	}
	public void setPatrocinios(HashMap<String, Patrocinio> patrocinios) {
		this.patrocinios = patrocinios;
	}
	public void agregarPatrocinio(Patrocinio patrocinio) {
		this.patrocinios.put(patrocinio.getNombre(), patrocinio);
	}
	public void eliminarPatrocinio(String nombrePatrocinio) {
		this.patrocinios.remove(nombrePatrocinio);
	}
	
	/**
	 * Retorna null en caso de que el TipoRegistro no exista en las asociaciones a la edición
	 * 
	 * @param nombreTipoRegistro
	 * @return
	 */
	public TipoRegistro getTipoRegistro(String nombreTipoRegistro) throws TipoRegistroNoExiste{
		if (!this.tiposRegistro.containsKey(nombreTipoRegistro)) {
			throw new TipoRegistroNoExiste("El Tipo de Registro no existe");
		} else {
			return this.tiposRegistro.get(nombreTipoRegistro);
		}
	}
	public HashMap<String, TipoRegistro> getTiposRegistro() {
		return tiposRegistro;
	}
	public void setTiposRegistro(HashMap<String, TipoRegistro> tiposRegistro) {
		this.tiposRegistro = tiposRegistro;
	}
	public void agregarTipoRegistro(String nombre, String descripcion, float costo, int cuposMax) throws NombreTipoRegistroRepetido {
		if (this.tiposRegistro.containsKey(nombre)) {
			throw new NombreTipoRegistroRepetido("El nombre del ya está en otro TipoRegistro de esta edicion");
		} else {
			TipoRegistro tipoRegistro = new TipoRegistro(nombre, descripcion, costo, cuposMax);
			this.tiposRegistro.put(nombre, tipoRegistro);
		}
	}
	public void eliminarTipoRegistro(String nombreTipoRegistro) {
		this.tiposRegistro.remove(nombreTipoRegistro);
	}
	
	// TODO: Implementar
	public DTEdicion listarEdicion() {
		return null;
	}
	
	// TODO: Implementar y revisar el nombre
	public boolean validar(String nicknameAsistente, String nombreTipoRegistro) {
		return false;
	}
	
	// TODO: Implementar y revisar el nombre
	public boolean check(String codigoPatrocinio, String nicknameAsistente) {
		return false;
	}
	
	public DTTipoRegistro getDTTipoRegistro(String nombreTipoRegistro) throws TipoRegistroNoExiste {
		try {
			TipoRegistro tipoRegistro = getTipoRegistro(nombreTipoRegistro);
		} catch (TipoRegistroNoExiste ex) {
			throw new TipoRegistroNoExiste("El Tipo de Registro no existe");
		};
		TipoRegistro tipoRegistro = getTipoRegistro(nombreTipoRegistro);
		return tipoRegistro.getDTTipoRegistro();
	}
}
