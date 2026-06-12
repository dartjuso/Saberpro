package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Docente;
import com.example.demo.repository.DocenteRepository;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    public List<Docente> obtenerTodos() {
        return docenteRepository.findAll();
    }

    public Docente guardar(Docente docente) {
        return docenteRepository.save(docente);
    }

    public void eliminar(Long id) {
        docenteRepository.deleteById(id);
    }
}