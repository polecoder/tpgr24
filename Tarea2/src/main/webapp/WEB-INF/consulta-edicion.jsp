<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
	DTDetalleUsuario datosUsuario = (DTDetalleUsuario) session.getAttribute("usuarioLogeado");
%>
<!DOCTYPE html>
<html lang="es">
<head>
	<jsp:include page="/WEB-INF/templates/head.jsp" />
</head>
<body class="d-flex flex-column min-vh-100">
	<jsp:include page="/WEB-INF/templates/header.jsp" />
	<%@ page import = "datatypes.DTEdicionEvento" %>
	<%@ page import = "datatypes.DTDetalleRegistro" %>
	<%@ page import = "datatypes.DTDetalleUsuario" %>
	<%@ page import = "datatypes.ETipoUsuario" %>
	<%@ page import = "com.eventosuy.models.TipoUsuario" %>
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
	          	<% String nombreEdicion = request.getParameter("nombre_edicion"); %>
	          	<% String nombreEvento = request.getParameter("nombre_evento"); %>
	            <label class="mb-3 fs-4 text-center w-100"> <%= nombreEdicion %> </label>
	            <%
				byte[] imagenBytes = (byte[]) ((DTEdicionEvento) request.getAttribute("detalle_edicion")).getImagen();
	            String base64Image = null; // declarar antes del if
	            if (imagenBytes != null && imagenBytes.length > 0) {
	                base64Image = java.util.Base64.getEncoder().encodeToString(imagenBytes);
	            }
				%>
				<% if (base64Image != null) { %>
					<img src="data:image/png;base64,<%=base64Image%>" alt="Imagen evento"
						style="width: 128px" />
				<% } %>
	          </div>
			  <% DTEdicionEvento dtEdicion = (DTEdicionEvento) request.getAttribute("detalle_edicion"); %>
			  <% SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); %>
	          <!-- Datos de la edición -->
	          <p><strong>Organizador:</strong> <%= dtEdicion.getOrganizadorNickname() %></p>
	          <p><strong>Sigla:</strong> <%= dtEdicion.getSigla() %> </p>
	          <p><strong>País:</strong> <%= dtEdicion.getPais() %> </p>
	          <p><strong>Ciudad:</strong> <%= dtEdicion.getCiudad() %></p>
	          <p><strong>Fecha de inicio:</strong> <%= formatter.format(dtEdicion.getFechaInicio()) %> </p>
	          <p><strong>Fecha de fin:</strong> <%= formatter.format(dtEdicion.getFechaFin()) %> </p>
	          <p><strong>Fecha de alta:</strong> <%= formatter.format(dtEdicion.getFechaAlta()) %> </p>
	         
	          
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
		                  <td> <a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=tipoRegistro%>" 
		                  	class="link-primary link-underline"> <%= tipoRegistro %> </a> 
		                  </td>
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
			                	<td>
			                		<a href="${pageContext.request.contextPath}/Institucion?action=consultaPatrocinio&nombreEvento=<%=nombreEvento%>&nombreEdicion=<%=nombreEdicion%>&nombre_patrocinador=<%=patrocinador%>" class="link-primary link-underline"> <%= patrocinador %> </a>
			                	</td>
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
			                	<% String tipoRegistro = registro.getNombreTipo(); %>
			                  <td><a href="${pageContext.request.contextPath}/usuario?accion=consulta&nick=<%=registro.getNicknameAsistente()%>" class="link-primary link-underline"> <%= registro.getNicknameAsistente() %> </a></td>
			                  <td><a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=tipoRegistro%>" class="link-primary link-underline"> <%=tipoRegistro%> </a></td>
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
			            	<% String nombreTipoRegistro = dtDetalleRegistro.getNombreTipo(); %>
			                <td><a href="${pageContext.request.contextPath}/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>&nombre_tipo_registro=<%=nombreTipoRegistro%>" class="link-primary link-underline"> <%=nombreTipoRegistro%> </a></td>
			                <td> <% dtDetalleRegistro.getFecha(); %> </td>
			                <td> <% dtDetalleRegistro.getCosto(); %> </td>
			            </tr>
			            </tbody>
			        </table>
			      </div>
		       <% } %>
	        </div>
			<% if ( request.getSession().getAttribute("tipoUsuarioLogeado") == TipoUsuario.ORGANIZADOR) { %> 
		        <div class="d-flex justify-content-center gap-3">
		            <a href="${pageContext.request.contextPath}/Institucion?action=altaPatrocinio&nombreEdicion=<%=nombreEdicion%>&nombreEvento=<%=nombreEvento%>" class="btn btn-primary btn-sm px-3"
		              >Alta patrocinio</a>
		            <% if ( datosUsuario.getNickname() == dtEdicion.getOrganizadorNickname()) { %>  
			            <a href="${pageContext.request.contextPath}/Ediciones?accion=AltaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>" class="btn btn-primary btn-sm px-3"
			              >Alta tipo registro</a>
			        <% } %>
		        </div>
	        <% } else if ( request.getSession().getAttribute("tipoUsuarioLogeado") == TipoUsuario.ASISTENTE) { %>
	        	<div class="d-flex justify-content-center gap-3">
		            <a href="${pageContext.request.contextPath}/usuario?accion=registro-edicion&evento=<%=nombreEvento%>&edicion=<%=nombreEdicion%>" class="btn btn-primary btn-sm px-3"
		              >Registrarse a edicion</a>
	        	</div>
			<% } %>	
	      </form>
	    </main>
  </div>
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>