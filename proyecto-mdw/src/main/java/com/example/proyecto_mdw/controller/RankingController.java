package com.example.proyecto_mdw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    
    // Métodos para administrar el ranking (opcional)
    @GetMapping("/admin/ranking")
    public String mostrarAdminRanking(Model model) {
        List<RankingItem> juegosRankeados = rankingService.obtenerTodosLosJuegosRankeados();
        model.addAttribute("juegos", juegosRankeados);
        model.addAttribute("juegoNuevo", new RankingItem());
        return "admin-ranking";
    }
    
    @PostMapping("/admin/ranking/guardar")
    public String guardarRanking(@ModelAttribute("juegoNuevo") RankingItem juegoRanking, 
                                RedirectAttributes redirectAttributes) {
        try {
            rankingService.guardar(juegoRanking);
            redirectAttributes.addFlashAttribute("mensaje", "Juego guardado en el ranking correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el juego en el ranking: " + e.getMessage());
        }
        return "redirect:/admin/ranking";
    }
    
    @GetMapping("/admin/ranking/eliminar/{id}")
    public String eliminarDelRanking(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            rankingService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Juego eliminado del ranking correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el juego del ranking: " + e.getMessage());
        }
        return "redirect:/admin/ranking";
    }
}