package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Resultado;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
}