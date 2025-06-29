package com.marcus.barber.Marcus_Barber_Backend.domain.pedido.dto;

import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.Estado;
import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.Pedido;
import com.marcus.barber.Marcus_Barber_Backend.domain.pedido.detallesPedido.DetallesPedido;

import java.util.Date;
import java.util.List;

public record DatosPedido(
        long id,
        String codigo,
        String nombreUsuario,
        Date fecha,
        Estado estado,
        List<DatosDetallesPedido> productos,
        Double total
) {

    public DatosPedido(Pedido pedido, List<DetallesPedido> detalles) {
        this(
                pedido.getId(),
                pedido.getCodigo(),
                pedido.getUsuario().getNombre(),
                pedido.getFecha(),
                pedido.getEstado(),
                detalles.stream()
                        .map(DatosDetallesPedido::new)
                        .toList(),
                pedido.getTotal()
        );
    }
}

