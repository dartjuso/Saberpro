package com.example.demo.controller.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // Necesario para obtener el usuario logueado
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Estudiante;
import com.example.demo.service.EstudianteService;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteWebController {

    @Autowired
    private EstudianteService estudianteService;

    // 1. Listado general
    @GetMapping
    public String listar(Model model) {
        List<Estudiante> todos = estudianteService.obtenerTodos();
        // Filtramos por si acaso hay algún objeto nulo en la lista
        model.addAttribute("estudiantes", todos.stream().filter(e -> e != null).toList());
        return "estudiantes/lista";
    }

    // 2. Detalle individual
    @GetMapping("/{id}")
    public String detalle(@PathVariable("id") Long id, Model model) {
        model.addAttribute("estudiante", estudianteService.obtenerTodos().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst().orElse(null));
        return "estudiantes/detalle";
    }

    // 3. Eliminar Estudiante
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        estudianteService.eliminar(id);
        redirectAttributes.addFlashAttribute("exito", "Estudiante eliminado correctamente.");
        return "redirect:/estudiantes";
    }

    // 4. Aprobar / Quitar Aprobación (Toggle)
    @GetMapping("/aprobar/{id}")
    public String toggleAprobacion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Optional<Estudiante> opt = estudianteService.obtenerTodos().stream().filter(e -> e.getId().equals(id)).findFirst();
        if (opt.isPresent()) {
            Estudiante est = opt.get();
            est.setAprobado(est.getAprobado() == null ? true : !est.getAprobado()); 
            estudianteService.guardar(est); 
            redirectAttributes.addFlashAttribute("exito", "Estado de aprobación actualizado para " + est.getPrimerNombre());
        }
        return "redirect:/estudiantes";
    }

    // 5. Informe General de Alumnos
    @GetMapping("/informe")
    public String informe(Model model) {
        List<Estudiante> todos = estudianteService.obtenerTodos();
        long total = todos.size();
        long aprobados = todos.stream().filter(e -> e.getAprobado() != null && e.getAprobado()).count();
        long pendientes = total - aprobados;

        double promedio = todos.stream()
            .mapToInt(e -> (e.getResultado() != null && e.getResultado().getPuntajeGlobal() != null) ? e.getResultado().getPuntajeGlobal() : 0)
            .average().orElse(0.0);

        model.addAttribute("total", total);
        model.addAttribute("aprobados", aprobados);
        model.addAttribute("pendientes", pendientes);
        model.addAttribute("promedio", Math.round(promedio * 100.0) / 100.0);
        return "estudiantes/informe";
    }
    
 // 1. Subir Pago
    @PostMapping("/subir-pago")
    public String subirPago(@RequestParam("urlPago") String urlPago, Authentication auth, RedirectAttributes redirectAttributes) {
        // Obtenemos el username del usuario logueado
        String username = auth.getName(); 
        Estudiante est = estudianteService.buscarPorDocumento(username)
                         .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        
        // Guardamos la URL
        est.setUrlPago(urlPago);
        
        // IMPORTANTE: Si quieres que el paso 2 se desbloquee automáticamente, descomenta esta línea:
        est.setPagoAprobado(true); 
        
        estudianteService.guardar(est);
        
        redirectAttributes.addFlashAttribute("exito", "Pago subido correctamente.");
        return "redirect:/estudiantes/perfil"; // <-- CORREGIDO: Vuelve a cargar el perfil
    }
    
    // 2. Marcar la prueba como presentada
    @PostMapping("/presentar")
    public String presentarPrueba(Authentication auth, RedirectAttributes redirect) {
        String username = auth.getName(); 
        
        // CORREGIDO: Faltaba el orElseThrow para extraer el estudiante del Optional
        Estudiante est = estudianteService.buscarPorDocumento(username)
                         .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        
        est.setPruebaPresentada(true);
        estudianteService.guardar(est);
        
        redirect.addFlashAttribute("exito", "¡Excelente! Has registrado la presentación de tu prueba.");
        
        return "redirect:/estudiantes/perfil";
    }
    
    @GetMapping("/perfil")
    public String perfil(Authentication auth, Model model, RedirectAttributes redirectAttributes) {
        String username = auth.getName(); 
        
        Optional<Estudiante> opt = estudianteService.buscarPorDocumento(username);
        
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Perfil no encontrado");
            return "redirect:/";
        }
        
        model.addAttribute("estudiante", opt.get());
        return "estudiantes/perfil"; // Asegúrate que el archivo existe en templates/estudiantes/perfil.html
    }
}