const usuarioSelect = document.getElementById("usuario");
    const panelContainer = document.getElementById("panelAsistenteOrganizador");
    const asistenteFields = document.getElementById("asistenteFields");
    const organizadorFields = document.getElementById("organizadorFields");

    const opt = document.createElement('option');
    opt.value = "Pedro asistente";
    opt.textContent = "Pedro asistente";
    usuarioSelect.appendChild(opt);

    const opt2 = document.createElement('option');
    opt2.value = "Pedro organizador";
    opt2.textContent = "Pedro organizador";
    usuarioSelect.appendChild(opt2);

    usuarioSelect.addEventListener("change", () => {
      const selected = usuarioSelect.value;

      // Simulación de tipo de usuario (en un sistema real esto vendría del backend)
      if (selected === "--Seleccionar un Usuario--") {
        panelContainer.classList.add("d-none");
        asistenteFields.classList.add("d-none");
        organizadorFields.classList.add("d-none");
      } else if (selected.includes("asistente")) {
        panelContainer.classList.remove("d-none");
        asistenteFields.classList.remove("d-none");
        organizadorFields.classList.add("d-none");
      } else if (selected.includes("organizador")) {
        panelContainer.classList.remove("d-none");
        asistenteFields.classList.add("d-none");
        organizadorFields.classList.remove("d-none");
      }
    });