package com.example.proyecto_mdw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.repository.JuegoRepository;

@Service
public class JuegoService {
    
    @Autowired
    private JuegoRepository juegoRepository;
    
    /**
     * Listar todos los juegos
     */
    public List<Juego> listarTodos() {
        return juegoRepository.findAll();
    }
    
    /**
     * Buscar un juego por su ID
     */
    public Optional<Juego> buscarPorId(Long id) {
        return juegoRepository.findById(id);
    }
    
    /**
     * Guardar un juego (crear o actualizar)
     */
    @Transactional
    public Juego guardar(Juego juego) {
        return juegoRepository.save(juego);
    }
    
    /**
     * Eliminar un juego por su ID
     */
    @Transactional
    public void eliminar(Long id) {
        juegoRepository.deleteById(id);
    }
}