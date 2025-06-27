package com.marcus.barber.Marcus_Barber_Backend.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByNombreOrCorreo(String nombre, String correo);

    Optional<Usuario> findByNombre(String nombre);

}
