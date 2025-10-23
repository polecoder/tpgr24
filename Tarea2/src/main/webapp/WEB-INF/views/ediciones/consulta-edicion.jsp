<%@ page
  contentType="text/html"
  pageEncoding="UTF-8"
%>
<%@ page import="datatypes.*"%>
<%@ page import="com.eventosuy.models.TipoUsuario"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
DTDetalleUsuario datosUsuario = (DTDetalleUsuario) session.getAttribute("usuarioLogeado");
String nombreEdicion = request.getParameter("nombre_edicion");
String nombreEvento = request.getParameter("nombre_evento");
if (nombreEvento == null) {
	nombreEvento = (String) request.getAttribute("nombre_evento");
}
// imagen
byte[] imagenBytes = (byte[]) ((DTEdicionEvento) request.getAttribute("detalle_edicion")).getImagen();
String base64Image = null;
if (imagenBytes != null && imagenBytes.length > 0) {
	base64Image = java.util.Base64.getEncoder().encodeToString(imagenBytes);
}
TipoUsuario tipoUsuarioLogeado = (TipoUsuario) request.getSession().getAttribute("tipoUsuarioLogeado");
DTEdicionEvento dtEdicion = (DTEdicionEvento) request.getAttribute("detalle_edicion");
Set<String> setPatrocinios = (Set<String>) request.getAttribute("lista_patrocinios");
Set<String> setTiposRegistro = (Set<String>) request.getAttribute("lista_tipos_registro");
SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
byte[] imagenOrganizador=(byte[])request.getAttribute("imagenOrg");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="/WEB-INF/views/partials/head.jsp" />
<style>
.badge-estado {padding: 0.3rem 0.6rem; border-radius: 0.5rem; font-weight: 600;}
.badge-ingresada {background-color: #fef9c3; color: #854d0e;}
.badge-aprobada {background-color: #dcfce7; color: #166534;}
.badge-rechazada {background-color: #fee2e2; color: #991b1b;}
.tipo-registro {display:inline-block; background-color:#e0f2fe; border-radius:1rem; padding:0.4rem 0.9rem; margin:0.2rem; font-weight:600; color:#0369a1; border:1px solid #bae6fd;}
.tipo-registro:hover{background-color:#bae6fd; text-decoration:none;}
</style>
</head>

</head>
<body class="d-flex flex-column min-vh-100">
  <jsp:include page="/WEB-INF/views/partials/header.jsp" />
  <div class="container flex-grow-1">
   <main class="p-4 bg-light border-0 rounded-4 shadow-sm mt-4" style="max-width: 900px; margin: 0 auto;">
      <form class="d-flex flex-column gap-4">
          <!-- Título + Imagen en línea -->
          <div class="d-flex align-items-center justify-content-between border-bottom pb-3">
          
            <% if (base64Image != null) { %>
            <img
              src="data:image/png;base64,<%=base64Image%>"
              alt="Imagen evento"
              style="width: 128px"
            />
            <% } %>
           <div style="flex-grow: 1; display: flex; flex-direction: column; align-items: center; word-break: break-word;">
    			<h1 class="text-uppercase fw-bold mb-1 fs-2 text-center" style="line-height: 1.2;"><%= nombreEdicion %></h1>
 		 </div>
 		 
 		  <div class="d-flex align-items-center gap-3 justify-content-center mb-4">
		        <div class="text-center">
		            <% if (imagenOrganizador != null) { %>
		                <% 
		                    byte[] imagenOrgBytes = imagenOrganizador; 
		                    if (imagenOrgBytes != null && imagenOrgBytes.length > 0) { 
		                        String base64Org = java.util.Base64.getEncoder().encodeToString(imagenOrgBytes);
		                %>
		                    <img src="data:image/png;base64,<%=base64Org%>" 
		                         alt="Imagen del organizador" 
		                         class="rounded-circle shadow-sm mb-2" 
		                         style="width: 60px; height: 60px; object-fit: cover;">
		                <% } %>
		            <% } %>
		            <p class="fw-semibold text-secondary mb-0"><%= dtEdicion.getOrganizadorNickname() %></p>
		        </div>
		    </div>
			

          </div>
        <!-- Datos de la edición -->
        <div class="card shadow-sm p-4">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="fw-bold text-primary mb-0">Detalles de la edición</h4>
            <% if (tipoUsuarioLogeado == TipoUsuario.ORGANIZADOR && datosUsuario.getNickname().equals(dtEdicion.getOrganizadorNickname())) { %>
              <% if (dtEdicion.getEstado() == EEstadoEdicion.valueOf("Ingresada")) { %>
                <span class="badge-estado badge-ingresada">Ingresada</span>
              <% } else if (dtEdicion.getEstado() == EEstadoEdicion.valueOf("Aceptada")) { %>
                <span class="badge-estado badge-aprobada">Aprobada</span>
              <% } else if (dtEdicion.getEstado() == EEstadoEdicion.valueOf("Rechazada")) { %>
                <span class="badge-estado badge-rechazada">Rechazada</span>
              <% } %>
            <% } %>
          </div>
          <div class="row gy-3">
            <div class="col-md-6">
              <strong class="text-secondary">Sigla:</strong>
              <p class="mb-0"><%= dtEdicion.getSigla() %></p>
            </div>
            <div class="col-md-6">
              <strong class="text-secondary">Fecha de alta:</strong>
              <p class="mb-0"><%= formatter.format(dtEdicion.getFechaAlta()) %></p>
            </div>
            <div class="col-md-6">
              <strong class="text-secondary">País:</strong>
              <p class="mb-0"><%= dtEdicion.getPais() %></p>
            </div>
            <div class="col-md-6">
              <strong class="text-secondary">Ciudad:</strong>
              <p class="mb-0"><%= dtEdicion.getCiudad() %></p>
            </div>
            <div class="col-md-6">
              <strong class="text-secondary">Fecha de inicio:</strong>
              <p class="mb-0"><%= formatter.format(dtEdicion.getFechaInicio()) %></p>
            </div>
            <div class="col-md-6">
              <strong class="text-secondary">Fecha de fin:</strong>
              <p class="mb-0"><%= formatter.format(dtEdicion.getFechaFin()) %></p>
            </div>
          </div>
        </div>

        
          <div class="card shadow-sm p-4">
            <h5 class="fw-bold text-primary mb-3 text-center">Tipos de registro:</h5>
            <% if (!setTiposRegistro.isEmpty()) { %>
            <div class="d-flex flex-wrap justify-content-center gap-3">
              <% for (String tipoRegistro : setTiposRegistro) { %>
                <a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=tipoRegistro%>"
                   class="card text-center shadow-sm border-primary text-decoration-none" style="width: 10rem;">
                  <div class="card-body">
                    <h6 class="card-title text-primary fw-semibold"><%=tipoRegistro%></h6>
                  </div>
                </a>
              <% } %>
            </div>
          </div>
        <% } else { %>
          <p class="text-secondary text-center mb-0">No tiene tipos de registro.</p>
         </div> 
        <% } %>

        <!-- Patrocinadores -->
        <div class="card shadow-sm p-4">
          <h5 class="fw-bold text-primary mb-3 text-center">Patrocinadores de la edición</h5>
          <% if (!setPatrocinios.isEmpty()) { %>
            <table class="table table-sm table-bordered">
              <thead class="table-light">
                <tr>
                  <th scope="col">Nombre del patrocinador</th>
                </tr>
              </thead>
              <tbody>
                <% for (String patrocinador : setPatrocinios) { %>
                  <tr>
                    <td>
                      <a href="${pageContext.request.contextPath}/Institucion?action=consultaPatrocinio&nombreEvento=<%=nombreEvento%>&nombreEdicion=<%=nombreEdicion%>&nombre_patrocinador=<%=patrocinador%>"
                         class="link-primary link-underline">
                        <%=patrocinador%>
                      </a>
                    </td>
                  </tr>
                <% } %>
              </tbody>
            </table>
          <% } else { %>
            <p class="text-secondary text-center mb-0">No tiene patrocinadores.</p>
          <% } %>
        </div>

        <%-- registros de la edición (organizador de la edición) --%>
        <%
        if (tipoUsuarioLogeado == TipoUsuario.ORGANIZADOR && datosUsuario.getNickname().equals(dtEdicion.getOrganizadorNickname())) {
          Set<DTDetalleRegistro> setRegistros = (Set<DTDetalleRegistro>) request.getAttribute("lista_registros");
        %>
        <div class="card shadow-sm p-4">
          
          <h5 class="fw-bold text-primary mb-3 text-center">Registros a la edición:</h5>
          <%
          if (setRegistros != null && !setRegistros.isEmpty()) {
          %>
          <table class="table table-sm table-bordered">
            <thead class="table-light">
              <tr>
                <th scope="col">Usuario</th>
                <th scope="col">Tipo</th>
                <th scope="col">Fecha</th>
                <th scope="col">Costo</th>
              </tr>
            </thead>
            <tbody>
              <% for (DTDetalleRegistro registro : setRegistros) { %>
              <tr>
                <td><a href="${pageContext.request.contextPath}/usuario?accion=consulta&nick=<%=registro.getNicknameAsistente()%>"
                       class="link-primary link-underline"><%=registro.getNicknameAsistente()%></a></td>
                <td><a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=registro.getNombreTipo()%>"
                       class="link-primary link-underline"><%= registro.getNombreTipo() %></a></td>
                <td><%=registro.getFecha()%></td>
                <td><%=registro.getCosto()%></td>
              </tr>
              <% } %>
            </tbody>
          </table>
          <% } else { %>
          <p class="text-secondary text-center mb-0">No tiene registros.</p>
          <% } %>
        </div>
        <%
        } else if (tipoUsuarioLogeado == TipoUsuario.ASISTENTE) {
            DTDetalleRegistro dtDetalleRegistro = (DTDetalleRegistro) request.getAttribute("detalle_registro");
            if (dtDetalleRegistro != null) {
                String nombreTipoRegistro = dtDetalleRegistro.getNombreTipo();
        %>
        <div class="card shadow-sm p-4">
          <h6 class="fw-bold mb-3">Tu registro en esta edición:</h6>
          <table class="table table-sm table-bordered">
            <thead class="table-light">
              <tr>
                <th scope="col">Tipo</th>
                <th scope="col">Fecha</th>
                <th scope="col">Costo</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=nombreTipoRegistro%>"
                       class="link-primary link-underline"><%= dtDetalleRegistro.getNombreTipo() %></a></td>
                <td><%= dtDetalleRegistro.getFecha() %></td>
                <td><%= dtDetalleRegistro.getCosto() %></td>
              </tr>
            </tbody>
          </table>
        </div>
        <% }} %>

        <%-- si el usuario logeado es organizador y organiza la edición --%>
        <%
        if (tipoUsuarioLogeado == TipoUsuario.ORGANIZADOR && datosUsuario.getNickname().equals(dtEdicion.getOrganizadorNickname())) {
        %>
        <div class="d-flex justify-content-center gap-3">
          <a href="${pageContext.request.contextPath}/Institucion?action=altaPatrocinio&nombreEdicion=<%=nombreEdicion%>&nombreEvento=<%=nombreEvento%>"
             class="btn btn-primary btn-sm px-3">Alta patrocinio</a>
          <a href="${pageContext.request.contextPath}/Ediciones?accion=AltaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>"
             class="btn btn-primary btn-sm px-3">Alta tipo registro</a>
        </div>
        <%-- si el usuario es asistente --%>
        <% } else if (tipoUsuarioLogeado == TipoUsuario.ASISTENTE) { 
            boolean finalizado = (boolean) request.getAttribute("finalizada");
            if (!finalizado) { %>
            <div class="d-flex justify-content-center gap-3">
              <a href="${pageContext.request.contextPath}/usuario?accion=registro-edicion&evento=<%=nombreEvento%>&edicion=<%=nombreEdicion%>"
                 class="btn btn-primary btn-sm px-3">Registrarse a edición</a>
            </div>
        <% }} %>
      </form>
    </main>
  </div>
  <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>