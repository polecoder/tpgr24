<%@ page
  contentType="text/html"
  pageEncoding="UTF-8"
%>
<%@ page import="com.eventosuy.servidor.*"%>
<%@ page import="com.eventosuy.models.TipoUsuario"%>
<%@ page import="com.eventosuy.utils.Utilidades"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.List"%>
<%
String cp = request.getContextPath();
DTDetalleUsuario datosUsuario = (DTDetalleUsuario) session.getAttribute("usuarioLogeado");
String nombreEdicion = request.getParameter("nombre_edicion");
String nombreEvento = request.getParameter("nombre_evento");
String videoURL = (String) request.getAttribute("videoURL");
if (nombreEvento == null) {
	nombreEvento = (String) request.getAttribute("nombre_evento");
}
TipoUsuario tipoUsuarioLogeado = (TipoUsuario) request.getSession().getAttribute("tipoUsuarioLogeado");
DTEdicionEvento dtEdicion = (DTEdicionEvento) request.getAttribute("detalle_edicion");
//imagen
byte[] imagenBytes = dtEdicion.getImagen();
String base64Image = null;
if (imagenBytes != null && imagenBytes.length > 0) {
  base64Image = java.util.Base64.getEncoder().encodeToString(imagenBytes);
}
List<String> setPatrocinios = (List<String>) request.getAttribute("lista_patrocinios");
List<String> setTiposRegistro = (List<String>) request.getAttribute("lista_tipos_registro");
SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
byte[] imagenOrganizador = (byte[]) request.getAttribute("imagenOrg");
// archivo
String error = (String) request.getAttribute("error");
String mensajeArchivado = (String) request.getAttribute("archivado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="/WEB-INF/views/partials/head.jsp" />
<style>
.badge-estado {
	padding: 0.3rem 0.6rem;
	border-radius: 0.5rem;
	font-weight: 600;
}

.badge-ingresada {
	background-color: #fef9c3;
	color: #854d0e;
}

.badge-aprobada {
	background-color: #dcfce7;
	color: #166534;
}

.badge-rechazada {
	background-color: #fee2e2;
	color: #991b1b;
}

.badge-archivada {
 	background-color: #004d00;
    color: white;
}

.tipo-registro {
	display: inline-block;
	background-color: #e0f2fe;
	border-radius: 1rem;
	padding: 0.4rem 0.9rem;
	margin: 0.2rem;
	font-weight: 600;
	color: #0369a1;
	border: 1px solid #bae6fd;
}

.tipo-registro:hover {
	background-color: #bae6fd;
	text-decoration: none;
}
</style>
<link
  rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css"
  crossorigin="anonymous"
  referrerpolicy="no-referrer"
/>
</head>
<body class="d-flex flex-column min-vh-100">
  <jsp:include page="/WEB-INF/views/partials/header.jsp" />
  <div class="container flex-grow-1">
    <main class="p-4 bg-light border-top border-2 border-primary rounded shadow-sm mt-4">
      <form class="d-flex flex-column gap-4">
        <!-- Título + Imagen en línea -->
        <div class="d-flex align-items-center justify-content-between border-bottom pb-3">

          <%
          if (base64Image != null) {
          %>
          <img
            src="data:image/png;base64,<%=base64Image%>"
            alt="Imagen edicion"
            style="width: 128px"
          />
          <%
          }
          %>
          <div style="flex-grow: 1; display: flex; flex-direction: column; align-items: center; word-break: break-word;">
            <h1
              class="text-primary fw-bold mb-1 fs-2 text-center"
              style="line-height: 1.2;"
            ><%=nombreEdicion%></h1>
          </div>

          <div class="d-flex align-items-center gap-3 justify-content-center mb-4">
            <div class="text-center">
              <%
              if (imagenOrganizador != null) {
              %>
              <%
              byte[] imagenOrgBytes = imagenOrganizador;
              if (imagenOrgBytes != null && imagenOrgBytes.length > 0) {
              	String base64Org = java.util.Base64.getEncoder().encodeToString(imagenOrgBytes);
              %>
              <img
                src="data:image/png;base64,<%=base64Org%>"
                alt="Imagen del organizador"
                class="rounded-circle shadow-sm mb-2"
                style="width: 60px; height: 60px; object-fit: cover;"
              >
              <%
              }
              %>
              <%
              }
              %>
              <p class="fw-semibold text-secondary mb-0"><%=dtEdicion.getOrganizadorNickname()%></p>
            </div>
          </div>
        </div>

        <!-- Datos de la edición -->
        <div class="card shadow-sm p-4">
          <div class="d-flex justify-content-between align-items-center mb-3">
            <h4 class="fw-bold text-primary mb-0">Detalles de la edición:</h4>
            <% if (tipoUsuarioLogeado == TipoUsuario.ORGANIZADOR && datosUsuario.getNickname().equals(dtEdicion.getOrganizadorNickname())) { %>
              <% if (dtEdicion.getEstado() == EEstadoEdicion.INGRESADA) { %>
                <span class="badge-estado badge-ingresada">Ingresada</span>
              <% } else if (dtEdicion.getEstado() == EEstadoEdicion.ACEPTADA) { %>
                <span class="badge-estado badge-aprobada">Aprobada</span>
              <% } else if (dtEdicion.getEstado() == EEstadoEdicion.RECHAZADA) { %>
                <span class="badge-estado badge-rechazada">Rechazada</span>
              <% } else if (dtEdicion.getEstado() == EEstadoEdicion.ARCHIVADA) { %>
                <span class="badge-estado badge-archivada">Archivada</span>
              <% } %>
            <% } %>
          </div>
          <div class="row gy-3">
            <div class="col-md-6">
              <strong class="text-secondary">Sigla:</strong>
              <p class="mb-0"><%=dtEdicion.getSigla()%></p>
            </div>
            <div class="col-md-6">
              <strong class="text-secondary">Fecha de alta:</strong>
              <p class="mb-0"><%= formatter.format(Utilidades.XMLGregorianCalendarToDate(dtEdicion.getFechaAlta())) %></p>
            </div>
            <div class="col-md-6">
              <strong class="text-secondary">País:</strong>
              <p class="mb-0"><%=dtEdicion.getPais()%></p>
            </div>
            <div class="col-md-6">
              <strong class="text-secondary">Ciudad:</strong>
              <p class="mb-0"><%=dtEdicion.getCiudad()%></p>
            </div>
            <div class="col-md-6">
              <strong class="text-secondary">Fecha de inicio:</strong>
              <p class="mb-0"><%= formatter.format(Utilidades.XMLGregorianCalendarToDate(dtEdicion.getFechaInicio())) %></p>
            </div>
            <div class="col-md-6">
              <strong class="text-secondary">Fecha de fin:</strong>
              <p class="mb-0"><%= formatter.format(Utilidades.XMLGregorianCalendarToDate(dtEdicion.getFechaFin())) %></p>
            </div>
          </div>
        </div>

        <%-- no muestro sección de tipos de registro si la edición está en el archivo --%>
        <% if (dtEdicion.getEstado() != EEstadoEdicion.valueOf("Archivada")) { %>
          <div class="card shadow-sm p-4">
            <h5 class="fw-bold text-primary mb-3 text-center">Tipos de registro:</h5>
            <%
            if (setTiposRegistro != null && !setTiposRegistro.isEmpty()) {
            %>
              <div class="d-flex flex-wrap justify-content-center gap-3">
                <%
                for (String tipoRegistro : setTiposRegistro) {
                %>
                <a
                  href="<%= cp %>/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=tipoRegistro%>"
                  class="card text-center shadow-sm border-primary text-decoration-none"
                  style="width: 10rem;"
                >
                  <div class="card-body">
                    <h6 class="card-title text-primary fw-semibold"><%=tipoRegistro%></h6>
                  </div>
                </a>
                <%
                }
                %>
              </div>
            <% } %>
          </div>
        <% } %>
        
        <!-- Patrocinadores -->
        <%-- no muestro sección de patrocinadores si la edición está archivada --%>
        <% if (dtEdicion.getEstado() != EEstadoEdicion.valueOf("Archivada")) { %>
          <div class="card shadow-sm p-4">
            <h5 class="fw-bold text-primary mb-3 text-center">Patrocinadores:</h5>
            <%
            if (setPatrocinios != null && !setPatrocinios.isEmpty()) {
            %>
            <table class="table table-sm table-bordered">
              <thead class="table-light">
                <tr>
                  <th scope="col">Nombre del patrocinador</th>
                </tr>
              </thead>
              <tbody>
                <%
                for (String patrocinador : setPatrocinios) {
                %>
                <tr>
                  <td><a
                    href="<%= cp %>/Institucion?action=consultaPatrocinio&nombreEvento=<%=nombreEvento%>&nombreEdicion=<%=nombreEdicion%>&nombre_patrocinador=<%=patrocinador%>"
                    class="link-primary link-underline"
                  > <%=patrocinador%>
                  </a></td>
                </tr>
                <%
                }
                %>
              </tbody>
            </table>
            <%
            } else {
            %>
            <p class="text-secondary text-center mb-0">No tiene patrocinadores.</p>
            <%
            }
            %>
          </div>
        <% } %>

        <%-- registros de la edición (organizador de la edición) --%>
        <%
        if (tipoUsuarioLogeado == TipoUsuario.ORGANIZADOR && datosUsuario.getNickname().equals(dtEdicion.getOrganizadorNickname())) {
        	List<DTDetalleRegistro> setRegistros = (List<DTDetalleRegistro>) request.getAttribute("lista_registros");
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
                <th scope="col">¿Asistió?</th>
              </tr>
            </thead>
            <tbody>
              <%
              for (DTDetalleRegistro registro : setRegistros) {
              %>
              <tr>
                <td><a
                  href="<%= cp %>/usuario?accion=consulta&nick=<%=registro.getNicknameAsistente()%>"
                  class="link-primary link-underline"
                ><%=registro.getNicknameAsistente()%></a></td>
                <td>
                  <%-- si la edición está archivada, no mmuestro link para Consulta Tipo Registro --%>
                  <% if (dtEdicion.getEstado() == EEstadoEdicion.valueOf("Archivada")) { %>
                    <%=registro.getNombreTipo()%>
                  <% } else { %>
                    <a
                  href="<%= cp %>/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=registro.getNombreTipo()%>"
                  class="link-primary link-underline"
                ><%=registro.getNombreTipo()%></a>
                  <% } %>
                </td>
                <td><%=registro.getFecha()%></td>
                <td><%=registro.getCosto()%></td>
                <% if (registro.isAsistio()) { %>
                	<td>Asistió</td>
                <% } else { %>
                	<td>No asistió</td>
                <% } %>
              </tr>
              <%
              }
              %>
            </tbody>
          </table>
          <%
          } else {
          %>
          <p class="text-secondary text-center mb-0">No tiene registros.</p>
          <%
          }
          %>
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
		                <th scope="col">¿Asistió?</th>
		              </tr>
		            </thead>
		            <tbody>
		              <tr>
		                <td><a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=nombreTipoRegistro%>"
		                       class="link-primary link-underline"><%= dtDetalleRegistro.getNombreTipo() %></a></td>
		                <td><%= dtDetalleRegistro.getFecha() %></td>
		                <td><%= dtDetalleRegistro.getCosto() %></td>
		                <% if (dtDetalleRegistro.isAsistio()) { %>
		                	<td>Asistió</td>
		                <% } else { %>
		                	<td>No asistió</td>
		                <% } %>
		              </tr>
		            </tbody>
		          </table>
		        </div>
        <% }} %>

        <%-- si la edición no está archivada, muestro botones para interactuar con la edición --%>
        <% if (dtEdicion.getEstado() != EEstadoEdicion.valueOf("Archivada")) { %>
          <%-- si el usuario logeado es organizador y organiza la edición --%>
          <%
          if (tipoUsuarioLogeado == TipoUsuario.ORGANIZADOR
              && datosUsuario.getNickname().equals(dtEdicion.getOrganizadorNickname())) {
          %>
          <div class="d-flex justify-content-center gap-3">
            <a
              href="<%= cp %>/Institucion?action=altaPatrocinio&nombreEdicion=<%=nombreEdicion%>&nombreEvento=<%=nombreEvento%>"
              class="btn btn-primary btn-sm px-3"
            >Alta patrocinio</a> <a
              href="<%= cp %>/Ediciones?accion=AltaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>"
              class="btn btn-primary btn-sm px-3"
            >Alta tipo registro</a> <a
              href="<%= cp %>/Ediciones?accion=ArchivarEdicion&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>"
              class="btn btn-primary btn-sm px-3"
              onclick="return confirm('¿Seguro que querés archivar esta edición?');"
            >Archivar edición</a>
          </div>
          <%-- si el usuario es asistente --%>
          <%
          } else if (tipoUsuarioLogeado == TipoUsuario.ASISTENTE) {
          boolean finalizado = (boolean) request.getAttribute("finalizada");
          if (!finalizado) {
          %>
          <div class="d-flex justify-content-center gap-3">
            <a
              href="<%= cp %>/usuario?accion=registro-edicion&evento=<%=nombreEvento%>&edicion=<%=nombreEdicion%>"
              class="btn btn-primary btn-sm px-3 fs-5"
            >Registrarse a edición</a>
          </div>
          <%
          }
          }
          %>
        <% } %>

        <!-- Video -->
        <%
        if (videoURL != null && !videoURL.isEmpty()) {
        %>
        <div class="card shadow-sm p-4 text-center d-flex justify-content-center align-items-center">
          <h5 class="fw-bold text-primary mb-3 text-center">Video promocional:</h5>
          <iframe
            src=<%=videoURL%>
            style="height: 400px; width: 700px;"
            title="video_edicion"
            allowfullscreen
          ></iframe>
        </div>
        <%
        }
        %>
      </form>
    </main>
  </div>
  <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
  <%-- Configuración de Toastr para mensajes en pantalla (usado para ArchivarEdicion) --%>
  <script
    src="https://code.jquery.com/jquery-3.6.0.min.js"
    crossorigin="anonymous"
  ></script>
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"
    crossorigin="anonymous"
    referrerpolicy="no-referrer"
    type="text/javascript"
  ></script>

  <script>
    toastr.options = {
        "closeButton": true,
        "progressBar": true,
        "positionClass": "toast-bottom-right",
        "timeOut": "5000"
    };

    // chequeamos si tenemos mensaje para mostrar.
    <% if (error != null || mensajeArchivado != null) {
        String mensaje = error == null ? mensajeArchivado : error;
        String tipoMensaje = error == null ? "success" : "error";
        mensaje = mensaje.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", ""); // para convertir a String a string de .js
    %>
        (function() {
            let mensaje = "<%= mensaje %>";
            let tipoMensaje = "<%= tipoMensaje %>";
            console.log(mensaje);
            console.log(tipoMensaje);
    		if (tipoMensaje === "success") {
    			toastr.success(mensaje);
    		} else if (tipoMensaje === "error") {
    			toastr.error(mensaje);
    		}
	    })();
    <% } %>
  </script>
</body>
</html>