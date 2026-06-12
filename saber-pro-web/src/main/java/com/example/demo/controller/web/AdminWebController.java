package com.example.demo.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    // Ahora la ruta real es /admin/dashboard
    @GetMapping("/dashboard")
    public String adminDashboard() { 
        return "admin/dashboard"; 
    }

    // Ahora la ruta real es /admin/facultades
    @GetMapping("/facultades")
    public String gestionarFacultades() { 
        return "admin/facultades"; 
    }

    // Ahora la ruta real es /admin/upload-csv
    @PostMapping("/upload-csv")
    public String subirCsv() {
        // Lógica de lectura de archivo CSV
        return "redirect:/admin/dashboard";
    }
}