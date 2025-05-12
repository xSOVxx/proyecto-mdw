// Archivo dedicado a animaciones

document.addEventListener("DOMContentLoaded", () => {
    const tarjetas = document.querySelectorAll(".card");

    tarjetas.forEach(tarjeta => {
        tarjeta.addEventListener("mouseover", () => {
            tarjeta.classList.add("shadow-lg");
            tarjeta.style.transition = "transform 0.3s";
            tarjeta.style.transform = "scale(1.05)";
        });

        tarjeta.addEventListener("mouseout", () => {
            tarjeta.classList.remove("shadow-lg");
            tarjeta.style.transform = "scale(1)";
        });
    });
});
