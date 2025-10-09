<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<% 
	// Testing session : Añadir / Borrar lo que se necesite
	session.setAttribute("usuarioNombre","Ana Torres");
	session.setAttribute("usuarioImagen", "./images/IMG-US01.jpg");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="WEB-INF/templates/head.jsp" />
</head>
<body>
    <jsp:include page="WEB-INF/templates/header.jsp" />
    <main>
        <h1>Bienvenido</h1>
    </main>
    <jsp:include page="WEB-INF/templates/footer.jsp" />
</body>
</html>