package com.moto.service.servicio;


import com.moto.service.entidades.Moto;
import com.moto.service.repositorio.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MotoService {
    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> obtenerMotos(){

        return ((List<Moto>) motoRepository.findAll());
    }

    public Moto guardarMoto(Moto moto){
        return motoRepository.save(moto);
    }

    public Moto obtenerPorId(Long id){
        return motoRepository.findById(id).orElse(null);
    }

    public List<Moto> obtenerMotosPorUsuarioId(Long usuarioId){
        return  motoRepository.findByUsuarioId(usuarioId);
    }

}
