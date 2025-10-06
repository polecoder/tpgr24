

const edicionSelect = document.getElementById("ediciones");
const nombreInput = document.getElementById("Nombre");
const costoInput = document.getElementById("Costo");
const cuposInput = document.getElementById("Cupos");
const descripcionInput = document.getElementById("Descripcion");
const btnAceptar = document.getElementById("btnAceptar");




btnAceptar.addEventListener("click", () => {

	
	// Lista de campos a validar
   const campos = [
     { input: edicionSelect, nombre: "Edición" },
     { input: nombreInput, nombre: "nombre" },
     { input: costoInput, nombre: "costo" },
     { input: cuposInput, nombre: "cupos" },
     { input: descripcionInput, nombre: "Descripcion" },,
   ];

let errores = false;

   // Limpiar estilos previos
   campos.forEach(c => c.input.style.borderColor = "");

   // Validar campos vacíos
   campos.forEach(c => {
     if (!c.input.value.trim()) {
       c.input.style.borderColor = "red";
       errores=true;
     }
   });
if(errores)return;

const costo = parseFloat(costoInput.value);
if (!costoInput.value || isNaN(costo) ||costo <= 0) {
  aporteInput.style.borderColor = "red";
  alert("El costo debe ser un número mayor a 0.");
  errores = true;
  return;
}
// Validar cupos gratuitos
const cupos = parseInt(cuposInput.value, 10);
if (!cuposInput.value || isNaN(cupos) || cupos <= 0) {
  cuposInput.style.borderColor = "red";
  alert("La cantidad de cupos debe ser un número entero mayor a 0.");
  errores = true;
  return;
}

// Si no hay errores, dar el alta y limpiar campos
  alert("Se dio de alta el Tipo de Registro de forma exitosa!");

  // Limpiar valores de los campos
  edicionSelect.value = "";
  nombreInput.value = "";
  costoInput.value = "";
  cuposInput.value = "";
  descripcionInput.value = "";
});
