<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%
  String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
  <head>
  <jsp:include page="../templates/head.jsp"/>
  </head>
  <body class="d-flex flex-column min-vh-100">
    <div class="container flex-grow-1">
      <div class="mx-auto" style="max-width: 768px">
        <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">
          eventos.uy
        </h1>
      </div>
      <main
        class="mx-auto p-4 bg-light border-top border-2 border-primary rounded"
        style="max-width: 375px"
      >
        <form action="${pageContext.request.contextPath}/login" method="post" class="d-flex flex-column gap-4">
          <%
            if (error != null) {
          %>
            <div class="d-flex justify-content-center gap-3">
              <p class="p-2 rounded" style="margin: 0; background-color: #FFB3B3;"><span style="font-weight: 800; color: #FF6666;">Error</span>: <%= error %></p>
            </div>
          <%
            }
          %>
          <div class="d-flex justify-content-center gap-3">
            <label for="usuario" class="form-label text-end" style="width: 80px"
              >Usuario:</label
            >
            <input
              type="text"
              id="usuario"
              name="usuario"
              class="form-control form-control-sm"
              style="max-width: 200px"
            />
          </div>

          <div class="d-flex justify-content-center gap-3">
            <label
              for="contrasena"
              class="form-label text-end"
              style="width: 80px"
              >Contraseña:</label
            >
            <input
              type="password"
              id="contrasena"
              name="contrasena"
              class="form-control form-control-sm"
              style="max-width: 200px"
            />
          </div>

          <div class="d-flex flex-column align-items-center justify-content-center gap-3">
            <button type="submit" class="btn btn-primary btn-sm px-3">
              Acceder
            </button>
            <p>¿No tiene una cuenta? <a href="#"
              >Registrarse</a></p>
          </div>
        </form>
      </main>
    </div>
	<jsp:include page="../templates/footer.jsp"/>
  </body>
</html>