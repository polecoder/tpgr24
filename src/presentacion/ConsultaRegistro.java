package presentacion;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import logica.IControladorUsuarios;
import logica.ControladorUsuarios;
//import logica.datatypes.DTAsistente;
import logica.datatypes.DTDetalleRegistro;
import logica.datatypes.DTRegistro;

public class ConsultaRegistro extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    //UI
    private JComboBox<String> comboUsuarios;
    private JComboBox<String> comboRegistros;
    private JButton btnDetalle;
    
    private boolean cargando = false;

    //Logica
    private final IControladorUsuarios cu = ControladorUsuarios.getInstance();
    private final DateTimeFormatter F = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // ventana: titulo, cerrar, minimizar
    public ConsultaRegistro() {
        setResizable(true);
        setIconifiable(true);
        setClosable(true);
        setTitle("Consulta de Registro");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(350, 250);
        setMinimumSize(new Dimension(350, 250));

        GridBagLayout g = new GridBagLayout();
        g.columnWidths  = new int[]{0, 0};
        g.rowHeights    = new int[]{0, 0, 0};
        g.columnWeights = new double[]{1.0, 1.0};
        g.rowWeights    = new double[]{0.0, 0.0, 0.0};
        getContentPane().setLayout(g);

        // Fila 0: Usuario
        JLabel lblUsuario = new JLabel("Usuario");
        GridBagConstraints gbcUlbl = new GridBagConstraints();
        gbcUlbl.insets = new Insets(0,0,5,5);
        gbcUlbl.anchor = GridBagConstraints.EAST;
        gbcUlbl.gridx = 0; gbcUlbl.gridy = 0;
        getContentPane().add(lblUsuario, gbcUlbl);

        comboUsuarios = new JComboBox<>(); 
        GridBagConstraints gbcU = new GridBagConstraints();
        gbcU.insets = new Insets(0,0,5,0);
        gbcU.fill = GridBagConstraints.HORIZONTAL;
        gbcU.weightx = 1.0;
        gbcU.gridx = 1; gbcU.gridy = 0;
        getContentPane().add(comboUsuarios, gbcU);

        // Fila 1: Registros
        JLabel lblReg = new JLabel("Registros");
        GridBagConstraints gbcRlbl = new GridBagConstraints();
        gbcRlbl.insets = new Insets(0,0,5,5);
        gbcRlbl.anchor = GridBagConstraints.EAST;
        gbcRlbl.gridx = 0; gbcRlbl.gridy = 1;
        getContentPane().add(lblReg, gbcRlbl);

        comboRegistros = new JComboBox<>(); 
        GridBagConstraints gbcR = new GridBagConstraints();
        gbcR.insets = new Insets(0,0,5,0);
        gbcR.fill = GridBagConstraints.HORIZONTAL;
        gbcR.weightx = 1.0;
        gbcR.gridx = 1; gbcR.gridy = 1;
        getContentPane().add(comboRegistros, gbcR);

        // Fila 2: Panel + botón
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        GridBagConstraints gbcP = new GridBagConstraints();
        gbcP.gridx = 0; gbcP.gridy = 2; gbcP.gridwidth = 2;
        gbcP.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(panel, gbcP);

        btnDetalle = new JButton("Ver Detalle");   //creo el boton
        btnDetalle.setEnabled(false);              
        panel.add(btnDetalle);

        // Datos + eventos
        cargando=true;
        cargarUsuarios();
        wireEvents();
        if (comboUsuarios.getItemCount() > 0) {
            listarRegistros(); 
        } else {
            comboRegistros.setEnabled(false);
        }
        cargando = false;
    }

    private void wireEvents() {
        comboUsuarios.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            	if(cargando) return;
                listarRegistros();
            }
        });
        btnDetalle.addActionListener(e -> verDetalle());
    }

    	//limpia el combobox usuarios ny lo llena con asistentes
    private void cargarUsuarios() {
        comboUsuarios.removeAllItems();
        for (logica.datatypes.DTUsuario u : cu.listarUsuarios()) {
            comboUsuarios.addItem(u.nickname());    
        }
        if (comboUsuarios.getItemCount() > 0) comboUsuarios.setSelectedIndex(0);
    }

    private void listarRegistros() {
    	comboRegistros.removeAllItems();
        String nick = (String) comboUsuarios.getSelectedItem();
        if (nick == null || nick.isBlank()) {
        	btnDetalle.setEnabled(false);
        	return;
        }

        List<DTRegistro> regs = cu.listarRegistrosUsuario(nick);

        if (regs == null || regs.isEmpty()) {
            comboRegistros.setEnabled(false);
            if (!cargando) { // para q no muestre el cartel al principo con el usuario predeterminado
                JOptionPane.showMessageDialog(this, "Este usuario no tiene registros.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            return;
        }
       
        
        for(DTRegistro r : regs) {
        	comboRegistros.addItem(r.nombreEdicion() + " , " + r.nombreTipo());
        }
        comboRegistros.setEnabled(true);
        comboRegistros.setSelectedIndex(0);
        btnDetalle.setEnabled(true);
        
    }

    	// al apretar el boton para ver el detalle del registro
    private void verDetalle() {
    	if(comboUsuarios.getSelectedIndex()==-1) {
    		JOptionPane.showMessageDialog(null, "No hay usuarios seleccionado");
    		return;
    	}
    	String nick = (String) comboUsuarios.getSelectedItem();
    	int idRegistro = comboRegistros.getSelectedIndex(); 
    	if (nick==null|| idRegistro<0) {
    		JOptionPane.showMessageDialog(this, "selecciona un registro", "Atencion" , JOptionPane.WARNING_MESSAGE);
    		return;
    	}
 
        
        DTDetalleRegistro d = cu.obtenerDetalleRegistro(nick, idRegistro);
        if (d == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el detalle.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String msg = """
                Edición: %s
                Usuario: %s
                Tipo: %s
                Fecha: %s
                Costo: %s
                """.formatted(
                d.getNombreEdicion(),
                d.getNicknameAsistente(),
                d.getNombreTipo(),
                d.getFecha() != null ? d.getFecha().format(F) : "",
                d.getCosto()
        );
        JOptionPane.showMessageDialog(this, msg, "Detalle del registro", JOptionPane.INFORMATION_MESSAGE);
    }
}