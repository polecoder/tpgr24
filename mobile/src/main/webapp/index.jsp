<%@ page
  contentType="text/html"
  pageEncoding="UTF-8"
%>

<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="WEB-INF/views/partials/head.jsp" />
<link rel="stylesheet" href="./css/style.css"/>
</head>
<%-- TODO: actualizar index mínimo --%>
<body class="d-flex flex-column min-vh-100 bg-light">
  <jsp:include page="./WEB-INF/views/partials/header.jsp"></jsp:include>
  <div class="container flex-grow-1"></div>
  <jsp:include page="./WEB-INF/views/partials/footer.jsp" />
</body>
</html>
