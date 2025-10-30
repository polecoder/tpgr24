const btn = document.getElementById('nav-toggle');
const nav = document.getElementById('primary-navigation');

// Abre o cierra el nav y actualiza aria-expanded
function toggleNav() {
  const isOpen = nav.classList.toggle('open');
  btn.setAttribute('aria-expanded', String(isOpen));
}

// Fuerza cerrar el nav
function closeNav() {
  if (nav.classList.contains('open')) {
    nav.classList.remove('open');
    btn.setAttribute('aria-expanded', 'false');
  }
}

btn.addEventListener('click', (e) => {
  e.stopPropagation();
  toggleNav();
});

// Cerrar cuando se hace click fuera del nav (y fuera del botón)
document.addEventListener('click', (e) => {
  const target = e.target;
  if (!nav.contains(target) && !btn.contains(target)) {
    closeNav();
  }
});

handleResize();