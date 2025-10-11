<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
  <!-- HEAD GLOBAL -->
  <jsp:include page="/WEB-INF/templates/head.jsp" />

  <body class="d-flex flex-column min-vh-100">

    <!-- HEADER GLOBAL -->
    <jsp:include page="/WEB-INF/templates/header.jsp" />

    <div class="container flex-grow-1">
      <div class="mx-auto" style="max-width: 768px">
        <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">REGISTRARSE</h1>
      </div>

      <div class="d-flex justify-content-between">
        <main class="mx-auto p-4 bg-light border-top border-2 border-primary rounded" style="max-width: 720px">
          <!-- Alertas -->
          <div id="alert-area" class="mb-3">
            <% String ok = request.getParameter("ok");
               if ("1".equals(ok)) { %>
              <div class="alert alert-success text-center">Usuario creado correctamente.</div>
            <% }
               Object err = request.getAttribute("error");
               if (err != null) { %>
              <div class="alert alert-danger text-center"><%= err %></div>
            <% } %>
          </div>

          <!-- FORM -->
          <form id="altaUsuarioForm"
                method="post"
                action="${pageContext.request.contextPath}/usuario?accion=alta"
                enctype="multipart/form-data"
                novalidate
                class="d-flex flex-column gap-4">

            <!-- Tipo -->
            <div class="d-flex justify-content-center gap-3">
              <label class="form-label text-end" style="width: 80px">Tipo:</label>
              <div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" name="rol" id="tipoAsistente" value="asistente"
                         <%= "asistente".equalsIgnoreCase(request.getParameter("rol")) ? "checked" : "" %> />
                  <label class="form-check-label" for="tipoAsistente">Asistente</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" name="rol" id="tipoOrganizador" value="organizador"
                         <%= "organizador".equalsIgnoreCase(request.getParameter("rol")) ? "checked" : "" %> />
                  <label class="form-check-label" for="tipoOrganizador">Organizador</label>
                </div>
                <div id="tipoFeedback" class="invalid-feedback d-block" style="display: none">Seleccioná un tipo.</div>
              </div>
            </div>

            <!-- Nickname -->
            <div class="d-flex justify-content-center gap-3">
              <label for="nickname" class="form-label text-end" style="width: 80px">Nickname:</label>
              <input type="text" id="nickname" name="nickname" required
                     class="form-control form-control-sm" style="max-width: 200px"
                     value="${param.nickname}" />
            </div>

            <!-- Nombre -->
            <div class="d-flex justify-content-center gap-3">
              <label for="nombre" class="form-label text-end" style="width: 80px">Nombre:</label>
              <input type="text" id="nombre" name="nombre" required
                     class="form-control form-control-sm" style="max-width: 200px"
                     value="${param.nombre}" />
            </div>

            <!-- Correo -->
            <div class="d-flex justify-content-center gap-3">
              <label for="email" class="form-label text-end" style="width: 80px">Correo:</label>
              <input type="email" id="email" name="email"
                     class="form-control form-control-sm" style="max-width: 200px"
                     value="${param.email}" />
            </div>

            <!-- Password -->
            <div class="d-flex justify-content-center gap-3">
              <label for="password" class="form-label text-end" style="width: 80px">Contraseña:</label>
              <input type="password" id="password" name="password"
                     class="form-control form-control-sm" style="max-width: 200px" />
            </div>

            <!-- Confirm -->
            <div class="d-flex justify-content-center gap-3">
              <label for="confirm" class="form-label text-end" style="width: 80px">Confirmación:</label>
              <input type="password" id="confirm" name="confirm"
                     class="form-control form-control-sm" style="max-width: 200px" required />
            </div>

            <!-- Imagen -->
            <div class="d-flex justify-content-center gap-3">
              <label for="imagen" class="form-label text-end" style="width: 80px">Imagen:</label>
              <input type="file" id="imagen" name="imagen" accept="image/*"
                     class="form-control form-control-sm" style="max-width: 200px" />
            </div>

            <!-- Bloque organizador -->
            <div id="bloqueOrganizador" class="d-none border-top pt-3">
              <div class="d-flex justify-content-center gap-3">
                <label for="descripcion" class="form-label text-end" style="width: 80px">Descripción:</label>
                <textarea id="descripcion" name="descripcion" rows="3"
                          class="form-control form-control-sm" style="max-width: 200px">${param.descripcion}</textarea>
              </div>
              <div class="d-flex justify-content-center gap-3">
                <label for="web" class="form-label text-end" style="width: 80px">Sitio web:</label>
                <input type="url" id="web" name="web" placeholder="https://…"
                       class="form-control form-control-sm" style="max-width: 200px"
                       value="${param.web}" />
              </div>
            </div>

            <!-- Bloque asistente -->
            <div id="bloqueAsistente" class="d-none border-top pt-3">
              <div class="d-flex justify-content-center gap-3">
                <label for="apellido" class="form-label text-end" style="width: 80px">Apellido:</label>
                <input type="text" id="apellido" name="apellido"
                       class="form-control form-control-sm" style="max-width: 200px"
                       value="${param.apellido}" />
              </div>

              <div class="d-flex justify-content-center gap-3">
                <label for="fechaNac" class="form-label text-end" style="width: 80px">Nacimiento:</label>
                <input type="date" id="fechaNac" name="fechaNac"
                       class="form-control form-control-sm" style="max-width: 200px"
                       value="${param.fechaNac}" />
              </div>

              <div class="d-flex justify-content-center gap-3">
                <label class="form-label text-end" style="width: 80px"></label>
                <div class="form-check">
                  <input id="perteneceInstitucion" name="perteneceInstitucion" type="checkbox" class="form-check-input"
                         <%= request.getParameter("institucion") != null && !request.getParameter("institucion").isBlank() ? "checked" : "" %> />
                  <label class="form-check-label" for="perteneceInstitucion">Pertenece a una institución</label>
                </div>
              </div>

              <div id="filaInstitucion" class="d-none d-flex justify-content-center gap-3">
                <label for="institucion" class="form-label text-end" style="width: 80px">Institución:</label>
                <input id="institucion" name="institucion"
                       class="form-control form-control-sm" style="max-width: 200px"
                       value="${param.institucion}" />
              </div>
            </div>

            <!-- Botones -->
            <div class="d-flex justify-content-center gap-3">
              <button type="submit" class="btn btn-primary btn-sm px-3">Aceptar</button>
              <button type="reset" class="btn btn-primary btn-sm px-3">Reset</button>
            </div>
          </form>
        </main>
      </div>
    </div>

    <!-- FOOTER GLOBAL -->
    <jsp:include page="/WEB-INF/templates/footer.jsp" />

    <!-- JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
      const tipoAsistente = document.getElementById("tipoAsistente");
      const tipoOrganizador = document.getElementById("tipoOrganizador");
      const bloqueAsistente = document.getElementById("bloqueAsistente");
      const bloqueOrganizador = document.getElementById("bloqueOrganizador");
      const chkInstitucion = document.getElementById("perteneceInstitucion");
      const filaInstitucion = document.getElementById("filaInstitucion");

      function toggleBloques() {
        bloqueAsistente.classList.toggle("d-none", !tipoAsistente.checked);
        bloqueOrganizador.classList.toggle("d-none", !tipoOrganizador.checked);
      }
      tipoAsistente.addEventListener("change", toggleBloques);
      tipoOrganizador.addEventListener("change", toggleBloques);

      (function init() {
        var rol = ("<%= request.getParameter("rol") != null ? request.getParameter("rol") : "" %>").toLowerCase();
        if (rol === "asistente") tipoAsistente.checked = true;
        if (rol === "organizador") tipoOrganizador.checked = true;
        toggleBloques();

        var instit = "<%= request.getParameter("institucion") != null ? request.getParameter("institucion") : "" %>";
        if (instit && instit.trim() !== "") {
          chkInstitucion.checked = true;
          filaInstitucion.classList.remove("d-none");
        }
      })();

      chkInstitucion.addEventListener("change", function() {
        filaInstitucion.classList.toggle("d-none", !chkInstitucion.checked);
      });
    </script>
  </body>
</html>