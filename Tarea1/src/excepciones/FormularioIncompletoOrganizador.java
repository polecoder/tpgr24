package excepciones;

public class FormularioIncompletoOrganizador extends Exception {
	private static final long serialVersionUID = 1L;

	public FormularioIncompletoOrganizador(String mensaje) {
		super(mensaje);
	}
}

