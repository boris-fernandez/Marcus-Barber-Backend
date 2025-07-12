package com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto;

import com.marcus.barber.Marcus_Barber_Backend.domain.producto.Producto;

public record CategoriasProducto(String categoria) {

    public CategoriasProducto (Producto producto){
        this(producto.getCategoria());
    }
}
