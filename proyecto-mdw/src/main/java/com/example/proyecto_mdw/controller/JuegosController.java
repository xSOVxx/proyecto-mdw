package com.example.proyecto_mdw.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.service.JuegoService;

@Controller
public class JuegosController {

    @Autowired
    private JuegoService juegoService;
    
    @GetMapping("/juegos")
    public String mostrarJuegos(
            @RequestParam(required = false) Double minPrecio,
            @RequestParam(required = false) Double maxPrecio,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String plataforma,
            @RequestParam(required = false) String ordenarPor,
            Model model) {
        try {
            List<Juego> juegos = juegoService.listarTodos();
            
            // Aplicar filtros
            if (minPrecio != null) {
                juegos = juegos.stream()
                    .filter(j -> j.getPrecio() >= minPrecio)
                    .collect(Collectors.toList());
            }
            
            if (maxPrecio != null) {
                juegos = juegos.stream()
                    .filter(j -> j.getPrecio() <= maxPrecio)
                    .collect(Collectors.toList());
            }
            
            if (categoria != null && !categoria.isEmpty()) {
                juegos = juegos.stream()
                    .filter(j -> j.getCategoria() != null && j.getCategoria().equals(categoria))
                    .collect(Collectors.toList());
            }
            
            if (plataforma != null && !plataforma.isEmpty()) {
                juegos = juegos.stream()
                    .filter(j -> j.getPlataformas() != null && j.getPlataformas().contains(plataforma))
                    .collect(Collectors.toList());
            }
            
            // Aplicar ordenamiento
            if ("precioAsc".equals(ordenarPor)) {
                juegos.sort((j1, j2) -> Double.compare(j1.getPrecio(), j2.getPrecio()));
            } else if ("precioDesc".equals(ordenarPor)) {
                juegos.sort((j1, j2) -> Double.compare(j2.getPrecio(), j1.getPrecio()));
            } else if ("nombre".equals(ordenarPor)) {
                juegos.sort((j1, j2) -> j1.getNombre().compareToIgnoreCase(j2.getNombre()));
            }
            
            model.addAttribute("juegos", juegos);
            
            // Pasar los parámetros de filtro al modelo para mantenerlos en la UI
            model.addAttribute("minPrecio", minPrecio);
            model.addAttribute("maxPrecio", maxPrecio);
            model.addAttribute("categoriaSeleccionada", categoria);
            model.addAttribute("plataformaSeleccionada", plataforma);
            model.addAttribute("ordenarPor", ordenarPor);
            
            return "juegos";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar los juegos: " + e.getMessage());
            model.addAttribute("juegos", Collections.emptyList());
            return "juegos";
        }
    }
    
    @GetMapping("/juegos/{id}")
    public String mostrarDetalleJuego(@PathVariable Long id, Model model) {
        juegoService.buscarPorId(id)
            .ifPresent(juego -> model.addAttribute("juego", juego));
        return "detalle-juego";
    }
}
