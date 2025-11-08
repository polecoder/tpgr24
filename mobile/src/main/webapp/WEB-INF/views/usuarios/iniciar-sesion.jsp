<%@ page
  contentType="text/html"
  pageEncoding="UTF-8"
%>
<%
String error = (String) request.getAttribute("error");
String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="/WEB-INF/views/partials/head.jsp" />
<link
  rel="stylesheet"
  href="<%=cp%>/css/iniciar-sesion.css"
/>
</head>
<body class="d-flex flex-column min-vh-100">
  <jsp:include page="/WEB-INF/views/partials/header.jsp" />
  <div class="container flex-grow-1 px-4">
    <div
      class="mx-auto"
      style="max-width: 768px"
    >
      <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">eventos.uy</h1>
    </div>
    <main
      class="mx-auto py-4 px-3 bg-light border-top border-2 border-primary rounded"
      style="max-width: 300px"
    >
      <form
        action="${pageContext.request.contextPath}/login"
        method="post"
        class="d-flex flex-column gap-4"
      >
        <%
        if (error != null) {
        %>
        <div class="d-flex justify-content-center gap-3">
          <p
            class="p-2 rounded"
            style="margin: 0; background-color: #FFB3B3;"
          >
            <span style="font-weight: 800; color: #FF6666;">Error</span>:
            <%=error%></p>
        </div>
        <%
        }
        %>
        <div class="d-flex justify-content-center gap-3">
          <label
            for="usuario"
            class="form-label text-end"
            style="width: 80px"
          >Usuario:</label> <input
            type="text"
            id="usuario"
            name="usuario"
            class="form-control form-control-sm"
            style="max-width: 150px"
          />
        </div>

        <div class="d-flex justify-content-center gap-3">
          <label
            for="contrasena"
            class="form-label text-end"
            style="width: 80px"
          >Contraseña:</label> <input
            type="password"
            id="contrasena"
            name="contrasena"
            class="form-control form-control-sm"
            style="max-width: 150px"
          />
        </div>

        <div class="d-flex flex-column align-items-center justify-content-center gap-3">
          <button
            type="submit"
            class="btn btn-primary btn-sm px-3"
          >Acceder</button>
        </div>
      </form>
    </main>
  </div>
  <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>