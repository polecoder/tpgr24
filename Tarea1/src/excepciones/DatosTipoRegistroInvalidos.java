package excepciones;

public class DatosTipoRegistroInvalidos extends Exception {
	private static final long serialVersionUID = 1L;

	public DatosTipoRegistroInvalidos(String mensaje) {
		super(mensaje);
	}
}
