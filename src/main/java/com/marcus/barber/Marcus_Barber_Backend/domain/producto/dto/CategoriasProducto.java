package com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto;

import com.marcus.barber.Marcus_Barber_Backend.domain.producto.Producto;

import java.io.Serializable;

public record CategoriasProducto(String categoria) implements Serializable {

    public CategoriasProducto (Producto producto){
        this(producto.getCategoria());
    }
}
