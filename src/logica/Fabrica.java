package logica;

public class Fabrica {
    private static Fabrica instancia;

    private Fabrica() {}

    public static Fabrica getInstancia() {
        if (instancia == null) instancia = new Fabrica();
        return instancia;
    }

    public ControladorUsuarios getControladorUsuarios() {
        return ControladorUsuarios.getInstance();
    }

    public ControladorEventos getControladorEvento() {
        return ControladorEventos.getInstancia();
    }

    public ControladorInstitucion getControladorInstitucion() {
        return ControladorInstitucion.getInstancia();
    }
}