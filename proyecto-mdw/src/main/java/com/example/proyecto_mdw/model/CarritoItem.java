package com.example.proyecto_mdw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "carrito_items")
public class CarritoItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "juego_id", nullable = false)
    private Juego juego;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(nullable = false)
    private Integer cantidad = 1;
    
    @NotNull(message = "El precio es obligatorio")
    @Column(nullable = false)
    private Double precio;
    
    @Column(length = 255)
    private String sessionId; // Para identificar el carrito de cada usuario/sesión
    
    // Constructor vacío
    public CarritoItem() {}
    
    // Constructor con parámetros
    public CarritoItem(Juego juego, Integer cantidad, String sessionId) {
        this.juego = juego;
        this.cantidad = cantidad;
        this.precio = juego.getPrecio();
        this.sessionId = sessionId;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Juego getJuego() {
        return juego;
    }
    
    public void setJuego(Juego juego) {
        this.juego = juego;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public Double getPrecio() {
        return precio;
    }
    
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    // Método para calcular el subtotal
    public Double getSubtotal() {
        return precio * cantidad;
    }
    
    @Override
    public String toString() {
        return "CarritoItem [id=" + id + ", juego=" + juego.getNombre() + ", cantidad=" + cantidad + ", precio=" + precio + "]";
    }
}
