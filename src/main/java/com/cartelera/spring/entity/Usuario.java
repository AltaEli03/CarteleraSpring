package com.cartelera.spring.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Usuario")
public class Usuario {
    @Id
    private ObjectId idUser;
    private String nombre;
    private String password;
    
    @DBRef(lazy = false)
    private EstadoUsuario estadoUsuario;
}