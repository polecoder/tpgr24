package tarea1;

import java.awt.EventQueue;
import javax.swing.JFrame;

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