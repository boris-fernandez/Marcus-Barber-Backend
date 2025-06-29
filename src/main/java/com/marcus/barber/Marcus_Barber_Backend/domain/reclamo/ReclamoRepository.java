package com.marcus.barber.Marcus_Barber_Backend.domain.reclamo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, Long> {
    Page<Reclamo> findByTipoOrUsuario_Nombre(Pageable pageable, String tipo, String cliente);
}
