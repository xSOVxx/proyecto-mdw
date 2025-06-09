package com.example.proyecto_mdw.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.proyecto_mdw.model.RankingItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RankingService {

    private static final String RANKING_JSON_PATH = "static/data/ranking.json";
    
    /**
     * Obtiene la lista completa de juegos rankeados desde el archivo JSON
     */
    public List<RankingItem> obtenerTodosLosJuegosRankeados() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = new ClassPathResource(RANKING_JSON_PATH).getInputStream();
            return mapper.readValue(is, new TypeReference<List<RankingItem>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    /**
     * Busca un juego específico por su ranking
     */
    public Optional<RankingItem> buscarPorRanking(int ranking) {
        List<RankingItem> juegos = obtenerTodosLosJuegosRankeados();
        return juegos.stream()
                .filter(juego -> juego.getRanking() == ranking)
                .findFirst();
    }
    
    /**
     * Busca un juego específico por su nombre
     */
    public Optional<RankingItem> buscarPorNombre(String nombre) {
        List<RankingItem> juegos = obtenerTodosLosJuegosRankeados();
        return juegos.stream()
                .filter(juego -> juego.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }
}