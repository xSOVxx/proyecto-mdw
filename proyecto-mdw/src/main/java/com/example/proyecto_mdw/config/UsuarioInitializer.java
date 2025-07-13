package com.example.proyecto_mdw.config;

import java.util.HashSet;
import java.util.Optional; // Import Optional for findByUsername
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.proyecto_mdw.model.Role;
import com.example.proyecto_mdw.model.Usuario;
import com.example.proyecto_mdw.repository.RoleRepository;
import com.example.proyecto_mdw.repository.UsuarioRepository;

import jakarta.annotation.PostConstruct;

@Component
public class UsuarioInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        Role userRole = findOrCreateRole("ROLE_USER");
        Role gestorJuegosRole = findOrCreateRole("ROLE_GESTORJUEGOS");       
        
        Optional<Usuario> regularUserOptional = userRepository.findByUsername("user");
        if (regularUserOptional.isEmpty()) {
            Usuario user = new Usuario();
            user.setUsername("user");
            user.setCorreo("user@example.com");
            user.setContrasena(passwordEncoder.encode("userpassword")); 
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);
            user.setRoles(userRoles);
            userRepository.save(user);
            System.out.println("Usuario regular creado por defecto.");
        } else {
            System.out.println("El usuario regular ya existe.");
        }

        // --- 4. Create Default GestorJuegos User ---
        Optional<Usuario> gestorUserOptional = userRepository.findByUsername("gestor");
        if (gestorUserOptional.isEmpty()) {
            Usuario gestor = new Usuario();
            gestor.setUsername("gestor");
            gestor.setCorreo("gestor@example.com");
            gestor.setContrasena(passwordEncoder.encode("gestorpassword")); 
            Set<Role> gestorRoles = new HashSet<>();
            gestorRoles.add(gestorJuegosRole); 
            gestor.setRoles(gestorRoles);
            userRepository.save(gestor);
            System.out.println("Usuario gestorJuegos creado por defecto.");
        } else {
            System.out.println("El usuario gestorJuegos ya existe.");
        }
    }

    private Role findOrCreateRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role newRole = new Role(roleName);
                    return roleRepository.save(newRole);
                });
    }
}
