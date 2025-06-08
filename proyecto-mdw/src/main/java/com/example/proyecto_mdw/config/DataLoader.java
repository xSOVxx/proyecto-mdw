package com.example.proyecto_mdw.config;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.repository.JuegoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JuegoRepository juegoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Cargar datos desde el JSON al iniciar la aplicación solo si la BD está vacía
        if (juegoRepository.count() == 0) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                InputStream is = new ClassPathResource("static/data/ranking.json").getInputStream();
                List<Juego> juegos = mapper.readValue(is, new TypeReference<List<Juego>>() {});
                
                // Guardar los juegos en la base de datos
                juegoRepository.saveAll(juegos);
                
                System.out.println("✅ Datos cargados en la base de datos: " + juegoRepository.count() + " juegos");
            } catch (Exception e) {
                System.err.println("❌ Error al cargar datos iniciales: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("ℹ️ La base de datos ya contiene datos. No se cargarán datos iniciales.");
        }
    }
}