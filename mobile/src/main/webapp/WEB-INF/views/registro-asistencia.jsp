<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, datatypes.*" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/partials/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100">
  <jsp:include page="/WEB-INF/views/partials/header.jsp" />

  <div class="container py-3 flex-grow-1">

    <!-- Mensajes -->
    <% if (request.getAttribute("flashOK") != null) { %>
      <div class="alert alert-success"><%= request.getAttribute("flashOK") %></div>
    <% } %>
    <% if (request.getAttribute("flashWARN") != null) { %>
      <div class="alert alert-warning"><%= request.getAttribute("flashWARN") %></div>
    <% } %>
    <% if (request.getAttribute("flashERR") != null) { %>
  <div class="alert alert-danger"><%= request.getAttribute("flashERR") %></div>
<% } %>

    <%
      DTDetalleRegistro detalle = (DTDetalleRegistro) request.getAttribute("detalle");
      if (detalle == null) {
        // ===== LISTA =====
        List<DTRegistro> registros = (List<DTRegistro>) request.getAttribute("registros");
    %>

      <h2 class="mb-3">Mis registros</h2>

      <% if (registros == null || registros.isEmpty()) { %>
        <p class="text-muted">No tenés registros.</p>
      <% } else { %>
        <div class="list-group">
          <%
            for (int i = 0; i < registros.size(); i++) {
              DTRegistro r = registros.get(i);
              // Ajustá los getters si tus nombres difieren:
              // getNombreEdicion() / getNombreTipo() o similares
          %>
            <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
               href="<%=request.getContextPath()%>/mobile/registro-asistencia?accion=detalle&indice=<%=i%>">
              <span>
                <strong>Edición:</strong> <%= r.getNombreEdicion() %> 
                <span class="text-muted"> — Tipo: <%= r.getNombreTipo() %></span> 
              </span>
              <span class="btn btn-sm btn-outline-primary">Ver detalle</span>
            </a>
          <% } %>
        </div>
      <% } %>

    <% } else { %>
      <!-- ===== DETALLE ===== -->
      <h2 class="mb-3">Detalle del registro</h2>

      <ul class="list-group mb-3">
        <li class="list-group-item"><strong>Edición:</strong> <%= detalle.getNombreEdicion() %></li>
        <li class="list-group-item"><strong>Tipo de registro:</strong> <%= detalle.getNombreTipo() %></li>
        <li class="list-group-item"><strong>Fecha de registro:</strong> <%= detalle.getFecha() %></li>
        <li class="list-group-item"><strong>Costo:</strong> $<%= detalle.getCosto() %></li>
        <li class="list-group-item">
          <strong>Patrocinio:</strong>
          <%= (detalle.getCodigoPatrocinio() != null) ? detalle.getCodigoPatrocinio() : "—" %>
        </li>
        <li class="list-group-item">
          <strong>Estado:</strong>
          <% if (detalle.getAsistio()) { %>
            <span class="badge bg-success">Asististe</span>
          <% } else { %>
            <span class="badge bg-secondary">Pendiente</span>
          <% } %>
        </li>
      </ul>

      <% if (!detalle.getAsistio()) { %>
        <form method="post" action="<%=request.getContextPath()%>/mobile/registro-asistencia"
              onsubmit="return confirm('¿Confirmás que asististe a esta edición?');">
          <input type="hidden" name="accion" value="marcar" />
          <input type="hidden" name="indice" value="<%= request.getAttribute("indice") %>" />
          <button class="btn btn-primary w-100">Marcar asistencia</button>
        </form>
      <% } else { %>
        <div class="alert alert-info">Ya marcaste asistencia para este registro.</div>
      <% } %>

      <a class="btn btn-link mt-3" href="<%=request.getContextPath()%>/mobile/registro-asistencia">Volver a mis registros</a>
    <% } %>

  </div>

  <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>