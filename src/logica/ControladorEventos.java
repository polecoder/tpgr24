package logica;

import java.util.Collections;
import java.util.Set;

import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTPatrocinio;
import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTDatosPatrocinio;
import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

import java.time.LocalDate;

public class ControladorEventos implements IControladorEventos {
    private static ControladorEventos instancia;
	private ManejadorEventos manejadorEventos;
    
    private ControladorEventos() {
    	this.manejadorEventos = ManejadorEventos.getInstancia();
    }

    public static ControladorEventos getInstancia() {
        if (instancia == null) instancia = new ControladorEventos();
        return instancia;
    }
  
    public Set<DTEdicionEvento> listarEdicionesEvento(String nombreEvento) {
        return null;
    }

    public Set<DTPatrocinio> listarPatrociniosEdicion(String nombreEdicion, String nombreEvento) {
        return null;
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
    
    public Set<DTTipoRegistro> listarTipoRegistro() {
		return null;
	}
    
    /**
     * Devuelve true si existe un evento e en el sistema tal que e.nombre = nombreEvento.
     * En caso contrario devuelve false
     * 
     * @param nombreEvento
     * @return
     */
    public boolean existeEvento(String nombreEvento) {
    	Evento evento = this.manejadorEventos.getEvento(nombreEvento);
    	if (evento != null) {
    		return true;
    	}
    	return false;
    }
}
