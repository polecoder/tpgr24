package presentacion;

import javax.swing.JInternalFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;

import logica.CargaDatos;

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
		
		//--------CATEGORIA----------
		JButton botonCategoria = new JButton("Cargar Categoria");
		GridBagConstraints gbc_botonCategoria = new GridBagConstraints();
		gbc_botonCategoria.gridwidth = 2;
		gbc_botonCategoria.insets = new Insets(0, 0, 5, 5);
		gbc_botonCategoria.gridx = 1;
		gbc_botonCategoria.gridy = 0;
		getContentPane().add(botonCategoria, gbc_botonCategoria);
		
		botonCategoria.addActionListener(e -> {
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Seleccionar archivo CSV - Categoria");
		    // Carpeta por defecto: Descargas
		    String home = System.getProperty("user.home");
		    fileChooser.setCurrentDirectory(new File(home, "Downloads"));
		    
		    // Filtrar para solo ver archivos .CSV
		    FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
		    fileChooser.setFileFilter(filtro);
		    
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File archivoCategorias = fileChooser.getSelectedFile();
                cargaDatos.leerCategorias(archivoCategorias.getAbsolutePath());
                cargaDatos.cargarCategorias();
            }
		});
		//--------CATEGORIA----------

		//--------EVENTO----------
		JButton botonEvento = new JButton("Cargar Evento");
		GridBagConstraints gbc_botonEvento = new GridBagConstraints();
		gbc_botonEvento.gridwidth = 2;
		gbc_botonEvento.insets = new Insets(0, 0, 5, 5);
		gbc_botonEvento.gridx = 1;
		gbc_botonEvento.gridy = 1;
		getContentPane().add(botonEvento, gbc_botonEvento);
		
		botonEvento.addActionListener(e -> {
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Seleccionar archivo CSV - Evento");
		    // Carpeta por defecto: Descargas
		    String home = System.getProperty("user.home");
		    fileChooser.setCurrentDirectory(new File(home, "Downloads"));
		    
		    // Filtrar para solo ver archivos .CSV
		    FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
		    fileChooser.setFileFilter(filtro);
		    
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File archivoEventos = fileChooser.getSelectedFile();
                cargaDatos.leerEventos(archivoEventos.getAbsolutePath());
                cargaDatos.cargarEventos();
            }
		});
		//--------EVENTO---------- 
		
		//--------EDICION----------
		JButton botonEdicion = new JButton("Cargar Edicion");
		GridBagConstraints gbc_botonEdicion = new GridBagConstraints();
		gbc_botonEdicion.gridwidth = 2;
		gbc_botonEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_botonEdicion.gridx = 1;
		gbc_botonEdicion.gridy = 2;
		getContentPane().add(botonEdicion, gbc_botonEdicion);
		
		botonEdicion.addActionListener(e -> {
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Seleccionar archivo CSV - Edicion");
		    // Carpeta por defecto: Descargas
		    String home = System.getProperty("user.home");
		    fileChooser.setCurrentDirectory(new File(home, "Downloads"));
		    
		    // Filtrar para solo ver archivos .CSV
		    FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
		    fileChooser.setFileFilter(filtro);
		    
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File archivoEdicion = fileChooser.getSelectedFile();
                cargaDatos.leerEdiciones(archivoEdicion.getAbsolutePath());
                cargaDatos.cargarEdiciones();
            }
		});
		//--------EVENTO---------- 
		
		//--------TIPOREGISTRO----------
		JButton botonTipoRegistro = new JButton("Cargar TipoRegistro");
		GridBagConstraints gbc_botonTipoRegistro = new GridBagConstraints();
		gbc_botonTipoRegistro.gridwidth = 2;
		gbc_botonTipoRegistro.insets = new Insets(0, 0, 5, 5);
		gbc_botonTipoRegistro.gridx = 1;
		gbc_botonTipoRegistro.gridy = 3;
		getContentPane().add(botonTipoRegistro, gbc_botonTipoRegistro);
		
		botonTipoRegistro.addActionListener(e -> {
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Seleccionar archivo CSV - TipoRegistro");
		    // Carpeta por defecto: Descargas
		    String home = System.getProperty("user.home");
		    fileChooser.setCurrentDirectory(new File(home, "Downloads"));
		    
		    // Filtrar para solo ver archivos .CSV
		    FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
		    fileChooser.setFileFilter(filtro);
		    
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File archivoTipoRegistro = fileChooser.getSelectedFile();
                cargaDatos.leerTipoRegistro(archivoTipoRegistro.getAbsolutePath());
                cargaDatos.cargarTipoRegistro();
            }
		});
		//--------TIPOREGISTRO----------
		
		//--------INSTITUCION----------
		JButton botonInstitucion = new JButton("Cargar Institucion");
		GridBagConstraints gbc_botonInstitucion = new GridBagConstraints();
		gbc_botonInstitucion.gridwidth = 2;
		gbc_botonInstitucion.insets = new Insets(0, 0, 5, 5);
		gbc_botonInstitucion.gridx = 1;
		gbc_botonInstitucion.gridy = 4;
		getContentPane().add(botonInstitucion, gbc_botonInstitucion);
		
		botonInstitucion.addActionListener(e -> {
		    JFileChooser fileChooser = new JFileChooser();
		    fileChooser.setDialogTitle("Seleccionar archivo CSV - Institucion");
		    // Carpeta por defecto: Descargas
		    String home = System.getProperty("user.home");
		    fileChooser.setCurrentDirectory(new File(home, "Downloads"));
		    
		    // Filtrar para solo ver archivos .CSV
		    FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
		    fileChooser.setFileFilter(filtro);
		    
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File archivoInstitucion = fileChooser.getSelectedFile();
                cargaDatos.leerInstitucion(archivoInstitucion.getAbsolutePath());
                cargaDatos.cargarInstitucion();
            }
		});
		//--------INSTITUCION---------- 
	}
}
