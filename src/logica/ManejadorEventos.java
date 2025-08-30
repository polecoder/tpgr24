package logica;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;
import logica.Evento;
import logica.Edicion;

public class ManejadorEventos {
	private 
	static ManejadorEventos instancia;
	Map<String,Evento> eventos;
	ManejadorEventos(){
		 this.eventos = new HashMap<>();
	};
	public static ManejadorEventos getInstancia() {
		if (instancia==null) {
			instancia= new ManejadorEventos();
		}
		return instancia;
	};
	Evento getEventoEdicion(String Evento) {
		return eventos.get(Evento);
	};
	
	Map<String,Evento> getEventos(){
		return eventos;
	}
	public boolean contieneEdicion(String nombreEvento,String NombreEdicion) {
		return eventos.get(nombreEvento).getediciones().containsKey(NombreEdicion);
	};
	
	public Set<DTEvento> listarEventos(){
		//TODO
		return null;
	};
	
	public Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion){
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
		return eventos.get(edicion).getediciones().get(edicion);
	}
	public Set<String> getNombresCategorias(String Nombre) {
		return eventos.get(Nombre).getcategoria().keySet();
	}
	public Set<String> getNombresEdiciones(String nombre){
		return eventos.get(nombre).getediciones().keySet();
	};
	public DTEvento getDTEvento(String nombre) {
		Evento evento=eventos.get(nombre);
		DTEvento dtEvento= new DTEvento(evento.getNombre(), evento.getDescripcion() , evento.getSigla(),evento.getfechaAlta());
		return dtEvento;
	}
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada) {
		Edicion edicion=eventos.get(eventoSeleccionado).getediciones().get(edicionSeleccionada);
		DTEdicionEvento dtEvento= new DTEdicionEvento(edicion.getNombre(),edicion.getSigla(), edicion.getFechaInicio(),edicion.getFechaFin(),edicion.getFechaAlta(),edicion.getCiudad(),edicion.getPais());
		return dtEvento;
	}
	
	public Set<String> getNombresEventos(){
		return eventos.keySet();
	};
	
	public void agregarEvento(Evento evento) {
		eventos.put(evento.getNombre(), evento);
	}

	public void AltaEdicion(String nombreEvento, String nombre, String sigla, LocalDate fechaInicio, LocalDate fechaFin,LocalDate fechaAlt, String ciudad, String pais) {
		Edicion edicion=new Edicion(nombre, sigla, fechaInicio, fechaFin, fechaAlt, ciudad, pais);
		eventos.get(nombreEvento).agregarEdicion(edicion);
	};
	
	public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento, String nombreEdicion) throws NombreTipoRegistroRepetido {
		Evento evento = eventos.get(nombreEvento);
		Edicion edicion = evento.getEdicion(nombreEdicion);
		try {
			edicion.agregarTipoRegistro(nombre, descripcion, costo, cuposMax);
		} catch (NombreTipoRegistroRepetido ex) {
			throw new NombreTipoRegistroRepetido("El nombre del ya está en otro TipoRegistro de esta edicion");
		}
	}
	
	public String[] getTiposRegistro(String nombreEvento, String nombreEdiocion) {
		Evento evento = eventos.get(nombreEvento);
		Edicion edicion = evento.getEdicion(nombreEdiocion);
		Map<String, TipoRegistro> tiposRegistro = edicion.getTiposRegistro();
		
		int i = 0;
		String[] nombresTiposRegistro = new String[tiposRegistro.size()];
		for (Map.Entry<String, TipoRegistro> tipoRegistro : tiposRegistro.entrySet()) {
			nombresTiposRegistro[i] = tipoRegistro.getKey();
			i++;
		}
		return nombresTiposRegistro;
	}
	
	public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombreTipoRegistro) throws TipoRegistroNoExiste {
		Evento evento = eventos.get(nombreEvento);
		try {
			evento.getDTTipoRegistro(nombreEdicion, nombreTipoRegistro);
		} catch (TipoRegistroNoExiste ex) {
			throw new TipoRegistroNoExiste("El Tipo de Registro no existe");
		};
		return evento.getDTTipoRegistro(nombreEdicion, nombreTipoRegistro);
	}
	
    public TipoRegistro encontrarTipoDe(Registro r){
        for (Evento ev : eventos.values()){
            for (Edicion ed : ev.getediciones().values()){
                for (TipoRegistro tr : ed.getTiposRegistro().values()){
                    if (tr.getRegistros().contains(r)) return tr;
                }
            }
        }
        return null;
    }

    public Edicion encontrarEdicionQueContiene(TipoRegistro t){
        for (Evento ev : eventos.values()){
            for (Edicion ed : ev.getediciones().values()){
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
}
