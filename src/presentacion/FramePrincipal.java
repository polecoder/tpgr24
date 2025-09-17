package presentacion;

import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class FramePrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JDesktopPane  panel = new JDesktopPane();
    
    public void abrirVentanaInterna(JInternalFrame ventana) {
        panel.add(ventana);
        ventana.setVisible(true);
        try {
            ventana.setSelected(true);
        } catch (Exception ex) {} 
    }
    public FramePrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 600);
        setTitle("Eventos.uy");
        getContentPane().setLayout(new BorderLayout(0, 0));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);	
        
        panel = new JDesktopPane();
        getContentPane().add(panel, BorderLayout.CENTER);
		
        JMenu menuUsuario = new JMenu("Usuarios");
        menuBar.add(menuUsuario);

        JMenuItem item_AltaUsuario = new JMenuItem("Alta Usuario");
        JMenuItem item_ConsultaUsuario = new JMenuItem("Consulta Usuario");
        JMenuItem item_ModificarDatosUsuario = new JMenuItem("Modificar Datos Usuario");
        
        menuUsuario.add(item_AltaUsuario);
        menuUsuario.add(item_ConsultaUsuario);
        menuUsuario.add(item_ModificarDatosUsuario);
		    	  
        JMenu menuEvento = new JMenu("Evento");
        menuBar.add(menuEvento);

        JMenuItem item_AltaEvento = new JMenuItem("Alta Evento");
        JMenuItem item_AltaEdicion = new JMenuItem("Alta Edición de Evento");
        JMenuItem item_ConsultaEvento = new JMenuItem("Consulta Evento");
        JMenuItem item_ConsultaEdicionEvento = new JMenuItem("Consulta Edicion de Evento");
        JMenuItem item_AceptarRechazar= new JMenuItem("Autorizar Ediciones");
        
        menuEvento.add(item_AltaEvento);
        menuEvento.add(item_AltaEdicion);
        menuEvento.add(item_ConsultaEvento);
        menuEvento.add(item_ConsultaEdicionEvento);
        menuEvento.add(item_AceptarRechazar);
		
        JMenu menuRegistro = new JMenu("Registro");
        menuBar.add(menuRegistro);

        JMenuItem item_AltaTipoRegistro = new JMenuItem("Alta Tipo Registro");
        JMenuItem item_ConsultaTipoRegistro = new JMenuItem("Consulta Tipo Registro");
        JMenuItem itemA = new JMenuItem("Consulta Registro");
        JMenuItem item_RegistroAEdicionEvento = new JMenuItem("Registro a Edicion de Evento");

        menuRegistro.add(item_AltaTipoRegistro);
        menuRegistro.add(item_ConsultaTipoRegistro);
        menuRegistro.add(itemA);
        menuRegistro.add(item_RegistroAEdicionEvento);
        
		item_ConsultaTipoRegistro.addActionListener(e -> {
		    ConsultaTipoRegistro ventanaConsultaTipoRegistro= new ConsultaTipoRegistro(null, null, null);
		    panel.add(ventanaConsultaTipoRegistro);           
		    ventanaConsultaTipoRegistro.setVisible(true);    
		    try {
		    	ventanaConsultaTipoRegistro.setSelected(true); 
		    } catch (Exception ex) {}
		});
        
        JMenu menuInstitucion = new JMenu("Institución");
        menuBar.add(menuInstitucion);

        JMenuItem item_AltaInstitucion = new JMenuItem("Alta Institución");
        JMenuItem item_AltaPatrocinio = new JMenuItem("Alta Patrocinio");
        //JMenuItem itemE = new JMenuItem("Consulta de Patrocinio");

        menuInstitucion.add(item_AltaInstitucion);
        menuInstitucion.add(item_AltaPatrocinio);
        //menuInstitucion.add(itemE);
        
        JMenu menuCargaDatos = new JMenu("Carga Datos");
        menuBar.add(menuCargaDatos);

        JMenuItem item_CargaDatos = new JMenuItem("Carga Datos");

        menuCargaDatos.add(item_CargaDatos);
		
        item_CargaDatos.addActionListener(e -> {
			JCargaDatos ventanaCargaDatos = new JCargaDatos();
			panel.add(ventanaCargaDatos);          
			ventanaCargaDatos.setVisible(true);    
			try {
				ventanaCargaDatos.setSelected(true); 
			} catch (Exception ex) {}
		});
        
		item_AltaTipoRegistro.addActionListener(e -> {
		    AltaTipoRegistro ventanaAltaTipoRegistro = new AltaTipoRegistro();
		    panel.add(ventanaAltaTipoRegistro);          
		    ventanaAltaTipoRegistro.setVisible(true);    
		    try {
		    	ventanaAltaTipoRegistro.setSelected(true); 
		    } catch (Exception ex) {}
		});
				
		item_AltaUsuario.addActionListener(e -> {
		    AltaUsuario ventanaAltaUsuario = new AltaUsuario();
		    panel.add(ventanaAltaUsuario);          
		    ventanaAltaUsuario.setVisible(true);    
		    try {
		        ventanaAltaUsuario.setSelected(true); 
		    } catch (Exception ex) {}
		});
		
		item_ConsultaUsuario.addActionListener(e -> {
		   ConsultaUsuario ventanaCU = new ConsultaUsuario(panel);
		   panel.add(ventanaCU);          
		   ventanaCU.setVisible(true);    
		   try {
		        ventanaCU.setSelected(true); 
		    } catch (Exception ex) {}
		});
		
		item_ModificarDatosUsuario.addActionListener(e -> {
			   ModificarDatosUsuario ventanaModificarDatosUsuario = new ModificarDatosUsuario();
			   panel.add(ventanaModificarDatosUsuario);          
			   ventanaModificarDatosUsuario.setVisible(true);    
			   try {
			        ventanaModificarDatosUsuario.setSelected(true); 
			   } catch (Exception ex) {}
		});

		item_AltaEvento.addActionListener(e -> {
			AltaEvento ventanaAltaEvento = new AltaEvento();
		    panel.add(ventanaAltaEvento);          
		    ventanaAltaEvento.setVisible(true);    
		    try {
		    	ventanaAltaEvento.setSelected(true); 
		    } catch (Exception ex) {}
		});

		item_AltaEdicion.addActionListener(e -> {
				AltaEdicionEvento ventanaAEv = new AltaEdicionEvento();
			    panel.add(ventanaAEv);          
			    ventanaAEv.setVisible(true);    
			    try {
			    	ventanaAEv.setSelected(true); 
			    } catch (Exception ex) {}
			});
		
		item_ConsultaEvento.addActionListener(e -> {
			ConsultaEvento ventanaCEv = new ConsultaEvento(panel);
		    panel.add(ventanaCEv);          
		    ventanaCEv.setVisible(true);    
		    try {
		    	ventanaCEv.setSelected(true); 
		    } catch (Exception ex) {}
		});
		
		itemA.addActionListener(e-> {
			ConsultaRegistro ventanaCRegistro = new ConsultaRegistro(null, null);
			panel.add(ventanaCRegistro);
			ventanaCRegistro.setVisible(true);
			try {
				ventanaCRegistro.setSelected(true);
			} catch( Exception ex) {}
			
		});
		
		item_RegistroAEdicionEvento.addActionListener(e -> {
			RegistroAEdicionEvento ventanaRegistro= new RegistroAEdicionEvento();
		    panel.add(ventanaRegistro);          
		    ventanaRegistro.setVisible(true);    
		    try {
		    	ventanaRegistro.setSelected(true); 
		    } catch (Exception ex) {}
		});
		
		item_AltaInstitucion.addActionListener(e->{
			AltaInstitucion ventanaAltaInstitucion = new AltaInstitucion();
			panel.add(ventanaAltaInstitucion);
			ventanaAltaInstitucion.setVisible(true);
			try {
			    ventanaAltaInstitucion.setSelected(true); 
			} catch (Exception ex) {}
		});
		
		item_ConsultaEdicionEvento.addActionListener(e-> {
			ConsultaEdicionEvento ventanaConsultaEdEv = new ConsultaEdicionEvento(null, null, panel);
			panel.add(ventanaConsultaEdEv);
			ventanaConsultaEdEv.setVisible(true);
			try {
				ventanaConsultaEdEv.setSelected(true);
			}catch(Exception ex) {}
		});
		
		item_AltaPatrocinio.addActionListener(e->{
			AltaPatrocinio ventanaAltaPatrocinio = new AltaPatrocinio();
			panel.add(ventanaAltaPatrocinio);
			ventanaAltaPatrocinio.setVisible(true);
			try {
			    ventanaAltaPatrocinio.setSelected(true); 
			} catch (Exception ex) {}
			
		});
		
		item_AceptarRechazar.addActionListener(e->{
			AceptarRechazarEdicion ventanaAceptarRechazarEdicion = new AceptarRechazarEdicion();
			panel.add(ventanaAceptarRechazarEdicion);
			ventanaAceptarRechazarEdicion.setVisible(true);
			try {
			    ventanaAceptarRechazarEdicion.setSelected(true); 
			} catch (Exception ex) {}
			
		});
		
		setVisible(true); 
	}
}