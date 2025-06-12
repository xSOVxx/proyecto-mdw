package com.example.proyecto_mdw.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "ranking_items")
public class RankingItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del juego es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @NotBlank(message = "La descripción es obligatoria") 
    @Size(min = 10, max = 1000, message = "La descripción debe tener entre 10 y 1000 caracteres")
    @Column(nullable = false, length = 1000)
    private String descripcion;
    
    @Column(length = 255)
    private String imagen;
    
    @NotNull(message = "La posición en el ranking es obligatoria")
    @Min(value = 1, message = "El ranking debe ser mayor a 0")
    @Max(value = 1000, message = "El ranking no puede ser mayor a 1000")
    @Column(nullable = false, unique = true)
    private Integer ranking;
    
    @NotBlank(message = "El género es obligatorio")
    @Size(max = 50, message = "El género no puede exceder 50 caracteres")
    @Column(nullable = false, length = 50)
    private String genero;
    
    @ElementCollection
    @CollectionTable(name = "ranking_plataformas", joinColumns = @JoinColumn(name = "ranking_item_id"))
    @Column(name = "plataforma")
    private List<String> plataforma;
    
    @Size(max = 20, message = "La fecha de lanzamiento no puede exceder 20 caracteres")
    @Column(length = 20)
    private String lanzamiento;
    
    @NotNull(message = "La calificación es obligatoria")
    @DecimalMin(value = "0.0", message = "La calificación debe ser mayor o igual a 0")
    @DecimalMax(value = "10.0", message = "La calificación no puede ser mayor a 10")
    @Column(nullable = false)
    private Double calificacion;
    
    @Size(max = 255, message = "El enlace no puede exceder 255 caracteres")
    @Column(length = 255)
    private String enlace;
    
    // Constructor vacío
    public RankingItem() {}
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getImagen() {
        return imagen;
    }
    
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public Integer getRanking() {
        return ranking;
    }
    
    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public List<String> getPlataforma() {
        return plataforma;
    }
    
    public void setPlataforma(List<String> plataforma) {
        this.plataforma = plataforma;
    }
    
    public String getLanzamiento() {
        return lanzamiento;
    }
    
    public void setLanzamiento(String lanzamiento) {
        this.lanzamiento = lanzamiento;
    }
    
    public Double getCalificacion() {
        return calificacion;
    }
    
    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
    
    public String getEnlace() {
        return enlace;
    }
    
    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }
}