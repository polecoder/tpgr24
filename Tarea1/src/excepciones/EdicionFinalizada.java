package excepciones;

public class EdicionFinalizada extends Exception {
	private static final long serialVersionUID = 1L;

	public EdicionFinalizada(String mensaje) {
		super(mensaje);
	}
}

