<%@page
  contentType="text/html"
  pageEncoding="UTF-8"
%>
<%@page import="datatypes.*, java.util.Base64, com.eventosuy.mobile.models.TipoUsuario"%>
<%
	DTDetalleUsuario datosUsuario = (DTDetalleUsuario) session.getAttribute("usuarioLogeado");
    String cp = request.getContextPath();
    String imagenUsuario = datosUsuario != null
                           ? Base64.getEncoder().encodeToString(datosUsuario.getImagen())
                           : null;
    String srcBoton = datosUsuario != null
        ? "data:image/png;base64," + Base64.getEncoder().encodeToString(datosUsuario.getImagen())
            : cp + "/images/menu.svg";
%>
<%-- TODO: Actualizar diseño a mobile --%>
<nav class="navbar d-flex justify-content-between bg-primary p-2 p-sm-3">
  <a
    <%-- TODO: Actualizar link --%>
		href="#"
    class="logo fw-bold text-white text-decoration-none"
  >eventos.uy</a>
  <button
    id="nav-toggle"
    class="nav-toggle"
    aria-controls="primary-navigation"
    aria-expanded="false"
    aria-label="Abrir menú"
  >
    <img src="<%= srcBoton %>" class="nav-image" />
  </button>
  <nav
    id="primary-navigation"
    class="nav"
    aria-label="Menú principal"
  >
    <ul>
      <%
      if (datosUsuario == null ) {
      %>
      <li><a href="<%= cp %>/login">Iniciar sesión</a></li>
      <% 
      } else { 
      %>
      <%-- TODO: Actualizar link y agregar links a CUs --%>
      <li><a href="#">Mi perfil</a></li>
      <li><a href="<%= cp %>/logout">Cerrar sesión</a></li>      
      <% 
      }
      %>
    </ul>
  </nav>
</nav>
<script src="<%= cp %>/js/navbar.js" defer></script>