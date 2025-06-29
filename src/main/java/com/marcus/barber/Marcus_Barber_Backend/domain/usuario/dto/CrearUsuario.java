package com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearUsuario(
        @NotBlank(message = "El nombre no puede estar en blanco")
        String nombre,
        @NotBlank(message = "El correo no debe estar en blanco")
        String correo,
        @NotBlank(message = "El celular no debe esta en blanco")
        String celular,
        @NotBlank(message = "La contraseña no debe estar en blanco")
        String contrasena
) {
}
