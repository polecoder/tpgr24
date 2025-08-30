package logica;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashMap;
//import java.util.Locale;
import java.util.Map;

import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTPatrocinio;
import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTDatosPatrocinio;
import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;

import java.time.LocalDate;

public class ControladorEventos {
    private static ControladorEventos instancia;
    private final ManejadorEventos me;
    
    private final Map<String, Categoria> categorias = new HashMap<>();


    private ControladorEventos() {
    	this.me = ManejadorEventos.getInstancia();
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
    
    public void altaCategoria(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede ser vacío o nulo.");
        }

        String nombreNormalizado = nombre.trim().toLowerCase();

        if (categorias.containsKey(nombreNormalizado)) {
            throw new IllegalArgumentException("La categoría '" + nombre + "' ya existe.");
        }

        categorias.put(nombreNormalizado, new Categoria(nombre));
    }
    
    public Set<String> listarCategorias() {
        return categorias.values().stream()
                         .map(Categoria::getNombre)
                         .collect(Collectors.toSet());
    }

    public boolean existeCategoria(String nombre) {
        if (nombre == null) return false;
        String key = nombre.trim();
        if (key.isEmpty()) return false;
        return categorias.containsKey(key);
    }

	public DTEvento getDTEvento(String nombreEvento) {
		Evento ev = me.getEventoEdicion(nombreEvento);
		DTEvento dte = new DTEvento (ev.getNombre(),ev.getDescripcion(), ev.getSigla());
		return dte;
	}

	public Set<String> nombresEdicionesEvento(String nombreEvento) {
	    Evento ev = me.getEventoEdicion(nombreEvento);
	    if (ev == null) {
	        return Set.of(); // evento no existe → conjunto vacío
	    }

	    // devolvemos el conjunto de nombres de ediciones
	    return ev.getediciones().keySet();
	}
}
