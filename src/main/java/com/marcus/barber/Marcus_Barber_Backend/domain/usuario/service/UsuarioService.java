package com.marcus.barber.Marcus_Barber_Backend.domain.usuario.service;

import com.marcus.barber.Marcus_Barber_Backend.exception.CredencialesInvalidasException;
import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Rol;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Usuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.UsuarioRepository;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto.CrearUsuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto.DatosUsuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto.IniciarSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DatosUsuario crearUsuario(CrearUsuario crearUsuario){
        if (usuarioRepository.findByNombre(crearUsuario.nombre()).isPresent()) {
            throw new ValidacionException("El usuario ya existe");
        }

        Usuario usuario = Usuario.builder()
                .nombre(crearUsuario.nombre())
                .correo(crearUsuario.correo())
                .celular(crearUsuario.celular())
                .contrasena(crearUsuario.contrasena())
                .rol(Rol.CLIENTE)
                .build();
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuarioRepository.save(usuario);

        return new DatosUsuario(usuario);
    }


}
