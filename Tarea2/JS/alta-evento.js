(() => {
  const form = document.getElementById('altaEventoForm');
  if (!form) return;

  const alertArea   = document.getElementById('alert-area');

  // refs
  const nombre      = document.getElementById('nombre');
  const descripcion = document.getElementById('descripcion');
  const sigla       = document.getElementById('sigla');
  const imagen      = document.getElementById('imagen');
  const cats        = form.querySelectorAll('input[name="categorias"]');

  // helpers
  const reNombre  = /^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñÜü'()., _\-]{3,80}$/; // 3–80, letras/nros/espacios y . , _ - ( ) '
  const reSigla   = /^[A-Za-z0-9\-]{2,15}$/;                      // opcional; si hay: 2–15 alfanum+guion
  const validImage = f => !f || (['image/jpeg','image/png','image/webp','image/gif'].includes(f.type) && f.size <= 2*1024*1024);

  const showAlert  = html => { alertArea.innerHTML = html; window.scrollTo({ top: 0, behavior: 'smooth' }); };
  const clearAlert = () => { alertArea.innerHTML = ''; };
  const setInvalid = (el, cond) => { if (!el) return false; if (cond) { el.classList.add('is-invalid'); return true; } el.classList.remove('is-invalid'); return false; };

  // UX: sigla en mayúsculas
  sigla?.addEventListener('input', () => { sigla.value = sigla.value.toUpperCase(); });

  // limpiar rojo al escribir/cambiar
  [nombre, descripcion, sigla, imagen].filter(Boolean)
    .forEach(el => ['input','change','blur'].forEach(evt => el.addEventListener(evt, () => el.classList.remove('is-invalid'))));
  cats.forEach(c => c.addEventListener('change', () => cats.forEach(x => x.classList.remove('is-invalid'))));

  function validar() {
    clearAlert();
    let hasErrors = false;
    const errs = [];

    // trims
    [nombre, descripcion, sigla].forEach(el => el && (el.value = (el.value || '').trim()));

    // nombre (requerido + formato) — la unicidad la valida el backend
    if (!reNombre.test(nombre.value || '')) {
      hasErrors |= setInvalid(nombre, true);
      errs.push('Nombre inválido (3–80, letras, números, espacios y . , _ - ( ) \').');
    }

    // descripción (requerida, mínimo 10)
    if (!descripcion.value || descripcion.value.length < 10) {
      hasErrors |= setInvalid(descripcion, true);
      errs.push('Descripción requerida (mínimo 10 caracteres).');
    }

    // sigla (opcional, pero si hay debe cumplir)
    if (sigla.value && !reSigla.test(sigla.value)) {
      hasErrors |= setInvalid(sigla, true);
      errs.push('Sigla inválida (2–15, letras/números/guion).');
    }

    // categorías: al menos una
    const algunaCat = Array.from(cats).some(c => c.checked);
    if (!algunaCat) {
      // marcamos el primero para que se vea el borde rojo
      cats[0]?.classList.add('is-invalid');
      errs.push('Seleccioná al menos una categoría.');
      hasErrors = true;
    }

    // imagen (opcional)
    const file = imagen?.files?.[0];
    if (file && !validImage(file)) {
      hasErrors |= setInvalid(imagen, true);
      errs.push('Imagen inválida: JPEG/PNG/WEBP/GIF y ≤ 2 MB.');
    }

    if (hasErrors) {
      showAlert('<div class="alert alert-warning"><strong>Revisá los campos:</strong><ul class="mb-0 ps-3">' +
        errs.map(x => `<li>${x}</li>`).join('') + '</ul></div>');
      (form.querySelector('.is-invalid') || nombre)?.focus?.();
    }

    return !hasErrors;
  }

  // submit: quedarse en la página; si todo OK, limpiar y mostrar éxito
  form.addEventListener('submit', (e) => {
    e.preventDefault();
    if (!validar()) return;

    form.reset();
    form.classList.remove('was-validated');
    form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));

    showAlert('<div class="alert alert-success mb-0">Evento dado de alta. Podés cargar otro.</div>');
    nombre?.focus();
  });

  // reset: limpiar alertas/estilos
  form.addEventListener('reset', () => {
    setTimeout(() => {
      clearAlert();
      form.classList.remove('was-validated');
      form.querySelectorAll('.is-invalid').forEach(el => el.classList.remove('is-invalid'));
    }, 0);
  });

  // mensajes opcionales desde backend (?ok / ?error) si luego conectás
  const qs = new URLSearchParams(location.search);
  if (qs.get('ok') === '1') {
    showAlert('<div class="alert alert-success">Evento creado correctamente.</div>');
  } else if (qs.get('error')) {
    showAlert('<div class="alert alert-danger">' + decodeURIComponent(qs.get('error')) + '</div>');
  }
})();