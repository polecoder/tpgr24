package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTTipoRegistro;

public class Edicion {
	private String nombre;
	private String sigla;
	private Date fechaInicio;
	private Date fechaFin;
	private Date fechaAlta;
	private String ciudad;
	private String pais;
	private HashMap<String, TipoRegistro> tiposRegistro;
	private HashMap<String, Patrocinio> patrocinios;
	private String organizadorNickname;
	
	public Edicion(String nombre, String sigla, Date fechaInicio, Date fechaFin, Date fechaAlta, String ciudad, String pais, String organizadorNickname) {
		this.setNombre(nombre);
		this.setSigla(sigla);
		this.setFechaInicio(fechaInicio);
		this.setFechaFin(fechaFin);
		this.setFechaAlta(fechaAlta);
		this.setCiudad(ciudad);
		this.setPais(pais);
		this.setPatrocinios(new HashMap<>());
		this.setTiposRegistro(new HashMap<>());
		this.setOrganizadorNickname(organizadorNickname);
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
		this.patrocinios.put(patrocinio.getCodigoPatrocinio(), patrocinio);
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
	public void agregarTipoRegistro(String nombre, String descripcion, float costo, int cuposMax) throws NombreTipoRegistroRepetido { //Con Datos
		if (this.tiposRegistro.containsKey(nombre)) {
			throw new NombreTipoRegistroRepetido("El nombre del ya está en otro TipoRegistro de esta edicion");
		} else {
			TipoRegistro tipoRegistro = new TipoRegistro(nombre, descripcion, costo, cuposMax, cuposMax);
			this.tiposRegistro.put(nombre, tipoRegistro);
		}
	}
	public void agregarTipoRegistro(TipoRegistro tipoRegistro) { // Con TipoRegistro
		if(tipoRegistro==null||tipoRegistro.getNombre()==null) return;
		this.tiposRegistro.put(tipoRegistro.getNombre(), tipoRegistro);
	}
	public void eliminarTipoRegistro(String nombreTipoRegistro) {
		this.tiposRegistro.remove(nombreTipoRegistro);
	}
	
	// TODO: Implementar
	public DTEdicionEvento listarEdicion() {
		return null;
	}
	
	// TODO: Implementar y revisar el nombre
	public boolean validar(String nicknameAsistente, String nombreTipoRegistro) {
		TipoRegistro tr = tiposRegistro.get(nombreTipoRegistro);
		int cupos = tr.getCuposDisponibles();
		if(cupos>0) {
			return tr.validar(nicknameAsistente);
		}else {
			System.out.print("Edicion::validar(): No hay cupos disponibles");
			return false;
		}
	}
	
	// TODO: Implementar y revisar el nombre
	public boolean check(String codigoPatrocinio, String nicknameAsistente) {
		boolean pertenece =  patrocinios.containsKey(codigoPatrocinio);
		if(pertenece) {
			Patrocinio p = patrocinios.get(codigoPatrocinio);
			boolean checkA = p.checkAsistente(nicknameAsistente);
			return checkA && pertenece;			
		}else {
			return false;
		}
	}
	
	public Map<String,DTTipoRegistro> listarTipoRegistro() {
		Map<String, DTTipoRegistro> mList = new HashMap<>();
		for(TipoRegistro r : tiposRegistro.values()) {
			String nombre = r.getNombre();
			String descripcion = r.getDescripcion();
			float costo = r.getCosto();
			int cupoM = r.getCuposMax();
			int cupoD = r.getCuposDisponibles();
			mList.put(nombre, new DTTipoRegistro(nombre, descripcion, costo, cupoM, cupoD));
		}
		return mList;
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
	
	public String getOrganizadorNickname() {return organizadorNickname;}
	public void setOrganizadorNickname(String organizadorNickname) {this.organizadorNickname=organizadorNickname;}
}
