package excepciones;

public class FormatoContrasenaInvalido extends Exception {
	private static final long serialVersionUID = 1L;

	public FormatoContrasenaInvalido(String mensaje) {
		super(mensaje);
	}
}

