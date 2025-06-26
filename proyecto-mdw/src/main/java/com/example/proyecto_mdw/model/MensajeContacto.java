package com.example.proyecto_mdw.model;

import jakarta.persistence.Entity; 
import jakarta.persistence.Id;     
import jakarta.persistence.GeneratedValue; 
import jakarta.persistence.GenerationType; 

@Entity // Declara esta clase como una entidad JPA
public class MensajeContacto {

    @Id // Marca 'id' como la clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID será autoincremental por la base de datos
    private Long id;

    private String email;
    private String password;
    private String mensaje;
    private long timestamp;

    public MensajeContacto() {
        // Constructor vacío 
    }

    public MensajeContacto(String email, String password, String mensaje) {
         this.email = email;
         this.password = password;
         this.mensaje = mensaje;
         this.timestamp = System.currentTimeMillis();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MensajeContacto{" +
               "id=" + id +
               ", email='" + email + '\'' +
               ", mensaje='" + mensaje + '\'' +
               ", timestamp=" + timestamp +
               '}';
    }
}