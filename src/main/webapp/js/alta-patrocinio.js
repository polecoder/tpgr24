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
 const eventoSelect= document.getElementById("evento")
 const edicionSelect = document.getElementById("edicion");
 const tipoRegistroSelect = document.getElementById("tipoRegistro");
 const aporteInput = document.getElementById("aporte");
 const cuposInput = document.getElementById("cupos");
 const codigoInput = document.getElementById("codigo");
 const nivelPatrocinioSelect = document.getElementById("nivelPatrocinio");
 const institucionesSelect = document.getElementById("instituciones");
 const btnAceptar = document.getElementById("btnAceptar");



//esta fuincion elige en base al evento que ediciones mostrar y las suma al select
eventoSelect.addEventListener("change", function () {
  const seleccionado = this.value;
  const filtradas = ediciones.filter(e => e.evento === seleccionado);

  edicionSelect.innerHTML = '<option value="">-- Selecciona una edición --</option>';
   
  filtradas.forEach(e => {
    const option = document.createElement("option");
	option.value = e.nombre; 
    option.textContent = e.nombre;
    edicionSelect.appendChild(option);
  });
  
});

edicionSelect.addEventListener("change", () => {
  if (!edicionSelect.value||edicionSelect.value==="") {
    tipoRegistroSelect.disabled = true;
	tipoRegistroSelect.innerHTML = '<option value="">-- Seleccionar un Tipo Registro --</option>';
  }
  else {
  tipoRegistroSelect.disabled = false;
  tipoRegistroSelect.innerHTML = '<option value="">-- Seleccionar un Tipo Registro --</option>';
  const option2 = document.createElement("option");
  option2.value =  "tipo1"; 
  option2.textContent = "tipo1";
  tipoRegistroSelect.appendChild(option2);
  }
  }); 
  
  btnAceptar.addEventListener("click", () => {
    // Lista de campos a validar
    const campos = [
      { input: eventoSelect, nombre: "Evento" },
      { input: edicionSelect, nombre: "Edición" },
      { input: tipoRegistroSelect, nombre: "Tipo de Registro" },
      { input: institucionesSelect, nombre: "Institución" },
      { input: nivelPatrocinioSelect, nombre: "Nivel de Patrocinio" },
      { input: aporteInput, nombre: "Aporte Económico" },
      { input: cuposInput, nombre: "Cupos Gratuitos" },
      { input: codigoInput, nombre: "Código Patrocinio" },
    ];

    let errores = [];

    // Limpiar estilos previos
    campos.forEach(c => c.input.style.borderColor = "");

    // Validar campos vacíos
    campos.forEach(c => {
      if (!c.input.value.trim()) {
        c.input.style.borderColor = "red";
        errores.push(`El campo ${c.nombre} es obligatorio.`);
      }
    });

	const aporte = parseFloat(aporteInput.value);
	if (!aporteInput.value || isNaN(aporte) || aporte <= 0) {
	  aporteInput.style.borderColor = "red";
	  errores.push("El Aporte Económico debe ser un número mayor a 0.");
	}

	// Validar cupos gratuitos
	const cupos = parseInt(cuposInput.value, 10);
	if (!cuposInput.value || isNaN(cupos) || cupos <= 0) {
	  cuposInput.style.borderColor = "red";
	  errores.push("Los Cupos Gratuitos deben ser un número entero mayor a 0.")
	}

	if (errores.length > 0) {
	    alert("Errores:\n" + errores.join("\n"));
	    return;
	 }

    // Si no hay errores, dar el alta y limpiar campos
    alert("Se dio de alta el patrocinio de forma exitosa!");

    eventoSelect.value = "";
    edicionSelect.innerHTML = '<option value="">-- Seleccionar una Edición --</option>';
    tipoRegistroSelect.innerHTML = '<option value="">-- Seleccionar un Tipo Registro --</option>';
    tipoRegistroSelect.disabled = true;
    institucionesSelect.value = "";
    nivelPatrocinioSelect.value = "";
    aporteInput.value = "";
    cuposInput.value = "";
    codigoInput.value = "";
  });

