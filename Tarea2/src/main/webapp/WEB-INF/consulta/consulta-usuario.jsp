<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Consulta Usuario</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>

<body class="d-flex flex-column min-vh-100">
<%
  // Datos de sesión para decidir qué NAV mostrar
  String rolSesion  = (String) session.getAttribute("rol");          // "asistente" | "organizador" | null (visitante)
  String nickSesion = (String) session.getAttribute("nick");         // ej. "atorres"

  // ¿estoy listando o viendo detalle?
  Object listaAttr  = request.getAttribute("usuarios");
  Object usuarioAttr= request.getAttribute("usuario");

  // Helper para componer URLs
  String ctx = request.getContextPath();
%>

<%-- ======================= NAV según rol ======================= --%>
<% if ("asistente".equalsIgnoreCase(rolSesion)) { %>
  <nav class="d-flex justify-content-between bg-primary p-2 p-sm-3">
    <a href="<%=ctx%>/index.jsp" class="fw-bold text-white text-decoration-none fs-5 fs-sm-4" style="line-height: 48px">
      eventos.uy
    </a>
    <ul class="list-unstyled m-0 d-flex gap-3">
      <li class="d-flex gap-3">
        <img src="<%=ctx%>/images/IMG-US01.jpg" alt="Imagen usuario" style="width:48px;height:48px" />
        <a href="<%=ctx%>/usuario?accion=consulta&nick=<%= (nickSesion!=null?nickSesion:"") %>"
           class="text-white m-0" style="line-height: 48px"><%= (nickSesion!=null?nickSesion:"") %></a>
      </li>
      <li class="text-uppercase text-white" style="line-height: 48px">
        <a href="<%=ctx%>/index.jsp" class="btn btn-light btn-sm ms-2">Cerrar Sesión</a>
      </li>
    </ul>
  </nav>
<% } else if ("organizador".equalsIgnoreCase(rolSesion)) { %>
  <nav class="d-flex justify-content-between bg-primary p-2 p-sm-3">
    <a href="<%=ctx%>/index.jsp" class="fw-bold text-white text-decoration-none fs-5 fs-sm-4" style="line-height: 48px">
      eventos.uy
    </a>
    <ul class="list-unstyled m-0 d-flex gap-3">
      <li class="d-flex gap-3">
        <img src="<%=ctx%>/images/IMG-US04.jpeg" alt="Imagen usuario" style="width:48px;height:48px" />
        <a href="<%=ctx%>/usuario?accion=consulta&nick=<%= (nickSesion!=null?nickSesion:"") %>"
           class="text-white m-0" style="line-height: 48px">MisEventos</a>
      </li>
      <li class="text-uppercase text-white" style="line-height: 48px">
        <a href="<%=ctx%>/index.jsp" class="btn btn-light btn-sm ms-2">Cerrar Sesión</a>
      </li>
    </ul>
  </nav>
<% } else { %> <%-- visitante --%>
  <nav class="d-flex justify-content-between bg-primary p-2 p-sm-3">
    <a href="<%=ctx%>/index.jsp" class="fw-bold text-white text-decoration-none fs-5 fs-sm-4">
      eventos.uy
    </a>
    <ul class="list-unstyled m-0">
      <li>
        <a href="<%=ctx%>/pages/usuario/inicio-sesion.jsp"
           class="text-white text-uppercase text-decoration-underline small">Iniciar sesión</a>
      </li>
    </ul>
  </nav>
<% } %>

<div class="container flex-grow-1 my-4">

  <%-- ======================= LISTA DE USUARIOS ======================= --%>
  <% if (listaAttr != null) { %>
    <div class="mx-auto" style="max-width: 768px">
      <h1 class="text-center text-uppercase py-3 fs-2 fs-sm-3">Consulta Usuario</h1>

      <div class="bg-light border-top border-2 border-primary rounded p-4">
        <p class="mb-3">Seleccioná un usuario para ver el perfil:</p>
        <ul class="list-group">
          <%
            // Se espera Collection<DTUsuario>. Intento reflejar nombre y nickname de forma segura.
            for (Object o : (Collection<?>) listaAttr) {
              String nick   = "";
              String nombre = "";
              try {
                // Funciona tanto si es record con nickname() / nombre() como si tiene getters getNickname()/getNombre()
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
              <a class="btn btn-primary btn-sm"
                 href="<%=ctx%>/usuario?accion=consulta&nick=<%=nick%>">Ver perfil</a>
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
        <%-- Imagen (si tu backend expone una URL en request, úsala; si no, dejo una por defecto) --%>
        <div class="mb-3 row">
          <label class="col-sm-4 col-form-im text-start"></label>
          <div class="col-sm-8">
            <img src="<%= (request.getAttribute("imagenUrl")!=null? request.getAttribute("imagenUrl"): ctx + "/images/IMG-US01.jpg") %>"
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

        <%-- Panel ASISTENTE (si el backend setea estos campos) --%>
        <%
          // Para decidir qué panel mostrar, miro si existen campos típicos
          boolean tieneApellido = request.getAttribute("usuario")!=null &&
                                  pageContext.getAttribute("usuario")!=null;
          // Si usuario.apellido no existe, EL devuelve vacío; controlo con try/reflection:
          boolean esAsistente = false, esOrganizador = false;
          try {
            java.lang.reflect.Method m = usuarioAttr.getClass().getMethod("apellido");
            esAsistente = true;
          } catch (NoSuchMethodException e) {
            try {
              java.lang.reflect.Method m2 = usuarioAttr.getClass().getMethod("descripcion");
              esOrganizador = true;
            } catch (NoSuchMethodException ignore) {}
          }
        %>

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

            <%-- Registros del asistente (si el servlet los setea como 'registros') --%>
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

            <%-- Ediciones (aceptadas / propias) si el servlet las setea --%>
            <%
              Object edsAcep = request.getAttribute("edicionesAceptadas");  // List<String> o Set<String>
              Object edsProp = request.getAttribute("misEdiciones");        // para propio: ingresada/rechazada
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
          // Considero "propio" si coincide el nick de sesión con el nickname del detalle (EL lo baja a request como 'usuario.nickname')
          String nickDetalle = String.valueOf(request.getAttribute("nickDetalle"));
          if (nickDetalle == null || "null".equals(nickDetalle)) {
            // intento obtenerlo por reflexión del objeto
            try {
              java.lang.reflect.Method mNickDet = usuarioAttr.getClass().getMethod("nickname");
              nickDetalle = String.valueOf(mNickDet.invoke(usuarioAttr));
            } catch (Exception ignore) { nickDetalle = ""; }
          }
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

<footer class="bg-primary text-white d-flex justify-content-between align-items-center p-2 p-sm-3 mt-auto">
  <p class="fw-bold m-0">TPROG 2025</p>
  <p class="m-0">Grupo 24</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>