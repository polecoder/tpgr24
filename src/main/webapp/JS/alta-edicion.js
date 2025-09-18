

const eventoSelec = document.getElementById("evento");
const nombreInput = document.getElementById("nombreEdicion");
const SiglaInput = document.getElementById("SiglaEdicion");
const CiudadInput = document.getElementById("ciudadEdicion");
const PaisInput = document.getElementById("PaisEdicion");
const fechaInput = document.getElementById("fechaInicio");
const fotoInput = document.getElementById("foto");
const btnAceptar = document.getElementById("btnAceptar");
const previewFoto = document.getElementById("previewFoto");




fotoInput.addEventListener("change", () => {
  const file = fotoInput.files[0]; // el archivo seleccionado
  if (file) {
    const reader = new FileReader();
    reader.onload = e => {
      previewFoto.src = e.target.result;
      previewFoto.style.display = "block"; // mostrar la imagen
    };
    reader.readAsDataURL(file);
  } else {
    previewFoto.src = "";
    previewFoto.style.display = "none"; // ocultar si no hay archivo
  }
});


btnAceptar.addEventListener("click", () => {
   // Lista de campos a validar
   const campos = [
     { input: eventoSelec, nombre: "Evento" },
     { input: nombreInput, nombre: "nombre" },
     { input: SiglaInput, nombre: "sigla" },
     { input: PaisInput, nombre: "pais" },
     { input: CiudadInput, nombre: "ciudad" },
     { input: fechaInput, nombre: "fechaIni" },
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

   // Si no hay errores, dar el alta y limpiar campos
   alert("Se dio de alta la edición de forma exitosa!");

   nombreInput.value = "";
   SiglaInput.value = "";
   CiudadInput.value = "";
   PaisInput.value = "";
   fechaInput.value = "";
   fotoInput.value = "";
   eventoSelec.value = "";
   previewFoto.src = "";        // Limpiar la imagen
   previewFoto.style.display = "none"; // Ocultar el img
 });

