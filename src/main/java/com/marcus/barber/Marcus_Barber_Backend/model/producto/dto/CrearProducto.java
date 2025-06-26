package com.marcus.barber.Marcus_Barber_Backend.model.producto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearProducto(
        @NotBlank(message = "El nombre no puede estar en blanco")
        String nombre,
        @NotBlank(message = "La marca no puede estar en blanco")
        String marca,
        @Min(value = 1, message = "El stock debe ser mayor a 0")
        int stock,
        @NotNull(message = "El nombre no puede esta en blanco")
        double precio,
        @NotBlank(message = "El nombre no puede esta en blanco")
        String categoria,
        @NotBlank(message = "El nombre no puede esta en blanco")
        String imagenPrincipal,
        @NotBlank(message = "El nombre no puede esta en blanco")
        String imagenSecundaria,
        @NotBlank(message = "El nombre no puede esta en blanco")
        String imagenTerciaria,
        @NotBlank(message = "La descripcion no puede estar en blanco")
        String descripcion
) {
}
