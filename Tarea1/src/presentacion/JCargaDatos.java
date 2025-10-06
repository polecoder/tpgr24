package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.util.Arrays;

import carga.CargaDatos;

public class JCargaDatos extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	public JCargaDatos() {
		super("Carga de Datos", true, true, true, true);
		CargaDatos cargaDatos = CargaDatos.getInstancia();
		setSize(400, 300);
		setLocation(50, 50);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0,0,0,0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0,0.0,0.0,1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
		getContentPane().setLayout(gridBagLayout);

		JButton botonCargar = new JButton("Cargar");
		GridBagConstraints gbc_botonCargar = new GridBagConstraints();
		gbc_botonCargar.gridwidth = 2;
		gbc_botonCargar.insets = new Insets(0, 0, 5, 5);
		gbc_botonCargar.gridx = 1;
		gbc_botonCargar.gridy = 1;
		getContentPane().add(botonCargar, gbc_botonCargar);
		
		botonCargar.addActionListener(e -> {
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setMultiSelectionEnabled(true);
		    fileChooser.setDialogTitle("Seleccionar archivos CSV");
		    
		    // Carpeta por defecto: Descargas
		    String home = System.getProperty("user.home");
		    fileChooser.setCurrentDirectory(new File(home, "Downloads"));
		    
		    // Filtrar para solo ver archivos .CSV
		    FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
		    fileChooser.setFileFilter(filtro);
		    
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
            	File[] archivos = fileChooser.getSelectedFiles();
                String[] rutas = Arrays.stream(archivos)
                                       .map(File::getAbsolutePath)
                                       .toArray(String[]::new);

                try {
                    cargaDatos.cargarArchivos(rutas);
                    JOptionPane.showMessageDialog(
                        this,
                        "Los archivos se cargaron correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Error al cargar los archivos.\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                };
            }
		});
	}
}
