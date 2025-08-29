package logica;

import java.util.Collections;
import java.util.Set;
import java.util.HashMap;
import java.util.Locale;
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
    
    private final Map<String, Categoria> categorias = new HashMap<>();


    private ControladorEventos() { }

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
    
    public boolean altaCategoria(String nombre) {
        if (nombre == null) return false;
        String key = nombre.trim();            // saca espacios extremos
        if (key.isEmpty()) return false;       
        if (categorias.containsKey(key)) return false; // si ya existe la categoria
        categorias.put(key, new Categoria(key));
        return true;                           
    }

    public boolean existeCategoria(String nombre) {
        if (nombre == null) return false;
        String key = nombre.trim();
        if (key.isEmpty()) return false;
        return categorias.containsKey(key);
    }
}
