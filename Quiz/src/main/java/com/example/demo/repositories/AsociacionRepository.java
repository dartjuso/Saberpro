package com.example.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Asociacion;

@Repository
public interface AsociacionRepository extends JpaRepository<Asociacion, Long> {
}