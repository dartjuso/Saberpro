package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    // Método personalizado para buscar un rol por su nombre (Ej: "ROLE_ADMIN")
    Optional<Rol> findByNombre(String nombre);
}