package com.marcus.barber.Marcus_Barber_Backend.controller;

import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Usuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto.CrearUsuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto.DatosUsuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto.IniciarSesion;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.service.UsuarioService;
import com.marcus.barber.Marcus_Barber_Backend.infra.security.DatosJWTToken;
import com.marcus.barber.Marcus_Barber_Backend.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("auth")
public class AutenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("login")
    public final ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid IniciarSesion datosAutenticacionUsuario){
        Authentication authcatoken = new UsernamePasswordAuthenticationToken(
                datosAutenticacionUsuario.correo(), datosAutenticacionUsuario.contrasena());
        var usuarioAutenticado = authenticationManager.authenticate(authcatoken);
        var JWtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWtoken));
    }

    @PostMapping("crear")
    private ResponseEntity<DatosUsuario> crearUsuario(@RequestBody CrearUsuario crearUsuario, UriComponentsBuilder builder){
        DatosUsuario usuario = usuarioService.crearUsuario(crearUsuario);
        URI uri = builder.path("/usuario/{id}").buildAndExpand(usuario.id()).toUri();

        return ResponseEntity.created(uri).body(usuario);
    }
}
