package com.example.proyecto_mdw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.proyecto_mdw.model.Inicio;

@Controller
public class InicioController {
    @GetMapping("/")
    public String index(Model model) {
        // Eliminamos la lista carrusel ya que el carrusel es estático
        List<Inicio> juegos = List.of(
            new Inicio("Fortnite", "Juego de batalla campal", "juegos/juego1.avif"),
            new Inicio("Valorant", "Juego de disparos táctico", "juegos/valorant.webp"),
            new Inicio("Minecraft", "Juego de construcción y aventuras", "juegos/minecraft.jpg")
        );

        model.addAttribute("juegos", juegos);
        return "index";
    }
}
