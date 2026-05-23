package com.example.demo.controllers;

import com.example.demo.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataController {

    private final ClubRepository clubRepository;
    private final JugadorRepository jugadorRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final AsociacionRepository asociacionRepository;
    private final CompeticionRepository competicionRepository;

    public DataController(ClubRepository clubRepository, JugadorRepository jugadorRepository, 
                          EntrenadorRepository entrenadorRepository, AsociacionRepository asociacionRepository, 
                          CompeticionRepository competicionRepository) {
        this.clubRepository = clubRepository;
        this.jugadorRepository = jugadorRepository;
        this.entrenadorRepository = entrenadorRepository;
        this.asociacionRepository = asociacionRepository;
        this.competicionRepository = competicionRepository;
    }

    @GetMapping("/dashboard")
    public String verDashboard(Model model) {
        // Enviamos todas las listas de la BD a la página
        model.addAttribute("clubes", clubRepository.findAll());
        model.addAttribute("jugadores", jugadorRepository.findAll());
        model.addAttribute("entrenadores", entrenadorRepository.findAll());
        model.addAttribute("asociaciones", asociacionRepository.findAll());
        model.addAttribute("competiciones", competicionRepository.findAll());
        return "dashboard"; // Nombre del archivo HTML en templates
    }
}