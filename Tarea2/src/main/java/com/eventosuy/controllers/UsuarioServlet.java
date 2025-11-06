package com.eventosuy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.eventosuy.servidor.Publicador;
import com.eventosuy.servidor.PublicadorService;
import com.eventosuy.servidor.DTDetalleAsistente;
import com.eventosuy.servidor.DTDetalleOrganizador;
import com.eventosuy.servidor.DTDetalleRegistro;
import com.eventosuy.servidor.DTDetalleUsuario;
import com.eventosuy.servidor.EEstadoEdicion;
import com.eventosuy.utils.Utilidades;

import datatypes.DTRegistro;
import datatypes.DTUsuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
						LocalDate fechaNac = parseDate(trim(request.getParameter("fechaNac"))); // puede ser null
						Date dateFechaNac = (fechaNac != null ? java.sql.Date.valueOf(fechaNac) : null);
						
						String institucion = trim(request.getParameter("institucion"));
						puerto.altaAsistente(
								nickname,         
								password,          
								nombre,            
								email,             
								apellido,          
								Utilidades.dateToXMLGregorianCalendar(dateFechaNac), 
								institucion,
								imagen
								);


					} else {
						request.setAttribute("error", "Rol inválido.");
						request.getRequestDispatcher("/WEB-INF/views/usuarios/alta-usuario.jsp")
						.forward(request, response);
						return;
					}
					 DTDetalleUsuario u = puerto.mostrarDetalleUsuario(nickname);
			            request.setAttribute("usuario", u);
					
			            if (imagen != null && imagen.length > 0) {
			                String contentType = null;
			                try {
			                    Part p = request.getPart("imagen");
			                    contentType = (p != null ? p.getContentType() : null);
			                } catch (Exception ignore) { /* sin drama */ }
			                if (contentType == null || contentType.isBlank()) {
			                    contentType = "image/jpeg"; // fallback
			                }
			                String b64 = java.util.Base64.getEncoder().encodeToString(imagen);
			                String dataUri = "data:" + contentType + ";base64," + b64;
			                request.setAttribute("imagenDataUri", dataUri);
			            }
			            
			            HttpSession ses = request.getSession(true);
			            ses.setAttribute("nick", nickname);
			            ses.setAttribute("rol", ("organizador".equalsIgnoreCase(rol) ? "organizador" : "asistente"));     

					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/usuarios/consulta-usuario.jsp");
					dispatcher.forward(request, response);
					
					if ("organizador".equalsIgnoreCase(rol)) {
					    request.setAttribute("edicionesAceptadas", java.util.Collections.emptyList());
					    request.setAttribute("misEdiciones", java.util.Collections.emptyList());
					} else {
					    request.setAttribute("registros", java.util.Collections.emptyList());
					}
					
					 //response.sendRedirect(request.getContextPath() + "/usuario?accion=consulta&nick="+ nickname);
					 return;//redirige

				} catch (Exception ex) {
					request.setAttribute("error", safeMsg(ex, "No se pudo crear el usuario."));
					request.getRequestDispatcher("/WEB-INF/views/usuarios/alta-usuario.jsp").forward(request, response);
					return;
				}

			} else {
				Set<String> listaNombresInstitucion = (Set<String>) puerto.nombresInstituciones();
				request.setAttribute("listaNombresInstitucion", listaNombresInstitucion);
				
				request.getRequestDispatcher("/WEB-INF/views/usuarios/alta-usuario.jsp")
				.forward(request, response);
				return;
			}

			// ==================== CONSULTA USUARIO ====================
		case "consulta": {
			String nick = trim(request.getParameter("nick"));
			DTDetalleUsuario usuarioLogeado = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");

			try {
				if (isBlank(nick)) {
					// listar todos
					Collection<DTUsuario> usrs = (Collection<DTUsuario>) puerto.listarUsuarios(); 
					request.setAttribute("usuarios", usrs);
					request.getRequestDispatcher("/WEB-INF/views/usuarios/consulta-usuario.jsp").forward(request, response);
					return;

				} else {
					// ver detalle de uno

					DTDetalleUsuario u;
					try {
						u = puerto.mostrarDetalleUsuario(nick); 
					} catch (Exception e) {
						// si no exiiste el usuario
						response.sendError(404);
						request.getRequestDispatcher("/pages/error/404.jsp")
						.include(request, response);
						return;
					}

					request.setAttribute("usuario", u);
					
					byte[] imgBytes = u.getImagen(); // viene del DTO
					if (imgBytes != null && imgBytes.length > 0) {
					    String contentType = "image/jpeg";
					    String b64 = java.util.Base64.getEncoder().encodeToString(imgBytes);
					    String dataUri = "data:" + contentType + ";base64," + b64;
					    request.setAttribute("imagenDataUri", dataUri);
					}
					
					boolean esPropio = usuarioLogeado != null && usuarioLogeado.getNickname().equals(u.getNickname());
					
					// ------ ORGANIZADOR ------
					if (u instanceof DTDetalleOrganizador) {
					    java.util.List<String> aceptadas = (List<String>) puerto.getEdicionesDeOrganizadorPorEstado(u.getNickname(), EEstadoEdicion.ACEPTADA);
					    if (aceptadas == null) aceptadas = java.util.Collections.emptyList();
					    request.setAttribute("edicionesAceptadas", aceptadas);
					    
					    List<String> ingresadas = new ArrayList<>();
					    List<String> rechazadas = new ArrayList<>();
					    if (esPropio) {
					        ingresadas =
					            (List<String>) puerto.getEdicionesDeOrganizadorPorEstado(u.getNickname(), EEstadoEdicion.INGRESADA);
					        rechazadas =
					            (List<String>) puerto.getEdicionesDeOrganizadorPorEstado(u.getNickname(), EEstadoEdicion.RECHAZADA);
					        if (ingresadas == null) ingresadas = java.util.Collections.emptyList();
					        if (rechazadas == null) rechazadas = java.util.Collections.emptyList();

					    }
					    request.setAttribute("misEdicionesIngresadas", ingresadas);
					    request.setAttribute("misEdicionesRechazadas", rechazadas);
					}

					// ------ ASISTENTE ------
					if (u instanceof DTDetalleAsistente) {
					    java.util.List<DTRegistro> regs = java.util.Collections.emptyList();
					    if (esPropio) {
					        regs = (List<DTRegistro>) puerto.listarRegistrosUsuario(u.getNickname());
					        if (regs == null) regs = java.util.Collections.emptyList();
					    }
					    request.setAttribute("registros", regs);
					}
					
					
		            request.getRequestDispatcher("/WEB-INF/views/usuarios/consulta-usuario.jsp")
		                   .forward(request, response);
		            return; 
		        }

			} catch (Exception ex) {
				response.sendError(500);
				request.setAttribute("error", safeMsg(ex, "No se pudo realizar la consulta."));
				request.getRequestDispatcher("/WEB-INF/views/usuarios/consulta-usuario.jsp")
				.include(request, response);
				return;
			}
		}

		// ==================== MODIFICAR DATOS ====================
		case "modificar":
			if (isPost(request)) {
				String nickSesion = getNickFromSession(request);
				if (nickSesion == null) {
					response.sendRedirect(request.getContextPath() + "/login?error=loginRequerido");
					return;
				}
				String nombre    = trim(request.getParameter("nombre"));
				String apellido  = trim(request.getParameter("apellido"));
				String email     = trim(request.getParameter("email"));
				String nuevaPass = trim(request.getParameter("nuevaPassword"));
				byte[] imagen    = readPartBytes(request.getPart("imagen"));

				try {

					DTDetalleUsuario det;
					try {
						det = puerto.mostrarDetalleUsuario(nickSesion); 
					} catch (Exception e) {
						request.setAttribute("error", "No se pudo cargar tus datos: " + e.getMessage());
						request.getRequestDispatcher("/pages/modificar-datos-usuario.jsp").forward(request, response);
						return;
					}

					if (det instanceof DTDetalleAsistente) {
						// NO pass/imagen
						String sFecha = trim(request.getParameter("fechaNac")); 
						java.sql.Date fecha = (sFecha == null || sFecha.isBlank()) ? null : java.sql.Date.valueOf(sFecha);

						puerto.modificarDatosAsistente(
								nickSesion,
								nombre,          // nuevoNombre
								apellido,        // nuevoApellido
								Utilidades.dateToXMLGregorianCalendar(fecha)            // nuevaFechaNacimiento
								);

					} else if (det instanceof DTDetalleOrganizador) {
						// NO pass/imagen
						String descripcion = trim(request.getParameter("descripcion"));
						String web         = trim(request.getParameter("web"));

						puerto.modificarDatosOrganizador(
								nickSesion,
								nombre,          // nuevoNombre
								descripcion,     // nuevaDescripcion
								web              // nuevoLink
								);

					} else {
						// caso raro: tipo desconocido
						request.setAttribute("error", "Tipo de usuario desconocido.");
						request.getRequestDispatcher("/pages/modificar-datos-usuario.jsp").forward(request, response);
						return;
					}
					response.sendRedirect(request.getContextPath()
							+ "/usuario?accion=modificar&ok=1");
					return;

				} catch (Exception ex) {
					request.setAttribute("error", safeMsg(ex, "No se pudieron guardar los cambios."));
					request.getRequestDispatcher("/pages/modificar-datos-usuario.jsp")
					.forward(request, response);
					return;
				}

			} else {
				String nickSesion = getNickFromSession(request);
				if (nickSesion == null) {
					response.sendRedirect(request.getContextPath() + "/login?error=loginRequerido");
					return;
				}

				try {
					DTDetalleUsuario det = puerto.mostrarDetalleUsuario(nickSesion); 
					request.setAttribute("usuario", det);


					if (det instanceof DTDetalleAsistente) {
						request.setAttribute("esAsistente", true);
						String inst = puerto.getNombreInstitucionAsistente(nickSesion);
						request.setAttribute("institucion", inst);
					} else if (det instanceof DTDetalleOrganizador) {
						request.setAttribute("esOrganizador", true);
					}
				} catch (Exception e) {
					request.setAttribute("error", "No se pudieron cargar tus datos: " + safeMsg(e, ""));

					request.getRequestDispatcher("/pages/modificar-datos-usuario.jsp").forward(request, response);
					return;
				}
				request.getRequestDispatcher("/pages/modificar-datos-usuario.jsp")
				.forward(request, response);
				return;
			}

			// ==================== REGISTRO A EDICIÓN ====================
		case "registro-edicion":
			if (isPost(request)) {
				// --- Requiere login ---
				DTDetalleUsuario usuarioLogeado = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");
				if (usuarioLogeado == null) {
				    request.setAttribute("error", "Debe estar logueado para registrarse a una edición.");
				    request.getRequestDispatcher("/WEB-INF/views/ediciones/registro-edicion.jsp").forward(request, response);
				    return;
				}
				String nick = usuarioLogeado.getNickname();

				String evento    = trim(request.getParameter("evento"));        
				String idEdicion = trim(request.getParameter("idEdicion"));     
				String idTipo    = trim(request.getParameter("idTipoRegistro"));
				String codigo    = trim(request.getParameter("codigo"));        

				if (anyBlank(evento, idEdicion, idTipo)) {
					request.setAttribute("error", "Seleccioná evento, edición y tipo de registro.");

					try {
						request.setAttribute("eventos",  puerto.nombresEventos());
						if (!isBlank(evento))  request.setAttribute("ediciones", puerto.nombresEdicionesEvento(evento));
						if (!isBlank(idEdicion) && !isBlank(evento)) {
							request.setAttribute("edicion", puerto.getDTEdicion(evento, idEdicion));
							request.setAttribute("tipos",   puerto.getNombresTipoRegistro(evento, idEdicion));
						}
					} catch (Exception ignore) {}
					request.getRequestDispatcher("/WEB-INF/views/ediciones/registro-edicion.jsp").forward(request, response);
					return;
				}
				try {
					puerto.altaRegistro(nick, idTipo, idEdicion, evento, codigo, Utilidades.dateToXMLGregorianCalendar(new Date()));
					response.sendRedirect(request.getContextPath() + "/usuario?accion=consulta-registro&ok=1");
					return;

				} catch (Exception ex) {
					request.setAttribute("error", safeMsg(ex, "No fue posible registrar la edición."));                    
					request.setAttribute("eventoSel",   request.getParameter("evento"));
					request.setAttribute("edicionSel",  request.getParameter("idEdicion"));
					request.setAttribute("tipoSel",     request.getParameter("idTipoRegistro"));
					request.setAttribute("edicion", puerto.getDTEdicion(evento, idEdicion));
					request.setAttribute("tipos",   puerto.getNombresTipoRegistro(evento, idEdicion));
					request.getRequestDispatcher("/WEB-INF/views/ediciones/registro-edicion.jsp").forward(request, response);
					return;
				}

			} else {
				// --------------------- GET Wizard ---------------------

				String evento  = trim(request.getParameter("evento"));   
				String edicion = trim(request.getParameter("edicion"));  

				try {
					if (!isBlank(edicion) && !isBlank(evento)) {
						//detalle de la edicion
						request.setAttribute("eventoSel",  evento);
						request.setAttribute("edicionSel", edicion);
						request.setAttribute("edicion",    puerto.getDTEdicion(evento, edicion));          
						request.setAttribute("tipos",      puerto.getNombresTipoRegistro(evento, edicion)); 
					} else if (!isBlank(evento)) {
						//listo ediciones del evento
						request.setAttribute("eventoSel", evento);
						request.setAttribute("ediciones", puerto.nombresEdicionesEvento(evento));           // Set<String>
					} else {
						//listar eventos
						request.setAttribute("eventos", puerto.nombresEventos());                           // Set<String>
					}
				} catch (Exception e) {
					request.setAttribute("error", safeMsg(e, "No fue posible cargar la información de registro."));
				}

				request.getRequestDispatcher("/WEB-INF/views/ediciones/registro-edicion.jsp").forward(request, response);
				return;
			}

			// ==================== CONSULTA DE REGISTROS ====================
		case "consulta-registro": {
			DTDetalleUsuario datosUsuario = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");


			String evento   = trim(request.getParameter("evento"));   // organizador
			String edicion  = trim(request.getParameter("edicion"));  // organizador
			String usuarioX = trim(request.getParameter("usuario"));  // organizador
			String idxParam = trim(request.getParameter("idx"));      // asistente

			try {

				if (datosUsuario instanceof DTDetalleOrganizador) {
					// --------------------------- ORGANIZADOR ---------------------------
					if (!isBlank(evento) && !isBlank(edicion) && !isBlank(usuarioX)) {
						//  detalle de registro de un usuario en esa edición

						// Validar que la edición pertenezca al evento
						Set<String> edicionesDelEvento = (Set<String>) puerto.nombresEdicionesEvento(evento);
						if (edicionesDelEvento == null || !edicionesDelEvento.contains(edicion)) {
							request.setAttribute("error", "La edición seleccionada no pertenece al evento indicado.");
							request.setAttribute("eventoSel",  evento);
							request.setAttribute("edicionSel", edicion);
							request.setAttribute("registrados", puerto.listarRegistrosAEdicion(evento, edicion));
						} else {
							// Buscar el registro del usuario por nombre de edición
							List<DTRegistro> regsUsr = (List<DTRegistro>) puerto.listarRegistrosUsuario(usuarioX);
							Integer matchIdx = null;
							for (int i = 0; i < regsUsr.size(); i++) {
								DTRegistro r = regsUsr.get(i);
								if (edicion.equals(r.nombreEdicion())) {
									matchIdx = i;
									break;
								}
							}
							if (matchIdx == null) {
								request.setAttribute("error", "Ese usuario no tiene registro para la edición seleccionada.");
							} else {
								DTDetalleRegistro detReg = puerto.obtenerDetalleRegistro(usuarioX, matchIdx);
								request.setAttribute("detalle", detReg);
							}
							request.setAttribute("eventoSel",  evento);
							request.setAttribute("edicionSel", edicion);
							request.setAttribute("usuarioSel", usuarioX);
						}

					} else if (!isBlank(evento) && !isBlank(edicion)) {
						// : listar usuarios registrados a esa edición
						request.setAttribute("eventoSel",  evento);
						request.setAttribute("edicionSel", edicion);
						request.setAttribute("registrados", puerto.listarRegistrosAEdicion(evento, edicion));

					} else {
						//  listar ediciones del organizador
						request.setAttribute("edicionesOrg", puerto.getEventosEdicionesDeOrganizador(datosUsuario.getNickname()));
					}

				} else {
					// --------------------------- ASISTENTE ----------------------------
					if (!isBlank(idxParam)) {
						// Ver detalle de uno de mis registros por índice
						int idx = Integer.parseInt(idxParam);
						DTDetalleRegistro det = puerto.obtenerDetalleRegistro(datosUsuario.getNickname(), idx);
						request.setAttribute("detalle", det);
						request.setAttribute("idxSel", idx);
					}
					// Siempre muestro mi lista de registros
					List<DTRegistro> regs = (List<DTRegistro>) puerto.listarRegistrosUsuario(datosUsuario.getNickname());
					request.setAttribute("registros", regs);
				}

			} catch (Exception ex) {
				request.setAttribute("error", safeMsg(ex, "No se pudo completar la consulta."));
			}

			request.getRequestDispatcher("/WEB-INF/views/registros/consulta-registro.jsp")
			.forward(request, response);
			return;
		}

		// TODO: Esto no debería estar, o debería redirigir a una página de error existente
		// ==================== ACCIÓN DESCONOCIDA ====================
		default:
			response.sendError(404);
			request.getRequestDispatcher("/pages/error/404.jsp").include(request, response);
		}
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
	
}
