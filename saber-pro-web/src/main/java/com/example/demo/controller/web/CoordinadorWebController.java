package com.example.demo.controller.web;

import com.example.demo.entity.Estudiante;
import com.example.demo.entity.Resultado; // <-- Aquí está el import que faltaba
import com.example.demo.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/coordinador")
public class CoordinadorWebController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping("/dashboard")
    public String coordDashboard(Model model) { 
        model.addAttribute("titulo", "Panel del Coordinador");
        return "coordinador/dashboard"; 
    }

    // --- MÓDULO DE GESTIÓN Y CALIFICACIÓN ---
    
    // Formulario para ingresar notas
    @GetMapping("/calificar/{id}")
    public String mostrarFormularioCalificacion(@PathVariable("id") Long id, Model model) {
        Estudiante est = estudianteService.obtenerTodos().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
                
        model.addAttribute("estudiante", est);
        model.addAttribute("titulo", "Calificar Estudiante");
        return "coordinador/calificar"; 
    }

 // Guardar las notas enviadas por el coordinador
    @PostMapping("/guardar-notas/{id}")
    public String guardarNotas(@PathVariable("id") Long id, 
                               @RequestParam("puntajeLectura") Integer puntajeLectura,
                               @RequestParam("puntajeIngles") Integer puntajeIngles,
                               @RequestParam("puntajeRazonamiento") Integer puntajeRazonamiento,
                               RedirectAttributes redirect) {
        
        // Buscamos el estudiante
        Estudiante est = estudianteService.obtenerTodos().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        
        // Si no tiene resultado, se lo creamos
        if (est.getResultado() == null) {
            est.setResultado(new Resultado());
        }
        
        // Calculamos el promedio global en el backend
        int puntajeCalculado = Math.round((puntajeLectura + puntajeIngles + puntajeRazonamiento) / 3.0f);
        
        // Guardamos todos los puntajes
        est.getResultado().setPuntajeGlobal(puntajeCalculado);
        est.getResultado().setPuntajeLectura(puntajeLectura);
        est.getResultado().setPuntajeIngles(puntajeIngles);
        est.getResultado().setPuntajeRazonamiento(puntajeRazonamiento);
        
        // Marcamos la prueba como presentada
        est.setPruebaPresentada(true);

        // --- LA MAGIA: Asignamos el beneficio INMEDIATAMENTE ---
        if (puntajeCalculado >= 170) {
            est.setBeneficio("Exoneración del 100% en Derechos de Grado + Beca de Postgrado Institucional");
        } else if (puntajeCalculado >= 150) {
            est.setBeneficio("Exoneración del 50% en Derechos de Grado de Pregrado");
        } else {
            est.setBeneficio("Mención de Reconocimiento por Rendimiento Académico");
        }
        
        // Guardamos todo de un solo golpe
        estudianteService.guardar(est);
        
        redirect.addFlashAttribute("exito", "Calificaciones guardadas y beneficio asignado exitosamente.");
        return "redirect:/coordinador/estudiantes";
    }
    
    // Aprobar estudiante
    @GetMapping("/aprobar/{id}")
    public String aprobarEstudiante(@PathVariable("id") Long id, RedirectAttributes redirect) {
        Estudiante est = estudianteService.obtenerTodos().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        
        est.setAprobado(true);
        estudianteService.guardar(est);
        redirect.addFlashAttribute("exito", "Estudiante aprobado correctamente.");
        return "redirect:/estudiantes";
    }

    // --- MÓDULO DE INFORMES Y BENEFICIOS ---

    @GetMapping("/informes")
    public String verInformes(@RequestParam(value = "buscar", required = false) String buscar, Model model) {
        model.addAttribute("titulo", "Asignación de Beneficios");
        
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();
        
        if (buscar != null && !buscar.trim().isEmpty()) {
            String query = buscar.toLowerCase().trim();
            estudiantes = estudiantes.stream()
                .filter(e -> (e.getPrimerNombre() != null && e.getPrimerNombre().toLowerCase().contains(query)) ||
                             (e.getPrimerApellido() != null && e.getPrimerApellido().toLowerCase().contains(query)))
                .toList();
        }
        
        model.addAttribute("estudiantes", estudiantes);
        model.addAttribute("buscar", buscar);
        return "coordinador/informes";
    }

    @GetMapping("/beneficios/{id}")
    public String verCardBeneficio(@PathVariable("id") Long id, Model model) {
        Estudiante est = estudianteService.obtenerTodos().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        // Lógica de asignación de beneficios
        if (est.getBeneficio() == null || est.getBeneficio().trim().isEmpty()) {
            
            // <-- CORRECCIÓN AQUÍ: Leemos el puntaje global del objeto Resultado
            int puntaje = (est.getResultado() != null && est.getResultado().getPuntajeGlobal() != null) 
                          ? est.getResultado().getPuntajeGlobal() : 0;
            
            if (puntaje >= 170) {
                est.setBeneficio("Exoneración del 100% en Derechos de Grado + Beca de Postgrado Institucional");
            } else if (puntaje >= 150) {
                est.setBeneficio("Exoneración del 50% en Derechos de Grado de Pregrado");
            } else {
                est.setBeneficio("Mención de Reconocimiento por Rendimiento Académico");
            }
            estudianteService.guardar(est);
        }

        model.addAttribute("estudiante", est);
        model.addAttribute("titulo", "Tarjeta de Beneficio de Estudiante");
        return "coordinador/beneficio_card";
    }

    public EstudianteService getEstudianteService() {
        return estudianteService;
    }

    public void setEstudianteService(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }
    
 // Mostrar la lista de estudiantes para el coordinador
    @GetMapping("/estudiantes")
    public String gestionarEstudiantes(Model model) {
        model.addAttribute("titulo", "Gestión de Estudiantes");
        // Mandamos la lista de todos los estudiantes a la vista
        model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        return "coordinador/lista"; // El nombre de tu archivo HTML de la lista
    }
}