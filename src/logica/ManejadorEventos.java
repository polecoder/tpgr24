package logica;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTAsistente;
import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

public class ManejadorEventos {
	private 
	static ManejadorEventos instancia;
	Map<String,Evento> eventos;
	Map<String, Categoria> categorias;
	ManejadorEventos(){
		  this.eventos = new HashMap<>();
		  this.categorias = new HashMap<>();
	};
	public static ManejadorEventos getInstancia() {
		if (instancia==null) {
			instancia= new ManejadorEventos();
		}
		return instancia;
	};
	
	// TODO: Hacer Setters
	// TODO: Hacer Getters
	Evento getEventoEdicion(String Evento) {
		return eventos.get(Evento);
	};
	
	Map<String,Evento> getEventos(){
		return eventos;
	}
	public boolean contieneEdicion(String nombreEvento,String NombreEdicion) {
		return eventos.get(nombreEvento).getEdiciones().containsKey(NombreEdicion);
	};
	
	public HashMap<String,DTEvento> listarEventos(){
		HashMap<String, DTEvento> hMLista = new HashMap<>();
		for (Evento e : eventos.values()) {
			String nombre = e.getNombre();
			String descripcion = e.getDescripcion();
			String sigla = e.getSigla();
			Date fAlta = e.getFechaAlta();
			DTEvento dtEv = new DTEvento(nombre, descripcion, sigla, fAlta);
			hMLista.put(nombre, dtEv);  
		}
		return hMLista;
	};
	
	public Map<String,DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion){
		//TODO
		return null;
	};
	
	public boolean Validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro){
		//TODO
		return true;
	};

	public void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion) {
		//TODO
	};
	
	
	public Registro getRegistro(String nombreEdicion,String nickAsistente){
		//TODO
		return null;
	};
	
	public Edicion getEdicion(String evento, String edicion){
		return eventos.get(evento).getEdiciones().get(edicion);
	}
	public Set<String> getNombresCategorias(String Nombre) {
		return eventos.get(Nombre).getCategoria().keySet();
	}
	public Set<String> getNombresEdiciones(String nombre) throws EventoNoExiste {
		if (eventos.get(nombre) == null) { throw new EventoNoExiste("Edicion no existe"); }
		return eventos.get(nombre).getEdiciones().keySet();
	};
	public DTEvento getDTEvento(String nombre) {
		Evento evento=eventos.get(nombre);
		DTEvento dtEvento= new DTEvento(evento.getNombre(), evento.getDescripcion() , evento.getSigla(),evento.getFechaAlta());
		return dtEvento;
	}
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada) {
		Edicion edicion=eventos.get(eventoSeleccionado).getEdiciones().get(edicionSeleccionada);
		DTEdicionEvento dtEvento= new DTEdicionEvento(edicion.getNombre(),edicion.getSigla(), edicion.getFechaInicio(),edicion.getFechaFin(),edicion.getFechaAlta(),edicion.getCiudad(),edicion.getPais());
		return dtEvento;
	}
	
	public Set<String> getNombresEventos(){
		return eventos.keySet();
	};
	
	public void agregarEvento(Evento evento) {
		eventos.put(evento.getNombre(), evento);
	}
	
	public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento, String nombreEdicion) throws NombreTipoRegistroRepetido {
		Evento evento = eventos.get(nombreEvento);
		Edicion edicion = evento.getEdicion(nombreEdicion);
		try {
			edicion.agregarTipoRegistro(nombre, descripcion, costo, cuposMax);
		} catch (NombreTipoRegistroRepetido ex) {
			throw new NombreTipoRegistroRepetido("El nombre del ya está en otro TipoRegistro de esta edicion");
		}
	}
	
	public String[] getTiposRegistro(String nombreEvento, String nombreEdiocion) throws EventoNoExiste, EdicionNoExiste {
		Evento evento = eventos.get(nombreEvento);
		if (evento == null) { throw new EventoNoExiste("Evento no existe"); }
		
		Edicion edicion = evento.getEdicion(nombreEdiocion);
		if (edicion == null) { throw new EdicionNoExiste("Edicion no existe"); };
		Map<String, TipoRegistro> tiposRegistro = edicion.getTiposRegistro();
		
		int i = 0;
		String[] nombresTiposRegistro = new String[tiposRegistro.size()];
		for (Map.Entry<String, TipoRegistro> tipoRegistro : tiposRegistro.entrySet()) { 
			nombresTiposRegistro[i] = tipoRegistro.getKey();
			i++;
		}
		return nombresTiposRegistro;
	}
	
	public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombreTipoRegistro) throws TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste {
		Evento evento = eventos.get(nombreEvento);
		if (evento == null) { throw new EventoNoExiste("Evento no existe"); }
		try { evento.getDTTipoRegistro(nombreEdicion, nombreTipoRegistro);
		} catch (TipoRegistroNoExiste ex) {throw ex;
		} catch (EdicionNoExiste ex) { throw ex;
		};
		return evento.getDTTipoRegistro(nombreEdicion, nombreTipoRegistro);
	}
	
    public TipoRegistro encontrarTipoDe(Registro r){
        for (Evento ev : eventos.values()){
            for (Edicion ed : ev.getEdiciones().values()){
                for (TipoRegistro tr : ed.getTiposRegistro().values()){
                    if (tr.getRegistros().contains(r)) return tr;
                }
            }
        }
        return null;
    }

    public Edicion encontrarEdicionQueContiene(TipoRegistro t){
        for (Evento ev : eventos.values()){
            for (Edicion ed : ev.getEdiciones().values()){
                if (ed.getTiposRegistro().containsValue(t)) return ed;
            }
        }
        return null;
    }

    public String nombreEdicionDeRegistro(Registro r){
        TipoRegistro t = encontrarTipoDe(r);
        if (t == null) return "(sin edición)";
        Edicion ed = encontrarEdicionQueContiene(t);
        return (ed != null) ? ed.getNombre() : "(sin edición)";
    }

    public String nombreTipoDeRegistro(Registro r){
        TipoRegistro t = encontrarTipoDe(r);
        return (t != null) ? t.getNombre() : "(sin tipo)";
    }
	
	/**
     * Devuelve true si existe un evento e en el sistema tal que e.nombre = nombreEvento.
     * En caso contrario devuelve false
     * 
     * @param nombreEvento
     * @return
     */
    public boolean existeEvento(String nombreEvento) {
    	return this.eventos.containsKey(nombreEvento);
    }
	
	/**
	 * Retorna el evento e en el sistema tal que e.nombre = nombreEvento.
	 * Si el evento no existe, retorna null
	 * 
	 * @param nombreEvento
	 * @return
	 */
	public Evento getEvento(String nombreEvento) {
		if (existeEvento(nombreEvento)) {
			return this.eventos.get(nombreEvento);
		}
		return null;
	}
	
	/**
	 * Agrega la categoría c tal que c.nombre = nombreCategoria al evento e tal que e.nombre = nombreEvento.
	 * También ingresa el Evento en la colección de Eventos de la categoría.
	 * Si alguno de los dos elementos del sistema no existe, no hace nada.
	 * 
	 * @param nombreEvento
	 * @param nombreCategoria
	 */
	public void agregarCategoriaAEvento(String nombreEvento, String nombreCategoria) {
		if (this.eventos.containsKey(nombreEvento) && this.categorias.containsKey(nombreCategoria)) {
			Evento evento = this.eventos.get(nombreEvento);
			Categoria categoria = this.categorias.get(nombreCategoria);
			evento.agregarCategoria(categoria);
			categoria.agregarEvento(evento);
		}
	}
	
	/**
	 * PRE-CONDICIÓN: Los datos ingresados por parámetro están formateados correctamente.
	 * Da de alta un Evento con los parámetros indicados en el sistema.
	 * Si ya existe un Evento con el nombre indicado en el sistema, no hace nada.
	 * 
	 * @param nombre
	 * @param descripcion
	 * @param fechaAlta
	 * @param sigla
	 * @param categorias
	 */
	public void altaEvento(String nombre, String descripcion, Date fechaAlta, String sigla, List<String> categorias) {
		if (!existeEvento(nombre)) {
			Evento evento = new Evento(nombre, descripcion, sigla, fechaAlta);
			this.eventos.put(nombre, evento);
			for (String nombreCategoria: categorias) {
				agregarCategoriaAEvento(nombre, nombreCategoria);
			}
		}
	}
  
  public void altaEdicion(String nombreEvento, String nombre, String sigla, Date fechaInicio, Date fechaFin,Date fechaAlt, String ciudad, String pais) {
		Edicion edicion = new Edicion(nombre, sigla, fechaInicio, fechaFin, fechaAlt, ciudad, pais);
		eventos.get(nombreEvento).agregarEdicion(edicion);
	};
		
	public void altaPatrocinio(String codigoPatrocinio, Date fechaPatrocinio, float aporte, ENivelPatrocinio nivel,
			int cuposGratuitos, Institucion institucion, Edicion edicion) {
		Patrocinio patrocinio = new Patrocinio(codigoPatrocinio, fechaPatrocinio, aporte, nivel, cuposGratuitos, institucion);
		edicion.agregarPatrocinio(patrocinio);
	}
	
	public void altaRegistro(Asistente asistente, TipoRegistro tipoRegistro, Patrocinio patrocinio, Date fecha, float costo) {
		Registro registro = new Registro(fecha, costo, asistente, patrocinio);
		tipoRegistro.addRegistro(registro);
		asistente.addRegistro(registro);
	}
	
	/**
	 * Crea una categoría de nombre "nombreCategoria" y la agrega a la colección de categorías.
	 * 
	 * @param nombreCategoria
	 */
	public void altaCategoria(String nombreCategoria) {
		Categoria categoria = new Categoria(nombreCategoria);
		this.categorias.put(nombreCategoria, categoria);
	}
	
	public boolean existeCategoria(String nombreCategoria) {
		return this.categorias.containsKey(nombreCategoria);
	}
	public Map<String, Categoria> getCategorias() {
		return this.categorias;
	}

}
