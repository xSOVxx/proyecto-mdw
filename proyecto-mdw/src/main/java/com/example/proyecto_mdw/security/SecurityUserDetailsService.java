package com.example.proyecto_mdw.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.proyecto_mdw.model.Usuario;
import com.example.proyecto_mdw.repository.UsuarioRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Intentando autenticar usuario: " + username);
        Usuario user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("Usuario no encontrado: " +
                            username);
                    return new UsernameNotFoundException("Usuario no encontrado: " + username);
                });
        System.out.println("Usuario encontrado: " + username);
       return new SecurityUserDetails(user);


    }
}
