package com.carro.service.entidades;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name= "CARRO")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String marca;

    private String modelo;

    private Long usuarioId;

}
