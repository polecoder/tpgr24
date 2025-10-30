<%@ page
  contentType="text/html"
  pageEncoding="UTF-8"
%>
<%@ page import="com.eventosuy.servidor.*, java.text.SimpleDateFormat, java.util.Base64, java.util.Locale, java.util.Date"%>
<%
String cp = request.getContextPath();
DTEvento datosEvento = (DTEvento) request.getAttribute("datosEvento");
String srcImagenEvento = datosEvento.getImagen() != null
		? "data:image/png;base64," + Base64.getEncoder().encodeToString(datosEvento.getImagen())
		: cp + "/images/sin-imagen.svg";
DTEdicionEvento datosEdicion = (DTEdicionEvento) request.getAttribute("datosEdicion");
String srcImagenEdicion = datosEdicion.getImagen() != null
		? "data:image/png;base64," + Base64.getEncoder().encodeToString(datosEdicion.getImagen())
		: cp + "/images/sin-imagen.svg";
String codigoPatrocinio = (String) request.getAttribute("codigoPatrocinio");
DTTipoRegistro dtTipoRegistro = (DTTipoRegistro) request.getAttribute("dtTipoRegistro");
DTDetallePatrocinio dtPatrocinio = (DTDetallePatrocinio) request.getAttribute("dtPatrocinio");
// formatear fecha con mayúsculas
SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy");
Date dateFechaAlta = dtPatrocinio.getFechaAlta().toGregorianCalendar().getTime();
String fechaAlta = sdf.format(dateFechaAlta).substring(0, 1).toUpperCase()
		+ sdf.format(dateFechaAlta).substring(1);
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="/WEB-INF/views/partials/head.jsp" />
<link
  rel="stylesheet"
  href="<%=cp%>/css/consulta-patrocinio.css"
/>
</head>
<body class="d-flex flex-column min-vh-100">
  <jsp:include page="/WEB-INF/views/partials/header.jsp" />

  <div class="container flex-grow-1">
    <div class="header d-flex gap-3 mt-5 p-3 justify-content-center border border-1 border-primary">
      <div class="institucion d-flex gap-1 align-items-center">
        <h1
          class="py-4 fs-2 fs-sm-3"
          style="max-width: 500px"
        >
          Edición patrocinada por: <span class="text-primary text-uppercase d-block fs-1 fw-bold"><%=dtPatrocinio.getNombreInstitucion()%></span>
        </h1>
        <%-- TODO: Falta implementar las imagenes de Institución --%>
        <img
          src="<%=cp%>/images/anonimo.svg"
          class="imagen p-2"
        />
      </div>
    </div>
    <main
      class="datos p-5 d-flex justify-content-between border border-1 border-primary mt-2"
      style="height: fit-content"
    >
      <section>
        <div class="campo">
          <h3 class="titulo text-primary">Patrocinando desde:</h3>
          <p><%=fechaAlta%></p>
        </div>
        <div class="campo">
          <h3 class="titulo text-primary">Ha aportado:</h3>
          <p>
            $<%=dtPatrocinio.getAporte()%></p>
        </div>
        <div class="campo">
          <h3 class="titulo text-primary">Nivel de patrocinio:</h3>
          <p><%=dtPatrocinio.getNivel()%></p>
        </div>
        <div class="campo">
          <h3 class="titulo text-primary">Tipo de registro:</h3> 
          <a
            href="<%= cp %>/Ediciones?accion=ConsultaTipoRegistro&nombre_evento=<%=datosEvento.getNombre()%>&nombre_edicion=<%=datosEdicion.getNombre()%>&nombre_tipo_registro=<%=dtTipoRegistro.getNombre()%>"
            class="link d-flex gap-3 align-items-center mb-3"
          > <span><%=dtTipoRegistro.getNombre()%></span> <img
            src="<%=cp%>/images/link.svg"
            width="18"
            height="18"
          />
          </a>
        </div>
        <div class="campo">
          <h3 class="titulo text-primary">Total de cupos gratuitos:</h3>
          <p><%=dtPatrocinio.getCuposGratuitos()%></p>
        </div>
      </section>
      <aside class="extras d-flex gap-5">
        <div class="d-flex flex-column justify-content-between text-center">
          <h2 class="titulo text-primary pt-0 p-3">Evento asociado</h2>
          <img
            class="imagen mx-auto"
            src=<%=srcImagenEvento%>
          /> <a
            href="<%=cp%>/Eventos?accion=consulta&nombre=<%=datosEvento.getNombre()%>"
            class="subtitulo py-3 d-flex gap-3 align-items-center"
          > <span><%=datosEvento.getNombre()%></span> <img
            src="<%=cp%>/images/link.svg"
            width="24"
            height="24"
          />
          </a>
        </div>
        <div class="d-flex flex-column justify-content-between text-center">
          <h2 class="titulo text-primary pt-0 p-3">Edición asociada</h2>
          <img
            class="imagen mx-auto"
            src=<%=srcImagenEdicion%>
          /> <a
            href="<%=cp%>/Ediciones?accion=ConsultaEdicion&nombre_evento=<%=datosEvento.getNombre()%>&nombre_edicion=<%=datosEdicion.getNombre()%>"
            class="subtitulo py-3 d-flex gap-3 align-items-center"
          > <span><%=datosEdicion.getNombre()%></span> <img
            src="<%=cp%>/images/link.svg"
            width="24"
            height="24"
          />
          </a>
        </div>
      </aside>
    </main>
  </div>

  <jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>