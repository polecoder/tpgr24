package datatypes;

public record DTRegistro(
    String nombreEdicion,
    String nombreTipo
) {
	public String getNombreEdicion() {
		return nombreEdicion;
	}
	
	public String getNombreTipo() {
		return nombreTipo;
	}
}
