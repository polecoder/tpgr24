package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.PopupMenuEvent;

import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.TipoRegistroNoExiste;
import logica.ControladorEventos;
import logica.datatypes.DTTipoRegistro;

public class ConsultaTipoRegistro extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textField_Nombre;
	private JTextField textField_Descripcion;
	private JTextField textField_Costo;
	private JTextField textField_CuposMax;
	private JTextField textField_CuposDisp;
	private ControladorEventos controladorEventos = ControladorEventos.getInstance();
	
	private JComboBox<String> comboEvento, comboEdicion, comboTipoRegistro;
	
	public ConsultaTipoRegistro(String nombreParam, String nombreEventoParam, String nombreEdicionParam) {		
		super("Consulta Tipo registro", true, true, true, true);
		setSize(300, 247);       
		setLocation(50, 50);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0}; // 3 elementos
		gridBagLayout.columnWeights = new double[]{1.0, 1.0,0.0,1.0};
		gridBagLayout.rowWeights = new double[]{0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
		getContentPane().setLayout(gridBagLayout);
		
		//----------EVENTO----------
		JLabel lbl_Evento = new JLabel("Evento:");
		GridBagConstraints gbc_lbl_Evento= new GridBagConstraints();
		gbc_lbl_Evento.anchor = GridBagConstraints.EAST;
		gbc_lbl_Evento.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Evento.gridx = 0;
		gbc_lbl_Evento.gridy = 0;
		getContentPane().add(lbl_Evento, gbc_lbl_Evento);
		
		String[] defaultEvento = { "- Seleccione un Evento -" };
		comboEvento = new JComboBox<>(defaultEvento);
		Set<String> eventos = controladorEventos.nombresEventos(); // Inicializo los eventos al iniciar el Caso de Uso
		if (eventos != null) {
			for (String evento : eventos) { comboEvento.addItem(evento); }
		}
		GridBagConstraints gbc_comboEvento = new GridBagConstraints();
		gbc_comboEvento.gridx = 1;
		gbc_comboEvento.gridy = 0;
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
		gbc_lbl_Edicion.gridy = 1;
		getContentPane().add(lbl_Edicion, gbc_lbl_Edicion);
		
		String[] defaultEdicion = { "- Seleccione una edicion -" };
		comboEdicion = new JComboBox<>(defaultEdicion);
		Set<String> ediciones;
		try { 
			String selectedEvento = comboEvento.getItemAt(comboEvento.getSelectedIndex());
			if(selectedEvento != null && selectedEvento != "- Seleccione un Evento -") {
				ediciones = controladorEventos.nombresEdicionesEvento(selectedEvento);
			}else {
				ediciones = null;
			}
		} catch (EventoNoExiste ex) {
			ediciones = null;
		}
		if (ediciones != null){
	        for (String edicion : ediciones) { comboEdicion.addItem(edicion); }
		}
		
		GridBagConstraints gbc_comboEdicion = new GridBagConstraints();
		gbc_comboEdicion.gridwidth = 2;
		gbc_comboEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_comboEdicion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEdicion.gridx = 1;
		gbc_comboEdicion.gridy = 1;
		getContentPane().add(comboEdicion, gbc_comboEdicion);
		
		
		//----------EDICION----------
		
		//----------TIPOREGISTRO----------
		JLabel lbl_TipoRegistro = new JLabel("Tipo de Registro:");
		GridBagConstraints gbc_lbl_TipoRegistro = new GridBagConstraints();
		gbc_lbl_TipoRegistro.anchor = GridBagConstraints.EAST;
		gbc_lbl_TipoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_TipoRegistro.gridx = 0;
		gbc_lbl_TipoRegistro.gridy = 2;
		getContentPane().add(lbl_TipoRegistro, gbc_lbl_TipoRegistro);
		
		String[] defaultTR = { "- Seleccione un Tipo Registro -" };
		comboTipoRegistro = new JComboBox<>(defaultTR);
		String[] tiposRegistro;
		try { 
			String selectedEvento = comboEvento.getItemAt(comboEvento.getSelectedIndex());
			String selectedEdicion = comboEdicion.getItemAt(comboEdicion.getSelectedIndex());
			if(selectedEvento != null && selectedEvento != "- Seleccione un Evento -"
					&& selectedEdicion != null && selectedEdicion != "- Seleccione una edicion -") {
				tiposRegistro = controladorEventos.getTiposRegistro(comboEvento.getItemAt(comboEvento.getSelectedIndex()), comboEdicion.getItemAt(comboEdicion.getSelectedIndex()));				
			}else {
				tiposRegistro = null;
			}
		} catch (EventoNoExiste ex) { tiposRegistro = null;
		} catch (EdicionNoExiste ex) { tiposRegistro = null;
		}
		if (tiposRegistro != null) {
    		for (String nombre : tiposRegistro) { comboTipoRegistro.addItem(nombre); }
    	}
		GridBagConstraints gbc_comboTipoRegistro = new GridBagConstraints();
		gbc_comboTipoRegistro.gridwidth = 2;
		gbc_comboTipoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_comboTipoRegistro.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboTipoRegistro.gridx = 1;
		gbc_comboTipoRegistro.gridy = 2;
		getContentPane().add(comboTipoRegistro, gbc_comboTipoRegistro);
		
		
		//----------TIPOREGISTRO----------
		
		//--------------------DETALLES--------------------
		//----------NOMBRE----------
		JLabel lbl_Nombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lbl_Nombre = new GridBagConstraints();
		gbc_lbl_Nombre.anchor = GridBagConstraints.EAST;
		gbc_lbl_Nombre.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Nombre.gridx = 0;
		gbc_lbl_Nombre.gridy = 3;
		getContentPane().add(lbl_Nombre, gbc_lbl_Nombre);
		
		textField_Nombre = new JTextField();
		GridBagConstraints gbc_textField_Nombre = new GridBagConstraints();
		gbc_textField_Nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Nombre.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Nombre.gridx = 1;
		gbc_textField_Nombre.gridy = 3;
		gbc_textField_Nombre.gridwidth = 2;  
		getContentPane().add(textField_Nombre, gbc_textField_Nombre);
		textField_Nombre.setEnabled(false);
		textField_Nombre.setColumns(10);
		//----------NOMBRE----------
		
		//----------DESCRIPCION----------
		JLabel lbl_Descripcion = new JLabel("Descripción:");
		GridBagConstraints gbc_lbl_Descripcion = new GridBagConstraints();
		gbc_lbl_Descripcion.anchor = GridBagConstraints.EAST;
		gbc_lbl_Descripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Descripcion.gridx = 0;
		gbc_lbl_Descripcion.gridy = 4;
		getContentPane().add(lbl_Descripcion, gbc_lbl_Descripcion);
		
		textField_Descripcion = new JTextField();
		GridBagConstraints gbc_textField_Descripcion = new GridBagConstraints();
		gbc_textField_Descripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Descripcion.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Descripcion.gridx = 1;
		gbc_textField_Descripcion.gridy = 4;
		gbc_textField_Descripcion.gridwidth = 2; 
		getContentPane().add(textField_Descripcion, gbc_textField_Descripcion);
		textField_Descripcion.setEnabled(false);
		textField_Descripcion.setColumns(10);
		//----------DESCRIPCION----------
		
		//----------COSTO----------
		JLabel lbl_Costo = new JLabel("Costo:");
		GridBagConstraints gbc_lbl_Costo = new GridBagConstraints();
		gbc_lbl_Costo.anchor = GridBagConstraints.EAST;
		gbc_lbl_Costo.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_Costo.gridx = 0;
		gbc_lbl_Costo.gridy = 5;
		getContentPane().add(lbl_Costo, gbc_lbl_Costo);
		
		textField_Costo = new JTextField();
		GridBagConstraints gbc_textField_Costo = new GridBagConstraints();
		gbc_textField_Costo.insets = new Insets(0, 0, 5, 5);
		gbc_textField_Costo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Costo.gridx = 1;
		gbc_textField_Costo.gridy = 5;
		gbc_textField_Costo.gridwidth = 2; 
		getContentPane().add(textField_Costo, gbc_textField_Costo);
		textField_Costo.setEnabled(false);
		textField_Costo.setColumns(10);
		//----------COSTO----------
		
		//----------CUPOS MAX----------
		JLabel lbl_CuposMax = new JLabel("Cupos máximos:");
		GridBagConstraints gbc_lbl_CuposMax = new GridBagConstraints();
		gbc_lbl_CuposMax.anchor = GridBagConstraints.EAST;
		gbc_lbl_CuposMax.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_CuposMax.gridx = 0;
		gbc_lbl_CuposMax.gridy = 6;
		getContentPane().add(lbl_CuposMax, gbc_lbl_CuposMax);
		
		textField_CuposMax = new JTextField();
		textField_CuposMax.setColumns(10);
		GridBagConstraints gbc_textField_CuposMax = new GridBagConstraints();
		gbc_textField_CuposMax.gridwidth = 2;
		gbc_textField_CuposMax.insets = new Insets(0, 0, 5, 5);
		gbc_textField_CuposMax.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_CuposMax.gridx = 1;
		gbc_textField_CuposMax.gridy = 6;
		textField_CuposMax.setEnabled(false);
		getContentPane().add(textField_CuposMax, gbc_textField_CuposMax);
		//----------CUPOS MAX----------
		
		//----------DISPONIBLES----------
		JLabel lbl_CuposDisp = new JLabel("Cupos dispobibles:");
		GridBagConstraints gbc_lbl_CuposDisp = new GridBagConstraints();
		gbc_lbl_CuposDisp.anchor = GridBagConstraints.EAST;
		gbc_lbl_CuposDisp.insets = new Insets(0, 0, 5, 5);
		gbc_lbl_CuposDisp.gridx = 0;
		gbc_lbl_CuposDisp.gridy = 7;
		getContentPane().add(lbl_CuposDisp, gbc_lbl_CuposDisp);
		
		textField_CuposDisp = new JTextField();
		textField_CuposDisp.setColumns(10);
		GridBagConstraints gbc_textField_CuposDisp = new GridBagConstraints();
		gbc_textField_CuposDisp.gridwidth = 2;
		gbc_textField_CuposDisp.insets = new Insets(0, 0, 5, 5);
		gbc_textField_CuposDisp.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_CuposDisp.gridx = 1;
		gbc_textField_CuposDisp.gridy = 7;
		textField_CuposDisp.setEnabled(false);
		getContentPane().add(textField_CuposDisp, gbc_textField_CuposDisp);
		//----------DISPONIBLES----------
		//--------------------DETALLES--------------------
		
		// Agrego datos al inicio
    	try { actualizarDatos();
    	} catch (TipoRegistroNoExiste ex) {}
		
		
		//----------ACTIONLISTENERS----------
		// Actualizo opciones de Edicion y TipoRegistro al cambiar de Evento
		comboEvento.addActionListener(e -> { // Cuando se cierra el combo
        	// Limpiar opciones viejas
        	comboEdicion.removeAllItems();
        	comboTipoRegistro.removeAllItems();
        	// Agregar nuevas opciones a comboEdicion
    		Set<String> ediciones2;
    		try { 
    			String selectedEvento = comboEvento.getItemAt(comboEvento.getSelectedIndex());
    			if(selectedEvento != null && selectedEvento != "- Seleccione un Evento -") {
    				ediciones2 = controladorEventos.nombresEdicionesEvento(selectedEvento);
    			}else {
    				ediciones2 = null;
    			}
    		} catch (EventoNoExiste ex) {
    			ediciones2 = null;
    		}
    		if (ediciones2 != null){
    	        for (String edicion : ediciones2) { comboEdicion.addItem(edicion); }
    		}
    		
        	// Agregar nuevas opciones a comboTipoRegistro
        	String[] nombreTiposRegistro;
    		try { 
    			String selectedEvento = comboEvento.getItemAt(comboEvento.getSelectedIndex());
    			String selectedEdicion = comboEdicion.getItemAt(comboEdicion.getSelectedIndex());
    			if(selectedEvento != null && selectedEvento != "- Seleccione un Evento -"
    					&& selectedEdicion != null && selectedEdicion != "- Seleccione una edicion -") {
    				nombreTiposRegistro = controladorEventos.getTiposRegistro(comboEvento.getItemAt(comboEvento.getSelectedIndex()), comboEdicion.getItemAt(comboEdicion.getSelectedIndex()));				
    			}else {
    				nombreTiposRegistro = null;
    			}
    		} catch (EventoNoExiste ex) { nombreTiposRegistro = null;
    		} catch (EdicionNoExiste ex) { nombreTiposRegistro = null;
    		}
        	if (nombreTiposRegistro != null) {
        		for (String nombre : nombreTiposRegistro) { comboTipoRegistro.addItem(nombre); }
        	}

        	// Actualizar datos
        	try { actualizarDatos();
        	} catch (TipoRegistroNoExiste ex) {
        		JOptionPane.showMessageDialog(null,  "El Tipo de Registro no existe");
				return;
        	}
        });
		// Actualizo opciones de  TipoRegistro al cambiar de Edicion
		comboEdicion.addActionListener(e ->{ // Cuando se cierra el combo
        	// Limpiar opciones viejas
        	comboTipoRegistro.removeAllItems();
        	// Agregar nuevas opciones a comboTipoRegistro
        	String eventoSeleccionado = comboEvento.getItemAt(comboEvento.getSelectedIndex());
        	String edicionSeleccionada = comboEdicion.getItemAt(comboEdicion.getSelectedIndex());
        	
        	String[] nombreTiposRegistro;
    		try { 
    			if(eventoSeleccionado != null && eventoSeleccionado != "- Seleccione un Evento -"
    					&& edicionSeleccionada != null && edicionSeleccionada != "- Seleccione una edicion -") {
    				nombreTiposRegistro = controladorEventos.getTiposRegistro(comboEvento.getItemAt(comboEvento.getSelectedIndex()), comboEdicion.getItemAt(comboEdicion.getSelectedIndex()));				
    			}else {
    				nombreTiposRegistro = null;
    			}
    		} catch (EventoNoExiste ex) { nombreTiposRegistro = null;
    		} catch (EdicionNoExiste ex) { nombreTiposRegistro = null;
    		}
        	if (nombreTiposRegistro != null) {
        		for (String nombre : nombreTiposRegistro) { comboTipoRegistro.addItem(nombre); }
        	}
        	// Actualizar datos
        	try {
            	actualizarDatos();
        	} catch (TipoRegistroNoExiste ex) {
        		JOptionPane.showMessageDialog(null,  "El Tipo de Registro no existe");
				return;
        	}
        });
		
		// Actualizar tabla al elegir TipoRegistro
		comboTipoRegistro.addActionListener(e -> { // Cuando se cierra el combo
        	// Actualizar datos
        	try { actualizarDatos();
        	} catch (TipoRegistroNoExiste ex) {
        		JOptionPane.showMessageDialog(null,  "El Tipo de Registro no existe");
				return;
        	}
        });
		
		// Trigger Listeners
		System.out.printf("ConsultaTipoRegistro::create() nombreEventoParam is: %s", nombreEventoParam);
		System.out.printf("ConsultaTipoRegistro::create() nombreEdicionParam is: %s", nombreEdicionParam);
		System.out.printf("ConsultaTipoRegistro::create() nombreParam is: %s", nombreParam);
		if (nombreEventoParam != null && nombreEdicionParam != null && nombreParam != null) {
			comboEvento.setSelectedItem("- Seleccione un Evento -");
			comboEvento.setSelectedItem(nombreEventoParam);
			comboEdicion.setSelectedItem("- Seleccione una edicion -");
			comboEdicion.setSelectedItem(nombreEdicionParam);
			comboTipoRegistro.setSelectedItem("- Seleccione un Tipo Registro -");
			comboTipoRegistro.setSelectedItem(nombreParam);
		}
	}
	
	// FUNCIONES
	public void actualizarDatos() throws TipoRegistroNoExiste {
		String tipoRegistroSeleccionado = comboTipoRegistro.getItemAt(comboTipoRegistro.getSelectedIndex());
		String eventoSeleccionado = comboEvento.getItemAt(comboEvento.getSelectedIndex());
		String edicionSeleccionada = comboEdicion.getItemAt(comboEdicion.getSelectedIndex());
		
		textField_Nombre.setText("");
    	textField_Descripcion.setText("");
    	textField_Costo.setText("");
    	textField_CuposMax.setText("");
    	textField_CuposDisp.setText("");
		
		DTTipoRegistro dtTipoRegistro = null;
		try {
			if(tipoRegistroSeleccionado!= null && tipoRegistroSeleccionado != "- Seleccione un Tipo Registro -" &&
					eventoSeleccionado != null && eventoSeleccionado != "- Seleccione un Evento -" &&
					edicionSeleccionada!= null && edicionSeleccionada != "- Seleccione una edicion -") {
				dtTipoRegistro = controladorEventos.getDTTipoRegistro(eventoSeleccionado, edicionSeleccionada, tipoRegistroSeleccionado);				
			}
    	} catch (TipoRegistroNoExiste ex) {
    		throw new TipoRegistroNoExiste("El Tipo de Registro no existe");
    	} catch (EventoNoExiste ex) {
    		return;
    	} catch (EdicionNoExiste ex) {
    		return;
    	}
		if (dtTipoRegistro != null) {
			textField_Nombre.setText(dtTipoRegistro.nombre());
	    	textField_Descripcion.setText(dtTipoRegistro.descripcion());
	    	textField_Costo.setText(String.valueOf(dtTipoRegistro.costo()));
	    	textField_CuposMax.setText(String.valueOf(dtTipoRegistro.cuposMax()));
	    	textField_CuposDisp.setText(String.valueOf(dtTipoRegistro.cuposDisponibles()));
		}
	}
}
