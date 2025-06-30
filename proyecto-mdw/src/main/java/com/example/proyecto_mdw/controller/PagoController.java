package com.example.proyecto_mdw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.proyecto_mdw.model.CarritoItem;
import com.example.proyecto_mdw.service.CarritoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/pago")
public class PagoController {
    
    @Autowired
    private CarritoService carritoService;
    
    @Value("${paypal.client-id:AelS8gq3hUVJNm7nq1gnAjnT8MRrJJiD6rQzVrZ4yQA6X0I8G5FtjOhCOOHOt42r9yTKODnBAH1YG}")
    private String paypalClientId;
    
    @Value("${paypal.mode:sandbox}")
    private String paypalMode;
    
    @Value("${paypal.currency:USD}")
    private String paypalCurrency;
    
    /**
     * Checkout mejorado con configuración de PayPal
     */
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String sessionId = session.getId();
        
        if (carritoService.estaVacio(sessionId)) {
            redirectAttributes.addFlashAttribute("error", "El carrito está vacío");
            return "redirect:/carrito/ver";
        }
        
        List<CarritoItem> items = carritoService.obtenerItemsCarrito(sessionId);
        Double total = carritoService.calcularTotal(sessionId);
        
        // Agregar configuración de PayPal al modelo
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("paypalClientId", paypalClientId);
        model.addAttribute("paypalMode", paypalMode);
        model.addAttribute("paypalCurrency", paypalCurrency);
        model.addAttribute("isSandbox", "sandbox".equals(paypalMode));
        
        return "checkout";
    }
    
    /**
     * Procesar pago exitoso con información del método utilizado
     */
    @PostMapping("/success")
    public String pagoExitoso(@RequestParam String paymentId, 
                             @RequestParam String payerId,
                             @RequestParam(required = false) String paymentMethod,
                             HttpSession session, 
                             RedirectAttributes redirectAttributes) {
        try {
            String sessionId = session.getId();
            
            // Obtener información del carrito antes de vaciarlo
            List<CarritoItem> items = carritoService.obtenerItemsCarrito(sessionId);
            Double total = carritoService.calcularTotal(sessionId);
            
            // Aquí podrías verificar el pago con PayPal API si fuera necesario
            // Para sandbox, simplemente vaciamos el carrito
            carritoService.vaciarCarrito(sessionId);
            
            // Determinar el método de pago usado
            String metodoPago = (paymentMethod != null) ? paymentMethod : "PayPal/Tarjeta";
            
            redirectAttributes.addFlashAttribute("mensaje", 
                "¡Pago exitoso! Tu compra ha sido procesada correctamente.");
            redirectAttributes.addFlashAttribute("transactionId", paymentId);
            redirectAttributes.addFlashAttribute("payerId", payerId);
            redirectAttributes.addFlashAttribute("metodoPago", metodoPago);
            redirectAttributes.addFlashAttribute("total", total);
            redirectAttributes.addFlashAttribute("cantidadItems", items.size());
            
            return "redirect:/pago/confirmacion";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Error al procesar el pago: " + e.getMessage());
            return "redirect:/carrito/ver";
        }
    }
    
    /**
     * Mostrar confirmación de pago
     */
    @GetMapping("/confirmacion")
    public String confirmacionPago(Model model) {
        return "pago-confirmacion";
    }
    
    /**
     * Pago cancelado
     */
    @GetMapping("/cancelado")
    public String pagoCancelado(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "El pago fue cancelado");
        return "redirect:/carrito/ver";
    }
}
