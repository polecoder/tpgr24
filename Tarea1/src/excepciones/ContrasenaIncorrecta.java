package excepciones;

public class ContrasenaIncorrecta extends Exception {
	private static final long serialVersionUID = 1L;

	public ContrasenaIncorrecta(String mensaje) {
		super(mensaje);
	}
}

