package presentacion;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class ConsultaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	public ConsultaUsuario() {
		super("Consulta de Usuario", true, true, true, true);
		setSize(300, 200);       
		setLocation(50, 50);   
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0,0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0,0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lbl_Eventos = new JLabel("Eventos Disponibles:");
		GridBagConstraints gbc_lbl_Eventos = new GridBagConstraints();
		gbc_lbl_Eventos.insets = new Insets(0, 0, 0, 5);
		gbc_lbl_Eventos.anchor = GridBagConstraints.EAST;
		gbc_lbl_Eventos.gridx = 1;
		gbc_lbl_Eventos.gridy = 0;
		getContentPane().add(lbl_Eventos, gbc_lbl_Eventos);
		
		String[] eventosDisponibles = {"nombre evento", "ev 1"};
		JComboBox<String> comboEventos = new JComboBox<>(eventosDisponibles);
		GridBagConstraints gbc_comboEventos = new GridBagConstraints();
		gbc_comboEventos.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEventos.gridx = 2;
		gbc_comboEventos.gridy = 0;
		gbc_comboEventos.gridwidth = 2;
		getContentPane().add(comboEventos, gbc_comboEventos);

	}

}
