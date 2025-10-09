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
<body>
    <jsp:include page="../templates/header.jsp" />
    <main>
        <h1>altaInstitucion</h1>
    </main>
    <jsp:include page="../templates/footer.jsp" />
</body>
</html>