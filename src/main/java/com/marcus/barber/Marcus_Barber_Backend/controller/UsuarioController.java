package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.model.usuario.dto.CrearUsuario;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.dto.DatosUsuario;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.dto.IniciarSesion;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.service.UsuarioService;
import jakarta.validation.Valid;
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

    @GetMapping
    private ResponseEntity<DatosUsuario> iniciarSesion(@RequestBody @Valid IniciarSesion iniciarSesion){
        return ResponseEntity.ok(usuarioService.iniciarSesion(iniciarSesion));
    }
}
