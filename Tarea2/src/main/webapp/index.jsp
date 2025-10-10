<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="datatypes.*" %>
<%@ page import="java.util.Date" %>

<% 
	// Testing session : Añadir / Borrar lo que se necesite
		session.setAttribute("usuarioNombre","Ana Torres");
		session.setAttribute("usuarioImagen", "./images/IMG-US01.jpg");
		session.setAttribute("tipoUsuario", "organizador");
	// Fin Testing session
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="WEB-INF/templates/head.jsp" />
</head>
<body>
    <jsp:include page="WEB-INF/templates/header.jsp" />
    <main>
        <h1>altaInstitucion</h1>
        	<a href="/Tarea2/Institucion?action=altaInstitucion">Click Me!</a>
        <h1>altaPatrocinio</h1>
        	<a href="/Tarea2/Institucion?action=altaPatrocinio&nombreEdicion=TestEdicion&nombreEvento=TestEvento">Click Me!</a>
        <h1>consultaPatrocinio</h1>
        	<a href="/Tarea2/Institucion?action=consultaPatrocinio&codigoPatrocinio=TECHUDELAR">Click Me!</a>
    </main>
    <jsp:include page="WEB-INF/templates/footer.jsp" />
</body>
</html>