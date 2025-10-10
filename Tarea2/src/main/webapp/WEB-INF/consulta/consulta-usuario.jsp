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
      Object listaAttr    = request.getAttribute("usuarios");   // Collection<DTUsuario> o null
      Object usuarioAttr  = request.getAttribute("usuario");    // DTDetalleUsuario o null
      String ctx          = request.getContextPath();
      String nickSesion   = (String) session.getAttribute("nick");
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
        <div class="mx-auto" style="max-width: 512px">
          <h1 class="text-center text-uppercase py-3 fs-2 fs-sm-3">Datos de usuario</h1>

          <form class="bg-light border-top border-2 border-primary rounded p-4 flex-grow-1">

            <%-- Imagen (si tenés servlet /img/usuario, usalo; si no, dejar default) --%>
            <div class="mb-3 row">
              <label class="col-sm-4 col-form-im text-start"></label>
              <div class="col-sm-8">
                <img
                  src="<%= (request.getAttribute("imagenUrl")!=null
                           ? String.valueOf(request.getAttribute("imagenUrl"))
                           : ctx + "/images/IMG-US01.jpg") %>"
                  alt="Imagen usuario" class="img-fluid rounded" style="max-height:100px;" />
              </div>
            </div>

            <%-- Nickname --%>
            <div class="mb-3 row">
              <label class="col-sm-4 col-form-label text-end">Nickname:</label>
              <div class="col-sm-8">
                <input type="text" class="form-control form-control-sm" readonly value="${usuario.nickname}" />
              </div>
            </div>

            <%-- Nombre --%>
            <div class="mb-3 row">
              <label class="col-sm-4 col-form-label text-end">Nombre:</label>
              <div class="col-sm-8">
                <input type="text" class="form-control form-control-sm" readonly value="${usuario.nombre}" />
              </div>
            </div>

            <%-- Correo --%>
            <div class="mb-3 row">
              <label class="col-sm-4 col-form-label text-end">Correo:</label>
              <div class="col-sm-8">
                <input type="text" class="form-control form-control-sm" readonly value="${usuario.correo}" />
              </div>
            </div>

            <%
              // Inferir tipo de usuario mirando métodos típicos en el DTO de detalle
              boolean esAsistente = false, esOrganizador = false;
              try { usuarioAttr.getClass().getMethod("apellido");     esAsistente = true; } catch (NoSuchMethodException ignore) {}
              try { usuarioAttr.getClass().getMethod("descripcion");  esOrganizador = true; } catch (NoSuchMethodException ignore) {}
            %>

            <%-- Panel ASISTENTE --%>
            <% if (esAsistente) { %>
              <div id="asistenteFields">
                <div class="mb-3 row">
                  <label class="col-sm-4 col-form-label text-end">Apellido:</label>
                  <div class="col-sm-8">
                    <input type="text" class="form-control form-control-sm" readonly value="${usuario.apellido}" />
                  </div>
                </div>

                <div class="mb-3 row">
                  <label class="col-sm-4 col-form-label text-end">Institución:</label>
                  <div class="col-sm-8">
                    <input type="text" class="form-control form-control-sm" readonly value="${usuario.institucion}" />
                  </div>
                </div>

                <div class="mb-3 row">
                  <label class="col-sm-4 col-form-label text-end">Nacimiento:</label>
                  <div class="col-sm-8">
                    <input type="text" class="form-control form-control-sm" readonly value="${usuario.fechaNacimiento}" />
                  </div>
                </div>

                <%-- Registros del asistente (si el servlet setea 'registros') --%>
                <%
                  Object regsAttr = request.getAttribute("registros"); // List<DTRegistro>
                  if (regsAttr instanceof List) {
                %>
                  <div class="mb-3 row">
                    <label class="col-sm-4 col-form-label text-end">Registros:</label>
                    <div class="col-sm-8 d-flex flex-column gap-2">
                      <%
                        List<?> regs = (List<?>) regsAttr;
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
                      <% } %>
                    </div>
                  </div>
                <% } %>
              </div>
            <% } %>

            <%-- Panel ORGANIZADOR --%>
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
                  Object edsAcep = request.getAttribute("edicionesAceptadas");  // Collection<String>
                  Object edsProp = request.getAttribute("misEdiciones");        // Collection<String>
                  if (edsAcep instanceof Collection || edsProp instanceof Collection) {
                %>
                  <div class="mb-3 row">
                    <label class="col-sm-4 col-form-label text-end">Ediciones:</label>
                    <div class="col-sm-8">
                      <select class="form-select form-select-sm"
                              onchange="if(this.value){location.href='<%=ctx%>/ediciones?accion=consulta&nombre='+encodeURIComponent(this.value);}">
                        <option value="">-- seleccionar --</option>
                        <% if (edsAcep instanceof Collection) {
                             for (Object s : (Collection<?>) edsAcep) { %>
                               <option value="<%=String.valueOf(s)%>"><%=String.valueOf(s)%> (ACEPTADA)</option>
                        <% } }
                           if (edsProp instanceof Collection) {
                             for (Object s : (Collection<?>) edsProp) { %>
                               <option value="<%=String.valueOf(s)%>"><%=String.valueOf(s)%></option>
                        <% } } %>
                      </select>
                    </div>
                  </div>
                <% } %>
              </div>
            <% } %>

            <%-- Botón Modificar (si es su propio perfil) --%>
            <%
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