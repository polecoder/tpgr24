<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, datatypes.*"%>

<% List<DTEdicionEvento> edicionesR = (List<DTEdicionEvento>) request.getAttribute("Ediciones"); %>

<!DOCTYPE html>
<html lang="es">
<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp" />

    <style>
        .eventos-container {
            max-width: 960px;
            margin: 30px auto; 
            padding: 0 20px;
        }
        
        .evento-card img {
		    width: 100%;
		    height: 160px; 
		    object-fit: cover; 
		    border-bottom: 1px solid #eee;
		}
		        
        .evento-card {
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            overflow: hidden;
            transition: transform 0.1s ease, box-shadow 0.2s ease;
        }

        .evento-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }

        .eventos-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            justify-content: center;
        }

        @media (min-width: 300px) {
    		.evento-card {
        		flex: 0 1 calc(50% - 15px);
    		}
		}
		
		@media (min-width: 768px) {
		    .evento-card {
		        flex: 0 1 calc(33.333% - 15px);
		    }
		}

    </style>
</head>
<body class="d-flex flex-column min-vh-100 bg-light">

<jsp:include page="/WEB-INF/views/partials/header.jsp"></jsp:include>

<div class="eventos-container">
	<div class="card shadow-sm mb-4 ">
    <h2 class="text-center text-primary fw-bold">Ediciones a las que esta registrado:</h2>
	</div>
    <% if (edicionesR != null && !edicionesR.isEmpty()) { %>
        <div class="eventos-grid">
            <% for (DTEdicionEvento e : edicionesR) { 
                byte[] imgBytes = e.getImagen();
                String base64Img = null;
                if (imgBytes != null && imgBytes.length > 0) {
                    base64Img = java.util.Base64.getEncoder().encodeToString(imgBytes);
                }
            %>
                <div class="evento-card">
                    <img src="<%= (base64Img != null) ? "data:image/jpeg;base64," + base64Img : request.getContextPath() + "/images/sin-imagen.svg" %>"
                         alt="Imagen de <%= e.getNombre() %>">
                    <div class="text-center p-2">
                        <a href="Registros?accion=Consulta&edicion=<%=e.getNombre()%>">
                           <%=e.getNombre()%>
                        </a>
                    </div>
                </div>
            <% } %>
        </div>
    <% } else { %>
        <p class="text-center text-muted">No estas registrado a ninguna edición.</p>
    <% } %>
</div>


<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>
