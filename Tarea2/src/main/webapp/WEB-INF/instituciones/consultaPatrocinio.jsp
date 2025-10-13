<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="datatypes.*" %>
<% 
	String nombreEdicion = (String) request.getAttribute("nombreEdicion");
	String nombreEvento = (String) request.getAttribute("nombreEvento");
	String rutaImagenEdicion = (String) request.getAttribute("rutaImagenEdicion");
	
	String codigoPatrocinio = (String) request.getAttribute("codigoPatrocinio"); 
	
	String rutaImagenInstitucion = (String) request.getAttribute("rutaImagenInstitucion");
	
	DTTipoRegistro dtTipoRegistro = (DTTipoRegistro) request.getAttribute("dtTipoRegistro");
	
	DTDetallePatrocinio dtPatrocinio = (DTDetallePatrocinio) request.getAttribute("dtPatrocinio");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="../templates/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100">
    <jsp:include page="../templates/header.jsp" />
    
	<div class="container flex-grow-1">
    <div class="mx-auto" style="max-width: 768px">
      <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">
        Consulta de Patrocinio
        <span class="text-primary d-block"><%= codigoPatrocinio %></span>
      </h1>
    </div>

    <div class="d-flex justify-content-between gap-5 mx-auto" style="max-width: 1024px">
      <!-- TODO: Actualizar los links -->

      <main class="mx-auto p-4 bg-light border-top border-2 border-primary rounded" style="height: fit-content">
        <form class="d-flex flex-column gap-4">
          <div class="d-flex justify-content-center gap-3">
            <label for="fecha" class="form-label text-end" style="width: 100px">Fecha:</label>
            <input type="text" id="fecha" name="fecha" class="form-control form-control-sm px-2" style="width: 250px"
              value=<%=dtPatrocinio.fechaAlta() %> readonly />
          </div>

          <div class="d-flex justify-content-center gap-3">
            <label for="aporte" class="form-label text-end" style="width: 100px">Aporte:</label>
            <input type="text" id="aporte" name="aporte" class="form-control form-control-sm px-2" style="width: 250px"
              value=<%= dtPatrocinio.aporte()%> readonly />
          </div>

          <div class="d-flex justify-content-center gap-3">
            <label for="nivel" class="form-label text-end" style="width: 100px">Nivel:</label>
            <input type="text" id="nivel" name="nivel" class="form-control form-control-sm px-2" style="width: 250px"
              value=<%= dtPatrocinio.nivel() %> readonly />
          </div>

          <div class="d-flex justify-content-center gap-3">
            <label for="cuposGratuitos" class="form-label text-end" style="width: 100px">Cupos:</label>
            <input type="text" id="cuposGratuitos" name="cuposGratuitos" class="form-control form-control-sm px-2"
              style="width: 250px" value=<%= dtPatrocinio.cuposGratuitos() %> readonly />
          </div>
        </form>
      </main>
      <aside class="border-primary border rounded" style="max-width: 275px; height: fit-content">
        <div>
          <h3 class="text-white bg-primary p-2 m-0 fs-4">Edición</h3>
          <div class="d-flex align-items-center gap-2 py-2 px-3 justify-content-center">
            <img src=<%= rutaImagenEdicion %> alt="Imagen evento" style="width: 64px; height: 64px" />
            <!-- TODO: Lleva a CU::ConsultaEdición -->
            <a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaEdicion&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>" class="text-primary"><%= nombreEdicion %></a>
          </div>
        </div>
        <div>
          <h3 class="text-white bg-primary p-2 m-0 fs-4">Institución</h3>
          <div class="d-flex align-items-center gap-2 py-2 px-3 justify-content-center">
            <img src=<%= rutaImagenInstitucion%> alt="Imagen institucion" style="width: 64px" />
            <!-- TODO: Lleva a CU::ConsultaInstitución -->
            <p class="text-black p-2 m-0 fs-5"><%= dtPatrocinio.nombreInstitucion() %></p>
          </div>
        </div>
        <div>
          <h3 class="text-white bg-primary p-2 m-0 fs-4">Tipo de Registro</h3>
          <div class="d-flex align-items-center gap-2 py-2 px-4 justify-content-between">
            <!-- TODO: Lleva a CU::ConsultaTipoRegistro -->
            <a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=dtPatrocinio.tipoRegistro()%>" class="text-primary"><%= dtPatrocinio.tipoRegistro() %></a>
            <p class="m-0"><%= dtTipoRegistro.costo() %></p>
          </div>
        </div>
      </aside>
    </div>
  </div>

    <jsp:include page="../templates/footer.jsp" />
</body>
</html>