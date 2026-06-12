package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Necesario para el Login de Spring Security
    Optional<Usuario> findByUsername(String username);
}