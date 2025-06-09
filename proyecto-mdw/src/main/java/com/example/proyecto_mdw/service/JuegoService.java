package com.example.proyecto_mdw.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.repository.JuegoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JuegoService {

    private static final String JSON_PATH = "src/main/resources/static/data/juegos.json";
    
    @Autowired
    private JuegoRepository juegoRepository;
    
    /**
     * Listar todos los juegos
     */
    public List<Juego> listarTodos() {
        return juegoRepository.findAll();
    }
    
    /**
     * Buscar un juego por su ID
     */
    public Optional<Juego> buscarPorId(Long id) {
        return juegoRepository.findById(id);
    }
    
    /**
     * Guardar un juego (crear o actualizar) y actualizar el JSON
     */
    @Transactional
    public Juego guardar(Juego juego) {
        // Guardar en la base de datos
        Juego juegoGuardado = juegoRepository.save(juego);
        
        // Actualizar automáticamente el archivo JSON
        actualizarJSON();
        
        return juegoGuardado;
    }
    
    /**
     * Eliminar un juego por su ID y actualizar el JSON
     */
    @Transactional
    public void eliminar(Long id) {
        // Eliminar de la base de datos
        juegoRepository.deleteById(id);
        
        // Actualizar automáticamente el archivo JSON
        actualizarJSON();
    }
    
    /**
     * Actualizar el archivo JSON con los datos de la base de datos
     */
    @Transactional
    public void actualizarJSON() {
        try {
            // Obtenemos todos los juegos de la base de datos
            List<Juego> juegos = juegoRepository.findAll();
            
            // Creamos el mapper con indentación para mejor legibilidad
            ObjectMapper mapper = new ObjectMapper();
            
            // Creamos el directorio si no existe
            Path directoryPath = Paths.get(JSON_PATH).getParent();
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            
            // Escribimos la lista al archivo JSON con formato bonito
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_PATH), juegos);
            
            System.out.println("✅ Archivo juegos.json actualizado correctamente: " + juegos.size() + " juegos guardados.");
        } catch (IOException e) {
            System.err.println("❌ Error al actualizar el archivo JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Cargar juegos desde el archivo JSON
     */
    @Transactional
    public void cargarDesdeJSON() {
        try {
            Path path = Paths.get(JSON_PATH);
            if (!Files.exists(path)) {
                System.out.println("⚠️ El archivo juegos.json no existe: " + JSON_PATH);
                return;
            }
            
            // Leemos el archivo JSON
            ObjectMapper mapper = new ObjectMapper();
            List<Juego> juegos = mapper.readValue(path.toFile(), 
                    mapper.getTypeFactory().constructCollectionType(List.class, Juego.class));
            
            // Guardamos en la base de datos
            for (Juego juego : juegos) {
                juegoRepository.save(juego);
            }
            
            System.out.println("✅ Juegos cargados desde juegos.json a la base de datos: " + juegos.size() + " juegos.");
        } catch (IOException e) {
            System.err.println("❌ Error al cargar juegos desde JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}