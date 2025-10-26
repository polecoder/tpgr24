<%@ page
  contentType="text/html"
  pageEncoding="UTF-8"
%>
<%@ page import="java.util.*, datatypes.*"%>
<%@ page import="com.eventosuy.models.TipoUsuario"%>

<%
DTDetalleUsuario datosUsuario = (DTDetalleUsuario) session.getAttribute("usuarioLogeado");
TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuarioLogeado");

List<DTDetalleUsuario> usuarios = (List<DTDetalleUsuario>) request.getAttribute("usuarios");
List<String> categorias = (List<String>) request.getAttribute("categorias");

List<DTEvento> eventosEnCurso = (List<DTEvento>) request.getAttribute("eventosEnCurso");
List<DTEvento> eventosFinalizados = (List<DTEvento>) request.getAttribute("eventosFinalizados");

// Nuevo: parámetro de categoría seleccionada
String categoriaSeleccionada = request.getParameter("categoria");
if (categoriaSeleccionada != null && categoriaSeleccionada.equalsIgnoreCase("Todas")) {
	categoriaSeleccionada = null; // “Todas” actúa como sin filtro
}
%>

<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="WEB-INF/views/partials/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
  <jsp:include page="./WEB-INF/views/partials/header.jsp"></jsp:include>

  <!-- Contenido principal -->
  <div class="container flex-grow-1 mt-4">
    <div class="row g-5">
      <!-- Panel lateral -->
      <div class="col-12 col-md-4">
        <%
        if (datosUsuario != null && tipoUsuario == TipoUsuario.ORGANIZADOR) {
        %>
        <div class="mb-4">
          <h5 class="text-white bg-primary p-2 rounded">Altas</h5>
          <ul class="list-unstyled ps-2">
            <li><a
              href="<%=request.getContextPath()%>/Eventos?accion=alta"
              class="text-primary fw-semibold"
            > Alta Evento </a></li>
            <li><a
              href="${pageContext.request.contextPath}/Institucion?action=altaInstitucion"
              class="text-primary fw-semibold"
            > Alta Institución </a></li>
          </ul>
        </div>
        <%
        }
        %>

        <nav class="border border-primary rounded p-3 bg-white shadow-sm">
          <!-- Usuarios -->
          <div class="mb-4">
            <h5 class="text-white bg-primary p-2 rounded">Usuarios</h5>
            <%
            if (usuarios != null) {
            	for (DTDetalleUsuario u : usuarios) {
            %>
            <div class="d-flex align-items-center mb-2">
              <img
                src="<%=(u.getImagen() != null && u.getImagen().length > 0)
		? "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(u.getImagen())
		: request.getContextPath() + "/images/anonimo.svg"%>"
                alt="<%=u.getNombre()%>"
                class="rounded-circle me-2"
                style="width: 48px; height: 48px"
              /> <a
                href="usuario?accion=consulta&nick=<%=u.getNickname()%>"
                class="text-primary fw-semibold"
              > <%=u.getNombre()%>
              </a>
            </div>
            <%
            }
            }
            %>
          </div>

          <!-- Categorías -->
          <div>
            <h5 class="text-white bg-primary p-2 rounded">Categorías</h5>
            <ul class="list-unstyled ps-2">
              <li><a
                href="?categoria=Todas"
                class="text-primary small d-block mb-2 fw-semibold <%=(categoriaSeleccionada == null) ? "text-decoration-underline" : ""%>"
              > Todas </a></li>
              <%
              if (categorias != null) {
              	for (String nombreCategoria : categorias) {
              		if (nombreCategoria.equalsIgnoreCase("Todas")) continue; // Evita duplicar “Todas”
              		boolean seleccionada = nombreCategoria.equals(categoriaSeleccionada);
              %>
              <li><a
                href="?categoria=<%=nombreCategoria%>"
                class="text-primary small d-block mb-2 <%=seleccionada ? "fw-bold text-decoration-underline" : ""%>"
              > <%=nombreCategoria%>
              </a></li>
              <%
                }
              } else {
              %>
              <li>Sin categorías</li>
              <%
              }
              %>
            </ul>
          </div>
        </nav>
      </div>

      <!-- Eventos disponibles -->
      <div class="col-12 col-md-8 order-1 order-md-2 justify-content-center">

  <!-- EVENTOS EN CURSO -->
  <h2 class="text-center text-uppercase pb-3 fs-3 text-success">Eventos en curso</h2>

  <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 g-4 justify-content-center">
    <%
    if (eventosEnCurso != null && !eventosEnCurso.isEmpty()) {
      boolean hayEventos = false;
      for (DTEvento e : eventosEnCurso) {
        Set<String> cats = e.getCategorias();
        boolean mostrar = (categoriaSeleccionada == null) || (cats != null && cats.contains(categoriaSeleccionada));

        if (mostrar) {
          hayEventos = true;
    %>
      <div class="col">
        <div class="card h-100 shadow-sm">
          <a href="Eventos?accion=consulta&nombre=<%=e.getNombre()%>">
            <img
              src="<%=(e.getImagen() != null && e.getImagen().length > 0)
              ? "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(e.getImagen())
              : request.getContextPath() + "/images/sin-imagen.svg"%>"
              alt="<%=e.getNombre()%>"
              class="card-img-top"
              style="object-fit: cover; aspect-ratio: 1/1"
            />
          </a>
          <div class="card-body text-center d-flex justify-content-center align-items-center">
            <a
              href="Eventos?accion=consulta&nombre=<%=e.getNombre()%>"
              class="card-title fs-6 text-decoration-none text-dark fw-semibold m-0"
            ><%=e.getNombre()%></a>
          </div>
        </div>
      </div>
    <%
        }
      }
      if (!hayEventos) {
    %>
      <p class="text-center text-muted">No hay eventos en esta categoría.</p>
    <%
      }
    } else {
    %>
      <p class="text-center text-muted">No hay eventos en curso.</p>
    <%
    }
    %>
  </div>

  <!-- EVENTOS FINALIZADOS -->
  <h2 class="text-center text-uppercase pb-3 fs-3 text-secondary mt-5">Eventos finalizados</h2>

  <div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 g-4 justify-content-center">
    <%
    if (eventosFinalizados != null && !eventosFinalizados.isEmpty()) {
      boolean hayFinalizados = false;
      for (DTEvento e : eventosFinalizados) {
        Set<String> cats = e.getCategorias();
        boolean mostrar = (categoriaSeleccionada == null) || (cats != null && cats.contains(categoriaSeleccionada));

        if (mostrar) {
          hayFinalizados = true;
    %>
      <div class="col">
        <div class="card h-100 shadow-sm border-secondary">
          <img
            src="<%=(e.getImagen() != null && e.getImagen().length > 0)
            ? "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(e.getImagen())
            : request.getContextPath() + "/images/sin-imagen.svg"%>"
            alt="<%=e.getNombre()%>"
            class="card-img-top"
            style="object-fit: cover; aspect-ratio: 1/1; opacity: 0.6;"
          />
          <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
            <span class="badge bg-secondary mb-2">Finalizado</span>
            <span class="fw-semibold text-muted"><%=e.getNombre()%></span>
          </div>
        </div>
      </div>
    <%
        }
      }
      if (!hayFinalizados) {
    %>
      <p class="text-center text-muted">No hay eventos finalizados en esta categoría.</p>
    <%
      }
    } else {
    %>
      <p class="text-center text-muted">No hay eventos finalizados.</p>
    <%
    }
    %>
  </div>

</div>
    </div>
  </div>

  <jsp:include page="./WEB-INF/views/partials/footer.jsp" />
</body>
</html>
