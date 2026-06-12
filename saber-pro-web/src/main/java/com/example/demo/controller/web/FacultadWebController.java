package com.example.demo.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Facultad;
import com.example.demo.service.FacultadService;

@Controller
@RequestMapping("/facultades")
public class FacultadWebController {

    @Autowired
    private FacultadService facultadService;

    // Mostrar la lista y el formulario de creación
    @GetMapping
    public String listarFacultades(Model model) {
        model.addAttribute("facultades", facultadService.obtenerTodas());
        model.addAttribute("nuevaFacultad", new Facultad()); // Objeto vacío para el formulario
        return "facultades/lista";
    }

    // Guardar una nueva facultad
    @PostMapping("/guardar")
    public String guardarFacultad(@ModelAttribute Facultad facultad, RedirectAttributes redirectAttributes) {
        facultadService.guardar(facultad);
        redirectAttributes.addFlashAttribute("exito", "Facultad guardada correctamente.");
        return "redirect:/facultades";
    }

    // Eliminar una facultad
    @GetMapping("/eliminar/{id}")
    public String eliminarFacultad(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            facultadService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Facultad eliminada.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se puede eliminar la facultad porque tiene registros asociados.");
        }
        return "redirect:/facultades";
    }
}