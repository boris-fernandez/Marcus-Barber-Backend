package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto.CrearUsuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto.DatosUsuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    private ResponseEntity<DatosUsuario> crearUsuario(@RequestBody CrearUsuario crearUsuario, UriComponentsBuilder builder){
        DatosUsuario usuario = usuarioService.crearUsuario(crearUsuario);
        URI uri = builder.path("/usuario/{id}").buildAndExpand(usuario.id()).toUri();

        return ResponseEntity.created(uri).body(usuario);
    }
}
