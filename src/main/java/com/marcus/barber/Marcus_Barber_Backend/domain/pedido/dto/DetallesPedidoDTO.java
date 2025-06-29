package com.marcus.barber.Marcus_Barber_Backend.domain.pedido.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DetallesPedidoDTO(
        @NotNull(message = "El id del producto no debe estar en blanco")
        Long idProducto,
        @Min(value = 1, message = "La cantidad debe ser minimo 1")
        int cantidad
) {
}
