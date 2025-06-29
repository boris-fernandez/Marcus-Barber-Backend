package com.marcus.barber.Marcus_Barber_Backend.domain.reclamo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CrearReclamo(
        @NotBlank(message = "El tipo no debe estar vacio")
        String tipo,
        @NotBlank(message = "La categoria servicio no debe estar vacio")
        String categoriaServicio,
        @NotBlank(message = "El canal de atencion no debe estar vacio")
        String canalAtencion,
        @NotNull(message = "La fecha de servicio no debe estar vacio")
        Date fechaServicio,
        @NotBlank(message = "La descripcion no debe estar vacio")
        String descripcion,
        @NotBlank(message = "La solicitud no debe estar vacio")
        String solicitud
) {
}
