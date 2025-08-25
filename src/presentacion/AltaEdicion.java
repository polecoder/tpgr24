package presentacion;


import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class AltaEdicion extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField_Nombre;
	private JTextField textField_Sigla;
	private JTextField textField_Cuidad;
	private JTextField textField_Pais;
	private JTextField textField_FechaInicio;
	private JTextField textField_FechaFinal;
	private JTextField textField_FechaAlta;


	public AltaEdicion() {
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

	    String[] eventosExistentes = {"evento 1", "evento 2"};
	    JComboBox<String> comboEventos = new JComboBox<>(eventosExistentes);
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

	    String[] organizadores = {"pablo", "juana"};
	    JComboBox<String> comboOrganizadores = new JComboBox<>(organizadores);
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

	    textField_Cuidad = new JTextField();
	    GridBagConstraints gbc_textField_Ciudad = new GridBagConstraints();
	    gbc_textField_Ciudad.insets = new Insets(5, 5, 5, 5);
	    gbc_textField_Ciudad.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textField_Ciudad.gridx = 1;
	    gbc_textField_Ciudad.gridy = 4;
	    gbc_textField_Ciudad.gridwidth = 2;
	    getContentPane().add(textField_Cuidad, gbc_textField_Ciudad);
	    textField_Cuidad.setColumns(10);

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

	    textField_FechaInicio = new JTextField();
	    GridBagConstraints gbc_textField_FechaInicio = new GridBagConstraints();
	    gbc_textField_FechaInicio.insets = new Insets(5, 5, 5, 3);
	    gbc_textField_FechaInicio.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textField_FechaInicio.gridx = 1;
	    gbc_textField_FechaInicio.gridy = 6;
	    getContentPane().add(textField_FechaInicio, gbc_textField_FechaInicio);
	    textField_FechaInicio.setColumns(10);

	    JLabel lbl_FechaFin = new JLabel("Fecha de Fin:");
	    GridBagConstraints gbc_lbl_FechaFin = new GridBagConstraints();
	    gbc_lbl_FechaFin.anchor = GridBagConstraints.EAST;
	    gbc_lbl_FechaFin.insets = new Insets(5, 3, 5, 5);
	    gbc_lbl_FechaFin.gridx = 2;
	    gbc_lbl_FechaFin.gridy = 6;
	    getContentPane().add(lbl_FechaFin, gbc_lbl_FechaFin);

	    textField_FechaFinal = new JTextField();
	    GridBagConstraints gbc_textField_FechaFinal = new GridBagConstraints();
	    gbc_textField_FechaFinal.insets = new Insets(5, 5, 5, 5);
	    gbc_textField_FechaFinal.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textField_FechaFinal.gridx = 3;
	    gbc_textField_FechaFinal.gridy = 6;
	    getContentPane().add(textField_FechaFinal, gbc_textField_FechaFinal);
	    textField_FechaFinal.setColumns(10);

	    JLabel lbl_FechaAlta = new JLabel("Fecha de Alta:");
	    GridBagConstraints gbc_lbl_FechaAlta = new GridBagConstraints();
	    gbc_lbl_FechaAlta.anchor = GridBagConstraints.EAST;
	    gbc_lbl_FechaAlta.insets = new Insets(5, 5, 5, 5);
	    gbc_lbl_FechaAlta.gridx = 0;
	    gbc_lbl_FechaAlta.gridy = 7;
	    getContentPane().add(lbl_FechaAlta, gbc_lbl_FechaAlta);

	    JTextField textField_FechaAlta = new JTextField();
	    GridBagConstraints gbc_textField_FechaAlta = new GridBagConstraints();
	    gbc_textField_FechaAlta.insets = new Insets(5, 5, 5, 5);
	    gbc_textField_FechaAlta.fill = GridBagConstraints.HORIZONTAL;
	    gbc_textField_FechaAlta.gridx = 1;
	    gbc_textField_FechaAlta.gridy = 7;
	    gbc_textField_FechaAlta.gridwidth = 2;
	    getContentPane().add(textField_FechaAlta, gbc_textField_FechaAlta);
	    textField_FechaAlta.setColumns(10);

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
	}
}
