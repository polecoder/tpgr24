package excepciones;

public class FormularioIncompletoAsistente extends Exception {
	private static final long serialVersionUID = 1L;

	public FormularioIncompletoAsistente(String mensaje) {
		super(mensaje);
	}
}

