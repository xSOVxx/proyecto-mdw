package com.example.proyecto_mdw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.proyecto_mdw.model.RankingItem;

@Repository
public interface RankingRepository extends JpaRepository<RankingItem, Long> {
    
    // Buscar por posición en el ranking
    Optional<RankingItem> findByRanking(int ranking);
    
    // Buscar por nombre (ignorando mayúsculas/minúsculas)
    Optional<RankingItem> findByNombreIgnoreCase(String nombre);
    
    // Listar todos ordenados por ranking
    List<RankingItem> findAllByOrderByRankingAsc();
}