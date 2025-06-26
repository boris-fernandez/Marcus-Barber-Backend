package com.marcus.barber.Marcus_Barber_Backend.model.producto;

import com.marcus.barber.Marcus_Barber_Backend.model.producto.dto.ActualizarProducto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "producto")
@Entity(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String marca;

    private Integer stock;

    private Double precio;

    private String categoria;

    private String imagenPrincipal;

    private String imagenSecundaria;

    private String imagenTerciaria;

    private String descripcion;

    private Boolean estado;

    public void actualizar(ActualizarProducto actualizarProducto) {
        actualizarProducto.nombre().ifPresent(value -> this.nombre = value);
        actualizarProducto.marca().ifPresent(value -> this.marca = value);
        actualizarProducto.stock().ifPresent(value -> this.stock = value);
        actualizarProducto.precio().ifPresent(value -> this.precio = value);
        actualizarProducto.categoria().ifPresent(value -> this.categoria = value);
        actualizarProducto.imagenPrincipal().ifPresent(value -> this.imagenPrincipal = value);
        actualizarProducto.imagenSecundaria().ifPresent(value -> this.imagenSecundaria = value);
        actualizarProducto.imagenTerciaria().ifPresent(value -> this.imagenTerciaria = value);
        actualizarProducto.descripcion().ifPresent(value -> this.descripcion = value);
    }

    public void actualizarEstado(){
        this.estado = false;
    }

}
