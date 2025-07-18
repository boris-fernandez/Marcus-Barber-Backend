package com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto;

import com.marcus.barber.Marcus_Barber_Backend.domain.producto.Producto;

import java.io.Serializable;

public record MarcasProducto(String marca) implements Serializable {

    public MarcasProducto (Producto producto){
        this(producto.getMarca());
    }
}
