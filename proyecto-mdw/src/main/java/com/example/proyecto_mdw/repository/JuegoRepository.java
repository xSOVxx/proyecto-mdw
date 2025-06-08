package com.example.proyecto_mdw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.proyecto_mdw.model.Juego;

@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
    
    // Método para encontrar juegos ordenados por ranking
    List<Juego> findAllByOrderByRankingAsc();
}