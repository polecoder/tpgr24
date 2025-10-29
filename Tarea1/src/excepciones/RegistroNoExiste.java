package excepciones;

public class RegistroNoExiste extends Exception {
	private static final long serialVersionUID = 1L;
	
	public RegistroNoExiste(String mensaje){ 
		super(mensaje); 
	}

}
