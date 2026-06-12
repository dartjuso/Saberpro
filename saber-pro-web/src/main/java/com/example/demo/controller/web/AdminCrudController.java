package com.example.demo.controller.web;

import com.example.demo.entity.Estudiante;
import com.example.demo.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminCrudController {

    @Autowired
    private EstudianteService estudianteService;

    // 1. Mostrar formulario vacío (CREAR)
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("titulo", "Registrar Nuevo Estudiante");
        return "admin/estudiante_form";
    }

    // 2. Mostrar formulario con datos (EDITAR)
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Estudiante est = estudianteService.obtenerTodos().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
                
        model.addAttribute("estudiante", est);
        model.addAttribute("titulo", "Editar Estudiante");
        return "admin/estudiante_form";
    }

    // 3. Guardar en Base de Datos (Sirve para Crear y Editar)
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Estudiante estudiante, RedirectAttributes redirect) {
        // Si no le ponen contraseña, le asignamos la por defecto
        if (estudiante.getPassword() == null || estudiante.getPassword().isEmpty()) {
            estudiante.setPassword("12345");
        }
        
        estudianteService.guardar(estudiante);
        redirect.addFlashAttribute("exito", "Usuario guardado exitosamente.");
        
        // Redirigimos a la lista general de estudiantes (ajústalo si tu ruta es otra)
        return "redirect:/estudiantes"; 
    }

    // 4. Eliminar Usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id, RedirectAttributes redirect) {
        // NOTA: Asegúrate de tener este método en tu EstudianteService
        estudianteService.eliminar(id); 
        redirect.addFlashAttribute("exito", "Usuario eliminado del sistema.");
        return "redirect:/estudiantes";
    }
    
 // 5. Eliminar TODOS los usuarios (Resetear BD)
    @GetMapping("/eliminar-todos")
    public String eliminarTodos(RedirectAttributes redirect) {
        estudianteService.eliminarTodos();
        redirect.addFlashAttribute("exito", "¡Peligro! Toda la base de datos ha sido borrada exitosamente.");
        // Redirige al dashboard del admin (Ajusta la ruta si tu dashboard principal es otro)
        return "redirect:/"; 
    }
}