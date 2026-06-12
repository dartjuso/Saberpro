package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Estudiante;
import com.example.demo.repository.EstudianteRepository;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    // Obtener todos los estudiantes para mostrarlos en la tabla del Dashboard
    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }

    // Buscar un estudiante por su cédula o tarjeta de identidad
    public Optional<Estudiante> buscarPorDocumento(String documento) {
        return estudianteRepository.findByDocumento(documento);
    }

    // Buscar por el código EK (Ej: EK20183007722)
    public Optional<Estudiante> buscarPorNumeroRegistro(String numeroRegistro) {
        return estudianteRepository.findByNumeroRegistro(numeroRegistro);
    }

    // Guardar o actualizar un estudiante en la base de datos
    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // --- MÉTODOS NUEVOS AGREGADOS ---

    // Eliminar un estudiante de la base de datos (¡El que causaba el error!)
    public void eliminar(Long id) {
        estudianteRepository.deleteById(id);
    }
    
    public void eliminarTodos() {
        estudianteRepository.deleteAll();
    }

    // Buscar un estudiante por su ID interno de la base de datos
    public Optional<Estudiante> buscarPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    // Buscar todos los estudiantes que pertenezcan a una Facultad específica (Para el filtro del Docente)
    public List<Estudiante> buscarPorFacultad(Long idFacultad) {
        return estudianteRepository.findByFacultadId(idFacultad);
    }
}