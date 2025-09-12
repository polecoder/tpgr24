package presentacion;

import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Date;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;

import logica.Fabrica;

import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

public class AltaEvento extends JInternalFrame {

	Fabrica fabrica = Fabrica.getInstancia();
	
	private static final long serialVersionUID = 1L;
	private JTextField nombreTextField;
	private JTextArea descripcionTextArea;
	private JTextField siglaTextField;
	private JSpinner fechaSpinner;
	JList<String> categoriasList;

	public AltaEvento() {
		super("Alta de Evento", true, true, true, true);
		setSize(400, 300);
		setMinimumSize(new Dimension(400, 300));
		setMaximumSize(new Dimension(800, 600)); 
		setLocation(50, 50);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0,0,0,0};
		gridBagLayout.rowHeights = new int[] {0,0,0,0,0,0,0,0,0};
		gridBagLayout.columnWeights = new double[]{1.0,0.0,0.0,1.0};
		gridBagLayout.rowWeights = new double[]{1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,1.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		nombreTextField = new JTextField();
		GridBagConstraints gbc_nombreTextField = new GridBagConstraints();
		gbc_nombreTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nombreTextField.anchor = GridBagConstraints.WEST;
		gbc_nombreTextField.insets = new Insets(0, 0, 5, 5);
		gbc_nombreTextField.gridx = 2;
		gbc_nombreTextField.gridy = 1;
		getContentPane().add(nombreTextField, gbc_nombreTextField);
		
		JLabel lblNewLabel_1 = new JLabel("Descripción:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);
		JScrollPane descripcionScrollPane = new JScrollPane();
		descripcionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_descripcionScrollPane = new GridBagConstraints();
		gbc_descripcionScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_descripcionScrollPane.fill = GridBagConstraints.BOTH;
		gbc_descripcionScrollPane.gridx = 2;
		gbc_descripcionScrollPane.gridy = 2;
		getContentPane().add(descripcionScrollPane, gbc_descripcionScrollPane);
		descripcionTextArea = new JTextArea();
		descripcionTextArea.setLineWrap(true);
		descripcionTextArea.setRows(2);
		descripcionScrollPane.setViewportView(descripcionTextArea);
		
		JLabel lblNewLabel_2 = new JLabel("Alta:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		fechaSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor editor = new JSpinner.DateEditor(fechaSpinner, "dd/MM/yyyy");
		fechaSpinner.setEditor(editor);
		GridBagConstraints gbc_fechaSpinner = new GridBagConstraints();
		gbc_fechaSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_fechaSpinner.anchor = GridBagConstraints.WEST;
		gbc_fechaSpinner.insets = new Insets(0, 0, 5, 5);
		gbc_fechaSpinner.gridx = 2;
		gbc_fechaSpinner.gridy = 3;
		getContentPane().add(fechaSpinner, gbc_fechaSpinner);
		
		JLabel lblNewLabel_3 = new JLabel("Sigla:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 4;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		siglaTextField = new JTextField();
		GridBagConstraints gbc_siglaTextField = new GridBagConstraints();
		gbc_siglaTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_siglaTextField.anchor = GridBagConstraints.WEST;
		gbc_siglaTextField.insets = new Insets(0, 0, 5, 5);
		gbc_siglaTextField.gridx = 2;
		gbc_siglaTextField.gridy = 4;
		getContentPane().add(siglaTextField, gbc_siglaTextField);
		
		JLabel lblNewLabel_4 = new JLabel("Categorías:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 5;
		getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);
		JScrollPane categoriasScrollPane = new JScrollPane();
		categoriasScrollPane.setPreferredSize(new Dimension(150, 80));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 5;
		gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(categoriasScrollPane, gbc_scrollPane);
		
		List<String> listaCategorias = fabrica.getControladorEvento().getListaCategorias();
		
		categoriasList = new JList<>(listaCategorias.toArray(new String[0]));
		categoriasScrollPane.setViewportView(categoriasList);
		categoriasList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 2;
		gbc_panel.gridy = 6;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0,0,0};
		gbl_panel.rowHeights = new int[]{0};
		gbl_panel.columnWeights = new double[]{1.0,1.0,1.0};
		gbl_panel.rowWeights = new double[]{1.0};
		panel.setLayout(gbl_panel);
		
		JButton botonAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		panel.add(botonAceptar, gbc_btnNewButton);
		
		JButton botonCancelar = new JButton("Cancelar");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 0;
		panel.add(botonCancelar, gbc_btnNewButton_1);
		
		JButton botonReset = new JButton("Reset");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.gridx = 2;
		gbc_btnNewButton_2.gridy = 0;
		panel.add(botonReset, gbc_btnNewButton_2);
		
		/**
		 * ACEPTAR
		 */
		botonAceptar.addActionListener(e->{
			String nombre = nombreTextField.getText().trim();
			String descripcion = descripcionTextArea.getText().trim();
			Date fechaAlta = (Date) fechaSpinner.getValue();
			String sigla = siglaTextField.getText().trim();
			List<String> categorias = categoriasList.getSelectedValuesList();
			
			if (nombre.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El campo Nombre es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
			}
			if (descripcion.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El campo Descripcion es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
			}
			// fechaAlta no necesita validación pues necesariamente se ingresará una fecha.
			if (sigla.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El campo Sigla es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
			}
			if (categorias.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Seleccionar por lo menos una categoría es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
		        return;
			}
			if (fabrica.getControladorEvento().existeEvento(nombre)) {
				int opcion = JOptionPane.showOptionDialog(
				        this,
				        "Ya existe un evento con este nombre. ¿Desea volver a intentar?",
				        "Advertencia",
				        JOptionPane.YES_NO_OPTION,
				        JOptionPane.QUESTION_MESSAGE,
				        null,
				        new Object[]{"Aceptar", "Cancelar"},
				        "Aceptar"
			    );
			    if (opcion == JOptionPane.YES_OPTION) {
			        resetCampos();
			    } else if (opcion == JOptionPane.NO_OPTION) {
			        this.dispose();
			    }
			    return;
			}
			// Si pasa todos los checks, se da de Alta finalmente
			fabrica.getControladorEvento().altaEvento(nombre, descripcion, fechaAlta, sigla, categorias);
			JOptionPane.showMessageDialog(this, "El evento se ha creado exitosamente.", "Operación exitosa", JOptionPane.INFORMATION_MESSAGE);
			resetCampos();
		});
		
		/**
		 * CANCELAR
		 */
		botonCancelar.addActionListener(e -> {
			this.dispose();
		});
		
		/**
		 * RESET
		 */
		botonReset.addActionListener(e->{
			resetCampos();
		});
	}

	private void resetCampos() {
		nombreTextField.setText("");
        descripcionTextArea.setText("");
        siglaTextField.setText("");
        fechaSpinner.setValue(new Date());
        categoriasList.clearSelection();
	}
	
}
