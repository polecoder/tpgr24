package presentacion;

import java.awt.Dimension;

import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class ConsultaPatrocinio extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField institucionTxt;
	private JTextField codigoTxt;
	private JTextField fechaTxt;
	private JTextField aporteTxt;
	private JTextField nivelTxt;
	private JTextField tipoRegistroTxt;
	private JTextField cuposTxt;

	
	
	public ConsultaPatrocinio() {
		super("Consulta de Patrocinio", true, true, true, true);
		setSize(400, 300);
		setMinimumSize(new Dimension(400, 300));
		setMaximumSize(new Dimension(800, 600)); 
		setLocation(50, 50);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0,0,0,0};
		gridBagLayout.rowHeights = new int[]{0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0};
		gridBagLayout.columnWeights = new double[]{1.0,0.0,1.0,1.0};
		gridBagLayout.rowWeights = new double[]{1.0,0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel eventoLbl = new JLabel("Evento:");
		GridBagConstraints gbc_eventoLbl = new GridBagConstraints();
		gbc_eventoLbl.anchor = GridBagConstraints.EAST;
		gbc_eventoLbl.insets = new Insets(0, 0, 5, 5);
		gbc_eventoLbl.gridx = 1;
		gbc_eventoLbl.gridy = 1;
		getContentPane().add(eventoLbl, gbc_eventoLbl);
		
		JComboBox comboEvento = new JComboBox();
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
		
		JComboBox comboEdicion = new JComboBox();
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
		
		JComboBox comboPatrocinio = new JComboBox();
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
		
		JLabel codigoLbl = new JLabel("Código:");
		GridBagConstraints gbc_codigoLbl = new GridBagConstraints();
		gbc_codigoLbl.anchor = GridBagConstraints.EAST;
		gbc_codigoLbl.insets = new Insets(0, 0, 5, 5);
		gbc_codigoLbl.gridx = 1;
		gbc_codigoLbl.gridy = 5;
		getContentPane().add(codigoLbl, gbc_codigoLbl);
		
		codigoTxt = new JTextField();
		codigoTxt.setEditable(false);
		GridBagConstraints gbc_codigoTxt = new GridBagConstraints();
		gbc_codigoTxt.insets = new Insets(0, 0, 5, 5);
		gbc_codigoTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_codigoTxt.gridx = 2;
		gbc_codigoTxt.gridy = 5;
		getContentPane().add(codigoTxt, gbc_codigoTxt);
		codigoTxt.setColumns(10);
		
		JLabel fechaLbl = new JLabel("Fecha:");
		GridBagConstraints gbc_fechaLbl = new GridBagConstraints();
		gbc_fechaLbl.anchor = GridBagConstraints.EAST;
		gbc_fechaLbl.insets = new Insets(0, 0, 5, 5);
		gbc_fechaLbl.gridx = 1;
		gbc_fechaLbl.gridy = 6;
		getContentPane().add(fechaLbl, gbc_fechaLbl);
		
		fechaTxt = new JTextField();
		fechaTxt.setEditable(false);
		GridBagConstraints gbc_fechaTxt = new GridBagConstraints();
		gbc_fechaTxt.insets = new Insets(0, 0, 5, 5);
		gbc_fechaTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaTxt.gridx = 2;
		gbc_fechaTxt.gridy = 6;
		getContentPane().add(fechaTxt, gbc_fechaTxt);
		fechaTxt.setColumns(10);
		
		JLabel aporteLbl = new JLabel("Aporte");
		GridBagConstraints gbc_aporteLbl = new GridBagConstraints();
		gbc_aporteLbl.anchor = GridBagConstraints.EAST;
		gbc_aporteLbl.insets = new Insets(0, 0, 5, 5);
		gbc_aporteLbl.gridx = 1;
		gbc_aporteLbl.gridy = 7;
		getContentPane().add(aporteLbl, gbc_aporteLbl);
		
		aporteTxt = new JTextField();
		aporteTxt.setEditable(false);
		GridBagConstraints gbc_aporteTxt = new GridBagConstraints();
		gbc_aporteTxt.insets = new Insets(0, 0, 5, 5);
		gbc_aporteTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_aporteTxt.gridx = 2;
		gbc_aporteTxt.gridy = 7;
		getContentPane().add(aporteTxt, gbc_aporteTxt);
		aporteTxt.setColumns(10);
		
		JLabel nivelLbl = new JLabel("Nivel:");
		GridBagConstraints gbc_nivelLbl = new GridBagConstraints();
		gbc_nivelLbl.anchor = GridBagConstraints.EAST;
		gbc_nivelLbl.insets = new Insets(0, 0, 5, 5);
		gbc_nivelLbl.gridx = 1;
		gbc_nivelLbl.gridy = 8;
		getContentPane().add(nivelLbl, gbc_nivelLbl);
		
		nivelTxt = new JTextField();
		nivelTxt.setEditable(false);
		GridBagConstraints gbc_nivelTxt = new GridBagConstraints();
		gbc_nivelTxt.insets = new Insets(0, 0, 5, 5);
		gbc_nivelTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_nivelTxt.gridx = 2;
		gbc_nivelTxt.gridy = 8;
		getContentPane().add(nivelTxt, gbc_nivelTxt);
		nivelTxt.setColumns(10);
		
		JLabel tipoRegistroLbl = new JLabel("Tipo de registro:");
		GridBagConstraints gbc_tipoRegistroLbl = new GridBagConstraints();
		gbc_tipoRegistroLbl.anchor = GridBagConstraints.EAST;
		gbc_tipoRegistroLbl.insets = new Insets(0, 0, 5, 5);
		gbc_tipoRegistroLbl.gridx = 1;
		gbc_tipoRegistroLbl.gridy = 9;
		getContentPane().add(tipoRegistroLbl, gbc_tipoRegistroLbl);
		
		tipoRegistroTxt = new JTextField();
		tipoRegistroTxt.setEditable(false);
		GridBagConstraints gbc_tipoRegistroTxt = new GridBagConstraints();
		gbc_tipoRegistroTxt.insets = new Insets(0, 0, 5, 5);
		gbc_tipoRegistroTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_tipoRegistroTxt.gridx = 2;
		gbc_tipoRegistroTxt.gridy = 9;
		getContentPane().add(tipoRegistroTxt, gbc_tipoRegistroTxt);
		tipoRegistroTxt.setColumns(10);
		
		JLabel cuposLbl = new JLabel("Cupos gratuitos:");
		GridBagConstraints gbc_cuposLbl = new GridBagConstraints();
		gbc_cuposLbl.anchor = GridBagConstraints.EAST;
		gbc_cuposLbl.insets = new Insets(0, 0, 5, 5);
		gbc_cuposLbl.gridx = 1;
		gbc_cuposLbl.gridy = 10;
		getContentPane().add(cuposLbl, gbc_cuposLbl);
		
		cuposTxt = new JTextField();
		cuposTxt.setEditable(false);
		GridBagConstraints gbc_cuposTxt = new GridBagConstraints();
		gbc_cuposTxt.insets = new Insets(0, 0, 5, 5);
		gbc_cuposTxt.fill = GridBagConstraints.HORIZONTAL;
		gbc_cuposTxt.gridx = 2;
		gbc_cuposTxt.gridy = 10;
		getContentPane().add(cuposTxt, gbc_cuposTxt);
		cuposTxt.setColumns(10);
	}
}
