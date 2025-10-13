package excepciones;

public class AporteInsuficiente extends Exception {
	private static final long serialVersionUID = 1L;

	public AporteInsuficiente(String mensaje) {
		super(mensaje);
	}
}

