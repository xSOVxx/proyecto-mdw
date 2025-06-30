package com.example.proyecto_mdw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto_mdw.model.CarritoItem;
import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.repository.CarritoRepository;
import com.example.proyecto_mdw.repository.JuegoRepository;

@Service
@Transactional
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;
    
    @Autowired
    private JuegoRepository juegoRepository;
    
    /**
     * Agregar un juego al carrito
     */
    public CarritoItem agregarAlCarrito(Long juegoId, String sessionId) {
        // Buscar el juego
        Optional<Juego> juegoOpt = juegoRepository.findById(juegoId);
        if (!juegoOpt.isPresent()) {
            throw new RuntimeException("Juego no encontrado");
        }
        
        Juego juego = juegoOpt.get();
        
        // Verificar si ya está en el carrito
        Optional<CarritoItem> itemExistente = carritoRepository.findByJuegoIdAndSessionId(juegoId, sessionId);
        
        if (itemExistente.isPresent()) {
            // Si ya existe, incrementar cantidad
            CarritoItem item = itemExistente.get();
            item.setCantidad(item.getCantidad() + 1);
            return carritoRepository.save(item);
        } else {
            // Si no existe, crear nuevo item
            CarritoItem nuevoItem = new CarritoItem(juego, 1, sessionId);
            return carritoRepository.save(nuevoItem);
        }
    }
    
    /**
     * Obtener todos los items del carrito
     */
    public List<CarritoItem> obtenerItemsCarrito(String sessionId) {
        return carritoRepository.findBySessionId(sessionId);
    }
    
    /**
     * Actualizar cantidad de un item
     */
    public CarritoItem actualizarCantidad(Long itemId, Integer nuevaCantidad) {
        Optional<CarritoItem> itemOpt = carritoRepository.findById(itemId);
        if (!itemOpt.isPresent()) {
            throw new RuntimeException("Item no encontrado en el carrito");
        }
        
        CarritoItem item = itemOpt.get();
        if (nuevaCantidad <= 0) {
            carritoRepository.delete(item);
            return null;
        } else {
            item.setCantidad(nuevaCantidad);
            return carritoRepository.save(item);
        }
    }
    
    /**
     * Eliminar un item del carrito
     */
    public void eliminarDelCarrito(Long itemId) {
        carritoRepository.deleteById(itemId);
    }
    
    /**
     * Obtener cantidad total de items en el carrito
     */
    public Long obtenerCantidadItems(String sessionId) {
        Long cantidad = carritoRepository.countBySessionId(sessionId);
        return cantidad != null ? cantidad : 0L;
    }
    
    /**
     * Calcular total del carrito
     */
    public Double calcularTotal(String sessionId) {
        Double total = carritoRepository.calculateTotalBySessionId(sessionId);
        return total != null ? total : 0.0;
    }
    
    /**
     * Vaciar carrito completamente
     */
    public void vaciarCarrito(String sessionId) {
        carritoRepository.deleteBySessionId(sessionId);
    }
    
    /**
     * Verificar si el carrito está vacío
     */
    public boolean estaVacio(String sessionId) {
        return obtenerCantidadItems(sessionId) == 0;
    }
}
