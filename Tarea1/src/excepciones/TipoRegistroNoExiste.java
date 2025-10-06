package excepciones;

public class TipoRegistroNoExiste extends Exception {
	private static final long serialVersionUID = 1L;

	public TipoRegistroNoExiste(String mensaje) {
		super(mensaje);
	}
}
