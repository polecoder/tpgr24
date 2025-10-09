<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<% 
	// Testing session : Añadir / Borrar lo que se necesite
	session.setAttribute("usuarioNombre","Ana Torres");
	session.setAttribute("usuarioImagen", "./images/IMG-US01.jpg");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="../templates/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100">
    <jsp:include page="../templates/header.jsp" />
    <div class="container flex-grow-1">
      <div class="mx-auto" style="max-width: 768px">
        <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">
          Alta de institucion
        </h1>
      </div>
      <main
        class="p-4 bg-light border-top border-2 border-primary rounded mx-auto"
        style="max-width: 520px"
      >
        <div id="alert-area" class="mb-3"></div>
        <form
          id="altaInstitucionForm"
          method="post"
          action="../alta-institucion"
          enctype="multipart/form-data"
          novalidate
          class="d-flex flex-column gap-4"
        >
          <div class="d-flex justify-content-center gap-3">
            <label for="nombre" class="form-label text-end" style="width: 80px"
              >Nombre:</label
            >
            <input
              type="text"
              id="nombre"
              name="nombre"
              class="form-control form-control-sm"
              style="max-width: 300px"
            />
          </div>

          <div class="d-flex justify-content-center gap-3">
            <label
              for="descripcion"
              class="form-label text-end"
              style="width: 80px"
              >Descripcion:</label
            >
            <textarea
              id="descripcion"
              name="descripcion"
              rows="3"
              class="form-control form-control-sm"
              style="max-width: 300px"
              required
            >
            </textarea>
          </div>

          <div class="d-flex justify-content-center gap-3">
            <label
              for="sitioWeb"
              class="form-label text-end"
              style="width: 80px"
              >Sitio web:</label
            >
            <input
              type="url"
              id="sitioWeb"
              name="sitioWeb"
              placeholder="https://..."
              class="form-control form-control-sm"
              style="max-width: 300px"
              required
            />
          </div>

          <div class="d-flex justify-content-center gap-3">
            <label for="imagen" class="form-label text-end" style="width: 80px"
              >Imagen:</label
            >
            <input
              type="file"
              id="imagen"
              name="imagen"
              accept="image/*"
              class="form-control form-control-sm"
              style="max-width: 300px"
            />
          </div>

          <div class="d-flex justify-content-center gap-3">
            <button type="submit" class="btn btn-primary btn-sm px-3">
              Aceptar
            </button>
            <button type="reset" class="btn btn-primary btn-sm px-3">
              Reset
            </button>
          </div>
        </form>
      </main>
    </div>
    <jsp:include page="../templates/footer.jsp" />
</body>
</html>