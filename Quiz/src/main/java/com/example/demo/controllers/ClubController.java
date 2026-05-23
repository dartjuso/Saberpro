package com.example.demo.controllers;

import com.example.demo.entities.Club;
import com.example.demo.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clubes")
public class ClubController {

    private final ClubRepository clubRepo;
    private final EntrenadorRepository entrenadorRepo;
    private final AsociacionRepository asociacionRepo;

    public ClubController(ClubRepository clubRepo, EntrenadorRepository entrenadorRepo, AsociacionRepository asociacionRepo) {
        this.clubRepo = clubRepo;
        this.entrenadorRepo = entrenadorRepo;
        this.asociacionRepo = asociacionRepo;
    }

    // LISTAR
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clubes", clubRepo.findAll());
        return "clubes/index";
    }

    // FORMULARIO CREAR
    @GetMapping("/nuevo")
    public String formularioCrear(Model model) {
        model.addAttribute("club", new Club());
        model.addAttribute("entrenadores", entrenadorRepo.findAll());
        model.addAttribute("asociaciones", asociacionRepo.findAll());
        return "clubes/form";
    }

    // GUARDAR (CREAR O ACTUALIZAR)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Club club) {
        clubRepo.save(club);
        return "redirect:/clubes";
    }

    // FORMULARIO EDITAR
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Club club = clubRepo.findById(id).orElseThrow();
        model.addAttribute("club", club);
        model.addAttribute("entrenadores", entrenadorRepo.findAll());
        model.addAttribute("asociaciones", asociacionRepo.findAll());
        return "clubes/form";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        clubRepo.deleteById(id);
        return "redirect:/clubes";
    }
}