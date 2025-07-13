package com.example.proyecto_mdw.controller;

import com.example.proyecto_mdw.model.Usuario;
import com.example.proyecto_mdw.repository.UsuarioRepository;
import com.example.proyecto_mdw.model.Role;
import com.example.proyecto_mdw.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private boolean validarCorreo(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    // private boolean validarContrasena(String contrasena) {
    //     return contrasena != null && contrasena.length() >= 6;
    // }

    @GetMapping("/register")
    public String mostrarRegistro() {
        return "register";
    }

    @PostMapping("/register")
    public String registrarUsuario(@RequestParam String usuario,
                                   @RequestParam String correo,
                                   @RequestParam String contrasena,
                                   @RequestParam String confirmarContrasena,
                                   Model model) {

        if (usuario == null || usuario.trim().isEmpty() || usuario.length() < 3 || !usuario.matches("^[A-Za-z0-9]+$")) {
            model.addAttribute("errorUsuario", "El usuario debe tener al menos 3 caracteres y solo letras o números.");
            return "register";
        }

        if (correo == null || correo.trim().isEmpty()) {
            model.addAttribute("errorCorreo", "El correo electrónico no puede estar vacío.");
            return "register";
        }

        if (!validarCorreo(correo)) {
            model.addAttribute("errorCorreo", "Por favor, ingresa un correo electrónico válido.");
            return "register";
        }

        if (!(correo.endsWith(".com") || correo.endsWith(".net"))) {
            model.addAttribute("errorCorreo", "El correo debe terminar en .com o .net.");
            return "register";
        }

        if (contrasena == null || contrasena.length() < 8 ||
            !contrasena.matches(".*[A-Z].*") ||
            !contrasena.matches(".*[a-z].*") ||
            !contrasena.matches(".*\\d.*") ||
            !contrasena.matches(".*[^A-Za-z0-9].*")) {
            model.addAttribute("errorContrasena", "La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas, números y símbolos.");
            return "register";
        }

        if (contrasena.toLowerCase().contains(usuario.toLowerCase()) || contrasena.toLowerCase().contains(correo.toLowerCase())) {
            model.addAttribute("errorContrasena", "La contraseña no debe contener el usuario ni el correo.");
            return "register";
        }

        if (!contrasena.equals(confirmarContrasena)) {
            model.addAttribute("errorConfirmarContrasena", "Las contraseñas no coinciden.");
            return "register";
        }

        if (usuarioRepository.findByUsername(usuario).isPresent() || usuarioRepository.findByCorreo(correo).isPresent()) {
            model.addAttribute("errorRegistro", "El usuario o el correo electrónico ya están registrados.");
            return "register";
        }

        String contrasenaCifrada = passwordEncoder.encode(contrasena);
        Usuario nuevoUsuario = new Usuario(usuario, correo, contrasenaCifrada);

        // Asignar rol USER
        Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
        nuevoUsuario.getRoles().add(userRole);

        usuarioRepository.save(nuevoUsuario);
        model.addAttribute("mensajeRegistroExitoso", "¡Registro exitoso! Ahora puedes iniciar sesión.");
        return "login";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // @PostMapping("/login")
    // public String loginUsuario(@RequestParam String correo,
    //                        @RequestParam String contrasena,
    //                        Model model,
    //                        RedirectAttributes redirectAttributes) {
    // Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(correo);

    // if (usuarioOpt.isPresent()) {
    //     Usuario usuario = usuarioOpt.get();

    //     if (passwordEncoder.matches(contrasena, usuario.getContrasena())) {
    //         // Autenticación manual en el contexto de Spring Security
    //         List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    //         Authentication auth = new UsernamePasswordAuthenticationToken(usuario.getCorreo(), null, authorities);
    //         SecurityContextHolder.getContext().setAuthentication(auth);

    //         redirectAttributes.addFlashAttribute("mensajeLoginExitoso", "¡Inicio de sesión exitoso!");
    //         return "redirect:/";
    //     }
    // }

    // model.addAttribute("errorLogin", "Correo electrónico o contraseña incorrectos.");
    //return "login";
//}

}
