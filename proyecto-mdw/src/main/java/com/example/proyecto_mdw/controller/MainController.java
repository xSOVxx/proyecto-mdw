package com.example.proyecto_mdw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/soporte")
    public String soporte() {
        return "soporte"; // Redirige a soporte.html
    }


}
