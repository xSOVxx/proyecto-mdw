package com.example.proyecto_mdw.controller;

import com.example.proyecto_mdw.model.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AuthController {

    private static final String USUARIOS_JSON = "src/main/resources/static/data/usuarios.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Usuario> cargarUsuarios() {
        try {
            File file = new File(USUARIOS_JSON);
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<List<Usuario>>() {});
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void guardarUsuarios(List<Usuario> usuarios) {
        try {
            File directory = new File("src/main/resources/static/data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            objectMapper.writeValue(new File(USUARIOS_JSON), usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validarCorreo(String correo) {
        // Expresión regular para validar un correo electrónico básico
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    private boolean validarContrasena(String contrasena) {
        // Aquí puedes agregar validaciones más robustas para la contraseña
        return contrasena != null && contrasena.length() >= 6;
    }

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

        if (!validarCorreo(correo)) {
            model.addAttribute("errorCorreo", "Por favor, ingresa un correo electrónico válido.");
            return "register";
        }

        if (!validarContrasena(contrasena)) {
            model.addAttribute("errorContrasena", "La contraseña debe tener al menos 6 caracteres.");
            return "register";
        }

        if (!contrasena.equals(confirmarContrasena)) {
            model.addAttribute("errorConfirmarContrasena", "Las contraseñas no coinciden.");
            return "register";
        }

        List<Usuario> usuarios = cargarUsuarios();
        if (usuarios.stream().anyMatch(u -> u.getUsuario().equals(usuario) || u.getCorreo().equals(correo))) {
            model.addAttribute("errorRegistro", "El usuario o el correo electrónico ya están registrados.");
            return "register";
        }

        usuarios.add(new Usuario(usuario, correo, contrasena));
        guardarUsuarios(usuarios);
        model.addAttribute("mensajeRegistroExitoso", "¡Registro exitoso! Ahora puedes iniciar sesión.");
        return "login"; // Redirigimos al login después del registro exitoso
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUsuario(@RequestParam String correo,
                               @RequestParam String contrasena,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        List<Usuario> usuarios = cargarUsuarios();
        Usuario usuarioAutenticado = usuarios.stream()
                .filter(u -> u.getCorreo().equals(correo) && u.getContrasena().equals(contrasena))
                .findFirst()
                .orElse(null);

        if (usuarioAutenticado != null) {
            redirectAttributes.addFlashAttribute("mensajeLoginExitoso", "¡Inicio de sesión exitoso!");
            return "redirect:/";
        } else {
            model.addAttribute("errorLogin", "Correo electrónico o contraseña incorrectos.");
            return "login";
        }
    }
}
