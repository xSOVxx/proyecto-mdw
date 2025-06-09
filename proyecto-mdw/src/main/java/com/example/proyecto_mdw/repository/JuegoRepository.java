package com.example.proyecto_mdw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.proyecto_mdw.model.Juego;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
    // Métodos de consulta de JPA se generan automáticamente
}