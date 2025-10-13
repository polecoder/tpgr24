<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, datatypes.*"%>
<%@ page import="com.eventosuy.models.TipoUsuario" %>
<%
  DTDetalleUsuario datosUsuario = (DTDetalleUsuario) session.getAttribute("usuarioLogeado");
  TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuarioLogeado");
%>
<%
  List<DTDetalleUsuario> usuarios = (List<DTDetalleUsuario>) request.getAttribute("usuarios");
  List<String> categorias = (List<String>) request.getAttribute("categorias");
  Map<String, DTEvento> eventos = (Map<String, DTEvento>) request.getAttribute("eventos");
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
			<% if (datosUsuario != null && tipoUsuario == TipoUsuario.ORGANIZADOR) { %>
                <div class="mb-4">
                  <h5 class="text-white bg-primary p-2 rounded">Altas</h5>
                  <ul class="list-unstyled ps-2">
                    <li>
                      <!-- Ajustá el mapping si es distinto -->
                      <a href="<%= request.getContextPath() %>/Eventos?accion=alta" class="text-primary fw-semibold">
                        Alta Evento
                      </a>
                    </li>
                    <li>
                      <!-- Ajustá el mapping si es distinto -->
                      <a href="<%= request.getContextPath() %>/Institucion?action=altaInstitucion" class="text-primary fw-semibold">
                        Alta Institución
                      </a>
                    </li>
                  </ul>
                </div>
            <% } %>
				<nav class="border border-primary rounded p-3 bg-white shadow-sm">
                    <div class="mb-4">
                        <h5 class="text-white bg-primary p-2 rounded">Usuarios</h5>
						<!-- Usuarios -->
						<%
						    if (usuarios != null) {
						      for (DTDetalleUsuario u: usuarios) {
						%>
                          
      						<div class="d-flex align-items-center mb-2">
    							<img src="<%= (u.getImagen() != null && u.getImagen().length > 0) 
                                              ? "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(u.getImagen())
                                              : request.getContextPath() + "/images/anonimo.svg" %>" 
                                     alt="<%= u.getNombre() %>"
    								 class="rounded-circle me-2"
                                     style="width: 48px; height: 48px" 
                                />
    							<a href="usuario?accion=consulta&nick=<%= u.getNickname()%>"class="text-primary fw-semibold"><%= u.getNombre() %></a>
      						</div>
  					      
						<%
						      }
						    }
						%>
                    </div>
					<!-- TODO: Agregar filtrado de categorías -->
					<!-- Categorías -->
					<div>
						<h5 class="text-white bg-primary p-2 rounded">Categorías</h5>
						<ul class="list-unstyled ps-2">
							<%
                                if (categorias != null) {
							      for (String nombreCategoria: categorias) {
							%>
								<li><a href="#" class="text-primary small d-block mb-2"><%=nombreCategoria%></a>
								</li>
							<%
							      }
                                } else {
							%>
                                <li>Sin categorías
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
                        if (eventos != null) {
					      for (DTEvento e: eventos.values()) {
					%>
					<div class="col">
						<div class="card h-100 shadow-sm">
							<a href="Eventos?accion=consulta&nombre=<%= e.getNombre() %>"><img src="<%= (e.getImagen() != null && e.getImagen().length > 0) 
                            ? "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(e.getImagen())
                            : request.getContextPath() + "/images/sin-imagen.svg" %>"
								alt="<%= e.getNombre() %>" class="card-img-top"
								style="object-fit: cover; aspect-ratio: 1 / 1" />
							</a>
							<div class="card-body text-center d-flex justify-content-center align-items-center">
								<a
									href="Eventos?accion=consulta&nombre=<%= e.getNombre() %>"
									class="card-title fs-6 text-decoration-none text-dark fw-semibold m-0"><%= e.getNombre() %></a>
							</div>
						</div>
					</div>
					<%
					      }
					    }
					%>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="./WEB-INF/templates/footer.jsp" />
</body>
</html>