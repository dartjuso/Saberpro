package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Facultad;
import com.example.demo.repository.FacultadRepository;

@Service
public class FacultadService {

    @Autowired
    private FacultadRepository facultadRepository;

    public List<Facultad> obtenerTodas() {
        return facultadRepository.findAll();
    }

    public Optional<Facultad> buscarPorId(Long id) {
        return facultadRepository.findById(id);
    }

    public Facultad guardar(Facultad facultad) {
        return facultadRepository.save(facultad);
    }

    public void eliminar(Long id) {
        facultadRepository.deleteById(id);
    }
}