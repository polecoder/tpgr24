<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.eventosuy.servidor.DTTipoRegistro" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100">
    <jsp:include page="/WEB-INF/views/partials/header.jsp" />

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
            
            // Usar getters en lugar de métodos record
            String nombreTipoRegistro = dtTipoRegistro.getNombre();
            String descripcion = dtTipoRegistro.getDescripcion();
            float costo = dtTipoRegistro.getCosto();
            int cuposMax = dtTipoRegistro.getCuposMax();

            String nombreEvento = request.getParameter("nombre_evento");
            String nombreEdicion = request.getParameter("nombre_edicion");
            %>

            <!-- Contenedor principal -->
         <main class="p-4 bg-light border-top border-2 border-primary rounded" >
                <div class="card-body">
                <form class="d-flex flex-column">
                
                <div class=" border-bottom p-3 mb-4 text-center">
				  <div class="d-flex align-items-center justify-content-center gap-3 flex-wrap">
				    <h1 class="text-uppercase fw-bold fs-3 text-primary mb-0">Registro <%= nombreTipoRegistro %></h1>
				  </div>
				  <p class="text-muted fst-italic mt-2 mb-0 fs-5">
				    <%= nombreEdicion %>
				  </p>
				</div>

                    <!-- Descripción -->
                    <div class="mb-3 text-center">
                         <p class="mb-0 text-semibold fs-5 fst-italic"><%= descripcion %></p>
                    </div>
					
					<div class="card shadow-sm p-4">
					 <h4 class="fw-bold text-primary mb-3 fs-5">Detalles del Tipo de registro:</h4>
	                    <!-- Costo -->
	                    <div class="mb-3">
	                        <h5 class="fw-bold text-secondary mb-1">Costo:</h5>
	                        <p class="fst-italic mb-0">$<%= costo %></p>
	                    </div>
	
	                    <!-- Cupos -->
	                    <div class="mb-3">
	                        <h5 class="fw-bold text-secondary mb-1">Cupos máximos:</h5>
	                        <p class="fst-italic mb-0"><%= cuposMax %></p>
	                    </div>
					 </div>
                   <!-- Sección de Edición -->
				<div class="border-top pt-4 mt-4">		
				    <div class="text-center mt-4">
				        <a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaEdicion&nombre_evento=<%= nombreEvento %>&nombre_edicion=<%= nombreEdicion %>"
				           class="btn btn-primary btn-sm px-3 fs-5" id="btnEdicion">
				            Volver a la edición
				        </a>
				    </div>
				</div>
			</div>
			</div>
		</div>
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>
