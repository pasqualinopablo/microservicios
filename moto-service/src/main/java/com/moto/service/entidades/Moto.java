package com.moto.service.entidades;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name= "MOTO")
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String marca;

    private String modelo;

    private Long usuarioId;

}
