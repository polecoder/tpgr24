package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import logica.Fabrica;
import logica.IControladorEventos;

public class AceptarRechazarEdicion extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private Fabrica fabrica = Fabrica.getInstancia();
    private IControladorEventos iContEvento = fabrica.getControladorEvento();

    public AceptarRechazarEdicion() {
        super("Autorizar Ediciones", true, true, true, true);
        setSize(400, 200);
        setLocation(50, 50);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{50, 100, 100, 50};
        gridBagLayout.rowHeights = new int[]{30, 30, 30, 30};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0}; // col 1 se expande
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0};
        getContentPane().setLayout(gridBagLayout);

        // ---------- EVENTO ----------
        JLabel lblEvento = new JLabel("Evento:");
        GridBagConstraints gbc_lblEvento = new GridBagConstraints();
        gbc_lblEvento.gridx = 1;
        gbc_lblEvento.gridy = 0;
        gbc_lblEvento.insets = new Insets(5, 5, 5, 5);
        gbc_lblEvento.anchor = GridBagConstraints.WEST;
        getContentPane().add(lblEvento, gbc_lblEvento);

        JComboBox<String> comboEvento = new JComboBox<>();
        GridBagConstraints gbc_comboEvento = new GridBagConstraints();
        gbc_comboEvento.gridx = 2;
        gbc_comboEvento.gridy = 0;
        gbc_comboEvento.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboEvento.weightx = 1.0;
        gbc_comboEvento.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(comboEvento, gbc_comboEvento);

        comboEvento.addItem("--Seleccionar un Evento--");

        Set<String> eventosDisponibles = iContEvento.nombresEventos();
        if (eventosDisponibles != null) {
            for (String evento : eventosDisponibles) {
                comboEvento.addItem(evento);
            }
        }

        // ---------- EDICIÓN ----------
        JLabel lblEdicion = new JLabel("Edición:");
        GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
        gbc_lblEdicion.gridx = 1;
        gbc_lblEdicion.gridy = 1;
        gbc_lblEdicion.insets = new Insets(5, 5, 5, 5);
        gbc_lblEdicion.anchor = GridBagConstraints.WEST;
        getContentPane().add(lblEdicion, gbc_lblEdicion);

        JComboBox<String> comboEdicion = new JComboBox<>();
        GridBagConstraints gbc_comboEdicion = new GridBagConstraints();
        gbc_comboEdicion.gridx = 2;
        gbc_comboEdicion.gridy = 1;
        gbc_comboEdicion.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboEdicion.weightx = 1.0;
        gbc_comboEdicion.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(comboEdicion, gbc_comboEdicion);
        comboEdicion.addItem("--Seleccionar una Edición--");
        
        JRadioButton Button_Aceptar = new JRadioButton("Aceptar");
        GridBagConstraints gbc_Button_Organizador = new GridBagConstraints();
        gbc_Button_Organizador.gridx = 1;
        gbc_Button_Organizador.gridy = 2;
        gbc_Button_Organizador.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(Button_Aceptar, gbc_Button_Organizador);

        JRadioButton Button_Rechazar = new JRadioButton("Rechazar");
        GridBagConstraints gbc_Button_Asistente = new GridBagConstraints();
        gbc_Button_Asistente.gridx = 2;
        gbc_Button_Asistente.gridy = 2;
        gbc_Button_Asistente.insets = new Insets(5, 5, 5, 5);
        getContentPane().add(Button_Rechazar, gbc_Button_Asistente);
        
        javax.swing.ButtonGroup grupo = new javax.swing.ButtonGroup();
        grupo.add(Button_Aceptar);
        grupo.add(Button_Rechazar);
        
        Button_Aceptar.setVisible(false);
        Button_Rechazar.setVisible(false);

        // ---------- LISTENER DE EVENTOS ----------
        comboEvento.addActionListener(e -> {
            comboEdicion.removeAllItems();
            Button_Aceptar.setVisible(false);
            Button_Rechazar.setVisible(false);
            
            String eventoSeleccionado = (String) comboEvento.getSelectedItem();
            if (eventoSeleccionado == null || eventoSeleccionado.isEmpty() 
                    || eventoSeleccionado.equals("--Seleccionar un Evento--")) {
                return;
            }

            comboEdicion.addItem("--Seleccionar una Edición--");
            Set<String> edicionesDisponibles = iContEvento.nombresEdicionesIngresadas(eventoSeleccionado);
            if (edicionesDisponibles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay ediciones por evaluar", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (String edicion : edicionesDisponibles) {
                comboEdicion.addItem(edicion);
            }
            
        
        });
        
        comboEdicion.addActionListener(e -> {
        	Button_Aceptar.setVisible(true);
            Button_Rechazar.setVisible(true);
            
            
        });
        
        // ---------- RADIO BUTTONS ----------
        

        // ---------- BOTONES ----------
        JButton Button_Aceptar2 = new JButton("Aceptar");
        GridBagConstraints gbc_Button_Aceptar2 = new GridBagConstraints();
        gbc_Button_Aceptar2.gridx = 1;
        gbc_Button_Aceptar2.gridy = 3;
        gbc_Button_Aceptar2.insets = new Insets(10, 5, 10, 5);
        getContentPane().add(Button_Aceptar2, gbc_Button_Aceptar2);
        
        Button_Aceptar2.addActionListener(e -> {
            String eventoSeleccionado = (String) comboEvento.getSelectedItem();
            String edicionSeleccionada = (String) comboEdicion.getSelectedItem();

            if (eventoSeleccionado == null || eventoSeleccionado.equals("--Seleccionar un Evento--")) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un evento.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (edicionSeleccionada == null || edicionSeleccionada.equals("--Seleccionar una Edición--")) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una edición.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!Button_Aceptar.isSelected() && !Button_Rechazar.isSelected()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar Aceptar o Rechazar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Button_Aceptar.isSelected()) {
            	iContEvento.setEstadoEdicion(eventoSeleccionado, edicionSeleccionada, true);
            }else {
            	iContEvento.setEstadoEdicion(eventoSeleccionado, edicionSeleccionada, false);
            }

            // 5. Limpiar campos
            comboEvento.setSelectedIndex(0); // vuelve al primer ítem
            comboEdicion.removeAllItems();
            comboEdicion.addItem("--Seleccionar una Edición--");
            Button_Aceptar.setSelected(false);
            Button_Rechazar.setSelected(false);
            Button_Aceptar.setVisible(false);
            Button_Rechazar.setVisible(false);
        });

        JButton Button_Cancelar = new JButton("Cancelar");
        GridBagConstraints gbc_Button_Cancelar = new GridBagConstraints();
        gbc_Button_Cancelar.gridx = 2;
        gbc_Button_Cancelar.gridy = 3;
        gbc_Button_Cancelar.insets = new Insets(10, 5, 10, 5);
        getContentPane().add(Button_Cancelar, gbc_Button_Cancelar);
        
        Button_Cancelar.addActionListener(e -> {
		    AceptarRechazarEdicion.this.dispose();
		});
    }
}
