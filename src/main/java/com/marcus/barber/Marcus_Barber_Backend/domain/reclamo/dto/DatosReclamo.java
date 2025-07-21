package com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.dto;

import com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.Reclamo;

import java.io.Serializable;
import java.util.Date;

public record DatosReclamo(
        Long id,
        String idUsuario,
        String tipo,
        String categoriaServicio,
        String canalAtencion,
        Date fechaServicio,
        String descripcion,
        String solicitud
) implements Serializable {
    public DatosReclamo(Reclamo reclamo) {
        this(reclamo.getId(),
                reclamo.getUsuario().getNombre(),
                reclamo.getTipo(),
                reclamo.getCategoriaServicio(),
                reclamo.getCanalAtencion(),
                reclamo.getFechaServicio(),
                reclamo.getDescripcion(),
                reclamo.getSolicitud());
    }

}
