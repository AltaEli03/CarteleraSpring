package com.cartelera.spring.entity;

import lombok.Data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "EstadoUsuario")
public class EstadoUsuario {
    @Id
    private ObjectId idUserState;
    private String valor;
    private String descripcion;
}