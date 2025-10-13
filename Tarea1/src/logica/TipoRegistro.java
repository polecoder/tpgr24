package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import datatypes.DTTipoRegistro;
import excepciones.AsistenteYaRegistrado;

public class TipoRegistro {

    private String nombre;
    private String descripcion;
    private float costo;
    private int cuposMax;
    private int cuposDisponibles;
    private List<Registro> registros;

    public TipoRegistro() { }

    public TipoRegistro(String nombre, String descripcion, float costo, int cuposMax, int cuposDisponibles) {
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setCosto(costo);
        this.setCuposMax(cuposMax);
        this.setCuposDisponibles(cuposDisponibles);
        this.registros = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public float getCosto() { return costo; }
    public void setCosto(float costo) { this.costo = costo; }

    public int getCuposMax() { return cuposMax; }
    public void setCuposMax(int cuposMax) { this.cuposMax = cuposMax; }

    public int getCuposDisponibles() { return cuposDisponibles; }
    public void setCuposDisponibles(int cuposDisponibles) { this.cuposDisponibles = cuposDisponibles; }

    public void addRegistro(Registro r) {
    	Objects.requireNonNull(r);
        if (cuposDisponibles <= 0) throw new IllegalStateException("Sin cupos");
        registros.add(r);
        cuposDisponibles--;
    }
    
    public List<Registro> getRegistros(){ return registros; }
    
    public boolean validar(String nicknameAsistente) throws AsistenteYaRegistrado {
    	boolean v = false;
    	for (Registro r: registros) {
    		v = v || r.validar(nicknameAsistente);
    	}
    	if (v) {
    		throw new AsistenteYaRegistrado("Este asistente ya esta registrado");
    	}
    	return !v; 
    }
    
    public DTTipoRegistro getDTTipoRegistro() {
    	DTTipoRegistro dtTipoRegistro = new DTTipoRegistro(
            this.getNombre(), 
            this.getDescripcion(), 
            this.getCosto(), 
            this.getCuposMax(), 
            this.getCuposDisponibles());
    	return dtTipoRegistro;
    }
}