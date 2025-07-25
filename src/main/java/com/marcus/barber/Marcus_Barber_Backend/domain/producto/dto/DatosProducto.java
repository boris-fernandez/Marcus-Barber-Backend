package com.marcus.barber.Marcus_Barber_Backend.domain.producto.dto;

import com.marcus.barber.Marcus_Barber_Backend.domain.producto.Producto;

import java.io.Serializable;

public record DatosProducto (
        long id,
        String nombre,
        String marca,
        int stock,
        double precio,
        String categoria,
        String imagenPrincipal,
        String imagenSecundaria,
        String imagenTerciaria,
        String descripcion
) implements Serializable {
    public DatosProducto(Producto producto) {
        this(producto.getId(), producto.getNombre(), producto.getMarca(), producto.getStock(), producto.getPrecio(), producto.getCategoria(), producto.getImagenPrincipal(),
                producto.getImagenSecundaria(), producto.getImagenTerciaria(), producto.getDescripcion());
    }
}
