package com.example.proyecto_mdw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.proyecto_mdw.model.RankingItem;
import com.example.proyecto_mdw.service.RankingService;

@Controller
public class RankingController {

    @Autowired
    private RankingService rankingService;
    
    @GetMapping("/ranking")
    public String mostrarRanking(Model model) {
        List<RankingItem> juegosRankeados = rankingService.obtenerTodosLosJuegosRankeados();
        model.addAttribute("juegos", juegosRankeados);
        return "ranking";
    }
    
    @GetMapping("/ranking/{ranking}")
    public String mostrarDetalleJuegoRankeado(@PathVariable int ranking, Model model) {
        rankingService.buscarPorRanking(ranking)
            .ifPresent(juego -> model.addAttribute("juego", juego));
        return "detalle-ranking";
    }
}