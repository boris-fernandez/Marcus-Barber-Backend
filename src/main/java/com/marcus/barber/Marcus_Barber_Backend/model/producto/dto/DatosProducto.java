package com.marcus.barber.Marcus_Barber_Backend.model.producto.dto;

import com.marcus.barber.Marcus_Barber_Backend.model.producto.Producto;

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
){
    public DatosProducto(Producto producto) {
        this(producto.getId(), producto.getNombre(), producto.getMarca(), producto.getStock(), producto.getPrecio(), producto.getCategoria(), producto.getImagenPrincipal(),
                producto.getImagenSecundaria(), producto.getImagenTerciaria(), producto.getDescripcion());
    }
}
