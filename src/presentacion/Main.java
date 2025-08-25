package presentacion;

import java.awt.EventQueue;


public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                framePrincipal frame = new framePrincipal();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}