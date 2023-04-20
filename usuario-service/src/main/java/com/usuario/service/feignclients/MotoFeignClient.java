package com.usuario.service.feignclients;

import com.usuario.service.modelos.Moto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name= "moto-service", url="http://localhost:8003")
public interface MotoFeignClient {

    @PostMapping("/moto")
    public Moto save(@RequestBody Moto moto);

    @GetMapping("/moto/usuario/{usuarioId}")
    public List<Moto> getMotos(@PathVariable("usuarioId") Long usuarioId);
}
