// Estructura de datos: eventos > ediciones > tipos de registro
const eventos = [
  {
    nombre: "Maratón Montevideo",
    ediciones: [
      {
        nombre: "Maratón 2025",
        tiposRegistro: [
          {
            nombre: "Corredor 42K",
            descripcion: "Inscripción a maratón completa",
            costo: "$1.200",
            cuposMaximos: 499,
            cuposDisponibles: 120
          },
          {
            nombre: "Corredor 21K",
            descripcion: "Inscripción a la media matatón",
            costo: "$800",
            cuposMaximos: 700,
            cuposDisponibles: 600
          },
		  {
              nombre: "Corredor 10K",
              descripcion: "Inscripción a la carrera 10K",
              costo: "$500",
              cuposMaximos: 1000,
              cuposDisponibles: 600
            }
        ]
      },
      {
        nombre: "Maratón 2024",
        tiposRegistro: [
          {
            nombre: "Corredor 42K",
            descripcion: "Inscripción a la maratón completa",
            costo: "$1000",
            cuposMaximos: 300,
            cuposDisponibles: 200
          },
		  {
              nombre: "Corredor 21K",
              descripcion: "Inscripción a media maratón",
              costo: "$500",
              cuposMaximos: 500,
              cuposDisponibles: 200
            }
        ]
      }
    ]
  },
  {
    nombre: "Montevideo Comics",
    ediciones: [
      {
        nombre: "Montevideo Comics 2025",
        tiposRegistro: [
          {
            nombre: "Cosplayer",
            descripcion: "Entrada especial con acreditación para concurso cosplay",
            costo: "$300",
            cuposMaximos: 50,
            cuposDisponibles: 8
          },
          {
            nombre: "General",
            descripcion: "Entrada para los 4 días de Montevideo Comics",
            costo: "$600",
            cuposMaximos: 1500,
            cuposDisponibles: 195
          }
        ]
      },
      {
        nombre: "Montevideo Comics 2024",
        tiposRegistro: [
			
        ]
      }
    ]
  }
];

// Elementos DOM
const eventoSelect = document.getElementById("dropdown-evento");
const edicionSelect = document.getElementById("dropdown-edicion");
const tipoRegistroSelect = document.getElementById("dropdown-tipoRegistro");

// Campos de información
const descripcionDiv = document.querySelectorAll(".form-control-plaintext")[0];
const costoDiv = document.querySelectorAll(".form-control-plaintext")[1];
const cuposMaximosDiv = document.querySelectorAll(".form-control-plaintext")[2];
const cuposDisponiblesDiv = document.querySelectorAll(".form-control-plaintext")[3];

// Cargar eventos en el dropdown
function cargarEventos() {
  eventoSelect.innerHTML = "";
  eventos.forEach((evento, idx) => {
    const opt = document.createElement("option");
    opt.value = idx;
    opt.textContent = evento.nombre;
    eventoSelect.appendChild(opt);
  });
}

// Cargar ediciones según evento seleccionado
function cargarEdiciones(eventoIdx) {
  edicionSelect.innerHTML = "";
  eventos[eventoIdx].ediciones.forEach((edicion, idx) => {
    const opt = document.createElement("option");
    opt.value = idx;
    opt.textContent = edicion.nombre;
    edicionSelect.appendChild(opt);
  });
}

// Cargar tipos de registro según edición seleccionada
function cargarTiposRegistro(eventoIdx, edicionIdx) {
  tipoRegistroSelect.innerHTML = "";
  eventos[eventoIdx].ediciones[edicionIdx].tiposRegistro.forEach((tipo, idx) => {
    const opt = document.createElement("option");
    opt.value = idx;
    opt.textContent = tipo.nombre;
    tipoRegistroSelect.appendChild(opt);
  });
}

// Mostrar error
function mostrarError(mensaje) {
    // Elimina mensaje previo si existe
    let error = tipoRegistroSelect.parentNode.querySelector(".text-danger.error");
    if (error) error.remove();

    // Agrega nuevo mensaje de error
    const errorMsg = document.createElement("small");
    errorMsg.className = "text-danger error";
    errorMsg.textContent = mensaje;
	tipoRegistroSelect.parentNode.appendChild(errorMsg);
}

// Función para limpiar errores
function limpiarErrores() {
	document.querySelectorAll(".text-danger.error").forEach(e => e.remove());
}

// Mostrar datos del tipo de registro seleccionado
function mostrarDatosTipoRegistro(eventoIdx, edicionIdx, tipoIdx) {
	limpiarErrores();
	if (eventoSelect.value !== "" && edicionSelect.value !== "" && tipoRegistroSelect.value !== "") {
		const tipo = eventos[eventoIdx].ediciones[edicionIdx].tiposRegistro[tipoIdx];
		descripcionDiv.textContent = tipo.descripcion;
		costoDiv.textContent = tipo.costo;
		cuposMaximosDiv.textContent = tipo.cuposMaximos;
		cuposDisponiblesDiv.textContent = tipo.cuposDisponibles;	
	} else {
		descripcionDiv.textContent = "-";
		costoDiv.textContent = "-";
		cuposMaximosDiv.textContent = "-";
		cuposDisponiblesDiv.textContent = "-";
		mostrarError("Debe seleccionar todos los campos");
	}
}

// Listeners para cambios en selects
eventoSelect.addEventListener("change", () => {
  const eventoIdx = parseInt(eventoSelect.value, 10);
  cargarEdiciones(eventoIdx);
  cargarTiposRegistro(eventoIdx, 0);
  mostrarDatosTipoRegistro(eventoIdx, 0, 0);
});

edicionSelect.addEventListener("change", () => {
  const eventoIdx = parseInt(eventoSelect.value, 10);
  const edicionIdx = parseInt(edicionSelect.value, 10);
  cargarTiposRegistro(eventoIdx, edicionIdx);
  mostrarDatosTipoRegistro(eventoIdx, edicionIdx, 0);
});

tipoRegistroSelect.addEventListener("change", () => {
  const eventoIdx = parseInt(eventoSelect.value, 10);
  const edicionIdx = parseInt(edicionSelect.value, 10);
  const tipoIdx = parseInt(tipoRegistroSelect.value, 10);
  mostrarDatosTipoRegistro(eventoIdx, edicionIdx, tipoIdx);
});

// Inicialización al cargar la página
document.addEventListener("DOMContentLoaded", () => {
  cargarEventos();
  eventoSelect.value = "0"; // Primer evento por defecto
  cargarEdiciones(0);
  edicionSelect.value = "0"; // Primera edición por defecto
  cargarTiposRegistro(0, 0);
  tipoRegistroSelect.value = "0"; // Primer tipo de registro por defecto
  mostrarDatosTipoRegistro(0, 0, 0);
});