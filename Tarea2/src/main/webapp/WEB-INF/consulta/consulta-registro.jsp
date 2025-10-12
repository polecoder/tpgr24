<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="datatypes.DTDetalleRegistro" %>
<!DOCTYPE html>
<html lang="es">
  <!-- HEAD GLOBAL -->
  <jsp:include page="/WEB-INF/templates/head.jsp" />

  <body class="d-flex flex-column min-vh-100">

    <!-- HEADER GLOBAL -->
    <jsp:include page="/WEB-INF/templates/header.jsp" />

    <%
      String ctx = request.getContextPath();
      String error = (String) request.getAttribute("error");

      // Atributos que setea el servlet en ambos flujos
      Object detalleAttr    = request.getAttribute("detalle");       // DTDetalleRegistro (opcional)
      Object regsAsisAttr   = request.getAttribute("registros");     // List<DTRegistro> (asistente)
      Object edsOrgAttr     = request.getAttribute("edicionesOrg");  // List<DTEventoEdicion> (organizador)
      Object regsEdAttr     = request.getAttribute("registrados");   // Set<String> usuarios registrados (organizador)

      String eventoSel  = (String) request.getAttribute("eventoSel");  // organizador
      String edicionSel = (String) request.getAttribute("edicionSel"); // organizador
      String usuarioSel = (String) request.getAttribute("usuarioSel"); // organizador

      // Título según contexto
      String titulo = "Consulta de Registro";
      if (regsAsisAttr instanceof List) titulo = "Mis Registros";
    %>

    <div class="container flex-grow-1">
      <div class="mx-auto" style="max-width: 768px">
        <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3"><%= titulo %></h1>
      </div>

      <div class="d-flex justify-content-between">
        <main class="mx-auto p-4 bg-light border-top border-2 border-primary rounded" style="max-width: 600px">

          <%-- ALERTA --%>
          <% if (error != null && !error.isBlank()) { %>
            <div class="alert alert-danger mb-4" role="alert"><%= error %></div>
          <% } %>

          <%
            // ======================== ASISTENTE ========================
            if (regsAsisAttr instanceof List) {
          %>
              <%-- Lista de mis registros (links por índice) --%>
              <div class="mb-4">
                <p class="fw-bold mb-2">Mis ediciones registradas:</p>
                <ul class="list-group">
                  <%
                    List<?> regs = (List<?>) regsAsisAttr; // List<DTRegistro> con nombreEdicion() y nombreTipo()
                    if (regs.isEmpty()) {
                  %>
                      <li class="list-group-item text-muted">No tenés registros todavía.</li>
                  <%
                    } else {
                      for (int i = 0; i < regs.size(); i++) {
                        Object r = regs.get(i);
                        String nomEd  = "";
                        String nomTipo= "";
                        try {
                          java.lang.reflect.Method mEd  = r.getClass().getMethod("nombreEdicion");
                          java.lang.reflect.Method mTr  = r.getClass().getMethod("nombreTipo");
                          nomEd   = String.valueOf(mEd.invoke(r));
                          nomTipo = String.valueOf(mTr.invoke(r));
                        } catch (Exception ignore) {}
                  %>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                          <span>
                            <strong><%= nomEd %></strong>
                            <% if (nomTipo != null && !nomTipo.isBlank()) { %>
                              <span class="text-muted"> · <%= nomTipo %></span>
                            <% } %>
                          </span>
                          <a class="btn btn-primary btn-sm"
                             href="<%= ctx %>/usuario?accion=consulta-registro&idx=<%= i %>">Ver detalle</a>
                        </li>
                  <%
                      }
                    }
                  %>
                </ul>
              </div>

              <%-- Detalle seleccionado (si viene) --%>
              <%
                if (detalleAttr != null) {
	              	DTDetalleRegistro dtDetalle = (DTDetalleRegistro) detalleAttr;
	              	String nomEd = dtDetalle.getNombreEdicion();
	              	Date fecha =  dtDetalle.getFecha();
	              	String nomTipo = dtDetalle.getNombreTipo();
	              	float patrocinio = dtDetalle.getCosto();
              %>
	                <h2 class="fs-5 mb-4 text-center text-uppercase">Detalle del Registro:</h2>
	                <p><strong>Edición:</strong> <%= nomEd %></p>
	                <p><strong>Fecha de registro:</strong> <%= fecha %></p>
	                <p><strong>Tipo de registro:</strong> <%= nomTipo %></p>
	                <p><strong>Patrocinio:</strong> <%= patrocinio %></p>
              <% } %>
          	<%
            // ======================== ORGANIZADOR ========================
            	} else {
              // 1) Si no hay seleccionadas evento/edición/usuario, espero lista de mis ediciones (List<DTEventoEdicion>)
              if (edsOrgAttr instanceof List && (eventoSel == null || eventoSel.isBlank())) {
          %>
                <form method="get" action="<%= ctx %>/usuario" class="d-flex flex-column gap-3">
                  <input type="hidden" name="accion" value="consulta-registro"/>
                  <p class="fw-bold">Seleccioná una edición:</p>
                  <div class="list-group">
                    <%
                      List<?> eds = (List<?>) edsOrgAttr; // cada item tiene getNombreEvento()/getNombre()
                      if (eds.isEmpty()) {
                    %>
                        <div class="list-group-item text-muted">No tenés ediciones asociadas.</div>
                    <%
                      } else {
                        for (Object e : eds) {
                          String ev = "", ed = "";
                          try {
                            // soporte a record u objeto con getters
                            java.lang.reflect.Method mEv = null, mEd = null;
                            try { mEv = e.getClass().getMethod("nombreEvento"); } catch (NoSuchMethodException ignore) {}
                            try { mEd = e.getClass().getMethod("nombre"); } catch (NoSuchMethodException ignore) {}
                            if (mEv == null) mEv = e.getClass().getMethod("getNombreEvento");
                            if (mEd == null) mEd = e.getClass().getMethod("getNombre");
                            ev = String.valueOf(mEv.invoke(e));
                            ed = String.valueOf(mEd.invoke(e));
                          } catch (Exception ignore) {}
                    %>
                          <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                             href="<%= ctx %>/usuario?accion=consulta-registro&evento=<%= java.net.URLEncoder.encode(ev, "UTF-8") %>&edicion=<%= java.net.URLEncoder.encode(ed, "UTF-8") %>">
                            <span><strong><%= ev %></strong> — <%= ed %></span>
                            <span class="btn btn-primary btn-sm">Seleccionar</span>
                          </a>
                    <%
                        }
                      }
                    %>
                  </div>
                </form>

          <%
              // 2) Con eventoSel+edicionSel, listar usuarios registrados
              } else if (eventoSel != null && !eventoSel.isBlank()
                      && edicionSel != null && !edicionSel.isBlank()
                      && regsEdAttr instanceof Set && (usuarioSel == null || usuarioSel.isBlank())) {
          %>
                <div class="mb-3">
                  <p><strong>Evento:</strong> <%= eventoSel %></p>
                  <p><strong>Edición:</strong> <%= edicionSel %></p>
                </div>

                <p class="fw-bold">Usuarios registrados:</p>
                <ul class="list-group mb-4">
                  <%
                    Set<?> regsEd = (Set<?>) regsEdAttr; // Set<String> nicknames
                    if (regsEd.isEmpty()) {
                  %>
                      <li class="list-group-item text-muted">No hay usuarios registrados en esta edición.</li>
                  <%
                    } else {
                      for (Object u : regsEd) {
                        String nickU = String.valueOf(u);
                  %>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                          <span><%= nickU %></span>
                          <a class="btn btn-primary btn-sm"
                             href="<%= ctx %>/usuario?accion=consulta-registro&evento=<%= java.net.URLEncoder.encode(eventoSel, "UTF-8") %>&edicion=<%= java.net.URLEncoder.encode(edicionSel, "UTF-8") %>&usuario=<%= java.net.URLEncoder.encode(nickU, "UTF-8") %>">
                            Ver detalle
                          </a>
                        </li>
                  <%
                      }
                    }
                  %>
                </ul>

                <div class="text-end">
                  <a class="btn btn-secondary" href="<%= ctx %>/usuario?accion=consulta-registro">Volver</a>
                </div>

          <%
              // 3) Con eventoSel+edicionSel+usuarioSel, mostrar detalle del registro
              } else if (usuarioSel != null && !usuarioSel.isBlank() && detalleAttr != null) {
                String nomEd   = "", nickAsis = "", nomTipo = "", fecha = "", costo = "";
                try {
                  Class<?> c = detalleAttr.getClass();
                  java.lang.reflect.Method mEd   = c.getMethod("nombreEdicion");
                  java.lang.reflect.Method mNick = c.getMethod("nicknameAsistente");
                  java.lang.reflect.Method mTipo = c.getMethod("nombreTipo");
                  java.lang.reflect.Method mFec  = c.getMethod("fechaRegistro");
                  java.lang.reflect.Method mCos  = c.getMethod("costo");
                  nomEd   = String.valueOf(mEd.invoke(detalleAttr));
                  nickAsis= String.valueOf(mNick.invoke(detalleAttr));
                  nomTipo = String.valueOf(mTipo.invoke(detalleAttr));
                  Object fecObj = mFec.invoke(detalleAttr);
                  Object cosObj = mCos.invoke(detalleAttr);
                  fecha = (fecObj != null ? String.valueOf(fecObj) : "");
                  costo = (cosObj != null ? String.valueOf(cosObj) : "");
                } catch (Exception ignore) {}
                boolean patrocinio = false;
                try { patrocinio = !costo.isBlank() && Double.parseDouble(costo) == 0.0; } catch (Exception ignore) {}
          %>
                <div class="mb-3">
                  <p><strong>Evento:</strong> <%= eventoSel %></p>
                  <p><strong>Edición:</strong> <%= edicionSel %></p>
                </div>

                <h2 class="fs-5 mb-4 text-center text-uppercase">Detalle del Registro:</h2>
                <p><strong>Usuario:</strong> <%= nickAsis %></p>
                <p><strong>Fecha de registro:</strong> <%= fecha %></p>
                <p><strong>Tipo de registro:</strong> <%= nomTipo %></p>
                <p><strong>Patrocinio:</strong> <%= patrocinio ? "Se utilizó código (costo 0)" : "No se utilizó patrocinio" %></p>

                <div class="text-end mt-4">
                  <a class="btn btn-secondary"
                     href="<%= ctx %>/usuario?accion=consulta-registro&evento=<%= java.net.URLEncoder.encode(eventoSel, "UTF-8") %>&edicion=<%= java.net.URLEncoder.encode(edicionSel, "UTF-8") %>">
                    Volver
                  </a>
                </div>

          <%
              // 4) Sin datos (fallback)
              } else {
          %>
                <div class="alert alert-warning">No hay información para mostrar.</div>
                <div class="text-end">
                  <a class="btn btn-primary" href="<%= ctx %>/usuario?accion=consulta-registro">Ir a mis ediciones</a>
                </div>
          <%
              }
            }
          %>

        </main>
      </div>
    </div>

    <!-- FOOTER GLOBAL -->
    <jsp:include page="/WEB-INF/templates/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
