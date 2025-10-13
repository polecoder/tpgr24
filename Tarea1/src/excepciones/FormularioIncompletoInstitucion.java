package excepciones;

public class FormularioIncompletoInstitucion extends Exception {
	private static final long serialVersionUID = 1L;

	public FormularioIncompletoInstitucion(String mensaje) {
		super(mensaje);
	}
}

