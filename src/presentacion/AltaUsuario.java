package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import logica.Fabrica;
import logica.IControladorInstitucion;
import logica.IControladorUsuarios;



public class AltaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField_Nick;
	private JTextField textField_Nombre;
	private JTextField textField_Correo;
	public AltaUsuario() {
		super("Alta de Usuario", true, true, true, true);
		setSize(350, 250);       
		setLocation(50, 50);
		
		Fabrica fabrica=Fabrica.getInstancia();
		IControladorUsuarios IContUsuario=fabrica.getControladorUsuarios();
		IControladorInstitucion IContInstituciones= fabrica.getControladorInstitucion();
		
		//-----------Ventanas----------
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0,0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0,0.0,1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0,0.0,0.0,0.0,0.0,1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lbl_nick = new JLabel("Nickname:");
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
		
		JLabel lbl_Descripcion = new JLabel("Descripción General: ");
		GridBagConstraints gbc_lbl_Descripcion = new GridBagConstraints();
		gbc_lbl_Descripcion.insets = new Insets(5, 0, 5, 5);
		gbc_lbl_Descripcion.gridx = 0;
		gbc_lbl_Descripcion.gridy = 6;
		lbl_Descripcion.setVisible(false);
		getContentPane().add(lbl_Descripcion, gbc_lbl_Descripcion);
		
		JLabel lbl_Apellido = new JLabel("Apellido: ");
		GridBagConstraints gbc_lbl_Apellido = new GridBagConstraints();
		gbc_lbl_Apellido.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Apellido.gridx = 0;
		gbc_lbl_Apellido.gridy = 7;
		lbl_Apellido.setVisible(false);
		getContentPane().add(lbl_Apellido, gbc_lbl_Apellido);
		
		JLabel lbl_FechaNacimiento = new JLabel("Fecha De Nacimiento: ");
		GridBagConstraints gbc_lbl_FechaNacimiento = new GridBagConstraints();
		gbc_lbl_FechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_FechaNacimiento.gridx = 0;
		gbc_lbl_FechaNacimiento.gridy = 8;
		lbl_FechaNacimiento.setVisible(false);
		getContentPane().add(lbl_FechaNacimiento, gbc_lbl_FechaNacimiento);
		
		JLabel lbl_Institucion = new JLabel("Instituciones: ");
		GridBagConstraints gbc_lbl_Institucion= new GridBagConstraints();
		gbc_lbl_Institucion.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Institucion.gridx = 0;
		gbc_lbl_Institucion.gridy = 5;
		lbl_Institucion.setVisible(false);
		getContentPane().add(lbl_Institucion, gbc_lbl_Institucion);
		
		JTextField textField_Link = new JTextField();
		textField_Link.setColumns(10);
		textField_Link.setVisible(false);  

		GridBagConstraints gbc_textField_Link = new GridBagConstraints();
		gbc_textField_Link.gridx = 1;
		gbc_textField_Link.gridy = 5;
		gbc_textField_Link.gridwidth = 2;
		gbc_textField_Link.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(textField_Link, gbc_textField_Link);
		
		JTextField textField_Descripcion = new JTextField();
		textField_Descripcion.setColumns(10);
		textField_Descripcion.setVisible(false); 
		
		GridBagConstraints gbc_textField_Descripcion = new GridBagConstraints();
		gbc_textField_Descripcion.gridx = 1;
		gbc_textField_Descripcion.gridy = 6;
		gbc_textField_Descripcion.gridwidth = 2;
		gbc_textField_Descripcion.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(textField_Descripcion, gbc_textField_Descripcion);
		
		JTextField textField_Apellido = new JTextField();
		textField_Apellido.setColumns(10);
		textField_Apellido.setVisible(false); 
		
		GridBagConstraints gbc_textField_Apellido = new GridBagConstraints();
		gbc_textField_Apellido.gridx = 1;
		gbc_textField_Apellido.gridy = 7;
		gbc_textField_Apellido.gridwidth = 2;
		gbc_textField_Apellido.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(textField_Apellido, gbc_textField_Apellido);
		
		SpinnerDateModel dateModel = new SpinnerDateModel();
		JSpinner Spinner_FechaNacimiento = new JSpinner(dateModel);
		Spinner_FechaNacimiento.setEditor(new JSpinner.DateEditor(Spinner_FechaNacimiento, "yyyy-MM-dd"));
		Spinner_FechaNacimiento.setVisible(false);

		GridBagConstraints gbc_textField_FechaNacimiento = new GridBagConstraints();
		gbc_textField_FechaNacimiento.gridx = 1;
		gbc_textField_FechaNacimiento.gridy = 8;
		gbc_textField_FechaNacimiento.gridwidth = 2;
		gbc_textField_FechaNacimiento.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(Spinner_FechaNacimiento, gbc_textField_FechaNacimiento);
        
		JComboBox<String> comboInstituciones = new JComboBox<>();
		comboInstituciones.addItem("Ninguno"); // Siempre lo tiene 
		
		Set<String> instituciones = IContInstituciones.nombresInstituciones();
		    if (instituciones != null) {
		        for (String inst : instituciones) {
		            comboInstituciones.addItem(inst);
		        }
		    }
		comboInstituciones.setVisible(false); 
		
		GridBagConstraints gbc_combo_instituciones = new GridBagConstraints();
		gbc_combo_instituciones.gridx = 1;
		gbc_combo_instituciones.gridy = 5;
		gbc_combo_instituciones.gridwidth = 2;
		gbc_combo_instituciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_combo_instituciones.insets = new Insets(0, 0, 5, 5);
		getContentPane().add(comboInstituciones, gbc_combo_instituciones);

		Button_Organizador.addActionListener(e -> {
			textField_Link.setVisible(true);  
		    lbl_link.setVisible(true);       
		    
		    textField_Descripcion.setVisible(true);  
		    lbl_Descripcion.setVisible(true); 
		    
		    comboInstituciones.setVisible(false);
		    lbl_Institucion.setVisible(false);
		    
		    textField_Apellido.setVisible(false);
		    lbl_Apellido.setVisible(false);
		    
		    Spinner_FechaNacimiento.setVisible(false);
		    lbl_FechaNacimiento.setVisible(false);

		    
		    revalidate();                 
		    repaint();  
		});
		
		Button_Asistente.addActionListener(e -> {
			textField_Link.setVisible(false);  
		    lbl_link.setVisible(false);    
		    
		    textField_Descripcion.setVisible(false);  
		    lbl_Descripcion.setVisible(false); 
		    
		    lbl_Institucion.setVisible(true);
		    comboInstituciones.setVisible(true);
		    
		    textField_Apellido.setVisible(true);
		    lbl_Apellido.setVisible(true);
		    
		    Spinner_FechaNacimiento.setVisible(true);
		    lbl_FechaNacimiento.setVisible(true);
		    
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

		gbc_Button_Aceptar.gridy = 9;
		getContentPane().add(Button_Aceptar, gbc_Button_Aceptar);
		
		JButton Button_Cancelar = new JButton("Cancelar");
		
		GridBagConstraints gbc_Button_Cancelar = new GridBagConstraints();
		gbc_Button_Cancelar.insets = new Insets(0, 0, 0, 10);
		gbc_Button_Cancelar.gridx = 2;
		gbc_Button_Cancelar.gridy = 9;
		getContentPane().add(Button_Cancelar, gbc_Button_Cancelar);
		
		Button_Cancelar.addActionListener(e -> {
		    AltaUsuario.this.dispose();
		});
		
		//---------------Funcionalidades-------------
		
		Button_Aceptar.addActionListener(e -> {
		    String nick = textField_Nick.getText().trim();
		    String nombre = textField_Nombre.getText().trim();
		    String correo = textField_Correo.getText().trim();
		    boolean esOrganizador=Button_Organizador.isSelected();
		    boolean esAsistente=Button_Asistente.isSelected();
		    
		    if (!esOrganizador && !esAsistente) {
		        JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un tipo de usuario (Organizador o Asistente)", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    if (esOrganizador) {
		        String descripcion = textField_Descripcion.getText().trim();
		        String Link = textField_Link.getText();
		        try {
		        	IContUsuario.altaOrganizador(nick,nombre,correo,descripcion,Link);
		        	
		        	//Si todo salió bien, aviso y limpio campos
		            JOptionPane.showMessageDialog(this,"Organizador creado correctamente","Éxito",JOptionPane.INFORMATION_MESSAGE);
		            // limpia los campos si llega al final
				      textField_Nick.setText("");
				      textField_Nombre.setText("");
				      textField_Correo.setText("");
				      textField_Link.setText("");
				      textField_Descripcion.setText("");
				      textField_Apellido.setText("");
				      Spinner_FechaNacimiento.setValue(new Date());
				      Button_Organizador.setSelected(false);
				      Button_Asistente.setSelected(false);
		        } catch (Exception ex) {
		            // Si el controlador lanza error, lo muestro
		            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		        }
		        
		    }

		    if (esAsistente) {
		    	String apellido=textField_Apellido.getText();
		    	String institucion = (String) comboInstituciones.getSelectedItem();
		    	Date fechaSeleccionada = (Date) Spinner_FechaNacimiento.getValue();
		    	LocalDate fechaNacimiento = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		    	try {
		            IContUsuario.altaAsistente(nick, nombre, correo, apellido, fechaNacimiento, institucion);
		            
		            //Si todo salió bien, aviso y limpio campos
		            JOptionPane.showMessageDialog(this,"Asistente creado correctamente","Éxito",JOptionPane.INFORMATION_MESSAGE);
		         // limpia los campos si llega al final
				      textField_Nick.setText("");
				      textField_Nombre.setText("");
				      textField_Correo.setText("");
				      textField_Link.setText("");
				      textField_Descripcion.setText("");
				      textField_Apellido.setText("");
				      Spinner_FechaNacimiento.setValue(new Date());
				      Button_Organizador.setSelected(false);
				      Button_Asistente.setSelected(false);
		        } catch (Exception ex) {
		            // Si el controlador lanza error, lo muestro
		            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		        }
		    
		    }
		    
		});

	} 
}
