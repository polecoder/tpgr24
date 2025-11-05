package datatypes;

public class DTEventoVisitas {
	
	private final String nombre;
    private final int visitas;

    public DTEventoVisitas(String nombre, int visitas) {
        this.nombre = nombre;
        this.visitas = visitas;
    }
    public String getNombre() { return nombre; }
    public int getVisitas() { return visitas; }

}
