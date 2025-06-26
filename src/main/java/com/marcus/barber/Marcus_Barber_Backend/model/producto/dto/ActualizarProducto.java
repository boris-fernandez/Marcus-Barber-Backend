package com.marcus.barber.Marcus_Barber_Backend.model.producto.dto;

import java.util.Optional;

public record ActualizarProducto(
        Optional<String> nombre,
        Optional<String> marca,
        Optional<Integer> stock,
        Optional<Double> precio,
        Optional<String> categoria,
        Optional<String> imagenPrincipal,
        Optional<String> imagenSecundaria,
        Optional<String> imagenTerciaria,
        Optional<String> descripcion
) {

}

