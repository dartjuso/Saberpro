package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Director;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}