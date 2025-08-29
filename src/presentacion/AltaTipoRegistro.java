package presentacion;

import logica.ControladorEventos;
import logica.datatypes.DTEvento;
import excepciones.NombreTipoRegistroRepetido;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;


public class AltaTipoRegistro extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textField_Nombre;
	private JTextField textField_Descripcion;
	private JTextField textField_Costo;
	private JTextField textField_Cupos;
	private ControladorEventos controladorEventos = ControladorEventos.getInstancia();
	
	public AltaTipoRegistro() {		
		super("Alta Tipo de registro", true, true, true, true);
		setSize(300, 247);       
		setLocation(50, 50);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		
		gridBagLayout.columnWidths = new int[]{0, 0, 0,0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0}; // 7 elementos
		gridBagLayout.columnWeights = new double[]{1.0, 1.0,0.0,1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);
		
		//----------NOMBRE----------
		JLabel lbl_Nombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lbl_Nombre = new GridBagConstraints();
		gbc_lbl_Nombre.anchor = GridBagConstraints.EAST;
		gbc_lbl_Nombre.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Nombre.gridx = 0;
		gbc_lbl_Nombre.gridy = 0;
		getContentPane().add(lbl_Nombre, gbc_lbl_Nombre);
		
		textField_Nombre = new JTextField();
		GridBagConstraints gbc_textField_Nombre = new GridBagConstraints();
		gbc_textField_Nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Nombre.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Nombre.gridx = 1;
		gbc_textField_Nombre.gridy = 0;
		gbc_textField_Nombre.gridwidth = 2;  
		getContentPane().add(textField_Nombre, gbc_textField_Nombre);
		textField_Nombre.setColumns(10);
		//----------NOMBRE----------
		
		//----------DESCRIPCION----------
		JLabel lbl_Descripcion = new JLabel("Descripción:");
		GridBagConstraints gbc_lbl_Descripcion = new GridBagConstraints();
		gbc_lbl_Descripcion.anchor = GridBagConstraints.EAST;
		gbc_lbl_Descripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Descripcion.gridx = 0;
		gbc_lbl_Descripcion.gridy = 1;
		getContentPane().add(lbl_Descripcion, gbc_lbl_Descripcion);
		
		textField_Descripcion = new JTextField();
		GridBagConstraints gbc_textField_Descripcion = new GridBagConstraints();
		gbc_textField_Descripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Descripcion.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Descripcion.gridx = 1;
		gbc_textField_Descripcion.gridy = 1;
		gbc_textField_Descripcion.gridwidth = 2; 
		getContentPane().add(textField_Descripcion, gbc_textField_Descripcion);
		textField_Descripcion.setColumns(10);
		//----------DESCRIPCION----------
		
		//----------COSTO----------
		JLabel lbl_Costo = new JLabel("Costo:");
		GridBagConstraints gbc_lbl_Costo = new GridBagConstraints();
		gbc_lbl_Costo.anchor = GridBagConstraints.EAST;
		gbc_lbl_Costo.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Costo.gridx = 0;
		gbc_lbl_Costo.gridy = 2;
		getContentPane().add(lbl_Costo, gbc_lbl_Costo);
		
		textField_Costo = new JTextField();
		GridBagConstraints gbc_textField_Costo = new GridBagConstraints();
		gbc_textField_Costo.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Costo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Costo.gridx = 1;
		gbc_textField_Costo.gridy = 2;
		gbc_textField_Costo.gridwidth = 2; 
		getContentPane().add(textField_Costo, gbc_textField_Costo);
		textField_Costo.setColumns(10);
		//----------COSTO----------
		
		//----------CUPOS----------
		JLabel lbl_Cupos = new JLabel("Cupos máximos:");
		GridBagConstraints gbc_lbl_Cupos = new GridBagConstraints();
		gbc_lbl_Cupos.anchor = GridBagConstraints.EAST;
		gbc_lbl_Cupos.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Cupos.gridx = 0;
		gbc_lbl_Cupos.gridy = 3;
		getContentPane().add(lbl_Cupos, gbc_lbl_Cupos);
		
		textField_Cupos = new JTextField();
		textField_Cupos.setColumns(10);
		GridBagConstraints gbc_textField_Cupos = new GridBagConstraints();
		gbc_textField_Cupos.gridwidth = 2;
		gbc_textField_Cupos.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Cupos.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Cupos.gridx = 1;
		gbc_textField_Cupos.gridy = 3;
		getContentPane().add(textField_Cupos, gbc_textField_Cupos);
		//----------CUPOS----------
		
		//----------EVENTO----------
		JLabel lbl_Evento = new JLabel("Evento:");
		GridBagConstraints gbc_lbl_Evento= new GridBagConstraints();
		gbc_lbl_Evento.anchor = GridBagConstraints.EAST;
		gbc_lbl_Evento.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Evento.gridx = 0;
		gbc_lbl_Evento.gridy = 4;
		getContentPane().add(lbl_Evento, gbc_lbl_Evento);
		
		String[] eventos = controladorEventos.getNombreEventos(); // Inicializo los eventos al iniciar el Caso de Uso
		JComboBox<String> comboEvento = new JComboBox<>(eventos);
		GridBagConstraints gbc_comboEvento = new GridBagConstraints();
		gbc_comboEvento.gridx = 1;
		gbc_comboEvento.gridy = 4;
		gbc_comboEvento.gridwidth = 2;
		gbc_comboEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEvento.insets = new Insets(0, 0, 5, 5);
		getContentPane().add(comboEvento, gbc_comboEvento);
		//----------EVENTO----------
		
		//----------EDICION----------
		JLabel lbl_Edicion = new JLabel("Edición:");
		GridBagConstraints gbc_lbl_Edicion = new GridBagConstraints();
		gbc_lbl_Edicion.anchor = GridBagConstraints.EAST;
		gbc_lbl_Edicion.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Edicion.gridx = 0;
		gbc_lbl_Edicion.gridy = 5;
		getContentPane().add(lbl_Edicion, gbc_lbl_Edicion);
		
		String[] ediciones = controladorEventos.getNombresEdiciones(comboEvento.getItemAt(comboEvento.getSelectedIndex()));
		JComboBox<String> comboEdicion = new JComboBox<>(ediciones);
		GridBagConstraints gbc_comboEdicion = new GridBagConstraints();
		gbc_comboEdicion.gridwidth = 2;
		gbc_comboEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_comboEdicion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEdicion.gridx = 1;
		gbc_comboEdicion.gridy = 5;
		getContentPane().add(comboEdicion, gbc_comboEdicion);
		//----------EDICION----------
		
		
		//----------ACEPTAR/CANCELAR----------
		JButton Button_Aceptar = new JButton("Aceptar");
		GridBagConstraints gbc_Button_Aceptar = new GridBagConstraints();
		gbc_Button_Aceptar.insets = new Insets(0, 0, 0, 10);
		gbc_Button_Aceptar.gridx = 1;
		gbc_Button_Aceptar.gridy = 6;
		getContentPane().add(Button_Aceptar, gbc_Button_Aceptar);
		
		JButton Button_Cancelar = new JButton("Cancelar");
		GridBagConstraints gbc_Button_Cancelar = new GridBagConstraints();
		gbc_Button_Cancelar.insets = new Insets(0, 0, 0, 10);
		gbc_Button_Cancelar.gridx = 2;
		gbc_Button_Cancelar.gridy = 6;
		getContentPane().add(Button_Cancelar, gbc_Button_Cancelar);
		//----------ACEPTAR/CANCELAR----------
		
		//----------ACTIONLISTENERS----------
		// Actualizo opciones de Edicion al cambiar de Evento
		comboEvento.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) { // Cuando se abre el combo
            }
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { // Cuando se cierra el combo
            	// Limpiar opciones viejas de comboEdicion
            	comboEdicion.removeAllItems();
            	// Agregar nuevas opciones a comboEdicion
            	String eventoSeleccionado = comboEvento.getItemAt(comboEvento.getSelectedIndex());
            	String[] nombreEdiciones = controladorEventos.getNombresEdiciones(eventoSeleccionado);
            	for (String nombre : nombreEdiciones) {
            		comboEdicion.addItem(nombre);
            	}
            }
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) { // No necesitamos hacer nada acá
            }
        });
		
		// Aceptar
		Button_Aceptar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e ) {
				// Reviso formatos
				try {
					float costo = Float.parseFloat(textField_Costo.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "El costo debe ser un número");
					return;
				};
				try {
					int cuposMax = Integer.parseInt(textField_Cupos.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Los cupos deben ser un entero");
					return;
				};
				if (comboEvento.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un evento");
					return;
				}
				if (comboEdicion.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar una edicion");
					return;
				}
				if (textField_Nombre.getText() == "") {
					JOptionPane.showMessageDialog(null,  "El nombre no puede ser vacio");
					return;
				}
				// Reviso formatos
				String nombre = textField_Nombre.getText(); // Obtengo nombre
				String descripcion = textField_Descripcion.getText(); // Obtengo descripcion
				String nombreEvento = comboEvento.getItemAt(comboEvento.getSelectedIndex()); // Obtengo nombreEvento
				String nombreEdicion = comboEdicion.getItemAt(comboEdicion.getSelectedIndex()); // Obtengo nombreEdicion
				float costo = Float.parseFloat(textField_Costo.getText()); // Obtengo costo
				int cuposMax = Integer.parseInt(textField_Cupos.getText()); // Obtengo cuposMax
				
				try {
					controladorEventos.altaTipoRegistro(nombre, descripcion, costo, cuposMax, nombreEvento, nombreEdicion);
				} catch (NombreTipoRegistroRepetido ex) {
					JOptionPane.showMessageDialog(null,  "El nombre del TipoRegistro ya esta en uso para esta Edicion");
					return;
				}
			}
		});
		//----------ACTIONLISTENERS----------
	}

}