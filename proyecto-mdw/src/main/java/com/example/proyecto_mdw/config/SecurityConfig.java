package com.example.proyecto_mdw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.proyecto_mdw.security.CustomSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
                .disable()
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/", "/register", "/login", "/css/**", "/js/**", "/juegos", "/ranking" ).permitAll()
                .requestMatchers("/admin/**").hasRole("GESTORJUEGOS")
                .requestMatchers( "/perfil").hasRole("USER")
                .anyRequest().hasRole("USER")
                
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
            )
            .formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("correo")            // Campo de formulario
                .passwordParameter("password")        // Campo de formulario
                .successHandler(successHandler)         // Usar tu CustomSuccessHandler
                .permitAll()
            )
            .logout(logout -> logout
                .permitAll()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/error/403")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

