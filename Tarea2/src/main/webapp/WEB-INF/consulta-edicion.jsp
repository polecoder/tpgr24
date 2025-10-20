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
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="/WEB-INF/templates/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100">
  <jsp:include page="/WEB-INF/templates/header.jsp" />
  <div class="container flex-grow-1">
    <div
      class="mx-auto"
      style="max-width: 780px"
    >
      <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">Consulta de edición de evento</h1>
    </div>
    <main
      class="mx-auto p-4 bg-light border-top border-2 border-primary rounded"
      style="max-width: 500px"
    >
      <form class="d-flex flex-column gap-4">
        <div
          id="info-edicion"
          class="mt-2 p-3 border rounded bg-white d-block mx-auto"
          style="min-width: 450px"
        >
          <!-- Título + Imagen en línea -->
          <div class="d-flex align-items-center justify-content-between">
            <label class="mb-3 fs-4 text-center w-100"> <%=nombreEdicion%>
            </label>
            <% if (base64Image != null) { %>
            <img
              src="data:image/png;base64,<%=base64Image%>"
              alt="Imagen evento"
              style="width: 128px"
            />
            <% } %>
          </div>
          <!-- Datos de la edición -->
          <p>
            <strong>Organizador:</strong>
            <%=dtEdicion.getOrganizadorNickname()%></p>
          <p>
            <strong>Sigla:</strong>
            <%=dtEdicion.getSigla()%>
          </p>
          <p>
            <strong>País:</strong>
            <%=dtEdicion.getPais()%>
          </p>
          <p>
            <strong>Ciudad:</strong>
            <%=dtEdicion.getCiudad()%></p>
          <p>
            <strong>Fecha de inicio:</strong>
            <%=formatter.format(dtEdicion.getFechaInicio())%>
          </p>
          <p>
            <strong>Fecha de fin:</strong>
            <%=formatter.format(dtEdicion.getFechaFin())%>
          </p>
          <p>
            <strong>Fecha de alta:</strong>
            <%=formatter.format(dtEdicion.getFechaAlta())%>
          </p>
          <div class="mt-3">
            <h6 class="fw-bold m-0">Tipos de registro:</h6>
            <% if (!setTiposRegistro.isEmpty()) { %>
            <table class="table table-sm table-bordered mt-2 mb-3">
              <thead class="table-light">
                <tr>
                  <th scope="col">Nombre del tipo de registro</th>
                </tr>
              </thead>
              <tbody>
                <% for (String tipoRegistro : setTiposRegistro) { %>
                <tr>
                  <td><a
                    href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=tipoRegistro%>"
                    class="link-primary link-underline"
                  > <%=tipoRegistro%>
                  </a></td>
                </tr>
                <% } %>
              </tbody>
            </table>
            <% } else { %>
            <p class="text-secondary ms-3 mb-2">No tiene tipos de registro.</p>
            <% } %>
          </div>


          <!-- Patrocinadores -->
          <div class="mt-3">
            <h6 class="fw-bold m-0">Patrocinadores de la edición:</h6>
            <% if (!setPatrocinios.isEmpty()) { %>
            <table class="table table-sm table-bordered mt-2 mb-3">
              <thead class="table-light">
                <tr>
                  <th scope="col">Nombre del patrocinador</th>
                </tr>
              </thead>
              <tbody>
                <% for (String patrocinador : setPatrocinios) { %>
                <tr>
                  <td><a
                    href="${pageContext.request.contextPath}/Institucion?action=consultaPatrocinio&nombreEvento=<%=nombreEvento%>&nombreEdicion=<%=nombreEdicion%>&nombre_patrocinador=<%=patrocinador%>"
                    class="link-primary link-underline"
                  > <%=patrocinador%>
                  </a></td>
                </tr>
                <% } %>
              </tbody>
            </table>
            <% } else { %>
            <p class="text-secondary ms-3 mb-2">No tiene patrocinadores.</p>
            <% } %>
          </div>

          <%-- registros de la edición (organizador de la edición) --%>
          <%
          if (tipoUsuarioLogeado == TipoUsuario.ORGANIZADOR &&
        	  datosUsuario.getNickname() == dtEdicion.getOrganizadorNickname()) {
            Set<DTDetalleRegistro> setRegistros = (Set<DTDetalleRegistro>) request.getAttribute("lista_registros");
          %>
          <div class="mt-3">
            <%
            if (setRegistros != null && !setRegistros.isEmpty()) {
            %>
            <h6 class="fw-bold m-0">Registros de la edición:</h6>
            <table class="table table-sm table-bordered mt-2 mb-3">
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
                  <td><a
                    href="${pageContext.request.contextPath}/usuario?accion=consulta&nick=<%=registro.getNicknameAsistente()%>"
                    class="link-primary link-underline"
                  > <%=registro.getNicknameAsistente()%>
                  </a></td>
                  <td><a
                    href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=registro.getNombreTipo()%>"
                    class="link-primary link-underline"
                  ><%= registro.getNombreTipo() %></a>
                  </td>
                  <td><%=registro.getFecha()%></td>
                  <td><%=registro.getCosto()%></td>
                </tr>
                <%
                }
                %>
              </tbody>
            </table>
            <% } else { %>
            <p class="text-secondary ms-3 mb-2">No tiene registros.</p>
            <% } %>
          </div>
          <%
          } else if (tipoUsuarioLogeado == TipoUsuario.ASISTENTE) {
              DTDetalleRegistro dtDetalleRegistro = (DTDetalleRegistro) request.getAttribute("detalle_registro");
              if (dtDetalleRegistro != null) {
              	String nombreTipoRegistro = dtDetalleRegistro.getNombreTipo();
          %>
		          <div class="mt-3">
		            <h6 class="fw-bold m-0">Tu registro en esta edición:</h6>
		            <table class="table table-sm table-bordered mt-2 mb-3">
		              <thead class="table-light">
		                <tr>
		                  <th scope="col">Tipo</th>
		                  <th scope="col">Fecha</th>
		                  <th scope="col">Costo</th>
		                </tr>
		              </thead>
		              <tbody>
		                <tr>
		                  <td><a
		                    href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=nombreTipoRegistro%>"
		                    class="link-primary link-underline"
		                  > <%= dtDetalleRegistro.getNombreTipo() %>
		                  </a></td>
		                  <td>
		                    <%= dtDetalleRegistro.getFecha() %>
		                  </td>
		                  <td>
		                    <%= dtDetalleRegistro.getCosto() %>
		                  </td>
		                </tr>
		              </tbody>
		            </table>
		          </div>
          <% }} %>
        </div>
        <%-- si el usuario logeado es organizador y organiza la edición --%>
        <%
        if (tipoUsuarioLogeado == TipoUsuario.ORGANIZADOR &&
        	datosUsuario.getNickname() == dtEdicion.getOrganizadorNickname()) {
        %>
        <div class="d-flex justify-content-center gap-3">
          <a
            href="${pageContext.request.contextPath}/Institucion?action=altaPatrocinio&nombreEdicion=<%=nombreEdicion%>&nombreEvento=<%=nombreEvento%>"
            class="btn btn-primary btn-sm px-3"
          >Alta patrocinio</a> 
          <a
            href="${pageContext.request.contextPath}/Ediciones?accion=AltaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>"
            class="btn btn-primary btn-sm px-3"
          >Alta tipo registro</a>
        </div>
        <%-- si el usuario es asistente --%>
        <% } else if (tipoUsuarioLogeado == TipoUsuario.ASISTENTE) { 
        		boolean finalizado = (boolean) request.getAttribute("finalizada");
        		if (!finalizado) { %>
			        <div class="d-flex justify-content-center gap-3">
			          <a
			            href="${pageContext.request.contextPath}/usuario?accion=registro-edicion&evento=<%=nombreEvento%>&edicion=<%=nombreEdicion%>"
			            class="btn btn-primary btn-sm px-3"
			          >Registrarse a edicion</a>
			        </div>
        <% }	} %>
      </form>
    </main>
  </div>
  <jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>