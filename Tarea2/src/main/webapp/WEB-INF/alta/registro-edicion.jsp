<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
  <!-- HEAD GLOBAL -->
  <jsp:include page="/WEB-INF/templates/head.jsp" />

  <body class="d-flex flex-column min-vh-100">

    <!-- HEADER GLOBAL -->
    <jsp:include page="/WEB-INF/templates/header.jsp" />

    <%
      String ctx = request.getContextPath();

      // Atributos que setea el servlet
      Object eventosAttr   = request.getAttribute("eventos");   // Set<String>
      Object edicionesAttr = request.getAttribute("ediciones"); // Set<String>
      Object edicionDet    = request.getAttribute("edicion");   // DTEdicionEvento (opcional)
      Object tiposAttr     = request.getAttribute("tipos");     // Collection<String>

      // Selecciones (el servlet suele setear eventoSel / edicionSel)
      String eventoSel  = (String) request.getAttribute("eventoSel");
      String edicionSel = (String) request.getAttribute("edicionSel");

      // Fallback a parámetros si no hay atributos
      if (eventoSel == null || eventoSel.isBlank())  eventoSel  = request.getParameter("evento");
      if (edicionSel == null || edicionSel.isBlank()) {
    	  edicionSel = (request.getParameter("idEdicion") != null && !request.getParameter("idEdicion").isBlank())
    	                 ? request.getParameter("idEdicion")
    	                 : request.getParameter("edicion");
    	}

      String error = (String) request.getAttribute("error");
    %>

    <div class="container flex-grow-1 py-4" style="max-width: 900px">
      <div class="mx-auto" style="max-width: 768px">
        <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">Registro a Edición de Evento</h1>
      </div>

      <main class="mx-auto p-4 bg-light border-top border-2 border-primary rounded" style="min-width: 500px; max-width: 650px;">

        <%-- ALERTAS --%>
        <% if (error != null && !error.isBlank()) { %>
          <div class="alert alert-danger mb-4" role="alert"><%= error %></div>
        <% } %>

        <%-- ================= PASO 1: LISTAR EVENTOS ================= --%>
        <% if (eventosAttr instanceof Set && (eventoSel == null || eventoSel.isBlank())) { %>
          <form method="get" action="<%= ctx %>/usuario" class="d-flex flex-column gap-4">
            <input type="hidden" name="accion" value="registro-edicion"/>
            <div class="d-flex justify-content-center gap-3">
              <label for="evento" class="form-label text-end" style="width: 120px;">Evento</label>
              <select id="evento" name="evento" class="form-select" style="max-width: 400px;" required>
                <option value="" selected disabled>-- seleccionar --</option>
                <%
                  for (String ev : (Set<String>) eventosAttr) {
                %>
                  <option value="<%= ev %>"><%= ev %></option>
                <% } %>
              </select>
            </div>

            <div class="text-end">
              <button type="submit" class="btn btn-primary">Continuar</button>
            </div>
          </form>

        <%-- ============ PASO 2: LISTAR EDICIONES DEL EVENTO ============ --%>
        <% } else if (edicionesAttr instanceof Set && (edicionSel == null || edicionSel.isBlank())) { %>
          <form method="get" action="<%= ctx %>/usuario" class="d-flex flex-column gap-4">
            <input type="hidden" name="accion" value="registro-edicion"/>
            <input type="hidden" name="evento" value="<%= eventoSel %>"/>

            <div class="d-flex justify-content-center gap-3">
              <label class="form-label text-end" style="width: 120px;">Evento</label>
              <input class="form-control" style="max-width: 400px;" value="<%= eventoSel %>" readonly />
            </div>

            <div class="d-flex justify-content-center gap-3">
              <label for="edicion" class="form-label text-end" style="width: 120px;">Edición</label>
              <select id="edicion" name="edicion" class="form-select" style="max-width: 400px;" required>
                <option value="" selected disabled>-- seleccionar --</option>
                <%
                  for (String ed : (Set<String>) edicionesAttr) {
                %>
                  <option value="<%= ed %>"><%= ed %></option>
                <% } %>
              </select>
            </div>

            <div class="text-end">
              <a class="btn btn-secondary" href="<%= ctx %>/usuario?accion=registro-edicion">Cancelar</a>
              <button type="submit" class="btn btn-primary ms-2">Continuar</button>
            </div>
          </form>

        <%-- ======= PASO 3: DETALLE EDICIÓN + TIPOS + FORM DE REGISTRO ======= --%>
        <% } else { %>
          <%
            // Esperamos tener eventoSel, edicionSel, edicionDet y tiposAttr
            Collection<?> tipos = null;
            if (tiposAttr instanceof Collection) tipos = (Collection<?>) tiposAttr;
          %>

          <form method="post" action="<%= ctx %>/usuario?accion=registro-edicion" class="d-flex flex-column gap-4" novalidate>
            <%-- OBLIGATORIOS PARA EL POST --%>
            <input type="hidden" name="evento" value="<%= (eventoSel!=null?eventoSel:"") %>"/>
            <input type="hidden" name="idEdicion" value="<%= (edicionSel!=null?edicionSel:"") %>"/>

            <div class="d-flex justify-content-center gap-3">
              <label class="form-label text-end" style="width: 120px;">Evento</label>
              <input class="form-control" style="max-width: 400px;" value="<%= (eventoSel!=null?eventoSel:"") %>" readonly />
            </div>

            <div class="d-flex justify-content-center gap-3">
              <label class="form-label text-end" style="width: 120px;">Edición</label>
              <input class="form-control" style="max-width: 400px;" value="<%= (edicionSel!=null?edicionSel:"") %>" readonly />
            </div>

            <%-- Detalle de la edición (si querés mostrar más datos del DTO, podés extraerlos por reflexión) --%>
            <%-- Ej: fechas, ciudad, país, etc. Si no los conocés, se omite. --%>

            <div class="d-flex justify-content-center gap-3">
              <label for="idTipoRegistro" class="form-label text-end" style="width: 120px;">Tipo de registro</label>
              <select id="idTipoRegistro" name="idTipoRegistro" class="form-select" style="max-width: 400px;" required>
                <option value="" selected disabled>-- seleccionar --</option>
                <%
                  if (tipos != null) {
                    for (Object t : tipos) {
                      String tr = String.valueOf(t);
                %>
                    <option value="<%= tr %>"><%= tr %></option>
                <%
                    }
                  }
                %>
              </select>
            </div>

            <div class="d-flex justify-content-center gap-3">
              <label for="codigo" class="form-label text-end" style="width: 120px;">Código patrocinio</label>
              <input type="text" id="codigo" name="codigo" class="form-control form-control-sm" style="max-width: 400px;"/>
            </div>

            <div class="text-end">
              <a class="btn btn-secondary" href="<%= ctx %>/usuario?accion=registro-edicion">Cancelar</a>
              <button type="submit" class="btn btn-primary ms-2">Aceptar</button>
            </div>
          </form>

        <% } %>

      </main>
    </div>

    <!-- FOOTER GLOBAL -->
    <jsp:include page="/WEB-INF/templates/footer.jsp" />

    <!-- JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>