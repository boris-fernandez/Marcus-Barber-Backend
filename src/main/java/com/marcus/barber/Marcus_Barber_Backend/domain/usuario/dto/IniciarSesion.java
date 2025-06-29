package com.marcus.barber.Marcus_Barber_Backend.domain.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record IniciarSesion(

        @NotBlank(message = "Debes ingresar con tu nombre de usuario o email")
        String nombre,

        @NotBlank(message = "La contraseña no puede estar vacía")
        String contrasena
) {
}
