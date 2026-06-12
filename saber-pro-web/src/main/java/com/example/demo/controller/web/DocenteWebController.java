package com.example.demo.controller.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Docente;
import com.example.demo.entity.Estudiante;
import com.example.demo.entity.Facultad;
import com.example.demo.service.DocenteService;
import com.example.demo.service.EstudianteService;
import com.example.demo.service.FacultadService;

@Controller
@RequestMapping("/docentes")
public class DocenteWebController {

    @Autowired
    private DocenteService docenteService;

    @Autowired
    private FacultadService facultadService;

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public String listarDocentes(Model model) {
        model.addAttribute("docentes", docenteService.obtenerTodos());
        model.addAttribute("facultades", facultadService.obtenerTodas());

        Docente nuevoDocente = new Docente();
        nuevoDocente.setFacultad(new Facultad());
        model.addAttribute("nuevoDocente", nuevoDocente);

        return "docentes/lista";
    }

    @GetMapping("/dashboard")
    public String dashboardDocente(@RequestParam(value = "documento", required = false) String documento,
                                   @RequestParam(value = "facultadId", required = false) Long facultadId,
                                   Model model) {

        List<Estudiante> lista;

        if (documento != null && !documento.isEmpty()) {
            lista = estudianteService.buscarPorDocumento(documento).stream().toList();
        } else if (facultadId != null) {
            lista = estudianteService.buscarPorFacultad(facultadId);
        } else {
            lista = estudianteService.obtenerTodos();
        }

        model.addAttribute("estudiantes", lista);
        model.addAttribute("facultades", facultadService.obtenerTodas());
        model.addAttribute("titulo", "Dashboard Docente");
        return "docentes/dashboard";
    }

    @PostMapping("/guardar")
    public String guardarDocente(@ModelAttribute Docente docente, RedirectAttributes redirectAttributes) {
        docenteService.guardar(docente);
        redirectAttributes.addFlashAttribute("exito", "Docente registrado correctamente.");
        return "redirect:/docentes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarDocente(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            docenteService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Docente eliminado del sistema.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el docente.");
        }
        return "redirect:/docentes";
    }

    @GetMapping("/informe-alumnos")
    public String informeAlumnos(@RequestParam(value = "documento", required = false) String documento, Model model) {
        if (documento != null && !documento.isEmpty()) {
            model.addAttribute("estudiantes", estudianteService.buscarPorDocumento(documento).stream().toList());
        } else {
            model.addAttribute("estudiantes", estudianteService.obtenerTodos());
        }
        return "docentes/informe_alumnos";
    }

    @GetMapping("/resolucion-beneficios")
    public String resolucionBeneficios(Model model) {
        List<Estudiante> beneficiados = estudianteService.obtenerTodos().stream()
            .filter(e -> e.getFacultad() != null && (e.getFacultad().getNombre().toLowerCase().contains("ingeniería") || e.getFacultad().getNombre().toLowerCase().contains("tecnología")))
            .filter(e -> e.getResultado() != null && e.getResultado().getPuntajeGlobal() != null && e.getResultado().getPuntajeGlobal() >= 160)
            .collect(Collectors.toList());

        model.addAttribute("beneficiados", beneficiados);
        return "docentes/resolucion_beneficios";
    }
}