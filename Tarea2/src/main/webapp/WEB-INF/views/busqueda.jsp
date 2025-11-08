<%@ page
  contentType="text/html"
  pageEncoding="UTF-8"
%>
<%@ page import="java.util.*, com.eventosuy.servidor.*"%>
<%@ page import="com.eventosuy.models.TipoUsuario"%>

<%
List<DTEvento> eventosBusqueda = (List<DTEvento>) request.getAttribute("eventosBusqueda");
//List<DTEvento> eventosFinalizados = (List<DTEvento>) request.getAttribute("eventosFinalizados");

// Nuevo: parámetro de categoría seleccionada
String categoriaSeleccionada = request.getParameter("categoria");
if (categoriaSeleccionada != null && categoriaSeleccionada.equalsIgnoreCase("Todas")) {
	categoriaSeleccionada = null; // “Todas” actúa como sin filtro
}
%>

<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="../views/partials/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100 bg-light">
  <jsp:include page="../views/partials/header.jsp"></jsp:include>

  <!-- Contenido principal -->
	<div class="container flex-grow-1 mt-4">
		<h2 class="text-center text-uppercase pb-3 fs-3 text-success">Resultados de búsqueda</h2>
	
		<!-- Barra de busqueda -->
		<% 	String query = request.getParameter("query"); 
			if (query == null) { 
				query = (String) request.getAttribute("query"); 
			}
			String orden = request.getParameter("orden");
			if (orden == null) {
				orden = (String) request.getAttribute("orden");
			}
		%>
		
		<div class="d-flex align-items-center mb-2 px-3">
			<div class="flex-grow-1"></div> <!-- Agrego div a la izquierda para centrar la barra -->
			<form class="d-flex" role="search" id="searchForm" action="Busqueda" method="get">
				<% if (query == null) { %>
					<input class="form-control" type="search" id="query" name="query" style="width:500px; height:40px" placeholder="Busca eventos o ediciones...">
				<% } else { %>
					<input class="form-control" type="search" id="query" name="query" style="width:500px; height:40px" placeholder="Busca eventos o ediciones..."
						value="<%=query%>">
				<% } %>
				<button type="submit" class="btn btn-primary btn-sm px-3 fs-5 ms-2" style="height:40px">Buscar</button>
				<select class="form-select ms-3" name="orden" id="orderSelect" style="width:300px; height:40px">
			      <option value="default" <%= orden.equals("default") ? "selected" : "" %>>Orden por defecto</option>
			      <option value="alfabetico_ascendente" <%= orden.equals("alfabetico_ascendente") ? "selected" : "" %>>Orden alfabetico (A-Z)</option>
			      <option value="alfabetico_descendiente" <%= orden.equals("alfabetico_descendiente") ? "selected" : "" %>>Orden alfabetico (Z-A)</option>
	    		</select>
			</form>	
		</div>
		<script> // Cuando cambia el dropdown, se envía el form
		  document.getElementById('orderSelect').addEventListener('change', function() {
		  	document.getElementById('searchForm').submit();
		  });
		</script>
		
			
			
		<div class="row row-cols-1 row-cols-sm-2 row-cols-lg-3 row-cols-xl-4 g-4 justify-content-center">
			<%
		    if (eventosBusqueda != null && !eventosBusqueda.isEmpty()) {
		    	boolean hayEventos = false;
		    	for (DTEvento evento : eventosBusqueda) {
		   	%>
		   			<!-- Tarjeta evento -->
			      	<div class="col">
			        	<div class="card h-100 shadow-sm">
			        		<!-- Imagen -->
				        	<a href="Eventos?accion=consulta&nombre=<%=evento.getNombre()%>">
					            <img
					              src="<%=(evento.getImagen() != null && evento.getImagen().length > 0) ? "data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(evento.getImagen()) : request.getContextPath() + "/images/sin-imagen.svg"%>"
					              alt="<%=evento.getNombre()%>"
					              class="card-img-top"
					              style="object-fit: cover; aspect-ratio: 1/1"
					            />
					        </a>
					        <!-- Titulo -->
					        <div class="card-body text-center d-flex justify-content-center align-items-center">
					          <a
					            href="Eventos?accion=consulta&nombre=<%=evento.getNombre()%>"
					            class="card-title fs-6 text-decoration-none text-dark fw-semibold m-0" >
					            <%=evento.getNombre()%>
					          </a>
					        </div>
			        	</div>
			      </div>
		    <%  }
		    } else { %>
		      <p class="text-center text-muted">Ningún evento coincide con la búsqueda</p>
		    <%} %>
		</div>
  </div>

  <jsp:include page="../views/partials/footer.jsp" />
</body>
</html>
