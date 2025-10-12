<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/WEB-INF/templates/head.jsp" />
</head>
<body>
	<jsp:include page="/WEB-INF/templates/header.jsp" />
	<%@ page import = "datatypes.DTTipoRegistro" %>
	<div class="container flex-grow-1">
		<div class="d-flex flex-column align-items-center py-5 gap-5">
			<div class="d-flex align-items-center gap-3">
				<img src="../images/IMG-EDEV03.jpeg" alt="Imagen usuario"
					style="max-width: 128px; max-height: 128px; width: auto; height: auto;" />
				<div class="d-flex flex-column" style="line-height: 1">
					<% String nombreEvento = request.getParameter("nombre_evento"); %>
					<% String nombreEdicion = request.getParameter("nombre_edicion"); %>
					<h1 class="fs-2 fw-bold mb-1"> <%=nombreEdicion%> </h1> 
					<a href="${pageContext.request.contextPath}/Eventos?action=consulta&nombre=<%=nombreEvento%>" class="fs-5 text-secondary text-decoration-underline"> <%=nombreEvento%> </a>
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
						<a href="${pageContext.request.contextPath}/Ediciones?caso_de_uso=ConsultaEdicion&nombre_evento=<%=nombreEvento%>&nombre_edicion=<%=nombreEdicion%>"
							class="btn btn-primary btn-sm px-3" id="btnEdicion"> Ver edición 
						</a>
					</div>
				</form>
			</main>
		</div>
	</div>
	<jsp:include page="/WEB-INF/templates/footer.jsp" />
</body>
</html>