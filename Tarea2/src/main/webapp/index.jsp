<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, datatypes.*"%>
<%
// Testing session : Añadir / Borrar lo que se necesite
session.setAttribute("usuarioNombre", "Ana Torres");
session.setAttribute("usuarioImagen", "./images/IMG-US01.jpg");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="WEB-INF/templates/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
	<jsp:include page="./WEB-INF/templates/header.jsp"></jsp:include>
	<!-- Contenido principal -->
	<div class="container flex-grow-1 mt-4">
		<div class="row g-5">
			<div class="col-12 col-md-4">
				<nav class="border border-primary rounded p-3 bg-white shadow-sm">
					<div class="mb-4">
						<h5 class="text-white bg-primary p-2 rounded">Usuarios</h5>
						<!-- Usuarios -->
						<%
						    List<DTDetalleUsuario> usuarios = (List<DTDetalleUsuario>) request.getAttribute("usuarios");
						    for (DTDetalleUsuario u: usuarios) {
						%>
						<div class="d-flex align-items-center">
							<!-- TODO: cambiar src de imagen -->
							<img src="./images/anonimo.svg" alt="<%= u.getNombre() %>"
								class="rounded-circle me-2" style="width: 48px; height: 48px" />
							<!-- TODO: cambiar href -->
							<a href="#" class="text-primary fw-semibold"><%= u.getNombre() %></a>
						</div>
						<%
						    }
						%>
					</div>

					<!-- TODO: Agregar filtrado de categorías -->
					<!-- Categorías -->
					<div>
						<h5 class="text-white bg-primary p-2 rounded">Categorías</h5>
						<ul class="list-unstyled ps-2">
							<%
							    List<String> categorias = (List<String>) request.getAttribute("categorias");
							    for (String nombreCategoria: categorias) {
							%>
								<li><a href="#" class="text-primary small d-block mb-2"><%=nombreCategoria%></a>
								</li>
							<%
							    }
							%>
						</ul>
					</div>
				</nav>
			</div>

			<!-- Eventos disponibles -->
			<div
				class="col-12 col-md-8 order-1 order-md-2 justify-content-center">
				<h1 class="text-center text-uppercase pb-3 fs-2">Eventos
					disponibles</h1>
				<div
					class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 g-4 justify-content-center">
					<%
					    Map<String, DTEvento> eventos = (Map<String, DTEvento>) request.getAttribute("eventos");
					    for (DTEvento e: eventos.values()) {
					%>
					<div class="col">
						<div class="card h-100 shadow-sm">
							<!-- TODO: Cambiar href y src de imagen -->
							<a href="#"> <img src="./images/sin-imagen.svg"
								alt="<%= e.getNombre() %>" class="card-img-top"
								style="object-fit: cover" />
							</a>
							<div class="card-body text-center">
								<a
									href="./pages/consulta-evento-maraton-montevideo-visitante.html"
									class="card-title fs-6 text-decoration-none text-dark fw-semibold"><%= e.getNombre() %></a>
							</div>
						</div>
					</div>
					<%
					    }
					%>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="./WEB-INF/templates/footer.jsp" />
</body>
</html>