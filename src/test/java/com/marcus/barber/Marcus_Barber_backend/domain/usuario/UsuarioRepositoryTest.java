package com.marcus.barber.Marcus_Barber_backend.domain.usuario;

import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Rol;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Usuario;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @Test
    public void guardarUsuario(){
        usuario = Usuario.builder()
                .nombre("Alberto")
                .celular("94947841")
                .contrasena("123")
                .rol(Rol.CLIENTE)
                .correo("alberto@gmail.com")
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        Assertions.assertThat(usuarioGuardado).isNotNull();
        Assertions.assertThat(usuarioGuardado.getId()).isGreaterThan(0);

    }
}