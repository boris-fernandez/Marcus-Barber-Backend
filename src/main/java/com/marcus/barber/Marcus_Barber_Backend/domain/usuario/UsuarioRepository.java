package com.marcus.barber.Marcus_Barber_Backend.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Usuario findByNombreOrCorreo(String nombre, String correo);

    Optional<Usuario> findByNombre(String nombre);

}
