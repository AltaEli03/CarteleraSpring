package com.cartelera.spring.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cartelera.spring.entity.Pelicula;
import com.cartelera.spring.repository.PeliculaRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaRepository peliculaRepository;

    // Listar todas las películas
    @GetMapping
    public String listarPeliculas(Model model) {
        model.addAttribute("peliculas", peliculaRepository.findAll());
        return "admin/list";
    }

    // Mostrar el formulario para crear una nueva película
    @GetMapping("/create")
    public String crearPeliculaForm(Model model) {
        model.addAttribute("pelicula", new Pelicula());
        return "admin/create";
    }

    // Guardar una nueva película, validando los datos enviados
    @PostMapping("/save")
    public String guardarPelicula(
            @Valid @ModelAttribute("pelicula") Pelicula pelicula,
            BindingResult result) {

        // Validación personalizada de fechas
        if (pelicula.getFechaInicio() != null && pelicula.getFechaFinal() != null
                && pelicula.getFechaFinal().isBefore(pelicula.getFechaInicio())) {
            result.rejectValue("fechaFinal", "error.fechaFinal",
                    "La fecha final debe ser posterior a la fecha de inicio");
        }

        if (result.hasErrors()) {
            // Se encontraron errores, se retorna al formulario para corregirlos
            return "admin/create";
        }

        if (pelicula.getVideoEmbed() != null) {
            String videoEmbed = pelicula.getVideoEmbed();

            if (videoEmbed.contains("youtube.com/watch")) {
                // Extraer videoId para URLs del formato youtube.com/watch
                String videoId = videoEmbed.split("v=")[1].split("&")[0];
                pelicula.setVideoEmbed("https://www.youtube-nocookie.com/embed/" + videoId);
            } else if (videoEmbed.contains("youtu.be/")) {
                // Extraer videoId para URLs acortadas del formato youtu.be
                String videoId = videoEmbed.split("youtu.be/")[1];
                pelicula.setVideoEmbed("https://www.youtube-nocookie.com/embed/" + videoId);
            } else if (videoEmbed.contains("youtube-nocookie.com/embed/")) {
                // Ya es una URL válida del formato youtube-nocookie
                pelicula.setVideoEmbed(videoEmbed);
            } else {
                // Rechazar si no es una URL válida de YouTube
                result.rejectValue("videoEmbed", "error.videoEmbed",
                        "La URL debe ser de YouTube (youtube.com, youtu.be o youtube-nocookie.com)");
            }
        }

        peliculaRepository.save(pelicula);
        return "redirect:/peliculas";
    }

    // Mostrar los detalles de una película
    @GetMapping("/show/{idmovie}")
    public String mostrarPelicula(@PathVariable ObjectId idmovie, Model model) {
        Pelicula pelicula = peliculaRepository.findById(idmovie)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        model.addAttribute("pelicula", pelicula);
        return "admin/show";
    }

    // Eliminar una película
    @PostMapping("/delete")
    public String eliminarPelicula(@RequestParam ObjectId idmovie) {
        Pelicula pelicula = peliculaRepository.findById(idmovie)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        peliculaRepository.delete(pelicula);
        return "redirect:/peliculas";
    }

    // Mostrar formulario para editar una película
    @GetMapping("/edit/{idmovie}")
    public String editarPeliculaForm(@PathVariable ObjectId idmovie, Model model) {
        Pelicula pelicula = peliculaRepository.findById(idmovie)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));
        model.addAttribute("pelicula", pelicula);
        return "admin/edit";
    }

    // Actualizar una película, validando también los datos enviados
    @PostMapping("/update")
    public String actualizarPelicula(
            @Valid @ModelAttribute("pelicula") Pelicula pelicula,
            BindingResult result) {

        // Validación personalizada de fechas
        if (pelicula.getFechaFinal().isBefore(pelicula.getFechaInicio())) {
            result.rejectValue("fechaFinal", "error.fechaFinal",
                    "La fecha final debe ser posterior a la fecha de inicio");
        }

        if (result.hasErrors()) {
            // Se encontraron errores en la validación; regresa al formulario de edición
            return "admin/edit";
        }

        if (pelicula.getVideoEmbed() != null) {
            String videoEmbed = pelicula.getVideoEmbed();

            if (videoEmbed.contains("youtube.com/watch")) {
                // Extraer videoId para URLs del formato youtube.com/watch
                String videoId = videoEmbed.split("v=")[1].split("&")[0];
                pelicula.setVideoEmbed("https://www.youtube-nocookie.com/embed/" + videoId);
            } else if (videoEmbed.contains("youtu.be/")) {
                // Extraer videoId para URLs acortadas del formato youtu.be
                String videoId = videoEmbed.split("youtu.be/")[1];
                pelicula.setVideoEmbed("https://www.youtube-nocookie.com/embed/" + videoId);
            } else if (videoEmbed.contains("youtube-nocookie.com/embed/")) {
                // Ya es una URL válida del formato youtube-nocookie
                pelicula.setVideoEmbed(videoEmbed);
            } else {
                // Rechazar si no es una URL válida de YouTube
                result.rejectValue("videoEmbed", "error.videoEmbed",
                        "La URL debe ser de YouTube (youtube.com, youtu.be o youtube-nocookie.com)");
            }
        }

        peliculaRepository.save(pelicula);
        return "redirect:/peliculas";
    }
}
