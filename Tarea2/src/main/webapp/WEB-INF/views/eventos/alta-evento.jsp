<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, datatypes.*"%>
<!DOCTYPE html>
<html lang="es">
    <!-- INCLUDE HEAD -->
    <jsp:include page="/WEB-INF/views/partials/head.jsp" />

    <body class="d-flex flex-column min-vh-100">

        <!-- INCLUDE HEADER -->
        <jsp:include page="/WEB-INF/views/partials/header.jsp" />

        <div class="container flex-grow-1" style="width: 900px">
            <div class="mx-auto">
                <h1 class="text-center text-uppercase py-4 fs-2 fs-sm-3">Alta de evento</h1>
            </div>

            <main class="p-4 bg-light border-top border-2 border-primary rounded mx-auto" style="max-width: 550px">

                <!-- ALERTA DE ERROR DEL SERVLET -->
                <%
                    String error = (String) request.getAttribute("error");
                    if (error != null) {
                %>
                    <div class="alert alert-danger" role="alert"><%= error %></div>
                <%
                    }
                %>

                <!-- FORMULARIO CORREGIDO: enctype agregado -->
                <form id="altaEventoForm" method="post" action="Eventos" enctype="multipart/form-data" class="d-flex flex-column gap-4" onsubmit="return validarFormulario()">
                    <input type="hidden" name="accion" value="confirmarAlta"/>

                    <!-- Nombre -->
                    <div class="d-flex justify-content-center gap-3 align-items-center">
                        <label for="nombre" class="form-label text-end" style="width: 155px">Nombre:</label>
                        <div>
                            <input type="text" id="nombre" name="nombre" class="form-control form-control-sm" style="width: 300px"
                                   value="<%= request.getParameter("nombre") != null ? request.getParameter("nombre") : "" %>"/>
                            <div id="errorNombre" class="text-danger"></div>
                        </div>
                    </div>

                    <!-- Descripción -->
                    <div class="d-flex justify-content-center gap-3 align-items-center">
                        <label for="descripcion" class="form-label text-end" style="width: 155px">Descripción:</label>
                        <div>
                            <textarea id="descripcion" name="descripcion" rows="3" class="form-control form-control-sm" style="width: 300px"><%= request.getParameter("descripcion") != null ? request.getParameter("descripcion") : "" %></textarea>
                            <div id="errorDescripcion" class="text-danger"></div>
                        </div>
                    </div>

                    <!-- Sigla -->
                    <div class="d-flex justify-content-center gap-3 align-items-center">
                        <label for="sigla" class="form-label text-end" style="width: 155px">Sigla:</label>
                        <div>
                            <input type="text" id="sigla" name="sigla" class="form-control form-control-sm" style="width: 300px"
                                   value="<%= request.getParameter("sigla") != null ? request.getParameter("sigla") : "" %>"/>
                            <div id="errorSigla" class="text-danger"></div>
                        </div>
                    </div>

                   <!-- Categorías -->
					 <div class="d-flex justify-content-center gap-3 align-items-center">
					    <label class="form-label text-end" style="width: 155px">Categorías:</label>
					    <div style="width: 300px">
					        <%
					            // Obtener lista de categorías desde el servlet
					            List<String> categorias = (List<String>) request.getAttribute("categorias");
					            if (categorias == null) categorias = new ArrayList<>();
					            String[] seleccionadas = request.getParameterValues("categorias");
					
					            for (String cat : categorias) {
					                boolean checked = false;
					                if (seleccionadas != null) {
					                    for (String sel : seleccionadas) {
					                        if (cat.equals(sel)) {
					                            checked = true;
					                            break;
					                        }
					                    }
					                }
					        %>
					            <div class="form-check">
					                <input class="form-check-input" type="checkbox" id="<%= cat %>" name="categorias" value="<%= cat %>"
					                       <%= checked ? "checked" : "" %> />
					                <label class="form-check-label" for="<%= cat %>"><%= cat %></label>
					            </div>
					        <%
					            }
					        %>
					        <div id="errorCategorias" class="text-danger"></div>
					    </div>
					</div>
                   

                    <!-- Imagen (opcional) -->
                    <div class="d-flex justify-content-center gap-3 align-items-center">
                        <label for="imagen" class="form-label text-end" style="width: 155px">Imagen:</label>
                        <input type="file" id="imagen" name="imagen" accept="image/*" class="form-control form-control-sm" style="width: 300px"/>
                    </div>

                    <!-- Botones -->
                    <div class="d-flex justify-content-center gap-3 align-items-center">
                        <button type="submit" class="btn btn-primary btn-sm px-3">Aceptar</button>
                        <button type="reset" class="btn btn-secondary btn-sm px-3">Reset</button>
                    </div>

                </form>
            </main>
        </div>

        <!-- INCLUDE FOOTER -->
        <jsp:include page="/WEB-INF/views/partials/footer.jsp" />

        <script>
            function validarFormulario() {
                let valido = true;

                // Limpiar errores anteriores
                document.getElementById("errorNombre").innerText = "";
                document.getElementById("errorDescripcion").innerText = "";
                document.getElementById("errorSigla").innerText = "";
                document.getElementById("errorCategorias").innerText = "";

                // Validar Nombre
                if (!document.getElementById("nombre").value.trim()) {
                    document.getElementById("errorNombre").innerText = "Campo obligatorio";
                    valido = false;
                }

                // Validar Descripción
                if (!document.getElementById("descripcion").value.trim()) {
                    document.getElementById("errorDescripcion").innerText = "Campo obligatorio";
                    valido = false;
                }

                // Validar Sigla
                if (!document.getElementById("sigla").value.trim()) {
                    document.getElementById("errorSigla").innerText = "Campo obligatorio";
                    valido = false;
                }

                // Validar Categorías
                let checkboxes = document.querySelectorAll('input[name="categorias"]:checked');
                if (checkboxes.length === 0) {
                    document.getElementById("errorCategorias").innerText = "Debe seleccionar al menos una categoría";
                    valido = false;
                }

                return valido;
            }
        </script>
    </body>
</html>

