package com.usuario.service.entidades;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name= "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String nombre;

    private String email;

}
