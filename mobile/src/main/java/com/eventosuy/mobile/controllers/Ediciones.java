package com.eventosuy.mobile.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.eventosuy.mobile.servidor.DTDetalleRegistro;
import com.eventosuy.mobile.servidor.DTDetalleUsuario;
import com.eventosuy.mobile.servidor.DTEdicionEvento;
import com.eventosuy.mobile.servidor.DTEventoEntry;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.eventosuy.mobile.utils.Utilidades;

import com.eventosuy.mobile.servidor.Publicador;
import com.eventosuy.mobile.servidor.PublicadorService;
import com.eventosuy.mobile.servidor.UsuarioNoExiste_Exception;

@WebServlet("/Ediciones")
@MultipartConfig
public class Ediciones extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Ediciones () {super();} // Constructor (no lleva parametros)
	
	private final static PublicadorService servicio = new PublicadorService();
	private final static Publicador puerto = servicio.getPublicadorPort();
	
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
				break;
             case "presentacion":
                 DTDetalleUsuario usuarioLogeado = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
            	 if (usuarioLogeado == null) {
                     response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Usuario no ha iniciado sesión.");
                     return;
                 }
            	 List<DTEdicionEvento> eds = puerto.listarEdicionesConRegistrosUsuario(usuarioLogeado.getNickname()).getItem();
				 request.setAttribute("Ediciones", eds);
				 request.getRequestDispatcher("/WEB-INF/views/ediciones/presentacion-ediciones-con-registro.jsp").forward(request, response);
				 break;
             default:
                 response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
                 break;
		}
	}
	
	

	private HttpServletRequest CUConsultaEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nombreEdicion = request.getParameter("nombre_edicion");
		if (nombreEdicion == null) { nombreEdicion = (String) request.getAttribute("nombre_edicion"); }
		
		String nombreEvento = request.getParameter("nombre_evento");
		if (nombreEvento == null) { nombreEvento = (String) request.getAttribute("nombre_evento"); }
		if (nombreEvento == null) { 
			List<DTEventoEntry>	eventos = puerto.listarEventos().getItem();
			
			for (DTEventoEntry evento : eventos) {
				DTEdicionEvento dted= puerto.getDTEdicion(nombreEdicion, nombreEvento);
				if (puerto.listarEdicionesEvento(evento.getKey()).getItem().contains(dted)) {
					nombreEvento = evento.getKey();
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
		
		DTEdicionEvento dtEdicion = puerto.getDTEdicion(nombreEvento, nombreEdicion); // Asumo tiene la imagen
		List<String> setPatrocinios = puerto.listarPatrocinadores(nombreEvento, nombreEdicion).getItem();
		List<String> setTiposRegistro = puerto.getNombresTipoRegistro(nombreEvento, nombreEdicion).getItem();
		List<DTDetalleRegistro> setRegistros = puerto.listarDTRegistrosAEdicion(nombreEvento, nombreEdicion).getItem();
		Map<String, DTDetalleRegistro> asistentesRegistrados = new HashMap<>();
		for (DTDetalleRegistro dtRegistro : setRegistros) {
			asistentesRegistrados.put(dtRegistro.getNicknameAsistente(), dtRegistro);
		}
		
		request.setAttribute("detalle_edicion", dtEdicion);
		request.setAttribute("lista_patrocinios", setPatrocinios);
		request.setAttribute("lista_tipos_registro", setTiposRegistro);
		request.setAttribute("finalizada", false);
		Date fechaFin=Utilidades.XMLGregorianCalendarToDate( dtEdicion.getFechaFin());
		if (fechaFin.before(new Date())) { 
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
		
		DTEdicionEvento ed =puerto.getDTEdicion(nombreEvento, nombreEdicion);
		try {
			DTDetalleUsuario u =puerto.mostrarDetalleUsuario(ed.getOrganizadorNickname());
			request.setAttribute("imagenOrg",u.getImagen());
		} catch (UsuarioNoExiste_Exception e) {
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
