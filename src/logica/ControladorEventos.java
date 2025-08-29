package logica;

import java.util.Collections;
import java.util.Set;
import excepciones.NombreTipoRegistroRepetido;
import excepciones.TipoRegistroNoExiste;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTPatrocinio;
import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTDatosPatrocinio;
import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

import java.time.LocalDate;

public class ControladorEventos {
    private static ControladorEventos instancia;
    private ManejadorEventos manejadorEventos;

    private ControladorEventos() {
    	manejadorEventos = ManejadorEventos.getInstancia();
    }

    public static ControladorEventos getInstancia() {
        if (instancia == null) instancia = new ControladorEventos();
        return instancia;
    }

  
    public Set<DTEdicionEvento> listarEdicionesEvento(String nombreEvento) {
        return Collections.emptySet();
    }

    public Set<DTPatrocinio> listarPatrociniosEdicion(String nombreEdicion, String nombreEvento) {
        return Collections.emptySet();
    }

    public DTDetallePatrocinio mostrarInfoPatrocinio(String codigoPatrocinio) {
        return null;
    }

    public void altaPatrocinio(DTDatosPatrocinio datosPatrocinio) {
        // HACER
    }

    public Set<DTEvento> listarEventos() {
        return Collections.emptySet();
    }

    public Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion) {
        return Collections.emptySet();
    }

    public boolean validar(String nicknameAsistente, String nombreEdicion,
                           String nombreTipoRegistro, String nombreEvento) {
        return false;
    }

    public void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro,
                                  String nombreEdicion, String codigoPatrocinio, LocalDate fecha) {
        // HACER
    }
    
    public boolean existeTipoRegistro(String nombre, String nombreEvento, String nombreEdicion) {
    	return false;
    }
    
    public String[] getNombreEventos() {
    	Set<DTEvento> listaEventos = listarEventos(); // Obtengo los DTEvento
		String[] nombreEventos = new String[listaEventos.size()]; // Creo lista vacia de nombres
		
		// Por cada evento, agrego su nombre a la lista
		int i = 0;
		for (DTEvento evento : listaEventos) {
			nombreEventos[i] = evento.getNombre();
		}
		return new String[] {"Ev1", "Ev2"};//nombreEventos;
    }
    
    public String[] getNombresEdiciones(String nombreEvento) {
    	/*Set<DTEdicionEvento> listaEdiciones = listarEdicionesEvento(nombreEvento);
    	String[] nombreEdiciones= new String[listaEdiciones.size()]; // Creo lista vacia de nombres
		
		// Por cada evento, agrego su nombre a la lista
		int i = 0;
		for (DTEdicionEvento evento : listaEdiciones) {
			nombreEdiciones[i] = evento.nombre();
		}
		return nombreEdiciones;*/
    	if (nombreEvento == "Ev1") {
    		return new String[] {"Op1"};
    	} else return new String[] {"Op2", "Op3"};
    }
    
    public void altaTipoRegistro(String nombre, String descripcion, float costo, int cuposMax, String nombreEvento, String nombreEdicion) throws NombreTipoRegistroRepetido {
    	try {
    		manejadorEventos.altaTipoRegistro(nombre, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
    	} catch (NombreTipoRegistroRepetido ex) {
    		throw new NombreTipoRegistroRepetido("El nombre del ya está en otro TipoRegistro de esta edicion");
    	}
    }
    
    public String[] getTiposRegistro(String nombreEvento, String nombreEdicion) {
    	return manejadorEventos.getTiposRegistro(nombreEvento,nombreEdicion);
    }
    
    public DTTipoRegistro getDTTipoRegistro(String nombreEvento, String nombreEdicion, String nombreTipoRegistro) throws TipoRegistroNoExiste {
    	try {
    		manejadorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro);
    	} catch (TipoRegistroNoExiste ex) {
    		throw new TipoRegistroNoExiste("El Tipo de Registro no existe");
    	};
    	return manejadorEventos.getDTTipoRegistro(nombreEvento, nombreEdicion, nombreTipoRegistro);
    }
}
