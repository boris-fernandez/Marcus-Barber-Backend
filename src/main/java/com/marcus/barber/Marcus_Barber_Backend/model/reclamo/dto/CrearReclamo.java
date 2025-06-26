package com.marcus.barber.Marcus_Barber_Backend.model.reclamo.dto;

import java.util.Date;

public record CrearReclamo(
        Long idUsuario,
        String tipo,
        String categoriaServicio,
        String canalAtencion,
        Date fechaServicio,
        String descripcion,
        String solicitud
) {
}
