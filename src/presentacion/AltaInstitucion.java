package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import logica.Fabrica;
import logica.IControladorInstitucion;

public class AltaInstitucion extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private Fabrica fabrica= Fabrica.getInstancia();
    private IControladorInstitucion IContInstitucion = fabrica.getControladorInstitucion();
   
    public AltaInstitucion() {
        super("Alta Institución", true, true, true, true);
        setSize(300, 250);
        setLocation(50, 50);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        getContentPane().setLayout(gridBagLayout);

        JLabel lbl_nombre = new JLabel("Nombre:");
        GridBagConstraints gbc_lbl_nombre = new GridBagConstraints();
        gbc_lbl_nombre.anchor = GridBagConstraints.EAST;
        gbc_lbl_nombre.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_nombre.gridx = 0;
        gbc_lbl_nombre.gridy = 1;
        getContentPane().add(lbl_nombre, gbc_lbl_nombre);

        JTextField textField_Nombre = new JTextField();
        GridBagConstraints gbc_textField_Nombre = new GridBagConstraints();
        gbc_textField_Nombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_Nombre.insets = new Insets(0, 0, 5, 5);
        gbc_textField_Nombre.gridx = 1;
        gbc_textField_Nombre.gridy = 1;
        gbc_textField_Nombre.gridwidth = 2;
        getContentPane().add(textField_Nombre, gbc_textField_Nombre);
        textField_Nombre.setColumns(10);

        JLabel lbl_Descripcion = new JLabel("Descripción:");
        GridBagConstraints gbc_lbl_Descripcion = new GridBagConstraints();
        gbc_lbl_Descripcion.anchor = GridBagConstraints.EAST;
        gbc_lbl_Descripcion.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_Descripcion.gridx = 0;
        gbc_lbl_Descripcion.gridy = 2;
        getContentPane().add(lbl_Descripcion, gbc_lbl_Descripcion);

        JScrollPane descripcionScrollPane = new JScrollPane();
		descripcionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_descripcionScrollPane = new GridBagConstraints();
		gbc_descripcionScrollPane.gridwidth = 2;
		gbc_descripcionScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_descripcionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_descripcionScrollPane.gridx = 1;
		gbc_descripcionScrollPane.gridy = 2;
		getContentPane().add(descripcionScrollPane, gbc_descripcionScrollPane);
		JTextArea textField_Descripcion = new JTextArea();
		textField_Descripcion.setLineWrap(true);
		textField_Descripcion.setRows(2);
		descripcionScrollPane.setViewportView(textField_Descripcion);

        JLabel lbl_SitioWeb = new JLabel("Sitio web:");
        GridBagConstraints gbc_lbl_SitioWeb = new GridBagConstraints();
        gbc_lbl_SitioWeb.anchor = GridBagConstraints.EAST;
        gbc_lbl_SitioWeb.insets = new Insets(0, 0, 5, 5);
        gbc_lbl_SitioWeb.gridx = 0;
        gbc_lbl_SitioWeb.gridy = 3;
        getContentPane().add(lbl_SitioWeb, gbc_lbl_SitioWeb);

        JTextField textField_SitioWeb = new JTextField();
        GridBagConstraints gbc_textField_SitioWeb = new GridBagConstraints();
        gbc_textField_SitioWeb.insets = new Insets(0, 0, 5, 5);
        gbc_textField_SitioWeb.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_SitioWeb.gridx = 1;
        gbc_textField_SitioWeb.gridy = 3;
        gbc_textField_SitioWeb.gridwidth = 2;
        getContentPane().add(textField_SitioWeb, gbc_textField_SitioWeb);
        textField_SitioWeb.setColumns(10);

        JButton btnAceptar = new JButton("Aceptar");
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(10, 0, 0, 5);
        gbc_btnAceptar.gridx = 1;
        gbc_btnAceptar.gridy = 4;
        getContentPane().add(btnAceptar, gbc_btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.insets = new Insets(10, 0, 0, 5);
        gbc_btnCancelar.gridx = 2;
        gbc_btnCancelar.gridy = 4;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

    	btnCancelar.addActionListener(e -> {
		    AltaInstitucion.this.dispose();
		});
		
    	btnAceptar.addActionListener((ActionEvent e) -> {
    	    String nombre = textField_Nombre.getText().trim();
    	    String descripcion = textField_Descripcion.getText().trim();
    	    String sitioWeb = textField_SitioWeb.getText().trim();

    	    try {
    	        IContInstitucion.AltaInstitucion(nombre, descripcion, sitioWeb);

    	        // Si todo salió bien, aviso y limpio campos
    	        JOptionPane.showMessageDialog(this, "Institución creada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    	        textField_Nombre.setText("");
    	        textField_Descripcion.setText("");
    	        textField_SitioWeb.setText("");
    	    } catch (Exception ex) {
    	        // Si el controlador lanza error, lo muestro
    	        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    	    }
    	});
    }
}
