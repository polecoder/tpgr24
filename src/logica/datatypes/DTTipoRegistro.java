package logica.datatypes;

public record DTTipoRegistro (
    String nombre, 
    String descripcion, 
    float costo, 
    int cuposMax, 
    int cuposDisponibles
    ) {
}
