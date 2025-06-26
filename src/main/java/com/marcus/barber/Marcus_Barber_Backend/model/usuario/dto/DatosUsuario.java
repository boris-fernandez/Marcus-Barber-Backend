package com.marcus.barber.Marcus_Barber_Backend.model.usuario.dto;

import com.marcus.barber.Marcus_Barber_Backend.model.usuario.Usuario;

public record DatosUsuario(
        Long id,
        String nombre,
        String correo,
        String celular,
        String contrasena
) {
    public DatosUsuario (Usuario usuario){
        this(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getCelular(), usuario.getContrasena());
    }
}
