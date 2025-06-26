package com.example.proyecto_mdw.repository;

import com.example.proyecto_mdw.model.MensajeContacto; // se importa la entidad mensajecontacto
import org.springframework.data.jpa.repository.JpaRepository; //herramienta que proporciona springdata para obetener los metodos CRUD
import org.springframework.stereotype.Repository;

@Repository 
public interface MensajeContactoRepository extends JpaRepository<MensajeContacto, Long> {

}
