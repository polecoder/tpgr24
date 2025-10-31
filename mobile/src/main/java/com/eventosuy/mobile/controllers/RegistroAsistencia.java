package com.eventosuy.mobile.controllers;

import java.io.IOException;
import java.util.List;

import com.eventosuy.mobile.models.TipoUsuario;

import datatypes.DTDetalleRegistro;
import datatypes.DTDetalleUsuario;
import datatypes.DTRegistro;
import excepciones.RegistroNoExiste;
import excepciones.UsuarioNoExiste;
import excepciones.YaAsisteAEdicion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Fabrica;
import logica.IControladorUsuarios;

@WebServlet("/mobile/registro-asistencia")
public class RegistroAsistencia extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IControladorUsuarios cu() {
        return Fabrica.getInstancia().getControladorUsuarios();
    }

    private String obtenerNick(HttpServletRequest req) {
        DTDetalleUsuario u = (DTDetalleUsuario) req.getSession().getAttribute("usuarioLogeado");
        return (u != null) ? u.getNickname() : null;
    }

    private boolean esAsistente(HttpSession s) {
        TipoUsuario t = (TipoUsuario) s.getAttribute("tipoUsuarioLogeado");
        return t != null && t == TipoUsuario.ASISTENTE;
    }

    // ---------- GET ----------
    // Sin parámetros => lista de registros
    // accion=detalle&indice=i => detalle de un registro
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession ses = req.getSession(false);
        if (ses == null || !esAsistente(ses)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String nick = obtenerNick(req);
        String accion = req.getParameter("accion");

        try {
            if ("detalle".equalsIgnoreCase(accion)) {
                // Mostrar detalle de un registro
                int indice = Integer.parseInt(req.getParameter("indice"));
                DTDetalleRegistro det = cu().obtenerDetalleRegistro(nick, indice);

                req.setAttribute("detalle", det);
                req.setAttribute("indice", indice); // lo uso luego para el POST
            } else {
                // Mostrar lista de registros
                List<DTRegistro> registros = cu().listarRegistrosUsuario(nick);
                req.setAttribute("registros", registros);
            }
            req.getRequestDispatcher("/WEB-INF/views/usuarios/registro-asistencia.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Índice inválido.");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    // ---------- POST ----------
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    	var ses = req.getSession(false);
        if (ses == null || !esAsistente(ses)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String accion = req.getParameter("accion");
        if (!"marcar".equalsIgnoreCase(accion)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String nick = obtenerNick(req);

        try {
            int indice = Integer.parseInt(req.getParameter("indice"));

            // intenta marcar asistencia
            cu().marcarAsistencia(nick, indice);

            // si esta ok
            var registros = cu().listarRegistrosUsuario(nick);
            req.setAttribute("registros", registros);
            req.setAttribute("flashOK", "Asistencia registrada correctamente.");
            req.getRequestDispatcher("/WEB-INF/views/usuarios/registro-asistencia.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Índice inválido.");
        } catch (excepciones.YaAsisteAEdicion e) {
            // Ya estaba marcada entonces vuelve al detalle con aviso
            volverADetalleConAviso(req, resp, nick, "Ya habías marcado asistencia para este registro.", false);
        } catch (IllegalStateException e) {
            // todavia no empezó la edición
            volverADetalleConAviso(req, resp, nick, "Aún no empezó la edición: no podés marcar asistencia.", true);
        } catch (excepciones.UsuarioNoExiste | excepciones.RegistroNoExiste e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al marcar asistencia.");
        }
    }

    //funcion para volver con los datos caragdos 
    private void volverADetalleConAviso(HttpServletRequest req, HttpServletResponse resp, String nick, String msg, boolean esError)
            throws ServletException, IOException {
        int indice = Integer.parseInt(req.getParameter("indice"));
        var det = cu().obtenerDetalleRegistro(nick, indice);
        req.setAttribute("detalle", det);
        req.setAttribute("indice", indice);
        if (esError) req.setAttribute("flashERR", msg);
        else req.setAttribute("flashWARN", msg);
        req.getRequestDispatcher("/WEB-INF/views/usuarios/registro-asistencia.jsp").forward(req, resp);
    }
}