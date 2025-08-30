package presentacion;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JDesktopPane;

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
        setBounds(100, 100, 550, 450);
        setTitle("Eventos.uy");
        getContentPane().setLayout(new BorderLayout(0, 0));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);	
        
        panel = new JDesktopPane();
        getContentPane().add(panel, BorderLayout.CENTER);
		
        JMenu menuUsuario = new JMenu("Usuarios");
        menuBar.add(menuUsuario);

        JMenuItem item_AltaUsuario = new JMenuItem("Alta Usuario");
        JMenuItem item2 = new JMenuItem("Consulta");
        JMenuItem item3 = new JMenuItem("Modificar Datos");

        menuUsuario.add(item_AltaUsuario);
        menuUsuario.add(item2);
        menuUsuario.add(item3);
		
		    	  
        JMenu menuEvento = new JMenu("Evento");
        menuBar.add(menuEvento);

        JMenuItem item4 = new JMenuItem("Alta Evento");
        JMenuItem item_AltaEdicion = new JMenuItem("Alta Edición de Evento");
        JMenuItem item_ConsultaEvento = new JMenuItem("Consulta Evento");
        JMenuItem item7 = new JMenuItem("Consulta Edicion de Evento");

        menuEvento.add(item4);
        menuEvento.add(item_AltaEdicion);
        menuEvento.add(item_ConsultaEvento);
        menuEvento.add(item7);
		
        JMenu menuRegistro = new JMenu("Registro");
        menuBar.add(menuRegistro);

        JMenuItem item8 = new JMenuItem("Alta Tipo Registro");
        JMenuItem item9 = new JMenuItem("Consulta Tipo Registro");
        JMenuItem itemA = new JMenuItem("Consulta Registro");
        JMenuItem itemB = new JMenuItem("Registro a Edicion de Evento");

        menuRegistro.add(item8);
        menuRegistro.add(item9);
        menuRegistro.add(itemA);
        menuRegistro.add(itemB);
			
        JMenu menuInstitucion = new JMenu("Institución");
        menuBar.add(menuInstitucion);

        JMenuItem itemC = new JMenuItem("Alta Institución");
        JMenuItem itemD = new JMenuItem("Alta Patrocinio");
        JMenuItem itemE = new JMenuItem("Consulta de Patrocinio");

        menuInstitucion.add(itemC);
        menuInstitucion.add(itemD);
        menuInstitucion.add(itemE);
				
		item_AltaUsuario.addActionListener(e -> {
				    AltaUsuario ventanaAltaUsuario = new AltaUsuario();
				    panel.add(ventanaAltaUsuario);          
				    ventanaAltaUsuario.setVisible(true);    
				    try {
				        ventanaAltaUsuario.setSelected(true); 
				    } catch (Exception ex) {}
				});
		
		item2.addActionListener(e -> {
		   ConsultaUsuario ventanaCU = new ConsultaUsuario();
		    panel.add(ventanaCU);          
		    ventanaCU.setVisible(true);    
		    try {
		        ventanaCU.setSelected(true); 
		    } catch (Exception ex) {}
		});
		
		item3.addActionListener(e -> {
			   ModificarUsuario ventanaMU = new ModificarUsuario();
			    panel.add(ventanaMU);          
			    ventanaMU.setVisible(true);    
			    try {
			        ventanaMU.setSelected(true); 
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
		
		itemB.addActionListener(e -> {
			RegistroAEdicionEvento ventanaRegistro= new RegistroAEdicionEvento();
		    panel.add(ventanaRegistro);          
		    ventanaRegistro.setVisible(true);    
		    try {
		    	ventanaRegistro.setSelected(true); 
		    } catch (Exception ex) {}
		});
		
		setVisible(true);
    }
}