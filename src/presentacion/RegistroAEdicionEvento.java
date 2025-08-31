package presentacion;
import logica.IControladorUsuarios;
import logica.Utilidades;
import logica.Fabrica;
import logica.IControladorEventos;

import logica.datatypes.DTEvento;
import logica.datatypes.DTTipoRegistro;
import logica.datatypes.DTAsistente;
import logica.datatypes.DTEdicionEvento;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;


public class RegistroAEdicionEvento extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField codigoPatrocinioField;
	private static final Logger logger = Logger.getLogger(RegistroAEdicionEvento.class.getName());

	public RegistroAEdicionEvento() {
		super("Registro a Edicion Evento", true, true, true, true);
		Fabrica fabrica = Fabrica.getInstancia();
        IControladorEventos ICEv = fabrica.getControladorEvento();
        IControladorUsuarios ICU = fabrica.getControladorUsuarios();
        
        List<String> listaEventos = new ArrayList<>();
        listaEventos.add("- Seleccione un Evento -");
        
        List<String> listaAsistentes = new ArrayList<>();
        listaAsistentes.add("- Seleccione un Asistente -");
        
        
		setSize(350, 250);       
		setLocation(50, 50);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		// Eventos
		JLabel eventoLabel = new JLabel("Evento");
		GridBagConstraints gbc_lblEventoLabel = new GridBagConstraints();
		gbc_lblEventoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblEventoLabel.anchor = GridBagConstraints.EAST;
		gbc_lblEventoLabel.gridx = 1;
		gbc_lblEventoLabel.gridy = 1;
		getContentPane().add(eventoLabel, gbc_lblEventoLabel);
		
		Map<String, DTEvento> eventos = ICEv.listarEventos();
		listaEventos.addAll(eventos.keySet());
		String[] arrayEventos = listaEventos.toArray(new String[0]);
		JComboBox<String> eventosComboBox = new JComboBox<>(arrayEventos);
		GridBagConstraints gbc_eventosComboBox = new GridBagConstraints();
		gbc_eventosComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_eventosComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_eventosComboBox.gridx = 2;
		gbc_eventosComboBox.gridy = 1;
		getContentPane().add(eventosComboBox, gbc_eventosComboBox);
		
		
		// Ediciones
		JLabel edicionesLabel = new JLabel("Ediciones");
		GridBagConstraints gbc_lblEdicionesLabel = new GridBagConstraints();
		gbc_lblEdicionesLabel.anchor = GridBagConstraints.EAST;
		gbc_lblEdicionesLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdicionesLabel.gridx = 1;
		gbc_lblEdicionesLabel.gridy = 2;
		getContentPane().add(edicionesLabel, gbc_lblEdicionesLabel);

		String[] ediciones = {"- Seleccione una edicion -"};
		JComboBox<String> edicionesComboBox = new JComboBox<>(ediciones);
		GridBagConstraints gbc_edicionesComboBox = new GridBagConstraints();
		gbc_edicionesComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_edicionesComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_edicionesComboBox.gridx = 2;
		gbc_edicionesComboBox.gridy = 2;
		getContentPane().add(edicionesComboBox, gbc_edicionesComboBox);
		
		
		// Tipos Registro
		JLabel tipoRegistroLabel = new JLabel("Tipo de Registro");
		GridBagConstraints gbc_lblTipoRegistro = new GridBagConstraints();
		gbc_lblTipoRegistro.anchor = GridBagConstraints.EAST;
		gbc_lblTipoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoRegistro.gridx = 1;
		gbc_lblTipoRegistro.gridy = 3;
		getContentPane().add(tipoRegistroLabel, gbc_lblTipoRegistro);
		
		String[] tiposRegistro = {"- Seleccione un Tipo de Registro -"};
		JComboBox<String> tipoRegistroComboBox = new JComboBox<>(tiposRegistro);
		GridBagConstraints gbc_tipoRegistroComboBox = new GridBagConstraints();
		gbc_tipoRegistroComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_tipoRegistroComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_tipoRegistroComboBox.gridx = 2;
		gbc_tipoRegistroComboBox.gridy = 3;
		getContentPane().add(tipoRegistroComboBox, gbc_tipoRegistroComboBox);
		
		
		// Asistente
		JLabel asistenteLabel = new JLabel("Asistente");
		GridBagConstraints gbc_lblAsistenteLabel = new GridBagConstraints();
		gbc_lblAsistenteLabel.anchor = GridBagConstraints.EAST;
		gbc_lblAsistenteLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblAsistenteLabel.gridx = 1;
		gbc_lblAsistenteLabel.gridy = 4;
		getContentPane().add(asistenteLabel, gbc_lblAsistenteLabel);
		
		Map<String, DTAsistente> HMAsistentes = ICU.listarAsistentes();
		listaAsistentes.addAll(HMAsistentes.keySet());
		String[] arrayAsistentes = listaAsistentes.toArray(new String[0]);
		JComboBox<String> asistenteComboBox = new JComboBox<>(arrayAsistentes);
		GridBagConstraints gbc_asistenteComboBox = new GridBagConstraints();
		gbc_asistenteComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_asistenteComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_asistenteComboBox.gridx = 2;
		gbc_asistenteComboBox.gridy = 4;
		getContentPane().add(asistenteComboBox, gbc_asistenteComboBox);
		
		
		// Codigo Patrocinio
		JLabel codigoPatrocinioLabel = new JLabel("Codigo de Patrocinio");
		GridBagConstraints gbc_codigoPatrocinioLabel = new GridBagConstraints();
		gbc_codigoPatrocinioLabel.anchor = GridBagConstraints.EAST;
		gbc_codigoPatrocinioLabel.insets = new Insets(0, 0, 5, 5);
		gbc_codigoPatrocinioLabel.gridx = 1;
		gbc_codigoPatrocinioLabel.gridy = 5;
		getContentPane().add(codigoPatrocinioLabel, gbc_codigoPatrocinioLabel);
		
		codigoPatrocinioField = new JTextField();
		GridBagConstraints gbc_codigoPatrocinioField = new GridBagConstraints();
		gbc_codigoPatrocinioField.insets = new Insets(0, 0, 5, 0);
		gbc_codigoPatrocinioField.fill = GridBagConstraints.HORIZONTAL;
		gbc_codigoPatrocinioField.gridx = 2;
		gbc_codigoPatrocinioField.gridy = 5;
		getContentPane().add(codigoPatrocinioField, gbc_codigoPatrocinioField);
		codigoPatrocinioField.setColumns(10);
		
		
		// Boton Aceptar
		JButton aceptarButton = new JButton("Aceptar");
		GridBagConstraints gbc_aceptarButton = new GridBagConstraints();
		gbc_aceptarButton.insets = new Insets(0, 0, 0, 5);
		gbc_aceptarButton.gridx = 1;
		gbc_aceptarButton.gridy = 7;
		getContentPane().add(aceptarButton, gbc_aceptarButton);
		
		
		// Boton Cancelar
		JButton cancelarButton = new JButton("Cancelar");
		GridBagConstraints gbc_cancelarButton = new GridBagConstraints();
		gbc_cancelarButton.gridx = 2;
		gbc_cancelarButton.gridy = 7;
		getContentPane().add(cancelarButton, gbc_cancelarButton);
		
		// Event Listeners
		// Event Listener Eventos
		eventosComboBox.addActionListener(e -> {
			String selectedEvento = (String) eventosComboBox.getSelectedItem();
			List<String> listaEdiciones = new ArrayList<>();
			Map<String, DTEdicionEvento> HMediciones = ICEv.listarEdicionesEvento(selectedEvento);
			listaEdiciones.addAll(HMediciones.keySet());
			String[] arrayEdiciones = listaEdiciones.toArray(new String[0]);
			edicionesComboBox.setModel(new DefaultComboBoxModel<>(arrayEdiciones));
		});
		// Event Listener Ediciones
		edicionesComboBox.addActionListener(e -> {
			String selectedEdicion = (String) edicionesComboBox.getSelectedItem();
			String selectedEvento = (String) eventosComboBox.getSelectedItem();
			List<String> listaTipoRegistro = new ArrayList<>();
			Map<String, DTTipoRegistro> HMTipoRegistros = ICEv.listarTipoRegistro(selectedEvento, selectedEdicion);
			listaTipoRegistro.addAll(HMTipoRegistros.keySet());
			String[] arrayTipoRegistros = listaTipoRegistro.toArray(new String[0]);
			tipoRegistroComboBox.setModel(new DefaultComboBoxModel<>(arrayTipoRegistros));
		});
		// Event Listener Aceptar()
		aceptarButton.addActionListener(e ->{
			String selectedEvento = (String) eventosComboBox.getSelectedItem();
			String selectedEdicion = (String) edicionesComboBox.getSelectedItem();
			String selectedTipoRegistro = (String) tipoRegistroComboBox.getSelectedItem();
			String selectedAsistente = (String) asistenteComboBox.getSelectedItem();
			String codigoPatrocinio = (String) codigoPatrocinioField.getText();
			boolean validacion = ICEv.validar(selectedAsistente, selectedEdicion, selectedTipoRegistro, selectedEvento);
			if(validacion) {
				ICEv.altaRegistro(
						selectedAsistente, 
						selectedTipoRegistro,
						selectedEdicion, 
						selectedEvento,
						codigoPatrocinio, 
						Utilidades.toDate(LocalDate.now())); //? LocalDate.now() Es apropiado?
				JOptionPane.showMessageDialog(this, 
						"Registro confirmado con exito!", 
						"Exito!", 
						JOptionPane.INFORMATION_MESSAGE);
				this.doDefaultCloseAction();
			}else {
				int option = JOptionPane.showConfirmDialog(this, 
						"Los datos ingresados no son correctos. ¿Desea reintentar?", 
						"Alerta:", 
						JOptionPane.OK_CANCEL_OPTION, 
						JOptionPane.WARNING_MESSAGE);
				if(option == JOptionPane.CANCEL_OPTION) {
					this.doDefaultCloseAction();
				}

			}
		});
		// Event Listener Cancelar()
		cancelarButton.addActionListener(e ->{
			this.doDefaultCloseAction();
		});

	}

}