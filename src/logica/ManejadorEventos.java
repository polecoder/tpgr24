package logica;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

public class ManejadorEventos {
	private 
	static ManejadorEventos instancia;
	Map<String,Evento> eventos;
	ManejadorEventos(){
		 this.eventos = new HashMap<>();
		 //TEST
		 Evento evento = new Evento("Ev1", "", "", LocalDate.now());
		 Edicion edicion = new Edicion("Op1", "", LocalDate.now(), LocalDate.now(), "", "");
		 evento.agregarEdicion(edicion);
		 this.eventos.put("Ev1", evento);
		 
		 Evento evento2 = new Evento("Ev2", "", "", LocalDate.now());
		 Edicion edicion2 = new Edicion("Op2", "", LocalDate.now(), LocalDate.now(), "", "");
		 evento2.agregarEdicion(edicion2);
		 Edicion edicion3 = new Edicion("Op3", "", LocalDate.now(), LocalDate.now(), "", "");
		 evento2.agregarEdicion(edicion3);
		 this.eventos.put("Ev2", evento2);
		 //TEST
	};
	public
	static ManejadorEventos getInstancia() {
		if (instancia==null) {
			instancia= new ManejadorEventos();
		}
		return instancia;
	};
	
	Evento getEventoEdicion(String nombreEdicion) {
		//TODO
		return null;
	};
	
	boolean contieneEdicion(String NombreEdicion) {
		//TODO
		return true;
	};
	
	Set<DTEvento> listarEventos(){
		//TODO
		return null;
	};
	
	Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion){
		//TODO
		return null;
	};
	
	boolean Validar(String nicknameAsistente, String nombreEdicion, String nombreTipoRegistro){
		//TODO
		return true;
	};

	void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro, String nombreEdicion) {
		//TODO
	};
		
	Registro getRegistro(String nombreEdicion,String nickAsistente){
		//TODO
		return null;
	};
	
	Edicion getEdicion(String nombreEdicion){
		return null;
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
}
