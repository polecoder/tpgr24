(() => {
  const form = document.getElementById('altaUsuarioForm');
  if (!form) return;

  const alertArea = document.getElementById('alert-area');

  // Radios y bloques
  const tipoRadios = form.querySelectorAll('input[name="tipo"]');
  const tipo = () => form.querySelector('input[name="tipo"]:checked')?.value || null;
  const bloqueOrg = document.getElementById('bloqueOrganizador');
  const bloqueAsi = document.getElementById('bloqueAsistente');
  const tipoFeedback = document.getElementById('tipoFeedback');

  // Campos comunes
  const nickname = document.getElementById('nickname');
  const nombre   = document.getElementById('nombre');
  const email    = document.getElementById('email');
  const password = document.getElementById('password');
  const confirm  = document.getElementById('confirmPassword');
  const imagen   = document.getElementById('imagen');

  // Organizador
  const descripcion = document.getElementById('descripcion');
  const sitioWeb    = document.getElementById('sitioWeb');

  // Asistente
  const apellido    = document.getElementById('apellido');
  const fechaNac    = document.getElementById('fechaNacimiento');
  const chkInst     = document.getElementById('perteneceInstitucion');
  const filaInst    = document.getElementById('filaInstitucion');
  const selInst     = document.getElementById('institucionId');

  // Helpers
  const reNick   = /^[A-Za-z0-9_.-]{3,20}$/;
  const reNombre = /^[A-Za-zÁÉÍÓÚáéíóúÑñÜü' -]{2,40}$/;
  const validURL = (u) => { try { const x = new URL(u); return x.protocol === 'http:' || x.protocol === 'https:'; } catch { return false; } };
  const notFuture = (s) => { const d = new Date(s); if (Number.isNaN(+d)) return false; const t = new Date(); t.setHours(0,0,0,0); const min = new Date('1900-01-01'); return d >= min && d <= t; };
  const validImage = (f) => !f || (['image/jpeg','image/png','image/webp','image/gif'].includes(f.type) && f.size <= 2*1024*1024);

  const showAlert = (html) => { alertArea.innerHTML = html; window.scrollTo({ top: 0, behavior: 'smooth' }); };
  const clearAlert = () => { alertArea.innerHTML = ''; };
  const setInvalid = (el, cond) => { if (!el) return false; if (cond) { el.classList.add('is-invalid'); return true; } el.classList.remove('is-invalid'); return false; };

  // Mostrar/ocultar por tipo y setear required dinámicos
  function syncTipo() {
    // limpiar required dinámicos
    [descripcion, apellido, fechaNac, selInst].forEach(el => el && (el.required = false));
    if (tipoFeedback) tipoFeedback.style.display = 'none';

    if (tipo() === 'ORGANIZADOR') {
      bloqueOrg.classList.remove('d-none'); bloqueAsi.classList.add('d-none');
      if (descripcion) descripcion.required = true;
      if (chkInst) { chkInst.checked = false; filaInst?.classList.add('d-none'); if (selInst) { selInst.required = false; selInst.value = ''; } }
    } else if (tipo() === 'ASISTENTE') {
      bloqueAsi.classList.remove('d-none'); bloqueOrg.classList.add('d-none');
      if (apellido) apellido.required = true;
      if (fechaNac) fechaNac.required = true;
    } else {
      bloqueOrg.classList.add('d-none'); bloqueAsi.classList.add('d-none');
    }
  }
  tipoRadios.forEach(r => r.addEventListener('change', syncTipo));
  chkInst?.addEventListener('change', () => {
    if (chkInst.checked) { filaInst?.classList.remove('d-none'); if (selInst) selInst.required = true; }
    else { filaInst?.classList.add('d-none'); if (selInst) { selInst.required = false; selInst.value = ''; } }
  });

  // Quitar rojo al escribir/cambiar
  [nickname,nombre,email,password,confirm,imagen,descripcion,sitioWeb,apellido,fechaNac,selInst]
    .filter(Boolean)
    .forEach(el => ['input','change','blur'].forEach(evt => el.addEventListener(evt, () => el.classList.remove('is-invalid'))));

  // Validación completa
  function validar() {
    clearAlert();
    let hasErrors = false;
    const errs = [];

    // normalizar strings
    const trim = (el) => { if (el && typeof el.value === 'string') el.value = el.value.trim(); };
    [nickname, nombre, email, apellido, descripcion, sitioWeb].forEach(trim);

    // tipo requerido
    if (!tipo()) { if (tipoFeedback) tipoFeedback.style.display = 'block'; hasErrors = true; errs.push('Seleccioná el tipo de usuario.'); }

    // comunes
    if (!reNick.test(nickname.value || '')) { hasErrors |= setInvalid(nickname, true); errs.push('Nickname inválido (3–20, letras/números/._-).'); }
    if (!reNombre.test(nombre.value || '')) { hasErrors |= setInvalid(nombre, true); errs.push('Nombre inválido.'); }

    if (email && email.type === 'email') {
      if (!email.checkValidity()) { hasErrors |= setInvalid(email, true); errs.push('Correo con formato inválido.'); }
    } else {
      const reEmail = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!reEmail.test(email.value || '')) { hasErrors |= setInvalid(email, true); errs.push('Correo con formato inválido.'); }
    }

    if ((password.value || '').length < 6) { hasErrors |= setInvalid(password, true); errs.push('Contraseña: mínimo 6 caracteres.'); }
    if ((confirm.value || '') !== (password.value || '')) { hasErrors |= setInvalid(confirm, true); errs.push('Las contraseñas no coinciden.'); }

    const file = imagen?.files?.[0];
    if (file && !validImage(file)) { hasErrors |= setInvalid(imagen, true); errs.push('Imagen: JPEG/PNG/WEBP/GIF y ≤ 2 MB.'); }

    // organizador
    if (tipo() === 'ORGANIZADOR') {
      if (!descripcion.value) { hasErrors |= setInvalid(descripcion, true); errs.push('Descripción de organizador es obligatoria.'); }
      if (sitioWeb.value) {
        if (!validURL(sitioWeb.value)) { hasErrors |= setInvalid(sitioWeb, true); errs.push('Sitio web debe empezar con http:// o https://'); }
        else sitioWeb.classList.remove('is-invalid');
      } else sitioWeb.classList.remove('is-invalid');
    }

    // asistente
    if (tipo() === 'ASISTENTE') {
      if (!reNombre.test(apellido.value || '')) { hasErrors |= setInvalid(apellido, true); errs.push('Apellido inválido.'); }
      if (!fechaNac.value || !notFuture(fechaNac.value)) { hasErrors |= setInvalid(fechaNac, true); errs.push('Fecha de nacimiento inválida (no puede ser futura).'); }
      if (chkInst?.checked) {
        if (!selInst?.value) { hasErrors |= setInvalid(selInst, true); errs.push('Seleccioná una institución o desmarcá la opción.'); }
      } else selInst?.classList.remove('is-invalid');
    }

    if (hasErrors) {
      showAlert('<div class="alert alert-warning"><strong>Revisá los campos marcados:</strong><ul class="mb-0 ps-3">' +
        errs.map(x => `<li>${x}</li>`).join('') + '</ul></div>');
      // foco al primer inválido
      const firstInvalid = form.querySelector('.is-invalid');
      firstInvalid?.focus?.();
    }
    return !hasErrors;
  }

  // Submit: NO navegar, limpiar si todo está OK
  form.addEventListener('submit', (e) => {
    e.preventDefault(); // siempre evitamos navegación
    if (!validar()) return;

    // Éxito (solo front): reset y mensaje
    form.reset();
    form.classList.remove('was-validated');
    // limpiar marcas rojas
    form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
    // ocultar bloques y feedback de tipo
    syncTipo();
    showAlert('<div class="alert alert-success mb-0">Usuario dado de alta. Podés ingresar otro.</div>');
    nickname?.focus();
  });

  // Reset: dejar todo prolijo
  form.addEventListener('reset', () => {
    setTimeout(() => {
      clearAlert();
      form.classList.remove('was-validated');
      form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
      if (tipoFeedback) tipoFeedback.style.display = 'none';
      syncTipo();
    }, 0);
  });

  // init
  syncTipo();
})();