package excepciones;

public class PatrocinioInvalido extends Exception {
	private static final long serialVersionUID = 1L;

	public PatrocinioInvalido(String mensaje) {
		super(mensaje);
	}
}

