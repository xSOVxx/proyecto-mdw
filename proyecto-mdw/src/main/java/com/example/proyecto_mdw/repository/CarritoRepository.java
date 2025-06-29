package com.example.proyecto_mdw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.proyecto_mdw.model.CarritoItem;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoItem, Long> {
    
    // Buscar todos los items del carrito por sessionId
    List<CarritoItem> findBySessionId(String sessionId);
    
    // Buscar un item específico por juego y sessionId
    Optional<CarritoItem> findByJuegoIdAndSessionId(Long juegoId, String sessionId);
    
    // Contar items en el carrito por sessionId
    @Query("SELECT COUNT(c) FROM CarritoItem c WHERE c.sessionId = :sessionId")
    Long countBySessionId(@Param("sessionId") String sessionId);
    
    // Calcular total del carrito por sessionId
    @Query("SELECT SUM(c.precio * c.cantidad) FROM CarritoItem c WHERE c.sessionId = :sessionId")
    Double calculateTotalBySessionId(@Param("sessionId") String sessionId);
    
    // Eliminar todos los items del carrito por sessionId
    void deleteBySessionId(String sessionId);
}
