package com.example.proyecto_mdw.config;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.proyecto_mdw.model.Role;
import com.example.proyecto_mdw.model.Usuario;
import com.example.proyecto_mdw.repository.RoleRepository;
import com.example.proyecto_mdw.repository.UsuarioRepository;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;

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
        // Primero verifica si ya existe el rol
        Role registradorRole = roleRepository.findByName("");
        if (registradorRole == null) {
            registradorRole = new Role();
            registradorRole.setName("");
            roleRepository.save(registradorRole);
        }
        Role consultorRole = roleRepository.findByName("");
        if (consultorRole == null) {
            consultorRole = new Role();
            consultorRole.setName("");
            roleRepository.save(consultorRole);
        }
        // Luego verifica si existe el usuario admin
        if (!userRepository.existsById("")) {
            // Usuario admin = new Usuario();
            // admin.setUsername("admin");
            // admin.setPassword(passwordEncoder.encode("admin123"));
            // admin.setEmail("admin@example.com");
            // admin.setEnabled(true);
            // admin.setRoles(new HashSet<>());
            // admin.getRoles().add(registradorRole);
            // userRepository.save(admin);
            // System.out.println("Usuario admin creado exitosamente");
        }
        // Crear un usuario consultor de prueba
        if (!userRepository.existsById("")) {
            // Usuario consultor = new Usuario();
            // consultor.setUsername("consultor");
            // consultor.setPassword(passwordEncoder.encode("consultor123")); // La contraseña será "consultor123"
            // consultor.setEmail("consultor@example.com");
            // consultor.setEnabled(true);
            // consultor.setRoles(new HashSet<>());
            // consultor.getRoles().add(consultorRole);
            // userRepository.save(consultor);
            // System.out.println("Usuario consultor creado exitosamente");
        }
    }

}
