package excepciones;

public class FormularioIncompletoPatrocinio extends Exception {
	private static final long serialVersionUID = 1L;

	public FormularioIncompletoPatrocinio(String mensaje) {
		super(mensaje);
	}
}

