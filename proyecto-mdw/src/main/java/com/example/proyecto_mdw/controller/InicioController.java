package com.example.proyecto_mdw.controller;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.proyecto_mdw.model.Inicio;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class InicioController {
    @GetMapping("/")
    public String index(Model model) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = new ClassPathResource("static/data/juegos.json").getInputStream();
            List<Inicio> juegos = mapper.readValue(is, new TypeReference<List<Inicio>>() {});
            model.addAttribute("juegos", juegos);
        } catch (Exception e) {
            model.addAttribute("juegos", Collections.emptyList());
        }
        return "index";
    }
}
