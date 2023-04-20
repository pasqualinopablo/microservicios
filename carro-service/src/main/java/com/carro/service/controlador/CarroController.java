package com.carro.service.controlador;



import com.carro.service.entidades.Carro;
import com.carro.service.servicio.CarroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public ResponseEntity<List<Carro>> obtenerCarros(){
        List<Carro> carros = carroService.obtenerCarros();
        if(carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }

    @PostMapping
    public ResponseEntity<Carro> guardarCarro( @RequestBody Carro carro){
        Carro car = carroService.guardarCarro(carro);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(carro.getId()).toUri();
        return ResponseEntity.created(location).body(car);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Carro> obtenerPorId(@PathVariable("id") Long id){
        Carro carro = carroService.obtenerPorId(id);
        if (carro==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carro);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carro>> obtenerCarrosDeUsuario(@PathVariable("usuarioId") Long id){
        List<Carro> carros = carroService.obtenerCarrosPorUsuarioId(id);
        if(carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }

}
