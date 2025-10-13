package excepciones;

public class FormularioIncompletoEdicion extends Exception {
	private static final long serialVersionUID = 1L;

	public FormularioIncompletoEdicion(String mensaje) {
		super(mensaje);
	}
}

