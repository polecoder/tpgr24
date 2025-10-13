package excepciones;

public class EdicionNoExiste extends Exception {
	private static final long serialVersionUID = 1L;

	public EdicionNoExiste(String mensaje) {
		super(mensaje);
	}

}
