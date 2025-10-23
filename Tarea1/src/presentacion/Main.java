package presentacion;

import java.awt.EventQueue;
import servidor.Publicador;


public class Main {
    public static void main(String[] args) {
    		Publicador publicador = new Publicador();
    		publicador.publicar();
        EventQueue.invokeLater(() -> {
        	FramePrincipal frame = new FramePrincipal();
            frame.setVisible(true);
        });
    }
}