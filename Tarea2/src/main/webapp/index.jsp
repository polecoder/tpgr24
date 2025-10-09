<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, datatypes.DTEvento" %>
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
		<div class="row g-4">
			<div class="col-12 col-md-4">
				<nav class="border border-primary rounded p-3 bg-white shadow-sm">
					<div class="mb-4">
						<h5 class="text-white bg-primary p-2 rounded">Usuarios</h5>
						<div class="d-flex align-items-center mb-3">
							<img src="./images/IMG-US01.jpg" alt="Ana Torres"
								class="rounded-circle me-2" style="width: 48px; height: 48px" />
							<a href="./pages/consulta-usuario-ana-torres-visitante.html"
								class="text-primary fw-semibold">Ana Torres</a>
						</div>
						<div class="d-flex align-items-center">
							<img src="./images/IMG-US04.jpeg" alt="MisEventos"
								class="rounded-circle me-2" style="width: 48px; height: 48px" />
							<a href="./pages/consulta-usuario-ana-torres-visitante.html"
								class="text-primary fw-semibold">MisEventos</a>
						</div>
					</div>

					<!-- Categorías -->
					<div>
						<h5 class="text-white bg-primary p-2 rounded">Categorías</h5>
						<ul class="list-unstyled ps-2">
							<li><a href="#" class="text-primary small d-block mb-2">Tecnología</a>
							</li>
							<li><a href="#" class="text-primary small d-block mb-2">Innovación</a>
							</li>
							<li><a href="#" class="text-primary small d-block mb-2">Deporte</a>
							</li>
							<li><a href="#" class="text-primary small d-block">Salud</a>
							</li>
						</ul>
					</div>
				</nav>
			</div>

			<!-- Eventos disponibles -->
			<div
				class="col-12 col-md-8 order-1 order-md-2 justify-content-center">
				<h1 class="text-center text-uppercase py-3 fs-2">Eventos
					disponibles</h1>
				<div class="row row-cols-1 row-cols-md-2 g-4 justify-content-center">
					<!-- TODO: Hardcodeado -->
					<!-- Tarjeta: Conferencia de Tecnología -->
					<div class="col" style="width: 350px">
						<div class="card h-100 shadow-sm">
							<a
								href="./pages/consulta-evento-maraton-montevideo-visitante.html">
								<img src="./images/sin-imagen.svg"
								alt="Conferencia de Tecnología" class="card-img-top"
								style="object-fit: cover" />
							</a>
							<div class="card-body text-center">
								<a
									href="./pages/consulta-evento-maraton-montevideo-visitante.html"
									class="card-title fs-6 text-decoration-none text-dark fw-semibold">Conferencia
									de Tecnología</a>
							</div>
						</div>
					</div>
					<!-- TODO: Hardcodeado -->
					<!-- Tarjeta: Maratón de Montevideo -->
					<div class="col" style="width: 350px">
						<div class="card h-100 shadow-sm">
							<a
								href="./pages/consulta-evento-maraton-montevideo-visitante.html">
								<img src="./images/IMG-EV04.png" alt="Maratón de Montevideo"
								class="card-img-top" style="object-fit: cover" />
							</a>
							<div class="card-body text-center">
								<a
									href="./pages/consulta-evento-maraton-montevideo-visitante.html"
									class="card-title fs-6 text-decoration-none text-dark fw-semibold">Maratón
									de Montevideo</a>
							</div>
						</div>
					</div>
					<%
					    Map<String, DTEvento> eventos = (Map<String, DTEvento>) request.getAttribute("eventos");
					    for (DTEvento e: eventos.values()) {
					%>
						<div class="col" style="width: 350px">
							<div class="card h-100 shadow-sm">
								<!-- TODO: Cambiar href de anchor y src de imagen -->
								<a
									href="#">
									<img src="./images/sin-imagen.svg" alt="<%= e.getNombre() %>"
									class="card-img-top" style="object-fit: cover" />
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