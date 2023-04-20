package com.moto.service.controlador;



import com.moto.service.entidades.Moto;
import com.moto.service.servicio.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/moto")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @GetMapping
    public ResponseEntity<List<Moto>> obtenerMotos(){
        List<Moto> motos = motoService.obtenerMotos();
        if(motos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }

    @PostMapping
    public ResponseEntity<Moto> guardarMoto( @RequestBody Moto moto){
        Moto car = motoService.guardarMoto(moto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(moto.getId()).toUri();
        return ResponseEntity.created(location).body(car);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Moto> obtenerPorId(@PathVariable("id") Long id){
        Moto moto = motoService.obtenerPorId(id);
        if (moto==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(moto);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> obtenerMotosDeUsuario(@PathVariable("usuarioId") Long id){
        List<Moto> motos = motoService.obtenerMotosPorUsuarioId(id);
        if(motos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }

}
