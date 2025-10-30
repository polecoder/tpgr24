package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import excepciones.EdicionNoExiste;
import excepciones.TipoRegistroNoExiste;
import datatypes.DTPatrocinio;
import datatypes.DTTipoRegistro;
import datatypes.EEstadoEvento;

public class Evento {
	private String nombre;
	private String sigla;
	private String descripcion;
	private Date fechaAlta;
	private Map<String, Categoria> categorias;
	private byte[] imagen; 
	private final Map<String, Edicion> ediciones;
	private EEstadoEvento estado = EEstadoEvento.EN_CURSO;
	private int visitas;

	public
	Evento(String nombre, String descripcion , String sigla, Date fechaAlta, byte[] imagen, EEstadoEvento estado, int visitas) {
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setSigla(sigla);
		this.setFechaAlta(fechaAlta);
		this.setImagen(imagen);
		this.categorias = new HashMap<>();
		this.ediciones = new HashMap<>(); 
		this.estado = EEstadoEvento.EN_CURSO;
		this.setVisitas(visitas);
	}
	
	public void registrarVisita() {      
        visitas++;
    }
	
	 public int getVisitas() {
	        return visitas;
	    }

	public String getNombre(){
		return this.nombre;
	}
	public String getSigla(){
		return this.sigla;
	}
	public String getDescripcion(){
		return this.descripcion;
	}
	Date getFechaAlta(){
		return this.fechaAlta;
	}
	public Map<String, Categoria> getCategoria(){
		return this.categorias;
	}
	public Map<String, Edicion> getEdiciones(){
		return this.ediciones;
	}
	public byte[] getImagen() {
		return this.imagen;
	}
	
	public EEstadoEvento getEstado() { 
		return estado; 
	}
	
	public boolean isFinalizado() { 
		return estado == EEstadoEvento.FINALIZADO; 
	}
	
	 public void setVisitas(int visitas) {
	        this.visitas=visitas;
	    }

	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	public void setSigla(String sigla){
		this.sigla = sigla;
	}
	void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
	 public void finalizar() {
	        this.estado = EEstadoEvento.FINALIZADO;
	 }

	public void agregarCategoria(Categoria categoria) {
		this.categorias.put(categoria.getNombre(), categoria);
	}
	public void agregarEdicion(Edicion edicion) {
		if (edicion == null) return;
		this.ediciones.put(edicion.getNombre(), edicion);
	}
	
	public void crearPatrocinio(DTPatrocinio datosPatrocinio) {
		//TODO
	}
	
	public Edicion getEdicion(String nombreEdicion) {
		return ediciones.get(nombreEdicion);
	}
	
	public DTTipoRegistro getDTTipoRegistro(String nombreEdiocion, String nombreTipoRegistro) throws TipoRegistroNoExiste, EdicionNoExiste {
		Edicion edicion = ediciones.get(nombreEdiocion);
		if (edicion == null) { 
			throw new EdicionNoExiste("Edicion no existe"); 
		}
		try {
			return edicion.getDTTipoRegistro(nombreTipoRegistro);
		} catch (TipoRegistroNoExiste ex) {
			throw new TipoRegistroNoExiste("El Tipo de Registro no existe");
		}
	}
}
