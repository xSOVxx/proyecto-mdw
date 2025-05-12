package com.example.proyecto_mdw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index"; // Redirige a index.html
    }

    @GetMapping("/soporte")
    public String soporte() {
        return "soporte"; // Redirige a soporte.html
    }

    @GetMapping("/ranking")
    public String ranking() {
        return "Ranking"; // Redirige a Ranking.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Redirige a login.html
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // Redirige a register.html
    }
}
