package com.marcus.barber.Marcus_Barber_Backend.domain.pedido.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CrearPedido(
        @NotNull(message = "Los detalles del pedido no deben ser nulos")
        @NotEmpty(message = "Debe haber al menos un producto en el pedido")
        @Valid
        List<DetallesPedidoDTO> detallesPedidoDTOS
) {
}
