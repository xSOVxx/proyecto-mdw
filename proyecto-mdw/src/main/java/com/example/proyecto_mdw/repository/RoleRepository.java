package com.example.proyecto_mdw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.proyecto_mdw.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
