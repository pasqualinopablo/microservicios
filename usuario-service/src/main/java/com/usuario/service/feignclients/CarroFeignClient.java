package com.usuario.service.feignclients;

import com.usuario.service.modelos.Carro;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name= "carro-service")
public interface CarroFeignClient {

    @PostMapping("/carro")
    public Carro save(@RequestBody Carro carro);

    @GetMapping("/carro/usuario/{usuarioId}")
    public List<Carro> getCarros(@PathVariable("usuarioId") Long usuarioId);
}
