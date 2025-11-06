<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
  <jsp:include page="/WEB-INF/views/partials/head.jsp" />

  <body class="d-flex flex-column min-vh-100">
    <jsp:include page="/WEB-INF/views/partials/header.jsp" />

    <%
      String ctx = request.getContextPath();
      String eventoSel = (String) request.getAttribute("eventoSel");
      String edicionSel = (String) request.getAttribute("edicionSel");
      String tipoSel = (String) request.getAttribute("tipoSel");
      Object tiposAttr = request.getAttribute("tipos");
      String error = (String) request.getAttribute("error");
      
      List<?> tipos = (tiposAttr instanceof List) ? (List<?>) tiposAttr : null;
    %>

    <div class="container flex-grow-1 py-4" style="max-width: 900px">
      <h1 class="text-center text-uppercase py-4 fs-2">Registro a Edición</h1>

      <main class="mx-auto p-4 bg-light border-top border-2 border-primary rounded" style="max-width: 650px;">

        <%-- ALERTA DE ERROR --%>
        <% if (error != null && !error.isBlank()) { %>
          <div class="alert alert-danger mb-4" role="alert"><%= error %></div>
        <% } %>

        <%-- SI NO VIENEN LOS PARÁMETROS --%>
        <% if (eventoSel == null || edicionSel == null) { %>
          <div class="alert alert-warning text-center">
            <p class="mb-3">Debe acceder al registro desde la consulta de una edición.</p>
            <a href="<%= ctx %>/edicion?accion=consulta" class="btn btn-primary">Ir a Consulta de Ediciones</a>
          </div>

        <%-- FORMULARIO DE REGISTRO --%>
        <% } else { %>
          <form method="post" action="<%= ctx %>/usuario" class="d-flex flex-column gap-4">
            <input type="hidden" name="accion" value="registro-edicion"/>
            <input type="hidden" name="evento" value="<%= eventoSel %>"/>
            <input type="hidden" name="idEdicion" value="<%= edicionSel %>"/>

            <%-- EVENTO (solo lectura) --%>
            <div class="d-flex justify-content-center gap-3">
              <label class="form-label text-end fw-bold" style="width: 150px;">Evento:</label>
              <input type="text" class="form-control" style="max-width: 400px;" value="<%= eventoSel %>" readonly />
            </div>

            <%-- EDICIÓN (solo lectura) --%>
            <div class="d-flex justify-content-center gap-3">
              <label class="form-label text-end fw-bold" style="width: 150px;">Edición:</label>
              <input type="text" class="form-control" style="max-width: 400px;" value="<%= edicionSel %>" readonly />
            </div>

            <%-- TIPO DE REGISTRO (obligatorio) --%>
            <div class="d-flex justify-content-center gap-3">
              <label for="idTipoRegistro" class="form-label text-end fw-bold" style="width: 150px;">
                Tipo de registro: <span class="text-danger">*</span>
              </label>
              <select id="idTipoRegistro" name="idTipoRegistro" class="form-select" style="max-width: 400px;" required>
                <option value="">-- Seleccionar tipo --</option>
                <%
                  if (tipos != null && !tipos.isEmpty()) {
                    for (Object t : tipos) {
                      String tipoNombre = String.valueOf(t);
                      boolean selected = tipoSel != null && tipoSel.equals(tipoNombre);
                %>
                  <option value="<%= tipoNombre %>" <%= selected ? "selected" : "" %>><%= tipoNombre %></option>
                <%
                    }
                  } else {
                %>
                  <option value="" disabled>No hay tipos disponibles</option>
                <%
                  }
                %>
              </select>
            </div>

            <%-- CÓDIGO DE PATROCINIO (opcional) --%>
            <div class="d-flex justify-content-center gap-3">
              <label for="codigo" class="form-label text-end" style="width: 150px;">Código patrocinio:</label>
              <input type="text" id="codigo" name="codigo" class="form-control" style="max-width: 400px;" 
                     placeholder="Opcional" maxlength="50"/>
            </div>

            <%-- BOTONES --%>
            <div class="text-end mt-3">
              <a class="btn btn-secondary" href="<%= ctx %>/edicion?accion=consulta&evento=<%= java.net.URLEncoder.encode(eventoSel, "UTF-8") %>&nombre_edicion=<%= java.net.URLEncoder.encode(edicionSel, "UTF-8") %>">
                Cancelar
              </a>
              <button type="submit" class="btn btn-primary ms-2">Registrarme</button>
            </div>
          </form>
        <% } %>

      </main>
    </div>

    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>