package com.moto.service.repositorio;

import com.moto.service.entidades.Moto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoRepository extends CrudRepository<Moto,Long> {
    public abstract List<Moto> findByUsuarioId(Long usuarioId);
}
