// Simulación de datos y eventos
    const usuarios = ['ana', 'bruno', 'carla'];
    const registrosPorUsuario = {
      'ana': ['Edición A', 'Edición B'],
      'bruno': [],
      'carla': ['Edición C']
    };

    const detalles = {
      'ana|Edición A': `Edición: Edición A\nUsuario: ana\nTipo: General\nFecha: 01/01/2025\nCosto: $100`,
      'ana|Edición B': `Edición: Edición B\nUsuario: ana\nTipo: VIP\nFecha: 03/02/2025\nCosto: $250`,
      'carla|Edición C': `Edición: Edición C\nUsuario: carla\nTipo: General\nFecha: 10/03/2025\nCosto: $150`,
      'Ana Torres|Maratón de Montevideo 2024': `Edición: Maratón de Montevideo 2024\nUsuario: Ana Torres\nTipo: Corredor 21K\nFecha: 30/07/2024\nCosto: $500\nRegistro con código: falso`,
      'Ana Torres|Web Summit 2026': `Edición: Web Summit 2026\nUsuario: Ana Torres\nTipo: Estudiante\nFecha: 21/08/2025\nCosto: $300\nRegistro con código: falso`,
    };

    const selectAsistente = document.getElementById('selectAsistente');
    const selectRegistro = document.getElementById('selectRegistro');
    const btnDetalle = document.getElementById('btnDetalle');
    const textarea = document.getElementById('detalle');

    // Cargar usuarios
    // usuarios.forEach(u => {
    //   const opt = document.createElement('option');
    //   opt.value = u;
    //   opt.textContent = u;
    //   selectAsistente.appendChild(opt);
    // });

    // // Al cambiar usuario
    // selectAsistente.addEventListener('change', () => {
    //   const usuario = selectAsistente.value;
    //   selectRegistro.innerHTML = '';
    //   textarea.value = '';
    //   btnDetalle.disabled = true;

    //   if (!usuario || !(usuario in registrosPorUsuario)) {
    //     selectRegistro.disabled = true;
    //     return;
    //   }

    //   const registros = registrosPorUsuario[usuario];
    //   if (registros.length === 0) {
    //     selectRegistro.disabled = true;
    //     alert('Este usuario no tiene registros.');
    //     return;
    //   }

    //   registros.forEach(r => {
    //     const opt = document.createElement('option');
    //     opt.value = r;
    //     opt.textContent = r;
    //     selectRegistro.appendChild(opt);
    //   });

    //   selectRegistro.disabled = false;
    //   btnDetalle.disabled = false;
    // });

    // Al hacer clic en Ver Detalle
    btnDetalle.addEventListener('click', () => {
      const usuario = selectAsistente.value;
      const registro = selectRegistro.value;
      const key = `${usuario}|${registro}`;
      debugger;
      if (!usuario) {
        alert('No hay usuario seleccionado.');
        return;
      }
      if (!registro) {
        alert('Selecciona un registro.');
        return;
      }

      textarea.value = detalles[key] || 'No se encontró el detalle.';
      textarea.scrollTop = 0;
    });