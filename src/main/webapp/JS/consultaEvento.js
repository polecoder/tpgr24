// Estructura de datos: eventos > ediciones
const eventos = [
	{
	    nombre: "Maratón de Montevideo",
	    sigla: "MM",
	    descripcion: "Maratones en Montevideo",
	    categorias: ["Deporte", "Atletismo"],
	    portada: "../imagenes/maraton-montevideo.png",
	    ediciones: [
	      "Maratón Enero 2025",
	      "Maratón Julio 2025"
	    ]
	  },
	  {
	    nombre: "Comics Montevideo",
	    sigla: "CM",
	    descripcion: "Convención de cómics y cultura pop",
	    categorias: ["Cultura", "Ocio", "Arte"],
	    portada: "../imagenes/comics-montevideo.png",
	    ediciones: [
	      "Expo Comics Abril 2025",
	      "Expo Comics Octubre 2025"
	    ]
	  },
	  {
	    nombre: "Festival Jazz",
	    sigla: "FJ",
	    descripcion: "Festival internacional de Jazz",
	    categorias: ["Música", "Cultura"],
	    portada: "../imagenes/festival-jazz.png",
	    ediciones: [
	      "Jazz Primavera 2025"
	    ]
	  }
];

// Elementos DOM
const eventoSelect = document.getElementById("dropdown-evento");
const siglaDiv = document.querySelectorAll(".form-control-plaintext")[0];
const descripcionDiv = document.querySelectorAll(".form-control-plaintext")[1];
const categoriasDiv = document.querySelectorAll(".form-control-plaintext")[2];
const edicionesDiv = document.querySelectorAll(".form-control-plaintext")[3];
const portadaImg = document.querySelector('img[alt="Portada Evento"]');

// Función para mostrar mensaje de error
function mostrarError(mensaje) {
// Elimina mensaje previo si existe
	let error = eventoSelect.parentNode.querySelector(".text-danger.error");
    if (error) error.remove();

    // Agrega nuevo mensaje de error
    const errorMsg = document.createElement("small");
    errorMsg.className = "text-danger error";
    errorMsg.textContent = mensaje;
    eventoSelect.parentNode.appendChild(errorMsg);

    // Opcional: destaca el campo
    eventoSelect.classList.add("is-invalid");
}

  // Función para limpiar errores
function limpiarErrores() {
    document.querySelectorAll(".text-danger.error").forEach(e => e.remove());
}

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

// Si no existe la imagen
portadaImg.onerror = function() {
  portadaImg.style.display = "none";
};


// Actualizar datos del evento seleccionado
function mostrarDatosEvento(eventoIdx) {
  limpiarErrores();
  if (eventoSelect.value !== "") {
	const evento = eventos[eventoIdx];
	  siglaDiv.textContent = evento.sigla;
	  descripcionDiv.textContent = evento.descripcion;
	  categoriasDiv.textContent = evento.categorias.join(", ");
	  // Ediciones: una debajo de la otra
	  edicionesDiv.innerHTML = evento.ediciones.map(e => 
	    `<a href="consultaEdicion.html?edicion=${encodeURIComponent(e)}" 
	        class="d-block link-primary mb-1" style="cursor:pointer;">
	        ${e}
	    </a>`
	  ).join("");
	  portadaImg.src = evento.portada;
	  portadaImg.style.display = "block";
	  portadaImg.alt = `Portada ${evento.nombre}`;
  } else {
	mostrarError("Debe seleccionar un evento");
  }
}

// Listener para cambio en select
eventoSelect.addEventListener("change", () => {
  const eventoIdx = parseInt(eventoSelect.value, 10);
  mostrarDatosEvento(eventoIdx);
});

// Inicializar al cargar la página
document.addEventListener("DOMContentLoaded", () => {
  cargarEventos();
  eventoSelect.value = "0"; // Primer evento por defecto
  mostrarDatosEvento(0);
});