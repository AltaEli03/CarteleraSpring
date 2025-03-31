package com.cartelera.spring.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.cartelera.spring.entity.Pelicula;

public interface PeliculaRepository extends MongoRepository<Pelicula, ObjectId> {
    Page<Pelicula> findByEstadoTrue(Pageable pageable);

    // Para búsquedas
    Page<Pelicula> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    Page<Pelicula> findByGeneroContainingIgnoreCase(String genero, Pageable pageable);
    Page<Pelicula> findByNombreContainingIgnoreCaseAndGeneroContainingIgnoreCase(String nombre, String genero, Pageable pageable);
}
