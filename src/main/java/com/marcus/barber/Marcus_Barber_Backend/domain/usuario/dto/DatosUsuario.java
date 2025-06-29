package com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto;

import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Rol;
import com.marcus.barber.Marcus_Barber_Backend.domain.usuario.Usuario;

public record DatosUsuario(
        Long id,
        String nombre,
        String correo,
        String celular,
        Rol rol
) {
    public DatosUsuario (Usuario usuario){
        this(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getCelular(), usuario.getRol());
    }
}
