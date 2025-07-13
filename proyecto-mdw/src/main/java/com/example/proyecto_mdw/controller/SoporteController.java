 package com.example.proyecto_mdw.controller;

import com.example.proyecto_mdw.model.SoporteModel;
import com.example.proyecto_mdw.model.MensajeContacto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.proyecto_mdw.repository.MensajeContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class SoporteController {

    @Autowired
    private MensajeContactoRepository mensajeContactoRepository;

     @Autowired
     private PasswordEncoder passwordEncoder;

    private static final String RUTA_SOPORTE_JSON = "/static/data/soporte.json";

    @GetMapping("/soporte")
    public String mostrarSoporte(Model model) {
        ObjectMapper mapper = new ObjectMapper();
        SoporteModel soporteModel = null;

        try (InputStream inputStream = getClass().getResourceAsStream(RUTA_SOPORTE_JSON)) {
            if (inputStream == null) {
                System.err.println("Recurso JSON soporte.json no encontrado en el classpath: " + RUTA_SOPORTE_JSON);
                model.addAttribute("error", "No se pudo cargar la información de soporte.");
                return "error";
            }
            soporteModel = mapper.readValue(inputStream, SoporteModel.class);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar soporte.json: " + e.getMessage());
            model.addAttribute("error", "Error interno al cargar la página de soporte.");
            return "error";
        }
        model.addAttribute("soporte", soporteModel);
        return "soporte";
    }

    @PostMapping("/soporte/enviar")
    public String enviarMensajeContacto(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("message") String mensaje,
            RedirectAttributes redirectAttributes) {

        try {
            String hashedPassword = passwordEncoder.encode(password);

            MensajeContacto nuevoMensaje = new MensajeContacto(email, hashedPassword, mensaje); // Pasa el hashedPassword

            mensajeContactoRepository.save(nuevoMensaje);

            redirectAttributes.addFlashAttribute("mensajeExito", "¡Tu mensaje ha sido enviado con éxito!");
            System.out.println("Mensaje de contacto guardado en la base de datos: " + nuevoMensaje);

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensajeError", "Hubo un error al enviar tu mensaje. Por favor, inténtalo de nuevo.");
            System.err.println("Error al guardar mensaje de contacto en la base de datos: " + e.getMessage());
        }

        return "redirect:/soporte";
    
    }
}

