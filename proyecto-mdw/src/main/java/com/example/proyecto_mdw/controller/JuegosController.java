package com.example.proyecto_mdw.controller;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.proyecto_mdw.model.Juego;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class JuegosController {

    @GetMapping("/juegos")
    public String mostrarJuegos(Model model) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = new ClassPathResource("static/data/juegos.json").getInputStream();
            List<Juego> juegos = mapper.readValue(is, new TypeReference<List<Juego>>() {});
            model.addAttribute("juegos", juegos);
        } catch (Exception e) {
            model.addAttribute("juegos", Collections.emptyList());
        }
        return "juegos";
    }
}
