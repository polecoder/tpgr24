package tarea1;


import javax.swing.JInternalFrame;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JComboBox;


public class AltaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField_Nick;
	private JTextField textField_Nombre;
	private JTextField textField_Correo;

	public AltaUsuario() {
		super("Alta de Usuario", true, true, true, true);
		setSize(400, 250);       
		setLocation(50, 50);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0,0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0,0.0,1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0,0.0,1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel nick = new JLabel("Nick:");
		GridBagConstraints gbc_nick = new GridBagConstraints();
		gbc_nick.anchor = GridBagConstraints.EAST;
		gbc_nick.insets = new Insets(0, 0, 5, 5);
		gbc_nick.gridx = 0;
		gbc_nick.gridy = 1;
		getContentPane().add(nick, gbc_nick);
		
		textField_Nick = new JTextField();
		GridBagConstraints gbc_textField_Nick = new GridBagConstraints();
		gbc_textField_Nick.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Nick.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Nick.gridx = 1;
		gbc_textField_Nick.gridy = 1;
		gbc_textField_Nick.gridwidth = 2;  
		getContentPane().add(textField_Nick, gbc_textField_Nick);
		textField_Nick.setColumns(10);
		
		JLabel label_nombre = new JLabel("Nombre:");
		GridBagConstraints gbc_label_nombre = new GridBagConstraints();
		gbc_label_nombre.anchor = GridBagConstraints.EAST;
		gbc_label_nombre.insets = new Insets(0, 0, 5, 5);
		gbc_label_nombre.gridx = 0;
		gbc_label_nombre.gridy = 2;
		getContentPane().add(label_nombre, gbc_label_nombre);
		
		textField_Nombre = new JTextField();
		GridBagConstraints gbc_textField_Nombre = new GridBagConstraints();
		gbc_textField_Nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Nombre.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Nombre.gridx = 1;
		gbc_textField_Nombre.gridy = 2;
		gbc_textField_Nombre.gridwidth = 2; 
		getContentPane().add(textField_Nombre, gbc_textField_Nombre);
		textField_Nombre.setColumns(10);
		
		JLabel label_Correo = new JLabel("Correo:");
		GridBagConstraints gbc_label_Correo = new GridBagConstraints();
		gbc_label_Correo.anchor = GridBagConstraints.EAST;
		gbc_label_Correo.insets = new Insets(0, 0, 5, 5);
		gbc_label_Correo.gridx = 0;
		gbc_label_Correo.gridy = 3;
		getContentPane().add(label_Correo, gbc_label_Correo);
		
		textField_Correo = new JTextField();
		GridBagConstraints gbc_textField_Correo = new GridBagConstraints();
		gbc_textField_Correo.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Correo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Correo.gridx = 1;
		gbc_textField_Correo.gridy = 3;
		gbc_textField_Correo.gridwidth = 2; 
		getContentPane().add(textField_Correo, gbc_textField_Correo);
		textField_Correo.setColumns(10);
		
		JLabel label_TipoUsuario = new JLabel("Tipo Usuario: ");
		GridBagConstraints gbc_label_TipoUsuario = new GridBagConstraints();
		gbc_label_TipoUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_label_TipoUsuario.gridx = 0;
		gbc_label_TipoUsuario.gridy = 4;
		getContentPane().add(label_TipoUsuario, gbc_label_TipoUsuario);
		
		JRadioButton Button_Organizador = new JRadioButton("Organizador");
		GridBagConstraints gbc_Button_Organizador = new GridBagConstraints();
		gbc_Button_Organizador.insets = new Insets(0, 0, 5, 5);
		gbc_Button_Organizador.gridx = 1;
		gbc_Button_Organizador.gridy = 4;
		getContentPane().add(Button_Organizador, gbc_Button_Organizador);
		
		JRadioButton Button_Asistente = new JRadioButton("Asistente");
		GridBagConstraints gbc_Button_Asistente = new GridBagConstraints();
		gbc_Button_Asistente.insets = new Insets(0, 0, 5, 5);
		gbc_Button_Asistente.gridx = 2;
		gbc_Button_Asistente.gridy = 4;
		getContentPane().add(Button_Asistente, gbc_Button_Asistente);
		
		JLabel label_link = new JLabel("Link: ");
		GridBagConstraints gbc_label_link = new GridBagConstraints();
		gbc_label_link.insets = new Insets(0, 0, 5, 5);
		gbc_label_link.gridx = 0;
		gbc_label_link.gridy = 5;
		label_link.setVisible(false);
		getContentPane().add(label_link, gbc_label_link);
		
		JLabel label_Institucion = new JLabel("Instituciones: ");
		GridBagConstraints gbc_label_Institucion= new GridBagConstraints();
		gbc_label_Institucion.insets = new Insets(0, 0, 5, 5);
		gbc_label_Institucion.gridx = 0;
		gbc_label_Institucion.gridy = 5;
		label_Institucion.setVisible(false);
		getContentPane().add(label_Institucion, gbc_label_Institucion);
		
		JTextField TextField_Link = new JTextField();
		TextField_Link.setColumns(10);
		TextField_Link.setVisible(false);  

		GridBagConstraints gbc_TextField_Link = new GridBagConstraints();
		gbc_TextField_Link.gridx = 1;
		gbc_TextField_Link.gridy = 5;
		gbc_TextField_Link.gridwidth = 2;
		gbc_TextField_Link.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(TextField_Link, gbc_TextField_Link);

		String[] instituciones = {"Ninguno", "Institución 1", "Institución 2", "Institución 3"};
		JComboBox<String> comboInstituciones = new JComboBox<>(instituciones);
		comboInstituciones.setVisible(false);
		
		GridBagConstraints gbc_instituciones = new GridBagConstraints();
		gbc_instituciones.gridx = 1;
		gbc_instituciones.gridy = 5;
		gbc_instituciones.gridwidth = 2;
		gbc_instituciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_instituciones.insets = new Insets(0, 0, 5, 5);
		getContentPane().add(comboInstituciones, gbc_instituciones);

		Button_Organizador.addActionListener(e -> {
			TextField_Link.setVisible(true);  
		    label_link.setVisible(true);          
		    
		    comboInstituciones.setVisible(false);
		    label_Institucion.setVisible(false);
		    
		    revalidate();                 
		    repaint();   
		});
		
		Button_Asistente.addActionListener(e -> {
			TextField_Link.setVisible(false);  
		    label_link.setVisible(false);      
		    
		    label_Institucion.setVisible(true);
		    comboInstituciones.setVisible(true);
		    
		    revalidate();
		    repaint();
		});
		
		ButtonGroup grupoTipoUsuario = new ButtonGroup();
		grupoTipoUsuario.add(Button_Organizador);   
		grupoTipoUsuario.add(Button_Asistente); 
		
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
		
		


	} 
}
