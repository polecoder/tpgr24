package excepciones;

public class UsuarioNoEsOrganizador extends Exception {
	private static final long serialVersionUID = 1L;
	
	public UsuarioNoEsOrganizador(String mensaje) { 
		super(mensaje);}
}
