<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Set" %>
<% Set<String> listaNombresInstitucion = (Set<String>) request.getAttribute("listaNombresInstitucion"); %>
<!DOCTYPE html>
<html lang="es">
  <!-- HEAD GLOBAL -->
  <jsp:include page="/WEB-INF/views/partials/head.jsp" />

  <body class="d-flex flex-column min-vh-100">

    <!-- HEADER GLOBAL -->
    <jsp:include page="/WEB-INF/views/partials/header.jsp" />

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
              <label class="text-end" style="width: 90px">Tipo:</label>
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
                <div id="tipoFeedback" class="invalid-feedback" style="display: none">Seleccioná un tipo.</div>
              </div>
            </div>

			<!-- Script para mostrar asistente u organizador -->
			<script>
				function mostrarBloqueSi(idBloque, condicion) {
				  const bloque = document.getElementById(idBloque);
				  if (bloque) {
				    bloque.style.display = condicion ? "block" : "none";
				  }
				}
				function mostrarApellidoSi(idBloque, condicion) {
					const bloque = document.getElementById(idBloque);
					if (condicion) {
						bloque.classList.remove("d-none");
						bloque.classList.add("d-flex");	
					} else {
						bloque.classList.remove("d-flex");
						bloque.classList.add("d-none");
					}
				}

			 function actualizarContenido() {
			 	const seleccionado = document.querySelector('input[name="rol"]:checked');
			 	const tipo = seleccionado ? seleccionado.value : null;
			 	
				mostrarBloqueSi("bloqueAsistente", tipo === "asistente");
				mostrarBloqueSi("bloqueOrganizador", tipo === "organizador");
			    mostrarBloqueSi("tipoFeedback", !tipo);
			    mostrarApellidoSi("bloqueApellido", tipo === "asistente");
			  }
			
			  // Ejecutar al cargar la página
			  window.addEventListener("DOMContentLoaded", actualizarContenido);
			
			  // Ejecutar al cambiar selección
			  document.querySelectorAll('input[name="rol"]').forEach(el => {
			    el.addEventListener("change", actualizarContenido);
			  });
			</script>

            <!-- Nickname -->
            <div class="d-flex justify-content-center gap-3">
              <label for="nickname" class="form-label text-end" style="width: 90px">Nickname:</label>
              <input type="text" id="nickname" name="nickname" required
                     class="form-control form-control-sm" style="max-width: 200px"
                     value="${param.nickname}"/>
            </div>

            <!-- Nombre -->
            <div class="d-flex justify-content-center gap-3">
              <label for="nombre" class="form-label text-end" style="width: 90px">Nombre:</label>
              <input type="text" id="nombre" name="nombre" required
                     class="form-control form-control-sm" style="max-width: 200px"
                     value="${param.nombre}" />
            </div>
            
            <!--  Apellido -->
            <div id="bloqueApellido" class="d-none justify-content-center gap-3">
                <label for="apellido" class="form-label text-end" style="width: 90px">Apellido:</label>
                <input type="text" id="apellido" name="apellido"
                       class="form-control form-control-sm" style="max-width: 200px"
                       value="${param.apellido}" />
            </div>

            <!-- Correo -->
            <div class="d-flex justify-content-center gap-3">
              <label for="email" class="form-label text-end" style="width: 90px">Correo:</label>
              <input type="email" id="email" name="email"
                     class="form-control form-control-sm" style="max-width: 200px"
                     value="${param.email}" />
            </div>

            <!-- Password -->
            <div class="d-flex justify-content-center gap-3">
              <label for="password" class="form-label text-end" style="width: 90px">Contraseña:</label>
              <input type="password" id="password" name="password"
                     class="form-control form-control-sm" style="max-width: 200px" />
            </div>

            <!-- Confirm -->
            <div class="d-flex justify-content-center gap-3">
              <label for="confirm" class="form-label text-end" style="width: 90px">Confirmación:</label>
              <input type="password" id="confirm" name="confirm"
                     class="form-control form-control-sm" style="max-width: 200px" required />
            </div>

            <!-- Imagen -->
            <div class="d-flex justify-content-center gap-3">
              <label for="imagen" class="form-label text-end" style="width: 90px">Imagen:</label>
              <input type="file" id="imagen" name="imagen" accept="image/*"
                     class="form-control form-control-sm" style="max-width: 200px" />
            </div>

            <!-- Bloque organizador -->
            <div id="bloqueOrganizador" class="d-none border-top pt-3" style="display: none;">
              <div class="d-flex justify-content-center gap-3">
                <label for="descripcion" class="form-label text-end" style="width: 90px">Descripción:</label>
                <textarea id="descripcion" name="descripcion" rows="3"
                          class="form-control form-control-sm" style="max-width: 200px">${param.descripcion}</textarea>
              </div>
              <div class="d-flex justify-content-center gap-3 mt-3">
                <label for="web" class="form-label text-end" style="width: 90px">Sitio web:</label>
                <input type="url" id="web" name="web" placeholder="https://…"
                       class="form-control form-control-sm" style="max-width: 200px"
                       value="${param.web}" />
              </div>
            </div>

            <!-- Bloque asistente -->
            <div id="bloqueAsistente" class="d-none border-top pt-3" style="display: none;">
              <div class="d-flex justify-content-center gap-3">
                <label for="fechaNac" class="form-label text-end" style="width: 90px">Nacimiento:</label>
                <input type="date" id="fechaNac" name="fechaNac"
                       class="form-control form-control-sm" style="max-width: 200px"
                       value="${param.fechaNac}" />
              </div>

              <div class="d-flex justify-content-center gap-3 mt-3">
	            <label for="instituciones" class="form-label" style="width: 90px">Instituciones: </label>
	            <select id="instituciones" name="Instituciones" class="form-select form-select-sm" style="max-width: 240px" required>
	              <option value="" selected>
	                -- No pertenece a institucion --
	              </option>
	              <% if (listaNombresInstitucion != null) { %>
		              <% for(String nombreInstitucion: listaNombresInstitucion) { %>
			              <option value="<%=nombreInstitucion%>"><%=nombreInstitucion%></option>
			          <% } %>
			     <% } %> 
	            </select>
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
    <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

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
       
      //chkInstitucion.addEventListener("change", function() {
      //  filaInstitucion.classList.toggle("d-none", !chkInstitucion.checked);
      //});
    </script>
  </body>
</html>