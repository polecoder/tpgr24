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
      <li class="d-flex gap-3 align-items-center">
		<%
			if (datosUsuario != null) {
    			// si la imagen existe, la codifica a String
    		    String imagenUsuario = datosUsuario.getImagen()!= null
    		                           ? java.util.Base64.getEncoder().encodeToString(datosUsuario.getImagen())
    		                           : null;
  		%>
              <% if (imagenUsuario != null) { %>
    	          <img src="data:image/png;base64,<%= imagenUsuario %>" alt="Imagen de <%= datosUsuario.getNickname() %>" style="width: 48px; height: 48px"/>
              <% } else { %>
                  <img src="./images/anonimo.svg" alt="Imagen de <%= datosUsuario.getNickname() %>" style="width: 48px; height: 48px" />
              <% } %>
	          <a
	            href="${pageContext.request.contextPath}/usuario?accion=consulta&nick=${datosUsuario.getNickname()}"
	            class="text-white m-0"
	            style="line-height: 48px"
	            ><%= datosUsuario.getNombre() %>
	          </a>
	          <a href="${pageContext.request.contextPath}/logout" class="btn btn-light btn-sm ms-2"
	            >CERRAR SESIÓN</a>
        <% } else { %>
		  	<a href="${pageContext.request.contextPath}/login" class="btn btn-light btn-sm ms-2"
            >INICIAR SESIÓN</a>
        <% } %>
        </li>
	</ul>
</nav>