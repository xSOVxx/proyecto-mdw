package com.example.proyecto_mdw.controller;

import com.example.proyecto_mdw.model.SoporteModel;
import com.example.proyecto_mdw.model.MensajeContacto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;


@Controller 
public class SoporteController {

    private static final String RUTA_SOPORTE_JSON = "/static/data/soporte.json";
    private static final String RUTA_BASE_PROYECTO = System.getProperty("user.dir");
    private static final String RUTA_MENSAJE_JSON = RUTA_BASE_PROYECTO + File.separator + "data" + File.separator + "mensaje.json";

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

        ObjectMapper mapper = new ObjectMapper();
        File mensajesFile = new File(RUTA_MENSAJE_JSON);

        List<MensajeContacto> listaMensajes = new ArrayList<>();

        try {
            File parentDir = mensajesFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
                System.out.println("Carpeta 'data' creada en: " + parentDir.getAbsolutePath());
            }

            if (mensajesFile.exists() && mensajesFile.length() > 0) {
                listaMensajes = mapper.readValue(mensajesFile, new TypeReference<List<MensajeContacto>>() {});
            } else {
                System.out.println("Creando o inicializando mensaje.json en: " + mensajesFile.getAbsolutePath());
                mapper.writerWithDefaultPrettyPrinter().writeValue(mensajesFile, new ArrayList<MensajeContacto>());
                listaMensajes = mapper.readValue(mensajesFile, new TypeReference<List<MensajeContacto>>() {});
            }

            MensajeContacto nuevoMensaje = new MensajeContacto(email, password, mensaje);
            listaMensajes.add(nuevoMensaje);

            mapper.writerWithDefaultPrettyPrinter().writeValue(mensajesFile, listaMensajes);

            redirectAttributes.addFlashAttribute("mensajeExito", "¡Tu mensaje ha sido enviado con éxito!");
            System.out.println("Mensaje de contacto guardado en " + RUTA_MENSAJE_JSON + ": " + nuevoMensaje);

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("mensajeError", "Hubo un error al enviar tu mensaje. Por favor, inténtalo de nuevo.");
            System.err.println("Error al guardar mensaje de contacto en " + RUTA_MENSAJE_JSON + ": " + e.getMessage());
        }

        return "redirect:/soporte";
    }
}
