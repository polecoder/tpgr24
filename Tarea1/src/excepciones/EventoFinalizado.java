package excepciones;

public class EventoFinalizado extends Exception {
	private static final long serialVersionUID = 1L;
	
	public EventoFinalizado(String mensaje) { 
		super(mensaje); 
	}

}
