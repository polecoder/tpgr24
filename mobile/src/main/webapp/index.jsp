<%@ page
  contentType="text/html"
  pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="/WEB-INF/views/partials/head.jsp" />
<style>

      main {
          flex: 1;
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          text-align: center;
      }

      .opciones {
          display: flex;
          flex-direction: column;
          gap: 1rem;
          width: 80%;
          max-width: 300px;
      }

      .boton {
          display: block;
          text-decoration: none;
          background-color: #007bff;
          color: white;
          text-align: center;
          padding: 1rem;
          border-radius: 12px;
          font-size: 1rem;
          font-weight: 500;
          transition: background-color 0.3s ease, transform 0.1s ease;
      }

      .boton:hover {
          background-color: #0056b3;
          transform: scale(1.02);
      }

      
  </style>
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
  <jsp:include page="./WEB-INF/views/partials/header.jsp"></jsp:include>
    <main>
       <h1 class="text-primary fw-bold mb-1 fs-2 text-center mb-5" style="line-height: 1.2;">Bienvenido!</h1>
        <div class="opciones">
            <a class="boton" href="<%=request.getContextPath()%>/Eventos?accion=presentacion">Consultar Edición</a>
            <a class="boton" href="#">Consultar Registro</a>
            <a class="boton" href="#">Confirmar Asistencia</a>
        </div>
    </main>
  <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>

