package com.example.proyecto_mdw.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.repository.JuegoRepository;
import com.example.proyecto_mdw.service.JuegoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JuegoRepository juegoRepository;
    
    @Autowired
    private JuegoService juegoService;

    @Override
    public void run(String... args) throws Exception {
        // Solo cargar datos si la base de datos está vacía
        if (juegoRepository.count() == 0) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                InputStream is = new ClassPathResource("static/data/juegos.json").getInputStream();
                List<Juego> juegos = mapper.readValue(is, new TypeReference<List<Juego>>() {});
                
                System.out.println("📚 Cargando " + juegos.size() + " juegos desde juegos.json...");
                
                // Guardar en base de datos
                for (Juego juego : juegos) {
                    juegoService.guardar(juego);
                }
                
                System.out.println("✅ Datos iniciales cargados en la base de datos");
            } catch (IOException e) {
                System.err.println("❌ Error al cargar datos iniciales: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("ℹ️ La base de datos ya contiene datos");
        }
    }
}