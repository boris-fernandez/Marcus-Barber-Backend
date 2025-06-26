package com.marcus.barber.Marcus_Barber_Backend.model.pedido.dto;


import java.util.List;

public record CrearPedido(
        Long idUsuario,
        List<DetallesPedidoDTO> detallesPedidoDTOS
) {
}
