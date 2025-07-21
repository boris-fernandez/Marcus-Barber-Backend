package com.marcus.barber.Marcus_Barber_Backend.domain.pedido.dto;

import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.detallesPedido.DetallesPedido;

import java.io.Serializable;

public record DatosDetallesPedido(
        Long idProducto,
        String producto,
        int cantidad,
        double precioUnitario
)implements Serializable {
    public DatosDetallesPedido (DetallesPedido detallesPedido){
        this(detallesPedido.getProducto().getId(), detallesPedido.getProducto().getNombre(),
                detallesPedido.getCantidad(), detallesPedido.getPrecioUnitario());
    }
}
