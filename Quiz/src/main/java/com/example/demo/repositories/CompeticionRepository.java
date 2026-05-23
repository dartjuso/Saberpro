package com.example.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Competicion;

@Repository
public interface CompeticionRepository extends JpaRepository<Competicion, Long> {
}