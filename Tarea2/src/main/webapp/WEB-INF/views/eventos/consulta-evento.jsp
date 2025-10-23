<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList" %>
<%@page import="datatypes.*, com.eventosuy.models.*" %>

<!DOCTYPE html>
<html lang="es">
<!-- INCLUDE HEAD -->
<jsp:include page="/WEB-INF/views/partials/head.jsp" />

<body class="d-flex flex-column min-vh-100">

    <!-- INCLUDE HEADER -->
    <jsp:include page="/WEB-INF/views/partials/header.jsp" />

    <div class="container flex-grow-1 py-4" style="max-width: 900px;">
        <main class="p-4 bg-light border-top border-2 border-primary rounded shadow-sm">

            <%
            // ==== DATOS DEL EVENTO ====
            String nombre = (String) request.getAttribute("nombre");
            String descripcion = (String) request.getAttribute("descripcion");
            String sigla = (String) request.getAttribute("sigla");
            String fechaAlta = (String) request.getAttribute("fechaAlta");
            
            DTDetalleUsuario datosUsuario = (DTDetalleUsuario) session.getAttribute("usuarioLogeado");
        	TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuarioLogeado");

            // Obtenemos la imagen como byte[]
            byte[] imagenBytes = (byte[]) request.getAttribute("imagen");
            String base64Image = null; // declarar antes del if
            if (imagenBytes != null && imagenBytes.length > 0) {
                base64Image = java.util.Base64.getEncoder().encodeToString(imagenBytes);
            }

            // ==== OTRAS LISTAS ====
            List<String> categorias = (List<String>) request.getAttribute("categorias");
            List<DTEdicionEvento> ediciones = (List<DTEdicionEvento>) request.getAttribute("ediciones");
            %>

            <% if (base64Image != null) { %>
                <div class="text-center mb-4">
                    <img src="data:image/png;base64,<%= base64Image %>" alt="Imagen del evento"
                         class="shadow-sm"
                         style="width: 100%; max-width: 900px; min-width: 350px;
                                height: 280px; object-fit: cover; border-radius: 12px;">
                </div>
            <% } %>

            <!-- Nombre del evento -->
            <h1 class="text-center text-uppercase fw-bold mb-3 fs-2"><%= nombre %></h1>

            <!-- Descripción -->
            <div class="mb-3">
                <h5 class="fw-bold text-primary">Descripción</h5>
                <p class="fst-italic mb-0"><%= descripcion %></p>
            </div>

            <!-- Sigla -->
            <div class="mb-3">
                <h5 class="fw-bold text-primary">Sigla</h5>
                <p class="fst-italic mb-0"><%= sigla %></p>
            </div>

            <!-- Categorías -->
            <div class="mb-3">
                <h5 class="fw-bold text-primary">Categorías</h5>
                <p class="fst-italic mb-0">
                    <%
                        List<String> catList = (categorias != null) ? categorias : new ArrayList<String>();
                        out.print(catList.isEmpty() ? "Sin categorías" : String.join(", ", catList));
                    %>
                </p>
            </div>

            <!-- Fecha de alta -->
            <div class="mb-4">
                <h5 class="fw-bold text-primary">Fecha de alta</h5>
                <p class="fst-italic mb-0"><%= fechaAlta %></p>
            </div>

            <!-- Ediciones -->
            <div class="border-top pt-4 mt-4">
		    <div class="d-flex justify-content-between align-items-center mb-3">
		        <h4 class="text-primary mb-0">Ediciones</h4>
		        <% if (datosUsuario != null && tipoUsuario == TipoUsuario.ORGANIZADOR) { %>
		            <a href="<%= request.getContextPath() %>/Ediciones?accion=AltaEdicion&nombre_evento=<%= nombre %>"
		               class="btn btn-primary fw-semibold shadow-sm px-3">
		                ➕ Dar de alta nueva edición
		            </a>
		        <% } %>
		    </div>
                <% if (ediciones != null && !ediciones.isEmpty()) { %>
				    <div class="d-flex flex-wrap gap-3 justify-content-center">
				        <% for (DTEdicionEvento ed : ediciones) {
				            byte[] imgBytes = ed.getImagen();
				            String base64Img = null;
				            if (imgBytes != null && imgBytes.length > 0) {
				                base64Img = java.util.Base64.getEncoder().encodeToString(imgBytes);
				            }
				        %>
					        <div class="card shadow-sm border-0" style="width: 200px;">
					            <img src="<%= (base64Img != null) ? "data:image/jpeg;base64," + base64Img : request.getContextPath() + "/images/sin-imagen.svg" %>"
					                 alt="Imagen de <%= ed.getNombre() %>"
					                 class="card-img-top rounded-top" style="height: 120px; object-fit: cover;">
					            <div class="card-body text-center p-2">
					                <a href="Ediciones?accion=ConsultaEdicion&nombre_edicion=<%=ed.getNombre()%>&nombre_evento=<%= nombre %>"
					                   class="text-decoration-none text-primary fw-semibold">
					                    <%= ed.getNombre() %>
					                </a>
					            </div>
					        </div>
				        <% } %>
				    </div>
				<% } else { %>
				    <p class="text-muted fst-italic">No hay ediciones disponibles.</p>
				<% } %>

        </main>
    </div>

    <!-- INCLUDE FOOTER -->
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
