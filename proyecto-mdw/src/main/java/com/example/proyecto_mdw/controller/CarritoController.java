package com.example.proyecto_mdw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${paypal.client-id:AelS8gq3hUVJNm7nq1gnACJIohbWJgQ-vXYoV1pCIiyEDbrrTLGc_E9Z-kyluWsf0GGp7cr90nXlpUVj}")
    private String paypalClientId;

    @PostMapping("/agregar/{juegoId}")
    @ResponseBody
    public ResponseEntity<?> agregarAlCarrito(@PathVariable Long juegoId, HttpSession session) {
        try {
            String sessionId = session.getId();
            carritoService.agregarAlCarrito(juegoId, sessionId);
            Long cantidadItems = carritoService.obtenerCantidadItems(sessionId);
            Double total = carritoService.calcularTotal(sessionId);
            return ResponseEntity.ok(new CarritoResponse("Producto agregado", cantidadItems, total));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new CarritoResponse("Error: " + e.getMessage(), 0L, 0.0));
        }
    }

    @GetMapping("/ver")
    public String verCarrito(HttpSession session, Model model) {
        String sessionId = session.getId();
        List<CarritoItem> items = carritoService.obtenerItemsCarrito(sessionId);
        Double total = carritoService.calcularTotal(sessionId);
        
        // Debug logging
        System.out.println("PayPal Client ID: " + paypalClientId);
        System.out.println("Total del carrito: " + total);
        System.out.println("Cantidad de items: " + items.size());
        
        model.addAttribute("items", items);
        model.addAttribute("total", total != null ? total : 0.0);
        model.addAttribute("cantidadItems", items.size());
        model.addAttribute("paypalClientId", paypalClientId);
        
        return "carrito";
    }

    @PostMapping("/actualizar/{itemId}")
    public String actualizarCantidad(@PathVariable Long itemId, @RequestParam Integer cantidad, RedirectAttributes redirectAttributes) {
        try {
            if (cantidad < 1 || cantidad > 10) {
                throw new IllegalArgumentException("La cantidad debe estar entre 1 y 10");
            }
            carritoService.actualizarCantidad(itemId, cantidad);
            redirectAttributes.addFlashAttribute("mensaje", "Cantidad actualizada");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/carrito/ver";
    }

    @PostMapping("/eliminar/{itemId}")
    public String eliminarDelCarrito(@PathVariable Long itemId, RedirectAttributes redirectAttributes) {
        try {
            carritoService.eliminarDelCarrito(itemId);
            redirectAttributes.addFlashAttribute("mensaje", "Producto eliminado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/carrito/ver";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            String sessionId = session.getId();
            carritoService.vaciarCarrito(sessionId);
            redirectAttributes.addFlashAttribute("mensaje", "Carrito vaciado");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/carrito/ver";
    }

    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<CarritoResponse> obtenerInfoCarrito(HttpSession session) {
        String sessionId = session.getId();
        Long cantidadItems = carritoService.obtenerCantidadItems(sessionId);
        Double total = carritoService.calcularTotal(sessionId);
        return ResponseEntity.ok(new CarritoResponse("OK", cantidadItems, total));
    }

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
        
        // Setters (opcional)
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }
        public void setCantidadItems(Long cantidadItems) { this.cantidadItems = cantidadItems; }
        public void setTotal(Double total) { this.total = total; }
    }
}