package com.eventosuy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import com.eventosuy.utils.Utilidades;

import com.eventosuy.servidor.StringArray;
import com.eventosuy.servidor.UsuarioNoExiste_Exception;
import com.eventosuy.servidor.DTRegistroArray;
import com.eventosuy.servidor.DTUsuario;
import com.eventosuy.servidor.DTEventoEdicionArray;

import javax.xml.datatype.XMLGregorianCalendar;

import com.eventosuy.servidor.Publicador;
import com.eventosuy.servidor.PublicadorService;
import com.eventosuy.servidor.DTDetalleAsistente;
import com.eventosuy.servidor.DTDetalleOrganizador;
import com.eventosuy.servidor.DTDetalleRegistro;
import com.eventosuy.servidor.DTDetalleUsuario;
import com.eventosuy.servidor.DTEventoEdicion;
import com.eventosuy.servidor.EEstadoEdicion;
import com.eventosuy.servidor.DTRegistro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet; 
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig 
@WebServlet("/usuario")                     
//@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // imagen opcional 
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Servicios
	private final static PublicadorService servicio = new PublicadorService();
	private final static Publicador puerto = servicio.getPublicadorPort();
	
	public UsuarioServlet() { super(); }

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");  //cualquier caracter

		final String accion = nvl(request.getParameter("accion"), "consulta");
		switch (accion) {

		// ==================== ALTA USUARIO ====================
		case "alta":
			if (isPost(request)) {
				String rol       = trim(request.getParameter("rol"));   
				String nickname  = trim(request.getParameter("nickname"));
				String nombre    = trim(request.getParameter("nombre"));
				String email     = trim(request.getParameter("email"));
				String password  = trim(request.getParameter("password"));
				byte[] imagen = readPartBytes(request.getPart("imagen")); 
				if (imagen == null) imagen = new byte[0]; //para q no me de error arg vacio
				
				try {
					if ("organizador".equalsIgnoreCase(rol)) {
						String descripcion = trim(request.getParameter("descripcion"));
						String web         = trim(request.getParameter("web"));
						puerto.altaOrganizador(
								nickname,          
								password,          
								nombre,            
								email,             
								descripcion,       
								web,
								imagen
								);


					} else if ("asistente".equalsIgnoreCase(rol)) {
						String apellido    = trim(request.getParameter("apellido"));
						LocalDate fechaNac = parseDate(trim(request.getParameter("fechaNac"))); 
						Date dateFechaNac = (fechaNac != null ? java.sql.Date.valueOf(fechaNac) : null);
						XMLGregorianCalendar xcal = Utilidades.dateToXMLGregorianCalendar(dateFechaNac);
						
						String institucion = trim(request.getParameter("institucion"));
						
						if (institucion == null || institucion.isBlank() || "-".equals(institucion)) {
						    institucion = "-";
						}
						
						puerto.altaAsistente(
								nickname,         
								password,          
								nombre,            
								email,             
								apellido,          
								xcal, 
								institucion,
								imagen
								);


					} else {
						request.setAttribute("error", "Rol inválido.");
						request.getRequestDispatcher("/WEB-INF/views/usuarios/alta-usuario.jsp")
						.forward(request, response);
						return;
					}

		            String contexto = request.getContextPath();
		            response.sendRedirect(
		                contexto + "/usuario?accion=consulta&nick=" +
		                java.net.URLEncoder.encode(nickname, java.nio.charset.StandardCharsets.UTF_8)
		            );
		            return;

				} catch (Exception ex) {
					request.setAttribute("error", safeMsg(ex, "No se pudo crear el usuario."));
					request.getRequestDispatcher("/WEB-INF/views/usuarios/alta-usuario.jsp").forward(request, response);
					return;
				}

			} else {
				List<String> listaNombresInstitucion = puerto.nombresInstituciones().getItem();
				request.setAttribute("listaNombresInstitucion", listaNombresInstitucion);
				
				request.getRequestDispatcher("/WEB-INF/views/usuarios/alta-usuario.jsp")
				.forward(request, response);
				return;
			}

			//----------CONSULTA USUARIO--------
		case "consulta": {
		    String nick = trim(request.getParameter("nick"));
		    DTDetalleUsuario usuarioLogeado = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");

		    try {
		        // listar los usuarios, al pedo pero por si accede directo a consulta-usuario
		        if (isBlank(nick)) {
		            Collection<DTUsuario> usrs = puerto.listarUsuarios().getItem(); 
		            request.setAttribute("usuarios", usrs);
		            request.getRequestDispatcher("/WEB-INF/views/usuarios/consulta-usuario.jsp")
		                   .forward(request, response);
		            return;
		        }
		        
		        //consulta del usuario
		        DTDetalleUsuario usuario = obtenerDetalleUsuario(nick);
		        if (usuario == null) {
		            // no existe
		            response.setStatus(404);
		            request.getRequestDispatcher("/pages/error/404.jsp").forward(request, response);
		            return;
		        }
		        
		        request.setAttribute("usuario", usuario);
		        
		        procesarImagenUsuario(usuario, request);
		        
		        // verificar si es el perfil propio
		        boolean esPropio = usuarioLogeado != null && usuarioLogeado.getNickname().equals(usuario.getNickname());
		        
		        // cargar datos según tipo de usuario
		        if (usuario instanceof com.eventosuy.servidor.DTDetalleOrganizador) {
		            cargarDatosOrganizador(usuario, esPropio, request);
		        }
		        
		        if (usuario instanceof DTDetalleAsistente) {
		            cargarDatosAsistente(usuario, esPropio, request);
		        }
		        
		        request.getRequestDispatcher("/WEB-INF/views/usuarios/consulta-usuario.jsp")
		               .forward(request, response);

		    } catch (Exception ex) {
		        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        request.setAttribute("error", safeMsg(ex, "No se pudo realizar la consulta."));
		        request.getRequestDispatcher("/WEB-INF/views/usuarios/consulta-usuario.jsp")
		               .include(request, response);
		    }
		    break;
		}

		// ==================== REGISTRO A EDICIÓN ====================
		case "registro-edicion":
		    // --- Requiere login ---
		    DTDetalleUsuario usuarioLogeado = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
		    if (usuarioLogeado == null) {
		        response.sendRedirect(request.getContextPath() + "/login");
		        return;
		    }
		    
		    if (isPost(request)) {
		        // post
		        procesarRegistroEdicion(usuarioLogeado, request, response);
		    } else {
		        // get
		        mostrarFormularioRegistro(request, response);
		    }
		    break;
		   
		 // ==================== CONSULTA DE REGISTROS ====================
		case "consulta-registro": {
		    DTDetalleUsuario usuariologeado = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
		    
		    if (usuariologeado == null) {
		        response.sendRedirect(request.getContextPath() + "/login");
		        return;
		    }

		    try {
		        if (usuariologeado instanceof DTDetalleOrganizador) {
		            // puede consultar registros de sus ediciones
		            consultarRegistrosComoOrganizador(usuariologeado, request, response);
		        } else {
		            // solo ve sus propios registros
		            consultarRegistrosComoAsistente(usuariologeado, request, response);
		        }
		    } catch (Exception ex) {
		        request.setAttribute("error", safeMsg(ex, "No se pudo completar la consulta."));
		        request.getRequestDispatcher("/WEB-INF/views/registros/consulta-registro.jsp")
		               .forward(request, response);
		    }
		    return;
		}
		case "checkNickname":{
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			String nick= request.getParameter("nickname");
			if (puerto.existeNick(nick)) {
		        response.getWriter().write("no-disponible");
		        
		    } else {
		        response.getWriter().write("disponible");
		     
		    }
		    return;
		}
		case "seguir":{
			doSeguir(request, response);
			break;
		}
		case "dejarDeSeguir":{
			doDejarDeSeguir(request, response);
			break;
		}

		// TODO: Esto no debería estar, o debería redirigir a una página de error existente
		// ==================== ACCIÓN DESCONOCIDA ====================
		default:
			response.setStatus(404);
			request.getRequestDispatcher("/pages/error/404.jsp").include(request, response);
		}
	}
		
		// consulta usuario FUNCIONES

		private DTDetalleUsuario obtenerDetalleUsuario(String nick) {
		    try {
		        Object detalle = puerto.mostrarDetalleUsuario(nick);
		        
		        if (detalle instanceof jakarta.xml.bind.JAXBElement<?>) {
		            detalle = ((jakarta.xml.bind.JAXBElement<?>) detalle).getValue();
		        }
		        
		        return (DTDetalleUsuario) detalle;
		    } catch (Exception e) {
		        return null;
		    }
		}

		private void procesarImagenUsuario(DTDetalleUsuario u, HttpServletRequest request) {
		    byte[] imgBytes = u.getImagen();
		    if (imgBytes != null && imgBytes.length > 0) {
		        String contentType = "image/jpeg";
		        String b64 = java.util.Base64.getEncoder().encodeToString(imgBytes);
		        String dataUri = "data:" + contentType + ";base64," + b64;
		        request.setAttribute("imagenDataUri", dataUri);
		    }
		}

		private void cargarDatosOrganizador(DTDetalleUsuario u, boolean esPropio, HttpServletRequest request) {
		    // ediciones aceptadas 
		    com.eventosuy.servidor.StringArray respAceptadas = puerto.getEdicionesDeOrganizadorPorEstado(
		            u.getNickname(), EEstadoEdicion.ACEPTADA);
		    List<String> aceptadas = convertirStringArray(respAceptadas);
		    request.setAttribute("edicionesAceptadas", aceptadas);

		    // ediciones ingresadas y rechazadas solo si consulta su perfil
		    List<String> ingresadas = Collections.emptyList();
		    List<String> rechazadas = Collections.emptyList();
		    List<String> archivadas  = Collections.emptyList();
		    
		    if (esPropio) {
		        com.eventosuy.servidor.StringArray Ingresadas = puerto.getEdicionesDeOrganizadorPorEstado(
		                u.getNickname(), EEstadoEdicion.INGRESADA);
		        com.eventosuy.servidor.StringArray Rechazadas = puerto.getEdicionesDeOrganizadorPorEstado(
		                u.getNickname(), EEstadoEdicion.RECHAZADA);
		        com.eventosuy.servidor.StringArray Archivadas = puerto.listarEdicionesArchivadasDeOrganizador(u.getNickname());
		        
		        ingresadas = convertirStringArray(Ingresadas);
		        rechazadas = convertirStringArray(Rechazadas);
		        archivadas = convertirStringArray(Archivadas);
		    }
		    
		    request.setAttribute("misEdicionesIngresadas", ingresadas);
		    request.setAttribute("misEdicionesRechazadas", rechazadas);
		    request.setAttribute("misEdicionesArchivadas",  archivadas);
		}

		private void cargarDatosAsistente(DTDetalleUsuario u, boolean esPropio, HttpServletRequest request) {
		    // nombre de institución
		    String inst = puerto.getNombreInstitucionAsistente(u.getNickname());
		    if (inst != null && !inst.isBlank() && !"-".equals(inst)) {
		        request.setAttribute("institucionNombre", inst);
		    }
		    
		    // registros si es su perfil
		    List<com.eventosuy.servidor.DTRegistro> regs = Collections.emptyList();
		    if (esPropio) {
		        com.eventosuy.servidor.DTRegistroArray registros = puerto.listarRegistrosUsuario(u.getNickname());
		        regs = convertirDTRegistroArray(registros);
		    }
		    request.setAttribute("registros", regs);
		}

		private List<String> convertirStringArray(com.eventosuy.servidor.StringArray array) {
		    if (array != null && array.getItem() != null) {
		        return array.getItem();
		    }
		    return Collections.emptyList();
		}

		private List<com.eventosuy.servidor.DTRegistro> convertirDTRegistroArray(com.eventosuy.servidor.DTRegistroArray array) {
		    if (array != null && array.getItem() != null) {
		        return array.getItem();
		    }
		    return Collections.emptyList();
		}
		
		//FUNCIONES registro a edicion
		
		private void mostrarFormularioRegistro(HttpServletRequest request, HttpServletResponse response) 
		        throws ServletException, IOException {
		    
		    String evento = trim(request.getParameter("evento"));
		    String edicion = trim(request.getParameter("edicion"));

		    // validar que vengan los parámetros necesarios
		    if (isBlank(evento) || isBlank(edicion)) {
		        request.setAttribute("error", "Debe acceder al registro desde la consulta de una edición.");
		        request.getRequestDispatcher("/WEB-INF/views/ediciones/registro-edicion.jsp").forward(request, response);
		        return;
		    }

		    try {
		        // cargar información de la edición
		        request.setAttribute("eventoSel", evento);
		        request.setAttribute("edicionSel", edicion);
		        request.setAttribute("edicion", puerto.getDTEdicion(evento, edicion));
		        
		        // cargar tipos de registro disponibles
		        com.eventosuy.servidor.StringArray tiposArr = puerto.listarTipoRegistroKeys(evento, edicion);
		        List<String> tipos = convertirStringArray(tiposArr);
		        request.setAttribute("tipos", tipos);
		        
		    } catch (Exception e) {
		        request.setAttribute("error", safeMsg(e, "No fue posible cargar la información de la edición."));
		    }

		    request.getRequestDispatcher("/WEB-INF/views/ediciones/registro-edicion.jsp").forward(request, response);
		}

		private void procesarRegistroEdicion(DTDetalleUsuario usuarioLogeado, HttpServletRequest request, HttpServletResponse response) 
		        throws ServletException, IOException {
		    
		    String nick = usuarioLogeado.getNickname();
		    String evento = trim(request.getParameter("evento"));
		    String idEdicion = trim(request.getParameter("idEdicion"));
		    String idTipo = trim(request.getParameter("idTipoRegistro"));
		    String codigo = trim(request.getParameter("codigo"));

		    // validar campos obligatorios
		    if (anyBlank(evento, idEdicion, idTipo)) {
		        try {
		            request.setAttribute("eventoSel", evento);
		            request.setAttribute("edicionSel", idEdicion);
		            request.setAttribute("edicion", puerto.getDTEdicion(evento, idEdicion));
		            
		            com.eventosuy.servidor.StringArray tiposArr = puerto.listarTipoRegistroKeys(evento, idEdicion);
		            List<String> tipos = convertirStringArray(tiposArr);
		            request.setAttribute("tipos", tipos);
		        } catch (Exception e) {
		            // Si falla, mostrar solo el error
		        }
		        
		        request.setAttribute("error", "Debe completar todos los campos obligatorios.");
		        request.getRequestDispatcher("/WEB-INF/views/ediciones/registro-edicion.jsp").forward(request, response);
		        return;
		    }

		    try {
		        // registrar al usuario en la edición
		        Date fechaActual = new Date();
		        puerto.altaRegistro(nick, idTipo, idEdicion, evento, codigo, Utilidades.dateToXMLGregorianCalendar(fechaActual));
		        
		        // redirigir a mis registros 
		        response.sendRedirect(request.getContextPath() + "/usuario?accion=consulta-registro&ok=1");

		    } catch (Exception ex) {
		        // si da error, volver
		        try {
		            request.setAttribute("eventoSel", evento);
		            request.setAttribute("edicionSel", idEdicion);
		            request.setAttribute("edicion", puerto.getDTEdicion(evento, idEdicion));
		            
		            com.eventosuy.servidor.StringArray tiposArr = puerto.listarTipoRegistroKeys(evento, idEdicion);
		            List<String> tipos = convertirStringArray(tiposArr);
		            request.setAttribute("tipos", tipos);
		        } catch (Exception e) {
		            // si falla, mostrar solo el error
		        }
		        
		        request.setAttribute("error", safeMsg(ex, "No fue posible completar el registro."));
		        request.setAttribute("tipoSel", idTipo);
		        request.getRequestDispatcher("/WEB-INF/views/ediciones/registro-edicion.jsp").forward(request, response);
		    }
		}
		
		//CONSULTA REGISTRO FUNCIONES
		
		
		// --------------------------- ORGANIZADOR ---------------------------
		private void consultarRegistrosComoOrganizador(DTDetalleUsuario organizador, HttpServletRequest request, HttpServletResponse response) 
		        throws Exception {
		    
		    String evento = trim(request.getParameter("evento"));
		    String edicion = trim(request.getParameter("edicion"));
		    String usuario = trim(request.getParameter("usuario"));

		    if (!isBlank(evento) && !isBlank(edicion) && !isBlank(usuario)) {
				//  detalle de registro de un usuario en esa edición
		        mostrarDetalleRegistroUsuario(evento, edicion, usuario, request);
		    } else if (!isBlank(evento) && !isBlank(edicion)) {
				// : listar usuarios registrados a esa edición
		        listarUsuariosRegistrados(evento, edicion, request);
		    } else {
				//  listar ediciones del organizador
		        listarEdicionesOrganizador(organizador.getNickname(), request);
		    }

		    request.getRequestDispatcher("/WEB-INF/views/registros/consulta-registro.jsp")
		           .forward(request, response);
		}

		private void mostrarDetalleRegistroUsuario(String evento, String edicion, String usuario, HttpServletRequest request) 
		        throws Exception {
		    
		    request.setAttribute("eventoSel", evento);
		    request.setAttribute("edicionSel", edicion);
		    request.setAttribute("usuarioSel", usuario);

		    // validar que la edición pertenece al evento
		    List<String> edicionesDelEvento = convertirStringArray(puerto.nombresEdicionesEvento(evento));
		    
		    if (!edicionesDelEvento.contains(edicion)) {
		        request.setAttribute("error", "La edición seleccionada no pertenece al evento indicado.");
		        request.setAttribute("registrados", new HashSet<>());
		        return;
		    }

		    // buscar el índice del registro del usuario para esa edición
		    Integer indiceRegistro = buscarIndiceRegistroPorEdicion(usuario, edicion);
		    
		    if (indiceRegistro == null) {
		        request.setAttribute("error", "Ese usuario no tiene registro para la edición seleccionada.");
		    } else {
		        DTDetalleRegistro detalle = puerto.obtenerDetalleRegistro(usuario, indiceRegistro);
		        request.setAttribute("detalle", detalle);
		    }
		}

		private Integer buscarIndiceRegistroPorEdicion(String usuario, String nombreEdicion) throws Exception {
		    List<DTRegistro> registrosUsuario = convertirDTRegistroArray(puerto.listarRegistrosUsuario(usuario));
		    
		    for (int i = 0; i < registrosUsuario.size(); i++) {
		        DTRegistro registro = registrosUsuario.get(i);
		        if (nombreEdicion.equals(registro.getNombreEdicion())) {
		            return i;
		        }
		    }
		    
		    return null; // no se encontró
		}

		private void listarUsuariosRegistrados(String evento, String edicion, HttpServletRequest request) throws Exception {
		    request.setAttribute("eventoSel", evento);
		    request.setAttribute("edicionSel", edicion);
		    
		    List<String> registrados = convertirStringArray(puerto.listarRegistrosAEdicion(evento, edicion));
		    request.setAttribute("registrados", new HashSet<>(registrados));
		}

		private void listarEdicionesOrganizador(String nicknameOrganizador, HttpServletRequest request) throws Exception {
		    List<DTEventoEdicion> ediciones = convertirDTEventoEdicionArray(puerto.getEventosEdicionesDeOrganizador(nicknameOrganizador));
		    request.setAttribute("edicionesOrg", ediciones);
		}

		// --------------------------- ASISTENTE ---------------------------
		private void consultarRegistrosComoAsistente(DTDetalleUsuario asistente, HttpServletRequest request, HttpServletResponse response) 
		        throws Exception {
		    
		    String nicknameAsistente = asistente.getNickname();
		    
		    // Cargar lista de todos los registros del asistente
		    List<DTRegistro> registros = convertirDTRegistroArray(puerto.listarRegistrosUsuario(nicknameAsistente));
		    request.setAttribute("registros", registros);
		    
		    // Si viene un índice, mostrar el detalle de ese registro
		    String idxParam = trim(request.getParameter("idx"));
		    if (!isBlank(idxParam)) {
		        try {
		            int indice = Integer.parseInt(idxParam);
		            DTDetalleRegistro detalle = puerto.obtenerDetalleRegistro(nicknameAsistente, indice);
		            request.setAttribute("detalle", detalle);
		            request.setAttribute("idxSel", indice);
		        } catch (NumberFormatException e) {
		            request.setAttribute("error", "Índice de registro inválido.");
		        }
		    }

		    request.getRequestDispatcher("/WEB-INF/views/registros/consulta-registro.jsp")
		           .forward(request, response);
		}


		private List<DTEventoEdicion> convertirDTEventoEdicionArray(DTEventoEdicionArray array) {
		    if (array != null && array.getItem() != null) {
		        return array.getItem();
		    }
		    return new ArrayList<>();
		}




	@Override protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { processRequest(req, resp); }
	@Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { processRequest(req, resp); }

	// --------------------------- Helpers ---------------------------
	private static boolean isPost(HttpServletRequest req) { return "POST".equalsIgnoreCase(req.getMethod()); }
	private static String  nvl(String v, String def) { return (v == null || v.isBlank()) ? def : v; }
	private static String  trim(String s) { return s == null ? null : s.trim(); }
	private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
	private static boolean anyBlank(String... xs) { for (String x : xs) if (isBlank(x)) return true; return false; }
	private static String  safeMsg(Exception e, String fallback) { String m = e.getMessage(); return (m == null || m.isBlank()) ? fallback : m; }

	private static byte[] readPartBytes(Part p) throws IOException {
		if (p == null || p.getSize() == 0) return null;
		try (InputStream in = p.getInputStream()) { return in.readAllBytes(); }
	}
	private static LocalDate parseDate(String iso) {
		if (isBlank(iso)) return null;
		try { return LocalDate.parse(iso); }
		catch (DateTimeParseException e) { return null; }
	}
	private static String getNickFromSession(HttpServletRequest req) {
		DTDetalleUsuario dtUsuario = (DTDetalleUsuario) req.getSession().getAttribute("usuarioLogeado"); // lo setea tu en action=alta
		return (dtUsuario == null) ? null : dtUsuario.getNickname();
	}
	
	private void doSeguir(HttpServletRequest request, HttpServletResponse response) {
		try {
			String nicknameSeguido = request.getParameter("nick");
			DTDetalleUsuario DTSeguidor = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
			String nicknameSeguidor = DTSeguidor != null ? DTSeguidor.getNickname() : null;
			try {
				if (nicknameSeguido == null || nicknameSeguidor == null) throw new UsuarioNoExiste_Exception("No se pudo seguir al usuario. Uno de los usuarios no existe en el sistema.\nPor favor, verifique que haya iniciado sesión correctamente.", null);
				puerto.seguir(nicknameSeguidor, nicknameSeguido);				
			}catch(UsuarioNoExiste_Exception userError) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				request.setAttribute("error", safeMsg(userError, "No se pudo seguir al usuario. Uno de los usuarios no existe en el sistema.\nPor favor, verifique que haya iniciado sesión correctamente."));
				request.getRequestDispatcher("/usuario?accion=consulta&nick="+nicknameSeguido)
				.include(request, response);	
				return;			
			}
			request.getRequestDispatcher("/usuario?accion=consulta&nick="+nicknameSeguido)
            .forward(request, response);
		}catch(ServletException | IOException dispatchError) {
            // Log the error and set a generic error response
            dispatchError.printStackTrace(); // Or use a proper logger
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
	}
	private void doDejarDeSeguir(HttpServletRequest request, HttpServletResponse response) {
		try {
			String nicknameSeguido = request.getParameter("nick");
			DTDetalleUsuario DTSeguidor = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
			String nicknameSeguidor = DTSeguidor != null ? DTSeguidor.getNickname() : null;
			try {
				if (nicknameSeguido == null || nicknameSeguidor == null) {
					throw new UsuarioNoExiste_Exception("No se pudo dejar de seguir al usuario. Uno de los usuarios no existe en el sistema.\nPor favor, verifique que haya iniciado sesión correctamente.", null);
				}
				puerto.dejarDeSeguir(nicknameSeguidor, nicknameSeguido);				
			}catch (UsuarioNoExiste_Exception userError) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				request.setAttribute("error", safeMsg(userError, "No se pudo dejar de seguir al usuario. Uno de los usuarios no existe en el sistema.\nPor favor, verifique que haya iniciado sesión correctamente."));
				request.getRequestDispatcher("/usuario?accion=consulta&nick="+nicknameSeguido)
				.include(request, response);	
				return;
			}
			request.getRequestDispatcher("/usuario?accion=consulta&nick="+nicknameSeguido)
            .forward(request, response);
		}catch(ServletException | IOException dispatchError) {
            // Log the error and set a generic error response
            dispatchError.printStackTrace(); // Or use a proper logger
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
	}
	
}
