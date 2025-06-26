package com.example.proyecto_mdw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto_mdw.model.RankingItem;
import com.example.proyecto_mdw.repository.RankingRepository;

@Service
public class RankingService {

    @Autowired
    private RankingRepository rankingRepository;
    
    /**
     * Obtiene la lista completa de juegos rankeados
     */
    public List<RankingItem> obtenerTodosLosJuegosRankeados() {
        return rankingRepository.findAllByOrderByRankingAsc();
    }
    
    /**
     * Busca un juego específico por su ranking
     */
    public Optional<RankingItem> buscarPorRanking(int ranking) {
        return rankingRepository.findByRanking(ranking);
    }
    
    /**
     * Busca un juego específico por su nombre
     */
    public Optional<RankingItem> buscarPorNombre(String nombre) {
        return rankingRepository.findByNombreIgnoreCase(nombre);
    }
    
    /**
     * Guarda un nuevo juego en el ranking o actualiza uno existente
     */
    @Transactional
    public RankingItem guardar(RankingItem item) {
        return rankingRepository.save(item);
    }
    
    /**
     * Elimina un juego del ranking
     */
    @Transactional
    public void eliminar(Long id) {
        rankingRepository.deleteById(id);
    }
}