package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Facultad;

public interface FacultadRepository extends JpaRepository<Facultad, Long> {
}