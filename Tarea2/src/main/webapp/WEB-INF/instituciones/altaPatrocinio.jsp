<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="datatypes.*" %>
<%@ page import="java.util.Date" %>
<% 
	// Testing
	DTEdicionEvento testingDtEdicion = new DTEdicionEvento(
			"TestEdicionEvento", 
			"TEE", 
			new Date(), 
			new Date(), 
			new Date(), 
			"Montevideo", 
			"Uruguay", 
			"MisEventos"
			);
	request.setAttribute("dtEdicion", testingDtEdicion);
	// Fin Testing

	DTEdicionEvento dtEdicion = request.getAttribute("dtEdicion");
	//String rutaImagenEdicion = dtEdicion.getRutaImagenEdicion();
	String rutaImagenEdicion = "../images/IMG-EDEV03.jpeg";
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="../templates/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100">
    <jsp:include page="../templates/header.jsp" />
    <div class="container flex-grow-1">
    <div class="mx-auto" style="max-width: 768px"></div>
    
    <!-- Evento y Edición -->
    <div class="container flex-grow-1">
      <div class="d-flex flex-column align-items-center py-5">
        <div class="d-flex align-items-center gap-3">
          <img src=<%=rutaImagenEdicion %> alt="Imagen usuario" style="
                max-width: 128px;
                max-height: 128px;
                width: auto;
                height: auto;
              " />
          <div class="d-flex flex-column" style="line-height: 1">
            <h1 class="fs-2 fw-bold mb-1"><%= dtEdicion.getNombre() %></h1>
            <a href="./Evento" class="fs-5 text-secondary text-decoration-underline"><%= dtEdicion.getNombre() %></a>
            <!-- Necesito saber como redirigir al Servlet de Evento -->
          </div>
        </div>
      </div>

      <main class="mx-auto p-4 bg-light border-top border-2 border-primary rounded" style="width: 500px">
        <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">
          Alta Patrocinio
        </h1>
        <form class="d-flex flex-column gap-4">
          <!-- Tipo de Registro -->
          <div class="d-flex gap-2">
            <label for="tipoRegistro" class="form-label" style="width: 300px">
              Tipo de Registro: <span class="text-danger">*</span>
            </label>
            <select id="tipoRegistro" name="TipoRegistro" class="form-select form-select-sm" style="max-width: 300px">
              <option value="" selected>
                -- Seleccionar un Tipo Registro --
              </option>
              <option value="Corredor 21K">Corredor 21K</option>
            </select>
          </div>

          <!-- Instituciones -->
          <div class="d-flex gap-2">
            <label for="instituciones" class="form-label" style="width: 300px">Instituciones: <span
                class="text-danger">*</span></label>
            <select id="instituciones" name="Instituciones" class="form-select form-select-sm" style="max-width: 300px">
              <option value="" selected>
                -- Seleccionar una Institución --
              </option>
              <option value="Facultad de Ingenieria">
                Facultad de Ingenieria
              </option>
            </select>
          </div>

          <!-- Nivel de Patrocinio -->
          <div class="d-flex gap-2">
            <label for="nivelPatrocinio" class="form-label" style="width: 300px">Nivel de Patrocinio: <span
                class="text-danger">*</span></label>
            <select id="nivelPatrocinio" name="Nivel" class="form-select form-select-sm" style="max-width: 300px">
              <option value="" selected>
                -- Seleccionar Nivel del Patrocinio --
              </option>
              <option value="Platino">Platino</option>
              <option value="Oro">Oro</option>
              <option value="Plata">Plata</option>
              <option value="Bronce">Bronce</option>
            </select>
          </div>

          <!-- Aporte Económico -->
          <div class="d-flex gap-2">
            <label for="aporteEconomico" class="form-label" style="width: 300px">Aporte Económico: <span
                class="text-danger">*</span></label>
            <input type="text" id="aporte" name="Aporte" class="form-control form-control-sm"
              style="max-width: 300px" />
          </div>

          <!-- Cupos Gratuitos -->
          <div class="d-flex gap-2">
            <label for="cuposGratuitos" class="form-label" style="width: 300px">Cant. cupos Gratuitos:
              <span class="text-danger">*</span></label>
            <input type="text" id="cupos" name="Cupos" class="form-control form-control-sm" style="max-width: 300px" />
          </div>

          <!-- Código Patrocinio -->
          <div class="d-flex gap-2">
            <label for="codigoPatrocinio" class="form-label" style="width: 300px">Código Patrocinio: <span
                class="text-danger">*</span></label>
            <input type="text" id="codigo" name="Codigo" class="form-control form-control-sm"
              style="max-width: 300px" />
          </div>

          <br />
          <!-- Botones -->
          <div class="d-flex flex-column gap-2">
            <button type="button" id="btnAceptar" class="btn btn-primary btn-sm px-3">
              Aceptar
            </button>
          </div>
        </form>
      </main>
    </div>
  </div>

  <br />
    <jsp:include page="../templates/footer.jsp" />
</body>
</html>