package logica;

public class Fabrica {
    private static Fabrica instancia;

    private Fabrica() {}

    public static Fabrica getInstancia() {
        if (instancia == null) instancia = new Fabrica();
        return instancia;
    }

    public IControladorUsuarios getControladorUsuarios() {
        return ControladorUsuarios.getInstance();
    }

    public ControladorEventos getControladorEvento() {
        return ControladorEventos.getInstancia();
    }

    public IControladorInstitucion getControladorInstitucion() {
        return ControladorInstitucion.getInstance();
    }
}