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
<body>
	<jsp:include page="/WEB-INF/templates/header.jsp" />
	<div class="container mx-auto">
		<div class="mx-auto">
			<h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">Alta Edición de Evento</h1>
		</div>
		<main
			class="p-4 bg-light border-top border-2 border-primary rounded mx-auto"
			style="max-width: 700px">
			<% String nombreEvento = (String) request.getAttribute("nombre_evento"); %>
			<% String nombreEdicion = (String) request.getAttribute("nombre_edicion"); %>
			<form action="Ediciones?caso_de_uso=AltaEdicion&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>" method="post" enctype="multipart/form-data" class="d-flex flex-column gap-4 mx-auto" style="max-width: 500px">
				<div class="d-flex gap-3 align-items-center">
					<img class="col-4" src="" alt="Imagen evento"
						style="width: auto; height: auto; max-width: 100px; max-height: 100px;" />
					<h2 class="evento" class="fs-3 fw-bold">
						<a href="./consulta-edicion-organizador.html"> <%= nombreEvento %> </a>
					</h2>
				</div>

				<div class="d-flex mb-2">
					<label for="nombreEdicion" class="form-label text-nowrap"
						style="width: 155px">Nombre de edición:<span class="text-danger">*</span> 
					</label> 
					<input type="text" id="nombreEdicion" name="nombre_edicion" class="form-control form-control-sm" style="width: 350px" required/>
				</div>

				<div class="d-flex mb-2">
					<label for="sigla" class="form-label" 
						style="width: 155px">Sigla:<span class="text-danger">*</span>
					</label> 
					<input type="text" id="sigla" name="sigla" class="form-control form-control-sm" style="max-width: 350px" required/>
				</div>

				<div class="d-flex mb-2">
					<label for="pais" class="form-label" 
						style="width: 155px">Pais:<span class="text-danger">*</span>
					</label> 
					<input type="text" id="pais" name="pais" class="form-control form-control-sm" style="max-width: 350px" required/>
				</div>

				<div class="d-flex mb-2">
					<label for="ciudad" class="form-label" 
						style="width: 155px">Ciudad:<span class="text-danger">*</span>
					</label> 
					<input type="text" id="ciudad" name="ciudad" class="form-control form-control-sm" style="max-width: 350px" required/>
				</div>

				<div class="d-flex align-items-center">
					<label for="fechaInicio" 
						style="height: fit-content; width: 155px">Fecha de inicio:<span class="text-danger">*</span>
					</label> 
					<input type="date" id="fechaInicio" name="fecha_inicio" class="form-control" style="max-width: 350px" required />
				</div>

				<div class="d-flex align-items-center">
					<label for="fechaFin" 
						style="height: fit-content; width: 155px">Fecha de fin:<span class="text-danger">*</span>
					</label> 
					<input type="date" id="fechaFin" name="fecha_fin" class="form-control" style="max-width: 350px" required />
				</div>

				<div class="d-flex mb-2">
					<label for="foto" class="form-label" style="width: 155px">Imágen: </label>
					<input type="file" id="foto" name="foto" class="form-control form-control-sm" style="max-width: 350px" accept="image/*" 
					/> 
					<img id="previewFoto" style="display: none; max-width: 100%; margin-top: 10px" />
				</div>

				<div class="d-flex justify-content-center gap-2 mb-2">
					<button type="submit" id="btnAceptar" class="btn btn-primary btn-sm px-3" style="max-width: 155px">
						Crear edición
					</button>
				</div>
			</form>
		</main>
	</div>
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>