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

@Entity
public class Juego {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String descripcion;
    private String imagen;
    private int ranking;
    private String genero;
    
    @ElementCollection
    @CollectionTable(name = "juego_plataformas", joinColumns = @JoinColumn(name = "juego_id"))
    @Column(name = "plataforma")
    private List<String> plataforma;
    
    private String lanzamiento;
    private double calificacion;
    private String enlace;

    public Juego() {}

    // Getters y setters existentes
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public int getRanking() { return ranking; }
    public void setRanking(int ranking) { this.ranking = ranking; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public List<String> getPlataforma() { return plataforma; }
    public void setPlataforma(List<String> plataforma) { this.plataforma = plataforma; }

    public String getLanzamiento() { return lanzamiento; }
    public void setLanzamiento(String lanzamiento) { this.lanzamiento = lanzamiento; }

    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }

    public String getEnlace() { return enlace; }
    public void setEnlace(String enlace) { this.enlace = enlace; }

    // Nuevo getter y setter para ID
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}