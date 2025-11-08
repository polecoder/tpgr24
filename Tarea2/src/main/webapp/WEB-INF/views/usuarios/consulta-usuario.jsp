<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.eventosuy.servidor.DTDetalleAsistente" %>
<%@ page import="com.eventosuy.servidor.DTDetalleUsuario" %>
<%@ page import="com.eventosuy.servidor.DTDetalleOrganizador" %>
<%@ page import="com.eventosuy.servidor.DTDetalleUsuario" %>
<%@ page import="com.eventosuy.servidor.DTRegistro" %>
<%@ page import="com.eventosuy.utils.Utilidades" %>
<!DOCTYPE html>
<html lang="es">
<jsp:include page="/WEB-INF/views/partials/head.jsp" />

<body class="d-flex flex-column min-vh-100">

<jsp:include page="/WEB-INF/views/partials/header.jsp" />

<%
  // =============== OBTENER DATOS DEL REQUEST ===============
  // lista de usuarios 
  List<DTDetalleUsuario> listaUsuarios = (List<DTDetalleUsuario>) request.getAttribute("usuarios");
  
  // usuario seleccionado
  Object detalleUsuario = request.getAttribute("usuario");
  DTDetalleUsuario usuarioConsultado = (DTDetalleUsuario) detalleUsuario;
  
  // registros del asistente
  List<DTRegistro> registrosAsistente = (List<DTRegistro>) request.getAttribute("registros");
  
  String contextoApp = request.getContextPath();
  
  // usuario logueado 
  String usuarioLogueado = (String) session.getAttribute("nick");
  
  // imagen del usuario
  String imagenUsuario = (String) request.getAttribute("imagenDataUri");
%>

<div class="container flex-grow-1 my-4">

<!-- =============== LISTA DE USUARIOS =============== -->
<% if (listaUsuarios != null) { %>
  <div class="mx-auto" style="max-width: 768px">
    <h1 class="text-center text-uppercase py-3 fs-2 fs-sm-3">Consulta Usuario</h1>
    
    <div class="bg-light border-top border-2 border-primary rounded p-4">
      <p class="mb-3">Seleccioná un usuario para ver el perfil:</p>
      
      <ul class="list-group">
        <%
          for (DTDetalleUsuario usuario : listaUsuarios) {
            String nickname = usuario.getNickname();
            String nombre = usuario.getNombre();
        %>
        <li class="list-group-item d-flex justify-content-between align-items-center">
          <span>
            <strong><%=nombre%></strong> 
            <span class="text-muted">@<%=nickname%></span>
          </span>
          <a class="btn btn-primary btn-sm" 
             href="<%=contextoApp%>/usuario?accion=consulta&nick=<%=nickname%>">
            Ver perfil
          </a>
        </li>
        <% } %>
      </ul>
    </div>
  </div>

<!-- =============== DETALLE DE USUARIO =============== -->
<% } else if (detalleUsuario != null) { %>
  
  <%
    //tipo de usuario
    boolean esAsistente = (detalleUsuario instanceof DTDetalleAsistente);
    boolean esOrganizador = (detalleUsuario instanceof DTDetalleOrganizador);
    
    DTDetalleUsuario usuarioBase = (DTDetalleUsuario) detalleUsuario;
    
    // verificar si el perfil es del usuario logueado
    boolean esPerfilPropio = (usuarioLogueado != null && usuarioLogueado.equals(usuarioBase.getNickname()));
  %>

  <div class="mx-auto" style="max-width: 720px">
    <h1 class="text-center text-uppercase py-3 fs-2 fs-sm-3">Datos de usuario</h1>

  <div class="row">
  	<div class="col-lg-8">
    <form class="bg-light border-top border-2 border-primary rounded p-4 flex-grow-1">

      <!-- Imagen del usuario -->
      <% if (imagenUsuario != null && !imagenUsuario.isBlank()) { %>
      <div class="mb-4 text-center">
        <img src="<%=imagenUsuario%>" 
             alt="Imagen usuario" 
             class="rounded-circle shadow-sm" 
             style="width: 120px; height: 120px; object-fit: cover;" />
      </div>
      <% } %>

      <!-- Campos comunes: Nickname, Nombre, Correo -->
      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Nickname:</label>
        <div class="col-sm-8">
          <input type="text" class="form-control form-control-sm" readonly value="${usuario.nickname}" />
        </div>
      </div>

      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Nombre:</label>
        <div class="col-sm-8">
          <input type="text" class="form-control form-control-sm" readonly value="${usuario.nombre}" />
        </div>
      </div>

      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Correo:</label>
        <div class="col-sm-8">
          <input type="text" class="form-control form-control-sm" readonly value="${usuario.correo}" />
        </div>
      </div>

      <!-- =============== CAMPOS ESPECÍFICOS DE ASISTENTE =============== -->
      <% if (esAsistente) { %>
        <%
          DTDetalleAsistente asistente = (DTDetalleAsistente) detalleUsuario;
          
          String fechaNacimiento = "";
          if (asistente.getNacimiento() != null) {
            Date fecha = Utilidades.XMLGregorianCalendarToDate(asistente.getNacimiento());
            fechaNacimiento = new SimpleDateFormat("dd-MM-yyyy").format(fecha);
          }
          
          // nombre de institución si existe
          String nombreInstitucion = (String) request.getAttribute("institucionNombre");
        %>

        <div class="mb-3 row">
          <label class="col-sm-4 col-form-label text-end">Apellido:</label>
          <div class="col-sm-8">
            <input type="text" class="form-control form-control-sm" readonly 
                   value="<%=asistente.getApellido()%>" />
          </div>
        </div>

        <% if (nombreInstitucion != null && !nombreInstitucion.isBlank()) { %>
        <div class="mb-3 row">
          <label class="col-sm-4 col-form-label text-end">Institución:</label>
          <div class="col-sm-8">
            <input type="text" class="form-control form-control-sm" readonly 
                   value="<%=nombreInstitucion%>" />
          </div>
        </div>
        <% } %>

        <div class="mb-3 row">
          <label class="col-sm-4 col-form-label text-end">Nacimiento:</label>
          <div class="col-sm-8">
            <input type="text" class="form-control form-control-sm" readonly 
                   value="<%=fechaNacimiento%>" />
          </div>
        </div>

        <!-- Lista de registros del asistente -->
        <% if (registrosAsistente != null && !registrosAsistente.isEmpty()) { %>
        <div class="mb-3 row">
          <label class="col-sm-4 col-form-label text-end">Registros:</label>
          <div class="col-sm-8 d-flex flex-column gap-2">
            <% 
              for (int indice = 0; indice < registrosAsistente.size(); indice++) {
                DTRegistro registro = registrosAsistente.get(indice);
                String nombreEdicion = registro.getNombreEdicion();
                
                // No mostrar ediciones archivadas o inválidas
                if (!nombreEdicion.equals("(sin edición)")) { 
            %>
              <a class="text-primary text-decoration-underline" 
                 href="<%=contextoApp%>/usuario?accion=consulta-registro&idx=<%=indice%>">
                <%=nombreEdicion%>
              </a>
            <%
                }
              }
            %>
          </div>
        </div>
        <% } %>

      <!-- =============== CAMPOS ESPECÍFICOS DE ORGANIZADOR =============== -->
      <% } else if (esOrganizador) { %>
        <%
          DTDetalleOrganizador organizador = (DTDetalleOrganizador) detalleUsuario;
          String descripcion = organizador.getDescripcion() != null ? organizador.getDescripcion() : "";
          String link = organizador.getLink() != null ? organizador.getLink() : "";
          
          //  ediciones del organizador
          List<String> edicionesAceptadas = (List<String>) request.getAttribute("edicionesAceptadas");
          List<String> edicionesIngresadas = (List<String>) request.getAttribute("misEdicionesIngresadas");
          List<String> edicionesRechazadas = (List<String>) request.getAttribute("misEdicionesRechazadas");
          List<String> edicionesArchivadas = (List<String>) request.getAttribute("misEdicionesArchivadas");
          
          // Manejar valores null
          if (edicionesAceptadas == null) edicionesAceptadas = Collections.emptyList();
          if (edicionesIngresadas == null) edicionesIngresadas = Collections.emptyList();
          if (edicionesRechazadas == null) edicionesRechazadas = Collections.emptyList();
          if (edicionesArchivadas == null) edicionesArchivadas = Collections.emptyList();
          
          boolean tieneEdiciones = !edicionesAceptadas.isEmpty() || 
                                   !edicionesIngresadas.isEmpty() || 
                                   !edicionesRechazadas.isEmpty() || 
                                   !edicionesArchivadas.isEmpty();
        %>

        <div class="mb-3 row">
          <label class="col-sm-4 col-form-label text-end">Descripción:</label>
          <div class="col-sm-8">
            <textarea class="form-control form-control-sm" rows="2" readonly><%=descripcion%></textarea>
          </div>
        </div>

        <div class="mb-3 row">
          <label class="col-sm-4 col-form-label text-end">Link:</label>
          <div class="col-sm-8">
            <input type="text" class="form-control form-control-sm" readonly 
                   value="<%=link%>" />
          </div>
        </div>

        <!-- Selector de ediciones del organizador -->
        <div class="mb-3 row">
          <label class="col-sm-4 col-form-label text-end">Ediciones:</label>
          <div class="col-sm-8">
            <% if (!tieneEdiciones) { %>
              <div class="form-text">No hay ediciones para mostrar.</div>
            <% } else { %>
              <select class="form-select form-select-sm"
                      onchange="if(this.value) window.location.href=this.value;">
                <option value="">-- seleccionar --</option>

                <!-- Ediciones aceptadas -->
                <% for (String nombreEdicion : edicionesAceptadas) {
                     String urlEdicion = contextoApp + "/Ediciones?accion=ConsultaEdicion&nombre_edicion=" 
                                       + java.net.URLEncoder.encode(nombreEdicion, "UTF-8");
                %>
                  <option value="<%=urlEdicion%>"><%=nombreEdicion%> (ACEPTADA)</option>
                <% } %>

                <!-- Ediciones ingresadas -->
                <% for (String nombreEdicion : edicionesIngresadas) {
                     String urlEdicion = contextoApp + "/Ediciones?accion=ConsultaEdicion&nombre_edicion=" 
                                       + java.net.URLEncoder.encode(nombreEdicion, "UTF-8");
                %>
                  <option value="<%=urlEdicion%>"><%=nombreEdicion%> (INGRESADA)</option>
                <% } %>

                <!-- Ediciones rechazadas -->
                <% for (String nombreEdicion : edicionesRechazadas) {
                     String urlEdicion = contextoApp + "/Ediciones?accion=ConsultaEdicion&nombre_edicion=" 
                                       + java.net.URLEncoder.encode(nombreEdicion, "UTF-8");
                %>
                  <option value="<%=urlEdicion%>"><%=nombreEdicion%> (RECHAZADA)</option>
                <% } %>

                <!-- Ediciones archivadas -->
                <% for (String nombreEdicion : edicionesArchivadas) { %>
                  <option value="<%=nombreEdicion%>"><%=nombreEdicion%> (ARCHIVADA)</option>
                <% } %>
              </select>
            <% } %>
          </div>
        </div>
      <% } %>

    <%
      String nickDetalle = "";
      nickDetalle = usuarioConsultado.getNickname();
      boolean esPropio = (usuarioLogueado != null && usuarioLogueado.equals(nickDetalle));
      if (esPropio) {
    %>
	    <div class="text-end mt-4">
	      <a href="<%=ctx%>/usuario?accion=modificar" class="btn btn-primary">Modificar Usuario</a>
	    </div>
    <% } else {
    	List<String> listaSeguidores = usuarioConsultado.getDataTypeSeguidores();
    	if(listaSeguidores.contains(usuarioLogueado)){
    		%>
    		<div class="text-end mt-4">
		      <a href="<%=ctx%>/usuario?accion=dejarDeSeguir&nick=<%=usuarioConsultado.getNickname()%>" class="btn btn-primary">Dejar de seguir</a>
		    </div>
    		<%
    	}else{
    		%>
    		<div class="text-end mt-4">
		      <a href="<%=ctx%>/usuario?accion=seguir&nick=<%=usuarioConsultado.getNickname()%>" class="btn btn-primary">Seguir</a>
		    </div>
    		<%
    	}
       } 
    %>

    </form>
    	
  	</div>
  	<div class="col-lg-4">
      <div class="bg-light border-top border-2 border-secondary rounded p-4 mb-3">
        <h5 class="mb-3">Seguidores</h5>
        <ul class="list-group">
          <%
            List<String> seguidores = usuarioConsultado.getDataTypeSeguidores();
            if (seguidores != null && !seguidores.isEmpty()) {
              for (String seguidor : seguidores) {
          %>
		          <li class="list-group-item d-flex justify-content-between align-items-center">
		            <span><%= seguidor %></span>
		            <a href="<%=ctx%>/usuario?accion=consulta&nick=<%=seguidor%>" class="btn btn-sm btn-outline-primary">Ver</a>
		          </li>
          <%
              }
            } else {
          %>
          		<li class="list-group-item text-muted">No hay seguidores</li>
          <% } %>
        </ul>
      </div>
      <div class="bg-light border-top border-2 border-secondary rounded p-4">
        <h5 class="mb-3">Siguiendo</h5>
        <ul class="list-group">
          <%
            List<String> seguidos = usuarioConsultado.getDataTypeSeguidos();
            if (seguidos != null && !seguidos.isEmpty()) {
              for (String seguido : seguidos) {
          %>
		          <li class="list-group-item d-flex justify-content-between align-items-center">
		            <span><%= seguido %></span>
		            <a href="<%=ctx%>/usuario?accion=consulta&nick=<%=seguido%>" class="btn btn-sm btn-outline-primary">Ver</a>
		          </li>
          <%
              }
            } else {
          %>
          <li class="list-group-item text-muted">No sigue a nadie</li>
          <% } %>
        </ul>
      </div>
    </div>
  </div>
</div>

<% } else { %>
  <!-- =============== VISTA: SIN DATOS =============== -->
  <div class="alert alert-warning mt-4">
    No hay información para mostrar.
  </div>
<% } %>
 
</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
