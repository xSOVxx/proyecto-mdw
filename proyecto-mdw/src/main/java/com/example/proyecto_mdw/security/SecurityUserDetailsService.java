package com.example.proyecto_mdw.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 

import com.example.proyecto_mdw.model.Usuario;
import com.example.proyecto_mdw.repository.UsuarioRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUserDetailsService.class);

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        logger.info("Intentando autenticar usuario por correo: " + correo);
        Usuario user = userRepository.findByCorreo(correo)
            .orElseThrow(() -> {
                logger.warn("Usuario no encontrado con correo: " + correo);
                return new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
            });
        logger.info("Usuario encontrado: " + correo);
        return new SecurityUserDetails(user);
    }
}
