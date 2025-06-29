package com.marcus.barber.Marcus_Barber_Backend.domain.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {


    @Query("""
        SELECT p FROM pedido p
        WHERE 
            (:cliente IS NULL OR :cliente = '' OR p.usuario.nombre LIKE %:cliente%)
        AND 
            (:estado IS NULL OR p.estado = :estado)
        AND
            (:fecha IS NULL OR DATE(p.fecha) = DATE(:fecha))
    """)
    Page<Pedido> filtrarPorClienteEstadoYFecha(@Param("cliente") String cliente,
                                               @Param("estado") Estado estado,
                                               @Param("fecha") Date fecha,
                                               Pageable pageable);

}
