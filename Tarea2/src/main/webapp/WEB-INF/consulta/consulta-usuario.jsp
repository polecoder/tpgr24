<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="datatypes.DTDetalleAsistente" %>
<%@ page import="datatypes.DTRegistro" %>
<!DOCTYPE html>
<html lang="es">
<jsp:include page="/WEB-INF/templates/head.jsp" />

<body class="d-flex flex-column min-vh-100">
<%@ page import = "java.text.SimpleDateFormat" %>

<jsp:include page="/WEB-INF/templates/header.jsp" />

<%
  Object listaAttr   = request.getAttribute("usuarios");
  Object usuarioAttr = request.getAttribute("usuario");
  String ctx         = request.getContextPath();
  String nickSesion  = (String) session.getAttribute("nick");
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

<div class="mx-auto" style="max-width: 512px">
  <h1 class="text-center text-uppercase py-3 fs-2 fs-sm-3">Datos de usuario</h1>

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
      boolean esAsistente  = (usuarioAttr instanceof datatypes.DTDetalleAsistente);
      boolean esOrganizador= (usuarioAttr instanceof datatypes.DTDetalleOrganizador);
    %>

    <% if (esAsistente) { %>
    <div id="asistenteFields">

      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Apellido:</label>
        <div class="col-sm-8">
          <input type="text" class="form-control form-control-sm" readonly value="${usuario.apellido}" />
        </div>
      </div>

      <% if (((datatypes.DTDetalleAsistente)usuarioAttr).getInstitucion()!=null){ %>
      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Institución:</label>
        <div class="col-sm-8">
          <input type="text" class="form-control form-control-sm" readonly value="${usuario.institucion}" />
        </div>
      </div>
      <% } %>

      <% SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); %>
      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Nacimiento:</label>
        <div class="col-sm-8">
          <input type="text" class="form-control form-control-sm" readonly 
                 value="<%= formatter.format(((datatypes.DTDetalleAsistente)usuarioAttr).getNacimiento()) %>" />
        </div>
      </div>

      <% if (regs != null && !regs.isEmpty()) { %>
      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Registros:</label>
        <div class="col-sm-8 d-flex flex-column gap-2">
          <% for (int i=0; i < regs.size(); i++) {
               DTRegistro registro = regs.get(i); %>
          <a class="text-primary text-decoration-underline" href="<%=ctx%>/usuario?accion=consulta-registro&idx=<%=i%>"><%= registro.nombreEdicion() %></a>
          <% } %>
        </div>
      </div>
      <% } %>

    </div>
    <% } %>

    <% if (esOrganizador) { %>
    <div id="organizadorFields">
      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Descripción:</label>
        <div class="col-sm-8">
          <textarea class="form-control form-control-sm" rows="2" readonly>${usuario.descripcion}</textarea>
        </div>
      </div>

      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Link:</label>
        <div class="col-sm-8">
          <input type="text" class="form-control form-control-sm" readonly value="${usuario.link}" />
        </div>
      </div>

      <%
        Object edsAcepObj = request.getAttribute("edicionesAceptadas");
        Object misEdsIngObj = request.getAttribute("misEdicionesIngresadas");
        Object misEdsRecObj = request.getAttribute("misEdicionesRechazadas");
        java.util.Collection<?> edsAcepCol = (edsAcepObj instanceof java.util.Collection) ? (java.util.Collection<?>) edsAcepObj : java.util.Collections.emptyList();
        java.util.Collection<?> misEdsIngCol = (misEdsIngObj instanceof java.util.Collection) ? (java.util.Collection<?>) misEdsIngObj : java.util.Collections.emptyList();
        java.util.Collection<?> misEdsRecCol = (misEdsRecObj instanceof java.util.Collection) ? (java.util.Collection<?>) misEdsRecObj : java.util.Collections.emptyList();
        boolean sinNada = edsAcepCol.isEmpty() && misEdsIngCol.isEmpty() && misEdsRecCol.isEmpty();
      %>

      <div class="mb-3 row">
        <label class="col-sm-4 col-form-label text-end">Ediciones:</label>
        <div class="col-sm-8">
          <% if (sinNada) { %>
          <div class="form-text">No hay ediciones para mostrar.</div>
          <% } else { %>
          <select class="form-select form-select-sm"
                  onchange="if(this.value){location.href='<%=ctx%>/ediciones?accion=consulta&nombre='+encodeURIComponent(this.value);}">
            <option value="">-- seleccionar --</option>
            <% for (Object s : edsAcepCol) { %>
            <option value="<%=s%>"><%=s%> (ACEPTADA)</option>
            <% } %>
            <% for (Object s : misEdsIngCol) { %>
            <option value="<%=s%>"><%=s%> (INGRESADA)</option>
            <% } %>
            <% for (Object s : misEdsRecCol) { %>
            <option value="<%=s%>"><%=s%> (RECHAZADA)</option>
            <% } %>
          </select>
          <% } %>
        </div>
      </div>
    </div>
    <% } %>

    <%
      String nickDetalle = "";
      try {
        java.lang.reflect.Method mNickDet = usuarioAttr.getClass().getMethod("nickname");
        nickDetalle = String.valueOf(mNickDet.invoke(usuarioAttr));
      } catch (Exception ignore) {}
      boolean esPropio = (nickSesion != null && nickSesion.equals(nickDetalle));
      if (esPropio) {
    %>
    <div class="text-end mt-4">
      <a href="<%=ctx%>/usuario?accion=modificar" class="btn btn-primary">Modificar Usuario</a>
    </div>
    <% } %>

  </form>
</div>

<% } else { %>
<div class="alert alert-warning mt-4">No hay información para mostrar.</div>
<% } %>

</div>

<jsp:include page="/WEB-INF/templates/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
