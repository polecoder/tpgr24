<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
  	<title>Eventos UY - Consulta Edición</title>
  	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="d-flex flex-column min-vh-100">
	<%@ page import = "datatypes.DTEdicionEvento" %>
	<%@ page import = "datatypes.DTDetalleRegistro" %>
	<%@ page import = "java.util.Set" %>
	<%@ page import = "java.text.SimpleDateFormat" %>

	<div class="container flex-grow-1">
	    <div class="mx-auto" style="max-width: 780px">
	      <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">
	        Consulta de edición de evento
	      </h1>
	    </div>
	
	    <main class="mx-auto p-4 bg-light border-top border-2 border-primary rounded" style="max-width: 500px">
	      <form class="d-flex flex-column gap-4">
	        <div id="info-edicion" class="mt-2 p-3 border rounded bg-white d-block mx-auto" style="min-width: 450px">
	          <!-- Título + Imagen en línea -->
	          <div class="d-flex align-items-center justify-content-between">
	          	<% String nombreEdicion = (String) request.getAttribute("nombre_edicion"); %>
	            <a href="TODO: agregar JSP a consulta evento" class="mb-3 fs-4 text-center w-100"> <%= nombreEdicion %> </a>
	          </div>
			  <% DTEdicionEvento dtEdicion = (DTEdicionEvento) request.getAttribute("detalle_edicion"); %>
			  <% SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy"); %>
	          <!-- Datos de la edición -->
	          <p><strong>Organizador:</strong> <%= dtEdicion.getOrganizadorNickname() %></p>
	          <p><strong>Sigla:</strong> <%= dtEdicion.getSigla() %> </p>
	          <p><strong>País:</strong> <%= dtEdicion.getPais() %> </p>
	          <p><strong>Ciudad:</strong> <%= dtEdicion.getCiudad() %></p>
	          <p><strong>Fecha de inicio:</strong> <%= dtEdicion.getFechaInicio() %> </p>
	          <p><strong>Fecha de fin:</strong> <%= dtEdicion.getFechaFin() %> </p>
	          <p><strong>Fecha de alta:</strong> <%= dtEdicion.getFechaAlta() %> </p>
	          <div class="mt-3">
	            <h6 class="fw-bold m-0">Tipos de registro:</h6>
	            <% Set<String> setTiposRegistro = (Set<String>) request.getAttribute("lista_tipos_registro"); %>
	            <% if (!setTiposRegistro.isEmpty()) { %>
		            <table class="table table-sm table-bordered mt-2 mb-3">
		              <thead class="table-light">
		                <tr>
		                  <th scope="col">Nombre del tipo de registro</th>
		                </tr>
		              </thead>
		              <tbody>
		              <% for (String tipoRegistro : setTiposRegistro) { %>
		                <tr>
		                  <td> <a href="TODO: agregar JSP a consulta tipo registro" class="link-primary link-underline"> <%= tipoRegistro %> </a> </td>
		                </tr>
		              <% } %>
		              </tbody>
		            </table>
		         <% } else { %>
		         	<p class="text-secondary ms-3 mb-2">No tiene tipos de registro.</p>
		         <% } %>
	          </div>
	          
	
	           <!-- Patrocinadores -->
	          <div class="mt-3">
		            <h6 class="fw-bold m-0">Patrocinadores de la edición:</h6>
		            <% Set<String> setPatrocinios = (Set<String>) request.getAttribute("lista_patrocinios"); %>
		            <% if (!setPatrocinios.isEmpty()) { %>
			            <table class="table table-sm table-bordered mt-2 mb-3">
			              <thead class="table-light">
			                <tr>
			                  <th scope="col">Nombre del patrocinador</th>
			                </tr>
			              </thead>
			              <tbody>
			              <% for (String patrocinador : setPatrocinios) { %>
			                <tr>
			                  <td> <%= patrocinador %> </td>
			                </tr>
			              <% } %>
			              </tbody>
			            </table>
			         <% } else { %>
			         	<p class="text-secondary ms-3 mb-2">No tiene patrocinadores.</p>
			         <% } %>
		          </div>
	          
	           <!-- Registros de la edición (organizador) -->
	          <% Boolean esOrganizador = (Boolean) request.getAttribute("es_organizador"); %>
	          <% Boolean esAsistente = (Boolean) request.getAttribute("es_asistente"); %>
	          <% if (Boolean.TRUE.equals(esOrganizador)) { %>
		          <div class="mt-3">
		            <h6 class="fw-bold m-0">Registros de la edición:</h6>
		            <% Set<DTDetalleRegistro> setRegistros = (Set<DTDetalleRegistro>) request.getAttribute("lista_registros"); %>
		            <% if (!setRegistros.isEmpty()) { %>
			            <table class="table table-sm table-bordered mt-2 mb-3">
			              <thead class="table-light">
			                <tr>
			                  <th scope="col">Usuario</th>
			                  <th scope="col">Tipo</th>
			                  <th scope="col">Fecha</th>
			                  <th scope="col">Costo</th>
			                </tr>
			              </thead>
			              <tbody>
			              <% for (DTDetalleRegistro registro : setRegistros) { %>
			                <tr>
			                  <td><a href="TODO: agregar JSP a consulta usuario" class="link-primary link-underline"> <%= registro.getNicknameAsistente() %> </a></td>
			                  <td><a href="TODO: agregar JSP  a consulta tipo registro" class="link-primary link-underline"> <%= registro.getNombreTipo() %> </a></td>
			                  <td> <%= registro.getFecha() %> </td>
			                  <td> <%= registro.getCosto() %> </td>
			                </tr>
			              <% } %>
			              </tbody>
			            </table>
			         <% } else { %>
			         	<p class="text-secondary ms-3 mb-2">No tiene registros.</p>
			         <% } %>
		          </div>
		       <% } else if (Boolean.TRUE.equals(esAsistente)) { %>
		       	  <% DTDetalleRegistro dtDetalleRegistro = (DTDetalleRegistro) request.getAttribute("detalle_registro"); %>
		       	  <div class="mt-3">
			        <h6 class="fw-bold m-0">Tu registro en esta edición:</h6>
			        <table class="table table-sm table-bordered mt-2 mb-3">
			            <thead class="table-light">
			            <tr>
			                <th scope="col">Tipo</th>
			                <th scope="col">Fecha</th>
			                <th scope="col">Costo</th>
			            </tr>
			            </thead>
			            <tbody>
			            <tr>
			                <td><a href="TODO: agregar JSP a consulta tipo registro" class="link-primary link-underline"> <% dtDetalleRegistro.getNombreTipo(); %> </a></td>
			                <td> <% dtDetalleRegistro.getFecha(); %> </td>
			                <td> <% dtDetalleRegistro.getCosto(); %> </td>
			            </tr>
			            </tbody>
			        </table>
			      </div>
		       <% } %>
				<!--
	          <img src="../images/IMG-EDEV03.jpeg" alt="Maratón de Montevideo 2024" class="img-fluid rounded w-100"
	            style="height: auto; max-width: 100%; object-fit: cover;" />
	             -->
	        </div>
	
	        <div class="d-flex justify-content-center gap-3">
	            <a href="TODO: agregar el JSP a alta patrocinio" class="btn btn-primary btn-sm px-3"
	              >Alta patrocinio</a>
	            <a href="TODO: agregar el JSP a alta patrocinio" class="btn btn-primary btn-sm px-3"
	              >Alta tipo registro</a>
	        </div>
	
	      </form>
	    </main>
  </div>


  <!-- Footer fuera del container -->
  <footer class="bg-primary text-white d-flex justify-content-between align-items-center p-2 p-sm-3 mt-auto">
    <p class="fw-bold m-0">TPROG 2025</p>
    <p class="m-0">Grupo 24</p>
  </footer>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="../js/consulta-edicion.js"></script>
  
</body>
</html>