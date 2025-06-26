package com.marcus.barber.Marcus_Barber_Backend.model.pedido.detallesPedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallesPedidosRepository extends JpaRepository<DetallesPedido, Long> {
    List<DetallesPedido> findByPedido_Id(Long pedidoId);
}
