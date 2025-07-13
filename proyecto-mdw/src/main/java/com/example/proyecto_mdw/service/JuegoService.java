package com.example.proyecto_mdw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.repository.JuegoRepository;

@Service
@Transactional
public class JuegoService {
    
    @Autowired
    private JuegoRepository juegoRepository;
    
    /**
     * Listar todos los juegos
     */
    public List<Juego> listarTodos() {
        try {
            List<Juego> juegos = juegoRepository.findAll();
            System.out.println("Encontrados " + juegos.size() + " juegos en la base de datos");
            return juegos;
        } catch (Exception e) {
            System.err.println("Error al listar juegos: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Buscar un juego por su ID
     */
    public Optional<Juego> buscarPorId(Long id) {
        try {
            return juegoRepository.findById(id);
        } catch (Exception e) {
            System.err.println("Error al buscar juego por ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Guardar un juego (crear o actualizar)
     */
    public Juego guardar(Juego juego) {
        try {
            Juego juegoGuardado = juegoRepository.save(juego);
            System.out.println("Juego guardado: " + juegoGuardado.getNombre() + " (ID: " + juegoGuardado.getId() + ")");
            return juegoGuardado;
        } catch (Exception e) {
            System.err.println("Error al guardar juego: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Eliminar un juego por su ID
     */
    public void eliminar(Long id) {
        try {
            juegoRepository.deleteById(id);
            System.out.println("Juego eliminado con ID: " + id);
        } catch (Exception e) {
            System.err.println("Error al eliminar juego con ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}