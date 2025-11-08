<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.ArrayList"%>
<%@page import="com.eventosuy.mobile.servidor.DTEdicionEvento"%>
<%@page import="com.eventosuy.mobile.servidor.DTDetalleUsuario"%>

<!DOCTYPE html>
<html lang="es">
<!-- INCLUDE HEAD -->
<jsp:include page="/WEB-INF/views/partials/head.jsp" />
<style>
#carouselEdiciones .carousel-control-prev-icon,
#carouselEdiciones .carousel-control-next-icon {
    background-image: none;
    width: 1rem;
    height: 1rem;
    border: solid #0d6efd;
    border-width: 0 0.25rem 0.25rem 0;
    padding: 0.5rem;
    display: inline-block;
    transform: rotate(135deg);
}

#carouselEdiciones .carousel-control-next-icon {
    transform: rotate(-45deg);
}

#carouselEdiciones {
    padding: 0 40px;
}

@media (min-width: 768px) {
    #carouselEdiciones {
        padding: 0 60px;
    }
}


#carouselEdiciones .carousel-control-prev,
#carouselEdiciones .carousel-control-next {
    width: 5%;
    top: 50%;
    transform: translateY(-50%);
}

.evento-card {
    width: 100%;
    max-width: 300px;
    margin: 0 auto;
}

.evento-img {
    width: 100%;
    height: 150px;
    object-fit: cover;
}
</style>
<body class="d-flex flex-column min-vh-100">

	<!-- INCLUDE HEADER -->
	<jsp:include page="/WEB-INF/views/partials/header.jsp" />
	
	<div class="container flex-grow-1 py-4">
		<main class="p-4 bg-light border-top border-2 border-primary rounded shadow-sm">

			<%
			String nombre = (String) request.getAttribute("nombre");
			String descripcion = (String) request.getAttribute("descripcion");
			String sigla = (String) request.getAttribute("sigla");
			String fechaAlta = (String) request.getAttribute("fechaAlta");

			DTDetalleUsuario datosUsuario = (DTDetalleUsuario) session.getAttribute("usuarioLogeado");

			byte[] imagenBytes = (byte[]) request.getAttribute("imagen");
			String base64Image = null;
			if (imagenBytes != null && imagenBytes.length > 0) {
				base64Image = java.util.Base64.getEncoder().encodeToString(imagenBytes);
			}

			List<String> categorias = (List<String>) request.getAttribute("categorias");
			List<DTEdicionEvento> ediciones = (List<DTEdicionEvento>) request.getAttribute("ediciones");
			%>

			<div class="border-bottom border-2 border-secondary-subtle pb-3 mb-4">
				<div class="d-flex align-items-center gap-3 flex-nowrap">
					<%
					if (base64Image != null) {
					%>
					<img src="data:image/png;base64,<%=base64Image%>"
						alt="Imagen evento" class="img-fluid rounded shadow-sm"
						style="max-width: 90px; min-width: 50px; height: auto; object-fit: cover;" />
					<%
					}
					%>

					<div class="flex-grow-1 text-center"
						style="word-break: break-word; overflow-wrap: break-word;">
						<h1 class="text-primary fw-bold mb-1 fs-2"
							style="line-height: 1.2; hyphens: auto;">
							<%=nombre%>
						</h1>
					</div>
				</div>
			</div>

			<!-- Detalle evento -->
			<div class="card shadow-sm p-4 mb-3">
				<h4 class="fw-bold text-primary mb-3 ">Detalles del Evento:</h4>
				<div class="row gy-3">
					<div class="col-md-6">
						<h5 class="fw-bold text-secondary">Descripción</h5>
						<p class="fst-italic mb-0"><%=descripcion%></p>
					</div>

					<div class="col-md-6">
						<h5 class="fw-bold text-secondary">Sigla</h5>
						<p class="fst-italic mb-0"><%=sigla%></p>
					</div>

					<div class="col-md-6">
						<h5 class="fw-bold text-secondary">Categorías</h5>
						<p class="fst-italic mb-0">
							<%
							List<String> catList = (categorias != null) ? categorias : new ArrayList<String>();
							out.print(catList.isEmpty() ? "Sin categorías" : String.join(", ", catList));
							%>
						</p>
					</div>

					<div class="col-md-6">
						<h5 class="fw-bold text-secondary">Fecha de alta</h5>
						<p class="fst-italic mb-0"><%=fechaAlta%></p>
					</div>
				</div>
			</div>

			<!-- Ediciones -->
			<div class="card shadow-sm p-4">
				<h4 class="text-primary mb-3">Ediciones:</h4>
				<%
				if (ediciones != null && !ediciones.isEmpty()) {
				%>
				<div id="carouselEdiciones" class="carousel slide" data-bs-ride="carousel">
					<div class="carousel-inner">
						<% for (int i = 0; i < ediciones.size(); i++) {
							DTEdicionEvento ed = ediciones.get(i);
							byte[] imgBytes = ed.getImagen();
							String base64Img = null;
							if (imgBytes != null && imgBytes.length > 0) {
								base64Img = java.util.Base64.getEncoder().encodeToString(imgBytes);
							}
						%>
						<div class="carousel-item <%= (i == 0) ? "active" : "" %>">
							<div class="card evento-card shadow-sm mx-auto">
								<img src="<%= (base64Img != null) ? "data:image/jpeg;base64," + base64Img
								     : request.getContextPath() + "/images/sin-imagen.svg" %>"
								     alt="Imagen de <%=ed.getNombre()%>" class="card-img-top evento-img">
								<div class="card-body text-center p-2">
									<a href="Ediciones?accion=consulta&nombre_edicion=<%=ed.getNombre()%>&nombre_evento=<%=nombre%>"
									   class="text-decoration-none text-primary fw-semibold "
									   style="word-break: break-word; overflow-wrap: break-word;">
									   <%=ed.getNombre()%>
									</a>
								</div>
							</div>
						</div>
						<% } %>
					</div>

					<button class="carousel-control-prev" type="button" data-bs-target="#carouselEdiciones" data-bs-slide="prev">
						<span class="carousel-control-prev-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Anterior</span>
					</button>
					<button class="carousel-control-next" type="button" data-bs-target="#carouselEdiciones" data-bs-slide="next">
						<span class="carousel-control-next-icon" aria-hidden="true"></span>
						<span class="visually-hidden">Siguiente</span>
					</button>
				</div>
				<%
				} else {
				%>
				<p class="text-muted fst-italic">No hay ediciones disponibles.</p>
				<%
				}
				%>
			</div>
		</main>
	</div>

	<!-- INCLUDE FOOTER -->
	<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

