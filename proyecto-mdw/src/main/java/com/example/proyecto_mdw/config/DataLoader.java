package com.example.proyecto_mdw.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.proyecto_mdw.model.Juego;
import com.example.proyecto_mdw.model.RankingItem;
import com.example.proyecto_mdw.repository.JuegoRepository;
import com.example.proyecto_mdw.repository.RankingRepository;

@Component
@Profile("!production") // Solo se ejecuta en perfiles que no sean producción
public class DataLoader implements CommandLineRunner {

    @Autowired
    private JuegoRepository juegoRepository;
    
    @Autowired
    private RankingRepository rankingRepository;

    @Override
    public void run(String... args) throws Exception {
        // Cargar juegos solo si la base de datos está vacía
        cargarJuegos();
        
        // Cargar ranking solo si la tabla de ranking está vacía
        cargarRanking();
    }
    
    private void cargarJuegos() {
        if (juegoRepository.count() == 0) {
            // Crear datos de prueba directamente
            List<Juego> juegos = new ArrayList<>();
            
            // Juego 1
            Juego fortnite = new Juego();
            fortnite.setNombre("Fortnite");
            fortnite.setDescripcion("Juego de batalla campal con elementos de construcción donde 100 jugadores luchan para ser el último en pie.");
            fortnite.setImagen("https://fn.gg/img/social-1200x630.jpg");
            fortnite.setPrecio(0);
            fortnite.setCategoria("Acción");
            fortnite.setPlataformas(Arrays.asList("PC", "PlayStation 5", "Xbox Series X|S", "Nintendo Switch"));
            fortnite.setFechaLanzamiento("2017-07-21");
            juegos.add(fortnite);
            
            // Juego 2
            Juego valorant = new Juego();
            valorant.setNombre("Valorant");
            valorant.setDescripcion("Juego de disparos táctico 5v5 basado en personajes con habilidades especiales y precisión de disparo.");
            valorant.setImagen("https://images.contentstack.io/v3/assets/bltb6530b271fddd0b1/blt3f072336e3f3ade4/63464a7ac890a1903b3f4701/Valorant_2022_E5A3_PlayVALORANT_ContentStackThumbnail_1200x625_MB01.png");
            valorant.setPrecio(0);
            valorant.setCategoria("Acción");
            valorant.setPlataformas(Arrays.asList("PC"));
            valorant.setFechaLanzamiento("2020-06-02");
            juegos.add(valorant);
            
            // Juego 3
            Juego minecraft = new Juego();
            minecraft.setNombre("Minecraft");
            minecraft.setDescripcion("Juego de construcción y aventuras en un mundo abierto generado proceduralmente con infinitas posibilidades.");
            minecraft.setImagen("https://www.minecraft.net/content/dam/games/minecraft/key-art/MC_2019Badge_800x800.jpg");
            minecraft.setPrecio(29.99);
            minecraft.setCategoria("Aventura");
            minecraft.setPlataformas(Arrays.asList("PC", "PlayStation 5", "Xbox Series X|S", "Nintendo Switch"));
            minecraft.setFechaLanzamiento("2011-11-18");
            juegos.add(minecraft);

            // Guardar todos los juegos
            juegoRepository.saveAll(juegos);
            
            System.out.println("✅ Juegos iniciales cargados en la base de datos");
        } else {
            System.out.println("ℹ️ La base de datos de juegos ya contiene datos");
        }
    }
    
    private void cargarRanking() {
        if (rankingRepository.count() == 0) {
            List<RankingItem> rankingItems = new ArrayList<>();
            
            // Ranking Item 1
            RankingItem rk1 = new RankingItem();
            rk1.setNombre("Minecraft");
            rk1.setDescripcion("Un juego de construcción y supervivencia donde puedes crear mundos virtuales y aventuras.");
            rk1.setImagen("https://www.minecraft.net/content/dam/games/minecraft/key-art/MC_2019Badge_800x800.jpg");
            rk1.setRanking(1);
            rk1.setGenero("Sandbox");
            rk1.setPlataforma(Arrays.asList("PC", "PlayStation", "Xbox", "Nintendo Switch", "Mobile"));
            rk1.setLanzamiento("2011");
            rk1.setCalificacion(4.8);
            rk1.setEnlace("https://www.minecraft.net/");
            rankingItems.add(rk1);
            
            // Ranking Item 2
            RankingItem rk2 = new RankingItem();
            rk2.setNombre("Fortnite");
            rk2.setDescripcion("Battle royale gratuito donde 100 jugadores luchan para ser el último en pie.");
            rk2.setImagen("https://fn.gg/img/social-1200x630.jpg");
            rk2.setRanking(2);
            rk2.setGenero("Battle Royale");
            rk2.setPlataforma(Arrays.asList("PC", "PlayStation", "Xbox", "Nintendo Switch", "Mobile"));
            rk2.setLanzamiento("2017");
            rk2.setCalificacion(4.5);
            rk2.setEnlace("https://www.epicgames.com/fortnite/");
            rankingItems.add(rk2);
            
            // Ranking Item 3
            RankingItem rk3 = new RankingItem();
            rk3.setNombre("League of Legends");
            rk3.setDescripcion("MOBA competitivo donde dos equipos de cinco jugadores se enfrentan para destruir la base enemiga.");
            rk3.setImagen("https://www.leagueoflegends.com/static/open-graph-2e582ae9fae8b0b396ca46ff21fd47a8.jpg");
            rk3.setRanking(3);
            rk3.setGenero("MOBA");
            rk3.setPlataforma(Arrays.asList("PC"));
            rk3.setLanzamiento("2009");
            rk3.setCalificacion(4.3);
            rk3.setEnlace("https://www.leagueoflegends.com/");
            rankingItems.add(rk3);
            
            // Guardar todos los rankings
            rankingRepository.saveAll(rankingItems);
            
            System.out.println("✅ Rankings iniciales cargados en la base de datos");
        } else {
            System.out.println("ℹ️ La base de datos de rankings ya contiene datos");
        }
    }
}