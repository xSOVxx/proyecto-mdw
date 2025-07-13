package com.example.proyecto_mdw.dto;

import org.springframework.security.access.prepost.PreAuthorize;

// @PreAuthorize("hasRole('USER')")
public class PerfilDTO {
    private String username;
    private String correo;

    // Constructor con argumentos
    public PerfilDTO(String username, String correo) {
        this.username = username;
        this.correo = correo;
    }

    // Constructor sin argumentos (necesario para algunas operaciones de Spring/Jackson)
    public PerfilDTO() {
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getCorreo() {
        return correo;
    }

    // Setters (añadidos para mayor flexibilidad, aunque no se usen directamente en este caso)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
