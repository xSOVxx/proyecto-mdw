package com.example.proyecto_mdw.model;

public class MensajeContacto {
 private String email;
 private String password;
 private String mensaje;
 private long timestamp;
 public MensajeContacto() {
    
 }
public MensajeContacto(String email, String password, String mensaje) {
     this.email = email;
        this.password = password;
        this.mensaje = mensaje;
        this.timestamp = System.currentTimeMillis(); 
}

//GETTERS Y SETTERS
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
               "email='" + email + '\'' +
               ", mensaje='" + mensaje + '\'' +
               ", timestamp=" + timestamp +
               '}';
    }
}
