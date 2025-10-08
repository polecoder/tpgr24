package com.eventosuy.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import logica.IControladorUsuarios;

@WebServlet("/usuario")                     
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // imagen opcional 
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
                        request.getRequestDispatcher("/pages/Altausuario.jsp")
                               .forward(request, response);
                        return;
                    }
                    if (!password.equals(confirm)) {
                        request.setAttribute("error", "La contraseña y su confirmación no coinciden.");
                        request.getRequestDispatcher("/pages/Altausuario.jsp")
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
                            	    nickname,          // nickname ingresado
                            	    password,          // contraseña tal cual (la lógica valida formato)
                            	    nombre,            // nombre
                            	    email,             // correo electrónico
                            	    descripcion,       // descripción del organizador
                            	    web                // link o sitio web
                            	);
                            

                        } else if ("asistente".equalsIgnoreCase(rol)) {
                            String apellido    = trim(request.getParameter("apellido"));
                            LocalDate fechaNac = parseDate(trim(request.getParameter("fechaNac"))); // puede ser null
                            String institucion = trim(request.getParameter("institucion"));
                            // TODO: Fabrica.getInstance().usuarios().altaAsistente(
                            //       nickname, nombre, apellido, email, hash(password), fechaNac, institucion, imagen);

                        } else {
                            request.setAttribute("error", "Rol inválido.");
                            request.getRequestDispatcher("/pages/usuario/Altausuario.jsp")
                                   .forward(request, response);
                            return;
                        }

                        // PRG: tras POST ok, redirigimos (evita reenvío con F5)
                        response.sendRedirect(request.getContextPath() + "/usuario?accion=alta&ok=1");
                        return;

                    } catch (Exception ex) {
                        request.setAttribute("error", safeMsg(ex, "No se pudo crear el usuario."));
                        request.getRequestDispatcher("/pages/usuario/Altausuario.jsp")
                               .forward(request, response);
                        return;
                    }

                } else {
                    // GET: mostrar formulario
                    request.getRequestDispatcher("/pages/usuario/Altausuario.jsp")
                           .forward(request, response);
                    return;
                }

            // ==================== CONSULTA USUARIO(S) ====================
            case "consulta": {
                String nick = trim(request.getParameter("nick"));

                try {
                    if (isBlank(nick)) {
                        // listar todos
                        // TODO: Collection<UsuarioDTO> usrs = Fabrica.getInstance().usuarios().listar();
                        Collection<?> usrs = java.util.List.of(); // placeholder
                        request.setAttribute("usuarios", usrs);
                        request.getRequestDispatcher("/pages/usuario/Consultausuario.jsp")
                               .forward(request, response);
                    } else {
                        // ver detalle de uno
                        // TODO: UsuarioDTO u = Fabrica.getInstance().usuarios().obtener(nick);
                        Object u = null; // placeholder
                        if (u == null) {
                            response.sendError(404);
                            request.getRequestDispatcher("/pages/error/404.jsp")
                                   .include(request, response);
                            return;
                        }
                        request.setAttribute("usuario", u);
                        // Si tenés una JSP específica de perfil, cambiá aquí la ruta
                        request.getRequestDispatcher("/pages/usuario/Consultausuario.jsp")
                               .forward(request, response);
                    }
                    return;

                } catch (Exception ex) {
                    response.sendError(500);
                    request.setAttribute("error", safeMsg(ex, "No se pudo realizar la consulta."));
                    request.getRequestDispatcher("/pages/usuario/Consultausuario.jsp")
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
                        // TODO: usuarios().modificar(nickSesion, nombre, apellido, email,
                        //       isBlank(nuevaPass) ? null : hash(nuevaPass), imagen);
                        response.sendRedirect(request.getContextPath()
                                + "/usuario?accion=modificar&ok=1");
                        return;

                    } catch (Exception ex) {
                        request.setAttribute("error", safeMsg(ex, "No se pudieron guardar los cambios."));
                        request.getRequestDispatcher("/pages/usuario/ModificarDatosUsuario.jsp")
                               .forward(request, response);
                        return;
                    }

                } else {
                    String nickSesion = getNickFromSession(request);
                    if (nickSesion == null) {
                        response.sendRedirect(request.getContextPath() + "/login?error=loginRequerido");
                        return;
                    }
                    // TODO: request.setAttribute("usuario", usuarios().obtener(nickSesion));
                    request.getRequestDispatcher("/pages/usuario/ModificarDatosUsuario.jsp")
                           .forward(request, response);
                    return;
                }

            // ==================== REGISTRO A EDICIÓN ====================
            case "registro-edicion":
                if (isPost(request)) {
                    String nick      = getNickFromSession(request);
                    if (nick == null) {
                        response.sendRedirect(request.getContextPath() + "/login?error=loginRequerido");
                        return;
                    }
                    String idEdicion = trim(request.getParameter("idEdicion"));
                    String idTipo    = trim(request.getParameter("idTipoRegistro"));
                    String codigo    = trim(request.getParameter("codigo")); // opcional

                    if (anyBlank(idEdicion, idTipo)) {
                        request.setAttribute("error", "Seleccioná edición y tipo de registro.");
                        request.getRequestDispatcher("/pages/usuario/registroEdicion.jsp")
                               .forward(request, response);
                        return;
                    }

                    try {
                        // TODO: registros().registrar(nick, idEdicion, idTipo, codigo);
                        response.sendRedirect(request.getContextPath()
                                + "/usuario?accion=consulta-registro&ok=1");
                        return;

                    } catch (Exception ex) {
                        request.setAttribute("error", safeMsg(ex, "No fue posible registrar la edición."));
                        request.getRequestDispatcher("/pages/usuario/registroEdicion.jsp")
                               .forward(request, response);
                        return;
                    }

                } else {
                    // GET: cargar combos (eventos/ediciones/tipos)
                    // TODO: request.setAttribute("ediciones", ediciones().confirmadas());
                    request.getRequestDispatcher("/pages/usuario/registroEdicion.jsp")
                           .forward(request, response);
                    return;
                }

            // ==================== CONSULTA DE REGISTROS ====================
            case "consulta-registro": {
                String nick = getNickFromSession(request);
                if (nick == null) {
                    response.sendRedirect(request.getContextPath() + "/login?error=loginRequerido");
                    return;
                }
                // TODO: request.setAttribute("registros", registros().deUsuario(nick));
                request.getRequestDispatcher("/pages/usuario/consultaRegistro.jsp")
                       .forward(request, response);
                return;
            }

            // ==================== ACCIÓN DESCONOCIDA ====================
            default:
                response.sendError(404);
                request.getRequestDispatcher("/pages/error/404.jsp").include(request, response);
        }
    }

    // Delegación estilo demo
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
        Object o = req.getSession().getAttribute("usrNick"); // lo setea tu LoginServlet
        return (o == null) ? null : o.toString();
    }
}