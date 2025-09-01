	package presentacion;
	
	import java.awt.GridBagConstraints;
	import java.awt.GridBagLayout;
	import java.awt.Insets;
	import java.util.Date;
	import java.util.Set;
	
	import javax.swing.JComboBox;
	import javax.swing.JInternalFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JTextField;

import excepciones.EdicionNoExiste;
import excepciones.EventoNoExiste;
import excepciones.TipoRegistroNoExiste;

import javax.swing.JButton;

import logica.ENivelPatrocinio;
import logica.Fabrica;
	import logica.IControladorEventos;
	import logica.IControladorInstitucion;
import logica.datatypes.DTTipoRegistro;
	
	public class AltaPatrocinio extends JInternalFrame { 
	    private Fabrica fabrica = Fabrica.getInstancia();
	    private IControladorEventos IContEvento = fabrica.getControladorEvento();
	    private IControladorInstitucion IContInstituciones = fabrica.getControladorInstitucion();
	    private static final long serialVersionUID = 1L;
	
	    public AltaPatrocinio() {
	        super("Alta Patrocinio", true, true, true, true);
	        setSize(470, 350);
	        setLocation(40, 25);
	
	        GridBagLayout layout = new GridBagLayout();
	        getContentPane().setLayout(layout);
	
	        JPanel panelFormulario = new JPanel(new GridBagLayout());
	        GridBagConstraints gbc_panelFormulario = new GridBagConstraints();
	        gbc_panelFormulario.gridx = 0;
	        gbc_panelFormulario.gridy = 0;
	        gbc_panelFormulario.gridwidth = 4;
	        gbc_panelFormulario.fill = GridBagConstraints.BOTH;
	        getContentPane().add(panelFormulario, gbc_panelFormulario); 
	
	        JLabel lblEvento = new JLabel("Evento:");
	        GridBagConstraints gbc_lblEvento = new GridBagConstraints();
	        gbc_lblEvento.gridx = 0;
	        gbc_lblEvento.gridy = 0;
	        gbc_lblEvento.insets = new Insets(5, 5, 5, 5);
	        gbc_lblEvento.anchor = GridBagConstraints.WEST;
	        panelFormulario.add(lblEvento, gbc_lblEvento);
	
	        JComboBox<String> comboEvento = new JComboBox<>();
	        GridBagConstraints gbc_comboEvento = new GridBagConstraints();
	        gbc_comboEvento.gridx = 1;
	        gbc_comboEvento.gridy = 0;
	        gbc_comboEvento.fill = GridBagConstraints.HORIZONTAL;
	        gbc_comboEvento.insets = new Insets(5, 5, 5, 5);
	        panelFormulario.add(comboEvento, gbc_comboEvento);
	        
	        Set<String> eventosDisponibles = IContEvento.nombresEventos();
	        if (eventosDisponibles != null) { 
	        	for (String evento : eventosDisponibles) { 
	        		comboEvento.addItem(evento); 
	        	} 
	        	
	       }
	
	        JLabel lblEdicion = new JLabel("Edición:");
	        GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
	        gbc_lblEdicion.gridx = 0;
	        gbc_lblEdicion.gridy = 1;
	        gbc_lblEdicion.insets = new Insets(5, 5, 5, 5);
	        gbc_lblEdicion.anchor = GridBagConstraints.WEST;
	        panelFormulario.add(lblEdicion, gbc_lblEdicion);
	
	        JComboBox<String> comboEdicion = new JComboBox<>();
	        GridBagConstraints gbc_comboEdicion = new GridBagConstraints();
	        gbc_comboEdicion.gridx = 1;
	        gbc_comboEdicion.gridy = 1;
	        gbc_comboEdicion.fill = GridBagConstraints.HORIZONTAL;
	        gbc_comboEdicion.insets = new Insets(5, 5, 5, 5);
	        panelFormulario.add(comboEdicion, gbc_comboEdicion);
	
	        JLabel lblTipo = new JLabel("Tipo de Registro:");
	        GridBagConstraints gbc_lblTipo = new GridBagConstraints();
	        gbc_lblTipo.gridx = 0;
	        gbc_lblTipo.gridy = 2;
	        gbc_lblTipo.insets = new Insets(5, 5, 5, 5);
	        gbc_lblTipo.anchor = GridBagConstraints.WEST;
	        panelFormulario.add(lblTipo, gbc_lblTipo);
	
	        JComboBox<String> comboTipo = new JComboBox<>();
	        GridBagConstraints gbc_comboTipo = new GridBagConstraints();
	        gbc_comboTipo.gridx = 1;
	        gbc_comboTipo.gridy = 2;
	        gbc_comboTipo.fill = GridBagConstraints.HORIZONTAL;
	        gbc_comboTipo.insets = new Insets(5, 5, 5, 5);
	        panelFormulario.add(comboTipo, gbc_comboTipo);
	
	        JLabel lblInstitucion = new JLabel("Institución:");
	        GridBagConstraints gbc_lblInstitucion = new GridBagConstraints();
	        gbc_lblInstitucion.gridx = 0;
	        gbc_lblInstitucion.gridy = 3;
	        gbc_lblInstitucion.insets = new Insets(5, 5, 5, 5);
	        gbc_lblInstitucion.anchor = GridBagConstraints.WEST;
	        panelFormulario.add(lblInstitucion, gbc_lblInstitucion);
	
	        JComboBox<String> comboInstitucion = new JComboBox<>();
	        GridBagConstraints gbc_comboInstitucion = new GridBagConstraints();
	        gbc_comboInstitucion.gridx = 1;
	        gbc_comboInstitucion.gridy = 3;
	        gbc_comboInstitucion.fill = GridBagConstraints.HORIZONTAL;
	        gbc_comboInstitucion.insets = new Insets(5, 5, 5, 5);
	        panelFormulario.add(comboInstitucion, gbc_comboInstitucion);
	        
	        Set<String> instituciones = IContInstituciones.nombresInstituciones();
	        if (instituciones != null) { 
	        	for (String inst : instituciones) { 
	        		comboInstitucion.addItem(inst); } 
	        	} 
	
	        JLabel lblNivel = new JLabel("Nivel de Patrocinio:");
	        GridBagConstraints gbc_lblNivel = new GridBagConstraints();
	        gbc_lblNivel.gridx = 0;
	        gbc_lblNivel.gridy = 4;
	        gbc_lblNivel.insets = new Insets(5, 5, 5, 5);
	        gbc_lblNivel.anchor = GridBagConstraints.WEST;
	        panelFormulario.add(lblNivel, gbc_lblNivel);
	
	        JComboBox<String> comboNivel = new JComboBox<>(new String[]{"Platino", "Oro", "Plata", "Bronce"});
	        GridBagConstraints gbc_comboNivel = new GridBagConstraints();
	        gbc_comboNivel.gridx = 1;
	        gbc_comboNivel.gridy = 4;
	        gbc_comboNivel.fill = GridBagConstraints.HORIZONTAL;
	        gbc_comboNivel.insets = new Insets(5, 5, 5, 5);
	        panelFormulario.add(comboNivel, gbc_comboNivel);
	
	        JLabel lblAporte = new JLabel("Aporte Económico:");
	        GridBagConstraints gbc_lblAporte = new GridBagConstraints();
	        gbc_lblAporte.gridx = 0;
	        gbc_lblAporte.gridy = 5;
	        gbc_lblAporte.insets = new Insets(5, 5, 5, 5);
	        gbc_lblAporte.anchor = GridBagConstraints.WEST;
	        panelFormulario.add(lblAporte, gbc_lblAporte); 
	
	        JTextField txtAporte = new JTextField(15);
	        GridBagConstraints gbc_txtAporte = new GridBagConstraints();
	        gbc_txtAporte.gridx = 1;
	        gbc_txtAporte.gridy = 5;
	        gbc_txtAporte.fill = GridBagConstraints.HORIZONTAL;
	        gbc_txtAporte.insets = new Insets(5, 5, 5, 5);
	        panelFormulario.add(txtAporte, gbc_txtAporte);
	
	        JLabel lblCupos = new JLabel("Cantidad Gratuitos:");
	        GridBagConstraints gbc_lblCupos = new GridBagConstraints();
	        gbc_lblCupos.gridx = 0;
	        gbc_lblCupos.gridy = 6;
	        gbc_lblCupos.insets = new Insets(5, 5, 5, 5);
	        gbc_lblCupos.anchor = GridBagConstraints.WEST;
	        panelFormulario.add(lblCupos, gbc_lblCupos);
	
	        JTextField txtCupos = new JTextField(15);
	        GridBagConstraints gbc_txtCupos = new GridBagConstraints();
	        gbc_txtCupos.gridx = 1;
	        gbc_txtCupos.gridy = 6;
	        gbc_txtCupos.fill = GridBagConstraints.HORIZONTAL;
	        gbc_txtCupos.insets = new Insets(5, 5, 5, 5);
	        panelFormulario.add(txtCupos, gbc_txtCupos);
	
	        JLabel lblCodigo = new JLabel("Código de Patrocinio:");
	        GridBagConstraints gbc_lblCodigo = new GridBagConstraints();
	        gbc_lblCodigo.gridx = 0;
	        gbc_lblCodigo.gridy = 7;
	        gbc_lblCodigo.insets = new Insets(5, 5, 5, 5);
	        gbc_lblCodigo.anchor = GridBagConstraints.WEST;
	        panelFormulario.add(lblCodigo, gbc_lblCodigo);
	
	        JTextField txtCodigo = new JTextField(15);
	        GridBagConstraints gbc_txtCodigo = new GridBagConstraints();
	        gbc_txtCodigo.gridx = 1;
	        gbc_txtCodigo.gridy = 7;
	        gbc_txtCodigo.fill = GridBagConstraints.HORIZONTAL;
	        gbc_txtCodigo.insets = new Insets(5, 5, 5, 5);
	        panelFormulario.add(txtCodigo, gbc_txtCodigo);
	
	        comboEvento.addActionListener(e -> {
	            String eventoSeleccionado = (String) comboEvento.getSelectedItem();
	            if (eventoSeleccionado == null || eventoSeleccionado.isEmpty()) return;

	            comboEdicion.removeAllItems();
	            comboTipo.removeAllItems();

	            try {
	                Set<String> edicionesDisponibles = IContEvento.nombresEdicionesEvento(eventoSeleccionado);
	                for (String edicion : edicionesDisponibles) {
	                    comboEdicion.addItem(edicion);
	                }
	            } catch (EventoNoExiste ex) {
	                JOptionPane.showMessageDialog(this, "El evento seleccionado no tiene ediciones registradas", "Advertencia", JOptionPane.WARNING_MESSAGE);
	            }
	        });

	        comboEdicion.addActionListener(e -> {
	            String eventoSeleccionado = (String) comboEvento.getSelectedItem();
	            String edicionSeleccionada = (String) comboEdicion.getSelectedItem();
	            if (eventoSeleccionado == null || edicionSeleccionada == null
	                || eventoSeleccionado.isEmpty() || edicionSeleccionada.isEmpty()) return;

	            comboTipo.removeAllItems();

	            Set<String> tiposDisponibles = IContEvento.getNombresTipoRegistro(eventoSeleccionado, edicionSeleccionada);
	            if (tiposDisponibles != null) {
	                for (String tipo : tiposDisponibles) {
	                    comboTipo.addItem(tipo);
	                }
	            }
	        });
	         
	        JButton Button_Aceptar = new JButton("Aceptar");
	        GridBagConstraints gbc_Button_Aceptar = new GridBagConstraints();
	        gbc_Button_Aceptar.insets = new Insets(15, 0, 10, 10);
	        gbc_Button_Aceptar.gridx = 1;
	        gbc_Button_Aceptar.gridy = 8;
	        gbc_Button_Aceptar.anchor = GridBagConstraints.EAST;
	        getContentPane().add(Button_Aceptar, gbc_Button_Aceptar);

	        Button_Aceptar.addActionListener(e -> {
	            String eventoSeleccionado = (String) comboEvento.getSelectedItem();
	            String edicionSeleccionada = (String) comboEdicion.getSelectedItem();
	            String institucionSeleccionada = (String) comboInstitucion.getSelectedItem();
	            String nivelPatrocinio = (String) comboNivel.getSelectedItem();
	            String codigoPatrocinio = txtCodigo.getText().trim();
	            String tipoRegistro=(String) comboTipo.getSelectedItem();
	            
	            ENivelPatrocinio nivel=ENivelPatrocinio.valueOf(nivelPatrocinio);
	            float aporteEconomico;
	            int cantidadGratuitos;

	            try {
	                // Validación de aporte económico
	                aporteEconomico = Float.parseFloat(txtAporte.getText().trim());
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(this, "El aporte debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            try {
	                // Validación de cantidad de cupos
	                cantidadGratuitos = Integer.parseInt(txtCupos.getText().trim());
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	            
	            if (aporteEconomico<0) {
	            	JOptionPane.showMessageDialog(this, "El aporte debe ser un número entero mayor a cero", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	            if (cantidadGratuitos<0) {
	            	JOptionPane.showMessageDialog(this, "La cantidad de cupos debe ser un número entero mayor a cero", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	           
	            	// Validación del 20% del aporte
	            	DTTipoRegistro tipo = null;
					try {
						tipo = IContEvento.getDTTipoRegistro(eventoSeleccionado, edicionSeleccionada, tipoRegistro);
					} catch (TipoRegistroNoExiste e1) {
						 JOptionPane.showMessageDialog(this, "TipoRegistro no existe", "Advertencia", JOptionPane.WARNING_MESSAGE);
					} catch (EventoNoExiste e1) {
						 JOptionPane.showMessageDialog(this, "Evento no existe", "Advertencia", JOptionPane.WARNING_MESSAGE);
					} catch (EdicionNoExiste e1) {
						JOptionPane.showMessageDialog(this, "Edicion no existe", "Advertencia", JOptionPane.WARNING_MESSAGE);
					}
					
	            	float valorCupo =tipo.costo();
	            	float costoTotalGratuitos = cantidadGratuitos * valorCupo;
	            	float limitePermitido = aporteEconomico * 0.2f;
	            	if (costoTotalGratuitos > limitePermitido) {
	            		 JOptionPane.showMessageDialog(this, "El costo de los registros gratuitos supera el 20% del aporte económico", "Error", JOptionPane.ERROR_MESSAGE);
	            	}


	            try {
	                // Llamada al método con validaciones internas
	            	Date fechaActual=new Date();
	            	IContEvento.altaPatrocinio(eventoSeleccionado,edicionSeleccionada,fechaActual,institucionSeleccionada,nivel,aporteEconomico,cantidadGratuitos,codigoPatrocinio);
	            	JOptionPane.showMessageDialog(this,"Patrocinio creado correctamente","Éxito",JOptionPane.INFORMATION_MESSAGE);
	                // limpia los campos si llega al final
	            	txtAporte.setText("");
	            	txtCupos.setText("");
	            	txtCodigo.setText("");
	            	comboEvento.setSelectedIndex(-1);
	            	comboEdicion.removeAllItems();
	            	comboTipo.removeAllItems();
	            	comboInstitucion.setSelectedIndex(-1);
	            	comboNivel.setSelectedIndex(-1);

	            } catch (Exception e1) {
	                JOptionPane.showMessageDialog(AltaPatrocinio.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        });


	         
	        JButton Button_Cancelar = new JButton("Cancelar");
	        GridBagConstraints gbc_Button_Cancelar = new GridBagConstraints();
	        gbc_Button_Cancelar.insets = new Insets(15, 0, 10, 10);
	        gbc_Button_Cancelar.gridx = 2;
	        gbc_Button_Cancelar.gridy = 8;
	        gbc_Button_Cancelar.anchor = GridBagConstraints.WEST;
	        getContentPane().add(Button_Cancelar, gbc_Button_Cancelar);

	        Button_Cancelar.addActionListener(e -> {
	            AltaPatrocinio.this.dispose();
	        });
	    }
	}      
	