package com.marcus.barber.Marcus_Barber_Backend.model.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("""
        SELECT p FROM pedido p
        WHERE (:cliente IS NOT NULL AND :cliente <> '' AND p.usuario.nombre LIKE CONCAT('%', :cliente, '%'))
        OR (:codigo IS NOT NULL AND :codigo <> '' AND p.codigo LIKE CONCAT('%', :codigo, '%'))
    """)
    List<Pedido> filtrarPorClienteOCodigo(@Param("cliente") String cliente, @Param("codigo") String codigo);

}
