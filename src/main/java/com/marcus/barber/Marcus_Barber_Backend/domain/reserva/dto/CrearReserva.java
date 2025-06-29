package com.marcus.barber.Marcus_Barber_Backend.domain.reserva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CrearReserva(
        @NotBlank(message = "El nombre del estilista no debe estar vacío")
        String estilista,

        @NotBlank(message = "El turno no debe estar vacío")
        String turno,

        @NotBlank(message = "La sede no debe estar vacía")
        String sede,

        @NotNull(message = "La fecha no debe estar vacía")
        Date fecha,

        @NotBlank(message = "El nombre del servicio no debe estar vacío")
        String servicio,

        @NotBlank(message = "Los detalles no deben estar vacíos")
        String detalles
) {
}
