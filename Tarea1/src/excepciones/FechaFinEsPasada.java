package excepciones;

public class FechaFinEsPasada extends Exception {
	private static final long serialVersionUID = 1L;

	public FechaFinEsPasada(String mensaje) {
		super(mensaje);
	}
}

