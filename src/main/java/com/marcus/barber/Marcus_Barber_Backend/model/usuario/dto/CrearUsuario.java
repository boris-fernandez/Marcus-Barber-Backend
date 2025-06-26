package com.marcus.barber.Marcus_Barber_Backend.model.usuario.dto;

public record CrearUsuario(
        String nombre,
        String correo,
        String celular,
        String contrasena
) {
}
