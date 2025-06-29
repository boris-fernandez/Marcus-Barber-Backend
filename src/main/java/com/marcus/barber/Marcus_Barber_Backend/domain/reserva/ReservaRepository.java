package com.marcus.barber.Marcus_Barber_Backend.domain.reserva;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Page<Reserva> findByUsuario_NombreOrEstilista(String usuarioNombre, String estilista, Pageable pageable);
}
