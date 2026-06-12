package com.example.demo.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // 1. Landing Page (La página bonita que acabamos de hacer)
    @GetMapping("/")
    public String index() {
        return "index"; 
    }

    // 2. Formulario de Login
    @GetMapping("/login")
    public String login() {
        return "auth/login"; 
    }
}