<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="es">
<!-- INCLUDE HEAD -->
<jsp:include page="/WEB-INF/templates/head.jsp" />

<body class="d-flex flex-column min-vh-100">

    <!-- INCLUDE HEADER -->
    <jsp:include page="/WEB-INF/templates/header.jsp" />

    <div class="container flex-grow-1 py-4" style="max-width: 900px;">
        <main class="p-4 bg-light border-top border-2 border-primary rounded shadow-sm">

            <%
                // ==== DATOS DEL EVENTO ====
                String imagen = (String) request.getAttribute("imagen");
                String nombre = (String) request.getAttribute("nombre");
                String descripcion = (String) request.getAttribute("descripcion");
                String sigla = (String) request.getAttribute("sigla");
                String fechaAlta = (String) request.getAttribute("fechaAlta");


                // ==== OTRAS LISTAS ====
                List<String> categorias = (List<String>) request.getAttribute("categorias");
                List<String> ediciones = (List<String>) request.getAttribute("ediciones");

                // ==== IMAGEN POR DEFECTO ====
                String imagenDefectoEdicion = "images/sin-imagen.svg";
            %>

            <% if (imagen != null && !imagen.isEmpty()) { %>
                <div class="text-center mb-4">
                    <img src="<%= imagen %>" alt="Imagen del evento"
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
                <h4 class="text-primary mb-3">Ediciones</h4>
                <% if (ediciones != null && !ediciones.isEmpty()) { %>
                    <div class="d-flex flex-wrap gap-3 justify-content-center">
                        <% for (String ed : ediciones) { %>
                            <div class="card shadow-sm border-0" style="width: 200px;">
                                <img src="../images/IMG-EDEV03.jpeg" alt="Imagen edición"
                                     class="card-img-top rounded-top" style="height: 120px; object-fit: cover;">
                                <div class="card-body text-center p-2">
                                    <a href="Ediciones?accion=consulta&nombreEdicion=<%= ed %>&nombreEvento=<%= nombre %>"
                                       class="text-decoration-none text-primary fw-semibold">
                                        <%= ed %>
                                    </a>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } else { %>
                    <p class="text-muted fst-italic">No hay ediciones disponibles.</p>
                <% } %>
            </div>

        </main>
    </div>

    <!-- INCLUDE FOOTER -->
    <jsp:include page="/WEB-INF/templates/footer.jsp" />

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
