<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>Eventos UY - Alta Tipo Registro</title>
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
		rel="stylesheet" 
	/>
</head>
<body>
	<div class="container flex-grow-1">
		<div class="mx-auto" style="max-width: 768px">
			<h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">Alta de Tipo de Registro</h1>
		</div>

		<div class="d-flex justify-content-between gap-5 mx-auto"
			style="max-width: 1024px">
			<main
				class="mx-auto p-4 bg-light border-top border-2 border-primary rounded"
				style="max-width: 375px">
				<% String nombreEvento = (String) request.getAttribute("nombre_evento"); %>
				<% String nombreEdicion = (String) request.getAttribute("nombre_edicion"); %>
				<form action="Ediciones?caso_de_uso=AltaTipoRegistro&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>" method="post" enctype="multipart/form-data" class="d-flex flex-column gap-4 mx-auto" style="max-width: 500px">

					<div
						class="d-flex align-items-center gap-2 py-2 px-3 justify-content-center">
						<img src="../images/IMG-EDEV03.jpeg" alt="Imagen evento"
							style="width: 128px" /> <a
							href="./consulta-edicion-organizador.html" class="text-primary"> <%=nombreEdicion%> </a>
					</div>

					<div class="d-flex gap-2">
						<label for="nombre" class="form-label" style="width: 200px">
							Nombre:<span class="text-danger">*</span>
						</label> 
						<input type="text" id="nombre" 
							name="nombre" class="form-control form-control-sm" style="max-width: 300px" required
						/>
					</div>

					<div class="d-flex gap-2">
						<label for="descripcion" class="form-label" style="width: 200px">
							Descripción:<span class="text-danger">*</span>
						</label> 
						<input type="text" id="descripcion"
							name="descripcion" class="form-control form-control-sm" style="max-width: 300px" required
						/>
					</div>

					<div class="d-flex gap-2">
						<label for="costo" class="form-label" style="width: 200px">
							Costo:<span class="text-danger">*</span>
						</label> 
						<input type="number" id="costo"
							name="costo" class="form-control form-control-sm" style="max-width: 300px" required
						/>
					</div>

					<div class="d-flex gap-2">
						<label for="cuposMax" class="form-label" style="width: 200px">
							Cupos:<span class="text-danger">*</span>
						</label> 
						<input type="number" id="cuposMax"
							name="cupos_max" class="form-control form-control-sm" style="max-width: 300px" required
						/>
					</div>
					<div class="d-flex justify-content-center gap-3">
						<button type="submit" id="btnAceptar" class="btn btn-primary btn-sm px-3">
							Aceptar
						</button>
					</div>
				</form>
			</main>
		</div>
	</div>
</body>
</html>