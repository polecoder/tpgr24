package presentacion;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logica.IControladorUsuarios;
import logica.ControladorUsuarios;
//import logica.datatypes.DTAsistente;
import datatypes.DTDetalleRegistro;
import datatypes.DTRegistro;

public class ConsultaRegistro extends JInternalFrame {
    private static final long serialVersionUID = 1L;

    //UI
    private JComboBox<String> comboUsuarios;
    private JComboBox<String> comboRegistros;
    private JButton btnDetalle;
    private JTextArea txtDetalle;
    
    private static final String PLACEHOLDER_USUARIO = "-- Seleccionar un Usuario --";
    
    private boolean cargando = false;

    //Logica
    private final IControladorUsuarios controladorusu = ControladorUsuarios.getInstance();
    //private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ConsultaRegistro(String nicknameAsistente, String nombreRegistro) {
        setResizable(true);
        setIconifiable(true);
        setClosable(true);
        setTitle("Consulta de Registro");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(400, 300);
        setLocation(50, 50);
        setMinimumSize(new Dimension(350, 250));

        GridBagLayout gridbagl = new GridBagLayout();
        gridbagl.columnWidths  = new int[]{0, 0};
        gridbagl.rowHeights    = new int[]{0, 0, 0, 0};
        gridbagl.columnWeights = new double[]{0.0, 0.0};
        gridbagl.rowWeights    = new double[]{0.0, 0.0, 0.0, 0.0};
        getContentPane().setLayout(gridbagl);

        // Fila 0: Usuario
        JLabel lblUsuario = new JLabel("Usuario");
        GridBagConstraints gbcUlbl = new GridBagConstraints();
        gbcUlbl.insets = new Insets(5, 5, 5, 5);
        gbcUlbl.anchor = GridBagConstraints.EAST;
        gbcUlbl.gridx = 0; gbcUlbl.gridy = 0;
        getContentPane().add(lblUsuario, gbcUlbl);

        comboUsuarios = new JComboBox<>(); 
        GridBagConstraints gbcU = new GridBagConstraints();
        gbcU.insets = new Insets(5, 0, 5, 0);
        gbcU.fill = GridBagConstraints.HORIZONTAL;
        gbcU.weightx = 1.0;
        gbcU.gridx = 1; gbcU.gridy = 0;
        getContentPane().add(comboUsuarios, gbcU);

        // Fila 1: Registros
        JLabel lblReg = new JLabel("Registros");
        GridBagConstraints gbcRlbl = new GridBagConstraints();
        gbcRlbl.insets = new Insets(0, 5, 5, 5);
        gbcRlbl.anchor = GridBagConstraints.EAST;
        gbcRlbl.gridx = 0; gbcRlbl.gridy = 1;
        getContentPane().add(lblReg, gbcRlbl);

        comboRegistros = new JComboBox<>();
        GridBagConstraints gbcR = new GridBagConstraints();
        gbcR.insets = new Insets(0, 0, 5, 0);
        gbcR.fill = GridBagConstraints.HORIZONTAL;
        gbcR.weightx = 1.0;
        gbcR.gridx = 1; gbcR.gridy = 1;
        getContentPane().add(comboRegistros, gbcR);
        if (nombreRegistro != null) {
        	comboRegistros.setSelectedItem(nombreRegistro);
        }

        // Fila 2: Panel + botón
        JPanel panel = new JPanel();
        GridBagConstraints gbcP = new GridBagConstraints();
        gbcP.gridx = 0; gbcP.gridy = 2; gbcP.gridwidth = 2;
        gbcP.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(panel, gbcP);

        btnDetalle = new JButton("Ver Detalle");   //creo el boton
        btnDetalle.setEnabled(false);              
        panel.add(btnDetalle);
        
        JLabel lblDetalle = new JLabel("Detalle");
        GridBagConstraints gbcDlbl = new GridBagConstraints();
        gbcDlbl.insets = new Insets(0, 0, 5, 5);
        gbcDlbl.anchor = GridBagConstraints.NORTHEAST;
        gbcDlbl.gridx = 0; gbcDlbl.gridy = 3;
        getContentPane().add(lblDetalle, gbcDlbl);
        
        txtDetalle = new JTextArea();
        txtDetalle.setEditable(false);
        txtDetalle.setLineWrap(true);
        txtDetalle.setWrapStyleWord(true);
        txtDetalle.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12)); // opcional para formato tipo "ficha"

        JScrollPane spDetalle = new JScrollPane(txtDetalle,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        GridBagConstraints gbcD = new GridBagConstraints();
        gbcD.insets = new Insets(0, 0, 5, 5);
        gbcD.fill = GridBagConstraints.BOTH;
        gbcD.weightx = 1.0;
        gbcD.weighty = 1.0;         // que crezca en alto
        gbcD.gridx = 1; gbcD.gridy = 3;
        getContentPane().add(spDetalle, gbcD);

        // Datos + eventos
        cargando=true;
        cargarUsuarios(nicknameAsistente);
        wireEvents(nicknameAsistente);
        listarRegistros(nombreRegistro);
        //comboRegistros.setEnabled(false);
        //btnDetalle.setEnabled(false);
        cargando = false;
    }

    private void wireEvents(String nicknameAsistente) {
        comboUsuarios.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            	if (cargando) return;
            	String sel = (String) comboUsuarios.getSelectedItem();
            	if (sel == null || PLACEHOLDER_USUARIO.equals(sel)) {
            		comboRegistros.removeAllItems();
                    comboRegistros.setEnabled(false);
                    btnDetalle.setEnabled(false);
                    txtDetalle.setText("");
            	}else {
            		listarRegistros(null);
            	}
            }
        });
        btnDetalle.addActionListener(e -> verDetalle());
    }

	// limpia el combobox usuarios y lo llena con asistentes
    private void cargarUsuarios(String nicknameAsistente) {
        comboUsuarios.removeAllItems();
        comboUsuarios.addItem(PLACEHOLDER_USUARIO);
        for (datatypes.DTUsuario u : controladorusu.listarUsuarios()) {
            comboUsuarios.addItem(u.nickname());    
        }
        // Seleccionar si se pasa por parámetro
        if (nicknameAsistente != null) {
            comboUsuarios.setSelectedItem(nicknameAsistente);
        }else {
            comboUsuarios.setSelectedItem(PLACEHOLDER_USUARIO);
        }
    }

    private void listarRegistros(String nombreRegistro) {
    	comboRegistros.removeAllItems();
    	txtDetalle.setText("");
    	
        String nick = (String) comboUsuarios.getSelectedItem();
        if (nick == null || nick.isBlank()|| PLACEHOLDER_USUARIO.equals(nick)) {
        	comboRegistros.setEnabled(false);
        	btnDetalle.setEnabled(false);
        	return;
        }
        List<DTRegistro> regs = controladorusu.listarRegistrosUsuario(nick);

        if (regs == null || regs.isEmpty()) {
            comboRegistros.setEnabled(false);
            if (!cargando) { // para q no muestre el cartel al principo con el usuario predeterminado
                JOptionPane.showMessageDialog(this, "Este usuario no tiene registros.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            txtDetalle.setText("");
            return;
        }
        for (DTRegistro r : regs) {
        	comboRegistros.addItem(r.getNombreEdicion());
        }
        comboRegistros.setEnabled(true);
        // Seleccionar si se pasa por parámetro
        if (nombreRegistro != null) {
            comboRegistros.setSelectedItem(nombreRegistro);
            comboRegistros.setEnabled(true);
        }
        btnDetalle.setEnabled(true);
    }

	// al apretar el boton para ver el detalle del registro
    private void verDetalle() {
    	if (comboUsuarios.getSelectedIndex()==-1|| PLACEHOLDER_USUARIO.equals(comboUsuarios.getSelectedItem())) {
    		JOptionPane.showMessageDialog(null, "No hay usuarios seleccionado");
    		return;
    	}
    	String nick = (String) comboUsuarios.getSelectedItem();
    	int idRegistro = comboRegistros.getSelectedIndex(); 
    	if (idRegistro<0) {
    		JOptionPane.showMessageDialog(this, "Selecciona un registro", "Atencion" , JOptionPane.WARNING_MESSAGE);
    		return;
    	}
        
        DTDetalleRegistro detallereg = controladorusu.obtenerDetalleRegistro(nick, idRegistro);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if (detallereg == null) {
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
                detallereg.getNombreEdicion(),
                detallereg.getNicknameAsistente(),
                detallereg.getNombreTipo(),
                formatter.format(detallereg.getFecha()),
                detallereg.getCosto()
        );
        txtDetalle.setText(msg);
        txtDetalle.setCaretPosition(0);
    }
}