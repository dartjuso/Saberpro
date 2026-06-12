package com.example.demo.service;

import com.example.demo.entity.Estudiante;
import com.example.demo.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 1. Lógica para Admin y Coordinador (Texto plano, totalmente estático)
        if ("admin".equals(username)) {
            return new User("admin", "admin123", 
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        if ("coor".equals(username)) {
            return new User("coor", "coor123", 
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_COORDINADOR")));
        }
     // ... (lógica del admin y coor)

        if ("docente".equals(username)) {
            return new User("docente", "doc123", 
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_DOCENTE")));
        }

        // 2. Lógica para Estudiantes (Buscando en tu tabla de H2)
        Estudiante est = estudianteRepository.findByDocumento(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Retorna el estudiante con su contraseña en texto plano ("12345")
        return new User(
            est.getDocumento(), 
            est.getPassword(), 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ESTUDIANTE"))
        );
    }
}