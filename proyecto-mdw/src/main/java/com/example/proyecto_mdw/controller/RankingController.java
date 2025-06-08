package com.example.proyecto_mdw.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.service.RankingService;

@Controller
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/ranking")
    public String mostrarRanking(Model model) {
        try {
            List<Juego> juegos = rankingService.obtenerJuegosRanking();
            model.addAttribute("juegos", juegos);
        } catch (Exception e) {
            model.addAttribute("juegos", Collections.emptyList());
            model.addAttribute("error", "Error al cargar datos: " + e.getMessage());
        }
        return "ranking"; // Mantenemos el nombre de la vista para no afectar rutas
    }
    
    @GetMapping("/ranking/juego/{id}")
    public String detalleJuego(@PathVariable Long id, Model model) {
        try {
            Optional<Juego> juego = rankingService.obtenerJuegoPorId(id);
            if (juego.isPresent()) {
                model.addAttribute("juego", juego.get());
                return "detalle-juego";
            } else {
                return "redirect:/ranking";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el juego: " + e.getMessage());
            return "redirect:/ranking";
        }
    }
}