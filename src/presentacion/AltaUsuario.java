package presentacion;


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
		setSize(300, 200);       
		setLocation(50, 50);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0,0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0,0.0,1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0,0.0,1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lbl_nick = new JLabel("Nick:");
		GridBagConstraints gbc_lbl_nick = new GridBagConstraints();
		gbc_lbl_nick.anchor = GridBagConstraints.EAST;
		gbc_lbl_nick.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_nick.gridx = 0;
		gbc_lbl_nick.gridy = 1;
		getContentPane().add(lbl_nick, gbc_lbl_nick);
		
		textField_Nick = new JTextField();
		GridBagConstraints gbc_textField_Nick = new GridBagConstraints();
		gbc_textField_Nick.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Nick.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Nick.gridx = 1;
		gbc_textField_Nick.gridy = 1;
		gbc_textField_Nick.gridwidth = 2;  
		getContentPane().add(textField_Nick, gbc_textField_Nick);
		textField_Nick.setColumns(10);
		
		JLabel lbl_nombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lbl_nombre = new GridBagConstraints();
		gbc_lbl_nombre.anchor = GridBagConstraints.EAST;
		gbc_lbl_nombre.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_nombre.gridx = 0;
		gbc_lbl_nombre.gridy = 2;
		getContentPane().add(lbl_nombre, gbc_lbl_nombre);
		
		textField_Nombre = new JTextField();
		GridBagConstraints gbc_textField_Nombre = new GridBagConstraints();
		gbc_textField_Nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Nombre.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Nombre.gridx = 1;
		gbc_textField_Nombre.gridy = 2;
		gbc_textField_Nombre.gridwidth = 2; 
		getContentPane().add(textField_Nombre, gbc_textField_Nombre);
		textField_Nombre.setColumns(10);
		
		JLabel lbl_Correo = new JLabel("Correo:");
		GridBagConstraints gbc_lbl_Correo = new GridBagConstraints();
		gbc_lbl_Correo.anchor = GridBagConstraints.EAST;
		gbc_lbl_Correo.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Correo.gridx = 0;
		gbc_lbl_Correo.gridy = 3;
		getContentPane().add(lbl_Correo, gbc_lbl_Correo);
		
		textField_Correo = new JTextField();
		GridBagConstraints gbc_textField_Correo = new GridBagConstraints();
		gbc_textField_Correo.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Correo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Correo.gridx = 1;
		gbc_textField_Correo.gridy = 3;
		gbc_textField_Correo.gridwidth = 2; 
		getContentPane().add(textField_Correo, gbc_textField_Correo);
		textField_Correo.setColumns(10);
		
		JLabel lbl_TipoUsuario = new JLabel("Tipo Usuario: ");
		GridBagConstraints gbc_lbl_TipoUsuario = new GridBagConstraints();
		gbc_lbl_TipoUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_TipoUsuario.gridx = 0;
		gbc_lbl_TipoUsuario.gridy = 4;
		getContentPane().add(lbl_TipoUsuario, gbc_lbl_TipoUsuario);
		
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
		
		JLabel lbl_link = new JLabel("Link: ");
		GridBagConstraints gbc_lbl_link = new GridBagConstraints();
		gbc_lbl_link.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_link.gridx = 0;
		gbc_lbl_link.gridy = 5;
		lbl_link.setVisible(false);
		getContentPane().add(lbl_link, gbc_lbl_link);
		
		JLabel lbl_Institucion = new JLabel("Instituciones: ");
		GridBagConstraints gbc_lbl_Institucion= new GridBagConstraints();
		gbc_lbl_Institucion.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Institucion.gridx = 0;
		gbc_lbl_Institucion.gridy = 5;
		lbl_Institucion.setVisible(false);
		getContentPane().add(lbl_Institucion, gbc_lbl_Institucion);
		
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
		
		GridBagConstraints gbc_combo_instituciones = new GridBagConstraints();
		gbc_combo_instituciones.gridx = 1;
		gbc_combo_instituciones.gridy = 5;
		gbc_combo_instituciones.gridwidth = 2;
		gbc_combo_instituciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_combo_instituciones.insets = new Insets(0, 0, 5, 5);
		getContentPane().add(comboInstituciones, gbc_combo_instituciones);

		Button_Organizador.addActionListener(e -> {
			TextField_Link.setVisible(true);  
		    lbl_link.setVisible(true);          
		    
		    comboInstituciones.setVisible(false);
		    lbl_Institucion.setVisible(false);
		    
		    revalidate();                 
		    repaint();   
		});
		
		Button_Asistente.addActionListener(e -> {
			TextField_Link.setVisible(false);  
		    lbl_link.setVisible(false);      
		    
		    lbl_Institucion.setVisible(true);
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
