package com.cartelera.spring.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.cartelera.spring.entity.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, ObjectId> {
    Optional<Usuario> findByNombre(String nombre);
}