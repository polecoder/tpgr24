package excepciones;

public class EdicionYaArchivada extends Exception {
	private static final long serialVersionUID = 1L;

	public EdicionYaArchivada(String mensaje) {
		super(mensaje);
	}
}

