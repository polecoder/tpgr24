package excepciones;

public class NoHayCuposDisponibles extends Exception {
	private static final long serialVersionUID = 1L;

	public NoHayCuposDisponibles(String mensaje) {
		super(mensaje);
	}
}

