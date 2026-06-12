package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // Útil para buscar a un estudiante por su cédula o tarjeta de identidad
    Optional<Estudiante> findByDocumento(String documento);

    // Útil para buscar por el número de registro EK (Ej: EK20183007722)
    Optional<Estudiante> findByNumeroRegistro(String numeroRegistro);

    // NUEVO: Útil para filtrar estudiantes por la facultad a la que pertenecen (Módulo Docentes)
    List<Estudiante> findByFacultadId(Long facultadId);
}