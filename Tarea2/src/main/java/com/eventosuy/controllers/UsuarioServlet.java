package com.eventosuy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import datatypes.DTDetalleAsistente;
import datatypes.DTDetalleOrganizador;
import datatypes.DTDetalleRegistro;
import datatypes.DTDetalleUsuario;
import datatypes.DTRegistro;
import datatypes.DTUsuario;
import datatypes.EEstadoEdicion;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import logica.Fabrica;
import logica.IControladorEventos;
import logica.IControladorUsuarios;

@MultipartConfig 
@WebServlet("/usuario")                     
//@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // imagen opcional 
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
				String confirm   = trim(request.getParameter("confirm"));

				if (anyBlank(rol, nickname, nombre, email, password)) {
					request.setAttribute("error", "Faltan campos obligatorios.");
					request.getRequestDispatcher("/WEB-INF/alta/alta-usuario.jsp")
					.forward(request, response);
					return;
				}
				if (!password.equals(confirm)) {
					request.setAttribute("error", "La contraseña y su confirmación no coinciden.");
					request.getRequestDispatcher("/WEB-INF/alta/alta-usuario.jsp")
					.forward(request, response);
					return;
				}

				byte[] imagen = readPartBytes(request.getPart("imagen")); 

				try {
					if ("organizador".equalsIgnoreCase(rol)) {
						String descripcion = trim(request.getParameter("descripcion"));
						String web         = trim(request.getParameter("web"));
						IControladorUsuarios ctrl = Fabrica.getInstancia().getControladorUsuarios();
						ctrl.altaOrganizador(
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
						String institucion = trim(request.getParameter("institucion"));
						IControladorUsuarios ctrl = Fabrica.getInstancia().getControladorUsuarios();
						ctrl.altaAsistente(
								nickname,         
								password,          
								nombre,            
								email,             
								apellido,          
								(fechaNac != null ? java.sql.Date.valueOf(fechaNac) : null), 
								institucion,
								imagen
								);


					} else {
						request.setAttribute("error", "Rol inválido.");
						request.getRequestDispatcher("/WEB-INF/alta/alta-usuario.jsp")
						.forward(request, response);
						return;
						// redirigir a pag de error
					}
					IControladorUsuarios cu = Fabrica.getInstancia().getControladorUsuarios();
					 DTDetalleUsuario u = cu.mostrarDetalleUsuario(nickname);
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

					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/consulta/consulta-usuario.jsp");
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
					request.getRequestDispatcher("/WEB-INF/alta/alta-usuario.jsp")
					.forward(request, response);
					return;
				}

			} else {
				request.getRequestDispatcher("/WEB-INF/alta/alta-usuario.jsp")
				.forward(request, response);
				return;
			}

			// ==================== CONSULTA USUARIO ====================
		case "consulta": {
			String nick = trim(request.getParameter("nick"));
			DTDetalleUsuario usuarioLogeado = (DTDetalleUsuario) request.getSession().getAttribute("usuarioLogeado");

			try {
				IControladorUsuarios cu = Fabrica.getInstancia().getControladorUsuarios();
				 IControladorEventos  ce = Fabrica.getInstancia().getControladorEvento();
			    //System.out.println("[consulta] param nick = " + nick);

				if (isBlank(nick)) {
					// listar todos
					Collection<DTUsuario> usrs = cu.listarUsuarios(); 
					request.setAttribute("usuarios", usrs);
					request.getRequestDispatcher("/WEB-INF/consulta/consulta-usuario.jsp").forward(request, response);
					return;

				} else {
					// ver detalle de uno

					DTDetalleUsuario u;
					try {
						u = cu.mostrarDetalleUsuario(nick); 
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
					    java.util.List<String> aceptadas =
					        ce.getEdicionesDeOrganizadorPorEstado(u.getNickname(), EEstadoEdicion.Aceptada);
					    if (aceptadas == null) aceptadas = java.util.Collections.emptyList();
					    request.setAttribute("edicionesAceptadas", aceptadas);
					    
					    List<String> ingresadas = new ArrayList<>();
					    List<String> rechazadas = new ArrayList<>();
					    if (esPropio) {
					        ingresadas =
					            ce.getEdicionesDeOrganizadorPorEstado(u.getNickname(), EEstadoEdicion.Ingresada);
					        rechazadas =
					            ce.getEdicionesDeOrganizadorPorEstado(u.getNickname(), EEstadoEdicion.Rechazada);
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
					        regs = cu.listarRegistrosUsuario(u.getNickname());
					        if (regs == null) regs = java.util.Collections.emptyList();
					    }
					    request.setAttribute("registros", regs);
					}
					
					
		            request.getRequestDispatcher("/WEB-INF/consulta/consulta-usuario.jsp")
		                   .forward(request, response);
		            return; 
		        }

			} catch (Exception ex) {
				response.sendError(500);
				request.setAttribute("error", safeMsg(ex, "No se pudo realizar la consulta."));
				request.getRequestDispatcher("/WEB-INF/consulta/consulta-usuario.jsp")
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
					IControladorUsuarios ctrl = Fabrica.getInstancia().getControladorUsuarios();

					DTDetalleUsuario det;
					try {
						det = ctrl.mostrarDetalleUsuario(nickSesion); 
					} catch (Exception e) {
						request.setAttribute("error", "No se pudo cargar tus datos: " + e.getMessage());
						request.getRequestDispatcher("/pages/modificar-datos-usuario.jsp").forward(request, response);
						return;
					}

					if (det instanceof DTDetalleAsistente) {
						// NO pass/imagen
						String sFecha = trim(request.getParameter("fechaNac")); 
						java.sql.Date fecha = (sFecha == null || sFecha.isBlank()) ? null : java.sql.Date.valueOf(sFecha);

						ctrl.modificarDatosAsistente(
								nickSesion,
								nombre,          // nuevoNombre
								apellido,        // nuevoApellido
								fecha            // nuevaFechaNacimiento
								);

					} else if (det instanceof DTDetalleOrganizador) {
						// NO pass/imagen
						String descripcion = trim(request.getParameter("descripcion"));
						String web         = trim(request.getParameter("web"));

						ctrl.modificarDatosOrganizador(
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
				IControladorUsuarios ctrl = Fabrica.getInstancia().getControladorUsuarios();

				try {
					DTDetalleUsuario det = ctrl.mostrarDetalleUsuario(nickSesion); 
					request.setAttribute("usuario", det);


					if (det instanceof DTDetalleAsistente) {
						request.setAttribute("esAsistente", true);
						String inst = ctrl.getNombreInstitucionAsistente(nickSesion);
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
				String nick = getNickFromSession(request);
				if (nick == null) {
					request.setAttribute("error_message", "Debe estae logueado para registrarse a una edicion");
					response.sendRedirect(request.getContextPath() + "/error.jsp");
					return;
				}

				String evento    = trim(request.getParameter("evento"));        
				String idEdicion = trim(request.getParameter("idEdicion"));     
				String idTipo    = trim(request.getParameter("idTipoRegistro"));
				String codigo    = trim(request.getParameter("codigo"));        

				if (anyBlank(evento, idEdicion, idTipo)) {
					request.setAttribute("error", "Seleccioná evento, edición y tipo de registro.");

					try {
						IControladorEventos ce = Fabrica.getInstancia().getControladorEvento();
						request.setAttribute("eventos",  ce.nombresEventos());
						if (!isBlank(evento))  request.setAttribute("ediciones", ce.nombresEdicionesEvento(evento));
						if (!isBlank(idEdicion) && !isBlank(evento)) {
							request.setAttribute("edicion", ce.getDTEdicion(evento, idEdicion));
							request.setAttribute("tipos",   ce.getNombresTipoRegistro(evento, idEdicion));
						}
					} catch (Exception ignore) {}
					request.getRequestDispatcher("/WEB-INF/alta/registro-edicion.jsp").forward(request, response);
					return;
				}

				try {
					IControladorEventos ce = Fabrica.getInstancia().getControladorEvento();

					try {
						boolean ok = ce.validar(nick, idEdicion, idTipo, evento);
						if (!ok) {
							request.setAttribute("error", "La validación del registro falló.");
							request.getRequestDispatcher("/WEB-INF/alta/registro-edicion.jsp").forward(request, response);
							return;
						}
					} catch (Exception e) {
						request.setAttribute("error", "Error validando el registro: " + safeMsg(e, ""));
						request.getRequestDispatcher("/WEB-INF/alta/registro-edicion.jsp").forward(request, response);
						return;
					}


					java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());
					ce.altaRegistro(nick, idTipo, idEdicion, evento, codigo, hoy);


					response.sendRedirect(request.getContextPath() + "/usuario?accion=consulta-registro&ok=1");
					return;

				} catch (Exception ex) {
					request.setAttribute("error", safeMsg(ex, "No fue posible registrar la edición."));                    
					request.setAttribute("eventoSel",   request.getParameter("evento"));
					request.setAttribute("edicionSel",  request.getParameter("idEdicion"));
					request.setAttribute("tipoSel",     request.getParameter("idTipoRegistro"));
					request.getRequestDispatcher("/WEB-INF/alta/registro-edicion.jsp").forward(request, response);
					return;
				}

			} else {
				// --------------------- GET Wizard ---------------------
				IControladorEventos ce = Fabrica.getInstancia().getControladorEvento();

				String evento  = trim(request.getParameter("evento"));   
				String edicion = trim(request.getParameter("edicion"));  

				try {
					if (!isBlank(edicion) && !isBlank(evento)) {
						//detalle de la edicion
						request.setAttribute("eventoSel",  evento);
						request.setAttribute("edicionSel", edicion);
						request.setAttribute("edicion",    ce.getDTEdicion(evento, edicion));          
						request.setAttribute("tipos",      ce.getNombresTipoRegistro(evento, edicion)); 
					} else if (!isBlank(evento)) {
						//listo ediciones del evento
						request.setAttribute("eventoSel", evento);
						request.setAttribute("ediciones", ce.nombresEdicionesEvento(evento));           // Set<String>
					} else {
						//listar eventos
						request.setAttribute("eventos", ce.nombresEventos());                           // Set<String>
					}
				} catch (Exception e) {
					request.setAttribute("error", safeMsg(e, "No fue posible cargar la información de registro."));
				}

				request.getRequestDispatcher("/WEB-INF/alta/registro-edicion.jsp").forward(request, response);
				return;
			}

			// ==================== CONSULTA DE REGISTROS ====================
		case "consulta-registro": {
			String nick = getNickFromSession(request);
			if (nick == null) {
				response.sendRedirect(request.getContextPath() + "/login?error=loginRequerido");
				return;
			}

			IControladorUsuarios cu = Fabrica.getInstancia().getControladorUsuarios();
			IControladorEventos  ce = Fabrica.getInstancia().getControladorEvento();

			String evento   = trim(request.getParameter("evento"));   // organizador
			String edicion  = trim(request.getParameter("edicion"));  // organizador
			String usuarioX = trim(request.getParameter("usuario"));  // organizador
			String idxParam = trim(request.getParameter("idx"));      // asistente

			try {
				// Organizador o Asistente
				DTDetalleUsuario detalle = cu.mostrarDetalleUsuario(nick);

				if (detalle instanceof DTDetalleOrganizador) {
					// --------------------------- ORGANIZADOR ---------------------------
					if (!isBlank(evento) && !isBlank(edicion) && !isBlank(usuarioX)) {
						//  detalle de registro de un usuario en esa edición

						// Validar que la edición pertenezca al evento
						Set<String> edicionesDelEvento = ce.nombresEdicionesEvento(evento);
						if (edicionesDelEvento == null || !edicionesDelEvento.contains(edicion)) {
							request.setAttribute("error", "La edición seleccionada no pertenece al evento indicado.");
							request.setAttribute("eventoSel",  evento);
							request.setAttribute("edicionSel", edicion);
							request.setAttribute("registrados", ce.listarRegistrosAEdicion(evento, edicion));
						} else {
							// Buscar el registro del usuario por nombre de edición
							List<DTRegistro> regsUsr = cu.listarRegistrosUsuario(usuarioX);
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
								DTDetalleRegistro detReg = cu.obtenerDetalleRegistro(usuarioX, matchIdx);
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
						request.setAttribute("registrados", ce.listarRegistrosAEdicion(evento, edicion));

					} else {
						//  listar ediciones del organizador
						request.setAttribute("edicionesOrg", cu.getEventosEdicionesDeOrganizador(nick));
					}

				} else {
					// --------------------------- ASISTENTE ----------------------------
					if (!isBlank(idxParam)) {
						// Ver detalle de uno de mis registros por índice
						int idx = Integer.parseInt(idxParam);
						DTDetalleRegistro det = cu.obtenerDetalleRegistro(nick, idx);
						request.setAttribute("detalle", det);
						request.setAttribute("idxSel", idx);
					}
					// Siempre muestro mi lista de registros
					List<DTRegistro> regs = cu.listarRegistrosUsuario(nick);
					request.setAttribute("registros", regs);
				}

			} catch (Exception ex) {
				request.setAttribute("error", safeMsg(ex, "No se pudo completar la consulta."));
			}

			request.getRequestDispatcher("/WEB-INF/consulta/consulta-registro.jsp")
			.forward(request, response);
			return;
		}

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
		Object o = req.getSession().getAttribute("nick"); // lo setea tu en action=alta
		return (o == null) ? null : o.toString();
	}
}