package com.example.proyecto_mdw.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.service.JuegoService;

@Controller
@RequestMapping("/admin")
public class AdminJuegosController {

    @Autowired
    private JuegoService juegoService;
    
    // Directorio donde se guardarán las imágenes
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";
    
    @GetMapping("")
    public String mostrarAdmin(Model model) {
        try {
            List<Juego> juegos = juegoService.listarTodos();
            model.addAttribute("juegos", juegos);
            
            if (!model.containsAttribute("juego")) {
                model.addAttribute("juego", new Juego());
            }
            
            return "admin";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar los juegos: " + e.getMessage());
            model.addAttribute("juegos", Collections.emptyList());
            model.addAttribute("juego", new Juego());
            return "admin";
        }
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Juego juego = juegoService.buscarPorId(id)
                .orElseThrow(() -> new Exception("No se encontró el juego con ID: " + id));
            
            model.addAttribute("juego", juego);
            model.addAttribute("juegos", juegoService.listarTodos());
            
            return "admin";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/admin";
        }
    }
    
    // Solo modificar la parte del método guardarJuego para no actualizar JSON
    @PostMapping("/guardar")
    public String guardarJuego(
            @ModelAttribute Juego juego,
            @RequestParam(required = false) List<String> plataformasSeleccionadas,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {
        try {
            // Manejar plataformas
            if (plataformasSeleccionadas != null && !plataformasSeleccionadas.isEmpty()) {
                juego.setPlataformas(plataformasSeleccionadas);
            } else {
                juego.setPlataformas(new ArrayList<>());
            }
            
            // Manejar la carga de imágenes
            if (imagenFile != null && !imagenFile.isEmpty()) {
                try {
                    // Generar nombre único para el archivo
                    String fileName = UUID.randomUUID().toString() + "_" + imagenFile.getOriginalFilename();
                    
                    // Crear directorio si no existe
                    Path uploadPath = Paths.get(UPLOAD_DIR);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }
                    
                    // Guardar el archivo
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(imagenFile.getInputStream(), filePath);
                    
                    // Actualizar la ruta de la imagen en el objeto juego
                    juego.setImagen(fileName);
                    
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("error", "Error al cargar la imagen: " + e.getMessage());
                    return "redirect:/admin";
                }
            }
            
            // Guardar el juego
            Juego juegoGuardado = juegoService.guardar(juego);
            
            String mensaje = (juego.getId() == null) 
                ? "¡Juego creado con éxito!" 
                : "¡Juego actualizado con éxito!";
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
        }
        
        return "redirect:/admin";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminarJuego(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            juegoService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Juego eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/admin";
    }
}




