package presentacion;

import java.awt.Dimension;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import excepciones.EventoNoExiste;
import logica.Fabrica;
import logica.datatypes.DTDetallePatrocinio;
import logica.datatypes.DTEvento;

public class ConsultaPatrocinio extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private Fabrica fabrica = Fabrica.getInstancia();
	private JTextField institucionTxt;
	private JTextField fechaTxt;
	private JTextField aporteTxt;
	private JTextField nivelTxt;
	private JTextField cuposTxt;
	private final String PLACEHOLDER_EVENTO = "--Seleccionar un Evento--";
	private final String PLACEHOLDER_EDICION = "--Seleccionar una Edición--";
	private final String PLACEHOLDER_PATROCINIO = "--Seleccionar un Patrocinio--";
	private JTextField tipoRegistroTxt;
	
	public ConsultaPatrocinio() {
		super("Consulta de Patrocinio", true, true, true, true);
		setSize(400, 300);
		setMinimumSize(new Dimension(400, 300));
		setMaximumSize(new Dimension(800, 600)); 
		setLocation(50, 50);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0,0,0,0};
		gridBagLayout.rowHeights = new int[]{0,0, 0, 0, 0, 0, 0, 0, 0,0};
		gridBagLayout.columnWeights = new double[]{1.0,0.0,1.0,1.0};
		gridBagLayout.rowWeights = new double[]{1.0,0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel eventoLbl = new JLabel("Evento:");
		GridBagConstraints gbc_eventoLbl = new GridBagConstraints();
		gbc_eventoLbl.anchor = GridBagConstraints.EAST;
		gbc_eventoLbl.insets = new Insets(0, 0, 5, 5);
		gbc_eventoLbl.gridx = 1;
		gbc_eventoLbl.gridy = 1;
		getContentPane().add(eventoLbl, gbc_eventoLbl);
		
		JComboBox<String> comboEvento = new JComboBox<String>();
		comboEvento.addItem(PLACEHOLDER_EVENTO);
		Set<String> eventos = fabrica.getControladorEvento().nombresEventos();
		for (String evento: eventos) {
			comboEvento.addItem(evento);
		}
		GridBagConstraints gbc_comboEvento = new GridBagConstraints();
		gbc_comboEvento.insets = new Insets(0, 0, 5, 5);
		gbc_comboEvento.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEvento.gridx = 2;
		gbc_comboEvento.gridy = 1;
		getContentPane().add(comboEvento, gbc_comboEvento);
		
		JLabel edicionLbl = new JLabel("Edición:");
		GridBagConstraints gbc_edicionLbl = new GridBagConstraints();
		gbc_edicionLbl.anchor = GridBagConstraints.EAST;
		gbc_edicionLbl.insets = new Insets(0, 0, 5, 5);
		gbc_edicionLbl.gridx = 1;
		gbc_edicionLbl.gridy = 2;
		getContentPane().add(edicionLbl, gbc_edicionLbl);
		
		JComboBox<String> comboEdicion = new JComboBox<String>();
		comboEdicion.addItem(PLACEHOLDER_EDICION);
		GridBagConstraints gbc_comboEdicion = new GridBagConstraints();
		gbc_comboEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_comboEdicion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEdicion.gridx = 2;
		gbc_comboEdicion.gridy = 2;
		getContentPane().add(comboEdicion, gbc_comboEdicion);
		
		JLabel patrocinioLbl = new JLabel("Patrocinio:");
		GridBagConstraints gbc_patrocinioLbl = new GridBagConstraints();
		gbc_patrocinioLbl.anchor = GridBagConstraints.EAST;
		gbc_patrocinioLbl.insets = new Insets(0, 0, 5, 5);
		gbc_patrocinioLbl.gridx = 1;
		gbc_patrocinioLbl.gridy = 3;
		getContentPane().add(patrocinioLbl, gbc_patrocinioLbl);
		
		JComboBox<String> comboPatrocinio = new JComboBox<String>();
		comboPatrocinio.addItem(PLACEHOLDER_PATROCINIO);
		GridBagConstraints gbc_comboPatrocinio = new GridBagConstraints();
		gbc_comboPatrocinio.insets = new Insets(0, 0, 5, 5);
		gbc_comboPatrocinio.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboPatrocinio.gridx = 2;
		gbc_comboPatrocinio.gridy = 3;
		getContentPane().add(comboPatrocinio, gbc_comboPatrocinio);
		
		JLabel institucionLbl = new JLabel("Institución:");
		GridBagConstraints gbc_institucionLbl = new GridBagConstraints();
		gbc_institucionLbl.anchor = GridBagConstraints.EAST;
		gbc_institucionLbl.insets = new Insets(0, 0, 5, 5);
		gbc_institucionLbl.gridx = 1;
		gbc_institucionLbl.gridy = 4;
		getContentPane().add(institucionLbl, gbc_institucionLbl);
		
		institucionTxt = new JTextField();
		institucionTxt.setEditable(false);
		GridBagConstraints gbc_institucionTxt = new GridBagConstraints();
		gbc_institucionTxt.insets = new Insets(0, 0, 5, 5);
		gbc_institucionTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_institucionTxt.gridx = 2;
		gbc_institucionTxt.gridy = 4;
		getContentPane().add(institucionTxt, gbc_institucionTxt);
		institucionTxt.setColumns(10);
		
		JLabel fechaLbl = new JLabel("Fecha:");
		GridBagConstraints gbc_fechaLbl = new GridBagConstraints();
		gbc_fechaLbl.anchor = GridBagConstraints.EAST;
		gbc_fechaLbl.insets = new Insets(0, 0, 5, 5);
		gbc_fechaLbl.gridx = 1;
		gbc_fechaLbl.gridy = 5;
		getContentPane().add(fechaLbl, gbc_fechaLbl);
		
		fechaTxt = new JTextField();
		fechaTxt.setEditable(false);
		GridBagConstraints gbc_fechaTxt = new GridBagConstraints();
		gbc_fechaTxt.insets = new Insets(0, 0, 5, 5);
		gbc_fechaTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaTxt.gridx = 2;
		gbc_fechaTxt.gridy = 5;
		getContentPane().add(fechaTxt, gbc_fechaTxt);
		fechaTxt.setColumns(10);
		
		JLabel aporteLbl = new JLabel("Aporte:");
		GridBagConstraints gbc_aporteLbl = new GridBagConstraints();
		gbc_aporteLbl.anchor = GridBagConstraints.EAST;
		gbc_aporteLbl.insets = new Insets(0, 0, 5, 5);
		gbc_aporteLbl.gridx = 1;
		gbc_aporteLbl.gridy = 6;
		getContentPane().add(aporteLbl, gbc_aporteLbl);
		
		aporteTxt = new JTextField();
		aporteTxt.setEditable(false);
		GridBagConstraints gbc_aporteTxt = new GridBagConstraints();
		gbc_aporteTxt.insets = new Insets(0, 0, 5, 5);
		gbc_aporteTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_aporteTxt.gridx = 2;
		gbc_aporteTxt.gridy = 6;
		getContentPane().add(aporteTxt, gbc_aporteTxt);
		aporteTxt.setColumns(10);
		
		JLabel nivelLbl = new JLabel("Nivel:");
		GridBagConstraints gbc_nivelLbl = new GridBagConstraints();
		gbc_nivelLbl.anchor = GridBagConstraints.EAST;
		gbc_nivelLbl.insets = new Insets(0, 0, 5, 5);
		gbc_nivelLbl.gridx = 1;
		gbc_nivelLbl.gridy = 7;
		getContentPane().add(nivelLbl, gbc_nivelLbl);
		
		nivelTxt = new JTextField();
		nivelTxt.setEditable(false);
		GridBagConstraints gbc_nivelTxt = new GridBagConstraints();
		gbc_nivelTxt.insets = new Insets(0, 0, 5, 5);
		gbc_nivelTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_nivelTxt.gridx = 2;
		gbc_nivelTxt.gridy = 7;
		getContentPane().add(nivelTxt, gbc_nivelTxt);
		nivelTxt.setColumns(10);
		
		JLabel cuposLbl = new JLabel("Cupos gratuitos:");
		GridBagConstraints gbc_cuposLbl = new GridBagConstraints();
		gbc_cuposLbl.anchor = GridBagConstraints.EAST;
		gbc_cuposLbl.insets = new Insets(0, 0, 5, 5);
		gbc_cuposLbl.gridx = 1;
		gbc_cuposLbl.gridy = 8;
		getContentPane().add(cuposLbl, gbc_cuposLbl);
		
		cuposTxt = new JTextField();
		cuposTxt.setEditable(false);
		GridBagConstraints gbc_cuposTxt = new GridBagConstraints();
		gbc_cuposTxt.insets = new Insets(0, 0, 5, 5);
		gbc_cuposTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_cuposTxt.gridx = 2;
		gbc_cuposTxt.gridy = 8;
		getContentPane().add(cuposTxt, gbc_cuposTxt);
		cuposTxt.setColumns(10);
		
		JLabel tipoRegistroLbl = new JLabel("Tipo de Registro");
		GridBagConstraints gbc_tipoRegistroLbl = new GridBagConstraints();
		gbc_tipoRegistroLbl.anchor = GridBagConstraints.EAST;
		gbc_tipoRegistroLbl.insets = new Insets(0, 0, 0, 5);
		gbc_tipoRegistroLbl.gridx = 1;
		gbc_tipoRegistroLbl.gridy = 9;
		getContentPane().add(tipoRegistroLbl, gbc_tipoRegistroLbl);
		
		tipoRegistroTxt = new JTextField();
		tipoRegistroTxt.setEditable(false);
		GridBagConstraints gbc_tipoRegistroTxt = new GridBagConstraints();
		gbc_tipoRegistroTxt.insets = new Insets(0, 0, 0, 5);
		gbc_tipoRegistroTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_tipoRegistroTxt.gridx = 2;
		gbc_tipoRegistroTxt.gridy = 9;
		getContentPane().add(tipoRegistroTxt, gbc_tipoRegistroTxt);
		tipoRegistroTxt.setColumns(10);
		
		comboEvento.addActionListener(e -> {
			String eventoSeleccionado = (String) comboEvento.getSelectedItem();
			if (eventoSeleccionado != null && !eventoSeleccionado.equals(PLACEHOLDER_EVENTO)) {
				try {
					Set<String> ediciones = fabrica.getControladorEvento().nombresEdicionesEvento(eventoSeleccionado);
					actualizarCombobox(comboEdicion, () -> {
						comboEdicion.removeAllItems();
						comboEdicion.addItem(PLACEHOLDER_EDICION);
						for (String edicion: ediciones) {
							comboEdicion.addItem(edicion);
						}
					});
				} catch (EventoNoExiste ex) {
					ex.printStackTrace();
				}
			}
			comboEdicion.setSelectedItem(PLACEHOLDER_EDICION);
			comboPatrocinio.setSelectedItem(PLACEHOLDER_PATROCINIO);
			resetCampos();
		});
		
		comboEdicion.addActionListener(e -> {
			String eventoSeleccionado = (String) comboEvento.getSelectedItem();
			String edicionSeleccionada = (String) comboEdicion.getSelectedItem();
			if (edicionSeleccionada != null && !edicionSeleccionada.equals(PLACEHOLDER_EDICION)) {
				Set<String> patrocinios = fabrica.getControladorEvento().listarPatrociniosDeEdicion(eventoSeleccionado, edicionSeleccionada);
				actualizarCombobox(comboPatrocinio, () -> {
					comboPatrocinio.removeAllItems();
					comboPatrocinio.addItem(PLACEHOLDER_PATROCINIO);
					for (String patrocinio: patrocinios) {
						comboPatrocinio.addItem(patrocinio);
					}
				});
			}
			comboPatrocinio.setSelectedItem(PLACEHOLDER_PATROCINIO);
			resetCampos();
		});
		
		comboPatrocinio.addActionListener(e -> {
			String eventoSeleccionado = (String) comboEvento.getSelectedItem();
			String edicionSeleccionada = (String) comboEdicion.getSelectedItem();
			String patrocinioSeleccionado = (String) comboPatrocinio.getSelectedItem();
			if (patrocinioSeleccionado != null && !patrocinioSeleccionado.equals(PLACEHOLDER_PATROCINIO)) {
				DTDetallePatrocinio patrocinio = fabrica.getControladorEvento().mostrarDetallePatrocinio(eventoSeleccionado, edicionSeleccionada, patrocinioSeleccionado);
				this.aporteTxt.setText(String.valueOf(patrocinio.aporte()));
				this.cuposTxt.setText(String.valueOf(patrocinio.cuposGratuitos()));
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		        String fechaPatrocinio = sdf.format(patrocinio.fechaAlta());
				this.fechaTxt.setText(fechaPatrocinio);
				this.institucionTxt.setText(patrocinio.nombreInstitucion());
				this.nivelTxt.setText(patrocinio.nivel().name());
				this.tipoRegistroTxt.setText(patrocinio.tipoRegistro());
				
				
			} else if (patrocinioSeleccionado.equals(PLACEHOLDER_PATROCINIO)) {
				resetCampos();
			}
		});
	}
	
	public void resetCampos() {
		this.aporteTxt.setText("");
		this.cuposTxt.setText("");
		this.fechaTxt.setText("");
		this.institucionTxt.setText("");
		this.nivelTxt.setText("");
	}
	
	/**
	 * Actualiza un combobox evitando que se ejecute el código de sus listeners mediante la función pasada por parámetro
	 * 
	 * @param combo
	 * @param funcionActualizacion
	 */
	public void actualizarCombobox(JComboBox<String> combo, Runnable funcionActualizacion) {
		ActionListener[] listeners = combo.getActionListeners();
		for (ActionListener listener: listeners) {
			combo.removeActionListener(listener);
		}
		// Corro la función pasada por parámetro
		funcionActualizacion.run();
		// Vuelvo a agregar los listeners una vez que se actualiza el combo
		for (ActionListener listener: listeners) {
			combo.addActionListener(listener);
		}
	}
	
}
