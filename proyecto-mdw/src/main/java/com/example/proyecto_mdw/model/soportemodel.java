package com.example.proyecto_mdw.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty; // Importar si los nombres del JSON no coinciden con las variables

public class SoporteModel{ 

    // Propiedades de alto nivel que mapean a los objetos principales del JSON
    private Compras compras;
    private Cuenta cuenta;
    private Juegos juegos;
    private Contacto contacto;

    // --- Getters y Setters para las propiedades de alto nivel ---
    public Compras getCompras() {
        return compras;
    }

    public void setCompras(Compras compras) {
        this.compras = compras;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Juegos getJuegos() {
        return juegos;
    }

    public void setJuegos(Juegos juegos) {
        this.juegos = juegos;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    // --- Clases Internas (anidadas) que representan los objetos del JSON ---

    public static class Compras {
        // En el JSON es "metodos_pago", si tu variable es camelCase "metodosPago", Jackson lo mapea por defecto.
        // Si quieres mantener "metodos_pago", puedes usar @JsonProperty("metodos_pago") o simplemente nombrar la variable igual.
        private List<MetodoPago> metodos_pago; // O List<MetodoPago> metodosPago; con @JsonProperty("metodos_pago")
        private String facturacion;

        // Getters y Setters para Compras
        public List<MetodoPago> getMetodos_pago() {
            return metodos_pago;
        }

        public void setMetodos_pago(List<MetodoPago> metodos_pago) {
            this.metodos_pago = metodos_pago;
        }

        public String getFacturacion() {
            return facturacion;
        }

        public void setFacturacion(String facturacion) {
            this.facturacion = facturacion;
        }
    }

    public static class Cuenta {
        private String cambiar_contrasena;
        private String actualizar_datos; // Recuerda: tenías esta clave duplicada en el JSON, asegúrate de que sea intencional o corrígela en el JSON si no.
        private String cerrar_sesion;

        // Getters y Setters para Cuenta
        public String getCambiar_contrasena() {
            return cambiar_contrasena;
        }

        public void setCambiar_contrasena(String cambiar_contrasena) {
            this.cambiar_contrasena = cambiar_contrasena;
        }

        public String getActualizar_datos() {
            return actualizar_datos;
        }

        public void setActualizar_datos(String actualizar_datos) {
            this.actualizar_datos = actualizar_datos;
        }

        public String getCerrar_sesion() {
            return cerrar_sesion;
        }

        public void setCerrar_sesion(String cerrar_sesion) {
            this.cerrar_sesion = cerrar_sesion;
        }
    }

    public static class Juegos {
        private String descargas;
        private String errores_frecuentes;
        private String actualizaciones;

        // Getters y Setters para Juegos
        public String getDescargas() {
            return descargas;
        }

        public void setDescargas(String descargas) {
            this.descargas = descargas;
        }

        public String getErrores_frecuentes() {
            return errores_frecuentes;
        }

        public void setErrores_frecuentes(String errores_frecuentes) {
            this.errores_frecuentes = errores_frecuentes;
        }

        public String getActualizaciones() {
            return actualizaciones;
        }

        public void setActualizaciones(String actualizaciones) {
            this.actualizaciones = actualizaciones;
        }
    }

    public static class Contacto {
        private String mensaje;

        // Getters y Setters para Contacto
        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}