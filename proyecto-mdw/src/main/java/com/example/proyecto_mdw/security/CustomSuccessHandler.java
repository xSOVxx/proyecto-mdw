package com.example.proyecto_mdw.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        System.out.println("✅ Redirección personalizada activada");

        authentication.getAuthorities().forEach(auth -> System.out.println("📌 Rol detectado: " + auth.getAuthority()));

        String redirectURL = request.getContextPath();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_GESTORJUEGOS"));
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));

        if (isAdmin) {
            redirectURL += "/admin";
        } else if (isUser) {
            redirectURL += "/perfil"; // ✅ Redirige a una VISTA con HTML/JS que luego llama a /api/perfil
        } else {
            redirectURL += "/login?error=true";
        }

        System.out.println("🔁 Redirigiendo a: " + redirectURL);
        response.sendRedirect(redirectURL);
    }
}
