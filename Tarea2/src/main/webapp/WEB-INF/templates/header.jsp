<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="d-flex justify-content-between bg-primary p-2 p-sm-3">
	<a
		href="/Tarea2/index.jsp"
        class="fw-bold text-white text-decoration-none fs-5 fs-sm-4"
        style="line-height: 48px"
      >eventos.uy</a>
	<ul class="list-unstyled m-0 d-flex gap-3 align-items-center">
        <li class="d-flex gap-3 align-items-center">
          <img
            src=<%= session.getAttribute("usuarioImagen") %>
            alt="Imagen usuario"
            style="width: 48px; height: 48px"
          />
          <a
            href="./consulta-usuario-miseventos-organizador.html"
            class="text-white m-0"
            style="line-height: 48px"
            ><%= session.getAttribute("usuarioNombre") %>
          </a>
          <a href="../index-visitante.html" class="btn btn-light btn-sm ms-2"
            >CERRAR SESIÓN</a
          >
        </li>
	</ul>
</nav>