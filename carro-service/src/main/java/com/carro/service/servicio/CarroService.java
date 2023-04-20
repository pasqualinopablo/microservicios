package com.carro.service.servicio;


import com.carro.service.entidades.Carro;
import com.carro.service.repositorio.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarroService {
    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> obtenerCarros(){

        return ((List<Carro>) carroRepository.findAll());
    }

    public Carro guardarCarro(Carro carro){
        return carroRepository.save(carro);
    }

    public Carro obtenerPorId(Long id){
        return carroRepository.findById(id).orElse(null);
    }

    public List<Carro> obtenerCarrosPorUsuarioId(Long usuarioId){
        return  carroRepository.findByUsuarioId(usuarioId);
    }

}
