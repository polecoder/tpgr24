<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="datatypes.*, com.eventosuy.models.*" %>
<%
	DTDetalleUsuario datosUsuario = (DTDetalleUsuario) session.getAttribute("usuarioLogeado");
	TipoUsuario tipoUsuario = (TipoUsuario) session.getAttribute("tipoUsuarioLogeado");
%>
<nav class="d-flex justify-content-between bg-primary p-2 p-sm-3">
	<a
		href="${pageContext.request.contextPath}/home"
        class="fw-bold text-white text-decoration-none fs-5 fs-sm-4"
        style="line-height: 48px"
      >eventos.uy</a>
	<ul class="list-unstyled m-0 d-flex gap-3 align-items-center">
		<%
			if (datosUsuario != null) {
		%>
	        <li class="d-flex gap-3 align-items-center">
	          <!-- TODO: Modificar el src de la imagen -->
	          <img
	            src="./images/anonimo.svg"
	            alt="Imagen <%= datosUsuario.getNombre() %>>"
	            style="width: 48px; height: 48px"
	          />
	          <!-- TODO: Modificar el href -->
	          <a
	            href="#"
	            class="text-white m-0"
	            style="line-height: 48px"
	            ><%= datosUsuario.getNombre() %>
	          </a>
	          <!-- TODO: Modificar al hacer el logout -->
	          <a href="#" class="btn btn-light btn-sm ms-2"
	            >CERRAR SESIÓN</a
	          >
          <%
			} else {
		  %>
		  	<a href="${pageContext.request.contextPath}/login" class="btn btn-light btn-sm ms-2"
            >INICIAR SESIÓN</a>
          <%
			}
          %>
        </li>
	</ul>
</nav>