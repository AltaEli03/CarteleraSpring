package com.cartelera.spring.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.cartelera.spring.entity.Pelicula;
import com.cartelera.spring.repository.PeliculaRepository;

@Controller
public class HomeController {

    @Autowired
    private PeliculaRepository peliculaRepository;

    @GetMapping("/home")
    public String home(Model model, Authentication authentication,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(defaultValue = "0") int page) {

        if (logout != null) {
            model.addAttribute("logout", "Sesión cerrada exitosamente.");
        }

        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("role", authentication.getAuthorities().iterator().next().getAuthority());
        }

        Pageable pageable = PageRequest.of(page, 8);
        Page<Pelicula> peliculasPage = peliculaRepository.findByEstadoTrue(pageable);
        model.addAttribute("peliculas", peliculasPage.getContent());
        model.addAttribute("totalPeliculas", peliculasPage.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", peliculasPage.getTotalPages());

        return "home";
    }

    @GetMapping("/pelicula/{id}")
    public String verDetallesPelicula(@PathVariable ObjectId id, Model model, Authentication authentication) {
        Pelicula pelicula = peliculaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        model.addAttribute("pelicula", pelicula);

        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
            model.addAttribute("role", authentication.getAuthorities().iterator().next().getAuthority());
        }
        return "user/pelicula-detalles";
    }

    @GetMapping("/search")
    public String buscarPeliculas(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
    
        Pageable pageable = PageRequest.of(page, 8);
        Page<Pelicula> peliculasPage;
    
        // Validar los criterios de búsqueda y llamar al método adecuado
        if (title != null && !title.isEmpty() && genre != null && !genre.isEmpty()) {
            peliculasPage = peliculaRepository.findByNombreContainingIgnoreCaseAndGeneroContainingIgnoreCase(title, genre, pageable);
        } else if (title != null && !title.isEmpty()) {
            peliculasPage = peliculaRepository.findByNombreContainingIgnoreCase(title, pageable);
        } else if (genre != null && !genre.isEmpty()) {
            peliculasPage = peliculaRepository.findByGeneroContainingIgnoreCase(genre, pageable);
        } else {
            peliculasPage = peliculaRepository.findByEstadoTrue(pageable);
        }
        
        model.addAttribute("peliculas", peliculasPage.getContent());
        model.addAttribute("totalPeliculas", peliculasPage.getTotalElements());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", peliculasPage.getTotalPages());
        
        return "fragments/peliculas-grid :: #movieGrid";
    }
    
}