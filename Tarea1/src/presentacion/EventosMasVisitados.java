package presentacion;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;

import datatypes.DTEventoVisitas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import logica.IControladorEventos;
import logica.Fabrica;

@SuppressWarnings("serial")
public class EventosMasVisitados extends JInternalFrame {

	private final IControladorEventos ctrl;  // se resuelve adentro
    private final DefaultTableModel model;

    public EventosMasVisitados(JFrame owner, JDesktopPane parent) {
        super("Eventos más visitados", true, true, true, true);

        // Opción A: con fábrica (la más común en TPROG)
        this.ctrl = Fabrica.getInstancia().getControladorEvento();

        // Opción B (si NO tenés fábrica): 
        // this.ctrl = ControladorEventos.getInstance();  // o new ControladorEventos();

        setSize(560, 340);
        setLayout(new BorderLayout(8, 8));

        JLabel titulo = new JLabel("Eventos más visitados (Top 5)");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        add(titulo, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"#", "Nombre Evento", "Visitas"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int c) {
                return (c == 0 || c == 2) ? Integer.class : String.class;
            }
        };
        JTable tabla = new JTable(model);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btn = new JButton("Actualizar");
        btn.addActionListener(e -> cargarDatos());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(btn);
        add(south, BorderLayout.SOUTH);

        cargarDatos();
        if (parent != null) {
            Dimension pd = parent.getSize();
            setLocation((pd.width - getWidth())/2, (pd.height - getHeight())/2);
        }
    }

    private void cargarDatos() {
        model.setRowCount(0);
        List<DTEventoVisitas> top = ctrl.obtenerEventosMasVisitados(5);
        int i = 1;
        for (DTEventoVisitas d : top) {
            model.addRow(new Object[]{i++, d.getNombre(), d.getVisitas()});
        }
    }
}