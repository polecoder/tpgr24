<%@ page
  contentType="text/html"
  pageEncoding="UTF-8"
%>
<%@ page import="datatypes.*"%>
<%@ page import="java.util.*"%>
<%
String error = (String) request.getAttribute("error");
String nombreEvento = (String) request.getAttribute("nombreEvento");
String nombreEdicion = (String) request.getAttribute("nombreEdicion");

byte[] imagenBytes = (byte[]) request.getAttribute("imagenEdicion");
String base64Image = null; // declarar antes del if
if (imagenBytes != null && imagenBytes.length > 0) {
	base64Image = java.util.Base64.getEncoder().encodeToString(imagenBytes);
}

Set<String> listaNombresTipoRegistro = (Set<String>) request.getAttribute("listaNombresRegistro");
Set<String> listaNombresInstitucion = (Set<String>) request.getAttribute("listaNombresInstitucion");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="/WEB-INF/views/partials/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100">
  <jsp:include page="/WEB-INF/views/partials/header.jsp" />
  <div class="container flex-grow-1">
    <div
      class="mx-auto"
      style="max-width: 768px"
    ></div>
    <!-- Evento y Edición -->
    <div class="container flex-grow-1">
      <div class="d-flex flex-column align-items-center py-5">
        <div class="d-flex align-items-center gap-3">
          <% if (base64Image != null) { %>
          <img
            src="data:image/png;base64,<%=base64Image%>"
            alt="Imagen edicion"
            style="max-width: 128px; max-height: 128px; width: auto; height: auto;"
          />
          <% } %>
          <div
            class="d-flex flex-column"
            style="line-height: 1"
          >
            <h1 class="fs-2 fw-bold mb-1"><%=nombreEdicion%></h1>
            <a
              href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaEdicion&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>"
              class="fs-5 text-secondary text-decoration-underline"
            ><%=nombreEdicion%></a>
          </div>
        </div>
      </div>

      <main
        class="mx-auto p-4 bg-light border-top border-2 border-primary rounded"
        style="width: 500px"
      >
        <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">Alta Patrocinio</h1>
        <form
          class="d-flex flex-column gap-4"
          method="post"
          action="Institucion?action=altaPatrocinio&nombreEvento=<%=nombreEvento%>&nombreEdicion=<%=nombreEdicion%>"
          enctype="multipart/form-data"
          novalidate
        >
          <% if (error != null) { %>
          <div class="d-flex justify-content-center gap-3">
            <p
              class="p-2 rounded"
              style="margin: 0; background-color: #FFB3B3;"
            >
              <span style="font-weight: 800; color: #FF6666;">Error</span>:
              <%=error%></p>
          </div>
          <% } %>

          <!-- campos ocultos, necesarios en el POST -->
          <input
            type="hidden"
            id="nombreEdicion"
            name="nombreEdicion"
            class="form-control form-control-sm"
            style="max-width: 300px"
            value=<%=nombreEdicion%>
          /> <input
            type="hidden"
            id="nombreEvento"
            name="nombreEvento"
            class="form-control form-control-sm"
            style="max-width: 300px"
            value=<%=nombreEvento%>
          />
          <!-- Tipo de Registro -->
          <div class="d-flex gap-2">
            <label
              for="tipoRegistro"
              class="form-label"
              style="width: 300px"
            > Tipo de Registro: <span class="text-danger">*</span>
            </label> <select
              id="tipoRegistro"
              name="TipoRegistro"
              class="form-select form-select-sm"
              style="max-width: 300px"
              required
            >
              <option
                value=""
                selected
              >-- Seleccionar un Tipo Registro --</option>
              <% for (String nombreTipoRegistro : listaNombresTipoRegistro) { %>
              <option value="<%=nombreTipoRegistro%>"><%=nombreTipoRegistro%></option>
              <% } %>
            </select>
          </div>

          <!-- Instituciones -->
          <div class="d-flex gap-2">
            <label
              for="instituciones"
              class="form-label"
              style="width: 300px"
            >Instituciones: <span class="text-danger">*</span></label> <select
              id="instituciones"
              name="Instituciones"
              class="form-select form-select-sm"
              style="max-width: 300px"
              required
            >
              <option
                value=""
                selected
              >-- Seleccionar una Institución --</option>
              <% for (String nombreInstitucion : listaNombresInstitucion) { %>
              <option value="<%=nombreInstitucion%>"><%=nombreInstitucion%></option>
              <% } %>
            </select>
          </div>

          <!-- Nivel de Patrocinio -->
          <div class="d-flex gap-2">
            <label
              for="nivelPatrocinio"
              class="form-label"
              style="width: 300px"
            >Nivel de Patrocinio: <span class="text-danger">*</span></label> <select
              id="nivelPatrocinio"
              name="Nivel"
              class="form-select form-select-sm"
              style="max-width: 300px"
              required
            >
              <option
                value=""
                selected
              >-- Seleccionar Nivel del Patrocinio --</option>
              <option value="Platino">Platino</option>
              <option value="Oro">Oro</option>
              <option value="Plata">Plata</option>
              <option value="Bronce">Bronce</option>
            </select>
          </div>

          <!-- Aporte Económico -->
          <div class="d-flex gap-2">
            <label
              for="aporteEconomico"
              class="form-label"
              style="width: 300px"
            >Aporte Económico: <span class="text-danger">*</span></label> <input
              type="text"
              id="aporte"
              name="Aporte"
              class="form-control form-control-sm"
              style="max-width: 300px"
              required
            />
          </div>

          <!-- Cupos Gratuitos -->
          <div class="d-flex gap-2">
            <label
              for="cuposGratuitos"
              class="form-label"
              style="width: 300px"
            >Cant. cupos Gratuitos: <span class="text-danger">*</span></label> <input
              type="text"
              id="cupos"
              name="Cupos"
              class="form-control form-control-sm"
              style="max-width: 300px"
              required
            />
          </div>

          <!-- Código Patrocinio -->
          <div class="d-flex gap-2">
            <label
              for="codigoPatrocinio"
              class="form-label"
              style="width: 300px"
            >Código Patrocinio: <span class="text-danger">*</span></label> <input
              type="text"
              id="codigo"
              name="Codigo"
              class="form-control form-control-sm"
              style="max-width: 300px"
              required
            />
          </div>

          <br />
          <!-- Botones -->
          <div class="d-flex flex-column gap-2">
            <button
              type="submit"
              id="btnAceptar"
              class="btn btn-primary btn-sm px-3"
            >Aceptar</button>
          </div>
        </form>
      </main>
    </div>
  </div>

  <br />
  <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>