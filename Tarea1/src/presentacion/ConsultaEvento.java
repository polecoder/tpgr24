package presentacion;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import excepciones.EventoNoExiste;
import logica.Fabrica;
import logica.IControladorEventos;
import datatypes.DTEvento;

public class ConsultaEvento extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private Fabrica fabrica= Fabrica.getInstancia();
	private IControladorEventos IContEvento= fabrica.getControladorEvento();
	public ConsultaEvento(JDesktopPane desktopPane) {
	    super("Consulta Evento", true, true, true, true);
	    setSize(450, 350);       
		setLocation(50, 50); 
	
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0,0};
		gridBagLayout.rowHeights = new int[]{0,0,0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0,0.0,0.0,0.0,0.0,0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0,0.0,0.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lbl_Eventos = new JLabel("Eventos Disponibles:");
		GridBagConstraints gbc_lbl_Eventos = new GridBagConstraints();
		gbc_lbl_Eventos.insets = new Insets(10, 10, 0, 5);
		gbc_lbl_Eventos.anchor = GridBagConstraints.EAST;
		gbc_lbl_Eventos.gridx = 1;
		gbc_lbl_Eventos.gridy = 0;
		getContentPane().add(lbl_Eventos, gbc_lbl_Eventos);
		
		JComboBox<String> comboEventos = new JComboBox<>();
		
		Set<String> eventosDisponibles =IContEvento.nombresEventos();
		comboEventos.addItem("--Seleccionar un Evento--");
		 if (eventosDisponibles != null) {
		        for (String evento : eventosDisponibles) {
		            comboEventos.addItem(evento);
		        }
		    }
		 
		GridBagConstraints gbc_comboEventos = new GridBagConstraints();
		gbc_comboEventos.insets= new Insets(10,0,0,10);
		gbc_comboEventos.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEventos.gridx = 2;
		gbc_comboEventos.gridy = 0;
		gbc_comboEventos.gridwidth = 2;
		getContentPane().add(comboEventos, gbc_comboEventos);
		
		JPanel panelDetalles = new JPanel(new GridBagLayout());
		GridBagConstraints gbc_panelDetalles = new GridBagConstraints();
		gbc_panelDetalles.gridx = 0;
		gbc_panelDetalles.gridy = 1;
		gbc_panelDetalles.gridwidth = 4;
		gbc_panelDetalles.fill = GridBagConstraints.BOTH;
		gbc_panelDetalles.weightx = 1.0;
		gbc_panelDetalles.weighty = 1.0;
		getContentPane().add(panelDetalles, gbc_panelDetalles);

		
		comboEventos.addActionListener(e -> {
		    String eventoSeleccionado = (String) comboEventos.getSelectedItem();
		    
		    if (eventoSeleccionado != null &&eventoSeleccionado!="--Seleccionar un Evento--") {
		    	
		    DTEvento detalleEvento = IContEvento.getDTEvento(eventoSeleccionado);

		    panelDetalles.removeAll(); 
		    
		   
		    
		    JLabel lbl_Nombre = new JLabel("Nombre:");
		    GridBagConstraints gbc_lbl_Nombre = new GridBagConstraints();
		    gbc_lbl_Nombre.insets = new Insets(0, 0, 0, 5);
		    gbc_lbl_Nombre.anchor = GridBagConstraints.EAST;
		    gbc_lbl_Nombre.gridx = 1;
		    gbc_lbl_Nombre.gridy = 1;
		    panelDetalles.add(lbl_Nombre, gbc_lbl_Nombre); 

		    JLabel lbl_nombre = new JLabel(detalleEvento.getNombre());
		    GridBagConstraints gbc_lbl_nombre = new GridBagConstraints();
		    gbc_lbl_nombre.insets = new Insets(0, 0, 0, 5);
		    gbc_lbl_nombre.anchor = GridBagConstraints.WEST;
		    gbc_lbl_nombre.gridx = 2;
		    gbc_lbl_nombre.gridy = 1;
		    panelDetalles.add(lbl_nombre, gbc_lbl_nombre);

		    JLabel lbl_Sigla = new JLabel("Sigla:");
		    GridBagConstraints gbc_lbl_Sigla = new GridBagConstraints();
		    gbc_lbl_Sigla.insets = new Insets(0, 0, 0, 5);
		    gbc_lbl_Sigla.anchor = GridBagConstraints.EAST;
		    gbc_lbl_Sigla.gridx = 1;
		    gbc_lbl_Sigla.gridy = 2;
		    panelDetalles.add(lbl_Sigla, gbc_lbl_Sigla);

		    JLabel lbl_sigla = new JLabel(detalleEvento.getSigla());
		    GridBagConstraints gbc_lbl_sigla = new GridBagConstraints();
		    gbc_lbl_sigla.insets = new Insets(0, 0, 0, 5);
		    gbc_lbl_sigla.anchor = GridBagConstraints.WEST;
		    gbc_lbl_sigla.gridx = 2;
		    gbc_lbl_sigla.gridy = 2;
		    panelDetalles.add(lbl_sigla, gbc_lbl_sigla);

		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaFormateada = formatter.format(detalleEvento.getFechaAlta());

		    JLabel lbl_FechaAlta = new JLabel("Fecha de Alta:");
		    GridBagConstraints gbc_lbl_FechaAlta = new GridBagConstraints();
		    gbc_lbl_FechaAlta.insets = new Insets(0, 0, 0, 5);
		    gbc_lbl_FechaAlta.anchor = GridBagConstraints.EAST;
		    gbc_lbl_FechaAlta.gridx = 1;
		    gbc_lbl_FechaAlta.gridy = 3;
		    panelDetalles.add(lbl_FechaAlta, gbc_lbl_FechaAlta);

		    JLabel lbl_fechaAlta = new JLabel(fechaFormateada);
		    GridBagConstraints gbc_lbl_fechaAlta = new GridBagConstraints();
		    gbc_lbl_fechaAlta.insets = new Insets(0, 0, 0, 5);
		    gbc_lbl_fechaAlta.anchor = GridBagConstraints.WEST;
		    gbc_lbl_fechaAlta.gridx = 2;
		    gbc_lbl_fechaAlta.gridy = 3;
		    panelDetalles.add(lbl_fechaAlta, gbc_lbl_fechaAlta);

		    JLabel lbl_Descripcion = new JLabel("Descripción:");
		    GridBagConstraints gbc_lbl_Descripcion = new GridBagConstraints();
		    gbc_lbl_Descripcion.insets = new Insets(0, 0, 0, 5);
		    gbc_lbl_Descripcion.anchor = GridBagConstraints.EAST;
		    gbc_lbl_Descripcion.gridx = 1;
		    gbc_lbl_Descripcion.gridy = 4;
		    panelDetalles.add(lbl_Descripcion, gbc_lbl_Descripcion);

		    JLabel lbl_descripcion = new JLabel(detalleEvento.getDescripcion());
		    GridBagConstraints gbc_lbl_descripcion = new GridBagConstraints();
		    gbc_lbl_descripcion.insets = new Insets(0, 0, 0, 5);
		    gbc_lbl_descripcion.anchor = GridBagConstraints.WEST;
		    gbc_lbl_descripcion.gridx = 2;
		    gbc_lbl_descripcion.gridy = 4;
		    panelDetalles.add(lbl_descripcion, gbc_lbl_descripcion);

		    ArrayList<String> datos = new ArrayList<>();
		    Set<String> categorias = IContEvento.nombresCategorias(detalleEvento.getNombre());
		    for (String categoria : categorias) {
		        datos.add(categoria);
		    }

		    JLabel lbl_Categorias = new JLabel("Categorías:");
		    GridBagConstraints gbc_lbl_Categorias = new GridBagConstraints();
		    gbc_lbl_Categorias.insets = new Insets(0, 0, 0, 5);
		    gbc_lbl_Categorias.anchor = GridBagConstraints.EAST;
		    gbc_lbl_Categorias.gridx = 1;
		    gbc_lbl_Categorias.gridy = 5;
		    panelDetalles.add(lbl_Categorias, gbc_lbl_Categorias);

		    JList<String> lista = new JList<>(datos.toArray(new String[0]));
		    JScrollPane scrollPane_Categorias = new JScrollPane(lista);
		    scrollPane_Categorias.setPreferredSize(new Dimension(200, 100));
		    GridBagConstraints gbc_scrollPane_Categorias = new GridBagConstraints();
		    gbc_scrollPane_Categorias.insets = new Insets(5, 5, 5, 5);
		    gbc_scrollPane_Categorias.gridx = 2;
		    gbc_scrollPane_Categorias.gridy = 5;
		    gbc_scrollPane_Categorias.gridwidth = 2;
		    gbc_scrollPane_Categorias.fill = GridBagConstraints.BOTH;
		    gbc_scrollPane_Categorias.weightx = 1.0;
		    gbc_scrollPane_Categorias.weighty = 1.0;
		    panelDetalles.add(scrollPane_Categorias, gbc_scrollPane_Categorias);

		    JLabel lbl_Ediciones = new JLabel("Ediciones Disponibles:");
		    GridBagConstraints gbc_lbl_Ediciones = new GridBagConstraints();
		    gbc_lbl_Ediciones.insets = new Insets(10, 5, 0, 5);
		    gbc_lbl_Ediciones.anchor = GridBagConstraints.EAST;
		    gbc_lbl_Ediciones.gridx = 1;
		    gbc_lbl_Ediciones.gridy = 6;
		    panelDetalles.add(lbl_Ediciones, gbc_lbl_Ediciones);
		    
		    JComboBox<String> comboEdiciones = new JComboBox<>();
			
			Set<String> edicionesDisponibles;
			try { edicionesDisponibles =IContEvento.nombresEdicionesEvento(detalleEvento.getNombre());
			} catch (EventoNoExiste ex){
				edicionesDisponibles = null;
			}
			 if (edicionesDisponibles != null){
			        for (String edicion : edicionesDisponibles) {
			            comboEdiciones.addItem(edicion);
			   }
			}
			 
			GridBagConstraints gbc_comboEdiciones = new GridBagConstraints();
			gbc_comboEdiciones.insets= new Insets(10,0,0,10);
			gbc_comboEdiciones.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboEdiciones.gridx = 2;
			gbc_comboEdiciones.gridy = 6;
			gbc_comboEdiciones.gridwidth = 2;
			panelDetalles.add(comboEdiciones, gbc_comboEdiciones);
		    
			comboEdiciones.addActionListener(o -> {
			    String edicionSeleccionada = (String) comboEdiciones.getSelectedItem();
			    ConsultaEdicionEvento consultaEdicion = new ConsultaEdicionEvento(detalleEvento.getNombre(),edicionSeleccionada, desktopPane);
			    desktopPane.add(consultaEdicion);
			    consultaEdicion.setVisible(true);
			    try {
			        consultaEdicion.setSelected(true);
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
			} ); 
		    panelDetalles.revalidate();
		    panelDetalles.repaint();
		  }else {
			  panelDetalles.removeAll();
			  panelDetalles.revalidate();
			  panelDetalles.repaint();

		    }
		});

		JButton Button_Cancelar = new JButton("Cancelar");
		
		GridBagConstraints gbc_Button_Cancelar = new GridBagConstraints();
		gbc_Button_Cancelar.insets = new Insets(5, 0, 10, 10);
		gbc_Button_Cancelar.gridx = 3;
		gbc_Button_Cancelar.gridy = 9;
		gbc_Button_Cancelar.anchor=GridBagConstraints.EAST;;
		getContentPane().add(Button_Cancelar, gbc_Button_Cancelar);
		
		Button_Cancelar.addActionListener(e -> {
		    ConsultaEvento.this.dispose();
		});


	}

}
