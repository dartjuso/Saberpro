package com.example.demo.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Estudiante;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteApiController {

    // TODO: Inyectar EstudianteService en el siguiente paso

    @GetMapping
    public ResponseEntity<List<Estudiante>> listarTodos() {
        // Por ahora devolvemos una lista vacía.
        // Luego la cambiaremos por: return ResponseEntity.ok(estudianteService.obtenerTodos());
        List<Estudiante> estudiantes = new ArrayList<>();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{documento}")
    public ResponseEntity<Estudiante> buscarPorDocumento(@PathVariable String documento) {
        // Estructura lista para buscar un estudiante específico por su cédula o TI
        return ResponseEntity.notFound().build();
    }
}