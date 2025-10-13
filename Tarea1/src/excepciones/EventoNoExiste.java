package excepciones;

public class EventoNoExiste extends Exception {
	private static final long serialVersionUID = 1L;

	public EventoNoExiste(String mensaje) {
		super(mensaje);
	}
}
