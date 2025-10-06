package presentacion;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import logica.Fabrica;
import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleUsuario;
import datatypes.DTUsuario;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import javax.swing.JButton;

public class ModificarDatosUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private String nicknameSeleccionado;
	private JTextField nombreTxt;
	private JTextField correoTxt;
	private JTextArea descripcionTxt;
	private JSpinner fechaSpinner;
	private JTextField apellidoTxt;
	private JTextField linkTxt;
	private JPanel cardPanel;
	private CardLayout cl;

	private boolean actualizandoNickname = false;

	Fabrica fabrica = Fabrica.getInstancia();
	
	public ModificarDatosUsuario() {
		super("Modificar Datos de Usuario", true, true, true, true);
		setSize(400, 300);
		setMinimumSize(new Dimension(600, 400));
		setMaximumSize(new Dimension(800, 600));
		setLocation(50, 50);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0,0,0,0};
		gridBagLayout.rowHeights = new int[]{0,0,0,0,0,0,0};
		gridBagLayout.columnWeights = new double[]{1.0,1.0,1.0,1.0};
		gridBagLayout.rowWeights = new double[]{1.0,0.0,0.0,0.0,0.0,0.0,1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		JComboBox<String> usuariosComboBox = new JComboBox<String>(); 
		List<DTUsuario> listaUsuarios = fabrica.getControladorUsuarios().listarUsuarios();
		usuariosComboBox.addItem("--Seleccionar un Usuario--");
		for (DTUsuario usuario: listaUsuarios) {
			usuariosComboBox.addItem(usuario.nickname());
		}
		GridBagConstraints gbc_usuariosComboBox = new GridBagConstraints();
		gbc_usuariosComboBox.anchor = GridBagConstraints.WEST;
		gbc_usuariosComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_usuariosComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_usuariosComboBox.gridx = 2;
		gbc_usuariosComboBox.gridy = 1;
		getContentPane().add(usuariosComboBox, gbc_usuariosComboBox);
		
		JLabel lblNewLabel_3 = new JLabel("Correo:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 2;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		correoTxt = new JTextField();
		correoTxt.setEditable(false);
		GridBagConstraints gbc_correoTxt = new GridBagConstraints();
		gbc_correoTxt.insets = new Insets(0, 0, 5, 5);
		gbc_correoTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_correoTxt.gridx = 2;
		gbc_correoTxt.gridy = 2;
		getContentPane().add(correoTxt, gbc_correoTxt);
		correoTxt.setColumns(15);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		nombreTxt = new JTextField();
		GridBagConstraints gbc_nombreTxt = new GridBagConstraints();
		gbc_nombreTxt.insets = new Insets(0, 0, 5, 5);
		gbc_nombreTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreTxt.gridx = 2;
		gbc_nombreTxt.gridy = 3;
		getContentPane().add(nombreTxt, gbc_nombreTxt);
		nombreTxt.setColumns(15);
		
		//***********************************************************//
		//******************** PANEL ORGANIZADOR ********************//
		//***********************************************************//
		JPanel panelOrganizador = new JPanel();
		GridBagLayout gbl_panelOrganizador = new GridBagLayout();
		gbl_panelOrganizador.columnWidths = new int[]{0,0};
		gbl_panelOrganizador.rowHeights = new int[]{30,30};
		gbl_panelOrganizador.columnWeights = new double[]{0.5,1.0};
		gbl_panelOrganizador.rowWeights = new double[]{1.0,0.0};
		panelOrganizador.setLayout(gbl_panelOrganizador);
		
		JLabel lblNewLabel_5 = new JLabel("Descripción:");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 0;
		panelOrganizador.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		panelOrganizador.add(scrollPane, gbc_scrollPane);
		
		descripcionTxt = new JTextArea();
		descripcionTxt.setRows(2);
		descripcionTxt.setLineWrap(true);
		descripcionTxt.setColumns(17);
		scrollPane.setViewportView(descripcionTxt);
		
		JLabel lblNewLabel_6 = new JLabel("Link:");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 1;
		panelOrganizador.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		linkTxt = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		panelOrganizador.add(linkTxt, gbc_textField);
		linkTxt.setColumns(15);
		
		//*********************************************************//
		//******************** PANEL ASISTENTE ********************//
		//*********************************************************//
		JPanel panelAsistente = new JPanel();
		JPanel wrapperAsistente = new JPanel(new BorderLayout());
		wrapperAsistente.add(panelAsistente, BorderLayout.NORTH);
		GridBagLayout gbl_panelAsistente = new GridBagLayout();
		gbl_panelAsistente.columnWidths = new int[]{0,0};
		gbl_panelAsistente.rowHeights = new int[]{30,30};
		gbl_panelAsistente.columnWeights = new double[]{0.5,1.0};
		gbl_panelAsistente.rowWeights = new double[]{0.0,0.0};
		panelAsistente.setLayout(gbl_panelAsistente);
		
		JLabel lblNewLabel_1 = new JLabel("Apellido:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panelAsistente.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		apellidoTxt = new JTextField();
		GridBagConstraints gbc_apellidoTxt = new GridBagConstraints();
		gbc_apellidoTxt.insets = new Insets(0, 0, 5, 0);
		gbc_apellidoTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_apellidoTxt.gridx = 1;
		gbc_apellidoTxt.gridy = 0;
		panelAsistente.add(apellidoTxt, gbc_apellidoTxt);
		apellidoTxt.setColumns(17);
		
		JLabel lblNewLabel_4 = new JLabel("Nacimiento:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 1;
		panelAsistente.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		fechaSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor editor = new JSpinner.DateEditor(fechaSpinner, "dd/MM/yyyy");
		fechaSpinner.setEditor(editor);
		GridBagConstraints gbc_fechaSpinner = new GridBagConstraints();
		gbc_fechaSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_fechaSpinner.gridx = 1;
		gbc_fechaSpinner.gridy = 1;
		panelAsistente.add(fechaSpinner, gbc_fechaSpinner);
		GridBagConstraints gbcWrapA1 = new GridBagConstraints();
		gbcWrapA1.gridx = 0;
		gbcWrapA1.gridy = 0;
		gbcWrapA1.weightx = 1.0;
		gbcWrapA1.weighty = 1.0;
		gbcWrapA1.anchor = GridBagConstraints.EAST;
		gbcWrapA1.fill = GridBagConstraints.NONE;
		
		//*****************************************************//
		//******************** CARD LAYOUT ********************//
		//*****************************************************//
		cardPanel = new JPanel(new CardLayout());
		wrapperAsistente.setName("Asistente");
		cardPanel.add(wrapperAsistente, "Asistente");
		panelOrganizador.setName("Organizador");
		cardPanel.add(panelOrganizador, "Organizador");
		cardPanel.setVisible(false);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
		getContentPane().add(cardPanel, gbc_panel);
		cl = (CardLayout) cardPanel.getLayout();
		
		// Botones
		JPanel panelBotones = new JPanel();
		GridBagConstraints gbc_panelBotones = new GridBagConstraints();
		gbc_panelBotones.insets = new Insets(0, 0, 0, 5);
		gbc_panelBotones.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBotones.gridx = 2;
		gbc_panelBotones.gridy = 5;
		getContentPane().add(panelBotones, gbc_panelBotones);
		
		JButton btnAceptar = new JButton("Aceptar");
		panelBotones.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		panelBotones.add(btnCancelar);
		
		// Actualización al cambiar el usuario del combo.
		usuariosComboBox.addActionListener(e -> {
			actualizandoNickname = true;
			this.nicknameSeleccionado = (String) usuariosComboBox.getSelectedItem();
		    if (this.nicknameSeleccionado != null && !this.nicknameSeleccionado.equals("--Seleccionar un Usuario--")) {
		    	cardPanel.setVisible(true);
		        actualizarDatosUsuario(this.nicknameSeleccionado);
		    } else {
		    	cardPanel.setVisible(false);
		    	resetCampos();
		    }
		    actualizandoNickname = false;
		});
		
		// Aceptar modificación
		btnAceptar.addActionListener(e -> {
			if (!usuariosComboBox.getSelectedItem().equals("--Seleccionar un Usuario--")) {
				String nickname = (String) usuariosComboBox.getSelectedItem();
				String nombre = nombreTxt.getText().trim();
				if (getCartaActiva().equals("Asistente")) {
					String apellido = apellidoTxt.getText().trim();
					Date nacimiento = (Date) fechaSpinner.getValue();
					try {
						fabrica.getControladorUsuarios().modificarDatosAsistente(nickname, nombre, apellido, nacimiento);
						JOptionPane.showMessageDialog(this, "Los datos del asistente han sido modificados con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else if (getCartaActiva().equals("Organizador")) {
					String descripcion = descripcionTxt.getText().trim();
					String link = linkTxt.getText().trim();
					try {
						fabrica.getControladorUsuarios().modificarDatosOrganizador(nickname, nombre, descripcion, link);
						JOptionPane.showMessageDialog(this, "Los datos del asistente han sido modificados con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				this.actualizarDatosUsuario(nickname);
			}
		});
		
		// Cancelar operación
		btnCancelar.addActionListener(e -> {
			this.dispose();
		});
	}
	
	private void actualizarDatosUsuario(String nickname) {
		try {
			DTDetalleUsuario usuario = fabrica.getControladorUsuarios().mostrarDetalleUsuario(nickname);
			nombreTxt.setText(usuario.getNombre());
			correoTxt.setText(usuario.getCorreo());
			if (usuario instanceof DTDetalleAsistente) {
				DTDetalleAsistente asistente = (DTDetalleAsistente) usuario;
				apellidoTxt.setText(asistente.getApellido());
				fechaSpinner.setValue(asistente.getNacimiento());
				cl.show(cardPanel, "Asistente");
			} else if (usuario instanceof DTDetalleOrganizador) {
				DTDetalleOrganizador organizador = (DTDetalleOrganizador) usuario;
				descripcionTxt.setText(organizador.getDescripcion());
				linkTxt.setText(organizador.getLink());
				cl.show(cardPanel, "Organizador");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getCartaActiva() {
	    for (Component componente : cardPanel.getComponents()) {
	        if (componente.isVisible()) {
	            return componente.getName();
	        }
	    }
	    return null;
	}
	
	private void resetCampos() {
		nombreTxt.setText("");
		correoTxt.setText("");
		apellidoTxt.setText("");
		fechaSpinner.setValue(new Date());
		descripcionTxt.setText("");
		linkTxt.setText("");
	}
}