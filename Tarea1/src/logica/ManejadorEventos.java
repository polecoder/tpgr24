package logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import datatypes.DTDetalleRegistro;
import datatypes.DTEdicionEvento;
import datatypes.DTEvento;
import datatypes.DTTipoRegistro;
import datatypes.EEstadoEdicion;
import datatypes.ENivelPatrocinio;

public class ManejadorEventos {
	private 
	static ManejadorEventos instancia;
	private Map<String, Evento> eventos;
	private Map<String, Categoria> categorias;

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
	
	public static void resetInstancia() {
		instancia = null;
	}
	
	Map<String, Evento> getEventos(){
		return eventos;
	}
	
	public boolean contieneEdicion(String nombreEvento, String NombreEdicion) {
		return eventos.get(nombreEvento).getEdiciones().containsKey(NombreEdicion);
	};
	
	public boolean existeEdicion(String nombreEdicion) {
		for (String evento : this.eventos.keySet()) {
			if (contieneEdicion(this.eventos.get(evento).getNombre(), nombreEdicion)) {
				return true;
			}
		}
		return false;
	}
	
	public Map<String, DTEvento> listarEventos() {
		Map<String, DTEvento> hMLista = new HashMap<>();
		for (Evento e : eventos.values()) {
			String nombre = e.getNombre();
			String descripcion = e.getDescripcion();
			String sigla = e.getSigla();
			Date fAlta = e.getFechaAlta();
			byte[] imagen = e.getImagen(); // ← nuevo
			Map<String, Categoria> categorias=e.getCategoria();
			DTEvento dtEv = new DTEvento(nombre, descripcion, sigla, fAlta,categorias.keySet(), imagen); 
			hMLista.put(nombre, dtEv);  
		}
		return hMLista;
	}
	
	public Map<String, DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion){
		Evento evento = eventos.get(nombreEvento);
    	Edicion edicion = evento.getEdicion(nombreEdicion);
    	return edicion.listarTipoRegistro();
	};
	
	/**
	 * Devuelve la edición de nombre "edicion" asociada al evento de nombre "evento".
	 * Si no existe alguno de los objetos "evento" o "edicion" retorna null.
	 * 
	 * @param evento
	 * @param edicion
	 * @return
	 */
	public Edicion getEdicion(String evento, String edicion){
		if (existeEvento(evento) && contieneEdicion(evento, edicion)) {
			return eventos.get(evento).getEdiciones().get(edicion);
		}
		return null;
	}
	
	public Set<String> getNombresCategorias(String Nombre) {
		return eventos.get(Nombre).getCategoria().keySet();
	}
	
	public Set<String> getNombresEdiciones(String nombre) throws EventoNoExiste {
		if (eventos.get(nombre) == null) { throw new EventoNoExiste("Evento no existe."); }
		return eventos.get(nombre).getEdiciones().keySet();
	}
	
	public DTEvento getDTEvento(String nombre) {
		Evento evento = eventos.get(nombre);
		if (evento == null) {
			return null;
		}

		byte[] imagen = evento.getImagen(); 
		DTEvento dtEvento = new DTEvento(
			evento.getNombre(),
			evento.getDescripcion(),
			evento.getSigla(),
			evento.getFechaAlta(),
			evento.getCategoria().keySet(),
			imagen 
		);
		
		return dtEvento;
	}

	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada) {
		Edicion edicion=eventos.get(eventoSeleccionado).getEdiciones().get(edicionSeleccionada);
		DTEdicionEvento dtEdicion= new DTEdicionEvento(edicion.getNombre(),edicion.getSigla(), edicion.getFechaInicio(),edicion.getFechaFin(),edicion.getFechaAlta(),edicion.getCiudad(),edicion.getPais(), edicion.getOrganizadorNickname(),edicion.getImagen(), edicion.getEstado());
		return dtEdicion;
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
	
	public String[] getTiposRegistro(String nombreEvento, String nombreEdicion) throws EventoNoExiste, EdicionNoExiste {
		Evento evento = eventos.get(nombreEvento);
		if (evento == null) { throw new EventoNoExiste("Evento no existe"); }
		
		Edicion edicion = evento.getEdicion(nombreEdicion);
		if (edicion == null) { throw new EdicionNoExiste("Edicion no existe"); 
		}
		;
		Map<String, TipoRegistro> tiposRegistro = edicion.getTiposRegistro();
		
		int contador = 0;
		String[] nombresTiposRegistro = new String[tiposRegistro.size()];
		for (Map.Entry<String, TipoRegistro> tipoRegistro : tiposRegistro.entrySet()) { 
			nombresTiposRegistro[contador] = tipoRegistro.getKey();
			contador++;
		}
		return nombresTiposRegistro;
	}
	
	public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombreTipoRegistro) throws TipoRegistroNoExiste, EventoNoExiste, EdicionNoExiste {
		Evento evento = eventos.get(nombreEvento);
		if (evento == null) { throw new EventoNoExiste("Evento no existe"); }
		try { evento.getDTTipoRegistro(nombreEdicion, nombreTipoRegistro);
		} catch (TipoRegistroNoExiste ex) {throw ex;
		} catch (EdicionNoExiste ex) { throw ex;
		}
		;
		return evento.getDTTipoRegistro(nombreEdicion, nombreTipoRegistro);
	}
	
    public TipoRegistro encontrarTipoDe(Registro registro){
        for (Evento ev : eventos.values()){
            for (Edicion ed : ev.getEdiciones().values()){
                for (TipoRegistro tr : ed.getTiposRegistro().values()){
                    if (tr.getRegistros().contains(registro)) return tr;
                }
            }
        }
        return null;
    }

    public Edicion encontrarEdicionQueContiene(TipoRegistro tiporeg){
        for (Evento ev : eventos.values()){
            for (Edicion ed : ev.getEdiciones().values()){
                if (ed.getTiposRegistro().containsValue(tiporeg)) return ed;
            }
        }
        return null;
    }

    public String nombreEdicionDeRegistro(Registro registro){
        TipoRegistro tiporegistro = encontrarTipoDe(registro);
        if (tiporegistro == null) return "(sin edición)";
        Edicion edicion = encontrarEdicionQueContiene(tiporegistro);
        return (edicion != null) ? edicion.getNombre() : "(sin edición)";
    }

    public String nombreTipoDeRegistro(Registro registro){
        TipoRegistro tiporegistro = encontrarTipoDe(registro);
        return (tiporegistro != null) ? tiporegistro.getNombre() : "(sin tipo)";
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
	public void altaEvento(String nombre, String descripcion, Date fechaAlta, String sigla, List<String> categorias, byte[] imagen) {
		if (!existeEvento(nombre)) {
			Evento evento = new Evento(nombre, descripcion, sigla, fechaAlta, imagen);
			this.eventos.put(nombre, evento);

			for (String nombreCategoria : categorias) {
				agregarCategoriaAEvento(nombre, nombreCategoria);
			}
		}
	}
	public Edicion altaEdicion(String nombreEvento, String nombre, String sigla, Date fechaInicio, Date fechaFin, Date fechaAlt, String ciudad, String pais, String organizadorNickname, byte[] imagen) {  
	    Edicion edicion = new Edicion( nombre,  sigla, fechaInicio,  fechaFin, fechaAlt, ciudad, pais, organizadorNickname, nombreEvento, imagen );
	    this.eventos.get(nombreEvento).agregarEdicion(edicion);
	    return edicion;
	}
	public Set<String> getNombresTipoRegistro(String eventoSeleccionado, String edicionSeleccionada) {
			return eventos.get(eventoSeleccionado).getEdicion(edicionSeleccionada).getTiposRegistro().keySet();
		}

	public void altaPatrocinio(String eventoSeleccionado, Date FechaAlta, String edicionSeleccionada, String institucionSeleccionada, ENivelPatrocinio nivelPatrocinio, float aporteEconomico, int cantidadGratuitos, String codigoPatrocinio, String tipoRegistroSeleccionado) throws TipoRegistroNoExiste{
		try {
			Fabrica fabrica=Fabrica.getInstancia();
			IControladorInstitucion IContInstitucion= fabrica.getControladorInstitucion();
			Institucion institucion =IContInstitucion.getInstitucion(institucionSeleccionada);
			Evento evento = eventos.get(eventoSeleccionado);
			Edicion edicion = evento.getEdicion(edicionSeleccionada);
			TipoRegistro tiporeg = edicion.getTipoRegistro(tipoRegistroSeleccionado);
			Patrocinio patrocinio = new Patrocinio(codigoPatrocinio, FechaAlta, aporteEconomico, nivelPatrocinio, cantidadGratuitos, institucion, tiporeg);
			edicion.agregarPatrocinio(patrocinio);
		} catch (TipoRegistroNoExiste tr) {
			throw tr;
		}
	}
	public void altaRegistro(Asistente asistente, TipoRegistro tipoRegistro, Patrocinio patrocinio, Date fecha, float costo) {
		Registro registro = new Registro(fecha, costo, asistente, patrocinio);
		tipoRegistro.addRegistro(registro);
		asistente.addRegistro(registro);
	}
	public boolean existePatrocinio(String institucion, String edicion) {
			for (Evento evento : eventos.values()) {
				Edicion edi = evento.getEdiciones().get(edicion);
				if (edi != null) {
					for (Patrocinio p : edi.getPatrocinios().values()) {
						if (p.getInstitucion().getNombre().equalsIgnoreCase(institucion)) {
							return true;
						}
					}
				}
			}
			return false;
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
	
	public Set<String> listarRegistrosAEdicion(String nombreEvento, String nombreEdicion) {
    	Evento evento = eventos.get(nombreEvento);
    	Edicion edicion = evento.getEdicion(nombreEdicion);
    	
    	// Tipos Registro
    	Map<String, TipoRegistro> trs = edicion.getTiposRegistro();
    	Set<String> ctoreg = new HashSet<>();
    	for (TipoRegistro tr: trs.values()) {
    		List<Registro> lreg = tr.getRegistros();
    		for (Registro r: lreg) {
    			String rNombre = tr.getNombre() 
    					+ "-" + r.getAsistente().getNombre();
    			ctoreg.add(rNombre);
    		}
    	}
    	
    	return ctoreg;
    }
	public Set<String> listarPatrociniosDeEdicion(String nombreEvento, String nombreEdicion){
		Evento evento = eventos.get(nombreEvento);
    	Edicion edicion = evento.getEdicion(nombreEdicion);
    	return edicion.getPatrocinios().keySet();
	}
	public Set<String> getNombresEdicionesIngresadas(String nombreEvento) {
		EEstadoEdicion estado=EEstadoEdicion.valueOf("Ingresada");
		Set<String> edicionesIngresadas = new HashSet<>();
		for (Edicion e:eventos.get(nombreEvento).getEdiciones().values()) {
			if (e.getEstado()==estado) {
			 edicionesIngresadas.add(e.getNombre());
				
			}
		}
		return edicionesIngresadas;
	}
	public void setEstadoEdicion(String eventoSeleccionado, String edicionSeleccionada, boolean bool) {
		EEstadoEdicion estado;
		String mensaje;

	    if (bool) {
	        estado = EEstadoEdicion.Aceptada;
	        mensaje = "La edición \"" + edicionSeleccionada + "\" del evento \"" + eventoSeleccionado + "\" fue ACEPTADA.";
	    } else {
	        estado = EEstadoEdicion.Rechazada;
	        mensaje = "La edición \"" + edicionSeleccionada + "\" del evento \"" + eventoSeleccionado + "\" fue RECHAZADA.";
	    }

	    eventos.get(eventoSeleccionado).getEdicion(edicionSeleccionada).setEstado(estado);

	    // Mostrar cartel siempre
	    JOptionPane.showMessageDialog(null, mensaje, "Estado de Edición", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public List<String> filtrarEdicionesPorOrganizadorYEstado(String organizadorNickname,
            java.util.Set<EEstadoEdicion> estados) {
List<String> out = new ArrayList<>();
if (organizadorNickname == null || estados == null || estados.isEmpty()) return out;

for (Evento ev : eventos.values()) {
for (Edicion ed : ev.getEdiciones().values()) {
// Ajustá el getter según tu modelo (ej.: getOrganizadorNickname() o getOrganizador().getNickname())
String orgDeLaEdicion = ed.getOrganizadorNickname();
if (organizadorNickname.equals(orgDeLaEdicion) && estados.contains(ed.getEstado())) {
out.add(ed.getNombre()); 
// Si querés "Evento - Edición": out.add(ev.getNombre() + " - " + ed.getNombre());
// Si preferís un DTO: out.add(new DTEventoEdicion(ev.getNombre(), ed.getNombre()));
}
}
}
return out;
}
	
	
	public Set<DTDetalleRegistro> listarDTRegistrosAEdicion(String nombreEvento, String nombreEdicion) {
		Evento evento = eventos.get(nombreEvento);
    	Edicion edicion = evento.getEdicion(nombreEdicion);
    	
    	// Tipos Registro
    	Map<String, TipoRegistro> setTiposRegistro = edicion.getTiposRegistro();
    	Set<DTDetalleRegistro> setRegistros = new HashSet<>();
    	for (TipoRegistro tipoRegistro: setTiposRegistro.values()) {
    		List<Registro> listaRegistros = tipoRegistro.getRegistros();
    		for (Registro registro: listaRegistros) {
    			setRegistros.add(registro.mostrarDetalle(nombreEdicion, tipoRegistro.getNombre()));
    		}
    	}
    	return setRegistros;
	}
}

