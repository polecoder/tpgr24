package logica;

//import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

public class ManejadorEventos {
	private 
	static ManejadorEventos instancia;
	Map<String,Evento> eventos;
	ManejadorEventos(){
		 this.eventos = new HashMap<>(); 
		 
		/*// crear edición
		 Edicion ed = new Edicion("ConfTec 2025", "", LocalDate.now(), LocalDate.now(), "", "");
		 eventos.put("ConfTec", new Evento("ConfTec", "...", "...", LocalDate.now()));
		 eventos.get("ConfTec").getediciones().put(ed.getNombre(), ed);

		 // crear tipos (SIN pasar ed)
		 TipoRegistro trGen = new TipoRegistro("General", "Acceso general", 1000f, 500, 100);
		 TipoRegistro trVip  = new TipoRegistro("VIP",     "Acceso VIP",     2500f, 100,  20);

		 // asociarlos a la edición (la edición sí contiene a los tipos)
		 ed.getTiposRegistro().put(trGen.getNombre(), trGen);
		 ed.getTiposRegistro().put(trVip.getNombre(),  trVip);*/
		 
	};
	public static ManejadorEventos getInstancia() {
		if (instancia==null) {
			instancia= new ManejadorEventos();
		}
		return instancia;
	};
	
	Evento getEventoEdicion(String nombreEdicion) {
		return eventos.get(nombreEdicion);
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
		//TODO
		return null;
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
