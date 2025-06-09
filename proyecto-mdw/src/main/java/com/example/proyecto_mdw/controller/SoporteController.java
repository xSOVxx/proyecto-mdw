/*package com.example.proyecto_mdw.controller;

import com.example.proyecto_mdw.model.soportemodel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;

@Controller
public class SoporteController {

    private static final String RUTA_JSON = "src/main/resources/static/data/soporte.json";

    @GetMapping("/soporte")
    public String mostrarSoporte(Model model) {
        try {
            // Convertir JSON en objeto Java
            ObjectMapper mapper = new ObjectMapper();
            File archivo = new File(RUTA_JSON);
            soportemodel soporte = mapper.readValue(archivo, soportemodel.class);

            // Agregar objeto a la vista
            model.addAttribute("soporte", soporte);
            return "soporte"; // Nombre del archivo HTML (soporte.html)

        } catch (IOException e) {
            e.printStackTrace();
            return "error"; // Vista de error si ocurre algún problema
        }
    }
}
*/