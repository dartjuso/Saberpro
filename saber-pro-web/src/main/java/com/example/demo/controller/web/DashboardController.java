package com.example.demo.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.EstudianteService;
import com.example.demo.service.ImportadorCsvService;

@Controller
@RequestMapping("/dashboard") // <--- Cambiado de '/' a '/dashboard' para evitar el conflicto
public class DashboardController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private ImportadorCsvService importadorCsvService;

    @GetMapping("") // Ahora responde a /dashboard
    public String index(Model model, Authentication authentication, HttpServletRequest request) {
        // Si no hay sesión, dejamos que Spring Security maneje el login
        if (authentication == null) {
            return "redirect:/login";
        }

        String currentPath = request.getRequestURI();

        // --- LÓGICA DE REDIRECCIÓN INTELIGENTE (TOTALMENTE INTACTA) ---
        
        // 1. Docentes
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCENTE"))) {
            if (!currentPath.equals("/docentes/dashboard")) return "redirect:/docentes/dashboard";
        }
        
        // 2. Estudiantes
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ESTUDIANTE"))) {
            if (!currentPath.equals("/estudiantes/perfil")) return "redirect:/estudiantes/perfil";
        }

        // 3. Administradores
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            if (!currentPath.equals("/admin/dashboard")) return "redirect:/admin/dashboard";
        }

        // 4. Coordinadores
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR"))) {
            if (!currentPath.equals("/coordinador/dashboard")) return "redirect:/coordinador/dashboard";
        }

        model.addAttribute("titulo", "Panel de Control - Saber Pro UTS");
        model.addAttribute("totalEstudiantes", estudianteService.obtenerTodos().size());
        
        return "dashboard/index"; 
    }

    // --- LÓGICA DE IMPORTACIÓN CSV (INTACTA) ---
    @PostMapping("/importar")
    public String importarCsv(@RequestParam(value = "archivo") MultipartFile archivo, RedirectAttributes redirectAttributes) {
        if (archivo.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Por favor selecciona un archivo CSV válido.");
            return "redirect:/dashboard"; // Redirigido al nuevo path
        }

        try {
            importadorCsvService.procesarArchivoCsv(archivo);
            redirectAttributes.addFlashAttribute("exito", "¡Archivo procesado y estudiantes guardados con éxito!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al procesar el archivo: " + e.getMessage());
        }

        return "redirect:/dashboard"; // Redirigido al nuevo path
    }
}