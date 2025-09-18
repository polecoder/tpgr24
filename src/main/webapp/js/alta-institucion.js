(() => {
  const form = document.getElementById('altaInstitucionForm');
  if (!form) return;

  const alertArea   = document.getElementById('alert-area');

  // refs
  const nombre      = document.getElementById('nombre');
  const descripcion = document.getElementById('descripcion');
  const sitioWeb    = document.getElementById('sitioWeb');
  const imagen      = document.getElementById('imagen');

  // helpers
  const reNombre   = /^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñÜü'()., _\-]{3,80}$/; // 3–80, letras/nros/espacios y . , _ - ( ) '
  const validImage = f => !f || (['image/jpeg','image/png','image/webp','image/gif'].includes(f.type) && f.size <= 2*1024*1024);
  const validURL   = (u) => { try { const x = new URL(u); return x.protocol === 'http:' || x.protocol === 'https:'; } catch { return false; } };

  const showAlert  = html => { alertArea.innerHTML = html; window.scrollTo({ top: 0, behavior: 'smooth' }); };
  const clearAlert = () => { alertArea.innerHTML = ''; };
  const setInvalid = (el, cond) => { if (!el) return false; if (cond) { el.classList.add('is-invalid'); return true; } el.classList.remove('is-invalid'); return false; };

  // limpiar rojo al escribir/cambiar
  [nombre, descripcion, sitioWeb, imagen]
    .filter(Boolean)
    .forEach(el => ['input','change','blur'].forEach(evt => el.addEventListener(evt, () => el.classList.remove('is-invalid'))));

  function validar() {
    clearAlert();
    let hasErrors = false;
    const errs = [];

    // trims
    [nombre, descripcion, sitioWeb].forEach(el => el && (el.value = (el.value || '').trim()));

    // nombre (requerido + formato; la unicidad la valida el backend)
    if (!reNombre.test(nombre.value || '')) {
      hasErrors |= setInvalid(nombre, true);
      errs.push('Nombre inválido (3–80, letras, números, espacios y . , _ - ( ) \').');
    }

    // descripción (requerida, al menos 10 chars)
    if (!descripcion.value || descripcion.value.length < 10) {
      hasErrors |= setInvalid(descripcion, true);
      errs.push('Descripción requerida (mínimo 10 caracteres).');
    }

    // sitio web (requerido, debe empezar con http:// o https://)
    if (!sitioWeb.value || !validURL(sitioWeb.value)) {
      hasErrors |= setInvalid(sitioWeb, true);
      errs.push('Sitio web inválido (debe empezar con http:// o https://).');
    }

    // imagen (opcional): tipo y tamaño
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

    showAlert('<div class="alert alert-success mb-0">Institución dada de alta. Podés cargar otra.</div>');
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

  // (Opcional) mensajes desde backend: ?ok / ?error
  const qs = new URLSearchParams(location.search);
  if (qs.get('ok') === '1') {
    showAlert('<div class="alert alert-success">Institución creada correctamente.</div>');
  } else if (qs.get('error')) {
    showAlert('<div class="alert alert-danger">' + decodeURIComponent(qs.get('error')) + '</div>');
  }
})();