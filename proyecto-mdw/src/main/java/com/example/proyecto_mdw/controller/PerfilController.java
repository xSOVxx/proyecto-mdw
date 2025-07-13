package com.example.proyecto_mdw.controller;

import com.example.proyecto_mdw.dto.PerfilDTO;
import com.example.proyecto_mdw.model.Usuario;
import com.example.proyecto_mdw.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails; // Importar UserDetails
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class PerfilController {

    private static final Logger logger = LoggerFactory.getLogger(PerfilController.class);

    private final UsuarioRepository usuarioRepository;

    public PerfilController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/perfil")
    public String showPerfil(Model model) {
        logger.info(">>> Accediendo al controlador PerfilController.showPerfil");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            logger.warn("<<< Usuario no autenticado intentando acceder al perfil. Redirigiendo o mostrando datos por defecto.");
            model.addAttribute("perfil", new PerfilDTO("No autenticado", "No autenticado"));
            return "perfil";
        }

        // --- CAMBIO CLAVE AQUÍ ---
        // Obtener el objeto UserDetails del principal de autenticación
        // Este objeto contiene la información del usuario cargada por SecurityUserDetailsService
        String userCorreo = null;
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            // Si el principal es una instancia de UserDetails (que es lo común con Spring Security)
            // entonces el "username" de UserDetails es el correo electrónico en tu caso.
            userCorreo = ((UserDetails) principal).getUsername();
            logger.info(">>> Obtenido correo de UserDetails: {}", userCorreo);
        } else {
            // Si el principal no es UserDetails (ej. String para usuarios anónimos),
            // se usa authentication.getName() como fallback, aunque no debería ser el caso aquí.
            userCorreo = authentication.getName();
            logger.warn(">>> Principal no es UserDetails. Usando authentication.getName() como fallback: {}", userCorreo);
        }
        // --- FIN DEL CAMBIO CLAVE ---


        if (userCorreo == null || userCorreo.isEmpty()) {
            logger.error("<<< No se pudo obtener el correo del usuario autenticado.");
            model.addAttribute("perfil", new PerfilDTO("Error", "Error al obtener correo"));
            return "perfil";
        }

        logger.info(">>> Buscando usuario en la base de datos con correo: {}", userCorreo);
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCorreo(userCorreo);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            logger.info(">>> Usuario encontrado en la base de datos. Username: {}, Correo: {}", usuario.getUsername(), usuario.getCorreo());

            PerfilDTO perfilDTO = new PerfilDTO(usuario.getUsername(), usuario.getCorreo());
            model.addAttribute("perfil", perfilDTO);
            logger.info("<<< PerfilDTO añadido al modelo con datos: Username={}, Correo={}", perfilDTO.getUsername(), perfilDTO.getCorreo());
        } else {
            logger.warn("<<< Usuario no encontrado en la base de datos para el correo: {}", userCorreo);
            model.addAttribute("perfil", new PerfilDTO("Usuario no encontrado", "Usuario no encontrado"));
            model.addAttribute("errorMessage", "No se pudo cargar la información del perfil. Usuario no encontrado en la base de datos.");
        }

        return "perfil";
    }
}