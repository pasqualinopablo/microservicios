package com.carro.service.repositorio;

import com.carro.service.entidades.Carro;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarroRepository extends CrudRepository<Carro,Long> {
    public abstract List<Carro> findByUsuarioId(Long usuarioId);
}
