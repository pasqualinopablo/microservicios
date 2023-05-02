package com.usuario.service.servicio;


import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarroFeignClient carroFeignClient;

    @Autowired
    private MotoFeignClient motoFeignClient;

    @Value("${moto-service.url}")
    private String motoServiceUrl;

    @Value("${carro-service.url}")
    private String carroServiceUrl;


    public List<Usuario> obtenerUsuarios(){

        return ((List<Usuario>) usuarioRepository.findAll());
    }

    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorId(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Carro> getCarros(Long usuarioId){
        List<Carro> carros = restTemplate.getForObject(carroServiceUrl + "/carro/usuario/" + usuarioId, List.class);
        return carros;
    }
    public List<Moto> getMotos(Long usuarioId){
        List<Moto> motos = restTemplate.getForObject(motoServiceUrl + "/moto/usuario/" + usuarioId, List.class);
        return motos;

    }

    public Carro saveCarro(Long usuarioId, Carro carro){
        carro.setUsuarioId(usuarioId);
        Carro nuevoCarro = carroFeignClient.save(carro);
        return nuevoCarro;
    }

    public Moto saveMoto(Long usuarioId, Moto moto){
        moto.setUsuarioId(usuarioId);
        Moto nuevaMoto = motoFeignClient.save(moto);
        return nuevaMoto;
    }

    public Map<String, Object> getUsuarioAndVehiculo(Long usuarioId){
        Map<String, Object> resultado = new HashMap<>();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if(usuario == null){
            resultado.put("Mensaje", "El usuario no existe");
            return resultado;
        }
        resultado.put("Usuario", usuario);

        List<Carro> carros = carroFeignClient.getCarros(usuarioId);
        if (carros == null || carros.isEmpty()){
            resultado.put("Carros", "El usuario no tiene carros");
        }else {
            resultado.put("Carros",carros);
        }

        List<Moto> motos = motoFeignClient.getMotos(usuarioId);
        if (motos == null || motos.isEmpty()){
            resultado.put("Motos", "El usuario no tiene motos");
        }else {
            resultado.put("Motos", motos);
        }
        return resultado;
    }
}
