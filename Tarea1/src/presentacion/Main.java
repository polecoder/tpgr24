package presentacion;

import java.awt.EventQueue;


public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
        	FramePrincipal frame = new FramePrincipal();
            frame.setVisible(true);
        });
    }
}