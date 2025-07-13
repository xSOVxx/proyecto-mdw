package com.example.proyecto_mdw.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = "Ha ocurrido un error";
        String errorDetails = "";
        String errorPage = "error"; // Vista por defecto

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorMessage = "Página no encontrada";
                errorDetails = "La página que busca no existe.";
                errorPage = "error/404"; 
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorMessage = "Acceso denegado";
                errorDetails = "No tiene permisos para acceder a esta página.";
                errorPage = "error/403"; 
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorMessage = "Error interno del servidor";
                errorDetails = "Ha ocurrido un error inesperado en el servidor. Por favor, intente de nuevo más tarde.";
                errorPage = "error/500"; 
            } else {
                errorMessage = "Error " + statusCode;
                errorDetails = "Ha ocurrido un error inesperado.";
            }
        }

        // Obtener mensaje de error específico si existe
        // Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        // if (message != null) {
        //     errorDetails += " " + message.toString();
        // }

        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("errorDetails", errorDetails);

        return errorPage; 
    }

}
