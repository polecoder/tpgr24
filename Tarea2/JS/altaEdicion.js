document.addEventListener("DOMContentLoaded", function () {
  // Obtener el formulario y los campos
  const form = document.querySelector("form");
  const evento = document.getElementById("dropdown-evento");
  const nombreEdicion = document.getElementById("txt-edicion");
  const sigla = document.getElementById("txt-sigla");
  const pais = document.getElementById("txt-pais");
  const ciudad = document.getElementById("txt-ciudad");
  const fechaInicio = document.getElementById("input-fechaInicio");
  const fechaFin = document.getElementById("input-fechaFin");
  const btnAceptar = form.querySelector('button[type="button"]');

  // Función para mostrar mensaje de error
  function mostrarError(elemento, mensaje) {
    // Elimina mensaje previo si existe
    let error = elemento.parentNode.querySelector(".text-danger.error");
    if (error) error.remove();

    // Agrega nuevo mensaje de error
    const errorMsg = document.createElement("small");
    errorMsg.className = "text-danger error";
    errorMsg.textContent = mensaje;
    elemento.parentNode.appendChild(errorMsg);

    // Opcional: destaca el campo
    elemento.classList.add("is-invalid");
  }

  // Función para limpiar errores
  function limpiarErrores() {
    document.querySelectorAll(".text-danger.error").forEach(e => e.remove());
    form.querySelectorAll(".is-invalid").forEach(e => e.classList.remove("is-invalid"));
  }

  // Validación al hacer clic en "Aceptar"
  btnAceptar.addEventListener("click", function (e) {
    limpiarErrores();
    let valido = true;

    if (!evento.value.trim()) {
      mostrarError(evento, "Debe seleccionar un evento.");
      valido = false;
    }
    if (!nombreEdicion.value.trim()) {
      mostrarError(nombreEdicion, "El nombre de la edición es obligatorio.");
      valido = false;
    }
    if (!sigla.value.trim()) {
      mostrarError(sigla, "La sigla de la edición es obligatoria.");
      valido = false;
    }
    if (!pais.value.trim()) {
      mostrarError(pais, "El país es obligatorio.");
      valido = false;
    }
    if (!ciudad.value.trim()) {
      mostrarError(ciudad, "La ciudad es obligatoria.");
      valido = false;
    }
	if (fechaFin.value < fechaInicio.value) {
		mostrarError(fechaFin, "La fecha de fin debe ser posterior o igual a la de inicio");
		valido = false;
	}
	
	if (fechaInicio.value < Date()) {
		mostrarError(fechaInicio, "La fecha de inicio no puede ser anterior a la fecha actual");
		valido = false;
	}
    if (!fechaInicio.value.trim()) {
      mostrarError(fechaInicio, "La fecha de inicio es obligatoria.");
      valido = false;
    }
    if (!fechaFin.value.trim()) {
      mostrarError(fechaFin, "La fecha de fin es obligatoria.");
      valido = false;
    }
	
    // Si hay errores, no enviar el formulario
    if (!valido) {
      e.preventDefault();
      return;
    }

    // Aquí puedes agregar lógica para enviar el formulario si todo está correcto
    // Por ejemplo: form.submit();
    alert("Datos validados correctamente.");
  });
});