package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorUsuarios;

public class AltaEdicionEvento extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField_Nombre;
	private JTextField textField_Sigla;
	private JTextField textField_Ciudad;
	private JTextField textField_Pais;
	private Fabrica fabrica= Fabrica.getInstancia();
	private IControladorEventos IContEvento= fabrica.getControladorEvento();
	private IControladorUsuarios IContUsuario= fabrica.getControladorUsuarios();
	public AltaEdicionEvento() {
	    super("Alta Edicion", true, true, true, true); 
	    setSize(400, 300);       
	    setLocation(50, 50);   

	    GridBagLayout gridBagLayout = new GridBagLayout();
	    gridBagLayout.columnWidths = new int[]{0, 0, 0 ,0,0};
	    gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
	    gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0,0.0};
	    gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	    getContentPane().setLayout(gridBagLayout);

	    JLabel lbl_Eventos = new JLabel("Eventos:");
	    GridBagConstraints gbc_lbl_Eventos = new GridBagConstraints();
	    gbc_lbl_Eventos.anchor = GridBagConstraints.EAST;
	    gbc_lbl_Eventos.insets = new Insets(5, 5, 5, 5);
	    gbc_lbl_Eventos.gridx = 0;
	    gbc_lbl_Eventos.gridy = 0;
	    getContentPane().add(lbl_Eventos, gbc_lbl_Eventos);

	    JComboBox<String> comboEventos = new JComboBox<>();
		
		Set<String> EventosDisponibles =IContEvento.nombresEventos();
		 if (EventosDisponibles != null) {
		        for (String evento : EventosDisponibles) {
		            comboEventos.addItem(evento);
		        }
		    }
		 
	    GridBagConstraints gbc_comboEventos = new GridBagConstraints();
	    gbc_comboEventos.insets = new Insets(5, 5 ,5, 3);
	    gbc_comboEventos.fill = GridBagConstraints.HORIZONTAL;
	    gbc_comboEventos.gridx = 1;
	    gbc_comboEventos.gridy = 0;
	    getContentPane().add(comboEventos, gbc_comboEventos);
	    
	   
	    JLabel lbl_Organizadores = new JLabel("Organizadores:");
	    GridBagConstraints gbc_lbl_Organizadores = new GridBagConstraints();
	    gbc_lbl_Organizadores.anchor = GridBagConstraints.CENTER;
	    gbc_lbl_Organizadores.insets = new Insets(5, 3, 5, 5);
	    gbc_lbl_Organizadores.gridx = 2;
	    gbc_lbl_Organizadores.gridy = 0;
	    getContentPane().add(lbl_Organizadores, gbc_lbl_Organizadores);

	    JComboBox<String> comboOrganizadores = new JComboBox<>();
		
		Set<String> OrganizadoresDisponibles =IContUsuario.nombresOrganizadores();
		 if (OrganizadoresDisponibles != null) {
		        for (String organizador : OrganizadoresDisponibles) {
		            comboOrganizadores.addItem(organizador);
		        }
		 }
		 
	    GridBagConstraints gbc_comboOrganizadores = new GridBagConstraints();
	    gbc_comboOrganizadores.insets = new Insets(5, 5, 5, 5);
	    gbc_comboOrganizadores.fill = GridBagConstraints.HORIZONTAL;
	    gbc_comboOrganizadores.gridx = 3;
	    gbc_comboOrganizadores.gridy = 0;
	    getContentPane().add(comboOrganizadores, gbc_comboOrganizadores);

	    JLabel lbl_Nombre = new JLabel("Nombre:");
	    GridBagConstraints gbc_lbl_Nombre = new GridBagConstraints();
	    gbc_lbl_Nombre.anchor = GridBagConstraints.EAST;
	    gbc_lbl_Nombre.insets = new Insets(5, 5, 5, 5);
	    gbc_lbl_Nombre.gridx = 0; 
	    gbc_lbl_Nombre.gridy = 2;
	    getContentPane().add(lbl_Nombre, gbc_lbl_Nombre);

	    textField_Nombre = new JTextField();
	    GridBagConstraints gbc_textField_Nombre = new GridBagConstraints();
	    gbc_textField_Nombre.insets = new Insets(5, 5, 5, 5);
	    gbc_textField_Nombre.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textField_Nombre.gridx = 1;
	    gbc_textField_Nombre.gridy = 2;
	    gbc_textField_Nombre.gridwidth = 2;
	    getContentPane().add(textField_Nombre, gbc_textField_Nombre);
	    textField_Nombre.setColumns(10);

	    JLabel lbl_Sigla = new JLabel("Sigla:");
	    GridBagConstraints gbc_lbl_Sigla = new GridBagConstraints();
	    gbc_lbl_Sigla.anchor = GridBagConstraints.EAST;
	    gbc_lbl_Sigla.insets = new Insets(5, 5, 5, 5);
	    gbc_lbl_Sigla.gridx = 0;
	    gbc_lbl_Sigla.gridy = 3;
	    getContentPane().add(lbl_Sigla, gbc_lbl_Sigla);

	    textField_Sigla = new JTextField();
	    GridBagConstraints gbc_textField_Sigla = new GridBagConstraints();
	    gbc_textField_Sigla.insets = new Insets(5, 5, 5, 5);
	    gbc_textField_Sigla.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textField_Sigla.gridx = 1;
	    gbc_textField_Sigla.gridy = 3;
	    gbc_textField_Sigla.gridwidth = 2;
	    getContentPane().add(textField_Sigla, gbc_textField_Sigla);
	    textField_Sigla.setColumns(10);

	    JLabel lbl_Ciudad = new JLabel("Ciudad:");
	    GridBagConstraints gbc_lbl_Ciudad = new GridBagConstraints();
	    gbc_lbl_Ciudad.anchor = GridBagConstraints.EAST;
	    gbc_lbl_Ciudad.insets = new Insets(5, 5, 5, 5);
	    gbc_lbl_Ciudad.gridx = 0;
	    gbc_lbl_Ciudad.gridy = 4;
	    getContentPane().add(lbl_Ciudad, gbc_lbl_Ciudad);

	    textField_Ciudad = new JTextField();
	    GridBagConstraints gbc_textField_Ciudad = new GridBagConstraints();
	    gbc_textField_Ciudad.insets = new Insets(5, 5, 5, 5);
	    gbc_textField_Ciudad.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textField_Ciudad.gridx = 1;
	    gbc_textField_Ciudad.gridy = 4;
	    gbc_textField_Ciudad.gridwidth = 2;
	    getContentPane().add(textField_Ciudad, gbc_textField_Ciudad);
	    textField_Ciudad.setColumns(10);

	    JLabel lbl_Pais = new JLabel("Pais:");
	    GridBagConstraints gbc_lbl_Pais = new GridBagConstraints();
	    gbc_lbl_Pais.anchor = GridBagConstraints.EAST;
	    gbc_lbl_Pais.insets = new Insets(5, 5, 5, 5);
	    gbc_lbl_Pais.gridx = 0;
	    gbc_lbl_Pais.gridy = 5;
	    getContentPane().add(lbl_Pais, gbc_lbl_Pais);

	    textField_Pais = new JTextField();
	    GridBagConstraints gbc_textField_Pais = new GridBagConstraints();
	    gbc_textField_Pais.insets = new Insets(5, 5, 5, 5);
	    gbc_textField_Pais.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textField_Pais.gridx = 1;
	    gbc_textField_Pais.gridy = 5;
	    gbc_textField_Pais.gridwidth = 2;
	    getContentPane().add(textField_Pais, gbc_textField_Pais); 
	    textField_Pais.setColumns(10);

	    JLabel lbl_FechaInicio = new JLabel("Fecha de Inicio:");
	    GridBagConstraints gbc_lbl_FechaInicio = new GridBagConstraints();
	    gbc_lbl_FechaInicio.anchor = GridBagConstraints.EAST;
	    gbc_lbl_FechaInicio.insets = new Insets(5, 5, 5, 5);
	    gbc_lbl_FechaInicio.gridx = 0;
	    gbc_lbl_FechaInicio.gridy = 6;
	    getContentPane().add(lbl_FechaInicio, gbc_lbl_FechaInicio);

	    SpinnerDateModel dateModel = new SpinnerDateModel();
		JSpinner Spinner_FechaInicio = new JSpinner(dateModel);
		Spinner_FechaInicio.setEditor(new JSpinner.DateEditor(Spinner_FechaInicio, "yyyy-MM-dd"));

		GridBagConstraints gbc_spinner_FechaInicio = new GridBagConstraints();
		gbc_spinner_FechaInicio.gridx = 1;
		gbc_spinner_FechaInicio.gridy = 6;
		gbc_spinner_FechaInicio.gridwidth = 1;
		gbc_spinner_FechaInicio.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(Spinner_FechaInicio, gbc_spinner_FechaInicio);

		
	    JLabel lbl_FechaFin = new JLabel("Fecha de Fin:");
	    GridBagConstraints gbc_lbl_FechaFin = new GridBagConstraints();
	    gbc_lbl_FechaFin.anchor = GridBagConstraints.EAST;
	    gbc_lbl_FechaFin.insets = new Insets(5, 3, 5, 5);
	    gbc_lbl_FechaFin.gridx = 2;
	    gbc_lbl_FechaFin.gridy = 6;
	    getContentPane().add(lbl_FechaFin, gbc_lbl_FechaFin);
	    
	    SpinnerDateModel dateModel2 = new SpinnerDateModel(); 
	    JSpinner Spinner_FechaFin = new JSpinner(dateModel2);
		Spinner_FechaFin.setEditor(new JSpinner.DateEditor(Spinner_FechaFin, "yyyy-MM-dd"));
		
	    GridBagConstraints gbc_Spinner_FechaFin = new GridBagConstraints();
		gbc_Spinner_FechaFin.gridx = 3;
		gbc_Spinner_FechaFin.gridy = 6;
		gbc_Spinner_FechaFin.gridwidth = 1;
		gbc_Spinner_FechaFin.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(Spinner_FechaFin, gbc_Spinner_FechaFin);

	    JLabel lbl_FechaAlta = new JLabel("Fecha de Alta:");
	    GridBagConstraints gbc_lbl_FechaAlta = new GridBagConstraints();
	    gbc_lbl_FechaAlta.anchor = GridBagConstraints.EAST;
	    gbc_lbl_FechaAlta.insets = new Insets(5, 5, 5, 5);
	    gbc_lbl_FechaAlta.gridx = 0;
	    gbc_lbl_FechaAlta.gridy = 7;
	    getContentPane().add(lbl_FechaAlta, gbc_lbl_FechaAlta);

	    SpinnerDateModel dateModel3 = new SpinnerDateModel();
	    JSpinner Spinner_FechaAlta = new JSpinner(dateModel3);
		Spinner_FechaAlta.setEditor(new JSpinner.DateEditor(Spinner_FechaAlta, "yyyy-MM-dd"));
		
		GridBagConstraints gbc_Spinner_FechaAlta = new GridBagConstraints();
		gbc_Spinner_FechaAlta.gridx = 1;
		gbc_Spinner_FechaAlta.gridy = 7;
		gbc_Spinner_FechaAlta.gridwidth = 1;
		gbc_Spinner_FechaAlta.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(Spinner_FechaAlta, gbc_Spinner_FechaAlta);

	    JButton btnAceptar = new JButton("Aceptar");
	    GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
	    gbc_btnAceptar.insets = new Insets(10, 5, 5, 5);
	    gbc_btnAceptar.gridx = 2;
	    gbc_btnAceptar.gridy = 8;
	    getContentPane().add(btnAceptar, gbc_btnAceptar);

	    JButton btnCancelar = new JButton("Cancelar");
	    GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
	    gbc_btnCancelar.insets = new Insets(10, 5, 5, 5);
	    gbc_btnCancelar.gridx = 3;
	    gbc_btnCancelar.gridy = 8;
	    getContentPane().add(btnCancelar, gbc_btnCancelar);
	    
	    btnCancelar.addActionListener(e -> {
		    AltaEdicionEvento.this.dispose();
		});
	    
	    btnAceptar.addActionListener(e -> {
	    	if (comboEventos.getSelectedItem() == null || comboOrganizadores.getSelectedItem() == null) {
			     JOptionPane.showMessageDialog(null, "Debe seleccionar un evento y un organizador.", "Error", JOptionPane.ERROR_MESSAGE);
			     return;
			}
	    	  
	    	String nombreEvento = comboEventos.getSelectedItem().toString().trim();
	    	String nombre = textField_Nombre.getText().trim();
		    String sigla = textField_Sigla.getText().trim();
		    String ciudad = textField_Ciudad.getText().trim();
		    String pais = textField_Pais.getText().trim();
		    Date fechaAlta = (Date) Spinner_FechaAlta.getValue();
		    Date fechaInicio = (Date) Spinner_FechaInicio.getValue();
		    Date fechaFin = (Date) Spinner_FechaFin.getValue();
		    
		  
		     							
		    try {
		        IContEvento.altaEdicionEvento(nombreEvento, nombre, sigla, fechaInicio, fechaFin, fechaAlta, ciudad, pais);

		        JOptionPane.showMessageDialog(this, "Edición creada correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

		        // Limpiar campos de texto
		        textField_Nombre.setText("");
		        textField_Sigla.setText("");
		        textField_Ciudad.setText("");
		        textField_Pais.setText("");

		        // Resetear fechas a hoy
		        Spinner_FechaAlta.setValue(new Date());
		        Spinner_FechaInicio.setValue(new Date());
		        Spinner_FechaFin.setValue(new Date());

		        // Deseleccionar combos
		        comboEventos.setSelectedIndex(-1);
		        comboOrganizadores.setSelectedIndex(-1);

		    } catch (Exception e1) {
		        JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		    }
	    });
	}
} 
