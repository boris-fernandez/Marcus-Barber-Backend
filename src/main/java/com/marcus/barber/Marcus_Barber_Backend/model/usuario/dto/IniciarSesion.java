package com.marcus.barber.Marcus_Barber_Backend.model.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record IniciarSesion(
        String nombre,

        @Email(message = "El correo debe tener un formato válido")
        String correo,

        @NotBlank(message = "La contraseña no puede estar vacía")
        String contrasena
) {
}
