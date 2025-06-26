package com.marcus.barber.Marcus_Barber_Backend.model.pedido.detallesPedido;

import com.marcus.barber.Marcus_Barber_Backend.model.pedido.Pedido;
import com.marcus.barber.Marcus_Barber_Backend.model.producto.Producto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "detalle_pedido")
@Entity(name = "detallePedido")
public class DetallesPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto")
    private Producto producto;

    private int cantidad;

    private double precioUnitario;
}

