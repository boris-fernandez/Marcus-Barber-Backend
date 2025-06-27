package com.marcus.barber.Marcus_Barber_Backend.model.usuario.service;

import com.marcus.barber.Marcus_Barber_Backend.exception.CredencialesInvalidasException;
import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.Rol;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.Usuario;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.UsuarioRepository;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.dto.CrearUsuario;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.dto.DatosUsuario;
import com.marcus.barber.Marcus_Barber_Backend.model.usuario.dto.IniciarSesion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    //Crear usuario
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

        usuarioRepository.save(usuario);

        return new DatosUsuario(usuario);
    }

    //Iniciar Sesion
    public DatosUsuario iniciarSesion(IniciarSesion iniciarSesion) {
        if ((iniciarSesion.nombre() == null || iniciarSesion.nombre().isBlank()) &&
                (iniciarSesion.correo() == null || iniciarSesion.correo().isBlank())) {
            throw new ValidacionException("Debe ingresar el nombre o el correo");
        }
        Optional<Usuario> usuario = usuarioRepository.findByNombreOrCorreo(iniciarSesion.nombre(), iniciarSesion.correo());

        if (usuario.isEmpty() || !usuario.get().getContrasena().equals(iniciarSesion.contrasena())){
            throw new CredencialesInvalidasException("Credenciales incorrectas");
        }

        return new DatosUsuario(usuario.get());
    }


}
