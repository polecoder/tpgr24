package presentacion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logica.Fabrica;
import logica.IControladorEventos;
import logica.datatypes.DTEdicionEvento;
import logica.datatypes.DTEvento;

public class ConsultaEdicionEvento extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField nombreTextField;
	private JTextField siglaTextField;
	private JTextField fechaInicioTextField;
	private JTextField fechaFinTextField;
	private JTextField fechaAltaTextField;
	private JTextField paisTextField;
	private JTextField ciudadTextField;
	private JTextField organizadorTextField;

	public ConsultaEdicionEvento(String nombreEvento, String nombreEdicion, JDesktopPane desktopPane) {
		super("Consulta de Edición de Evento", true, true, true, true);
		setSize(400, 500);
		setLocation(100, 10);

		Fabrica fabrica = Fabrica.getInstancia();
		IControladorEventos ICEv = fabrica.getControladorEvento();

		List<String> listaEventos = new ArrayList<>();
		listaEventos.add("- Seleccione un Evento -");

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		// Evento
		JLabel lblEvento = new JLabel("Evento:");
		GridBagConstraints gbc_lblEvento = new GridBagConstraints();
		gbc_lblEvento.insets = new Insets(0, 0, 5, 5);
		gbc_lblEvento.gridx = 1;
		gbc_lblEvento.gridy = 1;
		getContentPane().add(lblEvento, gbc_lblEvento);

		Map<String, DTEvento> eventos = ICEv.listarEventos();
		listaEventos.addAll(eventos.keySet());
		String[] arrayEventos = listaEventos.toArray(new String[0]);

		JComboBox<String> eventoComboBox = new JComboBox<>(arrayEventos);
		GridBagConstraints gbc_eventoComboBox = new GridBagConstraints();
		gbc_eventoComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_eventoComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_eventoComboBox.gridx = 3;
		gbc_eventoComboBox.gridy = 1;
		getContentPane().add(eventoComboBox, gbc_eventoComboBox);
		

		// Edicion
		JLabel lblEdicion = new JLabel("Edicion:");
		GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
		gbc_lblEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdicion.gridx = 1;
		gbc_lblEdicion.gridy = 2;
		getContentPane().add(lblEdicion, gbc_lblEdicion);

		String[] ediciones = { "- Seleccione una edicion -" };
		JComboBox<String> edicionComboBox = new JComboBox<>(ediciones);
		GridBagConstraints gbc_edicionComboBox = new GridBagConstraints();
		gbc_edicionComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_edicionComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_edicionComboBox.gridx = 3;
		gbc_edicionComboBox.gridy = 2;
		getContentPane().add(edicionComboBox, gbc_edicionComboBox);
		
		if(nombreEdicion != null) {
			edicionComboBox.setSelectedItem(nombreEdicion);
		}

		// Panel Datos
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		// Dato: Organizador
		JLabel lblNombre = new JLabel("Organizador:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 1;
		panel.add(lblNombre, gbc_lblNombre);

		nombreTextField = new JTextField();
		nombreTextField.setEditable(false);
		GridBagConstraints gbc_nombreTextField = new GridBagConstraints();
		gbc_nombreTextField.insets = new Insets(0, 0, 5, 0);
		gbc_nombreTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreTextField.gridx = 3;
		gbc_nombreTextField.gridy = 1;
		panel.add(nombreTextField, gbc_nombreTextField);
		nombreTextField.setColumns(10);

		// Dato: Sigla
		JLabel lblSigla = new JLabel("Sigla:");
		GridBagConstraints gbc_lblSigla = new GridBagConstraints();
		gbc_lblSigla.insets = new Insets(0, 0, 5, 5);
		gbc_lblSigla.gridx = 1;
		gbc_lblSigla.gridy = 2;
		panel.add(lblSigla, gbc_lblSigla);

		siglaTextField = new JTextField();
		siglaTextField.setEditable(false);
		GridBagConstraints gbc_siglaTextField = new GridBagConstraints();
		gbc_siglaTextField.insets = new Insets(0, 0, 5, 0);
		gbc_siglaTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_siglaTextField.gridx = 3;
		gbc_siglaTextField.gridy = 2;
		panel.add(siglaTextField, gbc_siglaTextField);
		siglaTextField.setColumns(10);

		// Dato: Fecha Inicio
		JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
		GridBagConstraints gbc_lblFechaInicio = new GridBagConstraints();
		gbc_lblFechaInicio.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaInicio.gridx = 1;
		gbc_lblFechaInicio.gridy = 3;
		panel.add(lblFechaInicio, gbc_lblFechaInicio);

		fechaInicioTextField = new JTextField();
		fechaInicioTextField.setEditable(false);
		GridBagConstraints gbc_fechaInicioTextField = new GridBagConstraints();
		gbc_fechaInicioTextField.insets = new Insets(0, 0, 5, 0);
		gbc_fechaInicioTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaInicioTextField.gridx = 3;
		gbc_fechaInicioTextField.gridy = 3;
		panel.add(fechaInicioTextField, gbc_fechaInicioTextField);
		fechaInicioTextField.setColumns(10);

		// Dato: Fecha Final
		JLabel lblFechaFin = new JLabel("Fecha Fin:");
		GridBagConstraints gbc_lblFechaFin = new GridBagConstraints();
		gbc_lblFechaFin.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaFin.gridx = 1;
		gbc_lblFechaFin.gridy = 4;
		panel.add(lblFechaFin, gbc_lblFechaFin);

		fechaFinTextField = new JTextField();
		fechaFinTextField.setEditable(false);
		GridBagConstraints gbc_fechaFinTextField = new GridBagConstraints();
		gbc_fechaFinTextField.insets = new Insets(0, 0, 5, 0);
		gbc_fechaFinTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaFinTextField.gridx = 3;
		gbc_fechaFinTextField.gridy = 4;
		panel.add(fechaFinTextField, gbc_fechaFinTextField);
		fechaFinTextField.setColumns(10);

		// Dato: Fecha Alta
		JLabel lblFechaAlta = new JLabel("Fecha Alta:");
		GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
		gbc_lblFechaAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaAlta.gridx = 1;
		gbc_lblFechaAlta.gridy = 5;
		panel.add(lblFechaAlta, gbc_lblFechaAlta);

		fechaAltaTextField = new JTextField();
		fechaAltaTextField.setEditable(false);
		GridBagConstraints gbc_fechaAltaTextField = new GridBagConstraints();
		gbc_fechaAltaTextField.insets = new Insets(0, 0, 5, 0);
		gbc_fechaAltaTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaAltaTextField.gridx = 3;
		gbc_fechaAltaTextField.gridy = 5;
		panel.add(fechaAltaTextField, gbc_fechaAltaTextField);
		fechaAltaTextField.setColumns(10);

		// Dato: Pais
		JLabel lblPais = new JLabel("Pais:");
		GridBagConstraints gbc_lblPais = new GridBagConstraints();
		gbc_lblPais.insets = new Insets(0, 0, 5, 5);
		gbc_lblPais.gridx = 1;
		gbc_lblPais.gridy = 6;
		panel.add(lblPais, gbc_lblPais);

		paisTextField = new JTextField();
		paisTextField.setEditable(false);
		GridBagConstraints gbc_paisTextField = new GridBagConstraints();
		gbc_paisTextField.insets = new Insets(0, 0, 5, 0);
		gbc_paisTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_paisTextField.gridx = 3;
		gbc_paisTextField.gridy = 6;
		panel.add(paisTextField, gbc_paisTextField);
		paisTextField.setColumns(10);

		// Dato: Ciudad
		JLabel lblCiudad = new JLabel("Ciudad:");
		GridBagConstraints gbc_lblCiudad = new GridBagConstraints();
		gbc_lblCiudad.insets = new Insets(0, 0, 5, 5);
		gbc_lblCiudad.gridx = 1;
		gbc_lblCiudad.gridy = 7;
		panel.add(lblCiudad, gbc_lblCiudad);

		ciudadTextField = new JTextField();
		ciudadTextField.setEditable(false);
		GridBagConstraints gbc_ciudadTextField = new GridBagConstraints();
		gbc_ciudadTextField.insets = new Insets(0, 0, 5, 0);
		gbc_ciudadTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ciudadTextField.gridx = 3;
		gbc_ciudadTextField.gridy = 7;
		panel.add(ciudadTextField, gbc_ciudadTextField);
		ciudadTextField.setColumns(10);

		// Dato: Organizador
		JLabel lblOrganizador = new JLabel("Organizador:");
		GridBagConstraints gbc_lblOrganizador = new GridBagConstraints();
		gbc_lblOrganizador.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrganizador.gridx = 1;
		gbc_lblOrganizador.gridy = 8;
		panel.add(lblOrganizador, gbc_lblOrganizador);

		organizadorTextField = new JTextField();
		organizadorTextField.setEditable(false);
		GridBagConstraints gbc_organizadorTextField = new GridBagConstraints();
		gbc_organizadorTextField.insets = new Insets(0, 0, 5, 0);
		gbc_organizadorTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_organizadorTextField.gridx = 3;
		gbc_organizadorTextField.gridy = 8;
		panel.add(organizadorTextField, gbc_organizadorTextField);
		organizadorTextField.setColumns(10);

		// Panel de Listas
		JPanel panel_listas = new JPanel();
		GridBagConstraints gbc_panel_listas = new GridBagConstraints();
		gbc_panel_listas.gridwidth = 3;
		gbc_panel_listas.insets = new Insets(0, 5, 5,0);
		gbc_panel_listas.fill = GridBagConstraints.BOTH;
		gbc_panel_listas.gridx = 1;
		gbc_panel_listas.gridy = 9;
		panel.add(panel_listas, gbc_panel_listas);
		GridBagLayout gbl_panel_listas = new GridBagLayout();
		gbl_panel_listas.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_listas.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_listas.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_listas.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_listas.setLayout(gbl_panel_listas);

		// Lista de Tipo Registros
		JLabel lblTipoRegistro = new JLabel("Tipo Registro:");
		GridBagConstraints gbc_lblTipoRegistro = new GridBagConstraints();
		gbc_lblTipoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoRegistro.gridx = 0;
		gbc_lblTipoRegistro.gridy = 1;
		panel_listas.add(lblTipoRegistro, gbc_lblTipoRegistro);

		JList<String> tipoRegistroList = new JList<>();
		GridBagConstraints gbc_tipoRegistroList = new GridBagConstraints();
		gbc_tipoRegistroList.insets = new Insets(0, 0, 0, 5);
		gbc_tipoRegistroList.fill = GridBagConstraints.BOTH;
		gbc_tipoRegistroList.gridx = 0;
		gbc_tipoRegistroList.gridy = 2;
		panel_listas.add(tipoRegistroList, gbc_tipoRegistroList);

		// Lista de Registros
		JLabel lblRegistro = new JLabel("Registros:");
		GridBagConstraints gbc_lblRegistro = new GridBagConstraints();
		gbc_lblRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_lblRegistro.gridx = 1;
		gbc_lblRegistro.gridy = 1;
		panel_listas.add(lblRegistro, gbc_lblRegistro);

		JList<String> registroList = new JList<>();
		GridBagConstraints gbc_registroList = new GridBagConstraints();
		gbc_registroList.insets = new Insets(0, 0, 0, 5);
		gbc_registroList.fill = GridBagConstraints.BOTH;
		gbc_registroList.gridx = 1;
		gbc_registroList.gridy = 2;
		panel_listas.add(registroList, gbc_registroList);

		// Lista de Patrocinios
		JLabel lblPatrocinio = new JLabel("Patrocinio");
		GridBagConstraints gbc_lblPatrocinio = new GridBagConstraints();
		gbc_lblPatrocinio.insets = new Insets(0, 0, 5, 0);
		gbc_lblPatrocinio.gridx = 2;
		gbc_lblPatrocinio.gridy = 1;
		panel_listas.add(lblPatrocinio, gbc_lblPatrocinio);

		JList<String> patrocinioList = new JList<>();
		GridBagConstraints gbc_patrocinioList = new GridBagConstraints();
		gbc_patrocinioList.fill = GridBagConstraints.BOTH;
		gbc_patrocinioList.gridx = 2;
		gbc_patrocinioList.gridy = 2;
		panel_listas.add(patrocinioList, gbc_patrocinioList);

		
		
		// Action Listeners
		eventoComboBox.addActionListener(e -> {
			String selectedEvento = (String) eventoComboBox.getSelectedItem();
			if(selectedEvento != "- Seleccione un Evento -") {
				//System.out.printf("\n ConsultaEdicionEvento::eventoComboBox.actionListener() selectedEvento is: %s",selectedEvento);
				
				List<String> listaEdiciones = new ArrayList<>();
				Map<String, DTEdicionEvento> HMediciones = ICEv.listarEdicionesEvento(selectedEvento);
				listaEdiciones.addAll(HMediciones.keySet());
				String[] arrayEdiciones = listaEdiciones.toArray(new String[0]);
				edicionComboBox.setModel(new DefaultComboBoxModel<>(arrayEdiciones));				
			}
		});
		edicionComboBox.addActionListener(e -> {
			String selectedEdicion = (String) edicionComboBox.getSelectedItem();
			String selectedEvento = (String) eventoComboBox.getSelectedItem();
			if(selectedEvento != "- Seleccione un Evento -" && selectedEdicion != "- Seleccione una edicion -") {
				DTEdicionEvento dtEd = ICEv.getDTEdicion(selectedEvento, selectedEdicion);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				// Text Fields
				nombreTextField.setText(dtEd.getNombre());
				siglaTextField.setText(dtEd.getSigla());
				fechaInicioTextField.setText(sdf.format(dtEd.getFechaInicio()));
				fechaFinTextField.setText(sdf.format(dtEd.getFechaFin()));
				fechaAltaTextField.setText(sdf.format(dtEd.getFechaAlta()));
				paisTextField.setText(dtEd.getPais());
				ciudadTextField.setText(dtEd.getCiudad());
				organizadorTextField.setText(dtEd.getOrganizadorNickname());
				
				// Lists
				// Tipo registro list
				DefaultListModel<String> trModel = new DefaultListModel<>();
				trModel.clear();
				for (String trName : ICEv.listarTipoRegistro(selectedEvento, selectedEdicion).keySet()) {
					trModel.addElement(trName);
				}
				tipoRegistroList.setModel(trModel);
				// Registro List
				DefaultListModel<String> rModel = new DefaultListModel<>();
				rModel.clear();
				for (String rName : ICEv.listarRegistrosAEdicion(selectedEvento, selectedEdicion)) {
					rModel.addElement(rName);
				}
				registroList.setModel(rModel);
				// Patrocinio List
				DefaultListModel<String> pModel = new DefaultListModel<>();
				pModel.clear();
				for (String pName : ICEv.listarPatrociniosDeEdicion(selectedEvento, selectedEdicion)) {
					pModel.addElement(pName);
				}
				patrocinioList.setModel(pModel);
			}
		});
		tipoRegistroList.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		        if (e.getClickCount() == 2) { 
		            String selectedTR = tipoRegistroList.getSelectedValue();
		            String selectedEdicion = (String) edicionComboBox.getSelectedItem();
					String selectedEvento = (String) eventoComboBox.getSelectedItem();
					System.out.print(selectedTR);
					System.out.print(".\n");
					System.out.print(selectedEdicion);
					System.out.print(".\n");
					System.out.print(selectedEvento);
					if(selectedTR != null && selectedEdicion != null && selectedEvento !=null) {
						ConsultaTipoRegistro ventanaConsultaTipoRegistro= new ConsultaTipoRegistro(selectedTR, selectedEvento, selectedEdicion);
						desktopPane.add(ventanaConsultaTipoRegistro);           
						ventanaConsultaTipoRegistro.setVisible(true);    
						try {
							ventanaConsultaTipoRegistro.setSelected(true); 
						} catch (Exception ex) {}						
					}
		            
		        }
		    }
		});
		
		patrocinioList.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		        if (e.getClickCount() == 2) { 
		            String seleccionado = patrocinioList.getSelectedValue();
		            System.out.println("Doble clic en: " + seleccionado);
		        }
		    }
		});
		
		// Trigger Listeners
		if (nombreEvento != null && nombreEdicion != null) {
			eventoComboBox.setSelectedItem("- Seleccione un Evento -");
			eventoComboBox.setSelectedItem(nombreEvento);
			edicionComboBox.setSelectedItem("- Seleccione una edicion -");
			edicionComboBox.setSelectedItem(nombreEdicion);
		}

	}

}
