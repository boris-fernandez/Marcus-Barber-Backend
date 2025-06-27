package com.marcus.barber.Marcus_Barber_Backend.model.reserva.dto;

import com.marcus.barber.Marcus_Barber_Backend.model.reserva.Reserva;

import java.util.Date;

public record DatosReserva(
        long id,
        String  cliente,
        String estilista,
        String turno,
        String sede,
        Date fecha,
        String servicio,
        String detalles
) {
    public DatosReserva(Reserva reserva) {
        this(reserva.getId(), reserva.getUsuario().getNombre(), reserva.getEstilista(),
                reserva.getTurno(), reserva.getSede(), reserva.getFecha(), reserva.getServicio(),
                reserva.getDetalles());
    }
}
