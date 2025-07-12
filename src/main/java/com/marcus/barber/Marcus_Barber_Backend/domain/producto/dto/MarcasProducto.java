package com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto;

import com.marcus.barber.Marcus_Barber_Backend.domain.producto.Producto;

public record MarcasProducto(String marca) {

    public MarcasProducto (Producto producto){
        this(producto.getMarca());
    }
}
