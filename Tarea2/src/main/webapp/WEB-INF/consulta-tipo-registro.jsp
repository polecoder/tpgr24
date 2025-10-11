<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>Eventos UY - Consulta Tipo Registro</title>
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
		rel="stylesheet" 
	/>
</head>
<body>
	<%@ page import = "datatypes.DTTipoRegistro" %>
	<div class="container flex-grow-1">
		<div class="d-flex flex-column align-items-center py-5 gap-5">
			<div class="d-flex align-items-center gap-3">
				<img src="../images/IMG-EDEV03.jpeg" alt="Imagen usuario"
					style="max-width: 128px; max-height: 128px; width: auto; height: auto;" />
				<div class="d-flex flex-column" style="line-height: 1">
					<h1 class="fs-2 fw-bold mb-1"> <%= request.getAttribute("nombre_edicion") %> </h1> 
					<a href="TODO: agregar JSP a consulta evento" class="fs-5 text-secondary text-decoration-underline"> <%= request.getAttribute("nombre_evento") %> </a>
				</div>
			</div>
			<main class="p-4 bg-light border-top border-2 border-primary rounded"
				style="max-width: 450px">
				<form class="d-flex flex-column">
					<label class="text-uppercase py-4 fs-4 fs-sm-3 fw-bold">
						Datos del tipo de registro 
					</label>
					<% 
						DTTipoRegistro dtTipoRegistro = (DTTipoRegistro) request.getAttribute("detalle_tipo_registro");
						String nombreTipoRegistro = dtTipoRegistro.nombre();
						String descripcion = dtTipoRegistro.descripcion();
						float costo = dtTipoRegistro.costo();
						int cuposMax = dtTipoRegistro.cuposMax();
					%>
					<div class="d-flex flex-row align-items-center gap-2 mb-3">
						<label for="nombre"
							class="form-label text-decoration-underline text-nowrap align-middle mb-0"
							style="width: auto">Nombre del tipo de registro:
						</label> 
						<label class="form-label m-0 text-nowrap align-middle"
							style="width: auto"> <%= nombreTipoRegistro %>
						</label>
					</div>

					<div class="d-flex flex-row align-items-center gap-2 mb-3">
						<label for="descripcion"
							class="form-label text-decoration-underline text-nowrap align-middle mb-0"
							style="width: auto">Descripción:
						</label>
						<label
							class="form-label m-0 align-middle" 
							style="width: auto"> <%= descripcion %>
						</label>
					</div>

					<div class="d-flex flex-row align-items-center gap-2 mb-3">
						<label for="costo"
							class="form-label text-decoration-underline text-nowrap align-middle mb-0"
							style="width: auto">Costo:
						</label> 
						<label
							class="form-label m-0 text-nowrap align-middle"
							style="width: auto"> <%= costo %>
						</label>
					</div>

					<div class="d-flex flex-row align-items-center gap-2 mb-3">
						<label for="cupos"
							class="form-label text-decoration-underline text-nowrap align-middle mb-0"
							style="width: auto">Cupos:
						</label> 
						<label
							class="form-label m-0 text-nowrap align-middle"
							style="width: auto"> <%= cuposMax %>
						</label>
					</div>

					<div class="d-flex justify-content-center gap-3">
						<a href="TODO: agregar JSP a consulta edicion"
							class="btn btn-primary btn-sm px-3" id="btnEdicion"> Ver edición 
						</a>
					</div>
				</form>
			</main>
		</div>
	</div>
</body>
</html>