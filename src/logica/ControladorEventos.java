package logica;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.List;

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
  
    public List<DTEdicionEvento> listarEdicionesEvento(String nombreEvento) {
        return null;
    }

    public List<DTPatrocinio> listarPatrociniosEdicion(String nombreEdicion, String nombreEvento) {
        return null;
    }

    public DTDetallePatrocinio mostrarInfoPatrocinio(String codigoPatrocinio) {
        return null;
    }

    public void altaPatrocinio(DTDatosPatrocinio datosPatrocinio) {
        // HACER
    }

    public List<DTEvento> listarEventos() {
        return null;
    }

    public Set<DTTipoRegistro> listarTipoRegistro(String nombreEvento, String nombreEdicion) {
        return null;
    }

    public boolean validar(String nicknameAsistente, String nombreEdicion,
                           String nombreTipoRegistro, String nombreEvento) {
        return false;
    }

    public void confirmarRegistro(String nicknameAsistente, String nombreTipoRegistro,
                                  String nombreEdicion, String codigoPatrocinio, LocalDate fecha) {
        // HACER
    }
    
    public List<DTTipoRegistro> listarTipoRegistro() {
		return null;
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
		this.manejadorEventos.altaEvento(nombre, descripcion, fechaAlta, sigla, categorias);
    }
    
    /**
	 * Retorna true en caso de que exista un evento e en el sistema tal que e.nombre == nombreEvento
	 * Retorna false en cualquier otro caso.
	 * 
	 * @param nombreEvento
	 */
	public boolean existeEvento(String nombreEvento) {
		return this.manejadorEventos.existeEvento(nombreEvento);
	}
}
