package presentacion;


import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;


public class ConsultaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	public ConsultaUsuario() {

		super("Consulta Usuario", true, true, true, true);
		setSize(300, 200);       
		setLocation(50, 50); 
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0,0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0,0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		String[] eventosDisponibles = {"nombre evento", "ev 1"};
		
		JPanel pnlSelector = new JPanel();
		GridBagConstraints gbc_pnlSelector = new GridBagConstraints();
		gbc_pnlSelector.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlSelector.weightx = 1.0;
		gbc_pnlSelector.insets = new Insets(8, 8, 8, 8);
		gbc_pnlSelector.gridx = 0;
		gbc_pnlSelector.gridy = 0;
		getContentPane().add(pnlSelector, gbc_pnlSelector);
		GridBagLayout gbl_pnlSelector = new GridBagLayout();
		gbl_pnlSelector.columnWidths = new int[]{38, 30, 65, 75, 0};
		gbl_pnlSelector.rowHeights = new int[]{14, 22, 23, 23, 0};
		gbl_pnlSelector.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_pnlSelector.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlSelector.setLayout(gbl_pnlSelector);
		
		JLabel lblUsuarios = new JLabel("Usuarios");
		GridBagConstraints gbc_lblUsuarios = new GridBagConstraints();
		gbc_lblUsuarios.insets = new Insets(0, 0, 0, 8);
		gbc_lblUsuarios.anchor = GridBagConstraints.EAST;
		gbc_lblUsuarios.gridx = 0;
		gbc_lblUsuarios.gridy = 0;
		pnlSelector.add(lblUsuarios, gbc_lblUsuarios);
		
		JComboBox combNick = new JComboBox();
		GridBagConstraints gbc_combNick = new GridBagConstraints();
		gbc_combNick.weightx = 1.0;
		gbc_combNick.fill = GridBagConstraints.HORIZONTAL;
		gbc_combNick.insets = new Insets(0, 0, 0, 8);
		gbc_combNick.gridx = 1;
		gbc_combNick.gridy = 0;
		pnlSelector.add(combNick, gbc_combNick);
		
		JPanel pnlBotones = new JPanel();
		pnlBotones.setPreferredSize(new java.awt.Dimension(170, 28));
		GridBagConstraints gbc_pnlBotones = new GridBagConstraints();
		gbc_pnlBotones.anchor = GridBagConstraints.EAST;
		gbc_pnlBotones.gridx = 2;
		gbc_pnlBotones.gridy = 0;
		pnlSelector.add(pnlBotones, gbc_pnlBotones);
		pnlBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 0));
		
		JButton btnBuscar = new JButton("Buscar");
		pnlBotones.add(btnBuscar);
		
		JButton btnCancelar = new JButton("Cancelar");
		pnlBotones.add(btnCancelar);
		
		JPanel pnlPerfil = new JPanel();
		GridBagConstraints gbc_pnlPerfil = new GridBagConstraints();
		gbc_pnlPerfil.weightx = 1.0;
		gbc_pnlPerfil.insets = new Insets(0, 8, 8, 8);
		gbc_pnlPerfil.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlPerfil.gridx = 0;
		gbc_pnlPerfil.gridy = 1;
		getContentPane().add(pnlPerfil, gbc_pnlPerfil);
		GridBagLayout gbl_pnlPerfil = new GridBagLayout();
		gbl_pnlPerfil.columnWidths = new int[]{0};
		gbl_pnlPerfil.rowHeights = new int[]{0};
		gbl_pnlPerfil.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_pnlPerfil.rowWeights = new double[]{Double.MIN_VALUE};
		pnlPerfil.setLayout(gbl_pnlPerfil);

	}

}



