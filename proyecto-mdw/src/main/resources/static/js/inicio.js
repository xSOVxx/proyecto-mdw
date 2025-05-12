// Crear un array de objetos para los juegos
let juegos = [
    { nombre: "Fortnite", descripcion: "Juego de batalla campal", imagen: "https://img.utdstc.com/icon/58d/ad7/58dad7ac070993e99d4c9f495898e5459c7aca5e133a1ff630468f1275f0d6fc:200" },
    { nombre: "Valorant", descripcion: "Juego de disparos táctico", imagen: "https://via.placeholder.com/300x200" },
    { nombre: "Minecraft", descripcion: "Juego de construcción y aventuras", imagen: "https://via.placeholder.com/300x200" }
];

// Función para renderizar las cartas de juegos
function renderizarJuegos() {
    const contenedor = document.getElementById("juegos");
    const fila = document.createElement("div");
    fila.className = "row g-4";

    juegos.forEach(juego => {
        const columna = document.createElement("div");
        columna.className = "col-12 col-sm-6 col-lg-4";

        const carta = document.createElement("div");
        carta.className = "card h-100 text-center";

        const imagen = document.createElement("img");
        imagen.src = juego.imagen;
        imagen.alt = juego.nombre;
        imagen.className = "card-img-top";

        const cuerpo = document.createElement("div");
        cuerpo.className = "card-body";

        const titulo = document.createElement("h5");
        titulo.className = "card-title";
        titulo.textContent = juego.nombre;

        const descripcion = document.createElement("p");
        descripcion.className = "card-text";
        descripcion.textContent = juego.descripcion;

        const pie = document.createElement("div");
        pie.className = "card-footer";

        const boton = document.createElement("a");
        boton.href = "#";
        boton.className = "btn btn-primary";
        boton.textContent = "Ver más";

        // Construir la carta
        cuerpo.appendChild(titulo);
        cuerpo.appendChild(descripcion);
        pie.appendChild(boton);
        carta.appendChild(imagen);
        carta.appendChild(cuerpo);
        carta.appendChild(pie);
        columna.appendChild(carta);
        fila.appendChild(columna);
    });

    contenedor.appendChild(fila);
}

// Llamar a la función para renderizar las cartas
renderizarJuegos();