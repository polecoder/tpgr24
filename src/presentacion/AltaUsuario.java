package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;

import excepciones.FormatoContrasenaInvalido;
import logica.Fabrica;
import logica.IControladorInstitucion;
import logica.IControladorUsuarios;
import javax.swing.JPasswordField;

public class AltaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField_Nick;
	private JTextField textField_Nombre;
	private JTextField textField_Correo;
	private JPasswordField contrasenaTxt;
	private JPasswordField confirmarContrasenaTxt;
	public AltaUsuario() {
		super("Alta de Usuario", true, true, true, true); 
		setSize(400, 400);       
		setLocation(50, 50);
		
		Fabrica fabrica=Fabrica.getInstancia();
		IControladorUsuarios IContUsuario=fabrica.getControladorUsuarios();
		IControladorInstitucion IContInstituciones= fabrica.getControladorInstitucion();
		
		//-----------Ventanas----------
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0,0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0,0.0,1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0,0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lbl_nick = new JLabel("Nickname:");
		GridBagConstraints gbc_lbl_nick = new GridBagConstraints();
		gbc_lbl_nick.anchor = GridBagConstraints.CENTER;
		gbc_lbl_nick.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_nick.gridx = 0;
		gbc_lbl_nick.gridy = 0;
		getContentPane().add(lbl_nick, gbc_lbl_nick);
		
		textField_Nick = new JTextField();
		GridBagConstraints gbc_textField_Nick = new GridBagConstraints();
		gbc_textField_Nick.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Nick.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Nick.gridx = 1;
		gbc_textField_Nick.gridy = 0;
		gbc_textField_Nick.gridwidth = 2;  
		getContentPane().add(textField_Nick, gbc_textField_Nick);
		textField_Nick.setColumns(10);
		
		JLabel lbl_contrasena = new JLabel("Contraseña:");
		GridBagConstraints gbc_lbl_contrasena = new GridBagConstraints();
		gbc_lbl_contrasena.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_contrasena.gridx = 0;
		gbc_lbl_contrasena.gridy = 1;
		getContentPane().add(lbl_contrasena, gbc_lbl_contrasena);
		
		contrasenaTxt = new JPasswordField();
		GridBagConstraints gbc_contrasenaTxt = new GridBagConstraints();
		gbc_contrasenaTxt.gridwidth = 2;
		gbc_contrasenaTxt.insets = new Insets(0, 0, 5, 5);
		gbc_contrasenaTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_contrasenaTxt.gridx = 1;
		gbc_contrasenaTxt.gridy = 1;
		getContentPane().add(contrasenaTxt, gbc_contrasenaTxt);
		
		JLabel lbl_confirmarContrasena = new JLabel("Confirmar Contraseña:");
		GridBagConstraints gbc_lbl_confirmarContrasena = new GridBagConstraints();
		gbc_lbl_confirmarContrasena.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_confirmarContrasena.gridx = 0;
		gbc_lbl_confirmarContrasena.gridy = 2;
		getContentPane().add(lbl_confirmarContrasena, gbc_lbl_confirmarContrasena);
		
		confirmarContrasenaTxt = new JPasswordField();
		GridBagConstraints gbc_confirmarContrasenaTxt = new GridBagConstraints();
		gbc_confirmarContrasenaTxt.gridwidth = 2;
		gbc_confirmarContrasenaTxt.insets = new Insets(0, 0, 5, 5);
		gbc_confirmarContrasenaTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_confirmarContrasenaTxt.gridx = 1;
		gbc_confirmarContrasenaTxt.gridy = 2;
		getContentPane().add(confirmarContrasenaTxt, gbc_confirmarContrasenaTxt);
		
		JLabel lbl_nombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lbl_nombre = new GridBagConstraints();
		gbc_lbl_nombre.anchor = GridBagConstraints.CENTER;
		gbc_lbl_nombre.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_nombre.gridx = 0;
		gbc_lbl_nombre.gridy = 3;
		getContentPane().add(lbl_nombre, gbc_lbl_nombre);
		
		textField_Nombre = new JTextField();
		GridBagConstraints gbc_textField_Nombre = new GridBagConstraints();
		gbc_textField_Nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Nombre.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Nombre.gridx = 1;
		gbc_textField_Nombre.gridy = 3;
		gbc_textField_Nombre.gridwidth = 2; 
		getContentPane().add(textField_Nombre, gbc_textField_Nombre);
		textField_Nombre.setColumns(10);
		

		JLabel lbl_Correo = new JLabel("Correo:");
		GridBagConstraints gbc_lbl_Correo = new GridBagConstraints();
		gbc_lbl_Correo.anchor = GridBagConstraints.CENTER;
		gbc_lbl_Correo.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Correo.gridx = 0;
		gbc_lbl_Correo.gridy = 4;
		getContentPane().add(lbl_Correo, gbc_lbl_Correo);
		
		textField_Correo = new JTextField();
		GridBagConstraints gbc_textField_Correo = new GridBagConstraints();
		gbc_textField_Correo.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Correo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Correo.gridx = 1;
		gbc_textField_Correo.gridy = 4;
		gbc_textField_Correo.gridwidth = 2; 
		getContentPane().add(textField_Correo, gbc_textField_Correo);
		textField_Correo.setColumns(10);
		
		JLabel lbl_TipoUsuario = new JLabel("Tipo Usuario: ");
		GridBagConstraints gbc_lbl_TipoUsuario = new GridBagConstraints();
		gbc_lbl_TipoUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_TipoUsuario.gridx = 0;
		gbc_lbl_TipoUsuario.gridy = 5;
		getContentPane().add(lbl_TipoUsuario, gbc_lbl_TipoUsuario);

		
		JRadioButton Button_Organizador = new JRadioButton("Organizador");
		GridBagConstraints gbc_Button_Organizador = new GridBagConstraints();
		gbc_Button_Organizador.insets = new Insets(0, 0, 5, 5);
		gbc_Button_Organizador.gridx = 1;
		gbc_Button_Organizador.gridy = 5;
		getContentPane().add(Button_Organizador, gbc_Button_Organizador);
		
		JRadioButton Button_Asistente = new JRadioButton("Asistente");
		GridBagConstraints gbc_Button_Asistente = new GridBagConstraints();
		gbc_Button_Asistente.insets = new Insets(0, 0, 5, 5);
		gbc_Button_Asistente.gridx = 2;
		gbc_Button_Asistente.gridy = 5;
		getContentPane().add(Button_Asistente, gbc_Button_Asistente);
		
		JLabel lbl_link = new JLabel("Link: ");
		GridBagConstraints gbc_lbl_link = new GridBagConstraints();
		gbc_lbl_link.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_link.gridx = 0;
		gbc_lbl_link.gridy = 6;
		lbl_link.setVisible(false);
		getContentPane().add(lbl_link, gbc_lbl_link);
		
		JLabel lbl_Descripcion = new JLabel("Descripción General: ");
		GridBagConstraints gbc_lbl_Descripcion = new GridBagConstraints();
		gbc_lbl_Descripcion.insets = new Insets(5, 0, 5, 5);
		gbc_lbl_Descripcion.gridx = 0;
		gbc_lbl_Descripcion.gridy = 7;
		lbl_Descripcion.setVisible(false);
		getContentPane().add(lbl_Descripcion, gbc_lbl_Descripcion);
		
		JLabel lbl_Apellido = new JLabel("Apellido: ");
		GridBagConstraints gbc_lbl_Apellido = new GridBagConstraints();
		gbc_lbl_Apellido.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Apellido.gridx = 0;
		gbc_lbl_Apellido.gridy = 8;
		lbl_Apellido.setVisible(false);
		getContentPane().add(lbl_Apellido, gbc_lbl_Apellido);
		
		JLabel lbl_FechaNacimiento = new JLabel("Fecha De Nacimiento: ");
		GridBagConstraints gbc_lbl_FechaNacimiento = new GridBagConstraints();
		gbc_lbl_FechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_FechaNacimiento.gridx = 0;
		gbc_lbl_FechaNacimiento.gridy = 9;
		lbl_FechaNacimiento.setVisible(false);
		getContentPane().add(lbl_FechaNacimiento, gbc_lbl_FechaNacimiento);
		
		JLabel lbl_Institucion = new JLabel("Instituciones: ");
		GridBagConstraints gbc_lbl_Institucion= new GridBagConstraints();
		gbc_lbl_Institucion.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Institucion.gridx = 0;
		gbc_lbl_Institucion.gridy = 6;
		lbl_Institucion.setVisible(false);
		getContentPane().add(lbl_Institucion, gbc_lbl_Institucion);
		
		JTextField textField_Link = new JTextField();
		textField_Link.setColumns(10);
		textField_Link.setVisible(false);  

		GridBagConstraints gbc_textField_Link = new GridBagConstraints();
		gbc_textField_Link.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Link.gridx = 1;
		gbc_textField_Link.gridy = 6;
		gbc_textField_Link.gridwidth = 2;
		gbc_textField_Link.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(textField_Link, gbc_textField_Link);
		
		
		JScrollPane descripcionScrollPane = new JScrollPane();
		descripcionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_descripcionScrollPane = new GridBagConstraints();
		gbc_descripcionScrollPane.gridwidth = 2;
		gbc_descripcionScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_descripcionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_descripcionScrollPane.gridx = 1;
		gbc_descripcionScrollPane.gridy = 7;
		getContentPane().add(descripcionScrollPane, gbc_descripcionScrollPane);
		JTextArea textField_Descripcion = new JTextArea();
		textField_Descripcion.setLineWrap(true);
		textField_Descripcion.setRows(2);
		descripcionScrollPane.setViewportView(textField_Descripcion);
		descripcionScrollPane.setVisible(false); 
		
		GridBagConstraints gbc_textField_Descripcion = new GridBagConstraints();
		gbc_textField_Descripcion.gridx = 1;
		gbc_textField_Descripcion.gridy = 6;
		gbc_textField_Descripcion.gridwidth = 2;
		gbc_textField_Descripcion.fill = GridBagConstraints.HORIZONTAL;
		//getContentPane().add(textField_Descripcion, gbc_textField_Descripcion);
		
		JTextField textField_Apellido = new JTextField();
		textField_Apellido.setColumns(10);
		textField_Apellido.setVisible(false); 
		
		GridBagConstraints gbc_textField_Apellido = new GridBagConstraints();
		gbc_textField_Apellido.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Apellido.gridx = 1;
		gbc_textField_Apellido.gridy = 8;
		gbc_textField_Apellido.gridwidth = 2;
		gbc_textField_Apellido.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(textField_Apellido, gbc_textField_Apellido);
		
		SpinnerDateModel dateModel = new SpinnerDateModel();
		JSpinner Spinner_FechaNacimiento = new JSpinner(dateModel);
		Spinner_FechaNacimiento.setEditor(new JSpinner.DateEditor(Spinner_FechaNacimiento, "yyyy-MM-dd"));
		Spinner_FechaNacimiento.setVisible(false);

		GridBagConstraints gbc_textField_FechaNacimiento = new GridBagConstraints();
		gbc_textField_FechaNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_textField_FechaNacimiento.gridx = 1;
		gbc_textField_FechaNacimiento.gridy = 9;
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
		gbc_combo_instituciones.gridy = 6;
		gbc_combo_instituciones.gridwidth = 2; 
		gbc_combo_instituciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_combo_instituciones.insets = new Insets(0, 0, 5, 5);
		getContentPane().add(comboInstituciones, gbc_combo_instituciones);

		Button_Organizador.addActionListener(e -> {
			textField_Link.setVisible(true);  
		    lbl_link.setVisible(true);       
		    
		    descripcionScrollPane.setVisible(true);  
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
		    
		    descripcionScrollPane.setVisible(false);  
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
		gbc_Button_Aceptar.insets = new Insets(0, 0, 5, 10);
		gbc_Button_Aceptar.gridx = 1;

		gbc_Button_Aceptar.gridy = 10;
		getContentPane().add(Button_Aceptar, gbc_Button_Aceptar);
		
		JButton Button_Cancelar = new JButton("Cancelar");
		
		GridBagConstraints gbc_Button_Cancelar = new GridBagConstraints();
		gbc_Button_Cancelar.insets = new Insets(0, 0, 5, 10);
		gbc_Button_Cancelar.gridx = 2;
		gbc_Button_Cancelar.gridy = 10;
		getContentPane().add(Button_Cancelar, gbc_Button_Cancelar);
		
		Button_Cancelar.addActionListener(e -> {
		    AltaUsuario.this.dispose();
		});
		
		//---------------Funcionalidades-------------
		
		Button_Aceptar.addActionListener(e -> {
			String nick = textField_Nick.getText().trim();
		    String nombre = textField_Nombre.getText().trim();
		    String contrasena = new String(contrasenaTxt.getPassword()).trim();
		    String contrasenaConfirmada = new String(confirmarContrasenaTxt.getPassword()).trim();
		    String correo = textField_Correo.getText().trim();
		    boolean esOrganizador=Button_Organizador.isSelected();
		    boolean esAsistente=Button_Asistente.isSelected();
		    
		    if (!esOrganizador && !esAsistente) {
		        JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un tipo de usuario (Organizador o Asistente)", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
		    }
		    
		    if (!contrasena.equals(contrasenaConfirmada)) {
		    	JOptionPane.showMessageDialog(this, "Las contraseñas proporcionadas no son iguales.", "Error", JOptionPane.ERROR_MESSAGE);
		    	contrasenaTxt.setText("");
			    confirmarContrasenaTxt.setText("");
		    	return;
		    }
		    
		    if (esOrganizador) {
		        String descripcion = textField_Descripcion.getText().trim();
		        String Link = textField_Link.getText();
		        try {
		        	IContUsuario.altaOrganizador(nick,contrasena, nombre,correo,descripcion,Link);
		        	
		        	//Si todo salió bien, aviso y limpio campos
		            JOptionPane.showMessageDialog(this,"Organizador creado correctamente","Éxito",JOptionPane.INFORMATION_MESSAGE);
				      textField_Nombre.setText("");
				      textField_Correo.setText("");
				      textField_Link.setText("");
				      textField_Descripcion.setText("");
				      textField_Apellido.setText("");
				      contrasenaTxt.setText("");
				      confirmarContrasenaTxt.setText("");
				      Spinner_FechaNacimiento.setValue(new Date());
				      Button_Organizador.setSelected(false);
				      Button_Asistente.setSelected(false);
		        } catch (FormatoContrasenaInvalido ex) {
		        	contrasenaTxt.setText("");
				    confirmarContrasenaTxt.setText("");
		            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		        }
		        
		    }

		    if (esAsistente) {
		    	String apellido=textField_Apellido.getText();
		    	String institucion = (String) comboInstituciones.getSelectedItem();
		    	Date fechaNacimiento = (Date) Spinner_FechaNacimiento.getValue();
		    	try {
		            IContUsuario.altaAsistente(nick, contrasena, nombre, correo, apellido, fechaNacimiento, institucion);
		            
		            //Si todo salió bien, aviso y limpio campos
		            JOptionPane.showMessageDialog(this,"Asistente creado correctamente","Éxito",JOptionPane.INFORMATION_MESSAGE);
				      textField_Nombre.setText("");
				      textField_Correo.setText("");
				      textField_Link.setText("");
				      textField_Descripcion.setText("");
				      textField_Apellido.setText("");
				      contrasenaTxt.setText("");
				      confirmarContrasenaTxt.setText("");
				      Spinner_FechaNacimiento.setValue(new Date());
				      Button_Organizador.setSelected(false);
				      Button_Asistente.setSelected(false);
		        } catch (FormatoContrasenaInvalido ex) {
		        	contrasenaTxt.setText("");
				    confirmarContrasenaTxt.setText("");
		            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		        } catch (Exception ex) {
		            JOptionPane.showMessageDialog(this,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		        }
		    
		    }
		    
		});

	} 
}
