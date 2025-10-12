<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="es">
  <!-- HEAD GLOBAL -->
  <jsp:include page="/WEB-INF/templates/head.jsp" />

  <body class="d-flex flex-column min-vh-100">

    <!-- HEADER GLOBAL -->
    <jsp:include page="/WEB-INF/templates/header.jsp" />

    <%
      // Datos que prepara el servlet
      Object listaAttr   = request.getAttribute("usuarios");   // Collection<DTUsuario> o null
      Object usuarioAttr = request.getAttribute("usuario");    // DTDetalleUsuario o null
      String ctx         = request.getContextPath();
      String nickSesion  = (String) session.getAttribute("nick");
    %>

    <div class="container flex-grow-1 my-4">

      <%-- ======================= LISTA DE USUARIOS ======================= --%>
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

      <%-- ======================= DETALLE DE UN USUARIO ======================= --%>
      <% } else if (usuarioAttr != null) { %>
        <%
          // Resolver la imagen: si el servlet mandó 'imagenDataUri', úsala; si no, imagen por defecto
          String imgSrc = ctx + "/images/IMG-US01.jpg";
          Object imgDataUri = request.getAttribute("imagenDataUri");
          if (imgDataUri != null) {
              imgSrc = String.valueOf(imgDataUri);
          }
        %>

        <div class="mx-auto" style="max-width: 512px">
          <h1 class="text-center text-uppercase py-3 fs-2 fs-sm-3">Datos de usuario</h1>

          <form class="bg-light border-top border-2 border-primary rounded p-4 flex-grow-1">

            <%-- Imagen (solo si hay imagenDataUri) --%>
<% Object _img = request.getAttribute("imagenDataUri"); %>
<% if (_img != null && !String.valueOf(_img).isBlank()) { %>
  <div class="mb-3 row">
    <label class="col-sm-4 col-form-im text-start"></label>
    <div class="col-sm-8">
      <img src="<%= String.valueOf(_img) %>" alt="Imagen usuario"
           class="img-fluid rounded" style="max-height:100px;" />
    </div>
  </div>
<% } %>

            <!-- Nickname -->
            <div class="mb-3 row">
              <label class="col-sm-4 col-form-label text-end">Nickname:</label>
              <div class="col-sm-8">
                <input type="text" class="form-control form-control-sm" readonly value="${usuario.nickname}" />
              </div>
            </div>

            <!-- Nombre -->
            <div class="mb-3 row">
              <label class="col-sm-4 col-form-label text-end">Nombre:</label>
              <div class="col-sm-8">
                <input type="text" class="form-control form-control-sm" readonly value="${usuario.nombre}" />
              </div>
            </div>

            <!-- Correo -->
            <div class="mb-3 row">
              <label class="col-sm-4 col-form-label text-end">Correo:</label>
              <div class="col-sm-8">
                <input type="text" class="form-control form-control-sm" readonly value="${usuario.correo}" />
              </div>
            </div>

            <%
  // Inferir tipo de usuario correctamente usando instanceof
  boolean esAsistente  = (usuarioAttr instanceof datatypes.DTDetalleAsistente);
  boolean esOrganizador= (usuarioAttr instanceof datatypes.DTDetalleOrganizador);
%>

            <% if (esAsistente) { %>
  <!-- Panel ASISTENTE -->
  <div id="asistenteFields">
    <div class="mb-3 row">
      <label class="col-sm-4 col-form-label text-end">Apellido:</label>
      <div class="col-sm-8">
        <input type="text" class="form-control form-control-sm" readonly value="${usuario.apellido}" />
      </div>
     </div>
	 <% if (((datatypes.DTDetalleAsistente)usuarioAttr).getInstitucion()!=null){%>
	    <div class="mb-3 row">
	      <label class="col-sm-4 col-form-label text-end">Institución:</label>
	      <div class="col-sm-8">
	        <input type="text" class="form-control form-control-sm" readonly value="${usuario.institucion}" />
	      </div>
	    </div>
	<%}%>
    <div class="mb-3 row">
      <label class="col-sm-4 col-form-label text-end">Nacimiento:</label>
      <div class="col-sm-8">
        <input type="text" class="form-control form-control-sm" readonly value="${usuario.nacimiento}" />
      </div>
    </div> 

    <%
      // Normalizo a lista vacía para que el bloque siempre se muestre
      Object regsObj = request.getAttribute("registros"); // List<DTRegistro> o null
      java.util.List<?> regs = (regsObj instanceof java.util.List) ? (java.util.List<?>) regsObj : java.util.Collections.emptyList();
    %>
    <div class="mb-3 row">
      <label class="col-sm-4 col-form-label text-end">Registros:</label>
      <div class="col-sm-8 d-flex flex-column gap-2">
        <% if (regs.isEmpty()) { %>
          <div class="form-text">Aún no tenés registros.</div>
        <% } else { 
             for (int i=0; i<regs.size(); i++) {
               Object r = regs.get(i);
               String nomEd  = "";
               try {
                 java.lang.reflect.Method mEd = r.getClass().getMethod("nombreEdicion");
                 nomEd = String.valueOf(mEd.invoke(r));
               } catch(Exception ignore) {}
        %>
          <a class="text-primary text-decoration-underline"
             href="<%=ctx%>/usuario?accion=consulta-registro&idx=<%=i%>"><%= nomEd %></a>
        <% } } %>
      </div>
    </div>
  </div>
<% } %>

            <% if (esOrganizador) { %>
  <!-- Panel ORGANIZADOR -->
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
      // Normalizo ambas colecciones para que el bloque SIEMPRE se muestre
      Object edsAcepObj = request.getAttribute("edicionesAceptadas"); // Collection<String> o null
      Object misEdsIngObj = request.getAttribute("misEdicionesIngresadas"); // Collection<String> o null
      Object misEdsRecObj = request.getAttribute("misEdicionesRechazadas"); // Collection<String> o null
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
              <option value="<%=String.valueOf(s)%>"><%=String.valueOf(s)%> (ACEPTADA)</option>
            <% } %>
            <% for (Object s : misEdsIngCol) { %>
              <option value="<%=String.valueOf(s)%>"><%=String.valueOf(s)%> (INGRESADA)</option>
            <% } %>
            <% for (Object s : misEdsRecCol) { %>
              <option value="<%=String.valueOf(s)%>"><%=String.valueOf(s)%> (RECHAZADA)</option>
            <% } %>
          </select>
        <% } %>
      </div>
    </div>
  </div>
<% } %>

            <%
              // Botón Modificar si es su propio perfil
              String nickDetalle = null;
              try {
                java.lang.reflect.Method mNickDet = usuarioAttr.getClass().getMethod("nickname");
                nickDetalle = String.valueOf(mNickDet.invoke(usuarioAttr));
              } catch (Exception ignore) { nickDetalle = ""; }
              boolean esPropio = (nickSesion != null && nickSesion.equals(nickDetalle));
              if (esPropio) {
            %>
              <div class="text-end mt-4">
                <a href="<%=ctx%>/usuario?accion=modificar" class="btn btn-primary">Modificar Usuario</a>
              </div>
            <% } %>

          </form>
        </div>

      <%-- ======================= SIN DATOS ======================= --%>
      <% } else { %>
        <div class="alert alert-warning mt-4">No hay información para mostrar.</div>
      <% } %>

    </div>

    <!-- FOOTER GLOBAL -->
    <jsp:include page="/WEB-INF/templates/footer.jsp" />

    <!-- JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  </body>
</html>