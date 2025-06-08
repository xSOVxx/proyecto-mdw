package com.example.proyecto_mdw.controller;

import com.example.proyecto_mdw.model.Soportemodel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.InputStream;
import java.io.IOException;

@Controller
public class SoporteController {

    private static final String RUTA_JSON = "/static/data/soporte.json";

    @GetMapping("/soporte")
    public String mostrarSoporte(Model model) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream inputStream = getClass().getResourceAsStream(RUTA_JSON);

            if (inputStream == null) { // se comprueba si se encuentra el json

                System.err.println("Recurso JSON no encontrado en el classpath: " + RUTA_JSON);
                return "error"; // mensaje de error

            }

            // Convertir JSON (desde el InputStream) en objeto Java
            Soportemodel soporte = mapper.readValue(inputStream, Soportemodel.class);

            // Cierra el InputStream después de usarlo
            inputStream.close();

            // Coloca los objetos java  en el model (Soportemodel)
            //Ahora el Thymeleaft podra acceder al objeto con el nombre "soporte"
            model.addAttribute("soporte", soporte);

            return "soporte"; // indica a spring que la vista a renderizar es el html"soporte"
            

        } catch (IOException e) { //Si ocurre un error al leer el Json y convertirlo a objeto se captura error
            e.printStackTrace();
            System.err.println("Error al procesar el archivo soporte.json: " + e.getMessage());
            return "error"; // Vista de error si ocurre algún problema
        }

    }
}
