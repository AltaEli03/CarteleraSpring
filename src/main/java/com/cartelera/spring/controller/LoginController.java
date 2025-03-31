package com.cartelera.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {
        if (error != null) {
            if (error.equals("credentials")) {
                model.addAttribute("error", "Credenciales inválidas. Por favor, verifica tu usuario y contraseña.");
            } else if (error.equals("system")) {
                model.addAttribute("error", "Hubo un problema con el sistema. Por favor, inténtalo más tarde.");
            } else if (error.equals("session")) {
                model.addAttribute("error", "Hubo un problema con tu sesion. Por favor, ingresa tus credenciales otra vez.");
            }
        }
        return "login";
    }

}
