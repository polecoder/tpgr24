package com.eventosuy.mobile.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import datatypes.DTDetalleRegistro;
import datatypes.DTDetalleUsuario;
import datatypes.DTEdicionEvento;
import datatypes.DTRegistro;
import excepciones.UsuarioNoExiste;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorUsuarios;

@WebServlet("/Ediciones")
@MultipartConfig
public class Ediciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Ediciones () {super();} // Constructor (no lleva parametros)
	
	private final static Fabrica fabrica = Fabrica.getInstancia();
	private final static IControladorEventos controladorEventos = fabrica.getControladorEvento();
	private final static IControladorUsuarios controladorUsuarios = fabrica.getControladorUsuarios();
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		 String accion = request.getParameter("accion");
         if (accion == null) {
             response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada.");
             return;
         }

         switch (accion) {
             case "consulta":
				request = CUConsultaEdicion(request, response);
				request.getRequestDispatcher("/WEB-INF/views/ediciones/consulta-edicion.jsp").forward(request, response);
         }
		}
	
	

	private HttpServletRequest CUConsultaEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreEdicion = request.getParameter("nombre_edicion");
		if (nombreEdicion == null) { nombreEdicion = (String) request.getAttribute("nombre_edicion"); }
		
		String nombreEvento = request.getParameter("nombre_evento");
		if (nombreEvento == null) { nombreEvento = (String) request.getAttribute("nombre_evento"); }
		if (nombreEvento == null) { 
			for (String evento : controladorEventos.listarEventos().keySet()) {
				if (controladorEventos.listarEdicionesEvento(evento).containsKey(nombreEdicion)) {
					nombreEvento = evento;
					request.setAttribute("nombre_evento", nombreEvento);
					break;
				}
			}
		}
		
		
		if (nombreEvento == null) {
			request.setAttribute("error_message", "La edicion no es de ningun evento");
			request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
			return request;
		}
		
		DTEdicionEvento dtEdicion = controladorEventos.getDTEdicion(nombreEvento, nombreEdicion); // Asumo tiene la imagen
		Set<String> setPatrocinios = controladorEventos.listarPatrocinadores(nombreEvento, nombreEdicion);
		Set<String> setTiposRegistro = controladorEventos.getNombresTipoRegistro(nombreEvento, nombreEdicion);
		Set<DTDetalleRegistro> setRegistros = controladorEventos.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion);
		Map<String, DTDetalleRegistro> asistentesRegistrados = new HashMap<>();
		for (DTDetalleRegistro dtRegistro : setRegistros) {
			asistentesRegistrados.put(dtRegistro.getNicknameAsistente(), dtRegistro);
		}
		
		request.setAttribute("detalle_edicion", dtEdicion);
		request.setAttribute("lista_patrocinios", setPatrocinios);
		request.setAttribute("lista_tipos_registro", setTiposRegistro);
		request.setAttribute("finalizada", false);
		if (dtEdicion.getFechaFin().before(new Date())) { 
			request.setAttribute("finalizada", true);
		}
		
		DTDetalleUsuario dtUsuario = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
		String nickname = null;
		if (dtUsuario != null) {nickname = dtUsuario.getNickname();}
		if (nickname != null && nickname.equals(dtEdicion.getOrganizadorNickname())) {
			request.setAttribute("lista_registros", setRegistros);
		} else if (nickname != null && asistentesRegistrados.keySet().contains(nickname)) {
			request.setAttribute("detalle_registro", asistentesRegistrados.get(nickname));
		}
		
		DTEdicionEvento ed =controladorEventos.getDTEdicion(nombreEvento, nombreEdicion);
		try {
			DTDetalleUsuario u =controladorUsuarios.mostrarDetalleUsuario(ed.getOrganizadorNickname());
			request.setAttribute("imagenOrg",u.getImagen());
		} catch (UsuarioNoExiste e) {
			//no va a entrar aca nunca
			e.printStackTrace();
		}
		return request;
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*try {
			request = Test(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
