package com.usuario.service.controlador;


import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerUsuarios(){
        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario( @RequestBody Usuario usuario){
        Usuario user = usuarioService.guardarUsuario(usuario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(location).body(user);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable("id") Long id){
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/carros/{usuarioId}")
    public ResponseEntity<List<Carro>> getCarros(@PathVariable("usuarioId") Long id){
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario==null){
            return ResponseEntity.noContent().build();
        }
        List<Carro> carros = usuarioService.getCarros(id);
        if(Objects.isNull(carros) ||  carros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/motos/{usuarioId}")
    public ResponseEntity<List<Moto>> getMotos(@PathVariable("usuarioId") Long id){
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario==null){
            return ResponseEntity.noContent().build();
        }
        List<Moto> motos = usuarioService.getMotos(id);
        if(Objects.isNull(motos) ||  motos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(motos);
    }

    @PostMapping("/carro/{usuarioId}")
    public ResponseEntity<Carro> guardarCarro(@PathVariable("usuarioId") Long id, @RequestBody Carro carro){
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario==null){
            return ResponseEntity.noContent().build();
        }
        Carro nuevoCarro = usuarioService.saveCarro(id, carro);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(location).body(nuevoCarro);
    }

    @PostMapping("/moto/{usuarioId}")
    public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") Long id, @RequestBody Moto moto){
        Usuario usuario = usuarioService.obtenerPorId(id);
        if (usuario==null){
            return ResponseEntity.noContent().build();
        }
        Moto nuevaMoto = usuarioService.saveMoto(id, moto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(location).body(nuevaMoto);
    }

    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String,Object>> listaTodos(@PathVariable("usuarioId") Long id){
        Map<String,Object> todo = usuarioService.getUsuarioAndVehiculo(id);
        return ResponseEntity.ok(todo);
    }
}
