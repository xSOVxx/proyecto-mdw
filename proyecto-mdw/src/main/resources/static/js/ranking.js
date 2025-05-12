// Efecto: animar las tarjetas al pasar el mouse y mostrar tooltip en el botón "Ver más"
document.addEventListener("DOMContentLoaded", function () {
  // Animación de sombra al pasar el mouse
  document.querySelectorAll('.game-card').forEach(card => {
    card.addEventListener('mouseenter', () => {
      card.style.boxShadow = '0 8px 32px rgba(0,173,181,0.25)';
      card.style.transform = 'scale(1.03)';
    });
    card.addEventListener('mouseleave', () => {
      card.style.boxShadow = '';
      card.style.transform = '';
    });
  });

  // Tooltips para los botones "Ver más"
  const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
  tooltipTriggerList.forEach(function (tooltipTriggerEl) {
    new bootstrap.Tooltip(tooltipTriggerEl);
  });
});