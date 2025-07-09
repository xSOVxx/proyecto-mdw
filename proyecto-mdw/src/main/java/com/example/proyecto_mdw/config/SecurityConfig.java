package com.example.proyecto_mdw.config;


//se traen bibliotecas nescesarias (Spring, SpringSecurity)
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher; 

@Configuration //anotacion que le dice a spring que busque beans en esta clase
@EnableWebSecurity //enciende spring security
public class SecurityConfig {

    @Bean //metodo que crea objeto de la clase BCryptPasswordEncoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
        //configura las reglas de seguridad http para la app web
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() 
            )
            .formLogin(form -> form
                .disable() 
            )
            .logout(logout -> logout
                .permitAll() 
            );

        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        http.csrf(csrf -> csrf
            .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
            .ignoringRequestMatchers(new AntPathRequestMatcher("/soporte/enviar")) 
        );


        return http.build(); //convierte todo en filtro de seguridad
    }
}