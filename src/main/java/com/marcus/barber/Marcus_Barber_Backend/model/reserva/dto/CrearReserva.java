package com.marcus.barber.Marcus_Barber_Backend.model.reserva.dto;

import java.util.Date;

public record CrearReserva(
        long idCliente,
        String estilista,
        String turno,
        String sede,
        Date fecha,
        String servicio,
        String detalles
) {
}
