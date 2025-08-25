package presentacion;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JDesktopPane;




public class framePrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JDesktopPane  panel = new JDesktopPane();

    public framePrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 450);
        setTitle("Eventos.uy");
        getContentPane().setLayout(new BorderLayout(0, 0));

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);	
        
        panel = new JDesktopPane();
        getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton Usuario = new JButton("Usuarios");
		menuBar.add(Usuario);
		
		JPopupMenu menuUsuario = new JPopupMenu();
		    JMenuItem item_AltaUsuario = new JMenuItem("Alta Usuario");
		    JMenuItem item2 = new JMenuItem("Consulta");
		    JMenuItem item3 = new JMenuItem("Modificar Datos");
		    
		    menuUsuario.add(item_AltaUsuario);
		    menuUsuario.add(item2);
		    menuUsuario.add(item3);

		    Usuario.addActionListener(e -> menuUsuario.show(Usuario, 0, Usuario.getHeight()));
		    	  
	    JButton Evento = new JButton("Evento");
		menuBar.add(Evento);
		JPopupMenu casosEvento = new JPopupMenu();
			 JMenuItem item4 = new JMenuItem("Alta Evento");
			 JMenuItem item_AltaEdicion = new JMenuItem("Alta Edición de Evento");
			 JMenuItem item_ConsultaEvento = new JMenuItem("Consulta Evento");
			 JMenuItem item7 = new JMenuItem("Consulta Edicion de Evento");
			 
			 casosEvento.add(item4);
			 casosEvento.add(item_AltaEdicion);
			 casosEvento.add(item_ConsultaEvento);
			 casosEvento.add(item7);
			 Evento.addActionListener(e ->casosEvento .show(Evento, 0, Evento.getHeight()));
		
		JButton Registro = new JButton("Registro");
		menuBar.add(Registro);
		
		JPopupMenu casosRegistro = new JPopupMenu();
			JMenuItem item8 = new JMenuItem("Alta Tipo Registro");
			JMenuItem item9 = new JMenuItem("Consulta Tipo Registro");
			JMenuItem itemA = new JMenuItem("Consulta Regsitro");
			JMenuItem itemB = new JMenuItem("Registro a Edicion de Evento");
		 
			casosRegistro.add(item8);
			casosRegistro.add(item9);
			casosRegistro.add(itemA);
			casosRegistro.add(itemB);
		Registro.addActionListener(e ->casosRegistro .show(Registro, 0, Registro.getHeight()));
			
		JButton Institucion = new JButton("Institucion");
		menuBar.add(Institucion);
			
			JPopupMenu casosInstitucion = new JPopupMenu();
				JMenuItem itemC = new JMenuItem("Alta Institucion");
				JMenuItem itemD = new JMenuItem("Alta Patrocinio");
				JMenuItem itemE = new JMenuItem("Consulta de Patrocinio");
				casosInstitucion.add(itemC);
				casosInstitucion.add(itemD);
				casosInstitucion.add(itemE);
		Institucion.addActionListener(e ->casosInstitucion .show(Institucion, 0, Institucion.getHeight()));
				
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
				AltaEdicion ventanaAEv = new AltaEdicion();
			    panel.add(ventanaAEv);          
			    ventanaAEv.setVisible(true);    
			    try {
			    	ventanaAEv.setSelected(true); 
			    } catch (Exception ex) {}
			});
		
		item_ConsultaEvento.addActionListener(e -> {
			ConsultaEvento ventanaCEv = new ConsultaEvento();
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