package logica;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import logica.datatypes.DTDatosPatrocinio;
import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;
import logica.datatypes.DTPatrocinio;
import logica.datatypes.DTTipoRegistro;

public class ControladorEventos implements IControladorEventos {
    private static ControladorEventos instancia;
    private ManejadorEventos manejadorEventos;

    private ControladorEventos() {
    	this.manejadorEventos =ManejadorEventos.getInstancia();
    }

    public static ControladorEventos getInstance() {
        if (instancia == null) {
        	instancia = new ControladorEventos();
        };
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

	@Override
	public List<DTTipoRegistro> listarTipoRegistro() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public DTEvento getDTEvento(String nombre) {
		return manejadorEventos.getDTEvento(nombre);
	}

	@Override
	public DTEdicionEvento getDTEdicion(String eventoSeleccionado, String edicionSeleccionada) {
		return manejadorEventos.getDTEdicion(eventoSeleccionado,edicionSeleccionada);
	}

	public Set<String> nombresEventos(){
		  return  manejadorEventos.getNombresEventos(); 
		}
	
	public Set<String> nombresCategorias(String nombre){
		 return manejadorEventos.getNombresCategorias(nombre);
		}
	
	public Set<String> nombresEdicionesEvento(String nombre) {
		return manejadorEventos.getNombresEdiciones(nombre);
	}

	
	
	}
