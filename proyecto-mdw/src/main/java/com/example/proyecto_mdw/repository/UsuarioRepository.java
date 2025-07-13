package com.example.proyecto_mdw.repository;

import com.example.proyecto_mdw.model.Usuario;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @EntityGraph(attributePaths = "roles")
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByUsername(String username);
}
