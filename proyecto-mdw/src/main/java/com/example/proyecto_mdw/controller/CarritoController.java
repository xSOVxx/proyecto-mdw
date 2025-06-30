package com.example.proyecto_mdw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.proyecto_mdw.model.CarritoItem;
import com.example.proyecto_mdw.service.CarritoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    
    @Autowired
    private CarritoService carritoService;
    
    /**
     * Agregar producto al carrito (AJAX)
     */
    @PostMapping("/agregar/{juegoId}")
    @ResponseBody
    public ResponseEntity<?> agregarAlCarrito(@PathVariable Long juegoId, HttpSession session) {
        try {
            String sessionId = session.getId();
            carritoService.agregarAlCarrito(juegoId, sessionId);
            
            // Devolver información actualizada del carrito
            Long cantidadItems = carritoService.obtenerCantidadItems(sessionId);
            Double total = carritoService.calcularTotal(sessionId);
            
            return ResponseEntity.ok()
                .body(new CarritoResponse("Producto agregado al carrito", cantidadItems, total));
                
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new CarritoResponse("Error al agregar producto: " + e.getMessage(), 0L, 0.0));
        }
    }
    
    /**
     * Mostrar carrito completo
     */
    @GetMapping("/ver")
    public String verCarrito(HttpSession session, Model model) {
        String sessionId = session.getId();
        List<CarritoItem> items = carritoService.obtenerItemsCarrito(sessionId);
        Double total = carritoService.calcularTotal(sessionId);
        
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("cantidadItems", items.size());
        
        return "carrito";
    }
    
    /**
     * Actualizar cantidad de un item
     */
    @PostMapping("/actualizar/{itemId}")
    public String actualizarCantidad(@PathVariable Long itemId, 
                                   @RequestParam Integer cantidad,
                                   RedirectAttributes redirectAttributes) {
        try {
            carritoService.actualizarCantidad(itemId, cantidad);
            redirectAttributes.addFlashAttribute("mensaje", "Cantidad actualizada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar cantidad: " + e.getMessage());
        }
        return "redirect:/carrito/ver";
    }
    
    /**
     * Eliminar item del carrito
     */
    @PostMapping("/eliminar/{itemId}")
    public String eliminarDelCarrito(@PathVariable Long itemId, RedirectAttributes redirectAttributes) {
        try {
            carritoService.eliminarDelCarrito(itemId);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado del carrito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar producto: " + e.getMessage());
        }
        return "redirect:/carrito/ver";
    }
    
    /**
     * Vaciar carrito completo
     */
    @PostMapping("/vaciar")
    public String vaciarCarrito(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String sessionId = session.getId();
            carritoService.vaciarCarrito(sessionId);
            redirectAttributes.addFlashAttribute("mensaje", "Carrito vaciado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al vaciar carrito: " + e.getMessage());
        }
        return "redirect:/carrito/ver";
    }
    
    /**
     * Obtener información del carrito (AJAX) - para el contador en el navbar
     */
    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<CarritoResponse> obtenerInfoCarrito(HttpSession session) {
        String sessionId = session.getId();
        Long cantidadItems = carritoService.obtenerCantidadItems(sessionId);
        Double total = carritoService.calcularTotal(sessionId);
        
        return ResponseEntity.ok(new CarritoResponse("OK", cantidadItems, total));
    }
    
    /**
     * Proceder al checkout (PayPal)
     */
    @GetMapping("/checkout")
    public String procederCheckout(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String sessionId = session.getId();
        
        if (carritoService.estaVacio(sessionId)) {
            redirectAttributes.addFlashAttribute("error", "El carrito está vacío");
            return "redirect:/carrito/ver";
        }
        
        List<CarritoItem> items = carritoService.obtenerItemsCarrito(sessionId);
        Double total = carritoService.calcularTotal(sessionId);
        
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        
        return "checkout";
    }
    
    // Clase para respuestas AJAX
    public static class CarritoResponse {
        private String mensaje;
        private Long cantidadItems;
        private Double total;
        
        public CarritoResponse(String mensaje, Long cantidadItems, Double total) {
            this.mensaje = mensaje;
            this.cantidadItems = cantidadItems;
            this.total = total;
        }
        
        // Getters
        public String getMensaje() { return mensaje; }
        public Long getCantidadItems() { return cantidadItems; }
        public Double getTotal() { return total; }
    }
}
