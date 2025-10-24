<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>Eventos UY - Alta Edición</title>
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
		rel="stylesheet" 
	/>
</head>
<body class="d-flex flex-column min-vh-100">
	<jsp:include page="/WEB-INF/views/partials/header.jsp" />

	<div class="container mx-auto flex-grow-1">
		
        <% 
			String nombreEvento = (String) request.getParameter("nombre_evento"); 
			byte[] imagenBytes = (byte[]) request.getAttribute("imagen_edicion");
	        String base64Image = null;
	        if (imagenBytes != null && imagenBytes.length > 0) {
	            base64Image = java.util.Base64.getEncoder().encodeToString(imagenBytes);
	        }
		%>
		
		<main class="p-4 bg-light border-top border-2 border-primary rounded mx-auto mt-5" style="max-width: 900px">
			
            <%
                String error = (String) request.getAttribute("error_message");
                if (error != null) {
            %>
                <div class="alert alert-danger" role="alert"><%= error %></div>
            <%
                }
            %>
             <h2 class="text-uppercase fw-bold fs-3 text-center mb-3">
			      Alta Edicion de:
			    </h2>
            <div class="border-bottom border-2 border-secondary-subtle pb-3 mb-4">
			  <div class="d-flex align-items-center justify-content-center gap-3">
			   
			   
			
			    <h2 class="text-uppercase fw-bold fs-5 text-primary mb-0">
			      <%= nombreEvento %>
			    </h2>
			  </div>
			</div>

			<form action="${pageContext.request.contextPath}/Ediciones?accion=AltaEdicion&nombre_evento=<%=nombreEvento%>" 
				  method="post" enctype="multipart/form-data" 
				  class="d-flex flex-column gap-4 mx-auto" style="max-width: 500px">
			
				<div class="d-flex mb-2">
					<label for="nombreEdicion" class="form-label fw-semibold"
						style="width: 155px">Nombre de edición:<span class="text-danger">*</span> 
					</label> 
					<input type="text" id="nombreEdicion" name="nombre_edicion" 
						   class="form-control form-control-sm" style="width: 350px" required/>
				</div>

				<div class="d-flex mb-2">
					<label for="sigla" class="form-label fw-semibold"
						style="width: 155px">Sigla:<span class="text-danger">*</span>
					</label> 
					<input type="text" id="sigla" name="sigla" 
						   class="form-control form-control-sm" style="max-width: 350px" required/>
				</div>

				<div class=" d-flex mb-2">
					<label for="pais" class="form-label fw-semibold" 
						style="width: 155px">País:<span class="text-danger">*</span>
					</label> 
					<input type="text" id="pais" name="pais" 
						   class="form-control form-control-sm" style="max-width: 350px" required/>
				</div>

				<div class="d-flex mb-2">
					<label for="ciudad" class="form-label fw-semibold" 
						style="width: 155px">Ciudad:<span class="text-danger">*</span>
					</label> 
					<input type="text" id="ciudad" name="ciudad" 
						   class="form-control form-control-sm" style="max-width: 350px" required/>
				</div>

				<div class="d-flex align-items-center mb-2">
					<label for="fechaInicio" class="form-label fw-semibold"
						style="height: fit-content; width: 155px">Fecha de inicio:<span class="text-danger">*</span>
					</label> 
					<input type="date" id="fechaInicio" name="fecha_inicio" 
						   class="form-control" style="max-width: 350px" required />
				</div>

				<div class="d-flex align-items-center mb-2">
					<label for="fechaFin" class="form-label fw-semibold"
						style="height: fit-content; width: 155px">Fecha de fin:<span class="text-danger">*</span>
					</label> 
					<input type="date" id="fechaFin" name="fecha_fin" 
						   class="form-control" style="max-width: 350px" required />
				</div>

				<div class="d-flex mb-2">
					<label for="foto" class="form-label fw-semibold " style="width: 155px">Imagen:</label>
					<input type="file" id="foto" name="foto" 
						   class="form-control form-control-sm" style="max-width: 350px" accept="image/*" /> 
					<img id="previewFoto" style="display: none; max-width: 100%; margin-top: 10px" />
				</div>

				<div class="d-flex justify-content-center gap-2 mb-2">
					<button type="submit" id="btnAceptar" 
							class="btn btn-primary btn-sm px-3 fs-5" style="max-width: 155px">
						Crear edición
					</button>
				</div>
			</form>
		</main>
	</div>

	<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
