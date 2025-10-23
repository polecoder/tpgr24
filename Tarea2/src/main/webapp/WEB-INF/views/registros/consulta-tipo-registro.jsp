<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="datatypes.DTTipoRegistro" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/templates/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100">
    <jsp:include page="/WEB-INF/templates/header.jsp" />

    <div class="container flex-grow-1">
        <div class="d-flex flex-column align-items-center py-5 gap-5">

            <%
                // Obtener datos desde la request
                byte[] imagenBytes = (byte[]) request.getAttribute("imagenEdicion");
                String base64Image = null;
                if (imagenBytes != null && imagenBytes.length > 0) {
                    base64Image = java.util.Base64.getEncoder().encodeToString(imagenBytes);
                }

                DTTipoRegistro dtTipoRegistro = (DTTipoRegistro) request.getAttribute("detalle_tipo_registro");
                String nombreTipoRegistro = dtTipoRegistro.nombre();
                String descripcion = dtTipoRegistro.descripcion();
                float costo = dtTipoRegistro.costo();
                int cuposMax = dtTipoRegistro.cuposMax();

                String nombreEvento = request.getParameter("nombre_evento");
                String nombreEdicion = request.getParameter("nombre_edicion");
            %>

            <!-- Contenedor principal -->
            <main class="p-4 bg-light border-top border-2 border-primary rounded" style="max-width: 450px">
                <form class="d-flex flex-column">
                    <label class="text-uppercase py-4 fs-4 fs-sm-3 fw-bold">
                        Tipo de registro: <%= nombreTipoRegistro %>
                    </label>

                    <!-- Descripción -->
                    <div class="mb-3">
                        <h5 class="fw-bold text-primary">Descripción</h5>
                        <p class="fst-italic mb-0"><%= descripcion %></p>
                    </div>

                    <!-- Costo -->
                    <div class="mb-3">
                        <h5 class="fw-bold text-primary">Costo</h5>
                        <p class="fst-italic mb-0"><%= costo %></p>
                    </div>

                    <!-- Cupos -->
                    <div class="mb-3">
                        <h5 class="fw-bold text-primary">Cupos</h5>
                        <p class="fst-italic mb-0"><%= cuposMax %></p>
                    </div>

                   <!-- Sección de Edición -->
				<div class="border-top pt-4 mt-4 text">
				    <h4 class="text-primary mb-4">Edición</h4>
				
				    <div class="d-flex justify-content-center align-items-center gap-3 mb-3">
				        <% if (base64Image != null) { %>
				            <img src="data:image/png;base64,<%= base64Image %>" 
				                 alt="Imagen edición"
				                 class="rounded"
				                 style="max-width: 90px; max-height: 90px; width: auto; height: auto;" />
				        <% } %>
				
				        <div class="text-start" style="line-height: 1.2">
				            <p class="fs-5 fw-semibold mb-1"><%= nombreEdicion %></p> 
				            <a href="${pageContext.request.contextPath}/Eventos?accion=consulta&nombre=<%= nombreEvento %>" 
				               class="fs-6 text-secondary text-decoration-underline">
				               <%= nombreEvento %>
				            </a>
				        </div>
				    </div>
				
				    <div class="d-flex justify-content-center mt-3">
				        <a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaEdicion&nombre_evento=<%= nombreEvento %>&nombre_edicion=<%= nombreEdicion %>"
				           class="btn btn-primary btn-sm px-3" id="btnEdicion">
				            Volver a la edición
				        </a>
				    </div>
				</div>
			</div>
			</div>
    <jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>
