package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Beneficio;

public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {
}