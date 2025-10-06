package excepciones;

public class NombreTipoRegistroRepetido extends Exception {
	private static final long serialVersionUID = 1L;

	public NombreTipoRegistroRepetido(String mensaje) {
		super(mensaje);
	}
}
