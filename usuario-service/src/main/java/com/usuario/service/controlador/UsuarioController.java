package com.usuario.service.controlador;


import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;

import com.usuario.service.servicio.UsuarioService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @CircuitBreaker(name= "carrosCB", fallbackMethod = "fallBackGetCarros")
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

    @CircuitBreaker(name= "motosCB", fallbackMethod = "fallBackGetMotos")
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

    @CircuitBreaker(name= "carrosCB", fallbackMethod = "fallBackSaveCarro")
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

    @CircuitBreaker(name= "motosCB", fallbackMethod = "fallBackSaveMoto")
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

    @CircuitBreaker(name= "todosCB", fallbackMethod = "fallBackGetTodos")
    @GetMapping("/todos/{usuarioId}")
    public ResponseEntity<Map<String,Object>> listaTodos(@PathVariable("usuarioId") Long id){
        Map<String,Object> todo = usuarioService.getUsuarioAndVehiculo(id);
        return ResponseEntity.ok(todo);
    }

    private ResponseEntity<List<Carro>> fallBackGetCarros(@PathVariable("usuarioId") Long id, RuntimeException exception){
        imprimirMensaje(exception.getMessage());
        return new ResponseEntity("El usuario: "+ id +" tiene los carros en el taller", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<List<Moto>> fallBackGetMotos(@PathVariable("usuarioId") Long id, RuntimeException exception){
        imprimirMensaje(exception.getMessage());
        return new ResponseEntity("El usuario: "+ id +" tiene las motos en el taller", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Carro> fallBackSaveCarro(@PathVariable("usuarioId") Long id, @RequestBody Carro carro, RuntimeException exception){
        imprimirMensaje(exception.getMessage());
        return new ResponseEntity("El usuario: "+ id +" no tiene dinero para los carros", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Moto> fallBackSaveMoto(@PathVariable("usuarioId") Long id, @RequestBody Moto moto, RuntimeException exception){
        imprimirMensaje(exception.getMessage());
        return new ResponseEntity("El usuario: "+ id +" no tiene dinero para las motos", HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String,Object>> fallBackGetTodos(@PathVariable("usuarioId") Long id, RuntimeException exception){
        imprimirMensaje(exception.getMessage());
        return new ResponseEntity("El usuario: "+ id +" tiene los vehiculos en el taller", HttpStatus.BAD_REQUEST);
    }

    private void imprimirMensaje(String message) {
        System.out.println(message);
    }
}
