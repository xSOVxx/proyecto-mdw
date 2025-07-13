package com.example.proyecto_mdw.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.proyecto_mdw.model.Role;
import com.example.proyecto_mdw.model.Usuario;

public class SecurityUserDetails implements UserDetails {

    private final Usuario user;

    public SecurityUserDetails(Usuario user) {
        if (user == null) {
            throw new IllegalArgumentException("Usuario no puede ser nulo.");
        }
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("🔍 Obteniendo roles del usuario: " + user.getUsername());
        if (user.getRoles() != null) {
            user.getRoles().forEach(role -> System.out.println("➡ Rol cargado: " + role.getName()));
        } else {
            System.out.println("⚠️ El usuario no tiene roles asignados (es null)");
        }

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(authorities);
    }

    @Override
    public String getPassword() {
        return user.getContrasena();
    }

    @Override
    public String getUsername() {
        return user.getCorreo();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Usuario getUser() {
        return user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SecurityUserDetails that = (SecurityUserDetails) o;
        // Comparar usuarios por nombre de usuario

        return Objects.equals(user.getUsername(), that.user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getUsername());
    }

    @Override
    public String toString() {
        return "SecurityUserDetails{" +
                "username='" + getUsername() + '\'' +
                ", authorities=" + getAuthorities() +
                ", enabled=" + isEnabled() +
                ", accountNonExpired=" + isAccountNonExpired() +
                ", accountNonLocked=" + isAccountNonLocked() +
                ", credentialsNonExpired=" + isCredentialsNonExpired() +
                '}';
    }

}



// package com.example.proyecto_mdw.security;

// import com.example.proyecto_mdw.model.Usuario;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import java.util.Collection;
// import java.util.Collections; // Importar Collections
// import java.util.List;     // Importar List
// import java.util.Objects; // Importar Objects
// import java.util.stream.Collectors;

// /**
//  * Implementación personalizada de UserDetails para integrar la entidad Usuario
//  * con el sistema de seguridad de Spring.
//  */
// public class SecurityUserDetails implements UserDetails {

//     private final Usuario user; // Cambiado de 'usuario' a 'user' para coincidir con tu código

//     /**
//      * Constructor que recibe el objeto Usuario de la base de datos.
//      * @param user El objeto Usuario.
//      */
//     public SecurityUserDetails(Usuario user) {
//         if (user == null) {
//             throw new IllegalArgumentException("Usuario no puede ser nulo.");
//         }
//         this.user = user;
//     }

//     /**
//      * Retorna la colección de autoridades (roles) del usuario.
//      * Los roles se mapean a SimpleGrantedAuthority con el prefijo "ROLE_".
//      * @return Colección de GrantedAuthority.
//      */
//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         System.out.println("🔍 Obteniendo roles del usuario: " + user.getUsername());
//         if (user.getRoles() != null) {
//             user.getRoles().forEach(role -> System.out.println("➡ Rol cargado: " + role.getName()));
//         } else {
//             System.out.println("⚠️ El usuario no tiene roles asignados (es null)");
//         }

//         // Asegúrate de que los roles tengan el prefijo "ROLE_" si tu SecurityConfig lo espera así
//         // Si tu SecurityConfig tiene .hasRole("USER"), entonces el rol en la base de datos debe ser "USER"
//         // y aquí debes añadir "ROLE_" al prefijo. Si tiene .hasAuthority("ROLE_USER"), entonces el rol en la base de datos
//         // debe ser "ROLE_USER" o aquí debes añadir "ROLE_" si el rol en la DB es solo "USER".
//         // Basado en tu SecurityConfig (.hasRole("GESTORJUEGOS"), .hasRole("USER")),
//         // los roles en tu DB deberían ser "GESTORJUEGOS" y "USER", y aquí debes añadir "ROLE_".
//         List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
//                 .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())) // Añadir "ROLE_"
//                 .collect(Collectors.toList());

//         return Collections.unmodifiableList(authorities);
//     }

//     /**
//      * Retorna la contraseña del usuario.
//      * @return Contraseña del usuario.
//      */
//     @Override
//     public String getPassword() {
//         return user.getContrasena();
//     }

//     /**
//      * Retorna el nombre de usuario para la autenticación.
//      * ¡ESTE ES EL CAMBIO CLAVE! Aseguramos que el "username" de UserDetails sea el correo.
//      * @return Correo electrónico del usuario.
//      */
//     @Override
//     public String getUsername() {
//         return user.getCorreo(); // ¡CORREGIDO! Ahora devuelve el correo electrónico.
//     }

//     /**
//      * Indica si la cuenta del usuario ha expirado.
//      * @return true si la cuenta no ha expirado, false en caso contrario.
//      */
//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }

//     /**
//      * Indica si la cuenta del usuario está bloqueada.
//      * @return true si la cuenta no está bloqueada, false en caso contrario.
//      */
//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }

//     /**
//      * Indica si las credenciales del usuario (contraseña) han expirado.
//      * @return true si las credenciales no han expirado, false en caso contrario.
//      */
//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }

//     /**
//      * Indica si el usuario está habilitado o deshabilitado.
//      * @return true si el usuario está habilitado, false en caso contrario.
//      */
//     @Override
//     public boolean isEnabled() {
//         return true;
//     }

//     /**
//      * Retorna el objeto Usuario subyacente.
//      * @return Objeto Usuario.
//      */
//     public Usuario getUser() {
//         return user;
//     }

//     @Override
//     public boolean equals(Object o) {
//         if (this == o)
//             return true;
//         if (o == null || getClass() != o.getClass())
//             return false;
//         SecurityUserDetails that = (SecurityUserDetails) o;
//         // Comparar usuarios por nombre de usuario
//         return Objects.equals(user.getUsername(), that.user.getUsername());
//     }

//     @Override
//     public int hashCode() {
//         return Objects.hash(user.getUsername());
//     }

//     @Override
//     public String toString() {
//         return "SecurityUserDetails{" +
//                 "username='" + getUsername() + '\'' +
//                 ", authorities=" + getAuthorities() +
//                 ", enabled=" + isEnabled() +
//                 ", accountNonExpired=" + isAccountNonExpired() +
//                 ", accountNonLocked=" + isAccountNonLocked() +
//                 ", credentialsNonExpired=" + isCredentialsNonExpired() +
//                 '}';
//     }
// }
