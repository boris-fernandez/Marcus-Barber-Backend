package com.marcus.barber.Marcus_Barber_Backend.domain.pedido.dto;

import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.Estado;
import jakarta.validation.constraints.NotNull;

public record ActualizarPedido(
        @NotNull(message = "El estado no debe esta en blanco")
        Estado estado
) {
}
