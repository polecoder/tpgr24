<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.eventosuy.mobile.servidor.DTDetalleRegistro" %>
<%@ page import="com.eventosuy.mobile.utils.Utilidades" %>
<!DOCTYPE html>
<html lang="es">
  <!-- HEAD GLOBAL -->
  <jsp:include page="/WEB-INF/views/partials/head.jsp" />

  <body class="d-flex flex-column min-vh-100">

    <!-- HEADER GLOBAL -->
    <jsp:include page="/WEB-INF/views/partials/header.jsp" />

    <%
      String ctx = request.getContextPath();
      String error = (String) request.getAttribute("error");

      DTDetalleRegistro detalleAttr = (DTDetalleRegistro)request.getAttribute("detalle");     

    %>

    <div class="container flex-grow-1">
      <div class="mx-auto" style="max-width: 768px">
        <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">Mis Registros</h1>
      </div>

      <div class="d-flex justify-content-between">
        <main class="mx-auto p-4 bg-light border-top border-2 border-primary rounded" style="max-width: 600px">
 
              <%
                if (detalleAttr != null) {
	              	String nomEd = detalleAttr.getNombreEdicion();
	              	Date fecha =  Utilidades.XMLGregorianCalendarToDate(detalleAttr.getFecha());
	              	String nomTipo = detalleAttr.getNombreTipo();
	              	float costo = detalleAttr.getCosto();
	              	String codigoPatrocinio = detalleAttr.getCodigoPatrocinio();
              %>
	                <h2 class="fs-5 mb-4 text-center text-uppercase">Detalle del Registro:</h2>
	                <p><strong>Edición:</strong> <%= nomEd %></p>
	                <p><strong>Fecha de registro:</strong> <%= fecha %></p>
	                <p><strong>Tipo de registro:</strong> <%= nomTipo %></p>
	                <p><strong>Costo:</strong> <%=costo%></p>
	                <% if (codigoPatrocinio != null) { %>
	                	<p><strong>Patrocinio:</strong> <%=codigoPatrocinio%></p>
	                <% } %>
	                
	                <% if (!detalleAttr.isAsistio()) { %>
				        <form method="post" 
						      action="<%= request.getContextPath() %>/Registros"
						      onsubmit="return confirm('¿Confirmás tu asistencia a esta edición?');">
						  <input type="hidden" name="accion" value="Confirmar Asistencia" />
						  <input type="hidden" name="edicion" value="<%= detalleAttr.getNombreEdicion() %>" />
						  <button class="btn btn-primary w-100 mt-3">Confirmar asistencia</button>
						</form>
							     
			      <% } else { %>
			        <div class="alert alert-info">Ya marcaste asistencia para este registro.</div>
			      <% } %>
              <% }else{%>
              	 <p><strong>No estas registrado a esta edicion</strong> </p>
            <% } %>
        </main>
      </div>
    </div>

    <!-- FOOTER GLOBAL -->
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
