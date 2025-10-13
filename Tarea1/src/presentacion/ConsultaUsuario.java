package presentacion;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;

import logica.Fabrica;
import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleUsuario;
import datatypes.DTEventoEdicion;
import datatypes.DTRegistro;
import datatypes.DTUsuario;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.ScrollPaneConstants;

public class ConsultaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private String nicknameSeleccionado;
	private JTextField nombreTxt;
	private JTextField correoTxt;
	private JTextField apellidoTextField;
	private JTextField institucionTextField;
	private JTextArea descripcionTextArea;
	private JTextField fechaNacimientoTextField;
	private JTextField linkTextField;
	
	private JPanel cardPanel, cardPanel1, cardPanel2;
	private CardLayout cl, cl1, cl2;
	
	private boolean actualizandoNicknameAsistente = false;

	private Fabrica fabrica = Fabrica.getInstancia();
	
	public ConsultaUsuario(JDesktopPane desktopPane) {
		super("Consulta Usuario", true, true, true, true);
		setSize(400, 300);
		setMinimumSize(new Dimension(400, 300));
		setMaximumSize(new Dimension(800, 600));
		setLocation(50, 50);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 133, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(5, 0, 5, 5);
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
		gbc_usuariosComboBox.insets = new Insets(5, 0, 5, 5);
		gbc_usuariosComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_usuariosComboBox.gridx = 2;
		gbc_usuariosComboBox.gridy = 1;
		getContentPane().add(usuariosComboBox, gbc_usuariosComboBox);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(5, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 2;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		nombreTxt = new JTextField();
		nombreTxt.setEditable(false);
		GridBagConstraints gbc_nombreTxt = new GridBagConstraints();
		gbc_nombreTxt.insets = new Insets(5, 0, 5, 5);
		gbc_nombreTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreTxt.gridx = 2;
		gbc_nombreTxt.gridy = 2;
		getContentPane().add(nombreTxt, gbc_nombreTxt);
		nombreTxt.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Correo:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(5, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 3;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		correoTxt = new JTextField();
		correoTxt.setEditable(false);
		GridBagConstraints gbc_correoTxt = new GridBagConstraints();
		gbc_correoTxt.insets = new Insets(5, 0, 5, 5);
		gbc_correoTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_correoTxt.gridx = 2;
		gbc_correoTxt.gridy = 3;
		getContentPane().add(correoTxt, gbc_correoTxt);
		correoTxt.setColumns(10);
		
		// ==== Panel ASISTENTE Campo 1 ====
		JPanel panelAsistente = new JPanel(new GridBagLayout());

		JLabel lblApellido = new JLabel("Apellido:");
		GridBagConstraints gbcLblAp = new GridBagConstraints();
		gbcLblAp.gridx = 0;
		gbcLblAp.gridy = 0;
		gbcLblAp.insets = new Insets(5, 5, 5, 10);
		gbcLblAp.anchor = GridBagConstraints.EAST;
		panelAsistente.add(lblApellido, gbcLblAp);

		apellidoTextField = new JTextField(20);
		apellidoTextField.setEditable(false);
		GridBagConstraints gbcTxtAp = new GridBagConstraints();
		gbcTxtAp.gridx = 1;
		gbcTxtAp.gridy = 0;
		gbcTxtAp.insets = new Insets(5, 0, 5, 0);
		gbcTxtAp.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtAp.weightx = 1.0;
		panelAsistente.add(apellidoTextField, gbcTxtAp);

		// Nuevo campo: Teléfono (solo para Asistente)
		JLabel lblInstitucion = new JLabel("Institucion:");
		GridBagConstraints gbcLblTel = new GridBagConstraints();
		gbcLblTel.gridx = 0;
		gbcLblTel.gridy = 1;
		gbcLblTel.insets = new Insets(5, 5, 5, 10);
		gbcLblTel.anchor = GridBagConstraints.EAST;
		panelAsistente.add(lblInstitucion, gbcLblTel);

		institucionTextField = new JTextField(20);
		institucionTextField.setEditable(false);
		GridBagConstraints gbcTxtTel = new GridBagConstraints();
		gbcTxtTel.gridx = 1;
		gbcTxtTel.gridy = 1;
		gbcTxtTel.insets = new Insets(5, 0, 5, 0);
		gbcTxtTel.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtTel.weightx = 1.0;
		panelAsistente.add(institucionTextField, gbcTxtTel);
		
		JPanel cardAsistente = new JPanel(new GridBagLayout());
		GridBagConstraints gbcWrapA = new GridBagConstraints();
		gbcWrapA.gridx = 0;
		gbcWrapA.gridy = 0;
		gbcWrapA.weightx = 1.0;
		gbcWrapA.weighty = 1.0;
		gbcWrapA.anchor = GridBagConstraints.EAST;
		gbcWrapA.fill = GridBagConstraints.NONE;
		cardAsistente.add(panelAsistente, gbcWrapA);

		// ==== Panel ORGANIZADOR Campo 1 ====
		JPanel panelOrganizador = new JPanel(new GridBagLayout());

		JLabel lblDesc = new JLabel("Descripción:");
		GridBagConstraints gbcLblDesc = new GridBagConstraints();
		gbcLblDesc.gridx = 0;
		gbcLblDesc.gridy = 0;
		gbcLblDesc.insets = new Insets(5, 5, 5, 10);
		gbcLblDesc.anchor = GridBagConstraints.EAST;
		panelOrganizador.add(lblDesc, gbcLblDesc);

		descripcionTextArea = new JTextArea(2, 20);
		descripcionTextArea.setLineWrap(true);
		descripcionTextArea.setEditable(false);
		JScrollPane spDesc = new JScrollPane(descripcionTextArea);
		spDesc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbcTxtDesc = new GridBagConstraints();
		gbcTxtDesc.gridx = 1;
		gbcTxtDesc.gridy = 0;
		gbcTxtDesc.insets = new Insets(5, 0, 5, 0);
		gbcTxtDesc.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtDesc.weightx = 1.0;
		panelOrganizador.add(spDesc, gbcTxtDesc);

		JPanel cardOrganizador = new JPanel(new GridBagLayout());
		GridBagConstraints gbcWrapO = new GridBagConstraints();
		gbcWrapO.gridx = 0;
		gbcWrapO.gridy = 0;
		gbcWrapO.weightx = 1.0;
		gbcWrapO.weighty = 1.0;
		gbcWrapO.anchor = GridBagConstraints.EAST;
		gbcWrapO.fill = GridBagConstraints.NONE;
		cardOrganizador.add(panelOrganizador, gbcWrapO);

		// ==== CardLayout Campo 1 ====
		cardPanel = new JPanel(new CardLayout());
		cardPanel.add(cardAsistente,   "Asistente");
		cardPanel.add(cardOrganizador, "Organizador");
		cardPanel.setVisible(false);
	
		// === Agregar el cardPanel al GridBag del frame ===
		GridBagConstraints gbc_cardPanel = new GridBagConstraints();
		gbc_cardPanel.gridwidth = 2;
		gbc_cardPanel.insets = new Insets(0, 0, 5, 5);
		gbc_cardPanel.fill = GridBagConstraints.BOTH;
		gbc_cardPanel.gridx = 1;
		gbc_cardPanel.gridy = 4;
		getContentPane().add(cardPanel, gbc_cardPanel);

		cl = (CardLayout) cardPanel.getLayout();
		
		// ==== Panel ASISTENTE Campo 2 ====
		JPanel panelAsistente1 = new JPanel(new GridBagLayout());

		JLabel lblFechaNacimiento = new JLabel("Fecha de nacimiento:");
		GridBagConstraints gbcLblFecha = new GridBagConstraints();
		gbcLblFecha.gridx = 0;
		gbcLblFecha.gridy = 0;
		gbcLblFecha.insets = new Insets(5, 5, 5, 10);
		gbcLblFecha.anchor = GridBagConstraints.EAST;
		panelAsistente1.add(lblFechaNacimiento, gbcLblFecha);

		fechaNacimientoTextField = new JTextField(20);
		fechaNacimientoTextField.setEditable(false);
		GridBagConstraints gbcTxtFecha = new GridBagConstraints();
		gbcTxtFecha.gridx = 1;
		gbcTxtFecha.gridy = 0;
		gbcTxtFecha.insets = new Insets(5, 0, 5, 0);
		gbcTxtFecha.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFecha.weightx = 1.0;
		panelAsistente1.add(fechaNacimientoTextField, gbcTxtFecha);
		
		JPanel cardAsistente1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbcWrapA1 = new GridBagConstraints();
		gbcWrapA1.gridx = 0;
		gbcWrapA1.gridy = 0;
		gbcWrapA1.weightx = 1.0;
		gbcWrapA1.weighty = 1.0;
		gbcWrapA1.anchor = GridBagConstraints.EAST;
		gbcWrapA1.fill = GridBagConstraints.NONE;
		cardAsistente1.add(panelAsistente1, gbcWrapA1);

		// ==== Panel ORGANIZADOR Campo 2 ====
		JPanel panelOrganizador1 = new JPanel(new GridBagLayout());

		JLabel lblLink = new JLabel("Link:");
		GridBagConstraints gbcLblLink = new GridBagConstraints();
		gbcLblLink.gridx = 0;
		gbcLblLink.gridy = 0;
		gbcLblLink.insets = new Insets(5, 5, 5, 10);
		gbcLblLink.anchor = GridBagConstraints.EAST;
		panelOrganizador1.add(lblLink, gbcLblLink);

		linkTextField = new JTextField(20);
		linkTextField.setEditable(false);
		GridBagConstraints gbcTxtLink = new GridBagConstraints();
		gbcTxtLink.gridx = 1;
		gbcTxtLink.gridy = 0;
		gbcTxtLink.insets = new Insets(5, 0, 5, 0);
		gbcTxtLink.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtLink.weightx = 1.0;
		panelOrganizador1.add(linkTextField, gbcTxtLink);

		JPanel cardOrganizador1 = new JPanel(new GridBagLayout());
		GridBagConstraints gbcWrapO1 = new GridBagConstraints();
		gbcWrapO1.gridx = 0;
		gbcWrapO1.gridy = 0;
		gbcWrapO1.weightx = 1.0;
		gbcWrapO1.weighty = 1.0;
		gbcWrapO1.anchor = GridBagConstraints.EAST;
		gbcWrapO1.fill = GridBagConstraints.NONE;
		cardOrganizador1.add(panelOrganizador1, gbcWrapO1);

		// ==== CardLayout Campo 2 ====
		cardPanel1 = new JPanel(new CardLayout());
		cardPanel1.add(cardAsistente1, "Asistente");
		cardPanel1.add(cardOrganizador1, "Organizador");
		cardPanel1.setVisible(false);


		// === Agregar el cardPanel al GridBag del frame ===
		GridBagConstraints gbc_cardPanel1 = new GridBagConstraints();
		gbc_cardPanel1.gridwidth = 2;
		gbc_cardPanel1.insets = new Insets(0, 0, 5, 5);
		gbc_cardPanel1.fill = GridBagConstraints.BOTH;
		gbc_cardPanel1.gridx = 1;
		gbc_cardPanel1.gridy = 5;
		getContentPane().add(cardPanel1, gbc_cardPanel1);

		cl1 = (CardLayout) cardPanel1.getLayout();
		
		// ==== Panel ASISTENTE Campo 3 ====
		JPanel panelAsistente2 = new JPanel(new GridBagLayout());

		JLabel lblRegistros = new JLabel("Registros:");
		GridBagConstraints gbcLblRegistros = new GridBagConstraints();
		gbcLblRegistros.gridx = 0;
		gbcLblRegistros.gridy = 0;
		gbcLblRegistros.insets = new Insets(5, 5, 5, 10);
		gbcLblRegistros.anchor = GridBagConstraints.EAST;
		panelAsistente2.add(lblRegistros, gbcLblRegistros);

		JComboBox<String> registrosCombo = new JComboBox<>();
		registrosCombo.setPreferredSize(new Dimension(225, 20));
		GridBagConstraints gbcCombo = new GridBagConstraints();
		gbcCombo.gridx = 1;
		gbcCombo.gridy = 0;
		gbcCombo.insets = new Insets(5, 0, 5, 0);
		gbcCombo.fill = GridBagConstraints.HORIZONTAL;
		gbcCombo.weightx = 1.0;
		panelAsistente2.add(registrosCombo, gbcCombo);

		JPanel cardAsistente2 = new JPanel(new GridBagLayout());
		GridBagConstraints gbcWrapA2 = new GridBagConstraints();
		gbcWrapA2.gridx = 0;
		gbcWrapA2.gridy = 0;
		gbcWrapA2.weightx = 1.0;
		gbcWrapA2.weighty = 1.0;
		gbcWrapA2.anchor = GridBagConstraints.EAST;
		gbcWrapA2.fill = GridBagConstraints.NONE;
		cardAsistente2.add(panelAsistente2, gbcWrapA2);

		// ==== Panel ORGANIZADOR Campo 3 ====
		JPanel panelOrganizador2 = new JPanel(new GridBagLayout());

		JLabel lblEdiciones = new JLabel("Ediciones:");
		GridBagConstraints gbcLblEdiciones = new GridBagConstraints();
		gbcLblEdiciones.gridx = 0;
		gbcLblEdiciones.gridy = 0;
		gbcLblEdiciones.insets = new Insets(5, 5, 5, 10);
		gbcLblEdiciones.anchor = GridBagConstraints.EAST;
		panelOrganizador2.add(lblEdiciones, gbcLblEdiciones);

		JComboBox<String> edicionesCombo = new JComboBox<>();
		edicionesCombo.setPreferredSize(new Dimension(225, 20));
		GridBagConstraints gbcEdicionesCombo = new GridBagConstraints();
		gbcEdicionesCombo.gridx = 1;
		gbcEdicionesCombo.gridy = 0;
		gbcEdicionesCombo.insets = new Insets(5, 0, 5, 0);
		gbcEdicionesCombo.fill = GridBagConstraints.HORIZONTAL;
		gbcEdicionesCombo.weightx = 1.0;
		panelOrganizador2.add(edicionesCombo, gbcEdicionesCombo);

		JPanel cardOrganizador2 = new JPanel(new GridBagLayout());
		GridBagConstraints gbcWrapO2 = new GridBagConstraints();
		gbcWrapO2.gridx = 0;
		gbcWrapO2.gridy = 0;
		gbcWrapO2.weightx = 1.0;
		gbcWrapO2.weighty = 1.0;
		gbcWrapO2.anchor = GridBagConstraints.EAST;
		gbcWrapO2.fill = GridBagConstraints.NONE;
		cardOrganizador2.add(panelOrganizador2, gbcWrapO2);

		// ==== CardLayout Campo 3 ====
		cardPanel2 = new JPanel(new CardLayout());
		cardPanel2.add(cardAsistente2,   "Asistente");
		cardPanel2.add(cardOrganizador2, "Organizador");
		cardPanel2.setVisible(false);

		// === Agregar el cardPanel al GridBag del frame ===
		GridBagConstraints gbc_cardPanel2 = new GridBagConstraints();
		gbc_cardPanel2.gridwidth = 2;
		gbc_cardPanel2.insets = new Insets(0, 0, 0, 5);
		gbc_cardPanel2.fill = GridBagConstraints.BOTH;
		gbc_cardPanel2.gridx = 1;
		gbc_cardPanel2.gridy = 6;
		getContentPane().add(cardPanel2, gbc_cardPanel2);

		cl2 = (CardLayout) cardPanel2.getLayout();
		
		/**
		 * Actualización al cambiar el usuario del combo.
		 */
		usuariosComboBox.addActionListener(e -> {
			actualizandoNicknameAsistente = true;
			this.nicknameSeleccionado = (String) usuariosComboBox.getSelectedItem();
			if ( this.nicknameSeleccionado == "--Seleccionar un Usuario--") {
				nombreTxt.setText("");
				correoTxt.setText("");
				edicionesCombo.removeAllItems();
				cardPanel.setVisible(false);
				cardPanel1.setVisible(false);
				cardPanel2.setVisible(false);

			}else if (this.nicknameSeleccionado != null ) {
		        actualizarDatosUsuario(this.nicknameSeleccionado);
		        List<DTRegistro> listaRegistros = fabrica.getControladorUsuarios().listarRegistrosUsuario(nicknameSeleccionado);
				registrosCombo.removeAllItems();
				for (DTRegistro registro: listaRegistros) {
					registrosCombo.addItem(registro.nombreEdicion());
				}
				List<DTEventoEdicion> listaEdiciones = fabrica.getControladorUsuarios().getEventosEdicionesDeOrganizador(nicknameSeleccionado);
				edicionesCombo.removeAllItems();
				for (DTEventoEdicion edicion: listaEdiciones) {
					edicionesCombo.addItem(edicion.nombreEvento() + ", " + edicion.nombreEdicion());
				}
		    }
		    
		    actualizandoNicknameAsistente = false;
		});
		
		/**
		 * Actualización al cambiar el registro del combo.
		 */
		registrosCombo.addActionListener(e -> {
			if (!actualizandoNicknameAsistente) {
				String nicknameSeleccionado = (String) usuariosComboBox.getSelectedItem();
				String registroSeleccionado = (String) registrosCombo.getSelectedItem();
			    if (registroSeleccionado != null) {
			        ConsultaRegistro ventanaConsultaRegistro = new ConsultaRegistro(nicknameSeleccionado, registroSeleccionado);
			        desktopPane.add(ventanaConsultaRegistro);
			        ventanaConsultaRegistro.setVisible(true);
				    try {
				        ventanaConsultaRegistro.setSelected(true);
				    } catch (PropertyVetoException ex) {
				        ex.printStackTrace();
				    }
			    }
			}
		});
		
		/**
		 * Actualización al cambiar la edicion del combo.
		 */
		edicionesCombo.addActionListener(e -> {
			if (!actualizandoNicknameAsistente) {
				String eventoEdicionSeleccionados = (String) edicionesCombo.getSelectedItem();
			    if (eventoEdicionSeleccionados != null) {
			    	String[] separacion = eventoEdicionSeleccionados.split(", ");
			    	String evento = separacion[0];
			    	String edicion = separacion[1];
			        ConsultaEdicionEvento ventanaConsultaRegistro = new ConsultaEdicionEvento(evento, edicion, desktopPane);
			        desktopPane.add(ventanaConsultaRegistro);
			        ventanaConsultaRegistro.setVisible(true);
				    try {
				        ventanaConsultaRegistro.setSelected(true);
				    } catch (PropertyVetoException ex) {
				        ex.printStackTrace();
				    }
			    }
			}
		});
	}
	
	private void actualizarDatosUsuario(String nickname) {
		try {
			cardPanel.setVisible(true);
			cardPanel1.setVisible(true);
			cardPanel2.setVisible(true);
			DTDetalleUsuario usuario = fabrica.getControladorUsuarios().mostrarDetalleUsuario(nickname);
			nombreTxt.setText(usuario.getNombre());
			correoTxt.setText(usuario.getCorreo());
			if (usuario instanceof DTDetalleAsistente) {
				DTDetalleAsistente asistente = (DTDetalleAsistente) usuario;
				String nombreInstitucion = fabrica.getControladorUsuarios().getNombreInstitucionAsistente(usuario.getNickname());
				institucionTextField.setText(nombreInstitucion);
				apellidoTextField.setText(asistente.getApellido());
		        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		        String nacimiento = formato.format(asistente.getNacimiento());
				fechaNacimientoTextField.setText(nacimiento);
				cl.show(cardPanel, "Asistente");
				cl1.show(cardPanel1, "Asistente");
				cl2.show(cardPanel2, "Asistente");
			} else if (usuario instanceof DTDetalleOrganizador) {
				DTDetalleOrganizador organizador = (DTDetalleOrganizador) usuario;
				descripcionTextArea.setText(organizador.getDescripcion());
				linkTextField.setText(organizador.getLink());
				cl.show(cardPanel, "Organizador");
				cl1.show(cardPanel1, "Organizador");
				cl2.show(cardPanel2, "Organizador");
			}
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

}