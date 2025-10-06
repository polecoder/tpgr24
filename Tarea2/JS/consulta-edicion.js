//datos hardocodeados 
const ediciones = [
  {
	    evento: "Maratón de Montevideo",
	    nombre: "Maratón de Montevideo 2025",
	    Sigla: "MARATON25",
	    fechaInicio: "2025-04-14",
	    fechaFin: "2025-04-14",
	    fechaAlta: "2024-12-01",
	    lugar: "Rambla Sur",
	    organizador: "Intendencia de Montevideo",
		fotoUrl:"../fotos/maratonMontevideo.png"
  },
  {
	    evento: "Montevideo Comics",
	    nombre: "Montevideo Comics 2025",
	    Sigla: "COMICS25",
	    fechaInicio: "2025-06-01",
	    fechaFin: "2025-06-02",
	    fechaAlta: "2025-01-15",
	    lugar: "Antel Arena",
	    organizador: "miseventos",
		fotoUrl:"../fotos/Comics.png"
  },
  	{
		evento: "Montevideo Rock",
	    nombre: "Montevideo Rock 2025",
	    sigla: "MONROCK25",
	    organizador: "Intendencia de Montevideo", // si US06 es el código del organizador
	    ciudad: "Montevideo",
	    pais: "Uruguay",
	    fechaInicio: "2025-11-20",
	    fechaFin: "2025-11-22",
	    fechaAlta: "2025-03-12",
		fotoUrl:"../fotos/montevideoRock.png"
	}
 ]

 window.addEventListener("DOMContentLoaded", () => {
   const params = new URLSearchParams(window.location.search);
   const eventoParam = params.get("evento");
   const edicionParam = params.get("edicion");

   if (eventoParam) {
     eventoSelect.value = eventoParam;

     // Llenar ediciones correspondientes
     const filtradas = ediciones.filter(e => e.evento === eventoParam);
     edicionSelect.innerHTML = '<option value="">-- Selecciona una edición --</option>';
     filtradas.forEach(e => {
       const option = document.createElement("option");
       option.value = e.nombre;
       option.textContent = e.nombre;
       edicionSelect.appendChild(option);
     });

     // Seleccionar la edición si viene en la URL
     if (edicionParam) {
       edicionSelect.value = edicionParam;

       // Mostrar info automáticamente
       const edicion = ediciones.find(e => e.nombre === edicionParam);
       if (edicion) {
         infoDiv.innerHTML = `
           <div class="d-flex align-items-center justify-content-between mb-3">
             <h5 class="m-0">${edicion.nombre}</h5>
            ${edicion.fotoUrl ? `<img src="${edicion.fotoUrl}" alt="Logo" style="max-height: 80px; border-radius: 8px;">` : ''}
           </div>
           <p class="mt-3"><strong>Organizador:</strong> ${edicion.organizador || "No disponible"}</p>
           <p><strong>Sigla:</strong> ${edicion.Sigla || "No disponible"}</p>
           <p><strong>País:</strong> ${edicion.pais || "Uruguay"}</p>
           <p><strong>Ciudad:</strong> ${edicion.ciudad || "Montevideo"}</p>
           <p><strong>Fecha de inicio:</strong> ${edicion.fechaInicio || "No disponible"}</p>
           <p><strong>Fecha de fin:</strong> ${edicion.fechaFin || "No disponible"}</p>
           <p><strong>Fecha de alta:</strong> ${edicion.fechaAlta || "No disponible"}</p>
           <p><strong>Tipos de Registros:</strong> ${edicion.tiposRegistros?.join(", ") || "No disponible"}</p>
           <p><strong>Patrocinadores:</strong> ${edicion.patrocinadores?.join(", ") || "No disponible"}</p>
         `;
         infoDiv.style.display = "block";
       }
     }
   }
 });


//guardo la info de los select
const eventoSelect = document.getElementById("evento");
const edicionSelect = document.getElementById("edicion");


//esta fuincion elige en base al evento que ediciones mostrar y las suma al select
eventoSelect.addEventListener("change", function () {
  const seleccionado = this.value;
  const filtradas = ediciones.filter(e => e.evento === seleccionado);

  edicionSelect.innerHTML = '<option value="">-- Selecciona una edición --</option>';

  infoDiv.style.display = "none";
  infoDiv.innerHTML = "";
   
  filtradas.forEach(e => {
    const option = document.createElement("option");
	option.value = e.nombre; 
    option.textContent = e.nombre;
    edicionSelect.appendChild(option);
  });
  
});

//aca es para buscar la info 
const btnCancelar = document.getElementById("btnCancelar");
const btnAceptar = document.getElementById("btnAceptar");
const infoDiv = document.getElementById("info-edicion");

btnAceptar.addEventListener("click", () => {
  const refSeleccionada = edicionSelect.value;
  const edicion = ediciones.find(e => e.nombre === refSeleccionada);

  if (!edicion) {
    infoDiv.style.display = "none";
    infoDiv.innerHTML = "";
    return;
  }

  // Simulamos más info (podés extender esto con datos reales)
  infoDiv.innerHTML = `
    <div class="d-flex align-items-center justify-content-between mb-3">
      <h5 class="m-0">${edicion.nombre}</h5>
       ${edicion.fotoUrl ? `<img src="${edicion.fotoUrl}" alt="Logo" style="max-height: 80px; border-radius: 8px;">` : ''}
    </div>

    <p class="mt-3"><strong>Organizador:</strong> ${edicion.organizador || "No disponible"}</p>
    <p><strong>Sigla:</strong> ${edicion.Sigla || "No disponible"}</p>
    <p><strong>País:</strong> ${edicion.pais || "Uruguay"}</p>
    <p><strong>Ciudad:</strong> ${edicion.ciudad || "Montevideo"}</p>
    <p><strong>Fecha de inicio:</strong> ${edicion.fechaInicio || "No disponible"}</p>
    <p><strong>Fecha de fin:</strong> ${edicion.fechaFin || "No disponible"}</p>
    <p><strong>Fecha de alta:</strong> ${edicion.fechaAlta || "No disponible"}</p>
    <p><strong>Tipos de Registros:</strong> ${edicion.tiposRegistros?.join(", ") || "No disponible"}</p>
    <p><strong>Patrocinadores:</strong> ${edicion.patrocinadores?.join(", ") || "No disponible"}</p>
  `;
  infoDiv.style.display = "block";
  
  btnAceptar.style.display = "none";
  btnCancelar.style.display = "inline-block";

  });
  
  btnCancelar.addEventListener("click", () => {
    // Limpiar selects
    eventoSelect.value = "";
    edicionSelect.innerHTML = '<option value="">-- Selecciona una edición --</option>';

    // Ocultar info
    infoDiv.style.display = "none";
    infoDiv.innerHTML = "";

    // Mostrar Aceptar nuevamente
    btnAceptar.style.display = "inline-block";
  });
