package com.cartelera.spring.entity;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Document(collection = "Peliculas")
public class Pelicula {
    @Id
    private ObjectId idmovie;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
    private String nombre;
    @NotBlank(message = "El género es obligatorio")
    @Size(max = 50, message = "El género no puede exceder los 50 caracteres")
    private String genero;
    @NotBlank(message = "La sinopsis es obligatoria")
    @Size(max = 250, message = "La sinopsis no puede exceder los 250 caracteres")
    private String sinopsis;
    @NotBlank(message = "El horario es obligatorio")
    @Size(max = 15, message = "El horario no puede exceder los 15 caracteres")
    private String horario;
    @NotBlank(message = "La sala es obligatoria")
    @Size(max = 10, message = "La sala no puede exceder los 10 caracteres")
    private String sala;
    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String imagenUrl;
    @NotBlank(message = "La URL del video es obligatorio")
    @Pattern(regexp = "^https://(www\\.)?(youtube\\.com|youtu\\.be|youtube-nocookie\\.com)/.*", message = "La URL debe ser de YouTube (youtube.com, youtu.be o youtube-nocookie.com)")
    private String videoEmbed;
    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
    @NotNull(message = "La fecha de inicio es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @NotNull(message = "La fecha final es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFinal;
}
