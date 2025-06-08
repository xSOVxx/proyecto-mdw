package com.example.proyecto_mdw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.repository.JuegoRepository;

@Service
public class RankingService {

    @Autowired
    private JuegoRepository juegoRepository;
    
    public List<Juego> obtenerJuegosRanking() {
        return juegoRepository.findAllByOrderByRankingAsc();
    }
    
    public Optional<Juego> obtenerJuegoPorId(Long id) {
        return juegoRepository.findById(id);
    }
    
    public Juego guardarJuego(Juego juego) {
        return juegoRepository.save(juego);
    }
    
    public void eliminarJuego(Long id) {
        juegoRepository.deleteById(id);
    }
    
    public boolean actualizarRanking(Long id, int nuevoRanking) {
        Optional<Juego> juegoOpt = juegoRepository.findById(id);
        if (juegoOpt.isPresent()) {
            Juego juego = juegoOpt.get();
            juego.setRanking(nuevoRanking);
            juegoRepository.save(juego);
            return true;
        }
        return false;
    }
}