<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>
<%@ page import="javax.xml.datatype.XMLGregorianCalendar" %>
<%@ page import="com.eventosuy.servidor.DTDetalleAsistente" %>
<%@ page import="com.eventosuy.servidor.DTDetalleUsuario" %>
<%@ page import="com.eventosuy.utils.Utilidades" %>
<%@ page import="java.util.List" %>
<%@ page import="com.eventosuy.servidor.DTRegistro" %>
<!DOCTYPE html>
<html lang="es">
<jsp:include page="/WEB-INF/views/partials/head.jsp" />

<body class="d-flex flex-column min-vh-100">
<%@ page import = "java.text.SimpleDateFormat" %>

<jsp:include page="/WEB-INF/views/partials/header.jsp" />

<%
  Object listaAttr   = request.getAttribute("usuarios");
  Object usuarioAttr = request.getAttribute("usuario");
  DTDetalleUsuario usuarioConsultado = (DTDetalleUsuario) usuarioAttr;
  String ctx         = request.getContextPath();
  DTDetalleUsuario usuarioLogeado = (DTDetalleUsuario) session.getAttribute("usuarioLogeado");
  String nickSesion  = (String) session.getAttribute("nick");
  String nickUsuarioLogeado = usuarioLogeado == null? null : usuarioLogeado.getNickname();
  nickSesion = nickSesion == null ? nickUsuarioLogeado: nickSesion;
  List<DTRegistro> regs = (List<DTRegistro>) request.getAttribute("registros");
%>

<div class="container flex-grow-1 my-4">

<% if (listaAttr != null) { %>
<div class="mx-auto" style="max-width: 768px">
  <h1 class="text-center text-uppercase py-3 fs-2 fs-sm-3">Consulta Usuario</h1>
  <div class="bg-light border-top border-2 border-primary rounded p-4">
    <p class="mb-3">Seleccioná un usuario para ver el perfil:</p>
    <ul class="list-group">
      <%
        for (Object o : (Collection<?>) listaAttr) {
          String nick = "";
          String nombre = "";
          try {
            java.lang.reflect.Method mNick = null, mNom = null;
            try { mNick = o.getClass().getMethod("nickname"); } catch (NoSuchMethodException ignore) {}
            try { mNom  = o.getClass().getMethod("nombre");   } catch (NoSuchMethodException ignore) {}
            if (mNick == null) mNick = o.getClass().getMethod("getNickname");
            if (mNom  == null) mNom  = o.getClass().getMethod("getNombre");
            nick   = String.valueOf(mNick.invoke(o));
            nombre = String.valueOf(mNom.invoke(o));
          } catch (Exception ignore) {}
      %>
      <li class="list-group-item d-flex justify-content-between align-items-center">
        <span><strong><%=nombre%></strong> <span class="text-muted">@<%=nick%></span></span>
        <a class="btn btn-primary btn-sm" href="<%=ctx%>/usuario?accion=consulta&nick=<%=nick%>">Ver perfil</a>
      </li>
      <% } %>
    </ul>
  </div>
</div>

<% } else if (usuarioAttr != null) { %>
<%
  String imgSrc = ctx + "/images/IMG-US01.jpg";
  Object imgDataUri = request.getAttribute("imagenDataUri");
  if (imgDataUri != null) {
      imgSrc = String.valueOf(imgDataUri);
  }
%>

<div class="mx-auto" style="max-width: 720px">
  <h1 class="text-center text-uppercase py-3 fs-2 fs-sm-3">Datos de usuario</h1>

  <div class="row">
  	<div class="col-lg-8">
  <form class="bg-light border-top border-2 border-primary rounded p-4 flex-grow-1">

    <% Object _img = request.getAttribute("imagenDataUri"); %>
    <% if (_img != null && !String.valueOf(_img).isBlank()) { %>
    <div class="mb-3 row">
      <div class="col-sm-8 offset-sm-4">
        <img src="<%= String.valueOf(_img) %>" alt="Imagen usuario" class="img-fluid rounded" style="max-height:100px;" />
      </div>
    </div>
    <% } %>

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
    

    <%
      boolean esAsistente  = (usuarioAttr instanceof com.eventosuy.servidor.DTDetalleAsistente);
      boolean esOrganizador= (usuarioAttr instanceof com.eventosuy.servidor.DTDetalleOrganizador);
    %>

    <% if (esAsistente) { %>
    <div id="asistenteFields">

      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Apellido:</label>
        <div class="col-sm-8">
          <input type="text" class="form-control form-control-sm" readonly value="${usuario.apellido}" />
        </div>
      </div>
		<%
			String institucionNombre = (String) request.getAttribute("institucionNombre");
		%>
      <% if (institucionNombre != null && !institucionNombre.isBlank()) { %>
    <div class="mb-3 row">
      <label class="col-sm-4 col-form-label text-end">Institución:</label>
      <div class="col-sm-8">
        <input type="text" class="form-control form-control-sm" readonly value="<%= institucionNombre %>" />
      </div>
    </div>
    <% } %>
    
    <%
     String nacimientoStr = "";
     com.eventosuy.servidor.DTDetalleAsistente asis = (com.eventosuy.servidor.DTDetalleAsistente) usuarioAttr;
      javax.xml.datatype.XMLGregorianCalendar xgc = asis.getNacimiento();  
      if (xgc != null) {
          Date d = Utilidades.XMLGregorianCalendarToDate(xgc); 
          nacimientoStr = new SimpleDateFormat("dd-MM-yyyy").format(d);
  }
%>

      <% SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); %>
      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Nacimiento:</label>
        <div class="col-sm-8">
          <input type="text" class="form-control form-control-sm" readonly value="<%= nacimientoStr %>" />
        </div>
      </div>

      <% if (regs != null && !regs.isEmpty()) { %>
      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Registros:</label>
        <div class="col-sm-8 d-flex flex-column gap-2">
          <% for (int i=0; i < regs.size(); i++) {
               DTRegistro registro = regs.get(i); 
               // si la edición tiene un nombre inválido (probablemente por estar archivada, no muestro el registro)
               if (registro.getNombreEdicion() != "(sin edición)") { %>
                 <a class="text-primary text-decoration-underline" href="<%=ctx%>/usuario?accion=consulta-registro&idx=<%=i%>"><%= registro.getNombreEdicion() %></a>
          <%
               }
             } 
          %>
        </div>
      </div>
      <% } %>
 
    </div>
    <% } %>
    
    <%
  		com.eventosuy.servidor.DTDetalleOrganizador org = null;
  			String descOrg = "";
  			String linkOrg = "";

  if (usuarioAttr instanceof com.eventosuy.servidor.DTDetalleOrganizador) {
    org = (com.eventosuy.servidor.DTDetalleOrganizador) usuarioAttr;

    if (org.getDescripcion() != null) {
      descOrg = org.getDescripcion();
    }
    if (org.getLink() != null) {
      linkOrg = org.getLink();
    }
  }
%>

    <% if (esOrganizador) { %>
    <div id="organizadorFields">
      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Descripción:</label>
        <div class="col-sm-8">
          <textarea class="form-control form-control-sm" rows="2" readonly><%= descOrg %></textarea>
        </div>
      </div>

      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Link:</label>
        <div class="col-sm-8">
          <input type="text" class="form-control form-control-sm" readonly value="<%= linkOrg %>" />
        </div>
      </div>

      <%
        // TODO: Este código está horrible
        Object edsAcepObj = request.getAttribute("edicionesAceptadas");
        Object misEdsIngObj = request.getAttribute("misEdicionesIngresadas");
        Object misEdsRecObj = request.getAttribute("misEdicionesRechazadas");
        List<String> edicionesArchivadas = (List<String>) request.getAttribute("misEdicionesArchivadas");
        java.util.List<?> edsAcepCol, misEdsIngCol, misEdsRecCol;

        java.util.function.Function<Object, java.util.List<?>> toList = (obj) -> {
            if (obj == null) return java.util.Collections.emptyList();
            if (obj instanceof java.util.Collection<?> c) return new java.util.ArrayList<>(c);
            if (obj.getClass().isArray()) return java.util.Arrays.asList((Object[]) obj);
            return java.util.Collections.singletonList(obj); // último recurso: 1 elemento
        };

        edsAcepCol   = toList.apply(edsAcepObj);
        misEdsIngCol = toList.apply(misEdsIngObj);
        misEdsRecCol = toList.apply(misEdsRecObj);

        boolean sinNada = edsAcepCol.isEmpty() && misEdsIngCol.isEmpty() && misEdsRecCol.isEmpty() && edicionesArchivadas.isEmpty();
        %>
        
        <%-- DEBUG temporal
<div class="text-muted" style="font-size:12px">
  aceptadas=<%= edsAcepCol %>
</div>
--%>

      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Ediciones:</label>
        <div class="col-sm-8">
          <% if (sinNada) { %>
          <div class="form-text">No hay ediciones para mostrar.</div>
          <% } else { %>
          <select class="form-select form-select-sm"
        onchange="if(this.value){ window.location.href=this.value; }">
  <option value="">-- seleccionar --</option>

  <% for (Object s : edsAcepCol) {
       String nombre = String.valueOf(s);
       // Solo nombre_edicion, sin nombre_evento
       String url = ctx + "/Ediciones?accion=ConsultaEdicion&nombre_edicion="
                    + java.net.URLEncoder.encode(nombre, java.nio.charset.StandardCharsets.UTF_8);
  %>
    <option value="<%= url %>"><%= nombre %> (ACEPTADA)</option>
  <% } %>

  <% for (Object s : misEdsIngCol) {
       String nombre = String.valueOf(s);
       String url = ctx + "/Ediciones?accion=ConsultaEdicion&nombre_edicion="
                    + java.net.URLEncoder.encode(nombre, java.nio.charset.StandardCharsets.UTF_8);
  %>
    <option value="<%= url %>"><%= nombre %> (INGRESADA)</option>
  <% } %>

  <% for (Object s : misEdsRecCol) {
       String nombre = String.valueOf(s);
       String url = ctx + "/Ediciones?accion=ConsultaEdicion&nombre_edicion="
                    + java.net.URLEncoder.encode(nombre, java.nio.charset.StandardCharsets.UTF_8);
  %>
    <option value="<%= url %>"><%= nombre %> (RECHAZADA)</option>
  <% } %>
            <% for (String nombreEdicion : edicionesArchivadas) { %>
            <option value="<%=nombreEdicion%>"><%=nombreEdicion%> (ARCHIVADA)</option>
            <% } %>
</select>
          <% } %>
        </div>
      </div>
    </div>
    <% } %>

    <%
      String nickDetalle = "";
      nickDetalle = usuarioConsultado.getNickname();
      boolean esPropio = (nickSesion != null && nickSesion.equals(nickDetalle));
      if (esPropio) {
    %>
	    <div class="text-end mt-4">
	      <a href="<%=ctx%>/usuario?accion=modificar" class="btn btn-primary">Modificar Usuario</a>
	    </div>
    <% } else {
    	List<String> listaSeguidores = usuarioConsultado.getDataTypeSeguidores();
    	System.out.println("nickSesion: " + nickSesion);
    	System.out.println("listaSeguidores: " + listaSeguidores.toString());
    	if(listaSeguidores.contains(nickSesion)){
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
<div class="alert alert-warning mt-4">No hay información para mostrar.</div>
<% } %>
 
</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
