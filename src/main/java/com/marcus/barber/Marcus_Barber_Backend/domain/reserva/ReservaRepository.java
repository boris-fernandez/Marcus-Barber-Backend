package com.marcus.barber.Marcus_Barber_Backend.domain.reserva;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("""
    SELECT r FROM reserva r
    WHERE (:cliente IS NULL OR r.usuario.nombre = :cliente)
      AND (:estilista IS NULL OR r.estilista = :estilista)
      AND (:sede IS NULL OR r.sede = :sede)
      AND (:fecha IS NULL OR r.fecha = :fecha)
    """)
    Page<Reserva> filtrarReservas(String cliente, String estilista, String sede, Date fecha, Pageable pageable);
}
